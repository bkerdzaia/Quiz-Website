# ---------------------------- Be cautious !!! -----------------------
# Script drops all the tables in Quiz_Website database. All the information
# will be permanently lost. 

USE DATABASE QuizWebsite;

# Get rid of various constraints.
SET UNIQUE_CHECKS=0;
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `quiz_categories`;
DROP TABLE IF EXISTS `quizzes`;
DROP TABLE IF EXISTS `quizzes_taken`;
DROP TABLE IF EXISTS `friends`;
DROP TABLE IF EXISTS `messages`;
DROP TABLE IF EXISTS `badges`;
DROP TABLE IF EXISTS `users_badges`;

# Restore configuration.
SET UNIQUE_CHECKS=1;
SET FOREIGN_KEY_CHECKS=1;



