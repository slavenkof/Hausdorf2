package figures;

import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Класс представляет вектор на плоскости. XXX: безопасность геттеров - возможно
 * ли с их помощью изменить координаты?
 *
 * @author Матвей
 */
public class Vector implements Cloneable {

    /**
     * x-координата вектора.
     */
    protected double x;
    /**
     * y-координата вектора.
     */
    protected double y;

    /**
     * Поле, хранящее начало вектора. Если вектор является фиксированным,
     * хранится непосредственно начало вектора. В противном случае значение поля
     * не определено и может быть любым.
     */
    protected Vertex start;
    /**
     * Поле, хранящее конец вектора. Если вектор является фиксированным,
     * хранится непосредственно конец вектора. В противном случае значение поля
     * не определено и может быть любым.
     */
    protected Vertex end;

    /**
     * Переменная, хранящая информацию о типе вектора. Вектор называется
     * фиксированным, если для него заданы начальная и конечная точка. Если
     * вектор фиксированный - то поле имеет значение {@code true}. В противном
     * случае вектор является свободным, поле имеет значение {@code false}.
     */
    protected boolean bounded;

    /**
     * Кэширующее поле, содержащее длину вектора. Изначально значение равно
     * нулю. Инициализируется вызовом метода {@code length()} или
     * {@code countLength()}, после чего длина не рассчитывается заново.
     *
     * @see #countedLength
     */
    protected double length;
    /**
     * Поле указывает, проводилась ли оценка длина вектора. {@code false}
     * обозначает, что нет. Т.е., поле {@code length} не несет смысловой
     * нагрузки. {@code true} указывает, что оценка проводилась, и ее результат
     * был кэширован.
     *
     * @see #length
     */
    protected boolean countedLength;

    /**
     * Кэширующее поле, содержащее квадрат длины вектора. Изначально значение
     * равно нулю. Инициализируется вызовом методов
     * {@code length()}, {@code countLength()}, {@code sqLength()} или
     * {@code countSqLength()}, после чего длина не рассчитывается заново.
     *
     * @see #countedSqLength
     */
    protected double sqLength;
    /**
     * Поле указывает, проводилась ли оценка квадрата длины вектора.
     * {@code false} обозначает, что нет. Т.е., поле {@code sqLength} не несет
     * смысловой нагрузки. {@code true} указывает, что оценка проводилась, и ее
     * результат был кэширован.
     *
     * @see #sqLength
     */
    protected boolean countedSqLength;

    /**
     * Базовый конструктор объекта. Создает свободный вектор по переданным
     * координатым.
     *
     * @param x первая координата вектора.
     * @param y вторая координата вектора.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Конструктор, сходный с конструктором {@code Vector(double x, double y)}.
     * Единственное различие заключается в том, что координаты принимаются в
     * виде массива. Для конструирования вектора используются только первые два
     * числа в массиве. Остальные числа игнорируются. Созданный вектор является
     * свободным.
     *
     * @param coordinates координаты вектора, обернутые в массив.
     */
    public Vector(double coordinates[]) {
        this(coordinates[0], coordinates[1]);
    }

    /**
     * Конструирование фиксированного вектора по начальной и конечной точкам.
     * Координаты высчитываются автоматически.
     *
     * @param start начало вектора.
     * @param end конец вектора.
     */
    public Vector(Point2D.Double start, Point2D.Double end) {
        this.x = end.getX() - start.getX();
        this.y = end.getY() - start.getY();
        this.start = (Vertex) start;
        this.end = (Vertex) end;
    }

    /**
     * Возвращает x-координату вектора.
     *
     * @return x-координата вектора.
     */
    public double getX() {
        return x;
    }

    /**
     * Возвращает y-координату вектора.
     *
     * @return y-координата вектора.
     */
    public double getY() {
        return y;
    }

    /**
     * Возвращает значение поля {@code start}. В случае, если вектор
     * фиксированный, то это начало вектора.
     *
     * @see #start
     * @return значение поля {@code start}. Если вектор фиксированный, то это
     * начало вектора. В противном случае возвращаемое значение не определено, и
     * может быть, в том числе, равно {@code null}.
     */
    public Vertex getStart() {
        return start;
    }

    /**
     * Возвращает значение поля {@code end}. В случае, если вектор
     * фиксированный, то это начало вектора.
     *
     * @see #end
     * @return значение поля {@code end}. Если вектор фиксированный, то это
     * начало вектора. В противном случае возвращаемое значение не определено, и
     * может быть, в том числе, равно {@code null}.
     */
    public Vertex getEnd() {
        return end;
    }

