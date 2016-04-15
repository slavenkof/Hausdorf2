package figures;

import java.awt.geom.Point2D;
import java.util.Objects;

/**
 *
 * Класс представляет отрезок. Включены необходимые в рамках данного проекта
 * методы: параллельный перенос на заданный вектор, поворот, изменение начала
 * координат. Спроектирован с использованием шаблона "текучий интерфейс" -
 * методы, изменяющие положение трезка в пространстве, во-первых, изменяют сам
 * отрезок (новых объектов не создается), во-вторых, возвращают в качестве
 * результата ссылку на этот же отрезок.
 *
 * @author Матвей
 */
public class Section {

    /**
     * Первая граничная точка отрезка.
     */
    protected Vertex a;
    /**
     * Вторая граничная точка отрезка.
     */
    protected Vertex b;

    /**
     * Создание отрезка на основе двух вершин.
     *
     * @param a - первый конец.
     * @param b - второй конец.
     */
    public Section(Point2D.Double a, Point2D.Double b) {
        this.a = (Vertex) a;
        this.b = (Vertex) b;
    }

    /**
     * Создание отрезка на основе начальной точки и направляющего вектора.
     *
     * @param start начальная точка.
     * @param vec направляющий вектор.
     */
    public Section(Point2D.Double start, Vector vec) {
        a = new Vertex(start);
        b = new Vertex(getA().clone().move(vec));
    }

    /**
     * Метод для параллельного переноса отрезка на указанный вектор.
     * Поддерживает шаблон "текучего интерфейса".
     *
     * @param vec вектор, на который осуществляется перенос.
     * @return возвращает этот же объект после смещения, тем самым обеспечивая
     * поддержку "текучего интерфейса".
     */
    public Section move(Vector vec) {
        getA().move(vec);
        b.move(vec);
        return this;
    }

    Vector direction() {
        throw new UnsupportedOperationException("NotSupportedYet");
//        return null;
    }

    /**
     * Поворот отрезка в положительном направлении (против часовой стрелки) на
     * указанный угол. Поддерживает шаблон "текучего интерфейса".
     *
     * @param phi угол поворота в радианах.
     * @return возвращает этот же объект после поворота, тем самым обеспечивая
     * поддержку "текучего интерфейса".
     */
    public Section rotate(double phi) {
        getA().rotate(phi);
        b.rotate(phi);
        return this;
    }

    /**
     * Смещение начала координат в указанную точку. Поддерживает шаблон
     * "текучего интерфейса".
     *
     * @param newO
     * @return возвращает этот же объект размещенный в новой системе координат,
     * тем самым обеспечивая поддержку "текучего интерфейса".
     */
    Section relocate(Vertex newO) {
        getA().relocate(newO);
        b.relocate(newO);
        return this;
    }

    /**
     * Возвращает строковое представление трезка в виде <code>[(x1; y1) <-> (x2;
     * y2)]</code>.
     *
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return "[" + getA().toString() + " <-> " + getB().toString() + "]";
    }

    /**
     * Проверка идентичности двух отрезков. Если граничные точки отрезков
     * совпадают, то отрезки считаются идентичными.
     *
     * @param obj объект для сравнения.
     * @return <code>true</code>, если граничные точки совпадают. Во всех
     * остальных случаях <code>false</code>.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Section) {
            final Section sect = (Section) obj;
            return (a.equals(sect.a) && b.equals(sect.b)
                    || b.equals(sect.a) && a.equals(sect.b));
        }
        return super.equals(obj);
    }

    /**
     * Вычисление хэша для данного отрезка. Два отрезка считаются идентичными,
     * если граничные точки совпадают.
     *
     * @return хэш.
     */
    @Override
    public int hashCode() {
        return 7 * 83 + a.hashCode() + b.hashCode();
    }

    /**
     * Доступ к первй граничной точке.
     *
     * @return первая граничная точка.
     */
    public Vertex getA() {
        return a;
    }

    /**
     * Доступ ко второй граничной точке.
     *
     * @return вторая граничная точка.
     */
    public Vertex getB() {
        return b;
    }

    /**
     * Установка значения первой граничной точки.
     *
     * @param a точка для установки.
     */
    public void setA(Vertex a) {
        this.a = a;
    }

    /**
     * Установка значения второй граничной точки.
     *
     * @param b точка для установки.
     */
    public void setB(Vertex b) {
        this.b = b;
    }

}
