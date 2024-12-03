INSERT INTO account (id, username, email, password) VALUES
(31, 'johndoe', 'johndoe@example.com', 'hashed_password1'),
(32, 'janedoe', 'janedoe@example.com', 'hashed_password2'),
(33, 'alexsmith', 'alexsmith@example.com', 'hashed_password3'),
(34, 'mariabrown', 'mariabrown@example.com', 'hashed_password4'),
(35, 'johnwick', 'johnwick@example.com', 'hashed_password5');

INSERT INTO team (id, name, description, leader_id) VALUES
(21, 'The Avengers', 'Team of superheroes saving the world', 31),
(22, 'The Spartans', 'Elite gaming squad', 33),
(23, 'Code Masters', 'Developers solving coding challenges', 34),
(24, 'Dream Team', 'Group of ambitious players', 35),
(25, 'Shadow Ninjas', 'Stealth and strategy specialists', NULL);

INSERT INTO team_members (team_id, members_id) VALUES
(21, 31),
(21, 32),
(21, 33),
(21, 34),
(21, 35),
(22, 33),
(23, 31),
(23, 34),
(24, 35),
(25, 32),
(25, 33);
