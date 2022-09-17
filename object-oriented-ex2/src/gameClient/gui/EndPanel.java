package gameClient.gui;

import javax.swing.*;

/**
 * this class is the end panel that show the game results after the game has ended
 */
public class EndPanel {
    private JFrame panel;
    private ImageIcon icon = new ImageIcon("./images/end.png");
    public void showResults(int level, int moves, int grade) {
        panel = new JFrame();
        JOptionPane.showMessageDialog(panel,
                "Results:\nLevel: " + level + "\nNumber of moves: " + moves + "\nGrade: " + grade,
                "Results",
                JOptionPane.INFORMATION_MESSAGE,
                icon);
    }
}