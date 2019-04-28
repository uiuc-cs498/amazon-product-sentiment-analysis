package com.kg.lambda;

public class AWSReview {

	private String marketplace;
	private String customer_id;
	private String review_id;
	private String product_id;
	private String product_parent;
	private String product_title;
	private String product_category;
	private String star_rating;
	private String helpful_votes;
	private String total_votes;
	private String vine;
	private String verified_purchase;
	private String review_headline;
	private String review_body;
	private String review_date;
	
	public AWSReview(String marketplace, String customer_id, String review_id, String product_id, String product_parent,
			String product_title, String product_category, String star_rating, String helpful_votes, String total_votes,
			String vine, String verified_purchase, String review_headline, String review_body, String review_date) {
		super();
		this.marketplace = marketplace;
		this.customer_id = customer_id;
		this.review_id = review_id;
		this.product_id = product_id;
		this.product_parent = product_parent;
		this.product_title = product_title;
		this.product_category = product_category;
		this.star_rating = star_rating;
		this.helpful_votes = helpful_votes;
		this.total_votes = total_votes;
		this.vine = vine;
		this.verified_purchase = verified_purchase;
		this.review_headline = review_headline;
		this.review_body = review_body;
		this.review_date = review_date;
	}
	
	public String getMarketplace() {
		return marketplace;
	}
	public void setMarketplace(String marketplace) {
		this.marketplace = marketplace;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getReview_id() {
		return review_id;
	}
	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_parent() {
		return product_parent;
	}
	public void setProduct_parent(String product_parent) {
		this.product_parent = product_parent;
	}
	public String getProduct_title() {
		return product_title;
	}
	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}
	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	public String getStar_rating() {
		return star_rating;
	}
	public void setStar_rating(String star_rating) {
		this.star_rating = star_rating;
	}
	public String getHelpful_votes() {
		return helpful_votes;
	}
	public void setHelpful_votes(String helpful_votes) {
		this.helpful_votes = helpful_votes;
	}
	public String getTotal_votes() {
		return total_votes;
	}
	public void setTotal_votes(String total_votes) {
		this.total_votes = total_votes;
	}
	public String getVine() {
		return vine;
	}
	public void setVine(String vine) {
		this.vine = vine;
	}
	public String getVerified_purchase() {
		return verified_purchase;
	}
	public void setVerified_purchase(String verified_purchase) {
		this.verified_purchase = verified_purchase;
	}
	public String getReview_headline() {
		return review_headline;
	}
	public void setReview_headline(String review_headline) {
		this.review_headline = review_headline;
	}
	public String getReview_body() {
		return review_body;
	}
	public void setReview_body(String review_body) {
		this.review_body = review_body;
	}
	public String getReview_date() {
		return review_date;
	}
	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}

	@Override
	public String toString() {
		return "AWSReview [marketplace=" + marketplace + ", customer_id=" + customer_id + ", review_id=" + review_id
				+ ", product_id=" + product_id + ", product_parent=" + product_parent + ", product_title="
				+ product_title + ", product_category=" + product_category + ", star_rating=" + star_rating
				+ ", helpful_votes=" + helpful_votes + ", total_votes=" + total_votes + ", vine=" + vine
				+ ", verified_purchase=" + verified_purchase + ", review_headline=" + review_headline + ", review_body="
				+ review_body + ", review_date=" + review_date + "]";
	}
	
	
	
}
