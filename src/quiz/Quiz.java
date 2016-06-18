package quiz;

public class Quiz {

	private String name;
	private String description;
	private User creator;
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
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
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
