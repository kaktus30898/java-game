package td;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Вспомогательный класс TowerMenu служит для сокрытия реализации меню выбора башни.
 */
public class TowerMenu {
    final private Group towerMenu = new Group();
    final private Rectangle background = new Rectangle();
    final private Circle option0 = new Circle();
    final private Circle option1 = new Circle();
    final private Circle option2 = new Circle();
    private Point2D position = null;

    public TowerMenu() {
        background.setTranslateX(0);
        background.setTranslateY(0);
        background.setWidth(90);
        background.setHeight(50);
        final int hue = 50;
        background.setFill(Color.rgb(hue, hue, hue));
        background.setArcHeight(15);
        background.setArcWidth(15);
        towerMenu.getChildren().add(background);

        option0.setCenterX(15);
        option0.setCenterY(35);
        option0.setRadius(10);
        option0.setFill(Color.RED);
        towerMenu.getChildren().add(option0);

        option1.setCenterX(45);
        option1.setCenterY(35);
        option1.setRadius(10);
        option1.setFill(Color.GREEN);
        towerMenu.getChildren().add(option1);

        option2.setCenterX(75);
        option2.setCenterY(35);
        option2.setRadius(10);
        option2.setFill(Color.BLUE);
        towerMenu.getChildren().add(option2);

        final Text caption = new Text();
        caption.setText("Выберите тип");
        caption.setTranslateX(15);
        caption.setTranslateY(15);
        caption.setFill(Color.WHITE);
        caption.setFont(new Font(10));
        towerMenu.getChildren().add(caption);

        towerMenu.setVisible(false);
    }

    /**
     * @return Объект, который можно прикрепить к слою UI, чтобы меню заработало.
     */
    public Node getNode() {
        return towerMenu;
    }

    /**
     * Определяет попадает ли указаная точка в нарисованое меню.
     * Если меню скрыто, возвращает false.
     */
    public boolean isAtMenu(final Point2D mousePosition) {
        if (!towerMenu.isVisible()) return false;
        final Point2D position = mousePosition.subtract(towerMenu.getTranslateX(), towerMenu.getTranslateY());
        return (position.getX() >= 0)
                && (position.getX() <= background.getWidth())
                && (position.getY() >= 0)
                && (position.getY() <= background.getHeight());
    }

    /**
     * Определяет попадает ли указаная точка на одну из опций меню.
     * Если нет, возвращает -1. Если меню скрыто, возвращает -1.
     * Если попадает, возвращает номер опции (0, 1 или 2).
     */
    public int isAtOption(final Point2D mousePosition) {
        if (!isAtMenu(mousePosition)) return -1;
        final Point2D position = mousePosition.subtract(towerMenu.getTranslateX(), towerMenu.getTranslateY());
        Point2D t = new Point2D(option0.getCenterX(), option0.getCenterY());
        if (position.distance(t) <= option0.getRadius()) return 0;
        t = new Point2D(option1.getCenterX(), option1.getCenterY());
        if (position.distance(t) <= option1.getRadius()) return 1;
        t = new Point2D(option2.getCenterX(), option2.getCenterY());
        if (position.distance(t) <= option2.getRadius()) return 2;
        return -1;
    }

    /**
     * Определяет показано ли меню сейчас.
     */
    public boolean isVisible() {
        return towerMenu.isVisible();
    }

    /**
     * Устанавливает значение флага видимости в переданное значение: true покажет меню, false - скроет.
     */
    public void setVisible(boolean value) {
        towerMenu.setVisible(value);
    }

    /**
     * Показывает меню, предварительно переместив его к данным координатам.
     * @param position Новые координаты меню.
     */
    public void showAt(final Point2D position) {
        this.position = position;
        towerMenu.setTranslateX(position.getX());
        towerMenu.setTranslateY(position.getY());
        towerMenu.setVisible(true);
    }

    /**
     * @return Текущее (или последнее) положение меню. Если меню не было показано, возвращает false.
     */
    public Point2D getPosition() {
        return position;
    }
}
