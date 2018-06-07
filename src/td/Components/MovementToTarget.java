package td.Components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.geometry.Point2D;

import java.util.ArrayList;

/**
 * Компонент MovementToTarget реализует движение.
 */
public class MovementToTarget extends Component {

    /**
     * Вспомогательный класс MovementPathBuilder нужен для построения пути,
     * по которому будет двигаться объект.
     */
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

        /**
         * @return Построенний объект пути, в который скопированы все точки маршрута.
         */
        public MovementPath build() {
            final ArrayList<Object> componentsCopy = new ArrayList<>(components.size());
            componentsCopy.addAll(components);
            return new MovementPath(componentsCopy);
        }
    }

    /**
     * Вспомогательный класс MovementPath служит для хранения маршрута, по которому движется объект и его состояния.
     */
    public static class MovementPath {
        private final ArrayList<Object> components;

        private MovementPath(ArrayList<Object> components) {
            this.components = components;
        }

        /**
         * @return Положение текущей "цели".
         *      Если "цель" точка - возвращает точку.
         *      Если "цель" сущность - возвращает результат вызова Shift::getTarget().
         *      Если "цель" не активна (удалена из игрового мира) или == null -
         *      удаляет её из списка и проводит проверку заново.
         */
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

        /**
         * Удаляет "цель" из списка для следования к следующей.
         *
         * @return true, если ещё остались цели и false, если список оказался пуст.
         */
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

    /**
     * @param path Путь следования объекта
     * @param speed Скорость движения
     * @param onDone Замыкание, которое будет вызвано при достижении объектом цели
     */
    public MovementToTarget(MovementPath path, double speed, Runnable onDone) {
        this.path = path;
        this.speed = speed;
        this.onDone = onDone;
    }

    private PositionComponent position;
    private Shift self;

    /**
     * Метод onUpdate вызывается движком, чтобы изменить состояние объекта.
     * Внутри вычисляется вектор от объекта до "цели", которому задаётся новая длина, равная tpf * скорость.
     * Затем объект двигается согласно вычисленному вектору.
     * Если объект достиг цели, вызывается замыкание.
     *
     * @param tpf Количество времени, которое прошло с последнего изменения объекта
     */
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
        if (differenceVector.magnitude() < 5) {
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
