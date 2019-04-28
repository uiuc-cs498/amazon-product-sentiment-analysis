INSERT INTO TABLE REVIEWS_TABLE_MASTER
SELECT
review_id,
helpful_votes,
marketplace,
product_category,
product_id,
product_parent,
review_date,
star_rating,
total_votes,
verified_purchase,
vine,
sentiment
FROM REVIEWS_TABLE_STAGING st WHERE st.review_id NOT IN(SELECT review_id_t FROM TEMP_DUP_REVIEW );