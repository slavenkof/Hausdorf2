package figures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Матвей
 */
public class VertexTest {

    public VertexTest() {
    }
    /**
     * В данном массиве представлены точки во всех квадрантах, на всех границах
     * квадрантов и начало координат.
     */
    double data[][] = new double[][]{
        new double[]{0, 0},
        new double[]{1, 0},
        new double[]{1, 1},
        new double[]{0, 1},
        new double[]{-1, 1},
        new double[]{-1, 0},
        new double[]{-1, -1},
        new double[]{0, -1},
        new double[]{1, -1}};

    /**
     * Тестирование преобразования точки в радиус-вектор. Тесты проводятся на
     * массиве data, а также массиве data, помноженном на 0,5.
     */
    @Test
    public void testToVector() {
        System.out.println("toVector: 18 tests");
        Vertex instance;
        Vector expected;
        int i = 0;
        for (double dat[] : data) {
            instance = new Vertex(dat);
            expected = new Vector(dat);
            assertEquals("failed on iteration i= " + i + ".1", expected, instance.toVector());

            instance = new Vertex(dat[0] * 0.5, dat[1] * 0.5);
            expected = new Vector(dat[0] * 0.5, dat[1] * 0.5);
            assertEquals("failed on iteration i= " + i + ".2", expected, instance.toVector());
            i++;
        }
    }

    /**
     * Тестирование смещения точки на вектор. Тест (первые два блока) взят из
     * предыдущего проекта. Третий блок тестирует смещение на нулевой вектор.
     * Четвертый блок также тестирует обращение вектора и преобразование вершины
     * в радиус-вектор - в нем представлен тест "сложение с противоположным".
     *
     * @see Vertex#toVector()
     * @see Vector#swap()
     *
     */
    @Test
    public void testMoveOld() {
        System.out.println("-------------------");
        System.out.println("testMoveOld: 16");
        Vertex data[] = new Vertex[4];
        data[0] = new Vertex(38, 295);
        data[1] = new Vertex(616, 42);
        data[2] = new Vertex(202, 420);
        data[3] = new Vertex(64, 67);
        Vector data1[] = new Vector[4];
        data1[0] = new Vector(9, 6);
        data1[1] = new Vector(4, 7);
        data1[2] = new Vector(1, 33);
        data1[3] = new Vector(0, 0);
        Vertex etData[] = new Vertex[8];
        etData[0] = new Vertex(47, 301);
        etData[1] = new Vertex(620, 49);
        etData[2] = new Vertex(203, 453);
        etData[3] = new Vertex(64, 67);
        etData[4] = new Vertex(42, 302);//0+1
        etData[5] = new Vertex(617, 75);//1+2
        etData[6] = new Vertex(202, 420);//2+3
        etData[7] = new Vertex(73, 73);//3+0

        for (int i = 0; i < 4; i++) {
            assertEquals("failed at " + i, etData[i], data[i].clone().move(data1[i]));
        }
        System.out.println("{First Asserted}");
        for (int i = 4; i < 7; i++) {
            assertEquals("failed at " + i, etData[i], data[i - 4].clone().move(data1[i - 3]));
        }
        System.out.println("{Second Asserted}");
        assertEquals(etData[7], data[3].move(data1[0]));

        for (Vertex v : etData) {
            assertEquals(v, v.move(data1[3]));
        }
        System.out.println("{Third Asserted}"); //Смещение на нулевой вектор

        for (Vertex v : etData) { //Сложение с противоположным
            assertEquals(new Vertex(0, 0), v.move(v.toVector().swap()));
        }
        System.out.println("{Forth Asserted}");
        System.out.println("-------------------");
    }

