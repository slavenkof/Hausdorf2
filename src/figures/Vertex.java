package figures;

import java.awt.geom.Point2D;

/**
 * Класс представляет вершину многоугольника. Включены необходимые в рамках
 * данного проекта методы: параллельный перенос на заданный вектор, поворот,
 * изменение начала координат. Спроектирован с использованием шаблона "текучий
 * интерфейс" - методы, изменяющие положение точки в пространстве, во-первых,
 * изменяют саму точку (новых объектов не создается), во-вторых, возвращают в
 * качестве результата эту же точку.
 *
 * XXX: поле name и метод toString();
 *
 * @author Матвей
 */
public class Vertex extends Point2D.Double implements Cloneable {

    private static final long serialVersionUID = 1L;

    /**
     * Поле, хранящее "имя" вершины многоугольника.
     */
    protected String name;

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
     * Конструктор вершины многоугольника. Для конструирования используются
     * только первые две координаты, остальные игнорируются.
     *
     * @param coordinates координаты вершины.
     */
    public Vertex(double coordinates[]) {
        this(coordinates[0], coordinates[1]);
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
     * Возвращает имя, присвоенное вершине.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Присвоить имя данной вершине. Поддерживает шаблон "текучего интерфейса".
     *
     * @param name имя, которое надлежит присвоить вершине
     * @return возвращает этот же объект, тем самым обеспечивая поддержку
     * "текучего интерфейса".
     */
    public Vertex setName(String name) {
        this.name = name;
        return this;
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
     * Параллельный перенос точки на заданный вектор. Поддерживает шаблон
     * "текучего интерфейса".
     *
     * @param vec вектор, на который осуществляется параллельный перенос.
     * @return возвращает этот же объект после смещения, тем самым обеспечивая
     * поддержку "текучего интерфейса".
     */
    public Vertex move(Vector vec) {
        x += vec.getX();
        y += vec.getY();
        return this;
    }

    /**
     * Поворот точки на заданный угол вокруг начала координат. Поддерживает
     * шаблон "текучего интерфейса".
     *
     * @param angle угол поворота в радианах.
     * @return возвращает этот же объект после поворота, тем самым обеспечивая
     * поддержку "текучего интерфейса".
     */
    public Vertex rotate(double angle) {
        throw new UnsupportedOperationException("NotSupportedYet");
//        return this;
    }

    /**
     * Смещение начала координат в указанную точку. Поддерживает шаблон
     * "текучего интерфейса".
     *
     * @param newO новое начало координат.
     * @return возвращает этот же объект размещенныйв новой системе координат,
     * тем самым обеспечивая поддержку "текучего интерфейса".
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
