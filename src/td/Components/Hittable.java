package td.Components;

import com.almasb.fxgl.entity.component.Component;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Компонент Hittable реализует поведение объекта, который можно "ударить".
 *
 * Принимает при создании начальный уровень здоровья
 * и замыкание, которое будет вызвано, когда у текущего объекта закончится здоровье.
 */
public class Hittable extends Component {
    private final SimpleIntegerProperty hitPoints;
    private boolean isAlive = true;
    private final Runnable onDead;

    public Hittable(final int hitPoints, Runnable onDead) {
        this.hitPoints = new SimpleIntegerProperty(hitPoints);
        this.onDead = onDead;
    }

    /**
     * Публичный метод hit нужен для того, чтобы сообщать объекту о том, что его кто-то "ударил".
     * Когда очки здоровья падают до 0 или ниже, объект считается "мёртвым" и вызывается соотв. замыкание.
     * "Бить" мёртвый объект можно, но это не произведёт никакого эффекта.
     *
     * @param decrement Количество очков здоровья, которое теряет текущий объект.
     */
    public void hit(int decrement) {
        if (!isAlive) return;
        final int newValue = hitPoints.get() - decrement;
        hitPoints.set(newValue);
        if (newValue <= 0) {
            hitPoints.set(0);
            isAlive = false;
            onDead.run();
        }
    }

    /**
     * @return Количество оставшихся очков здоровья объекта
     */
    public SimpleIntegerProperty hitPoints() { return hitPoints; }
}
