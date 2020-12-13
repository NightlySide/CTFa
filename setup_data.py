from random import randrange, choice
from datetime import datetime, timedelta

create_tables = """
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
);"""

indexes = """
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
"""

init_values = """
INSERT INTO `categories` (`name`) VALUES
('OSINT'),
('Programmation'),
('Stéganographie');

INSERT INTO `challenges` (`category_id`, `title`, `score`, `description`, `link_to_file`, `flag`, `visible`) VALUES
    (1, 'The Lost Park', 50, 'What is the name of this monument?\\r\\n\\r\\nSolve this challenge to unlock another one!\\r\\n\\r\\n![Image](https://images-na.ssl-images-amazon.com/images/I/71ZnqHvbl2L._AC_SY879_.jpg)\\r\\n\\r\\nThe answer is `CTFA{Major Mark Park}`.', NULL, 'CTFA{Major Mark Park}', 1),
    (1, 'Qui est charlie ?', 200, 'Le but : trouver des infos sur charlie.', NULL, 'CTFA{qdoql782klf9dfsfsdfs}', 1),
    (2, 'Vigenère', 100, 'Déchiffrage d''un message chiffré par vigenère...', NULL, 'CTFA{HELLO_WORLD}', 1),
    (3, 'Introduction', 25, 'Caché dans une image :)', 'https://preview.redd.it/ihdbwsphmsw51.png?width=640&height=973&crop=smart&auto=webp&s=580215ed8243e77a60f9bc49db0afcbebf3683c3', 'CTFA{J_ETAIT_BIEN_CACHE}', 1);

INSERT INTO `users` (`username`, `email`, `password`, `team_id`, `role`) VALUES
    ('admin', 'admin@test.com', '$2y$04$up7wpNcrtUiTXDeNdKeFH.gDroZD1oPxV3koMctme.I/BuRbjSbzG', null, 'ROLE_ADMIN'),
    ('user', 'user@test.com', '$2y$04$iDIOYKz5j3vH6bHjDo8sQuuDuFdhCG2Col6gD1jzhlqiByxT2hXc6', null, 'ROLE_USER');
"""

## Start of program

teams = ["ENS7UXNET", "FWORD", "Poseidon", "DefSmarts"]

users = ["thomas", "franck", "leonidas", "faxe0ff", "dat@hunt3r", "foreach", "forrunner", "thyr3l1", "hUm@n", "r0b0t", "françois", "théo", "guillaume", "sytrics"]

## Code génération

print("[ ] data.sql file generation ...\n")

# teams
res_team = "INSERT INTO `teams` (`teamname`, `password`, `open_to_register`) VALUES\n"
for team in teams:
    print(f"[+] Adding team : {team} with id {teams.index(team)+1}")
    res_team += "('" + team + "', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', 1),\n"
res_team = res_team[:-2] + ";"
print("")

# users
res_users = "INSERT INTO `users` (`username`, `email`, `password`, `team_id`, `role`) VALUES\n"
for user in users:
    print(f"[+] Adding user : {user} with id {users.index(user)+1}")
    res_users += f"('{user}', '{user}@gmail.com', '$2y$04$EsY8t/oioAf4wUNzAxYqpOE3vXjT8lzNxpwWiDFtlIYZpviv/e00y', {randrange(len(teams)) + 1}, 'ROLE_USER'),\n"
res_users = res_users[:-2] + ";"
print("")

# challenge solves
current = datetime.now()
res_chall_solve = "INSERT INTO `challenge_solves` (`user_id`, `challenge_id`, `date`) VALUES\n";

# chaque user solve 2 à 3 challenges en moyenne
nbSolves = randrange(1 * len(users), 2 * len(users))
solves = []
for k_solve in range(nbSolves):
    # un solve toutes les 15mn à 1h30
    delta_t = timedelta(seconds=randrange(3600//4, 3600 * 1.5))
    current -= delta_t # calcul le nouveau temps
    solve = (choice(users), randrange(1, 4))
    while (solve in solves):
        solve = (choice(users), randrange(1, 4))
    print(f"[+] Adding solve by {solve[0]} for chall n°{solve[1]}")
    solves.append(solve)
    res_chall_solve += f"({users.index(solve[0]) + 1 + 2}, {solve[1]}, '{current}'),\n"
res_chall_solve = res_chall_solve[:-2] + ";"
print("")

## Writing in file
data = "\n\n".join([create_tables, indexes, init_values, res_team, res_users, res_chall_solve])

with open("src/main/resources/data.sql", "w") as f:
    f.write(data)

print("==> Data setup completed! ready to run the server")