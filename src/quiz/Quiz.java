package quiz;

import java.sql.Timestamp;

/**
 * @author dav23r
 * The class encapsulates quiz with all it's properties and 
 * corresponding getters and setter to set and retrieve them.
 */
public class Quiz {

	private String name;
	private String description;
	private String creator;
	private Timestamp creationDate;
	private double summaryStatistics;
	private QuizQuestions questions;
	private QuizProperty property;
	
	/** Empty constructor */
	public Quiz() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setCreationDate(Timestamp date){
		creationDate = date;
	}
	
	public Timestamp getCreationDate(){
		return creationDate;
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
