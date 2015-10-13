package figures;

import java.util.LinkedList;

/**
 *
 * @author Матвей
 */
public class Console {

    public static void main(String[] args) {
        LinkedList<Vertex> points = new LinkedList<>();
        Vertex a = new Vertex(0, 0);
        Vertex b = new Vertex(33, 33);
        points.add(a);
        points.add(b);
        
        Vector vec = new Vector(-23, -23);
        points.forEach(point -> point.move(vec));
        points.forEach(point -> System.out.println(point));

        Section sect = new Section(a, b);
        System.out.println(a.toString());
//        System.out.println("before " + sect.toString());
//        
//
//        a.move(vec);
//        b.move(vec);
//        System.out.println(a.toString());
//        System.out.println("after " + sect.toString());
    }

}
