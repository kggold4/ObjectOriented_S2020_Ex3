package gameClient.gui;

// imports
import api.*;
import gameClient.Arena;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * this class is the game panel control that show everything in the window while the game is running
 */
public class GamePanel extends JPanel {

    private Graphics2D G2D;
    private Image agent, pokaball1, pokaball2, background;
    private Arena arena;
    private directed_weighted_graph graph;
    private gameClient.util.Range2Range W2F;
    private static String FONT = "Arial";

    // constructor
    public GamePanel(directed_weighted_graph g, Arena a) {
        super();
        this.arena = a;
        this.graph = g;
        this.agent = new ImageIcon("./images/player.png").getImage();
        this.pokaball1 = new ImageIcon("./images/pokaball1.png").getImage();
        this.pokaball2 = new ImageIcon("./images/pokaball2.png").getImage();
        this.background = new ImageIcon("./images/back2.png").getImage();
    }

    /**
     * update panel
     */
    public void updatePanel() {
        double j = ((this.getHeight() * this.getWidth()) / 4000);
        double k = ((this.getHeight() * this.getWidth()) / 100000);
        Range rx = new Range(k, this.getWidth() - 10);
        Range ry = new Range(this.getHeight() - 10, j);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = this.arena.getGraph();
        this.W2F = Arena.w2f(g, frame);
    }

    /**
     * draw all
     * @param graphics
     */
    public void paint(Graphics graphics) {
        this.G2D = (Graphics2D) graphics;
        int w = this.getWidth();
        int h = this.getHeight();
        graphics.clearRect(0, 0, w, h);
        this.G2D.drawImage(this.background, 0, 0, w, h, null);
        drawGraph(graphics);
        drawPokemons(graphics);
        drawAgents(graphics);
        drawInfo(graphics);
    }

    /**
     * draw each node
     * @param node
     * @param graphics
     */
    public void drawNode(node_data node, Graphics graphics) {
        geo_location p = node.getLocation();
        geo_location f = this.W2F.world2frame(p);
        graphics.fillOval((int) f.x() - 5, (int) f.y() - 5, 2 * 5, 2 * 5);
        graphics.drawString("" + node.getKey(), (int) f.x(), (int) f.y() - 4 * 5);
    }

    /**
     * draw the graph
     * @param graphics
     */
    public void drawGraph(Graphics graphics) {
        this.G2D = (Graphics2D)graphics;
        for(node_data node : this.graph.getV()) {
            Font font = new Font(FONT, Font.BOLD, 12);
            graphics.setFont(font);
            graphics.setColor(Color.CYAN);
            drawNode(node, graphics);
            for(edge_data e : this.graph.getE(node.getKey())) {
                this.G2D.setStroke(new BasicStroke(2));
                this.G2D.setColor(Color.WHITE);
                drawEdge(e, this.G2D, this.graph);
            }
        }
    }

    /**
     * draw game info
     * @param graphics
     */
    private void drawInfo(Graphics graphics) {

        Graphics2D g2D = (Graphics2D)graphics;

        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));

        int x0 = this.getWidth() / 70;
        int y0 = this.getHeight() / 20;

        // print time left
        g2D.drawString("" + String.valueOf(arena.getTime() / 1000), x0 * 34, y0 + 2);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));

        int game_level = this.arena.getLevel();
        int grade = this.arena.getGrade();
        int moves = this.arena.getMoves();

        // print game level
        g2D.drawString("" + game_level, x0 * 22, y0 + 2);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));

        // print grade
        g2D.drawString("" + grade, x0 * 44, y0 + 2);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));

        // print moves
        g2D.drawString("" + moves, x0 * 55, y0 + 2);
        g2D.setFont(new Font(FONT, Font.PLAIN, (this.getHeight() * this.getWidth()) / 40000));
    }

    /**
     * draw each edge
     * @param edge
     * @param graphics
     * @param graph
     */
    public void drawEdge(edge_data edge, Graphics graphics, directed_weighted_graph graph) {
        geo_location src = graph.getNode(edge.getSrc()).getLocation();
        geo_location dest = graph.getNode(edge.getDest()).getLocation();
        geo_location s0 = this.W2F.world2frame(src);
        geo_location d0 = this.W2F.world2frame(dest);
        graphics.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
    }

    /**
     * draw each pokemon on the graph
     * @param graphics
     */
    private void drawPokemons(Graphics graphics) {
        this.G2D = (Graphics2D)graphics;
        List<CL_Pokemon> pokemons = this.arena.getPokemons();
        if(pokemons != null) {
            for(CL_Pokemon pokemon : pokemons) {
                Point3D point = pokemon.getLocation();
                int r = 9;
                if(point != null) {
                    geo_location fp = this.W2F.world2frame(point);
                    if(pokemon.getType() < 0) G2D.drawImage(this.pokaball1, (int) fp.x() - 3 * r, (int) fp.y() - r, 3 * r, 3 * r, null);
                    else this.G2D.drawImage(this.pokaball2, (int) fp.x() - r + 15, (int) fp.y() - r, 3 * r, 3 * r, null);
                }
            }
        }
    }

    /**
     * draw each agents on the graph
     * @param graphics
     */
    private void drawAgents(Graphics graphics) {
        this.G2D = (Graphics2D)graphics;
        List<CL_Agent> agents = this.arena.getAgents();
        graphics.setColor(Color.red);
        int i = 0;
        while(agents != null && i < agents.size()) {
            geo_location location = agents.get(i).getLocation();
            int r = 8;
            i++;
            if(location != null) {
                geo_location fp = this.W2F.world2frame(location);
                this.G2D.drawImage(this.agent, (int) fp.x() - 2 * r, (int) fp.y() - r, 5 * r, 5 * r, null);
            }
        }
    }
}
