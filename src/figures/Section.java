package figures;

import java.awt.geom.Point2D;

/**
 *
 * @author Матвей
 */
public class Section {

    Vertex a;
    Vertex b;

    Section(Point2D.Double a, Point2D.Double b) {
        this.a = (Vertex)a;
        this.b = (Vertex)b;
    }

    Section(Point2D.Double start, Vector vec) {
        a = new Vertex(start);
        b = new Vertex(a.clone().move(vec));
    }

    Section move(Vector vec) {
        a.move(vec);
        b.move(vec);

        return this;
    }

    Vector direction() {
        return null;
    }

    Section rotate(double angle) {
        return this;
    }
    
    Section relocate(Vertex newO){
        return this;
    }

    @Override
    public String toString() {
        return "[" + a.toString() + " <-> " + b.toString() + "]";
    }
}
