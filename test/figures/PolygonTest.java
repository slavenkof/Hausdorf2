package figures;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Матвей
 */
public class PolygonTest {

    /**
     * Данные, общие для всех тестов. Представляют разнобразные случаи, которые
     * стоит подвергнуть тестированию.
     */
    LinkedList<Vertex[]> sharedData;

    /**
     * Инициализация данных
     */
    public PolygonTest() {
        sharedData = new LinkedList<>();
        sharedData.add(
                new Vertex[]{
                    new Vertex(0, 0),
                    new Vertex(10, 0),
                    new Vertex(0, 10)
                });
        sharedData.add( //Обработка точек в разных квадрантах
                new Vertex[]{
                    new Vertex(-5, 5),
                    new Vertex(5, 5),
                    new Vertex(5, -5),
                    new Vertex(-5, -5)
                });
        sharedData.add( //На границах квадрантов
                new Vertex[]{
                    new Vertex(0, 5),
                    new Vertex(-5, 0),
                    new Vertex(0, -5),
                    new Vertex(5, 0)
                }
        );
        sharedData.add( //Невыпуклый
                new Vertex[]{
                    new Vertex(-5, 5),
                    new Vertex(5, 5),
                    new Vertex(5, -5),
                    new Vertex(-5, -5),
                    new Vertex(0, 0)
                });
    }

    /**
     * Тестирование метода getVertexes() на общих данных.
     *
     * @throws VertexAmountException
     * @throws VertexPositionException
     */
    @Test
    public void testGetVertexesOnSharedData() throws VertexAmountException, VertexPositionException {
        System.out.println("getVertexes:SharedData: " + sharedData.size() + " test");

        ListIterator<Vertex[]> itera = sharedData.listIterator();
        int i = 0;
        LinkedList<Vertex> expResult = new LinkedList<>();
        Vertex[] points;
        while (itera.hasNext()) {
            points = itera.next();

            Polygon instance = new Polygon(points);
            expResult.addAll(Arrays.asList(points));
            expResult.add(points[0]);

            assertEquals("Fail on the iteration with i = " + i, expResult, instance.getVertexes());

            expResult.clear();
        }
    }

    /**
     * Тестирование метода getEdges() на общих данных.
     *
     * @throws VertexAmountException
     * @throws VertexPositionException
     */
    @Test
    public void testGetEdgesOnSharedData() throws VertexAmountException, VertexPositionException {
        System.out.println("getEdges:SharedData: " + sharedData.size() + " test");

        ListIterator<Vertex[]> itera = sharedData.listIterator();
        int i = 0;

        while (itera.hasNext()) {

            Vertex points[] = itera.next();
            Polygon instance = new Polygon(points);

            LinkedList<Section> expResult = new LinkedList<Section>() { //using double brace initialization syntax to simplify 
                private static final long serialVersionUID = 1L;//the filling of list of expected results and make it transparent

                {
                    for (int j = 0; j < points.length - 1; j++) { // at this cycle we create an array, which is an answer
                        add(j, j + 1);
                    }
                    add(points.length - 1, 0);
                }

                void add(int a, int b) { // defining a specific .add() method. It adds a new Section, consisting 
                    add(new Section(points[a], points[b])); //of points with indexes 'a' and 'b'
                }
            };

            LinkedList<Section> result = instance.getEdges();
            assertArrayEquals("Fail on the iteration with i = " + i, expResult.toArray(), result.toArray());
            i++;

        }
    }

    /**
     * Тестирование выбрасывания исключения VertexAmountException
     *
     * @throws VertexAmountException
     * @throws VertexPositionException
     */
    @Test
    public void testNumberOfVertexesExceptionInConstructor() throws VertexAmountException, VertexPositionException {
        Vertex data[][] = new Vertex[][]{
            new Vertex[]{}, // пустота
            new Vertex[]{new Vertex(0, 0)}, // одна точка
            new Vertex[]{new Vertex(0, 0), new Vertex(33, 23)} // две точки
        };

        System.out.println("numberOfVertexesExceptionInConstructor: " + data.length + " test");
        Polygon instance;
        /*
         * Исключение должно быть выброшено. Если этого не происходит, тест
         * падает.
         */
        for (int i = 0; i < data.length; i++) {
            try {
                instance = new Polygon(data[i]);
                fail("Failed on the iteration with i = " + i);
            } catch (VertexAmountException e) {
            }
        }

    }

    /**
     * Тестирование выбрасывания исключения VertexPositionException. Тестируются
     * вырожденные случаи многоугольников-отрезков.
     *
     * @throws VertexAmountException
     * @throws VertexPositionException
     */
    @Test
    public void testVertexPositionException1() throws VertexAmountException, VertexPositionException {
        System.out.println("testVertexPositionException1");
        Polygon instance;
        try {
            instance = Polygon.builder() //горизонтальный отрезок на оси оХ
                    .add(-1, 0)
                    .add(0, 0)
                    .add(1, 0)
                    .build();
            fail("Fail on the 1 set");
        } catch (VertexPositionException e) {
        }

        try {
            instance = Polygon.builder() //диагональный отрезок, проходящий через центр
                    .add(-1, -1)
                    .add(0, 0)
                    .add(1, 1)
                    .build();
            fail("Fail on the 2 set");
        } catch (VertexPositionException e) {
        }

        try {
            instance = Polygon.builder() //вертикальный отрезок на оси оУ
                    .add(0, -1)
                    .add(0, 0)
                    .add(0, 1)
                    .build();
            fail("Fail on the 3 set");
        } catch (VertexPositionException e) {
        }
    }

