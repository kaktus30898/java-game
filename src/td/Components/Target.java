package td.Components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.geometry.Point2D;

public class Target extends Component {
    private PositionComponent position;
    private Point2D shift;

    public Target(Point2D shift) {
        this.shift = shift;
    }

    public Target(int x, int y) {
        this(new Point2D(x, y));
    }

    public Point2D getTarget() {
        return position.getValue().add(shift);
    }
}
