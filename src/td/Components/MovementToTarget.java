package td.Components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class MovementToTarget extends Component {

    public static class MovementPathBuilder {
        private final ArrayList<Object> components = new ArrayList<>();

        public MovementPathBuilder(Entity... targets) {
            for (Entity target : targets)
                add(target);
        }

        public MovementPathBuilder(Point2D... targets) {
            for (Point2D target : targets)
                add(target);
        }

        public void add(Entity target) {
            components.add(target);
        }

        public void add(Point2D target) {
            components.add(target);
        }

        public MovementPath build() {
            final ArrayList<Object> componentsCopy = new ArrayList<>(components.size());
            componentsCopy.addAll(components);
            return new MovementPath(componentsCopy);
        }
    }

    public static class MovementPath {
        private final ArrayList<Object> components;

        private MovementPath(ArrayList<Object> components) {
            this.components = components;
        }

        public Point2D getCurrentTargetPosition() {
            while ((!components.isEmpty()) && (components.get(0) == null))
                components.remove(0);
            if (components.isEmpty())
                return null;
            Object top = components.get(0);
            if (top instanceof Entity) {
                final Entity target = (Entity) top;
                if (!target.isActive()) {
                    components.remove(0);
                    return getCurrentTargetPosition();
                }
                return target.getComponent(Shift.class).getTarget();
            } else if (top instanceof Point2D) {
                return (Point2D) top;
            } else {
                components.remove(0);
                return getCurrentTargetPosition();
            }
        }

        public boolean nextTarget() {
            if (components.isEmpty())
                return false;
            components.remove(0);
            return !components.isEmpty();
        }
    }

    private final MovementPath path;
    private final double speed;
    private boolean done = false;
    private final Runnable onDone;

    public MovementToTarget(MovementPath path, double speed, Runnable onDone) {
        this.path = path;
        this.speed = speed;
        this.onDone = onDone;
    }

    private PositionComponent position;
    private Shift self;

    @Override
    public void onUpdate(double tpf) {
        if (done) return;
        final Point2D targetPosition = path.getCurrentTargetPosition();
        if (targetPosition == null) {
            done = true;
            onDone.run();
            return;
        }
        final Point2D currentPosition = self.getTarget();
        final Point2D differenceVector = targetPosition.subtract(currentPosition);
        if (differenceVector.magnitude() < 2) {
            if (!path.nextTarget()) {
                done = true;
                onDone.run();
            }
        } else {
            final Point2D movementVector = differenceVector.multiply(
                    speed * tpf / differenceVector.magnitude()
            );
            position.translate(movementVector);
        }
    }
}
