CREATE EXTERNAL TABLE REVIEWS_TABLE_STAGING(
review_id string,
helpful_votes int,
marketplace string,
product_category string,
product_id string,
product_parent string,
review_date string,
star_rating int,
total_votes int,
verified_purchase string,
vine string,
sentiment string)
ROW FORMAT delimited 
fields terminated by ','
stored as textfile
location '/usr/reviews/reviews_staging';


CREATE EXTERNAL TABLE REVIEWS_TABLE_MASTER(
review_id string,
helpful_votes int,
marketplace string,
product_category string,
product_id string,
product_parent string,
review_date string,
star_rating int,
total_votes int,
verified_purchase string,
vine string,
sentiment string)
stored as ORC
location '/usr/reviews/master'
tblproperties ("orc.compress"="NONE");


CREATE EXTERNAL TABLE TEMP_DUP_REVIEW(
review_id_t string
)
stored as textfile
location '/usr/reviews/temp_dup';