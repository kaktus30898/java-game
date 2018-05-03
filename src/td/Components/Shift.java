package td.Components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.geometry.Point2D;

public class Shift extends Component {
    private PositionComponent position;
    private final Point2D shift;

    public Shift(Point2D shift) {
        this.shift = shift;
    }

    public Shift(double x, double y) { this(new Point2D(x, y)); }

    public Shift(double radius) { this(radius, radius); }

    public Point2D getTarget() {
        return position.getValue().add(shift);
    }

    public Point2D getShift() { return shift; }
}
