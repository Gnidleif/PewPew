package GameStates;

import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.Entity;
import EntityHandling.EntityFactory;
import EntityHandling.EntityManager;
import java.awt.Graphics;
import java.util.LinkedList;

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
        mBalls.add(EntityFactory.getInstance().createSquare());
        mBalls.add(EntityFactory.getInstance().createSquare());
        mBalls.getLast().get(PositionComponent.class).x += 240.0;
        mBalls.getLast().get(PositionComponent.class).y += 240.0;
        mBalls.getLast().remove(VelocityComponent.class);
        //mBalls.getLast().get(VelocityComponent.class).x *= -1.0;
        //mBalls.getLast().get(VelocityComponent.class).y *= -1.0;
    }

    @Override
    public void update(double dt) {
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
