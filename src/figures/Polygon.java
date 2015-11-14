package figures;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Многоугольник. В основе реализации лежит LinkedList из вершин.
 *
 * NB:Не меньше трех точек.
 *
 * NB:testMove() - сдвинулись ли отрезки?
 *
 * NB:Недопустимо иметь в многоугольнике вершину, при которой угол является
 * развернутым.
 *
 * @author Матвей
 */
public class Polygon {

    /**
     * Вершины многоугольника.
     */
    protected LinkedList<Vertex> vertexes;
    /**
     * Кэширующее поле, содержащее стороны многоугольника.
     *
     * XXX: отследить влияние трансформирующих многоугольник методов на
     * содержимое этого поля.
     *
     * NB: изначально поле пустое. Инициализируется вызовом breakToEdges() или
     * getEdges()(который содержит вызов breakToEdges()).
     */
    protected LinkedList<Section> edges;

    /**
     * Кэширующее поле, содержащее запись о выпуклости многоугольника. true,
     * если многоугольник выпуклый; false в противном случае, или если
     * вычисление еще не проводилось. NB: изначально поле имеет значение false.
     * Инициализируется вызовом метода countConvexity() или isConvex()(который
     * содержит вызов countConvexity()).
     *
     * @see #countedConvexity
     */
    protected boolean convex;
    /**
     * Поле указывает, проводилась ли оценка выпуклости многоугольника. false
     * обозначает, что нет. Т.е., поле convex не несет смысловой нагрузки. true
     * указывает, что оценка выпуклости проводилась, и ее результат был
     * кэширован.
     *
     * @see #countedConvexity
     */
    protected boolean countedConvexity;

    /**
     * Создает многоугольник из упорядоченного массива вершин.
     *
     * @param points вершины многоугольника.
     * @throws VertexAmountException свидетельствует о том, что количество точек
     * меньше трех, что недопустимо.
     * @throws VertexPositionException свидетельствует о том, что некоторые
     * стороны имеют нулевую длину, либо при некоторых вершинах угол
     * развернутый.
     */
    public Polygon(Point2D.Double... points) throws VertexAmountException, VertexPositionException {
        this(Arrays.asList(points));
    }

    public Polygon(List<? extends Point2D.Double> points) throws VertexAmountException, VertexPositionException {
        if (points.size() < 3) {
            throw new VertexAmountException("Need at least three vertexes. Found: " + points.size());
        }

        //XXX: отследить безопасность этого куска кода в плане того, что добавленные точки  не мгут подвергнуться внешним изменениям.
        vertexes = new LinkedList<>();
        points.forEach(point -> vertexes.add((Vertex) point));
        vertexes.add((Vertex) points.get(0));

        if (Polygon.positionsCorrupted(vertexes)) {
            throw new VertexPositionException("Shouldn't contain fictive vertexs");
        } /*
         * Проверка вынесена в конец потому, что метод positionsCorrupted()
         * требует, чтобы в конец была добавлена фиктивная точка. Таким образом,
         * если конструктору будет передан набор вершин, в котром первая и
         * последняя точки совпадают, он не будет отбракован.
         */

    }

    /**
     * Возвращает связный список вершин. NB: Последняя и первая точки в нем
     * всегда совпадают. Это сделано для упрощения написания кода.
     *
     * @return
     */
    public LinkedList<Vertex> getVertexes() {
        return (LinkedList) vertexes.clone();
    }

    /**
     * Возвращает связный список отрезков, которые соответствуют его сторонам.
     * Метод поддерживает кэширование. Разбиение на отрезки производится только
     * при вызове данного метода. В последствии, при вызове метода возвращается
     * кэшированный результат.
     *
     * @return
     */
    public LinkedList<Section> getEdges() {
        if (edges == null) {
            breakToEdges();
        }
        return (LinkedList) edges.clone();
    }

    /**
     * Смещение многоугольника на вектор vec. Производится смещение
     * многоугольника и возвращается ссылка на многоугольник (новых копий не
     * создается)
     *
     * @param vec вектор, на который производится смещение.
     * @return ссылка на _этот_ же многоугольник после сдвига.
     */
    public Polygon move(Vector vec) {
        vertexes.forEach(point -> point.move(vec));
        return this;
    }

    /**
     * Поворот многоугольника. Производится поворот многоугольника и
     * возвращается ссылка на многоугольник (новых копий не создается).
     *
     * @param angle угол поворота (в радианах)
     * @return ссылка на _этот_ же многоугольник после поворота.
     */
    public Polygon rotate(double angle) {
        throw new UnsupportedOperationException("NotSupportedYet");
//        return this;
    }

    /**
     * Смещение начала координат в указанную точку. Рассчитываются координаты
     * точек в новой системе координат и возвращается ссылка на многоугольник.
     *
     * @param newO новое начало координат.
     * @return ссылка на _этот_ же многоугольник, размещенный в новой системе
     * координат.
     */
    public Polygon relocate(Vertex newO) {
        throw new UnsupportedOperationException("NotSupportedYet");
//        return this;
    }

