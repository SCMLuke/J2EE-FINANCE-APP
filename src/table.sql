CREATE TABLE `todo` (
    `task_number` int NOT NULL AUTO_INCREMENT,
    `task_name` varchar(255) DEFAULT NULL,
    `task_description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`task_number`)
)