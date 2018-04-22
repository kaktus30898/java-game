package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Game extends GameApplication {
    public enum EntityType {
        Road,
        Enemy,
        Fabric,
        TowerPosition,
        Tower,
        TowerBullet,
        Home,
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(576);
        settings.setTitle("TD");
        settings.setVersion("0.0.0");
    }

    @Override
    protected void initGame() {
        GameWorld world = getGameWorld();
        world.addEntityFactory(new Fabric(
                new Point2D(500, 100),
                50
        ));
        for (int i = 100; i <= 500; i+= 10)
            world.spawn("RoadElement", i, 100);
        world.spawn("EnemyFabric", 100, 100);
        world.spawn("Home", 500, 100);
        world.spawn("Enemy", 100, 100);
        world.spawn("Tower", 250, 150);
        world.spawn("TowerBullet", 250, 150);
    }

}
