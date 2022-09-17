package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 * @author boaz.benmoshe
 */

public class Arena {
	public static final double EPS1 = 0.001, EPS2 = EPS1 * EPS1;
	private directed_weighted_graph graph;
	private List<CL_Agent> agents;
	private List<CL_Pokemon> pokemons;
	private List<String> info;
	private long time;
	private static Point3D MIN = new Point3D(0, 100, 0);
	private static Point3D MAX = new Point3D(0, 100, 0);

	// current game grade, number of moves and game level
	private int grade, moves, game_level;

	// default constructor
	public Arena() { info = new ArrayList<String>(); }

	// second constructor
	private Arena(directed_weighted_graph g, List<CL_Agent> r, List<CL_Pokemon> p) {
		this.graph = g;
		this.setAgents(r);
		this.setPokemons(p);
		info = new ArrayList<String>();
	}

	/**
	 * setting pokemons by list
	 * @param pokemons
	 */
	public void setPokemons(List<CL_Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	/**
	 * setting agents by list
	 * @param agents
	 */
	public void setAgents(List<CL_Agent> agents) {
		this.agents = agents;
	}

	/**
	 * setting graph directed_weighted_graph graph
	 * @param graph
	 */
	public void setGraph(directed_weighted_graph graph) { this.graph = graph; }

	/**
	 * setting the info
	 * @param info
	 */
	public void set_info(List<String> info) { this.info = info; }

	/**
	 * initialize
	 */
	private void init() {
		MIN = null;
		MAX = null;
		double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
		Iterator<node_data> iter = this.graph.getV().iterator();
		while (iter.hasNext()) {
			geo_location c = iter.next().getLocation();
			if (MIN == null) {
				x0 = c.x();
				y0 = c.y();
				x1 = x0;
				y1 = y0;
				MIN = new Point3D(x0, y0);
			}
			if (c.x() < x0) x0 = c.x();
			if (c.y() < y0) y0 = c.y();
			if (c.x() > x1) x1 = c.x();
			if (c.y() > y1) y1 = c.y();

		}
		double dx = x1 - x0, dy = y1 - y0;
		MIN = new Point3D(x0 - dx / 10, y0 - dy / 10);
		MAX = new Point3D(x1 + dx / 10, y1 + dy / 10);
	}

	/**
	 * getting the agent by list
	 * @return
	 */
	public List<CL_Agent> getAgents() {
		return this.agents;
	}

	/**
	 * getting the pokemons by list
	 * @return
	 */
	public List<CL_Pokemon> getPokemons() { return this.pokemons; }

	/**
	 * getting the graph
	 * @return
	 */
	public directed_weighted_graph getGraph() {
		return this.graph;
	}

	/**
	 * getting the info by list
	 * @return
	 */
	public List<String> get_info() {
		return this.info;
	}

	/**
	 * static helpful function
	 * @param aa
	 * @param gg
	 * @return
	 */
	public static List<CL_Agent> getAgents(String aa, directed_weighted_graph gg) {
		ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
		try {
			JSONObject ttt = new JSONObject(aa);
			JSONArray ags = ttt.getJSONArray("Agents");
			for(int i = 0; i < ags.length(); i++) {
				CL_Agent c = new CL_Agent(gg, 0);
				c.update(ags.get(i).toString());
				ans.add(c);
			}
		} catch (JSONException e) { e.printStackTrace(); }
		return ans;
	}

	/**
	 * json string of pokemons to a list of pokemons onjects
	 * @param fs
	 * @return
	 */
	public static ArrayList<CL_Pokemon> json2Pokemons(String fs) {
		ArrayList<CL_Pokemon> ans = new ArrayList<CL_Pokemon>();
		try {
			JSONObject ttt = new JSONObject(fs);
			JSONArray ags = ttt.getJSONArray("Pokemons");
			for (int i = 0; i < ags.length(); i++) {
				JSONObject pp = ags.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon");
				int t = pk.getInt("type");
				double v = pk.getDouble("value");
				//double s = 0;//pk.getDouble("speed");
				String p = pk.getString("pos");
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
				ans.add(f);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ans;
	}

	/**
	 * update edge for pokemon
	 * @param fr
	 * @param g
	 */
	public static void updateEdge(CL_Pokemon fr, directed_weighted_graph g) {
		//	oop_edge_data ans = null;
		Iterator<node_data> itr = g.getV().iterator();
		while (itr.hasNext()) {
			node_data v = itr.next();
			Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
			while (iter.hasNext()) {
				edge_data e = iter.next();
				boolean f = isOnEdge((geo_location) fr.getLocation(), e, fr.getType(), g);
				if (f) {
					fr.set_edge(e);
				}
			}

		}

	}

	/**
	 * return true if p geo location is between src and dest locations
	 * @param p
	 * @param src
	 * @param dest
	 * @return
	 */
	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest) {
		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist > d1 - EPS2) ans = true;
		return ans;
	}

	/**
	 * return true if p geo location is between s and d locations in the graph
	 * @param p
	 * @param s
	 * @param d
	 * @param g
	 * @return
	 */
	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p, src, dest);
	}

	/**
	 * return true if p geo location is between src and dest locations of the e edge
	 * @param p
	 * @param e
	 * @param type
	 * @param g
	 * @return
	 */
	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if (type < 0 && dest > src) return false;
		if (type > 0 && src > dest) return false;
		return isOnEdge(p, src, dest, g);
	}

	/**
	 * update edge for agent
	 * @param fr
	 * @param g
	 */
	public static void updateEdgeForAgent(CL_Agent fr, directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		while (itr.hasNext()) {
			node_data v = itr.next();
			Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
			while (iter.hasNext()) {
				edge_data e = iter.next();
				geo_location src =g.getNode(e.getSrc()).getLocation();
				geo_location dest =g.getNode(e.getDest()).getLocation();
				boolean f= isAgentsOnEdge(fr.getLocation(),src,dest);
				if (f == true) {
					fr.set_curr_edge(e);
					return;
				}
			}
		}
	}

	/**
	 * @param p
	 * @param src
	 * @param dest
	 * @return
	 */
	private static boolean isAgentsOnEdge(geo_location p, geo_location src, geo_location dest) {
		boolean ans = false;
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if (dist > d1 - EPS2) ans = true;
		return ans;
	}

	/**
	 *
	 * @param g
	 * @return
	 */
	private static Range2D GraphRange(directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
		boolean first = true;
		while (itr.hasNext()) {
			geo_location p = itr.next().getLocation();
			if (first) {
				x0 = p.x();
				x1 = x0;
				y0 = p.y();
				y1 = y0;
				first = false;
			} else {

				if (p.x() < x0) x0 = p.x();
				if (p.x() > x1) x1 = p.x();
				if (p.y() < y0) y0 = p.y();
				if (p.y() > y1) y1 = p.y();

			}
		}
		Range xr = new Range(x0, x1);
		Range yr = new Range(y0, y1);
		return new Range2D(xr, yr);
	}

	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

	/**
	 * set time
	 * @param time
	 */
	public void setTime(long time) { this.time = time; }

	/**
	 * get time
	 * @return
	 */
	public long getTime() { return this.time; }

	/**
	 * set level
	 * @param game_level
	 */
	public void setLevel(int game_level) { this.game_level = game_level; }

	/**
	 * game level
	 * @return
	 */
	public int getLevel() { return this.game_level; }

	/**
	 * set grade
	 * @param grade
	 */
	public void setGrade(int grade) { this.grade = grade; }

	/**
	 * get grade
	 * @return
	 */
	public int getGrade() { return this.grade; }

	/**
	 * set moves
	 * @param moves
	 */
	public void setMoves(int moves) { this.moves = moves; }

	/**
	 * get moves
	 * @return
	 */
	public int getMoves() { return this.moves; }

}
