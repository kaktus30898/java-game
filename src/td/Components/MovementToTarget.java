package td.Components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.geometry.Point2D;

public class MovementToTarget extends Component {

    private final Entity target;
    private final double speed;
    private boolean done = false;
    private final Runnable onDone;

    public MovementToTarget(Entity target, double speed, Runnable onDone) {
        this.target = target;
        this.speed = speed;
        this.onDone = onDone;
    }

    private Point2D getTargetPosition() {
        return target.getComponent(Shift.class).getTarget();
    }

    private PositionComponent position;
    private Shift self;

    @Override
    public void onUpdate(double tpf) {
        if (done) return;
        if (!target.isActive()) {
            done = true;
            onDone.run();
            return;
        }
        final Point2D currentPosition = self.getTarget();
        final Point2D differenceVector = getTargetPosition().subtract(currentPosition);
        if (differenceVector.magnitude() < 2) {
            done = true;
            onDone.run();
        } else {
            final Point2D movementVector = differenceVector.multiply(
                    speed * tpf / differenceVector.magnitude()
            );
            position.translate(movementVector);
        }
    }
}
