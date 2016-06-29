# ---------------------------- Be cautious !!! -----------------------
# Script drops all the tables in Quiz_Website database. All the information
# will be permanently lost. 

USE QuizWebsite;

# Get rid of various constraints.
SET UNIQUE_CHECKS=0;
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `quizzes`;
DROP TABLE IF EXISTS `quizzes_taken`;
DROP TABLE IF EXISTS `friends`;
DROP TABLE IF EXISTS `messages`;
DROP TABLE IF EXISTS `badges`;
DROP TABLE IF EXISTS `users_badges`;
DROP TABLE IF EXISTS `question_response`;
DROP TABLE IF EXISTS `question_response_correct_answers`;
DROP TABLE IF EXISTS `fill_in_blank`;
DROP TABLE IF EXISTS `fill_in_blank_correct_answers`;
DROP TABLE IF EXISTS `multiple_choise`;
DROP TABLE IF EXISTS `multiple_choise_answers`;
DROP TABLE IF EXISTS `picture_response`;
DROP TABLE IF EXISTS `picture_response_correct_answers`;
DROP TABLE IF EXISTS `friend_requests`;

# Restore configuration.
SET UNIQUE_CHECKS=1;
SET FOREIGN_KEY_CHECKS=1;



