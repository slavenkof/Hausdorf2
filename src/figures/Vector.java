package figures;

import java.awt.geom.Point2D;

/**
 *
 * @author Матвей
 */
public class Vector {

    double x;
    double y;

    Point2D.Double start;
    Point2D.Double end;

    boolean isBounded;

    double length;
    boolean lengthCounted;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Point2D.Double start, Point2D.Double end) {

    }

    Vector nMultiply(double n) {
        return this;
    }

    Vector swap() {
        return this;
    }

    Vector bound(Point2D.Double start) {
        return this;
    }

    Vector unbound() {
        return this;
    }

    double length() {
        return 0;
    }

    double lengthSq() {
        return 0;
    }

    Vector normalize() {
        return this;
    }

    double multiplyVectorly(Vector vector) {
        return 0;
    }

    Vertex toVertex() {
        return new Vertex(x, y);
    }
}
