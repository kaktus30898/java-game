package td;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import td.Components.Hittable;
import td.Components.MovementToTarget;
import td.Entities.EnemyFabric;
import td.Entities.Home;
import td.Entities.RoadElement;
import td.Entities.Tower;

/**
 * Класс Game является главным классом программы и реализует центральную логику игры.
 */
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

    /**
     * Инициализация настроек игры
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(650);
        settings.setHeight(450);
        settings.setTitle("TD");
        settings.setVersion("0.0.4");
    }

    private Home home;
    private final GameContext context = new GameContext(this);

    /**
     * Инициализация игровых объектов
     */
    @Override
    protected void initGame() {
        /*
         * Дорога.
         */
        final Point2D roadPointA = new Point2D(100, 100);
        final Point2D roadPointB = new Point2D(500, 100);
        final Point2D roadPointC = new Point2D(500, 300);
        RoadElement.DrawRoad(context, roadPointA, roadPointB);
        RoadElement.DrawRoad(context, roadPointB, roadPointC);

        final MovementToTarget.MovementPathBuilder road0 = new MovementToTarget.MovementPathBuilder(
                roadPointA,
                roadPointB,
                roadPointC
        );

        final Point2D roadPointD = new Point2D(100, 300);

        RoadElement.DrawRoad(context, roadPointA, roadPointD);
        RoadElement.DrawRoad(context, roadPointD, roadPointC);

        final MovementToTarget.MovementPathBuilder road1 = new MovementToTarget.MovementPathBuilder(
                roadPointA,
                roadPointD,
                roadPointC
        );

        /*
         * Дом.
         */
        home = new Home(() -> {
            getMasterTimer().clear();
            Text gameOver = new Text();
            gameOver.setText("Game over");
            gameOver.setTranslateX(250);
            gameOver.setTranslateY(50);
            getGameScene().addUINode(gameOver);
        });
        context.spawn(home, roadPointC);

        /*
         * Фабрика врагов.
         */
        EnemyFabric fabric = new EnemyFabric(
                context,
                new MovementToTarget.MovementPathBuilder[]{
                        road0,
                        road1,
                },
                home
        );
        context.spawn(fabric, 100, 100);

        /*
         * Первая башня.
         */
        context.spawn(new Tower(context, Tower.TowerType.Red), 450, 250);
    }

    final private TowerMenu towerMenu = new TowerMenu();

    /**
     * Инициализация слоя UI.
     */
    @Override
    protected void initUI() {
        /*
         * Очки здоровья "дома".
         */
        final GameScene scene = getGameScene();
        final Text hitPoints = new Text();
        hitPoints.setTranslateX(530);
        hitPoints.setTranslateY(330);

        hitPoints.textProperty().bind(
                home.getComponent(Hittable.class)
                        .hitPoints()
                        .asString()
        );

        scene.addUINode(hitPoints);

        /*
         * Меню выбора башни.
         */
        scene.addUINode(towerMenu.getNode());
    }

    /**
     * Инициализация обработчиков ввода от пользователя.
     */
    @Override
    protected void initInput() {
        /*
         * Клик по игровому экрану.
         */
        final Input input = getInput();
        final GameWorld world = getGameWorld();
        input.addAction(new UserAction("Click") {
            @Override
            protected void onActionBegin() {
                Point2D position = input.getMousePositionWorld();
                if (towerMenu.isVisible()) {
                    if (towerMenu.isAtMenu(position)) {
                        final int option = towerMenu.isAtOption(position);
                        if (option >= 0)
                            towerMenu.setVisible(false);
                        switch (option) {
                            case 0:
                                context.spawn(new Tower(context, Tower.TowerType.Red), towerMenu.getPosition());
                                break;
                            case 1:
                                context.spawn(new Tower(context, Tower.TowerType.Green), towerMenu.getPosition());
                                break;
                            case 2:
                                context.spawn(new Tower(context, Tower.TowerType.Blue), towerMenu.getPosition());
                                break;
                        }
                    } else
                        towerMenu.setVisible(false);
                } else {
                    position = context.findPlaceToSafeSpawn(position, 25);
                    if (position != null)
                        towerMenu.showAt(position);
                }
            }
        }, MouseButton.PRIMARY);
    }
}
