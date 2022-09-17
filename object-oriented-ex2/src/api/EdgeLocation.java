package api;

import java.io.Serializable;
import java.util.Objects;

// this class represents a position on the graph
public class EdgeLocation implements edge_location, Serializable {

    // edge object
    private edge_data edge;

    // default constructor
    public EdgeLocation(edge_data edge) { this.edge = edge; }

    // return the edge of the edge location
    @Override
    public edge_data getEdge() { return this.edge; }

    // get ratio method
    @Override
    public double getRatio() { return 0; }

    // to string method
    @Override
    public String toString() { return "" + this.edge; }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeLocation)) return false;
        EdgeLocation that = (EdgeLocation) o;
        return Objects.equals(edge, that.edge);
    }

    // hash code method
    @Override
    public int hashCode() { return Objects.hash(edge); }
}