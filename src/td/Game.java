package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.text.Text;
import td.Components.Hittable;
import td.Components.MovementToTarget;
import td.Entities.EnemyFabric;
import td.Entities.Home;
import td.Entities.RoadElement;
import td.Entities.Tower;

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
        settings.setHeight(350);
        settings.setTitle("TD");
        settings.setVersion("0.0.3");
    }

    private Home home;

    @Override
    protected void initGame() {
        final GameContext context = new GameContext(this);

        final Point2D roadPointA = new Point2D(100, 100);
        final Point2D roadPointB = new Point2D(500, 100);
        final Point2D roadPointC = new Point2D(250, 300);
        RoadElement.DrawRoad(context, roadPointA, roadPointB);
        RoadElement.DrawRoad(context, roadPointB, roadPointC);

        final MovementToTarget.MovementPathBuilder road = new MovementToTarget.MovementPathBuilder(
                roadPointA,
                roadPointB,
                roadPointC
        );

        home = new Home(() -> {
            getMasterTimer().clear();
            Text gameOver = new Text();
            gameOver.setText("Game over");
            gameOver.setTranslateX(250);
            gameOver.setTranslateY(50);
            getGameScene().addUINode(gameOver);
        });
        context.spawn(home, roadPointC);

        EnemyFabric fabric = new EnemyFabric(context, road, home);
        context.spawn(fabric, 100, 100);

        context.spawn(new Tower(context), 350, 150);
    }

    @Override
    protected void initUI() {
        final Text hitPoints = new Text();
        hitPoints.setTranslateX(300);
        hitPoints.setTranslateY(300);

        hitPoints.textProperty().bind(
                home.getComponent(Hittable.class)
                        .hitPoints()
                        .asString()
        );

        getGameScene().addUINode(hitPoints);
    }
}
