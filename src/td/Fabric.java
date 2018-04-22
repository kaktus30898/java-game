package td;

import com.almasb.fxgl.entity.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Fabric implements EntityFactory {

    private Point2D target;
    private double speed;

    Fabric(Point2D target, double speed) {
        this.target = target;
        this.speed = speed;
    }

    private Entity enemy = null;

    @Spawns("Enemy")
    public Entity SpawnEnemy(SpawnData data) {
        enemy = Entities.builder()
                .type(Game.EntityType.Enemy)
                .at(data.getX() - 10, data.getY() - 10)
                .viewFromNode(new Circle(10, Color.rgb(0xe5, 0x73, 0x73)))
                .with(new Movement(target.subtract(10, 10), speed))
                .build();
        return enemy;
    }

    @Spawns("EnemyFabric")
    public Entity SpawnFabric(SpawnData data) {
        return Entities.builder()
                .type(Game.EntityType.Fabric)
                .at(data.getX() - 25, data.getY() - 25)
                .viewFromNode(new Circle(25, Color.rgb(0xba, 0x68, 0xc8)))
                .build();
    }

    @Spawns("Home")
    public Entity SpawnHome(SpawnData data) {
        return Entities.builder()
                .type(Game.EntityType.Fabric)
                .at(data.getX() - 25, data.getY() - 25)
                .viewFromNode(new Circle(25, Color.rgb(0x7c, 0xb3, 0x42)))
                .build();
    }

    @Spawns("RoadElement")
    public Entity SpawnRoad(SpawnData data) {
        return Entities.builder()
                .type(Game.EntityType.Road)
                .at(data.getX() - 5, data.getY() - 5)
                .viewFromNode(new Rectangle(10, 10, Color.rgb(0x03, 0x9b, 0xe5)))
                .build();
    }

    @Spawns("Tower")
    public Entity SpawnTower(SpawnData data) {
        return Entities.builder()
                .type(Game.EntityType.Fabric)
                .at(data.getX() - 15, data.getY() - 15)
                .viewFromNode(new Circle(15, Color.rgb(0x78, 0x90, 0x9c)))
                .build();
    }

    @Spawns("TowerBullet")
    public Entity SpawnTowerBullet(SpawnData data) {

        return Entities.builder()
                .type(Game.EntityType.Fabric)
                .at(data.getX() - 5, data.getY() - 5)
                .viewFromNode(new Circle(5, Color.rgb(0xff, 0x8a, 0x65)))
                .with(new MovementToTarget(enemy, speed * 2))
                .build();
    }
}
