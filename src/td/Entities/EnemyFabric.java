package td.Entities;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import td.Game;

public class EnemyFabric {
    private static Entity SpawnFabric(GameWorld world, int x, int y) {
        return Entities.builder()
                .type(Game.EntityType.Fabric)
                .at(x - 25, y - 25)
                .viewFromNode(new Circle(25, Color.rgb(0xba, 0x68, 0xc8)))
                .buildAndAttach(world);
    }

    private GameWorld world;
    private Entity entity;

    public EnemyFabric(GameWorld world, int x, int y) {
        this.world = world;
        this.entity = SpawnFabric(world, x, y);
    }

    public Point2D getCenter() {
        return entity.getPosition()
                .add(25, 25);
    }

    public Entity spawnEnemy(Home target) {
        final EnemyFabric fabric = this;
        final Point2D initialPosition = getCenter();
        return Enemy.SpawnEnemy(world,
                (int) initialPosition.getX(),
                (int) initialPosition.getY(),
                target
        );
    }
}
