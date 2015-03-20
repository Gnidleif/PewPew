package GameStates;

import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.ImageComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.Entity;
import EntityHandling.EntityFactory;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import pewpew.AssetManager;

public class StartState extends BaseState {
    private final LinkedList<Entity> mBalls;
    
    public StartState(){
        super();
        mBalls = new LinkedList<>();
    }
    
    @Override
    public void onEnter() {
    }
    
    @Override
    public void initialize(){
        mBalls.add(EntityFactory.getInstance().createBall());
        mBalls.getLast().add(new VelocityComponent(5.0, 5.0));
        mBalls.add(EntityFactory.getInstance().createBall());
        mBalls.getLast().get(PositionComponent.class).x += 60.0;
        mBalls.getLast().get(PositionComponent.class).y += 60.0;
        mBalls.getLast().get(ColorComponent.class).color = Color.red;
        mBalls.getLast().get(ImageComponent.class).tex = AssetManager.getInstance().getBufferedImage("/sprites/placeholder.png");
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g) {
        
    }

    @Override
    public void onExit() {
        for(Entity e : mBalls){
            e.kill();
        }
    }

    @Override
    public void reset() {
    }
    
}
