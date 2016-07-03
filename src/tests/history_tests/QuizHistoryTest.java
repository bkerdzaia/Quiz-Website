package tests.history_tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import factory.DefaultQuizFactory;
import factory.DefaultUserFactory;
import quiz.PerformanceOnQuiz;
import quiz.QuizHistory;

public class QuizHistoryTest {

	private QuizHistory history;

	private static final Comparator<PerformanceOnQuiz> QUIZ_PERFORMANCE_COMPARATOR =  new Comparator<PerformanceOnQuiz>() {

		@Override
		public int compare(PerformanceOnQuiz p1, PerformanceOnQuiz p2) {
			int cmpUser = p1.getUser().compareTo(p2.getUser());
			if (cmpUser != 0) return cmpUser;
			int cmpAmount = Integer.compare(p1.getAmountTime(), p2.getAmountTime());
			if (cmpAmount != 0) return cmpAmount;
			int cmpPercent = Double.compare(p1.getPercentCorrect(), p2.getPercentCorrect());
			if (cmpPercent != 0) return cmpPercent;
			return p1.getDate().compareTo(p2.getDate());
		}
	};
	
	/* returns the PerformanceOnQuiz of given arguments */
	private PerformanceOnQuiz getPerformance(int amountTimeSecs, Timestamp date, double percentCorrect,
			String userName) {
		PerformanceOnQuiz performance = DefaultQuizFactory.getFactoryInstance().getPerformanceOnQuiz();
		performance.setAmountTime(amountTimeSecs);
		performance.setDate(date);
		performance.setPercentCorrect(percentCorrect);
		performance.setUser(userName);
		return performance;
	}

	/* fill quiz history by some values */
	@Before
	public void setUp() throws Exception {
		history = DefaultUserFactory.getFactoryInstance().getHistoryPerformanceOnQuiz();
		history.add(getPerformance(60, new Timestamp(0), 34, "user1"));
		history.add(getPerformance(90, new Timestamp(1000), 31, "user1"));
		history.add(getPerformance(80, new Timestamp(1000), 36, "user2"));
		history.add(getPerformance(110, new Timestamp(0), 33, "user1"));
		history.add(getPerformance(150, new Timestamp(0), 10, "user2"));
		history.add(getPerformance(343, new Timestamp(4), 12, "user3"));
		history.add(getPerformance(120, new Timestamp(0), 77, "user1"));
		history.add(getPerformance(55, new Timestamp(0), 10, "user2"));
		history.add(getPerformance(34, new Timestamp(0), 45, "user1"));
	}

	/* checks equality for two arrays of PerformanceOnQuiz comparing their values by compare function */
	private boolean equals(PerformanceOnQuiz[] one, PerformanceOnQuiz[] sec, Comparator<PerformanceOnQuiz> compare) {
		if (one.length != sec.length) return false;
		for (int i=0; i<one.length; i++) {
			if (compare.compare(one[i], sec[i]) != 0) return false;
		}
		return true;
	}
	

	/* test for sorted by user1 correctness */
	@Test
	public void testSortingForUser1() {
		ArrayList<PerformanceOnQuiz> sortedHist = (ArrayList<PerformanceOnQuiz>) history.sortByUser("user1",
				DefaultQuizFactory.getFactoryInstance().getOrderByPercentCorrectInstance());
		PerformanceOnQuiz[] sortedPerformance = new PerformanceOnQuiz[sortedHist.size()];
		sortedPerformance = sortedHist.toArray(sortedPerformance);
		PerformanceOnQuiz[] realPerformance = { 
			getPerformance(90, new Timestamp(1000), 31, "user1"),
			getPerformance(110, new Timestamp(0), 33, "user1"), 
			getPerformance(60, new Timestamp(0), 34, "user1"),
			getPerformance(34, new Timestamp(0), 45, "user1"),
			getPerformance(120, new Timestamp(0), 77, "user1")
		};
		assertTrue(equals(realPerformance, sortedPerformance, QUIZ_PERFORMANCE_COMPARATOR));
	}
	
	/* test for sorted by user2 correctness */
	@Test
	public void testSortingForUser2() {
		ArrayList<PerformanceOnQuiz> sortedHist = (ArrayList<PerformanceOnQuiz>) history.sortByUser("user2",
				DefaultQuizFactory.getFactoryInstance().getOrderByPercentCorrectInstance());
		PerformanceOnQuiz[] sortedPerformance = new PerformanceOnQuiz[sortedHist.size()];
		sortedPerformance = sortedHist.toArray(sortedPerformance);
		PerformanceOnQuiz[] realPerformance = { 
				getPerformance(150, new Timestamp(0), 10, "user2"),
				getPerformance(55, new Timestamp(0), 10, "user2"),
				getPerformance(80, new Timestamp(1000), 36, "user2")
				
		};
		assertTrue(equals(realPerformance, sortedPerformance, QUIZ_PERFORMANCE_COMPARATOR));
	}

}
