package com.marineapi;

public class Logging {
	
	private static Logging instance;
	
	public static Logging getLogger() {
		if(instance == null)
			instance = new Logging();
		return instance;
		
	}
	
	public void log(String s) {
		System.out.println(s);
	}
	
	public void info(String s) {
		System.out.println(s);
	}
	
	public void debug(String s) {
		System.out.println(s);
	}
	
	public void fatal(String s) {
		System.out.println(s);
	}
	
	public void error(String s) {
		System.out.print(s);
	}
	
}
