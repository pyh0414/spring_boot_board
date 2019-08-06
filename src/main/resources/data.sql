INSERT INTO `user` (`username`, `password`, `name`)
VALUES ('admin', '$2a$10$fVq6aC5iN0d0k5.5TK7Mz.EYxbC.4eW0kOgyyVyPL06AAt800FxMq', '관리자');

INSERT INTO `user_role` (`user_id`, `roles`) VALUES
('1', 'ROLE_MANAGE_USER'),
('1', 'ROLE_MANAGE_NOTICE'),
('1', 'ROLE_MANAGE_FREEBOARD'),
('1', 'ROLE_MANAGE_DEPARTMENT');
