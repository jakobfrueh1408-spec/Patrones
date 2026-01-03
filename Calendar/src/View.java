import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private Controller controller;
    public View(Controller controller) {
        this.controller = controller;
        this.Initialize();
    }
    public void Initialize(){
        JFrame frame = new JFrame();
        frame.setTitle("Calendar");
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(100, 100, 100, 50);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.onExitClicked();
            }
        });
        frame.add(exitButton);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