    /**
     * Метод выполняет расчет длины вектора, результат кэшируется. Кроме того,
     * если квадрат длины еще не кэширован, проводится и его кэширование. Метод
     * поддерживает шаблон "текучий интерфейс".
     *
     * @see #countedSqLength
     * @see #sqLength
     * @see #countedLength
     * @see #length
     * @return возвращает этот же объект, тем самым обеспечивая поддержку
     * "текучего интерфейса".
     */
    public Vector countLength() {
        if (!countedLength) {
            if (!countedSqLength) {
                countSqLength();
            }
            length = Math.sqrt(sqLength);
            countedLength = true;
        }
        return this;
    }

    /**
     * Обеспечивает доступ к полю {@code countedLength}.
     *
     * @see #countedLength
     * @return значение поля {@code countedLength}.
     */
    public boolean countedLength() {
        return countedLength;
    }

    /**
     * Обеспечивает доступ к значению квадрата длины вектора. Метод поддерживает
     * кэширование: значение рассчитывается при первом вызове и сохраняется. При
     * последующих вызовах значение берется из кэша. Кроме того, выполняются
     * расчеты и кэширование квадрат длины вектора. Соответственно, заполняются
     * поля {@code sqLength} и {@code length}.
     *
     * @see #sqLength
     * @see #countedSqLength
     * @see #countSqLength()
     * @see #countLength()
     * @return длина вектора.
     */
    public double length() {
        if (!countedLength) {
            countLength();
        }
        return length;
    }

    /**
     * Метод выполняет расчет квадрата длины вектора, результат кэшируется.
     * Метод поддерживает шаблон "текучий интерфейс".
     *
     * @see #countedSqLength
     * @see #sqLength
     * @return возвращает этот же объект, тем самым обеспечивая поддержку
     * "текучего интерфейса".
     */
    public Vector countSqLength() {
        if (!countedSqLength) {
            sqLength = x * x + y * y;
            countedSqLength = true;
        }
        return this;
    }

    /**
     * Обеспечивает доступ к полю {@code countedSqLength}.
     *
     * @see #countedSqLength
     * @return значение поля {@code countedSqLength}.
     *
     */
    public boolean countedSqLength() {
        return countedSqLength;
    }

    /**
     * Обеспечивает доступ к значению квадрата длины вектора. Метод поддерживает
     * кэширование: значение рассчитывается при первом вызове и сохраняется. При
     * последующих вызовах значение берется из кэша. Кэш также может быть
     * заполнен при вызове методов
     * {@code countSqLength()}, {@code countLength()} или {@code length()}. В
     * этом случае значение также повторно не рассчитывается.
     *
     * @see #sqLength
     * @see #countedSqLength
     * @see #countSqLength()
     * @see #length()
     * @see #countLength()
     * @return квадрат длины вектора.
     */
    public double sqLength() {
        if (!countedSqLength) {
            countSqLength();
        }
        return sqLength;
    }

    /**
     * Операция векторного сложения. В качестве слагаемого допускается
     * использовать только свободные вектра. В противном случае выбрасывается
     * {@code UnsupportedOperationException}. Допустимо прибавлять к
     * фиксированному вектору какие-либо другие вектора.
     *
     * @param v вектор-слагаемое.
     * @return этот же вектор после операции сложения.
     * @throws UnsupportedOperationException в случае, если в качестве второго
     * слагаемого используется фиксированный вектор.
     */
    public Vector sumWith(Vector v) throws UnsupportedOperationException {
        if (v.isBounded()) {
            throw new UnsupportedOperationException("The second vector is bounded.");
        }
        x += v.x;
        y += v.y;
        if (isBounded()) {
            end.move(v);
        }
        return this;
    }

    /**
     * Умножение вектора на заданное число. Возможно только в том случае, если
     * вектор свободный. В противном случае UnsupportedOperationException.
     *
     * @param n множитель.
     * @return этот же вектор, умноженный на заданное число.
     * @throws UnsupportedOperationException в случае, если вектор фиксирован.
     */
    public Vector nMultiply(double n) throws UnsupportedOperationException {
        if (bounded) {
            throw new UnsupportedOperationException("Vector is bounded");
        }
        x *= n;
        y *= n;
        return this;
    }

    /**
     * Обращает вектор (производит умножение на -1). Метод поддерживает как
     * ограниченные, так и свободные вектора.
     *
     * @return этот же вектор после обращения.
     */
    public Vector swap() {
        x *= -1;
        y *= -1;
        if (isBounded()) {
            Vertex p = getStart();
            start = getEnd();
            end = p;
        }
        return this;
    }

