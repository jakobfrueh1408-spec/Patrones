package View;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JPanel {
    private JComboBox userList;
    public MainMenuView(String[] users) {
        userList = new JComboBox(users);
        setLayout(new BorderLayout());
        add(new JLabel("Current users:"), BorderLayout.NORTH);
        add(userList, BorderLayout.CENTER);
    }
}
