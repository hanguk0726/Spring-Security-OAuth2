INSERT INTO `oauth_client_details`(
  `client_id`,
  `resource_ids`,
  `client_secret`,
  `scope`,
  `authorized_grant_types`,
  `web_server_redirect_uri`,
  `authorities`,
  `access_token_validity`,
  `refresh_token_validity`,
  `additional_information`,
  `autoapprove`
  )

  VALUES(
  'admin',
  '{bcrypt}$2a$10$iP9ejueOGXO29.Yio7rqeuW9.yOC4YaV8fJp3eIWbP45eZSHFEwMG',
  null,
  'read_posts, write_post',
  'authorization_code,implicit,password,client_credentials,refresh_token',
  'http://localhost:9000/callback',
  null,
  3000,
  6000,
  null ,
  'false'
  );


  INSERT INTO `oauth_client_details`(
  `client_id`,
  `resource_ids`,
  `client_secret`,
  `scope`,
  `authorized_grant_types`,
  `web_server_redirect_uri`,
  `authorities`,
  `access_token_validity`,
  `refresh_token_validity`,
  `additional_information`,
  `autoapprove`
  )

  VALUES(
  'member',
  '{bcrypt}$2a$10$iP9ejueOGXO29.Yio7rqeuW9.yOC4YaV8fJp3eIWbP45eZSHFEwMG',
  null,
  'read_posts',
  'authorization_code,implicit,password,client_credentials,refresh_token',
  'http://localhost:9000/callback',
  null,
  3000,
  6000,
  null ,
  'false'
  );