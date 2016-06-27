
/* 
-- The script initializes database scheme with it's 
-- tables from scratch. Various constraint are added
-- such as primary/foreign key, etc. No rows are 
-- being added; only table declarations. If database
-- is already populated with entries, you may consider
-- deleting all the tables, with scripts located in 
-- the same directory.
*/

/* Weaken some check, such as foreign key constaint, etc. */

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

  `username`          CHARACTER(45)  NOT NULL,
  `passw_hash`        CHAR(20)       NOT NULL,
  `profile_pic_url`   TEXT           NULL, # default
  `description`       TEXT           NULL, # to null

  PRIMARY KEY (`username`))

ENGINE = InnoDB
COMMENT = 'Table stores users of system.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`quiz_categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`quiz_categories` (

  `category_id`      INT           NOT NULL,
  `category_name`    VARCHAR(45)   NOT NULL,

  PRIMARY KEY (`category_id`))

ENGINE = InnoDB
COMMENT = 'Stores thematic categories, e.g. Geograpy, Math...';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`quizzes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`quizzes` (

  `quiz_name`           CHARACTER(45)  NOT NULL,
  `creation_date`       TIMESTAMP      NOT NULL,
  `quiz_creator`        CHARACTER(45)  NOT NULL,  # FK 
  `random_order`        BIT            NOT NULL,  # 
  `instant_correction`  BIT            NOT NULL,  # is just a way
  `one_mult_page`       BIT            NOT NULL,  # of storing booleans
  `quiz_description`    TEXT           NULL,      # creator supplied

  PRIMARY KEY (`quiz_name`),

  /* Indexing foreign keys */
  /*INDEX `quiz_to_category_idx` (`category_id` ASC), */
  INDEX `quiz_to_creator_idx` (`quiz_creator` ASC),

  /* Adding foreign key constraint to `category_id` and `quiz_creator`. */
/** 
  CONSTRAINT `quiz_to_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `QuizWebsite`.`quiz_categories` (`category_id`),
**/
  CONSTRAINT `quiz_to_creator`
    FOREIGN KEY (`quiz_creator`)
    REFERENCES `QuizWebsite`.`users` (`username`))

ENGINE = InnoDB
COMMENT = 'Stores quiz properties; actual content will be composed by joining other tables.
           `category_id` column implements one-to-many relation between categories and quizzes.
           `quiz_creator_id` column points to username, who is the author of particular quiz,
           implements one-to-many relation between users(authors) and created quzzes';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`quizzes_taken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`quizzes_taken` (

  `quiz_name`        CHARACTER(45)  NOT NULL,  # FK
  `attempt_id`       INT            NOT NULL,
  `username`         CHARACTER(45)  NOT NULL,  # FK
  `percent_correct`  INT            NOT NULL,
  `attempt_date`     DATE           NOT NULL,
  `time_amount_secs` INT            NOT NULL,
  
  PRIMARY KEY (`attempt_id`),
  
  /* Indexing foreign keys */
  INDEX `quiz_idx` (`quiz_name` ASC),
  INDEX `user_idx` (`username` ASC),

  /* Adding foreign key constraints. */ 
  CONSTRAINT `quiz`
    FOREIGN KEY (`quiz_name`)
    REFERENCES `QuizWebsite`.`quizzes` (`quiz_name`),
  CONSTRAINT `user`
    FOREIGN KEY (`username`)
    REFERENCES `QuizWebsite`.`users` (`username`))

ENGINE = InnoDB
COMMENT = 'Implements many-to-many relationship between users and
           quizzes. Also stores score and date of each attempt.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`friends` (

  `first_user_name`      CHARACTER(45)     NOT NULL,  # initiator of friendship
  `second_user_name`     CHARACTER(45)     NOT NULL,  # the one to accept
  `status`               ENUM('0','1')     NOT NULL,  # pending / accepted (by second user)
  `friendship_id`        INT               NOT NULL, 

  PRIMARY KEY (`friendship_id`),

  /* Indexing foreign keys */
  INDEX `fk_first_user_to_users_idx` (`first_user_name` ASC),
  INDEX `fk_second_user_to_users_idx` (`second_user_name` ASC),

  /* Adding foreign key constraints. */ 
  CONSTRAINT `first_user_to_users`
    FOREIGN KEY (`first_user_name`)
    REFERENCES `QuizWebsite`.`users` (`username`),
  CONSTRAINT `second_user_to_users`
    FOREIGN KEY (`second_user_name`)
    REFERENCES `QuizWebsite`.`users` (`username`))

