package View;

import Controller.Command;

import javax.swing.*;

public class SmartButton extends JButton {
    private Command command;
    public SmartButton(Command command, String text) {
        super(text);
        this.command = command;
    }
    public SmartButton(String text) {
        super(text);
    }

    public Command getCommand() {
        return command;
    }
}
