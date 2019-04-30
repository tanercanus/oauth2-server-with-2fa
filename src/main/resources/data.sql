INSERT INTO USERS (ID, USERNAME,PASSWORD) VALUES (1, 'user','$2a$08$fL7u5xcvsZl78su29x1ti.dxI.9rYO8t0q5wk2ROJ.1cdR53bmaVG');
INSERT INTO USERS (ID, USERNAME,PASSWORD) VALUES (2, 'user-taner','$2a$08$fL7u5xcvsZl78su29x1ti.dxI.9rYO8t0q5wk2ROJ.1cdR53bmaVG');


INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('ClientId', '$2a$10$ytAeYtSKiU4IaQ.fZeoZvO1xfKjNGBWPD5Dv5pjmpO3kZF1wVALYi', 'user_info,read,write,taner',
	'authorization_code',
	'http://localhost:8082/ui/login, http://localhost:8082/ui/secure, http://localhost:8081/auth/principal,/',
	'ROLE_TWO_FACTOR_AUTHENTICATION_ENABLED', 20, 20, null, false);
