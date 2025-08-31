CREATE TABLE `post`
(
    `id`           int(11)   NOT NULL AUTO_INCREMENT,
    `author_id`    int(11)   NOT NULL,
    `text_content` longtext  NOT NULL,
    `likes_count`  int       NOT NULL,
    `created_at`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `post_author_fk` FOREIGN KEY (`author_id`)
        REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `post__like`
(
    `id`         int(11)   NOT NULL AUTO_INCREMENT,
    `post_id`    int(11)   NOT NULL,
    `user_id`    int(11)   NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `post__like_post_user_uq` (`post_id`, `user_id`),
    CONSTRAINT `post__like_post_fk` FOREIGN KEY (`post_id`)
        REFERENCES `post` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `post__like_user_fk` FOREIGN KEY (`user_id`)
        REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user_follower`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT,
    `user_id`     int(11)   NOT NULL,
    `follower_id` int(11)   NOT NULL,
    `created_at`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_follower_user_follower_uq` (`follower_id`, `user_id`),
    CONSTRAINT `user_follower_user_fk` FOREIGN KEY (`user_id`)
        REFERENCES `post` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `user_follower_follower_fk` FOREIGN KEY (`follower_id`)
        REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;