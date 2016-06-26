package quiz;

import java.util.Date;

public class Quiz {

	private String name;
	private String description;
	private String creator;
	private Date creationDate;
	private String category;
	private double summaryStatistics;
	private QuizQuestions questions;
	private QuizProperty property;
	
	public Quiz() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setCreationDate(Date date){
		creationDate = date;
	}
	
	public Date getCreationDate(){
		return creationDate;
	}

	public void setCategory(String category){
		this.category = category;
	}
	
	public String getCategory(){
		return category;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public double getSummaryStatistics() {
		return summaryStatistics;
	}

	public void setSummaryStatistics(double summaryStatistics) {
		this.summaryStatistics = summaryStatistics;
	}

	public QuizQuestions getQuestions() {
		return questions;
	}

	public void setQuestions(QuizQuestions questions) {
		this.questions = questions;
	}

	public QuizProperty getProperty() {
		return property;
	}

	public void setProperty(QuizProperty property) {
		this.property = property;
	}
	
}
