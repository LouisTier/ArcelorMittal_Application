SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
DROP DATABASE IF EXISTS rugo;
CREATE DATABASE IF NOT EXISTS rugo DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE rugo;

CREATE TABLE table_data_orowan_in (
  id int(11) NOT NULL,
  Cas int(11) NOT NULL,
  He double NOT NULL,
  Hs double NOT NULL,
  Te double NOT NULL,
  Ts double NOT NULL,
  Diam_WR double NOT NULL,
  WRyoung double NOT NULL,
  offset double NOT NULL,
  mu_ini double NOT NULL,
  Force_data double NOT NULL,
  G double NOT NULL,
  nom_poste varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE table_data_orowan_out (
  id int(11) NOT NULL,
  case_ int(11) NOT NULL,
  Error varchar(30) NOT NULL,
  OffsetYield double NOT NULL,
  Friction double NOT NULL,
  Rolling_Torque double NOT NULL,
  Sigma_Moy double NOT NULL,
  Sigma_Ini double NOT NULL,
  Sigma_Out double NOT NULL,
  Sigma_Max double NOT NULL,
  Force_Error double NOT NULL,
  Slip_Error double NOT NULL,
  Has_Converged varchar(30) NOT NULL,
  nom_poste varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE table_donnee_affichage (
  id int(11) NOT NULL,
  roll_speed double NOT NULL,
  friction double NOT NULL,
  sigma double NOT NULL,
  nom_poste varchar(30) NOT NULL,
  erreur varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE table_donnee_capteurs_out (
  id int(11) NOT NULL,
  lp int(11) NOT NULL,
  MatId int(11) NOT NULL,
  XTime double NOT NULL,
  Xloc double NOT NULL,
  EnThick double NOT NULL,
  ExThick double NOT NULL,
  EnTens double NOT NULL,
  ExTens double NOT NULL,
  RollForce double NOT NULL,
  FSlip double NOT NULL,
  Daiameter double NOT NULL,
  Rolled_length_for_Work_Rolls double NOT NULL,
  YoungModulus double NOT NULL,
  Bockup_roll_dia double NOT NULL,
  Rolled_length_for_Backup_Rolls double NOT NULL,
  Mu double NOT NULL,
  Torque double NOT NULL,
  AverageSigma double NOT NULL,
  InputError double NOT NULL,
  LubWFlUp double NOT NULL,
  LubWFlLo double NOT NULL,
  LubOilFlUp double NOT NULL,
  LubOilFlLo double NOT NULL,
  Work_roll_speed double NOT NULL,
  nom_poste varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE table_user (
  id int(11) NOT NULL,
  username varchar(30) NOT NULL,
  password varchar(50) NOT NULL,
  admin int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO table_user (id, username, password, admin) VALUES
(2, 'admin', '21232f297a57a5a743894a0e4a801fc3', 1),
(3, 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 1),
(14, 'user2', 'ee11cbb19052e40b07aac0ca060c23ee', 0);


ALTER TABLE table_data_orowan_in
  ADD PRIMARY KEY (id);

ALTER TABLE table_data_orowan_out
  ADD PRIMARY KEY (id);

ALTER TABLE table_donnee_affichage
  ADD PRIMARY KEY (id);

ALTER TABLE table_donnee_capteurs_out
  ADD PRIMARY KEY (id);

ALTER TABLE table_user
  ADD PRIMARY KEY (id),
  ADD UNIQUE KEY `unique` (username);


ALTER TABLE table_data_orowan_in
  MODIFY id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE table_data_orowan_out
  MODIFY id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE table_donnee_affichage
  MODIFY id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE table_donnee_capteurs_out
  MODIFY id int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE table_user
  MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
