 package ui;

import javax.swing.*;
import java.awt.*;

    public class MainFrame extends JFrame {

        public MainFrame() {

            setTitle("Todo List Reminder");
            setSize(900,500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setLayout(new BorderLayout());

            JLabel label = new JLabel("Todo List", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 24));

            add(label, BorderLayout.NORTH);

            setVisible(true);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(MainFrame::new);
        }
    }

