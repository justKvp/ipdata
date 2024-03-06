-- ipdata.domains определение

CREATE TABLE `domains` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                           `domain` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                           `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ipdata.ipdata определение

CREATE TABLE `ipdata` (
                          `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                          `domain_id` bigint unsigned NOT NULL,
                          `ip` varchar(96) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                          `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `comment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;