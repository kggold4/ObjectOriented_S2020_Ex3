package gameClient;

import api.*;
import gameClient.util.Point3D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CL_Pokemon {
	private edge_data edge;
	private double value;
	private int type;
	private Point3D pos;
	private double min_dist;
	private int min_ro;
	private CL_Agent nxtEater;
	private List<CL_Pokemon> closePkm;
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		type = t;
		value = v;
		set_edge(e);
		pos = p;
		min_dist = Integer.MAX_VALUE;
		min_ro = -1;
		nxtEater= null;
		closePkm= new ArrayList<>();
	}

	public List<CL_Pokemon> getClosePokemon() { return closePkm; }

	public String toString() { return "F:{v="+this.value+", t="+this.type+"}"; }

	public edge_data get_edge() { return this.edge; }

	public void set_edge(edge_data _edge) { this.edge = _edge; }

	public Point3D getLocation() { return this.pos; }
	public int getType() {return type;}

	public double getValue() {return this.value;}

	public double getMin_dist() { return this.min_dist; }

	public void setMin_dist(double mid_dist) { this.min_dist = mid_dist; }

	public CL_Agent getNxtEater() { return this.nxtEater; }

	public void setNxtEater(CL_Agent nxtEater) { this.nxtEater = nxtEater; }

	public int getMin_ro() { return this.min_ro; }

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		CL_Pokemon that = (CL_Pokemon) o;
		return Double.compare(that.value, this.value) == 0 &&
				this.type == that.type &&
				Objects.equals(this.pos, that.pos);
	}

	@Override
	public int hashCode() { return Objects.hash(this.value, this.type, this.pos); }
}
