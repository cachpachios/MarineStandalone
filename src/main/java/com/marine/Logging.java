///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.marine;

import com.marine.gui.ConsoleWindow;
import com.marine.plugins.PluginLogger;
import com.marine.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Logging class - Used for all
 * logging purposes, even the built
 * in plugin loggers.
 *
 * @author Fozie
 * @author Citymonstret
 */
public class Logging extends PrintStream {

    private static Logging instance;
    private final Calendar calendar = GregorianCalendar.getInstance();
    private final List<String> list;
    private final ConsoleWindow c;
    private boolean haveWindow;

    public Logging(final ConsoleWindow window) {
        super(window);
        list = new ArrayList<>();
        this.haveWindow = false;
        this.c = window;
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

    public void clearLogger() {
        c.clear();
    }

    public boolean isDisplayed() {
        return haveWindow;
    }

    public boolean hasBeenTerminated() {
        return c.isClosed();
    }

    public void log(final PluginLogger.PluginMessage message) {
        log(message.getMessage());
    }

    public void logf(final String s, final Object... os) {
        this.log(StringUtils.format(s, os));
    }

    public void log(final String s) {
        c.write(format('3', s));
        String l = format(s);
        System.out.println(l);
        list.add(l);
    }

    public void info(final String s) {
        c.write(format('9', "INFO", s));
        String l = format("INFO", s);
        System.out.println(l);
        list.add(l);
    }

    public void debug(final String s) {
        c.write(format('1', "DEBUG", s));
        String l = format("DEBUG", s);
        System.out.println(l);
        list.add(l);
    }

    public void fatal(final String s) {
        c.write(format('c', "FATAL", s));
        String l = format("FATAL", s);
        System.out.println(l);
        list.add(l);
    }

    public void error(final String s) {
        c.write(format('c', "ERROR", s));
        String l = format("ERROR", s);
        System.out.println(l);
        list.add(l);
    }

    public void warn(final String s) {
        c.write(format('c', "WARNING", s));
        String l = format("WARNING", s);
        System.out.println(l);
        list.add(l);
    }

    private String format(final char color, final String prefix, final String msg) {
        final Date date = new Date();
        this.calendar.setTime(date);
        return String.format("§%c[%d:%d:%d] [%s] §0%s",
                color,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                prefix,
                msg
        );
    }

    private String format(final String prefix, final String msg) {
        final Date date = new Date();
        this.calendar.setTime(date);
        return String.format("[%d:%d:%s] [%s] %s",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                (calendar.get(Calendar.SECOND) + "").length() == 1 ? ("0" + calendar.get(Calendar.SECOND)) : calendar.get(Calendar.SECOND),
                prefix,
                msg
        ).replaceAll("§([a-z0-9])", "");
    }

    private String format(final String msg) {
        final Date date = new Date();
        this.calendar.setTime(date);
        return String.format("[%d:%d:%d] %s",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                msg
        ).replaceAll("§([a-z0-9])", "");
    }

    private String format(final char color, final String msg) {
        final Date date = new Date();
        this.calendar.setTime(date);
        return String.format("§%c§l[%d:%d:%d] §0%s",
                color,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                msg
        );
    }

    public void saveLog() {
        File file, parent = new File("./log");
        if (!parent.exists() && !parent.mkdir())
            throw new RuntimeException("Could not create the log parent folder");
        try {
            compress(new File(parent, "old.zip"), parent.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".log");
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        file = new File(parent,
                calendar.get(Calendar.YEAR) + "-" +
                        calendar.get(Calendar.MONTH) + "-" +
                        calendar.get(Calendar.WEEK_OF_MONTH) + "-" +
                        calendar.get(Calendar.DAY_OF_WEEK) + "-" +
                        calendar.get(Calendar.HOUR_OF_DAY) + "_" +
                        calendar.get(Calendar.MINUTE) + "_" +
                        calendar.get(Calendar.SECOND) + ".log"
        );
        if (file.exists())
            file.delete();
        try {
            if (!file.createNewFile())
                throw new RuntimeException("Could not create log");
            final PrintWriter writer = new PrintWriter(file);
            for (final String line : list)
                writer.println(line);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void compress(File zipFile, File[] files) throws IOException {
        if (!zipFile.exists()) {
            if (!zipFile.createNewFile()) {
                throw new RuntimeException("Could not create" + zipFile.getPath());
            }
        }
        File tempFile = File.createTempFile(zipFile.getName(), null);
        tempFile.delete();
        if (!zipFile.renameTo(tempFile)) {
            throw new RuntimeException("Could not rename the file " + zipFile.getAbsolutePath() + " to " + tempFile.getAbsolutePath());
        }
        byte[] buf = new byte[1024 * 1024]; // 1mb
        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        ZipEntry entry = zin.getNextEntry();
        while (entry != null) {
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : files) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
            }
            if (notInFiles) {
                out.putNextEntry(new ZipEntry(name));
                int len;
                while ((len = zin.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            entry = zin.getNextEntry();
        }
        zin.close();
        for (int i = 0; i < files.length; i++) {
            InputStream in = new FileInputStream(files[i]);
            out.putNextEntry(new ZipEntry(files[i].getName()));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        for (File file : files)
            file.delete();
        out.close();
        tempFile.delete();
    }

}