ENGINE = InnoDB
COMMENT = 'Links users to each other implementing \'friends\' feature.
           via many-to-many relation between users on both sides. 
           As an attempt to reduce data redundancy, each pair of users
           is stored only once, with first_user being the one who
           inititated friendship (sent request)';


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
  INDEX `fk_messages_friends_idx` (`friendship_id` ASC),

  /* Adding foreign key constraint*/ 
  CONSTRAINT `fk_messages_friends`
    FOREIGN KEY (`friendship_id`)
    REFERENCES `QuizWebsite`.`friends` (`friendship_id`))

ENGINE = InnoDB
COMMENT = 'Stores messages associated with particular friendship entry.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`badges`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`badges` (

  `badge_id`               INT           NOT NULL,
  `badge_description`      VARCHAR(45)   NOT NULL, # we will insist on 
  `badge_picture_url`      VARCHAR(45)   NOT NULL, # well-formed badges

  PRIMARY KEY (`badge_id`))

ENGINE = InnoDB
COMMENT = 'Stores various badges the users are rewarded with when 
           achieving some milestone.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`users_badges`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`users_badges` (

  `username`      CHARACTER(45)  NOT NULL, # FK points to `users` table
  `badge_id`      INT            NOT NULL, # FK points to `badges` table

  PRIMARY KEY (`username`, `badge_id`), # as usual for many-to-many

  /* Indexing foreign keys */
  INDEX `fk_users_badges_to_user_idx` (`badge_id` ASC),
  INDEX `fk_users_badges_to_badge_idx` (`username` ASC),

  /* Adding foreign key constraints. */ 
  CONSTRAINT `fk_users_badges_to_user`
    FOREIGN KEY (`username`)
    REFERENCES `QuizWebsite`.`users` (`username`),
  CONSTRAINT `fk_users_badges_to_badge`
    FOREIGN KEY (`badge_id`)
    REFERENCES `QuizWebsite`.`badges` (`badge_id`))

ENGINE = InnoDB
COMMENT = 'Implements many-to-many relation between users and badges, allowing
           each user to have various badges, and vice-versa.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`question_response`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`question_response` (

  `problem_id`               INT               NOT NULL AUTO_INCREMENT, 
  `question`                 TEXT              NOT NULL,
  `quiz_name`                CHARACTER(45)     NOT NULL, # FK
  `rel_position`             INT,     # may be null in case of random order

  PRIMARY KEY (`problem_id`),

  /* Indexing foreign keys */
  INDEX `fk_question_response_to_quiz_idx` (`quiz_name` ASC),

  CONSTRAINT `fk_question_response_to_quiz`
    FOREIGN KEY (`quiz_name`)
    REFERENCES `QuizWebsite`.`quizzes` (`quiz_name`)
    ON DELETE CASCADE)

ENGINE = InnoDB
COMMENT = 'Stores problems of type "Queston-response". Each entry
           is associated with particular quiz via `quiz_name` attribute.
           Relationship between quizzes and question_response is `one-to-many`';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`question_response_correct_answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`question_response_correct_answers` (
    
  `problem_id`                   INT    NOT NULL, # FK
  `correct_answer`               TEXT   NOT NULL,

  /* Indexing foreign keys */
  INDEX `fk_correct_answers_to_question_response_idx` (`problem_id` ASC),

  CONSTRAINT `fk_correct_answers_to_question_response`
    FOREIGN KEY (`problem_id`)
    REFERENCES `QuizWebsite`.`question_response` (`problem_id`))

ENGINE = InnoDB
COMMENT = 'Stores correct answers for "Question-response" problems.
           Allows author of the quiz to specify several correct answers.
           Implements one-to-many between `question_responce` and current table';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`fill_in_blank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`fill_in_blank` (

  `problem_id`        INT               NOT NULL AUTO_INCREMENT,
  `question`          TEXT              NOT NULL,
  `quiz_name`         CHARACTER(45)     NOT NULL,
  `field_position`    INT               NOT NULL,
  `rel_position`      INT,     # may be null in case of random order
  

  PRIMARY KEY (`problem_id`),

  /* Indexing foreign keys */
  INDEX `fk_quiz_idx` (`quiz_name` ASC),

  CONSTRAINT `fk_fill_in_blank_problem_to_quiz`
    FOREIGN KEY (`quiz_name`)
    REFERENCES `QuizWebsite`.`quizzes` (`quiz_name`)
    ON DELETE CASCADE)

