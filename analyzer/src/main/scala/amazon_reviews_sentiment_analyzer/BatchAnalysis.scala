package amazon_review_sentiment_analyzer

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object BatchAnalysis {

  def main(args:Array[String]) {

    

    val spark = SparkSession
      .builder
      .appName("Streaming")
      .master("local[4]")
      .config("spark.driver.bindAddress", "127.0.0.1")
      .getOrCreate()

    import spark.implicits._
    val sc: SparkContext = spark.sparkContext

    val inputPath = "/[source path here]/CCA/src/main/resources/streamdata/*.json"
    val outputPath = "/[destination path here]/src/main/result/batch"


    val AFINN = sc.textFile("/insert your source path here/src/main/resources/AFINN.txt").map(x=> x.split("\t")).map(x=>(x(0).toString,x(1).toInt))

    val review_df = spark.read.json(inputPath)
    review_df.printSchema()

    val reviews = review_df.select("review_id", "review_body").collect

    val rSenti = reviews.map(x => {
      val reviewWordsSentiment = x(1).toString.split("[^0-9a-zA-Z-]").map(word => {
        var senti: Int = 0
        if (AFINN.lookup(word.toLowerCase()).nonEmpty) {
          senti = AFINN.lookup(word.toLowerCase()).head
        }
        senti
      })
      val tSentiment = if (reviewWordsSentiment.sum ==0) "neutral" else if (reviewWordsSentiment.sum > 0) "positive" else "negative"
      (x(0).toString,tSentiment)
    })

    val sentiment_df = sc.parallelize(rSenti).toDF("review_id","sentiment")

    val df = review_df.join(sentiment_df, "review_id")

    df.rdd.saveAsTextFile(outputPath)

    spark.stop()

  }
}
