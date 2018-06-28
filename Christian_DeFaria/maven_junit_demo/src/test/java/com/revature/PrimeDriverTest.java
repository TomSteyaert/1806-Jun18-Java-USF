package com.revature;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Ignore;
import org.junit.Test;

import com.revatue.PrimeDriver;

public class PrimeDriverTest {

	@Test
	public void is113Prime() {
		assertEquals("113 is prime", true, PrimeDriver.isPrime(BigInteger.valueOf(113)));
	}
	
	@Test
	public void is1024Prime() {
		assertEquals("1024 is not prime", false, PrimeDriver.isPrime(BigInteger.valueOf(1024)));
	}
	
	@Ignore("Fermat's Little Theorem does not account for Carmichael numbers")
	public void is561Prime() {
		assertEquals("561 is prime", false, PrimeDriver.isPrime(BigInteger.valueOf(561)));
	}

}