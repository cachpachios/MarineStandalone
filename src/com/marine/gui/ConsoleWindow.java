package com.marine.gui;

import com.marine.ServerProperties;
import com.marine.game.chat.ChatColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConsoleWindow { // Simple console window :)
	private JFrame jFrame;
	
	private JTextPane text;
	private JTextPane input;

	private ArrayList<String> console;
	
	private final int maxLines;

    private final boolean showHTML;

	public ConsoleWindow(int maxLines) {
		this.maxLines = maxLines;
		console = new ArrayList<>();
        this.showHTML = false;
	}
	
	public void initWindow() {
		jFrame = new JFrame();
		jFrame.setTitle("MarineStandalone " + ServerProperties.BUILD_VERSION + " " + ServerProperties.BUILD_TYPE + " (" + ServerProperties.BUILD_NAME + ")");
		jFrame.setSize(600, 400);
		
		GridBagConstraints c = new GridBagConstraints();
		
		jFrame.setLayout(new GridBagLayout());
		
		text = new JTextPane();
        input = new JTextPane();

        if(!showHTML)
            text.setContentType("text/html");
		text.setEditable(false);
        text.setBackground(Color.BLACK);
		text.setBackground(Color.BLACK);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1.5D;
		c.weighty = 1;
		
		c.insets = new Insets(1, 1, 1, 1);
		jFrame.add(text, c);

		jFrame.setVisible(true);
	}
	
	public void write(String s) {
		console.add(format(s));
		update();
	}

    private String format(String string) {
        string = string.replace("§0", "§f");
        string = "<font face='MarineStandalone'>" + string;
        for(ChatColor color : ChatColor.values()) {
            if(color.isColor()) {
                string = string.replace("§" + color.getOldSystemID(), "</b></u></i></s><font color='#" + color.getHexa() + "'>");
            }
        }
        string = string.replace("§l", "<b></u></i></s>");
        string = string.replace("§o", "</b></u><i></s>");
        string = string.replace("§n", "</b><u></i></s>");
        string = string.replace("§s", "</b></u></i><s>");
        return string + "</font>";
    }

	
	public void update() { // TODO remove old lines.
		if(console.size() > maxLines)
			console.remove(0);
		String str = "";
		for(String s : console) {
			str += s;
			str += "<br>";
		}
		
		text.setText(str);
	}
	
	public boolean isClosed() {
		return !jFrame.isVisible();
	}
	
}
