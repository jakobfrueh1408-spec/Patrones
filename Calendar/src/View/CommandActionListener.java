package View;

import Controller.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommandActionListener implements ActionListener {
    private final Command command;
    public CommandActionListener(Command command) {
        this.command = command;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.command.execute();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
