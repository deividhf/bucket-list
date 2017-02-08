CREATE TABLE bucket_list (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	user_id BIGINT NOT NULL,
	description VARCHAR(100),

	CONSTRAINT FOREIGN KEY fk_bucket_list_user(user_id) REFERENCES user(id),
  	UNIQUE KEY uk_bucket_list_user_description (user_id, description)
);
