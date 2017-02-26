CREATE TABLE goal (
	bucket_list_id BIGINT,
	description VARCHAR(1000),

	CONSTRAINT FOREIGN KEY fk_goal_bucket_list(bucket_list_id) REFERENCES bucket_list(id)
);
