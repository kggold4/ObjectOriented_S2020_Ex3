package tests;

import org.junit.jupiter.api.Test;
import api.*;
import static org.junit.jupiter.api.Assertions.*;

class GEOLocationTest {

    // create new location by default constructor
    private geo_location g0 = new GEOLocation();  // (0, 0, 0)

    // create new location by given coordinates constructor
    private geo_location g1 = new GEOLocation(1.5,2.5,3.5);

    @Test
    void x() {

        assertEquals(0,g0.x());
        assertEquals(1.5,g1.x());
    }

    @Test
    void y() {

        assertEquals(0,g0.y());
        assertEquals(2.5,g1.y());
    }

    @Test
    void z() {

        assertEquals(0,g0.z());
        assertEquals(3.5,g1.z());
    }

    @Test
    void distance() {

        // distance should be sqrt(27)
        geo_location g0 = new GEOLocation(1,2,3);
        geo_location g1 = new GEOLocation(4,5,6);
        assertEquals(Math.sqrt(27), g0.distance(g1));

        // distance should be sqrt(104.05)
        geo_location g2 = new GEOLocation(-1,3.5,-0.1);
        geo_location g3 = new GEOLocation(-4,5.2,9.5);
        assertEquals(Math.sqrt(104.05), g2.distance(g3));

        // distance should be sqrt(3)
        geo_location g4 = new GEOLocation(0,1,0);
        geo_location g5 = new GEOLocation(1,0,1);
        assertEquals(Math.sqrt(3), g4.distance(g5));

    }
}