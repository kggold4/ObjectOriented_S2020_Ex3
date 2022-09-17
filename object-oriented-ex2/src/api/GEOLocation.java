package api;

import java.io.Serializable;
import java.util.Objects;

// this GEOLocation class represents a geographic location <x,y,z> in the graph
public class GEOLocation implements geo_location, Serializable {

    // coordinates
    private double x, y, z;

    // GEOLocation default constructor
    public GEOLocation() {
        this.x = this.y = this.z = 0;
    }

    // GEOLocation constructor by given coordinates
    public GEOLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // deep copy constructor
    public GEOLocation(geo_location new_location) {
        this.x = new_location.x();
        this.y = new_location.y();
        this.z = new_location.z();
    }

    // return x coordinate
    @Override
    public double x() {
        return this.x;
    }

    // return y coordinate
    @Override
    public double y() {
        return this.y;
    }

    // return z coordinate
    @Override
    public double z() {
        return this.z;
    }

    // return the distance between this get-locating and a given another geo_location
    @Override
    public double distance(geo_location g) { return Math.sqrt(Math.pow(this.x - g.x(), 2) + Math.pow(this.y - g.y(), 2) + Math.pow(this.z - g.z(), 2)); }

    // to string method
    @Override
    public String toString() {
        return "(" + this.x() + ", " + this.y() + ", " + this.z() + ")";
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GEOLocation)) return false;
        GEOLocation that = (GEOLocation) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.z, z) == 0;
    }

    // hash code method
    @Override
    public int hashCode() { return Objects.hash(x, y, z); }
}