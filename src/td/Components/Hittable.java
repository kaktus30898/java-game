package td.Components;

import com.almasb.fxgl.entity.component.Component;
import javafx.beans.property.SimpleIntegerProperty;

public class Hittable extends Component {
    private final SimpleIntegerProperty hitPoints = new SimpleIntegerProperty(10);
    private boolean isAlive = true;
    private final Runnable onDead;

    public Hittable(Runnable onDead) {
        this.onDead = onDead;
    }

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

    public SimpleIntegerProperty hitPoints() { return hitPoints; }
}
