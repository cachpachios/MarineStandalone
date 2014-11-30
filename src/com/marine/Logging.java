package com.marine;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.marine.gui.ConsoleWindow;

public class Logging {
	
	private static Logging instance;

	private boolean haveWindow;
	
	Calendar calendar = GregorianCalendar.getInstance();
	
	public Logging() {
		haveWindow = false;
		c = new ConsoleWindow(50);
	}
	
	public void createConsoleWindow() {
		c.initWindow();
		haveWindow = true;
	}
	
	public boolean isDisplayed() {
		return haveWindow;
	}
	
	private ConsoleWindow c;
	
	public static Logging getLogger() {
		if(instance == null)
			instance = new Logging();
		return instance;
	}
	
	public boolean hasBeenTerminated() {
		return c.isClosed();
	}
	
	public void log(String s) {
		Date date = new Date();
		calendar.setTime(date);
		c.write("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] " + s);
		System.out.println("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] " + s);
	}
	
	public void info(String s) {
		Date date = new Date();
		calendar.setTime(date);
		c.write("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [INFO] " + s);

		System.out.println("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [INFO] " + s);
	}
	
	public void debug(String s) {
		Date date = new Date();
		calendar.setTime(date);
		c.write("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [DEBUG] " + s);

		System.out.println("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [DEBUG] " + s);
	}
	
	public void fatal(String s) {
		Date date = new Date();
		calendar.setTime(date);
		c.write("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [FATAL] " + s);

		System.out.println("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [FATAL] " + s);
	}
	
	public void error(String s) {
		Date date = new Date();
		calendar.setTime(date);
		c.write("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [ERROR] " + s);

		System.out.println("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [ERROR] " + s);
	}
	
	public void warn(String s) {
		Date date = new Date();
		calendar.setTime(date);
		c.write("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [WARNING] " + s);

		System.out.println("["+calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND) + "] [WARNING] " + s);
	}
	
}
