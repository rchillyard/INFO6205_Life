package edu.neu.coe.info6205.life.base;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.neu.coe.info6205.life.library.Library;

public class CircleTest {

	@Test
	public void testCircle1() {
		String pattern = "5 1, 5 2, 5 3, 5 7, 5 8, 5 9, 1 5, 2 5, 3 5, 7 5, 8 5, 9 5";
		Boolean has = Game.hasCircle(pattern);
		assertEquals(true, has);
	}

	@Test
	public void testCircle2() {
		String pattern = "5 1, 5 2, 5 3, 5 7, 5 8, 5 9, 1 5, 2 5, 3 5, 7 5, 8 5";
		Boolean has = Game.hasCircle(pattern);
		assertEquals(true, has);
	}
	
	@Test
	public void testCircle3() {
		String pattern = Library.get("Loaf");;
		Boolean has = Game.hasCircle(pattern);
		assertEquals(true, has);
	}
	
	@Test
	public void testCircle4() {
		String pattern = Library.get("Loaf");;
		Boolean has = Game.hasCircle(pattern);
		assertEquals(true, has);
	}
}
