CREATE TABLE IF NOT EXISTS `character_friends` (
  `charId` INT UNSIGNED NOT NULL DEFAULT 0,
  `friendId` INT UNSIGNED NOT NULL DEFAULT 0,
  `relation` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`charId`,`friendId`)
) DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;