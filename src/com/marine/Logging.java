package com.marine;

import com.marine.gui.ConsoleWindow;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static java.lang.System.out;

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
        c.write(format(s));
        out.println(format(s));
	}
	
	public void info(String s) {
        c.write(format("INFO", s));
        out.println(format("INFO", s));
    }
	
	public void debug(String s) {
        c.write(format("DEBUG", s));
        out.println(format("DEBUG", s));
    }
	
	public void fatal(String s) {
        c.write(format("FATAL", s));
        out.println(format("FATAL", s));
    }
	
	public void error(String s) {
        c.write(format("ERROR", s));
        out.println(format("ERROR", s));
    }
	
	public void warn(String s) {
        c.write(format("WARNING", s));
        out.println(format("WARNING", s));
    }

    private String format(String prefix, String msg) {
        Date date = new Date();
        calendar.setTime(date);
        return String.format("[%d:%d:%d] [%s] %s",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                prefix,
                msg
        );
    }

    private String format(String msg) {
        Date date = new Date();
        calendar.setTime(date);
        return String.format("[%d:%d:%d] %s",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                msg
        );
    }
}