    /**
     * Превращает данный вектор в ограниченный, откладывая его от переданной
     * точки. В случае, если вектор уже ограниченный, то просто заменяются его
     * начальная и конечная точки.
     *
     * @param start точка, от которой откладывается вектор.
     * @return этот же вектор, отложенный от заданной точки.
     */
    public Vector bound(Point2D.Double start) {
        this.start = (Vertex) start;
        end = this.start.clone().move(this);
        bounded = true;
        return this;
    }

    /**
     * Метод позволяет узнать тип вектора.
     *
     * @return {@code true}, если вектор фиксированный. {@code false} в
     * противном случае.
     */
    public boolean isBounded() {
        return bounded;
    }

    /**
     * Использование данного метода превращает вектор в свободный,
     * соответствующие поля освобождаются.
     *
     * @return этот же вектор, с освобожденными полями начальной и конечной
     * точки.
     */
    public Vector unbound() {
        start = null;
        end = null;
        bounded = false;
        return this;
    }

    /**
     * Нормирует вектор, то есть делит на собственную длину. Применимо только
     * для свобобных векторов.
     *
     * @return этот же вектор после нормирования.
     * @throws UnsupportedOperationException в случае, если вектор фиксирован.
     */
    public Vector normalize() throws UnsupportedOperationException {
        if (bounded) {
            throw new UnsupportedOperationException("Vector is bounded");

        }
        nMultiply(1 / length());
        return this;

    }

    /**
     * Псевдовекторное произведение.
     *
     * @param vector множитель.
     * @return псевдовекторное произведение векторов.
     */
    public double multiplyVectorly(Vector vector) {
        double a = this.getX() * vector.getY();
        double b = -this.getY() * vector.getX();
        return a + b;
    }

    /**
     * Возвращает точку, которой соответствует данный вектор (как
     * радиус-вектор). Т.е. координаты точки и вектора будут совпадать.
     *
     * @return точка, которой соответствует данный радиус-вектор.
     */
    public Vertex toVertex() {
        return new Vertex(getX(), getY());
    }

    /**
     * Метод возвращает координаты вектора в текстовом формате. Координаты
     * заключены в фигурные скобки и разделены точкой с запятой.
     *
     * @return координаты вектора в строковом представлении.
     */
    @Override
    public String toString() {
        return "{" + getX() + "; " + getY() + "}";
    }

    /**
     * Проверка совпадения векторов. Проводится следующим образом. Проверяются
     * координаты векторов. У векторов должны совпадать значения полей
     * <code>bounded</code>. Если вектора свободные, то есть
     * <code>bounded == false</code>, то вектора считаются совпадающими. В
     * противном случае проводится также проверка совпадения их начальных и
     * конечных точек.
     *
     * @param obj объект для сравнения.
     * @return совпадают ли вектора. Учитывается тип векторов ((un)bounded), их
     * координаты, и, в случае bounded, координаты начала и конца векторов.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector other = (Vector) obj;
        if (Double.doubleToLongBits(this.getX()) != Double.doubleToLongBits(other.getX())) {
            return false;
        }
        if (Double.doubleToLongBits(this.getY()) != Double.doubleToLongBits(other.getY())) {
            return false;
        }

        if (this.isBounded() == other.isBounded()) {
            if (!isBounded()) {
                return true;
            }
            if (!Objects.equals(this.start, other.start)) {
                return false;
            }
            if (!Objects.equals(this.end, other.end)) {
                return false;
            }
        }
        return true;
    }

    /**
     * При вычислении хэш-кода начальная и конечная точка учитываются только в
     * том случае, если вектор фиксирован.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.getX()) ^ (Double.doubleToLongBits(this.getX()) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.getY()) ^ (Double.doubleToLongBits(this.getY()) >>> 32));
        if (isBounded()) {
            hash = 19 * hash + Objects.hashCode(this.getStart());
            hash = 19 * hash + Objects.hashCode(this.getEnd());
        }
        hash = 19 * hash + (this.isBounded() ? 1 : 0);
        return hash;
    }

    /**
     * Производит глубокое копирование объекта.
     *
     * @return копия текущего объекта.
     * @throws CloneNotSupportedException
     */
    @Override
    public Vector clone() throws CloneNotSupportedException {
        Vector clone = (Vector) super.clone();
        clone.start = start.clone();
        clone.end = end.clone();
        return clone;
    }
}
