package gameClient.gui;

// imports
import javax.swing.JOptionPane;
import javax.swing.*;

/**
 * this class is the panel window that show the menu before the game is starting for write ID and select a level
 */
public class StartPanel {

    private static ImageIcon icon1 = new ImageIcon("./images/vg_logo3.png");
    private static ImageIcon icon2 = new ImageIcon("./images/empty.png");

    /**
     * getting the id from the user
     * @return
     */
    public static String getId () {
        return (String)JOptionPane.showInputDialog(
                null,
                "Please enter ID number: ",
                "Enter ID",
                JOptionPane.QUESTION_MESSAGE,
                icon1,
                null,
                0
        );
    }

    /**
     * getting the game level by the user selection
     * @return
     */
    public static String getGameLevel () {
        String[] options ={"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        return (String) JOptionPane.showInputDialog(
                null,
                "Please select level: ",
                "Select Level",
                JOptionPane.QUESTION_MESSAGE,
                icon2,
                options,
                options[0]
        );
    }
}