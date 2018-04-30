package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.Timer;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import td.Components.Movement;
import td.Entities.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Game extends GameApplication {
    public enum EntityType {
        Road,
        Enemy,
        Fabric,
        TowerPosition,
        Tower,
        Bullet,
        Home,
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(300);
        settings.setTitle("TD");
        settings.setVersion("0.0.2");
    }

    private Home home;

    @Override
    protected void initGame() {
        final GameWorld world = getGameWorld();
        for (int i = 100; i <= 500; i += 10)
            RoadElement.SpawnRoad(world, i, 100);
        EnemyFabric fabric = new EnemyFabric(world, 100, 100);

        home = new Home(
                Home.SpawnHome(world, 500, 100),
                () -> {
                    getMasterTimer().clear();
                    Text gameOver = new Text();
                    gameOver.setText("Game over");
                    gameOver.setTranslateX(250);
                    gameOver.setTranslateY(50);
                    getGameScene().addUINode(gameOver);
                }
        );

        Timer timer = getMasterTimer();

        timer.runAtInterval(() -> fabric.spawnEnemy(home), Duration.millis(1000));

        Tower tower = new Tower(world, 250, 150);

        timer.runAtInterval(() -> {
            List<Entity> enemies = world.getEntitiesByType(EntityType.Enemy);
            if (enemies.isEmpty()) return;
            int index = (int) Math.floor(Math.random()*enemies.size());
            Entity enemy = enemies.get(index);
            if (enemy != null)
                tower.shoot(enemy);
        }, Duration.millis(100));
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
