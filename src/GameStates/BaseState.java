package GameStates;

import java.awt.Graphics;

public abstract class BaseState {
    public abstract void initialize();
    public abstract void onEnter();
    public abstract void update(double dt);
    public abstract void render(Graphics g);
    public abstract void onExit();
    public abstract void reset();
}
