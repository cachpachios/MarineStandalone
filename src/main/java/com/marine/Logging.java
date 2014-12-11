///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
//     Copyright (C) IntellectualSites (marine.intellectualsites.com)
//
//     This program is free software; you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation; either version 2 of the License, or
//     (at your option) any later version.
//
//     This program is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.
//
//     You should have received a copy of the GNU General Public License along
//     with this program; if not, write to the Free Software Foundation, Inc.,
//     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.marine;

import com.marine.gui.ConsoleWindow;
import com.marine.plugins.PluginLogger;
import com.marine.util.StringUtils;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Logging extends PrintStream {

    private static Logging instance;
    Calendar calendar = GregorianCalendar.getInstance();
    private boolean haveWindow;
    private ConsoleWindow c;

    public Logging(ConsoleWindow window) {
        super(window);
        haveWindow = false;
        c = window;
    }

    public static Logging getLogger() {
        if (instance == null) {
            ConsoleWindow window = new ConsoleWindow(25);
            instance = new Logging(window);
        }
        return instance;
    }

    public static Logging instance() {
        return getLogger();
    }

    public void createConsoleWindow() {
        c.initWindow();
        haveWindow = true;
    }

    public boolean isDisplayed() {
        return haveWindow;
    }

    public boolean hasBeenTerminated() {
        return c.isClosed();
    }

    public void log(PluginLogger.PluginMessage message) {
        log(message.getMessage());
    }

    public void logf(String s, Object... os) {
        this.log(StringUtils.format(s, os));
    }

    public void log(String s) {
        c.write(format('3', s));
        System.out.println(format(s));
    }

    public void info(String s) {
        c.write(format('9', "INFO", s));
        System.out.println(format("INFO", s));
    }

    public void debug(String s) {
        c.write(format('1', "DEBUG", s));
        System.out.println(format("DEBUG", s));
    }

    public void fatal(String s) {
        c.write(format('c', "FATAL", s));
        System.out.println(format("FATAL", s));
    }

    public void error(String s) {
        c.write(format('c', "ERROR", s));
        System.out.println(format("ERROR", s));
    }

    public void warn(String s) {
        c.write(format('c', "WARNING", s));
        System.out.println(format("WARNING", s));
    }

    private String format(char color, String prefix, String msg) {
        Date date = new Date();
        calendar.setTime(date);
        return String.format("§%c[%d:%d:%d] [%s] §0%s",
                color,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                prefix,
                msg
        );
    }

    private String format(String prefix, String msg) {
        Date date = new Date();
        calendar.setTime(date);
        return String.format("[%d:%d:%s] [%s] %s",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                (calendar.get(Calendar.SECOND) + "").length() == 1 ? ("0" + calendar.get(Calendar.SECOND)) : calendar.get(Calendar.SECOND),
                prefix,
                msg
        ).replaceAll("§([a-z0-9])", "");
    }

    private String format(String msg) {
        Date date = new Date();
        calendar.setTime(date);
        return String.format("[%d:%d:%d] %s",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                msg
        ).replaceAll("§([a-z0-9])", "");
    }

    private String format(char color, String msg) {
        Date date = new Date();
        calendar.setTime(date);
        return String.format("§%c§l[%d:%d:%d] §0%s",
                color,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                msg
        );
    }
}
