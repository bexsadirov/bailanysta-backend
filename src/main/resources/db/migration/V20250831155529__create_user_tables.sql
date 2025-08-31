CREATE TABLE `user`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(256) NOT NULL,
    `username`   VARCHAR(256) NOT NULL,
    `name`       varchar(256) NOT NULL,
    `surname`    varchar(256) NOT NULL,
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `user_email_uq` (`email`),
    INDEX `user_username_uq` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user__password`
(
    `user_id`  int(11)      NOT NULL,
    `password` varchar(256) NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `user__password_user_fk` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `image`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `entity_id`  int(11)      NOT NULL,
    `type`       varchar(255) NOT NULL,
    `file_name`  varchar(255) NOT NULL,
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `image_entity_idx` (`entity_id`, `type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `confirmation_code`
(
    `id`             int(11)      NOT NULL AUTO_INCREMENT,
    `type`           varchar(15)  NOT NULL,
    `email`          varchar(255)          DEFAULT NULL,
    `attempts`       int(11)      NOT NULL DEFAULT 0,
    `create_time`    datetime     NOT NULL,
    `codes`          varchar(256) NOT NULL,
    `last_sent_time` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `confirmation_code_email_uq` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