    /**
     * Тестирование смещения точки на вектор. В тесте преставлены четыре блока.
     * Тестируется смещение точек как имеющих целые координаты, так и дробные,
     * на вектора с целыми и дробными координатами.
     */
    @Test
    public void testMove() {
        System.out.println("-------------------");
        System.out.println("testMove: 224 test");
        Vertex instance;
        Vector vec;
        Vertex expected;
        int i = 0;
        int j = 0;

        for (double vertexCoords[] : data) {//целые кординаты вектора и точки
            for (double vectCoords[] : data) {
                instance = new Vertex(vertexCoords);
                vec = new Vector(vectCoords);
                expected = new Vertex(sumArrays(vertexCoords, vectCoords));

                assertEquals("Failed on iteration I " + i + "." + j, expected, instance.move(vec));
                j++;
            }
            i++;
        }
        System.out.println("{First Asserted}");

        i = 0;
        j = 0;

        for (double vertexCoords[] : data) { //целый вектор, дробная точка
            for (double vectCoords[] : data) {
                instance = new Vertex(vertexCoords[0] * 0.5, vertexCoords[1] * 0.5);
                vec = new Vector(vectCoords);
                expected = new Vertex(sumArrays(new double[]{vertexCoords[0] * 0.5, vertexCoords[1] * 0.5}, vectCoords));

                assertEquals("Failed on iteration II " + i + "." + j, expected, instance.move(vec));
                j++;
            }
            i++;
        }
        System.out.println("{Second Asserted}");

        i = 0;
        j = 0;

        for (double vertexCoords[] : data) { //дробный вектор, целая точка
            for (double vectCoords[] : data) {
                instance = new Vertex(vertexCoords);
                vec = new Vector(vectCoords[0] * 0.5, vectCoords[1] * 0.5);
                expected = new Vertex(sumArrays(vertexCoords, new double[]{vectCoords[0] * 0.5, vectCoords[1] * 0.5}));

                assertEquals("Failed on iteration III " + i + "." + j, expected, instance.move(vec));
                j++;
            }
            i++;
        }
        System.out.println("{Third Asserted}");

        i = 0;
        j = 0;

        for (double vertexCoords[] : data) { //дробный вектор, дробная точка
            for (double vectCoords[] : data) {
                instance = new Vertex(vertexCoords[0] * 0.5, vertexCoords[1] * 0.5);
                vec = new Vector(vectCoords[0] * 0.5, vectCoords[1] * 0.5);
                expected = new Vertex(
                        sumArrays(new double[]{vertexCoords[0] * 0.5, vertexCoords[1] * 0.5},
                        new double[]{vectCoords[0] * 0.5, vectCoords[1] * 0.5}));

                assertEquals("Failed on iteration III " + i + "." + j, expected, instance.move(vec));
                j++;
            }
            i++;
        }
        System.out.println("{Forth Asserted}");

        System.out.println("-------------------");
    }

    /**
     * Вспмогательная функция для тестирования. Складывает векторно два массива
     * и возвращает результат. Если количество членов в массивах неравно,
     * возможны ошибки. См. реализацию.
     *
     * @param ar1
     * @param ar2
     * @return
     */
    private double[] sumArrays(final double[] ar1, final double[] ar2) {
        double[] ans = new double[ar1.length];
        for (int i = 0; i < ar1.length; i++) {
            ans[i] = ar1[i] + ar2[i];
        }
        return ans;
    }

    @Ignore
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double angle = 0.0;
        Vertex instance = null;
        Vertex expResult = null;
        Vertex result = instance.rotate(angle);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void testRelocate() {
        System.out.println("relocate");
        Vertex newO = null;
        Vertex instance = null;
        Vertex expResult = null;
        Vertex result = instance.relocate(newO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Тестирование метода преобразования в строку.
     */
    @Test
    public void testToString() {
        Vertex instances[] = new Vertex[]{
            new Vertex(0, 0),
            new Vertex(-5, 33),
            new Vertex(23, 23),
            new Vertex(-14, -14),
            new Vertex(50, -25)
        };
        System.out.println("toString: " + instances.length + " tests");
        String expResult[] = new String[]{
            "(0.0; 0.0)",
            "(-5.0; 33.0)",
            "(23.0; 23.0)",
            "(-14.0; -14.0)",
            "(50.0; -25.0)"
        };
        for (int i = 0; i < instances.length; i++) {
            String result = instances[i].toString();
            assertEquals("'fail at " + Integer.toString(i + 1) + " iteration'", expResult[i], result);
        }
    }

}
