package td.Components;

import com.almasb.fxgl.entity.component.Component;

public class Hittable extends Component {
    private int hitPoints = 10;
    private boolean isAlive = true;
    private Runnable onDead;

    public Hittable(Runnable onDead) {
        this.onDead = onDead;
    }

    public void hit(int decrement) {
        if (!isAlive) return;
        hitPoints -= decrement;
        if (hitPoints <= 0) {
            isAlive = false;
            onDead.run();
        }
    }
}
