create database if not exists blind_app;
use blind_app;

DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS POST;
DROP TABLE IF EXISTS LIKES;
DROP TABLE IF EXISTS REVIEW;
DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS POST_CATEGORY;
DROP TABLE IF EXISTS CORPORATION;

CREATE TABLE POST
(
    post_id                int(11)      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    post_title             varchar(100) NOT NULL,
    post_content           varchar(500) NOT NULL,
    post_hit               int(11)      NOT NULL DEFAULT 0,
    post_like              int(11)      NOT NULL DEFAULT 0,
    post_userId            varchar(100) NOT NULL,
    post_username          varchar(100) NOT NULL,
    post_corporation_title varchar(100) NOT NULL,
    post_created_at        datetime     NOT NULL DEFAULT now(),
    post_updated_at        datetime     NOT NULL DEFAULT now(),
    post_deleted_at        datetime     NULL,
    post_category_id       int(11)      NOT NULL,
    post_member_id         int(11)      NOT NULL
);

CREATE TABLE COMMENT
(
    comment_id                int(11)      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    comment_content           varchar(500) NOT NULL,
    comment_username          varchar(20)  NOT NULL,
    comment_corporation_title varchar(50)  NOT NULL,
    comment_like              int(11)      NOT NULL DEFAULT 0,
    comment_created_at        datetime     NOT NULL DEFAULT now(),
    comment_updated_at        datetime     NOT NULL DEFAULT now(),
    comment_deleted_at        datetime     NULL,
    comment_post_id           int(11)      NOT NULL,
    comment_member_id         int(11)      NOT NULL
);


CREATE TABLE MEMBER
(
    member_id             int(11)     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    member_userId         varchar(20) NOT NULL,
    member_nickname       varchar(20) NOT NULL,
    member_username       varchar(20) NOT NULL,
    member_created_at     datetime    NOT NULL DEFAULT now(),
    member_updated_at     datetime    NOT NULL DEFAULT now(),
    member_deleted_at     datetime    NULL,
    member_corporation_id int(11)     NOT NULL
);


CREATE TABLE POST_CATEGORY
(
    post_category_id         int(11)     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    post_category_title      varchar(20) NOT NULL,
    post_category_created_at datetime    NOT NULL DEFAULT now(),
    post_category_updated_at datetime    NOT NULL DEFAULT now(),
    post_category_deleted_at datetime    NULL
);

CREATE TABLE CORPORATION
(
    corporation_id                   int(11)      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    corporation_title                varchar(100) NOT NULL,
    corporation_size_type            varchar(100) NOT NULL,
    corporation_business_type        varchar(100) NOT NULL,
    corporation_head_office_location varchar(100) NULL,
    corporation_min_annual_salary    int(11)      NOT NULL DEFAULT 0,
    corporation_max_annual_salary    int(11)      NOT NULL DEFAULT 0,
    corporation_review_average_point tinyint(3)   NOT NULL DEFAULT 0,
    corporation_created_at           datetime     NOT NULL DEFAULT now(),
    corporation_updated_at           datetime     NOT NULL DEFAULT now(),
    corporation_deleted_at           datetime     NULL
);


CREATE TABLE LIKES
(
    like_id          int(11)     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    like_type        varchar(20) NOT NULL,
    like_target_type varchar(20) NOT NULL,
    like_target_id   int(11)     NOT NULL,
    like_created_at  datetime    NOT NULL DEFAULT now(),
    like_updated_at  datetime    NOT NULL DEFAULT now(),
    like_deleted_at  datetime    NULL,
    like_member_id   int(11)     NOT NULL
);

CREATE TABLE REVIEW
(
    review_id                       int(11)      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    review_corporation_title        varchar(50)  NOT NULL,
    review_total_point              tinyint(3)       NOT NULL,
    review_career_improvement_point tinyint(3)       NOT NULL,
    review_work_life_balance_point  tinyint(3)       NOT NULL,
    review_salary_welfare_point     tinyint(3)       NOT NULL,
    review_corporate_culture_point  tinyint(3)       NOT NULL,
    review_management_point         tinyint(3)       NOT NULL,
    review_one_sentence_comment     varchar(200) NULL,
    review_pros                     varchar(200) NULL,
    review_cons                     varchar(200) NULL,
    review_created_at               datetime     NOT NULL DEFAULT now(),
    review_updated_at               datetime     NOT NULL DEFAULT now(),
    review_deleted_at               datetime     NULL,
    review_corporation_id           int(11)      NOT NULL,
    review_member_id                int(11)      NOT NULL
);

ALTER TABLE POST ADD CONSTRAINT FK_POST_CATEGORY_TO_POST_1
        FOREIGN KEY (post_category_id) REFERENCES POST_CATEGORY (post_category_id);

ALTER TABLE POST ADD CONSTRAINT FK_MEMBER_TO_POST_1
        FOREIGN KEY (post_member_id) REFERENCES MEMBER (member_id);

ALTER TABLE COMMENT ADD CONSTRAINT FK_POST_TO_COMMENT_1
        FOREIGN KEY (comment_post_id) REFERENCES POST (post_id);

ALTER TABLE COMMENT ADD CONSTRAINT FK_MEMBER_TO_COMMENT_1
        FOREIGN KEY (comment_member_id) REFERENCES MEMBER (member_id);

ALTER TABLE MEMBER
    ADD CONSTRAINT FK_CORPORATION_TO_MEMBER_1
        FOREIGN KEY (member_corporation_id)
            REFERENCES CORPORATION (corporation_id);

ALTER TABLE LIKES ADD CONSTRAINT FK_MEMBER_TO_LIKES_1
        FOREIGN KEY (like_member_id) REFERENCES MEMBER (member_id);

ALTER TABLE REVIEW ADD CONSTRAINT FK_CORPORATION_TO_REVIEW_1
        FOREIGN KEY (review_corporation_id) REFERENCES CORPORATION (corporation_id);

ALTER TABLE REVIEW ADD CONSTRAINT FK_MEMBER_TO_REVIEW_1
        FOREIGN KEY (review_member_id) REFERENCES MEMBER (member_id)

# INSERT INTO POST(post_id,
#                  post_title,
#                  post_content,
#                  post_userId,
#                  post_username,
#                  post_corporation_title,
#                  post_category_id,
#                  post_member_id)
# VALUES (2, "testTitle", "content", "test", "test", "Test", 1, 1);