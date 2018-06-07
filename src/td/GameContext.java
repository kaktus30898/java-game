package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.util.Optional;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import td.Components.Shift;

import java.util.Iterator;
import java.util.List;

/**
 * Класс GameContext служит помощников в доступе к игровому миру.
 */
public class GameContext {
    final private GameApplication app;

    public GameContext(final GameApplication app) {
        this.app = app;
    }

    /**
     * Изменяет положение сущности.
     * Если к сущности прикреплён компонент Shift, то учтёт и сдвиг, указаный в нём.
     *
     * @param entity Сущность.
     * @param x Новое значение координаты x
     * @param y Новое значение координаты y
     */
    private static void correctPosition(final Entity entity, double x, double y) {
        final Optional<Shift> shiftOpt = entity.getComponentOptional(Shift.class);
        if (shiftOpt.isPresent()) {
            final Point2D shift = shiftOpt.get().getShift();
            x -= shift.getX();
            y -= shift.getY();
        }
        entity.setPosition(x, y);
    }

    /**
     * Размещает сущность в игровом мире, предварительно изменив её положение функцией correctPosition.
     * @param entity Сущность.
     * @param x Значение координаты x
     * @param y Значение координаты y
     */
    public void spawn(final Entity entity, final double x, final double y) {
        correctPosition(entity, x, y);
        app.getGameWorld().addEntity(entity);
    }

    /**
     * Размещает сущность в игровом мире, предварительно изменив её положение функцией correctPosition.
     * @param entity Сущность.
     * @param position Координаты.
     */
    public void spawn(final Entity entity, final Point2D position) {
        correctPosition(entity, position.getX(), position.getY());
        app.getGameWorld().addEntity(entity);
    }

    /**
     * Добавляет к игровому таймеру новое событие.
     * @param action Событие, которое будет вызываться.
     * @param interval Интервал, с которым будет вызываться событие.
     */
    public void runAtInterval(final Runnable action, final Duration interval) {
        app.getMasterTimer().runAtInterval(action, interval);
    }

    /**
     * Ищет в игровом мире все сущности с данными типами.
     * @param types Тип сущности.
     * @return Список найденых сущностей.
     */
    public List<Entity> getEntitiesByType(final Enum<?>... types) {
        return app.getGameWorld().getEntitiesByType(types);
    }

    /**
     * Ищет в игровом мире все сущности с данным типом, которые располагаются около указанной точки.
     * @param type Тип сущности.
     * @param center Точка, около которой ведётся поиск.
     * @param radius Максимальное расстояние от сущнности до точки.
     * @return Список найденых сущностей.
     */
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

    /**
     * Вычисляет "сдвиг", которые необходимо совершить,
     * чтобы избежать столкновения с уже имеющимися в мире сущностями.
     *
     * @param position Текущее положение предполагаемой новой сущности.
     * @param radius Её предполагаемый радиус, который она займёт.
     * @param stepSize Размер шага. Модуль возвращаемого вектора не может превышать это значение.
     * @return  Вектор, на который необходимо сдвигуть сущность.
     *          Null, если сдвиг не требуется.
     */
    public Point2D calcShiftForSafeSpawn(final Point2D position, final double radius, final double stepSize) {
        List<Entity> collisions = app.getGameWorld().getEntitiesByComponent(Shift.class);
        for (final Iterator<Entity> it = collisions.iterator(); it.hasNext(); ) {
            final Shift shift = it.next().getComponent(Shift.class);
            final double distance = shift.getTarget().distance(position) - shift.getRadius();
            if (distance > radius)
                it.remove();
        }
        if (collisions.isEmpty())
            return null;
        Point2D result = new Point2D(0, 0);
        for (Entity collision : collisions) {
            result = result.add(position.subtract(collision.getComponent(Shift.class).getTarget()));
        }
        final double magnitude = result.magnitude();
        if (magnitude > stepSize)
            return result.multiply(stepSize / result.magnitude());
        else
            return result;
    }

    /**
     * Вычисляет "сдвиг", которые необходимо совершить,
     * чтобы избежать столкновения с уже имеющимися в мире сущностями.
     *
     * @param position Текущее положение предполагаемой новой сущности.
     * @param radius Её предполагаемый радиус, который она займёт.
     * @return  Вектор, на который необходимо сдвигуть сущность.
     *          Null, если сдвиг не требуется.
     */
    public Point2D calcShiftForSafeSpawn(final Point2D position, final double radius) {
        return calcShiftForSafeSpawn(position, radius, 10);
    }

    /**
     * Вычисляет положение рядом с точкой такое,
     * чтобы избежать столкновения с уже имеющимися в мире сущностями.
     *
     * @param position Начальное положение предполагаемой новой сущности.
     * @param radius Её предполагаемый радиус, который она займёт.
     * @return  Положение, на котором будет безопасно разместить новую сущность.
     *          Null, если такое положение не было найдено.
     */
    public Point2D findPlaceToSafeSpawn(Point2D position, final double radius) {
        for (int i = 0; i < 1000; i++) {
            Point2D step = calcShiftForSafeSpawn(position, radius);
            if ((step == null) || (step.magnitude() < 1))
                return position;
            position = position.add(step);
        }
        return null;
    }
}
