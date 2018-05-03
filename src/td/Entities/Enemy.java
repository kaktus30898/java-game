package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Components.Hittable;
import td.Components.MovementToTarget;
import td.Components.Shift;
import td.Game;

public class Enemy extends Entity {
    public Enemy(final Home target) {
        super();

        setType(Game.EntityType.Enemy);
        setView(new Circle(10, Color.rgb(0xe5, 0x73, 0x73)));
        addComponent(new Shift(10));
        addComponent(new MovementToTarget(
                target,
                60,
                () -> {
                    if (target.isActive())
                        target.getComponent(Hittable.class).hit(1);
                    removeFromWorld();
                }
        ));
        addComponent(new Hittable(this::removeFromWorld));
    }
}