    /**
     * Тестирование выбрасывания исключения VertexPositionException. Тестируются
     * случаи последних удвоенных точек. На общих данных.
     *
     * @throws VertexAmountException
     * @throws VertexPositionException
     */
    @Test
    public void testVertexPositionException2onSharedData() throws VertexAmountException, VertexPositionException {
        System.out.println("testVertexPositionException2: " + sharedData.size() + " tests");
        Polygon instance;
        int i = 0;
        for (Vertex[] points : sharedData) {
            try {
                instance = new Polygon(new Polygon(points).getVertexes());
                fail("Failed on the iteration with i = " + i);
            } catch (VertexPositionException e) {
            }
            i++;
        }
    }

    /**
     * Тестирование выбрасывания исключения VertexPositionException. Тестируются
     * случаи удвоенных точек.
     *
     * @throws VertexAmountException
     * @throws VertexPositionException
     */
    @Test
    public void testVertexPositionException3() throws VertexAmountException, VertexPositionException {
        System.out.println("testVertexPositionException");
        Polygon instance;
        List<Vertex> data;

        data = new LinkedList<>(Arrays.asList(sharedData.get(0)));
        data.add(0, data.get(0));
        try {
            instance = new Polygon(data);
            fail("Fail on the 1 set");
        } catch (VertexPositionException e) {
        }

        data = new LinkedList<>(Arrays.asList(sharedData.get(0)));
        data.add(1, data.get(0));

        try {
            instance = new Polygon(data);
            fail("Fail on the 2 set");
        } catch (VertexPositionException e) {
        }

        data = new LinkedList<>(Arrays.asList(sharedData.get(0)));
        data.add(1, data.get(1));

        try {
            instance = new Polygon(data);
            fail("Fail on the 2 set");
        } catch (VertexPositionException e) {
        }
    }

    /**
     * Тестирование выбрасывания исключения VertexPositionException. Тестируются
     * случаи развернутых углов.
     *
     * @throws VertexAmountException
     * @throws VertexPositionException
     */
    @Test
    public void testVertexPositionException4() throws VertexAmountException, VertexPositionException {
        System.out.println("testVertexPositionException4");
        Polygon instance;
        try {
            instance = Polygon.builder() //горизонтальный отрезок на оси оХ
                    .add(-1, 0)
                    .add(0, 0)
                    .add(1, 0)
                    .add(0, 1)
                    .build();
            fail("Fail on the 1 set");
        } catch (VertexPositionException e) {
        }

        try {
            instance = Polygon.builder() //диагональный отрезок, проходящий через центр
                    .add(-1, -1)
                    .add(0, 0)
                    .add(1, 1)
                    .add(-1, 1)
                    .build();
            fail("Fail on the 2 set");
        } catch (VertexPositionException e) {
        }

        try {
            instance = Polygon.builder() //вертикальный отрезок на оси оУ
                    .add(0, -1)
                    .add(0, 0)
                    .add(0, 1)
                    .add(-1, 0)
                    .build();
            fail("Fail on the 3 set");
        } catch (VertexPositionException e) {
        }
    }

    @Ignore
    @Test
    public void testMove() {
        System.out.println("move");
        Vector vec = null;
        Polygon instance = null;
        Polygon expResult = null;
        Polygon result = instance.move(vec);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double angle = 0.0;
        Polygon instance = null;
        Polygon expResult = null;
        Polygon result = instance.rotate(angle);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testRelocate() {
        System.out.println("relocate");
        Vertex newO = null;
        Polygon instance = null;
        Polygon expResult = null;
        Polygon result = instance.relocate(newO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEdges method, of class Polygon.
     */
    @Ignore
    @Test
    public void testGetEdges() throws VertexAmountException, VertexPositionException {
        System.out.println("getEdges");
        Polygon instance = new Polygon();
        LinkedList<Section> expResult = null;
        LinkedList<Section> result = instance.getEdges();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isConvex method, of class Polygon.
     */
    @Ignore
    @Test
    public void testIsConvex() throws VertexAmountException, VertexPositionException {
        System.out.println("isConvex");
        Polygon instance = new Polygon();
        boolean expResult = false;
        boolean result = instance.isConvex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVertexes method, of class Polygon.
     */
    @Ignore
    @Test
    public void testGetVertexes() {
        System.out.println("getVertexes");
        Polygon instance = null;
        LinkedList<Vertex> expResult = null;
        LinkedList<Vertex> result = instance.getVertexes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
