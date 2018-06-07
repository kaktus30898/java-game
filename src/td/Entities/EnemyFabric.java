package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import td.Components.MovementToTarget.MovementPathBuilder;
import td.Components.Shift;
import td.Game;
import td.GameContext;

/**
 * Сущность "Фабрика врагов".
 * Производит много врагов, здоровье которых постепенно увеличивается.
 */
public class EnemyFabric extends Entity {
    private final GameContext context;
    private final MovementPathBuilder[] pathBuilders;
    private final Home target;
    private double hitPoints = 10;
    private double chanse = 1.0;

    public EnemyFabric(final GameContext context, final MovementPathBuilder[] pathBuilders, final Home target) {
        super();
        this.context = context;
        this.pathBuilders = pathBuilders;
        this.target = target;

        setType(Game.EntityType.Fabric);
        setView(new Circle(25, Color.rgb(0xba, 0x68, 0xc8)));
        addComponent(new Shift(25));

        context.runAtInterval(this::spawnEnemy, Duration.millis(300));
    }

    public void spawnEnemy() {
        if (Math.random() <= chanse) {
            final int road = (int) Math.floor(Math.random() * pathBuilders.length);
            context.spawn(new Enemy(pathBuilders[road].build(), target, (int) hitPoints), getComponent(Shift.class).getTarget());
            hitPoints += 5;
            chanse *= 0.999;
        }
    }
}
