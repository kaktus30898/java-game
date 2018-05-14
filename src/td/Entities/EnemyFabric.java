package td.Entities;

import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import td.Components.MovementToTarget.MovementPathBuilder;
import td.Components.Shift;
import td.Game;
import td.GameContext;

public class EnemyFabric extends Entity {
    private final GameContext context;
    private final MovementPathBuilder pathBuilder;
    private final Home target;

    public EnemyFabric(final GameContext context, final MovementPathBuilder pathBuilder, final Home target) {
        super();
        this.context = context;
        this.pathBuilder = pathBuilder;
        this.target = target;

        setType(Game.EntityType.Fabric);
        setView(new Circle(25, Color.rgb(0xba, 0x68, 0xc8)));
        addComponent(new Shift(25));

        context.runAtInterval(this::spawnEnemy, Duration.millis(500));
    }

    public void spawnEnemy() {
        context.spawn(new Enemy(pathBuilder.build(), target), getComponent(Shift.class).getTarget());
    }
}
