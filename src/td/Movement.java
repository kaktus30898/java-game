package td;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.geometry.Point2D;

public class Movement extends Component {

    private Point2D target;
    private double speed;

    Movement(Point2D target, double speed) {
        this.target = target;
        this.speed = speed;
    }

    private PositionComponent position;

    @Override
    public void onUpdate(double tpf) {
        final Point2D currentPosition = position.getValue();
        final Point2D differenceVector = target.subtract(currentPosition);
        if (differenceVector.magnitude() < 1) {
//            System.out.println("Movement is done!");
        } else {
            final Point2D movementVector = differenceVector.multiply(
                    speed * tpf / differenceVector.magnitude()
            );
            position.translate(movementVector);
        }
    }
}
