CREATE TABLE `feature` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `number_of_runs_required` int NOT NULL,
  `run_configs` varbinary(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


