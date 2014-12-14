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

import org.marinemc.Logging;
import org.marinemc.MainComponent;
import org.marinemc.ServerProperties;
import org.marinemc.game.CommandManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.player.Player;
import org.marinemc.server.Marine;
import org.marinemc.util.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
        // This is the closing listener, unfortunately
        // it seems to cause a lot of CPU cycles and stuff
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(jFrame, "Are you sure you want to close the server?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Marine.stop();
                }
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
        text.setEditable(false);
        // Set background
        text.setBackground(Color.BLACK);
        // Grid layout
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1.5D;
        c.weighty = 1;
        c.insets = new Insets(1, 1, 1, 1);
        jFrame.add(new JScrollPane(text), c);
        // Set visible, yay
        final JMenuBar menuBar = new JMenuBar();
        final JMenu
                actions = new JMenu("actions"),
                tools = new JMenu("tools"),
                help = new JMenu("help");
        actions.setText("Actions");
        tools.setText("Tools");
        help.setText("Help");
        final JMenuItem
                authors = new JMenuItem("Authors"),
                commands = new JMenuItem(new AbstractAction("Commands") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame commands = new JFrame("Commands");
                        commands.setSize(600, 400);
                        commands.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        String[] columns = new String[]{
                                "Command",
                                "Aliases",
                                "Description",
                        };
                        List<Command> cmds = new ArrayList<>(CommandManager.getInstance().getCommands());
                        Object[][] objects = new Object[cmds.size()][];
                        Command c;
                        for (int x = 0; x < cmds.size(); x++) {
                            c = cmds.get(x);
                            objects[x] = new Object[]{
                                    c.toString(),
                                    StringUtils.join(Arrays.asList(c.getAliases()), ", "),
                                    c.getDescription()
                            };
                        }
                        DefaultTableModel model = new DefaultTableModel(objects, columns) {
                            @Override
                            public boolean isCellEditable(int r, int c) {
                                return false;
                            }
                        };
                        JTable table = new JTable(model);
                        table.setFillsViewportHeight(true);
                        commands.add(new JScrollPane(table));
                        commands.setVisible(true);
                    }
                }),
                restart = new JMenuItem(new AbstractAction("Restart") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Marine.getServer().getServer().restart();
                    }
                }),
                kick_all = new JMenuItem(new AbstractAction("Kick All") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = JOptionPane.showInputDialog(jFrame, "Enter a kick message", "Kick Message", JOptionPane.QUESTION_MESSAGE);
                        for (Player player : Marine.getPlayers())
                            player.kick(ChatColor.transform('&', s));
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
        jFrame.setJMenuBar(menuBar);
        jFrame.setVisible(true);
    }

    @Override
    public void write(byte[] s) {
        this.write(new String(s));
    }

    public void write(String s) {
        synchronized (console) {
            console.add(format(s));
            update();
        }
    }

    public void clear() {
        console.clear();
    }

    private String format(String string) {
        if (!MainComponent.arguments.contains("no-colors")) {
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
        return "<font face='MarineStandalone' color='#FFFFFF'>" + string.replaceAll("§([a-z0-9])", "") + "</font>";
    }

    public void update() {
        synchronized (console) {
            while (console.size() > maxLines)
                console.remove(0);
            String sB = "";
            for (String s : console) {
                sB += s;
                sB += "<br>";
            }
            text.setText(sB);
        }
    }

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
