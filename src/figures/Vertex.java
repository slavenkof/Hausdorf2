package figures;

import java.awt.geom.Point2D;

/**
 *
 * @author Матвей
 */
public class Vertex extends Point2D.Double implements Cloneable {

    private static final long serialVersionUID = 1L;

    public Vertex(double x, double y) {
        super(x, y);
    }

    public Vertex(Point2D.Double point) {
        super();
        this.setLocation(point);
    }

    Vector toVector() {
        return new Vector(x, y);
    }

    Vertex move(Vector vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }
    
    Vertex rotate(double angle){
        return this;
    }
    
    Vertex relocate(Vertex newO){
        return this;
    }

    @Override
    public Vertex clone() {
        return (Vertex) super.clone();
    }

    @Override
    public String toString() {
        return "(" + java.lang.Double.toString(x) + "; " + java.lang.Double.toString(x) + ")";
    }
}
