
-- categories
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
    `id` int(11) AUTO_INCREMENT,
    `name` varchar(255) NOT NULL
);

-- challenges
DROP TABLE IF EXISTS `challenges`;
CREATE TABLE `challenges` (
    `id` int(255) AUTO_INCREMENT,
    `category_id` int(11) NOT NULL,
    `title` varchar(512) NOT NULL,
    `score` int(255) NOT NULL,
    `description` text NOT NULL,
    `link_to_file` varchar(2000) DEFAULT NULL,
    `flag` varchar(255) NOT NULL,
    `visible` int(1) NOT NULL
);

-- teams
DROP TABLE IF EXISTS `teams`;
CREATE TABLE `teams` (
    `id` int(11) AUTO_INCREMENT,
    `teamname` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `open_to_register` tinyint(1) NOT NULL
);

-- users
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(25) DEFAULT 'ROLE_USER',
  `team_id` int(11)
);

-- challengesSolves
DROP TABLE IF EXISTS `challenge_solves`;
CREATE TABLE `challenge_solves` (
    `id` int(11) AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `challenge_id` int(11) NOT NULL,
    `date` timestamp NOT NULL
);


-- INDEXES
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `challenges`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `teams`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `teams`
  ADD UNIQUE KEY `teamname` (`teamname`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `users`
  ADD UNIQUE KEY `username` (`username`);

ALTER TABLE `challenge_solves`
    ADD PRIMARY KEY (`id`);
ALTER TABLE `challenge_solves`
    ADD UNIQUE KEY (`user_id`, `challenge_id`);



INSERT INTO `categories` (`name`) VALUES
('OSINT'),
('Programmation'),
('Stéganographie');

INSERT INTO `challenges` (`category_id`, `title`, `score`, `description`, `link_to_file`, `flag`, `visible`) VALUES
    (1, 'The Lost Park', 50, 'What is the name of this monument?\r\n\r\nSolve this challenge to unlock another one!\r\n\r\n![Image](https://images-na.ssl-images-amazon.com/images/I/71ZnqHvbl2L._AC_SY879_.jpg)\r\n\r\nThe answer is `CTFA{Major Mark Park}`.', NULL, 'CTFA{Major Mark Park}', 1),
    (1, 'Qui est charlie ?', 200, 'Le but : trouver des infos sur charlie.', NULL, 'CTFA{qdoql782klf9dfsfsdfs}', 1),
    (2, 'Vigenère', 100, 'Déchiffrage d''un message chiffré par vigenère...', NULL, 'CTFA{HELLO_WORLD}', 1),
    (3, 'Introduction', 25, 'Caché dans une image :)', 'https://preview.redd.it/ihdbwsphmsw51.png?width=640&height=973&crop=smart&auto=webp&s=580215ed8243e77a60f9bc49db0afcbebf3683c3', 'CTFA{J_ETAIT_BIEN_CACHE}', 1);

INSERT INTO `users` (`username`, `email`, `password`, `team_id`, `role`) VALUES
    ('admin', 'admin@test.com', '$2y$04$up7wpNcrtUiTXDeNdKeFH.gDroZD1oPxV3koMctme.I/BuRbjSbzG', null, 'ROLE_ADMIN'),
    ('user', 'user@test.com', '$2y$04$iDIOYKz5j3vH6bHjDo8sQuuDuFdhCG2Col6gD1jzhlqiByxT2hXc6', null, 'ROLE_USER');


INSERT INTO `teams` (`teamname`, `password`, `open_to_register`) VALUES
('ENS7UXNET', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1),
('FWORD', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1),
('Poseidon', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1),
('DefSmarts', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1);

INSERT INTO `users` (`username`, `email`, `password`, `team_id`, `role`) VALUES
('thomas', 'thomas@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1, 'ROLE_USER'),
('franck', 'franck@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 4, 'ROLE_USER'),
('leonidas', 'leonidas@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 2, 'ROLE_USER'),
('faxe0ff', 'faxe0ff@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 4, 'ROLE_USER'),
('dat@hunt3r', 'dat@hunt3r@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 2, 'ROLE_USER'),
('foreach', 'foreach@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 3, 'ROLE_USER'),
('forrunner', 'forrunner@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1, 'ROLE_USER'),
('thyr3l1', 'thyr3l1@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1, 'ROLE_USER'),
('hUm@n', 'hUm@n@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 2, 'ROLE_USER'),
('r0b0t', 'r0b0t@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 2, 'ROLE_USER'),
('françois', 'françois@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 2, 'ROLE_USER'),
('théo', 'théo@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 4, 'ROLE_USER'),
('guillaume', 'guillaume@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 3, 'ROLE_USER'),
('sytrics', 'sytrics@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1, 'ROLE_USER');

INSERT INTO `challenge_solves` (`user_id`, `challenge_id`, `date`) VALUES
(11, 1, '2020-12-12 10:30:06.828391'),
(7, 2, '2020-12-12 10:07:50.828391'),
(14, 2, '2020-12-12 08:48:48.828391'),
(11, 3, '2020-12-12 07:52:25.828391'),
(13, 1, '2020-12-12 06:26:11.828391'),
(6, 2, '2020-12-12 05:03:03.828391'),
(15, 3, '2020-12-12 04:26:08.828391'),
(7, 1, '2020-12-12 03:44:36.828391'),
(12, 3, '2020-12-12 02:38:30.828391'),
(4, 1, '2020-12-12 01:23:55.828391'),
(15, 2, '2020-12-12 01:03:06.828391'),
(3, 2, '2020-12-12 00:18:00.828391'),
(16, 1, '2020-12-11 23:23:38.828391'),
(5, 1, '2020-12-11 22:19:59.828391'),
(4, 2, '2020-12-11 21:26:53.828391'),
(7, 3, '2020-12-11 20:35:00.828391'),
(6, 3, '2020-12-11 19:45:56.828391'),
(3, 3, '2020-12-11 18:55:36.828391'),
(15, 1, '2020-12-11 17:36:46.828391'),
(6, 1, '2020-12-11 17:13:01.828391'),
(4, 3, '2020-12-11 16:09:52.828391'),
(13, 3, '2020-12-11 14:55:36.828391'),
(10, 1, '2020-12-11 14:21:08.828391'),
(14, 1, '2020-12-11 13:16:49.828391'),
(11, 2, '2020-12-11 12:49:37.828391');