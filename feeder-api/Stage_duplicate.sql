INSERT INTO TABLE TEMP_DUP_REVIEW
SELECT
st.review_id
FROM REVIEWS_TABLE_STAGING st, REVIEWS_TABLE_MASTER m
WHERE st.review_id = m.review_id AND st.review_date = m.review_date;