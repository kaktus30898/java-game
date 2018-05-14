package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import td.Components.Shift;
import td.Game;
import td.GameContext;
import td.Helpers.List;

public class Tower extends Entity {
    private final GameContext context;

    public Tower(final GameContext context) {
        super();
        this.context = context;

        setType(Game.EntityType.Tower);
        setView(new Circle(15, Color.rgb(0x78, 0x90, 0x9c)));
        addComponent(new Shift(15));

        context.runAtInterval(this::makeShoot, Duration.millis(50));
    }

    public void shoot(Entity target) {
        context.spawn(new Bullet(target), getComponent(Shift.class).getTarget());
    }

    public void makeShoot() {
        final Entity enemy = List.getRandomElement(context.getEntitiesInRange(
                Game.EntityType.Enemy,
                getComponent(Shift.class).getTarget(),
                125
        ));
        if (enemy != null)
            shoot(enemy);
    }
}
