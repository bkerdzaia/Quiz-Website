package application;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Func {

	public void k() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(3);
		a.add(5);
		a.add(6);
		Stream<Integer> st = a.stream();
		Stream<Integer> x = st.map(i -> i+1);
		x.forEach(i -> System.out.println("Map: " + i));
		for(Integer i : a) {
			System.out.println("list: " + i);
		}
		st.close();
		
		
	}
	
	public static boolean mod3(int i) {
		return i % 3 == 0;
	}
	
	public static void main(String[] args) {
		int x = IntStream.range(1, 10).filter(Func::mod3).findFirst().getAsInt();
		System.out.println(x);
		
		List<Integer> vals = Arrays.asList(1, 2, 3, 4, 5);
		
		System.out.println(vals.stream().reduce(0, Math::addExact));
	}
	
}
