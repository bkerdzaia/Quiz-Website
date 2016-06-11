
/* 
-- The script initializes database scheme with it's 
-- tables from scratch. Various constraint are added
-- such as primary/foreign key, etc. No rows are 
-- being added; only table declarations. If database
-- is already populated with entries, you may consider
-- deleting all the tables, with scripts located in 
-- the same directory.
*/

/* Weaken some check, such as foreign key constaint, etc. (Auto-generated) */
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema QuizWebsite
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema QuizWebsite
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `QuizWebsite` DEFAULT CHARACTER SET utf8 ;
USE `QuizWebsite` ;


-- -----------------------------------------------------
-- Table `QuizWebsite`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`users` (

  `user_id`           INT       NOT NULL AUTO_INCREMENT,
  `username`          CHAR(45)  NOT NULL,
  `passw_hash`        INT       NOT NULL,
  `profile_pic_url`   TEXT      NULL, # default
  `description`       TEXT      NULL, # to null

  PRIMARY KEY (`user_id`))

ENGINE = InnoDB
COMMENT = 'Table stores users of system.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`quiz_categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`quiz_categories` (

  `category_id`      INT       NOT NULL,
  `category_name`    CHAR(45)  NULL,

  PRIMARY KEY (`category_id`))

ENGINE = InnoDB
COMMENT = 'Stores thematic categories, e.g. Geograpy, Math...';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`quizzes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`quizzes` (

  `quiz_id`          INT       NOT NULL,
  `quiz_name`        CHAR(45)  NOT NULL,
  `creation_date`    DATE      NOT NULL,
  `category_id`      INT       NOT NULL,
  `quiz_content`     BLOB      NOT NULL,
  `quiz_creator_id`  INT       NOT NULL,

  PRIMARY KEY (`quiz_id`, `quiz_creator_id`),

  /* Indexing foreign keys */
  INDEX `fk_quiz_to_category_idx` (`category_id` ASC),
  INDEX `fk_quiz_to_creator_idx` (`quiz_creator_id` ASC),

  /* Adding foreign key constraint to `category_id` and `quiz_creator_id`. */ 
  CONSTRAINT `fk_quiz_to_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `QuizWebsite`.`quiz_categories` (`category_id`),
  CONSTRAINT `fk_quiz_to_creator`
    FOREIGN KEY (`quiz_creator_id`)
    REFERENCES `QuizWebsite`.`users` (`user_id`))

ENGINE = InnoDB
COMMENT = 'Stores quiz blobs (serialized objects) alongside with some properties.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`quizzes_taken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`quizzes_taken` (

  `quiz_id`         INT  NOT NULL,
  `attempt_id`      INT  NOT NULL,
  `user_id`         INT  NOT NULL,
  `score`           INT  NOT NULL,
  `attempt_date`    DATE NOT NULL,
  
  PRIMARY KEY (`attempt_id`),
  
  /* Indexing foreign keys */
  INDEX `fk_quizzes_has_users_users1_idx` (`user_id` ASC),
  INDEX `fk_quizzes_has_users_quizzes1_idx` (`quiz_id` ASC),

  /* Adding foreign key constraints. */ 
  CONSTRAINT `fk_quizzes_has_users_quizzes1`
    FOREIGN KEY (`quiz_id`)
    REFERENCES `QuizWebsite`.`quizzes` (`quiz_id`),
  CONSTRAINT `fk_quizzes_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `QuizWebsite`.`users` (`user_id`))

ENGINE = InnoDB
COMMENT = 'Implements many-to-many relationship between users and
           quizzes. Also stores score and date of each attempt.';

-- -----------------------------------------------------
-- Table `QuizWebsite`.`friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`friends` (

  `first_user_id`      INT            NOT NULL,  # initiator of friendship
  `second_users_id`    INT            NOT NULL,  # the one to accept
  `status`             ENUM('0','1')  NOT NULL,
  `friendship_id`      INT            NOT NULL, 

  PRIMARY KEY (`friendship_id`),

  /* Indexing foreign keys */
  INDEX `fk_users_has_users_users2_idx` (`second_users_id` ASC),
  INDEX `fk_users_has_users_users1_idx` (`first_user_id` ASC),

  /* Adding foreign key constraints. */ 
  CONSTRAINT `fk_users_has_users_users1`
    FOREIGN KEY (`first_user_id`)
    REFERENCES `QuizWebsite`.`users` (`user_id`),
  CONSTRAINT `fk_users_has_users_users2`
    FOREIGN KEY (`second_users_id`)
    REFERENCES `QuizWebsite`.`users` (`user_id`))

ENGINE = InnoDB
COMMENT = 'first_user is\nthe initiator of \nfriendship,\nsecond_user \nshould accept \nrequest.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`messages` (

  `message_id`               INT               NOT NULL,
  `friendship_id`            INT               NOT NULL,
  `text`                     TEXT              NULL,
  `sent_date`                DATE              NULL,
  `sender`                   ENUM('0', '1')    NULL,  # indicates which part sent particular message.

  PRIMARY KEY (`message_id`),

  /* Indexing foreign keys */
  INDEX `fk_messages_friends1_idx` (`friendship_id` ASC),

  /* Adding foreign key constraint*/ 
  CONSTRAINT `fk_messages_friends1`
    FOREIGN KEY (`friendship_id`)
    REFERENCES `QuizWebsite`.`friends` (`friendship_id`))

ENGINE = InnoDB
COMMENT = 'Stores messages associated with particular friendship entry.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`badges`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`badges` (

  `badge_id`               INT           NOT NULL,
  `badge_description`      VARCHAR(45)   NULL,
  `badge_picture_url`      VARCHAR(45)   NULL,
  `badgescol`              VARCHAR(45)   NULL,

  PRIMARY KEY (`badge_id`))

ENGINE = InnoDB
COMMENT = 'Stores various badges the users are rewarded with when 
           achieving some milestone.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`users_badges`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`users_badges` (

  `user_id` INT NOT NULL,
  `badge_id` INT NOT NULL,

  PRIMARY KEY (`user_id`, `badge_id`),

  /* Indexing foreign keys */
  INDEX `fk_users_has_badges_badges1_idx` (`badge_id` ASC),
  INDEX `fk_users_has_badges_users1_idx` (`user_id` ASC),

  /* Adding foreign key constraints. */ 
  CONSTRAINT `fk_users_has_badges_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `QuizWebsite`.`users` (`user_id`),
  CONSTRAINT `fk_users_has_badges_badges1`
    FOREIGN KEY (`badge_id`)
    REFERENCES `QuizWebsite`.`badges` (`badge_id`))

ENGINE = InnoDB
COMMENT = 'many to many relation\nbetween users and various\nbadges.';

/* Re-enable all the constraints */
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
