package td.Components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.geometry.Point2D;

/**
 * Компонент Shift реализует поведение, помогающее прочей логике понимать где находится "центр" объекта,
 * к которому необходимо стремиться при движении.
 * Так же он определяет радиус, в пределах которого рядом с объектом нельзя поставить новую башню.
 */
public class Shift extends Component {
    private PositionComponent position;
    private final Point2D shift;
    private final double radius;

    public Shift(final Point2D shift, final double radius) {
        this.shift = shift;
        this.radius = radius;
    }

    public Shift(double x, double y) {
        this(new Point2D(x, y), Math.sqrt(x*x + y*y));
    }

    public Shift(double radius) {
        this(new Point2D(radius, radius), radius);
    }

    public Point2D getTarget() {
        return position.getValue().add(shift);
    }

    public Point2D getShift() {
        return shift;
    }

    public double getRadius() {
        return radius;
    }
}
