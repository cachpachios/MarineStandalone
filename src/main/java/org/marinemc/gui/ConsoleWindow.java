///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
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

package org.marinemc.gui;

import org.marinemc.Bootstrap;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.logging.Logging;
import org.marinemc.server.Marine;
import org.marinemc.server.ServerProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ConsoleWindow extends OutputStream {
	private final int maxLines;
	private final boolean showHTML;
	private final List<String> console;
	private final JTextPane text;
	private final JTextField input;
	private JFrame jFrame;
	private String s;
	private java.util.List<Character> validChars;

	public ConsoleWindow(final int maxLines) {
		this.maxLines = maxLines;
		console = new ArrayList<String>();
		showHTML = false;
		text = new JTextPane();
		input = new JTextField();
	}

	private void requestClose() {
		final int confirm = JOptionPane.showOptionDialog(jFrame,
				"Are you sure you want to close the server?",
				"Exit Confirmation", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (confirm == 0)
			Marine.stop();
	}

	public void initWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Throwable e) {
			System.out.print("[Error] Could not set the GUI look and feel!");
			e.printStackTrace();
		}
		jFrame = new JFrame();
		jFrame.setTitle("MarineStandalone " + ServerProperties.BUILD_VERSION
				+ " " + ServerProperties.BUILD_TYPE + " ("
				+ ServerProperties.BUILD_NAME + ")");
		jFrame.setSize(600, 400);
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jFrame.setIconImage(new ImageIcon("./res/icon.png").getImage());
		// This is the closing listener, unfortunately
		// it seems to cause a lot of CPU cycles and stuff
		final WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				requestClose();
			}
		};
		// The listener
		jFrame.addWindowListener(exitListener);
		// The layout stuffz
		final GridBagConstraints c = new GridBagConstraints();
		jFrame.setLayout(new GridBagLayout());
		// Should we display the html outputted?
		if (!showHTML)
			text.setContentType("text/html");
		// This ain't editable
		input.setEditable(true);
		input.setBackground(Color.BLACK);
		input.setForeground(Color.WHITE);
		input.setCaretColor(Color.WHITE);
		input.setBorder(new EmptyBorder(0, 0, 0, 0));
		text.setEditable(false);
		text.setBorder(new EmptyBorder(0, 0, 0, 0));
		// Set background
		text.setBackground(Color.BLACK);
		// Grid layout
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1.5D;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0, 0);
		jFrame.add(new JScrollPane(text) {
			{
				setBorder(new EmptyBorder(0, 0, 0, 0));
			}
		}, c);
		c.gridy = 1;
		c.weighty = .035;
		jFrame.add(input, c);

		final CommandHistory history = new CommandHistory();
		input.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					input.setText(history.getNext());
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					input.setText(history.getPrevious(input.getText()));
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String text = input.getText();
					if (!text.startsWith("/")) {
						text = "/" + text;
					}
					if (text.length() < 1) {
						return;
					}
					String[] args, pure;
					String command;
					pure = text.split(" ");
					command = pure[0];
					if (pure.length < 2) {
						args = new String[0];
					} else {
						List<String> sar = new ArrayList<String>();
						for (int x = 1; x < pure.length; x++) {
							if (pure[x] != null)
								sar.add(pure[x]);
						}
						args = sar.toArray(new String[sar.size()]);
					}
					history.add(input.getText());
					history.reset();
					input.setText("");
					Marine.getServer().getConsoleSender().executeCommand(command, args);
				} else if (e.getKeyCode() == KeyEvent.VK_TAB) {
					Logging.getLogger().log("Tab Completion Isn't Implemented Yet :(");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		// Set visible, yay
		/*final JMenuBar menuBar = new JMenuBar();
		final JMenu actions = new JMenu("actions"), tools = new JMenu("tools"), help = new JMenu(
				"help");
		actions.setText("Actions");
		tools.setText("Tools");
		help.setText("Help");
		final JMenuItem authors = new JMenuItem("Authors"), commands = new JMenuItem(
				new AbstractAction("Commands") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						final JFrame commands = new JFrame("Commands");
						commands.setSize(600, 400);
						commands.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						final String[] columns = new String[] { "Command",
								"Aliases", "Description", };
						final List<Command> cmds = new ArrayList<>(
								CommandManager.getInstance().getCommands());
						final Object[][] objects = new Object[cmds.size()][];
						Command c;
						for (int x = 0; x < cmds.size(); x++) {
							c = cmds.get(x);
							objects[x] = new Object[] {
									c.toString(),
									StringUtils.join(
											Arrays.asList(c.getAliases()), ", "),
									c.getDescription() };
						}
						final DefaultTableModel model = new DefaultTableModel(
								objects, columns) {
							@Override
							public boolean isCellEditable(final int r,
									final int c) {
								return false;
							}
						};
						final JTable table = new JTable(model);
						table.setFillsViewportHeight(true);
						commands.add(new JScrollPane(table));
						commands.setVisible(true);
					}
				}), restart = new JMenuItem(new AbstractAction("Restart") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Logging.getLogger().error("Restart Disabled");
			}
		}), kick_all = new JMenuItem(new AbstractAction("Kick All") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final String s = JOptionPane.showInputDialog(jFrame,
						"Enter a kick message", "Kick Message",
						JOptionPane.QUESTION_MESSAGE);
				// for (Player player : Marine.getPlayers()) TODO
				// player.kick(ChatColor.transform('&', s));
			}
		});
		authors.setEnabled(true);
		commands.setEnabled(true);
		restart.setEnabled(true);
		kick_all.setEnabled(true);
		help.add(authors);
		help.add(commands);
		actions.add(restart);
		actions.add(kick_all);
		menuBar.add(actions);
		menuBar.add(tools);
		menuBar.add(help);
		jFrame.setJMenuBar(menuBar);*/

		jFrame.setVisible(true);
	}

	@Override
	public void write(final byte[] s) {
		this.write(new String(s));
	}

	public void write(final String s) {
		synchronized (console) {
			console.add(format(s));
			update();
		}
	}

	public void clear() {
		console.clear();
	}

	private String format(String string) {
		string = string.replace("\n", "<br>").replace("\t", "   ");
		if (!Bootstrap.instance().arguments.contains("nocolors")) {
			string = string.replace("§0", "§f");
			string = "<font face='MarineStandalone'>" + string;
			for (final ChatColor color : ChatColor.values())
				if (color.isColor())
					string = string.replace("§" + color.getOldSystemID(),
							"</b></u></i></s><font color='#" + color.getHexa()
									+ "'>");
			string = string.replace("§l", "<b></u></i></s>");
			string = string.replace("§o", "</b></u><i></s>");
			string = string.replace("§n", "</b><u></i></s>");
			string = string.replace("§s", "</b></u></i><s>");
			string = string.replace("§r", "</b></u></i></s><font face='MarineStandalone' color='#FFFFFF'>");
			return string + "</font>";
		}
		return "<font face='MarineStandalone' color='#FFFFFF'>"
				+ string.replaceAll("§([a-z0-9])", "") + "</font>";
	}

	public void update() {
		synchronized (console) {
			while (console.size() > maxLines)
				console.remove(0);
			final StringBuilder sB = new StringBuilder();
			for (final String s : console) {
				sB.append(s);
				sB.append("<br>");
			}
			text.setText(sB.toString());
			text.setCaretPosition(text.getDocument().getLength());
		}
	}

	public boolean isClosed() {
		return !jFrame.isVisible();
	}

	@Override
	public void write(final int b) throws IOException {
		if (validChars == null) {
			validChars = new ArrayList<>();
			for (final char c : " \t\\\r/abcdefghjiklmnopqrstuvwxyzABCDEFGHJIKLMNOPQRSTUVWXYZ0123456789._,:;[](){}><-+\"$@#£€&="
					.toCharArray())
				validChars.add(c);
		}
		final char c = (char) b;
		if (c == '\n') {
			// write(s);
			Logging.getLogger().error(s);
			s = "";
		} else if (validChars.contains(c))
			s += c;
		else
			s += "Unknown character: [" + c + "]";
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll(">", "&gt;");
		s = s.replaceAll("€", "&euro;");
		s = s.replaceAll("£", "&pound;");
	}

	private class CommandHistory {
		private int current = -1;
		private List<String> history = new ArrayList<>();

		public String getPrevious(String s) {
			if (s.length() > 0 && !history.get(history.size() - 1).equals(s)) {
				history.add(s);
			}
			if (current == -1) {
				current = history.size();
			}
			if (current < 1) {
				current = 1;
			}
			return history.get(--current);
		}

		public String getNext() {
			if (history.isEmpty()) {
				return "";
			}
			if (++current >= history.size()) {
				current = history.size() - 1;
			}
			return history.get(current);
		}

		public void reset() {
			current = -1;
		}

		public void add(String s) {
			if (!history.contains(s)) {
				history.add(s);
			}
		}
	}
}
