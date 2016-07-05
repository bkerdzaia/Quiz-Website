package tests.quiz_tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import quiz.Message;
import quiz.TextMessage;
import quiz.UserMessageList;

public class MessageSortTest {

	@Before
	public void setUp() throws Exception {}

	@Test
	public void test() {
		Timestamp date = new Timestamp(new Date().getTime());
		Message m1 = new TextMessage();
		m1.setDate(date);
		Message m2 = new TextMessage();
		m2.setDate(new Timestamp (date.getTime() + 10000));
		
		UserMessageList list = new UserMessageList();
		list.add(m1);
		list.add(m2);
		
		list.sortByDate();
		assertEquals(2, list.size());
		assertEquals(m2, list.get(0));
		assertEquals(m1,list.get(1));
	}

}
