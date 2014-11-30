package com.marine.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.marine.ServerProperties;

public class ConsoleWindow { // Simple console window :)
	private JFrame jFrame;
	
	private JTextArea text;
	
	private ArrayList<String> console;
	
	private final int maxLines;
	
	public ConsoleWindow(int maxLines) {
		this.maxLines = maxLines;
		console = new ArrayList<String>();
	}
	
	public void initWindow() {
		jFrame = new JFrame();
		jFrame.setTitle("MarineStandalone " + ServerProperties.BUILD_VERSION + " " + ServerProperties.BUILD_TYPE + " (" + ServerProperties.BUILD_NAME + ")");
		jFrame.setSize(600, 400);
		
		GridBagConstraints c = new GridBagConstraints();
		
		jFrame.setLayout(new GridBagLayout());
		
		text = new JTextArea();
		text.setEditable(false);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1.5D;
		c.weighty = 1;
		
		c.insets = new Insets(1, 1, 1, 1);
		jFrame.add(text,c);
		
		jFrame.setVisible(true);
	}
	
	public void write(String s) {
		console.add(s);
		update();
	}
	
	public void update() { // TODO remove old lines.
		if(console.size() > maxLines)
			console.remove(0);
		String str = "";
		for(String s : console) {
			str += s;
			str += "\n";
		}
		
		text.setText(str);
	}
	
	public boolean isClosed() {
		return !jFrame.isVisible();
	}
	
}
