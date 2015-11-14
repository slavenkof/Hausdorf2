package figures;

import java.awt.geom.Point2D;

/**
 * Класс представляет вершину многоугольника. Включены необходимые в рамках
 * данного проекта методы: параллельный перенос на заданный вектор, поворот,
 * изменение начала координат. Спроектирован с использованием шаблона "текучий
 * интерфейс" - методы, изменяющие положение точки в пространстве, во-первых,
 * изменяют саму точку (новых объектов не создается), во-вторых, возвращают в
 * качестве эту же точку.
 *
 * @author Матвей
 */
public class Vertex extends Point2D.Double implements Cloneable {

    private static final long serialVersionUID = 1L;

    /**
     * Базовый конструктор вершины многоугольника. Принимает два аргумента -
     * координаты вершины.
     *
     * @param x x-координата вершины.
     * @param y y-координата вершины.
     */
    public Vertex(double x, double y) {
        super(x, y);
    }

    /**
     * Конструктор, создающий вершину на основе имеющейся точки. XXX: проверить,
     * насколько безопасен данный конструктор относительно вызова метода
     * setLocation() для базовой точки.
     *
     * @param point точка, которую требуется преобразовать в вершину.
     */
    public Vertex(Point2D point) {
        super();
        this.setLocation(point);
    }

    /**
     * Метод для получения радиус-вектора точки.
     *
     * @return радиус-вектор точки.
     */
    public Vector toVector() {
        return new Vector(x, y);
    }

    /**
     * Параллельный перенос точки на заданный вектор.
     *
     * @param vec вектор, на который осуществляется параллельный перенос.
     * @return эта же точка после переноса.
     */
    public Vertex move(Vector vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }

    /**
     * Поворот точки на заданный угол вокруг начала координат.
     *
     * @param angle угол поворота в радианах.
     * @return эта же точка после поворота.
     */
    public Vertex rotate(double angle) {
        throw new UnsupportedOperationException("NotSupportedYet");
//        return this;
    }

    /**
     * Смещение начала координат в указанную точку.
     *
     * @param newO новое начало координат.
     * @return точка, размещенная в новой системе координат.
     */
    public Vertex relocate(Vertex newO) {
        throw new UnsupportedOperationException("NotSupportedYet");
//        return this;
    }

    /**
     * Создает новый объект этого же класса с таким же содержимым.
     *
     * @return копия этого экземпляра.
     */
    @Override
    public Vertex clone() {
        return (Vertex) super.clone();
    }

    /**
     * Возвращает строковое представление точки в виде <code>(x; y)</code>.
     *
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return "(" + java.lang.Double.toString(x) + "; " + java.lang.Double.toString(y) + ")";
    }
}