    /**
     * Определение того, является ли многоугольник выпуклым. Метод поддерживает
     * кэширование - после первого вычисления результат запоминается.
     *
     * @return выпуклый ли многоугольник?
     */
    public boolean isConvex() {
        if (!countedConvexity) {
            convex = countConvexity();
            countedConvexity = true;
        }
        return convex;
    }

    /**
     * Проверка принадлежности точки заданному многоугольнику. Если точка лежит
     * на границе, то считается,что она _принадлежит_ многоугольнику.
     *
     * @param point проверяемая точка.
     * @return true, если многоугольник содержит точку. В противном случае
     * false.
     */
    public boolean contains(Point2D.Double point) {
        throw new UnsupportedOperationException();
    }

    /**
     * Метод упрощает ручное построение многоугольника через использование
     * паттерна Builder.
     *
     * @return builder многоугольника.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Подсчет выпуклости данного многоугольника. Вызов метода не влияет на кэш.
     *
     * @return
     */
    protected boolean countConvexity() {
        ListIterator<Vertex> itera = vertexes.listIterator();

        Vertex p1 = itera.next();
        Vertex p2 = itera.next();
        Vertex p3 = itera.next();

        Vector v1 = new Vector(p2, p1);
        Vector v2 = new Vector(p2, p3);

        double etalonSign = StrictMath.signum(v1.multiplyVectorly(v2));

        while (itera.hasNext()) {
            p1 = p2;
            p2 = p3;
            p3 = itera.next();

            v1 = new Vector(p2, p1);
            v2 = new Vector(p2, p3);

            double sign = StrictMath.signum(v1.multiplyVectorly(v2));

            if (etalonSign + sign == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Заполняется список сторон многоугольника - кэш.
     */
    protected void breakToEdges() {
        edges = new LinkedList<>();
        ListIterator<Vertex> itera = vertexes.listIterator();
        Vertex p1;
        Vertex p2;
        p1 = itera.next();
        while (itera.hasNext()) {
            p2 = itera.next();
            edges.add(new Section(p1, p2));
            p1 = p2;
        }
    }

    /**
     * Проверка на корректность взаимного расположения точек многоугольника.
     * Проверяется, чтобы никакой угол не был развернутым и никакая сторона не
     * имела нулевую длину. NB: условие "ломаная не является самопересекающейся"
     * не проверяется. Контроль лежит на пользователе.
     *
     * @param points
     * @return
     */
    protected static boolean positionsCorrupted(Collection<? extends Point2D.Double> points) {
        Iterator<? extends Point2D.Double> itera = points.iterator();

        Point2D.Double p1 = itera.next();
        Point2D.Double p2 = itera.next();
        Point2D.Double p3 = itera.next();

        Vector v1 = new Vector(p2, p1);
        Vector v2 = new Vector(p2, p3);

        if (StrictMath.signum(v1.multiplyVectorly(v2)) == 0) {
            return true;
        }

        while (itera.hasNext()) {
            p1 = p2;
            p2 = p3;
            p3 = itera.next();

            v1 = new Vector(p2, p1);
            v2 = new Vector(p2, p3);

            if (StrictMath.signum(v1.multiplyVectorly(v2)) == 0) {
                return true;
            }

        }

        return false;
    }

    /**
     * Реализация паттерна Builder для упрощения построения многоульника
     * вручную. Поддерживается задание вершин как через передачу координат, так
     * и непосредственно объекта-вершины. Класс спроектирован с использванием
     * шаблона "текучего интерфейса" - каждый метод (кроме метода build(),
     * который явдяется терминальным) возвращает текущий объект-builder как
     * результат.
     */
    public static class Builder {

        private Builder() {
        }
        /**
         * Буфер, в котором последовательно накапливаются точки, из которых
         * будет составлен многоугольник.
         */
        protected final LinkedList<Vertex> p = new LinkedList<>();

        /**
         * Добавление в последовательность вершин точки по ее координатам
         * (передаются отдельно).
         *
         * @param x x-координата вершины.
         * @param y y-координата вершины.
         * @return текущий объект-builder.
         */
        public Builder add(double x, double y) {
            p.add(new Vertex(x, y));
            return this;
        }

        /**
         * Добавление в последовательность вершин точки по ее координатам
         * (передаются в виде массива).
         *
         * @param coordinates массив координат вершины.
         * @return текущий объект-builder.
         */
        public Builder add(double[] coordinates) {
            p.add(new Vertex(coordinates[0], coordinates[1]));
            return this;
        }

        /**
         * Добавление в последовательность вершин точки в виде объекта.
         *
         * @param v очередная вершина многоугольника.
         * @return текущий объект-builder.
         */
        public Builder add(Point2D.Double v) {
            p.add((Vertex) v);
            return this;
        }

        /**
         * Терминальная операция - строит многоугольник по накопленным в буфере
         * вершинам.
         *
         * @throws VertexAmountException свидетельствует о том, что количество
         * точек меньше трех, что недопустимо.
         * @throws VertexPositionException свидетельствует о том, что некоторые
         * стороны имеют нулевую длину, либо при некоторых вершинах угол
         * развернутый.
         * @return многоугольник, сконструированный на основе накопленных точек.
         */
        public Polygon build() throws VertexAmountException, VertexPositionException {
            return new Polygon(p);
        }
    }
}
