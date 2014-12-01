package com.marine.game.command;

/**
 * Created 2014-12-01 for MarineStandalone
 *
 * @author Citymonstret
 */
public abstract class Command {

    private final String command;
    private final String[] aliases;
    private final String description;

    public Command(String command, String[] aliases, String description) {
        this.command = command.toLowerCase();
        this.aliases = aliases;
        this.description = description;
    }

    @Override
    public String toString() {
        return command;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public abstract void execute(CommandSender sender, String[] arguments);
}
