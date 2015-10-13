package figures;

import java.util.LinkedList;

/**
 *
 * @author Матвей
 */
public class Polygon {
    
    LinkedList<Vertex> points;
    LinkedList<Section> edges;
    
    Polygon move(Vector vec){
        return this;
    }
    
    Polygon rotate(double angle){
        return this;
    }
    
    Polygon relocate(Vertex newO){
        return this;
    }
    
}
