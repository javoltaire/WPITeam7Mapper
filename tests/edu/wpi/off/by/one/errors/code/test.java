package edu.wpi.off.by.one.errors.code;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import java.lang.String;
import java.util.Vector;

import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class test extends TestCase{
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    
    //test coordinate getters
    @Test
    public void testCo1(){
        Coordinate c = new Coordinate(100,200, -300);
        assertEquals(c.getX(), (float)100.0);
        assertEquals(c.getY(), (float)200.0);
        assertEquals(c.getZ(), (float)-300.0);
    }
    
    @Test
    public void testCo2(){
        Coordinate c = new Coordinate(100,200);
        assertEquals(c.getX(), (float)100.0);
        assertEquals(c.getY(), (float)200.0);
        assertEquals(c.getZ(), (float)0.0);
    }
    @Test
    public void testCo3(){
        Coordinate c = new Coordinate(400);
        assertEquals(c.getX(), (float)400.0);
        assertEquals(c.getY(), (float)400.0);
        assertEquals(c.getZ(), (float)400.0);
    }
    
    //test Graph
    @Test
    public void testGraph(){
        Graph g = new Graph();
        Coordinate c1 = new Coordinate(400);
        Coordinate c2 = new Coordinate(100,0,0);
        Coordinate c3 = new Coordinate(0);
        Coordinate c4 = new Coordinate(600);
        g.addNode(c1);
        g.addNode(c2);
       
        assertEquals( g.addEdgeRint(0, 1), 0);
        
        g.addNode(c3);
        g.addNode(c4);

        assertEquals( g.addEdgeRint(2, 3), 1);
        
        Vector<Node> lon = g.getNodes();
        assertEquals(lon.get(0).getId(), 0);
        assertEquals(lon.get(1).getId(), 1);
        
        Vector<Edge> loe = g.getEdges();
        assertEquals(loe.get(0).getId(), 0);
        g.editNode(1, c1);//could have something wrong
        assertEquals(lon.get(1).getCoordinate(), c1);
    }
    
    //test FileIO

    
}
