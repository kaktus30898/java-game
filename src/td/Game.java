package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.settings.GameSettings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import td.Entities.Enemy;
import td.Entities.Home;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

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
        settings.setWidth(600);
        settings.setHeight(200);
        settings.setTitle("TD");
        settings.setVersion("0.0.1");
    }

    private Home home;

    @Override
    protected void initGame() {
        final GameWorld world = getGameWorld();
        world.addEntityFactory(new Fabric(
                new Point2D(500, 100),
                50
        ));
        for (int i = 100; i <= 500; i += 10)
            world.spawn("RoadElement", i, 100);
        world.spawn("EnemyFabric", 100, 100);

        home = new Home(
                Home.SpawnHome(world, 500, 100),
                () -> {
                    getMasterTimer().clear();
                    for (Entity enemy : world.getEntitiesByType(EntityType.Enemy)) {
                        enemy.removeFromWorld();
                    }
                    Text gameOver = new Text();
                    gameOver.setText("Game over");
                    gameOver.setTranslateX(250);
                    gameOver.setTranslateY(50);
                    getGameScene().addUINode(gameOver);
                }
        );

        getMasterTimer().runAtInterval(() -> Enemy.SpawnEnemy(world, 120, 100, home), Duration.millis(900));

//        world.spawn("Tower", 250, 150);
//        world.spawn("TowerBullet", 250, 150);
    }

    @Override
    protected void initUI() {
        final Text hitPoints = new Text();
        hitPoints.setTranslateX(530);
        hitPoints.setTranslateY(130);

        hitPoints.textProperty().bind(home.hitPoints().asString());

        getGameScene().addUINode(hitPoints);
    }
}
