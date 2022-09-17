package gameClient.gui;

// imports
import gameClient.Arena;
import javax.swing.*;
import java.awt.*;

/**
 * This class represents a very simple GUI
 */
public class MyFrame extends JFrame {

	private Arena arena;
	private GamePanel gamePanel;

	// constructor
	public MyFrame(String a) {
		super(a);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * initialize panel
	 */
	public void initPanel() {
		this.gamePanel = new GamePanel(arena.getGraph(), this.arena);
		this.add(this.gamePanel);
	}

	/**
	 * update arena and game panel
	 * @param arena
	 */
	public void update(Arena arena) {
		this.arena = arena;
		initPanel();
		this.gamePanel.updatePanel();
	}

	/**
	 * paint arena and game panel
	 * @param graph
	 */
	public void paint(Graphics graph) {
		this.gamePanel.updatePanel();
		this.gamePanel.repaint();
	}
}