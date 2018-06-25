package com.revature;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestDriverTest {
	
	//not main for test
	//assertEquals() tests that two values are the same.
	//NOTE: arrjays/objects the reference is checked not the content or states.
	@Test
	public void testMultiply() {
		TestDriver tester = new TestDriver();
		assertEquals("10 * 5 must equal 50", 50, tester.multiply(10, 5));
		
	}
	
	@Test
	public void fakeTest() {
		return;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExceptionIsThrown() {
		TestDriver tester = new TestDriver();
		tester.
	}
}
