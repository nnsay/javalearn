CREATE DATABASE IF NOT EXISTS app;

CREATE TABLE `Users`
(
    `id`                        int(11) NOT NULL AUTO_INCREMENT,
    `email`                     varchar(255) DEFAULT NULL,
    `password`                  varchar(255) NOT NULL,
    `firstName`                 varchar(255) DEFAULT NULL,
    `lastName`                  varchar(255) DEFAULT NULL,
    `createdAt`                 datetime     NOT NULL,
    `updatedAt`                 datetime     NOT NULL,
    `isNGAdmin`                 tinyint(1) NOT NULL DEFAULT '0',
    `isOrgAdmin`                tinyint(1) NOT NULL DEFAULT '0',
    `orgId`                     int(11) DEFAULT NULL,
    `accessToken`               varchar(255) DEFAULT NULL,
    `isActive`                  tinyint(1) NOT NULL DEFAULT '1',
    `accessTokenExpirationTime` datetime     DEFAULT NULL,
    `isNGRA`                    tinyint(1) NOT NULL DEFAULT '0',
    `verifyStatus`              int(11) DEFAULT NULL,
    `lastLoggedAt`              datetime     DEFAULT NULL,
    `username`                  varchar(255) DEFAULT NULL,
    `shouldChangePassword`      tinyint(1) NOT NULL DEFAULT '0',
    `localUsername`             varchar(255) DEFAULT NULL,
    `isReviewer`                tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;

INSERT INTO Users(id, email, password, firstName, lastName, createdAt, updatedAt, isNGAdmin, isOrgAdmin, orgId,
                  accessToken, isActive, accessTokenExpirationTime, isNGRA, verifyStatus, lastLoggedAt, username,
                  shouldChangePassword, localUsername, isReviewer)
VALUES (2, 'org1@neuralgalaxy.com', '$2b$10$evq3wd.wuv6Yz5vI6qvizeS99p8Gns.K3sdR0.HeN73bb7.PBdUjG', 'ORGADMIN', null,
        '2021-11-14 15:43:21', '2022-02-21 03:32:34', 0, 1, 2, null, 1, null, 0, null, '2022-02-21 03:32:34', 'haiker',
        0, 'haiker', 0);
