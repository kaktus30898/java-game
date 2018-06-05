package td.Components;

import com.almasb.fxgl.entity.component.Component;

public class Clickable extends Component {
    private final Runnable onClick;
    private boolean isActive = true;

    public Clickable(Runnable onClick) {
        this.onClick = onClick;
    }

    public void click() {
        onClick.run();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
