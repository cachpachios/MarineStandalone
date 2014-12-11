package com.marine.gui;

import com.marine.Logging;
import com.marine.ServerProperties;
import com.marine.game.chat.ChatColor;
import com.marine.server.Marine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ConsoleWindow extends OutputStream {
    private final int maxLines;
    private final boolean showHTML;
    private JFrame jFrame;
    private JTextPane text;
    @SuppressWarnings("unused")
    private JTextPane input;
    private List<String> console;
    private String s;
    private java.util.List<Character> validChars;

    public ConsoleWindow(int maxLines) {
        this.maxLines = maxLines;
        console = new ArrayList<String>();
        this.showHTML = false;
        text = new JTextPane();
        input = new JTextPane();
    }

    public void initWindow() {
        jFrame = new JFrame();
        jFrame.setTitle("MarineStandalone " +
                ServerProperties.BUILD_VERSION +
                " " + ServerProperties.BUILD_TYPE +
                " (" + ServerProperties.BUILD_NAME + ")");
        jFrame.setSize(600, 400);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setIconImage(new ImageIcon("./res/icon.png").getImage());

        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to close the server?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Marine.stop();
                }
            }
        };

        jFrame.addWindowListener(exitListener);

        GridBagConstraints c = new GridBagConstraints();

        jFrame.setLayout(new GridBagLayout());

        if (!showHTML)
            text.setContentType("text/html");
        text.setEditable(false);
        text.setBackground(Color.BLACK);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1.5D;
        c.weighty = 1;

        c.insets = new Insets(1, 1, 1, 1);
        jFrame.add(new JScrollPane(text), c);

        jFrame.setVisible(true);
    }

    @Override
    public void write(byte[] s) {
        this.write(new String(s));
    }

    public void write(String s) {
        console.add(format(s));
        update();

    }

    private String format(String string) {
        string = string.replace("§0", "§f");
        string = "<font face='MarineStandalone'>" + string;
        for (ChatColor color : ChatColor.values()) {
            if (color.isColor()) {
                string = string.replace("§" + color.getOldSystemID(), "</b></u></i></s><font color='#" + color.getHexa() + "'>");
            }
        }
        string = string.replace("§l", "<b></u></i></s>");
        string = string.replace("§o", "</b></u><i></s>");
        string = string.replace("§n", "</b><u></i></s>");
        string = string.replace("§s", "</b></u></i><s>");
        return string + "</font>";
    }

    public void update() { synchronized(console) {
    	while(console.size() > maxLines)
        	console.remove(0);
        String sB = "";
        for (String s : console) {
            sB += s;
            sB += "<br>";
        }
        text.setText(sB);
    }}

    public boolean isClosed() {
        return !jFrame.isVisible();
    }

    @Override
    public void write(int b) throws IOException {
        if (validChars == null) {
            validChars = new ArrayList<>();
            for (char c : " \t\\\r/abcdefghjiklmnopqrstuvwxyzABCDEFGHJIKLMNOPQRSTUVWXYZ0123456789._,:;[](){}><-+\"$@#£€&=".toCharArray()) {
                validChars.add(c);
            }
        }
        char c = (char) b;
        if (c == '\n') {
            //write(s);
            Logging.getLogger().error(s);
            s = "";
        } else if (validChars.contains(c)) {
            s += c;
        } else {
            s += "Unknown character: [" + c + "]";
        }
        s = s.replaceAll("<", "&lt;");
        s = s.replaceAll(">", "&gt;");
        s = s.replaceAll("€", "&euro;");
        s = s.replaceAll("£", "&pound;");
    }
}
