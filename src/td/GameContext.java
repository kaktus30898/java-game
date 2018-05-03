package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.util.Optional;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import td.Components.Shift;

import java.util.Iterator;
import java.util.List;

public class GameContext {
    final private GameApplication app;

    public GameContext(final GameApplication app) {
        this.app = app;
    }

    private static void correctPosition(final Entity entity, double x, double y) {
        final Optional<Shift> shiftOpt = entity.getComponentOptional(Shift.class);
        if (shiftOpt.isPresent()) {
            final Point2D shift = shiftOpt.get().getShift();
            x -= shift.getX();
            y -= shift.getY();
        }
        entity.setPosition(x, y);
    }

    public void spawn(final Entity entity, final double x, final double y) {
        correctPosition(entity, x, y);
        app.getGameWorld().addEntity(entity);
    }

    public void spawn(final Entity entity, final Point2D position) {
        correctPosition(entity, position.getX(), position.getY());
        app.getGameWorld().addEntity(entity);
    }

    public void runAtInterval(final Runnable action, final Duration interval) {
        app.getMasterTimer().runAtInterval(action, interval);
    }

    public List<Entity> getEntitiesByType(final Enum<?>... types) {
        return app.getGameWorld().getEntitiesByType(types);
    }

    public List<Entity> getEntitiesInRange(final Enum<?> type, final Point2D center, final double radius) {
        final List<Entity> result = getEntitiesByType(type);
        for (final Iterator<Entity> it = result.iterator(); it.hasNext(); ) {
            final double distance = it.next()
                    .getComponent(Shift.class)
                    .getTarget()
                    .distance(center);
            if (distance > radius)
                it.remove();
        }
        return result;
    }
}
