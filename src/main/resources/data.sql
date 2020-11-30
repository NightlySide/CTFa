-- categories
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
    `id` int(11) AUTO_INCREMENT,
    `name` varchar(255) NOT NULL
);

INSERT INTO `categories` (`name`) VALUES
('OSINT'),
('Programmation'),
('Stéganographie');

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

INSERT INTO `challenges` (`category_id`, `title`, `score`, `description`, `link_to_file`, `flag`, `visible`) VALUES
    (1, 'The Lost Park', 50, 'What is the name of this monument?\r\n\r\nSolve this challenge to unlock another one!\r\n\r\n![Image](https://images-na.ssl-images-amazon.com/images/I/71ZnqHvbl2L._AC_SY879_.jpg)\r\n\r\nThe answer is `CTFA{Major Mark Park}`.', NULL, 'CTFA{Major Mark Park}', 1),
    (1, 'Qui est charlie ?', 200, 'Le but : trouver des infos sur charlie.', NULL, 'CTFA{qdoql782klf9dfsfsdfs}', 1),
    (2, 'Vigenère', 100, 'Déchiffrage d''un message chiffré par vigenère...', NULL, 'CTFA{HELLO_WORLD}', 1),
    (3, 'Introduction', 25, 'Caché dans une image :)', 'https://preview.redd.it/ihdbwsphmsw51.png?width=640&height=973&crop=smart&auto=webp&s=580215ed8243e77a60f9bc49db0afcbebf3683c3', 'CTFA{J_ETAIT_BIEN_CACHE}', 1);

-- teams
DROP TABLE IF EXISTS `teams`;
CREATE TABLE `teams` (
    `id` int(11) AUTO_INCREMENT,
    `teamname` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `open_to_register` tinyint(1) NOT NULL
);

INSERT INTO `teams` (`teamname`, `password`, `open_to_register`) VALUES
    ('ENS7UXN3T', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1),
    ('F''WORD', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1);

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

INSERT INTO `users` (`username`, `email`, `password`, `team_id`, `role`) VALUES
    ('nightlyside', 'nightlyside@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1, 'ROLE_ADMIN'),
    ('sytrics', 'sytrics@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1, 'ROLE_ADMIN'),
    ('face0ff', 'faceoff@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 2, 'ROLE_USER'),
    ('franz', 'francois.schmidt@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 2, 'ROLE_USER'),
    ('admin', 'admin@test.com', '$2y$04$up7wpNcrtUiTXDeNdKeFH.gDroZD1oPxV3koMctme.I/BuRbjSbzG', null, 'ROLE_ADMIN'),
    ('user', 'user@test.com', '$2y$04$iDIOYKz5j3vH6bHjDo8sQuuDuFdhCG2Col6gD1jzhlqiByxT2hXc6', null, 'ROLE_USER');

-- challengesSolves
DROP TABLE IF EXISTS `challenge_solves`;
CREATE TABLE `challenge_solves` (
    `id` int(11) AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `challenge_id` int(11) NOT NULL,
    `date` timestamp NOT NULL
);

INSERT INTO `challenge_solves` (`user_id`, `challenge_id`, `date`) VALUES
    (1, 1, '2020-11-20 12:45:00'),
    (1, 3, '2020-11-21 05:15:15'),
    (3, 1, '2020-11-20 13:15:00'),
    (3, 2, '2020-11-21 08:03:00'),
    (2, 2, '2020-11-22 15:15:00'),
    (4, 4, '2020-11-22 13:54:00');

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