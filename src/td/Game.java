package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.text.Text;
import td.Components.Hittable;
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
        settings.setHeight(300);
        settings.setTitle("TD");
        settings.setVersion("0.0.2");
    }

    private Home home;

    @Override
    protected void initGame() {
        final GameContext context = new GameContext(this);

        for (int i = 100; i <= 500; i += 10)
            context.spawn(new RoadElement(), i, 100);

        home = new Home(() -> {
            getMasterTimer().clear();
            Text gameOver = new Text();
            gameOver.setText("Game over");
            gameOver.setTranslateX(250);
            gameOver.setTranslateY(50);
            getGameScene().addUINode(gameOver);
        });
        context.spawn(home, 500, 100);

        EnemyFabric fabric = new EnemyFabric(context, home);
        context.spawn(fabric, 100, 100);

        context.spawn(new Tower(context), 250, 150);
    }

    @Override
    protected void initUI() {
        final Text hitPoints = new Text();
        hitPoints.setTranslateX(530);
        hitPoints.setTranslateY(130);

        hitPoints.textProperty().bind(
                home.getComponent(Hittable.class)
                        .hitPoints()
                        .asString()
        );

        getGameScene().addUINode(hitPoints);
    }
}
