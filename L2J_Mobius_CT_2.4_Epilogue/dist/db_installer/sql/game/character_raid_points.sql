CREATE TABLE IF NOT EXISTS `character_raid_points` (
  `charId` INT UNSIGNED NOT NULL DEFAULT 0,
  `boss_id` INT UNSIGNED NOT NULL DEFAULT 0,
  `points` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`charId`,`boss_id`)
) DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;