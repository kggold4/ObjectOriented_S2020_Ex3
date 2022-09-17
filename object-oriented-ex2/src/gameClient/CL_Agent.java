package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.List;

public class CL_Agent {
	private int id;
	private geo_location pos;
	private double speed;
	private edge_data currentEdge;
	private node_data currNode;
	private directed_weighted_graph graph;
	private CL_Pokemon _curr_fruit;
	private long _sg_dt;
	private double value;
	private List<node_data> path;

	public List<node_data> getPath() {
		return path;
	}

	public void setPath(List<node_data> path, node_data n) {
		this.path = path;
		path.add(n);
	}

	public CL_Agent(directed_weighted_graph g, int start_node) {
		this.graph = g;
		setMoney(0);
		this.currNode = this.graph.getNode(start_node);
		this.pos = this.currNode.getLocation();
		this.id = -1;
		setSpeed(0);
	}

	public void update(String json) {
		JSONObject line;
		try {
			line = new JSONObject(json);
			JSONObject ttt = line.getJSONObject("Agent");
			int id = ttt.getInt("id");
			if(id==this.getID() || this.getID() == -1) {
				if(this.getID() == -1) this.id = id;
				double speed = ttt.getDouble("speed");
				String p = ttt.getString("pos");
				Point3D pp = new Point3D(p);
				int src = ttt.getInt("src");
				int dest = ttt.getInt("dest");
				double value = ttt.getDouble("value");
				this.pos = pp;
				this.setCurrNode(src);
				this.setSpeed(speed);
				this.setNextNode(dest);
				this.setMoney(value);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int getSrcNode() {return this.currNode.getKey();}

	public String toJSON() {
		int d = this.getNextNode();
		String ans = "{\"Agent\":{"
				+ "\"id\":"+this.id+","
				+ "\"value\":"+this.value+","
				+ "\"src\":"+this.currNode.getKey()+","
				+ "\"dest\":"+d+","
				+ "\"speed\":"+this.getSpeed()+","
				+ "\"pos\":\""+this.pos.toString()+"\""
				+ "}"
				+ "}";
		return ans;
	}
	private void setMoney(double v) {this.value = v;}

	public boolean setNextNode(int dest) {
		boolean ans = false;
		int src = this.currNode.getKey();
		this.currentEdge = this.graph.getEdge(src, dest);
		if(this.currentEdge != null) ans=true;
		return ans;
	}

	public void setCurrNode(int src) { this.currNode = this.graph.getNode(src); }

	public boolean isMoving() { return this.currentEdge!=null; }

	public String toString() { return toJSON(); }

	public int getID() { return this.id; }

	public geo_location getLocation() { return this.pos; }

	public double getValue() { return this.value; }

	public int getNextNode() {
		int ans = -2;
		if(this.currentEdge == null) ans = -1;
		else ans = this.currentEdge.getDest();
		return ans;
	}

	public double getSpeed() { return this.speed; }

	public void setSpeed(double v) { this.speed = v; }

	public CL_Pokemon get_curr_fruit() { return _curr_fruit; }

	public void set_curr_fruit(CL_Pokemon curr_fruit) { this._curr_fruit = curr_fruit; }

	public void set_curr_edge(edge_data _curr_edge) { this.currentEdge = _curr_edge; }

	public void set_SDT(long ddtt) {
		long ddt = ddtt;
		if(this.currentEdge!=null) {
			double w = get_curr_edge().getWeight();
			geo_location dest = this.graph.getNode(get_curr_edge().getDest()).getLocation();
			geo_location src = this.graph.getNode(get_curr_edge().getSrc()).getLocation();
			double de = src.distance(dest);
			double dist = this.pos.distance(dest);
			if(this.get_curr_fruit().get_edge()==this.get_curr_edge()) {
				dist = _curr_fruit.getLocation().distance(this.pos);
			}
			double norm = dist/de;
			double dt = w*norm / this.getSpeed();
			ddt = (long)(1000.0*dt);
		}
		this.set_sg_dt(ddt);
	}

	public edge_data get_curr_edge() { return this.currentEdge; }

	public void set_sg_dt(long _sg_dt) { this._sg_dt = _sg_dt; }
}