ENGINE = InnoDB
COMMENT = 'Stores problems of type "Fill in the blank". Each entry
           is associated with particular quiz via `quiz_name` attribute.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`fill_in_blank_correct_answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`fill_in_blank_correct_answers` (

  `problem_id`                    INT    NOT NULL,  # FK
  `correct_answer`                TEXT   NOT NULL,

  /* Indexing foreign keys */
  INDEX `fk_correct_answers_to_fill_in_blank_idx` (`problem_id` ASC),

  CONSTRAINT `fk_correct_answer_to_fill_in_blank`
    FOREIGN KEY (`problem_id`)
    REFERENCES `QuizWebsite`.`fill_in_blank` (`problem_id`))

ENGINE = InnoDB
COMMENT = 'Stores answers for particular "Fill in the blank" question that are considered correct.
            Each entry is associated with particular quiz via `quiz_name` attribute.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`multiple_choise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`multiple_choise` (

  `problem_id`             INT             NOT NULL AUTO_INCREMENT,
  `quiz_name`              CHARACTER(45)   NOT NULL, # FK
  `question`               TEXT            NOT NULL,
  `rel_position`           INT,     # may be null in case of random order

  PRIMARY KEY (`problem_id`),

  /* Indexing foreign keys */
  INDEX `fk_multiple_choise_problem_to_quiz_idx` (`quiz_name` ASC),

  CONSTRAINT `fk_multiple_choise_problem_to_quiz`
    FOREIGN KEY (`quiz_name`)
    REFERENCES `QuizWebsite`.`quizzes` (`quiz_name`)
    ON DELETE CASCADE)

ENGINE = InnoDB
COMMENT = 'Stores problems of type "Multiple choise". Each entry
           is associated with particular quiz via `quiz_name` attribute.
           Relationship between quizzes and `multiple_choise` is one-to-many';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`multiple_choise_answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`multiple_choise_answers` (

  `problem_id`                    INT             NOT NULL, # FK
  `answer`                        TEXT            NOT NULL,
  `is_correct`                    BIT             NOT NULL,

  /* Indexing foreign keys */
  INDEX `fk_answer_to_multiple_choise_problem_idx` (`problem_id` ASC),

  CONSTRAINT `fk_answer_to_multiple_choise_problem`
    FOREIGN KEY (`problem_id`)
    REFERENCES `QuizWebsite`.`multiple_choise` (`problem_id`))

ENGINE = InnoDB
COMMENT = 'Stores answers for particula "Multiple choise" problem.';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`picture_response`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`picture_response` (

  `problem_id`          INT              NOT NULL AUTO_INCREMENT,
  `quiz_name`           CHARACTER(45)    NOT NULL,
  `question`            TEXT             NOT NULL,
  `image_url`           TEXT             NOT NULL,
  `rel_position`        INT,    # may be null in case of random order

  PRIMARY KEY (`problem_id`),

  /* Indexing foreign keys */
  INDEX `fk_picture_response_to_quiz_idx` (`quiz_name` ASC),

  CONSTRAINT `picture_response_to_quiz`
    FOREIGN KEY (`quiz_name`)
    REFERENCES `QuizWebsite`.`quizzes` (`quiz_name`)
    ON DELETE CASCADE)

ENGINE = InnoDB
COMMENT = 'Stores problems of type "Picture-response".';


-- -----------------------------------------------------
-- Table `QuizWebsite`.`picture_response_correct_answers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QuizWebsite`.`picture_response_correct_answers` (

  `problem_id`           INT    NOT NULL,
  `correct_answer`       TEXT   NOT NULL,

  /* Indexing foreign keys */
  INDEX `correct_answer_to_picture_response_idx` (`problem_id` ASC),

  CONSTRAINT `fk_correct_answer_to_picture_response`
    FOREIGN KEY (`problem_id`)
    REFERENCES `QuizWebsite`.`picture_response` (`problem_id`))

ENGINE = InnoDB
COMMENT = 'Stores correct responses for Picture-response problems';



/* Re-enable all the constraints */
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
