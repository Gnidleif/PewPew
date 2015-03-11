package GameStates;

import EntityHandling.Components.PositionComponent;
import EntityHandling.Entity;
import EntityHandling.EntityFactory;
import java.awt.Graphics;
import java.util.LinkedList;

public class StartState extends BaseState {
    private Entity mBackground = null;
    private final LinkedList<Entity> mBalls;
    
    public StartState(){
        super();
        mBalls = new LinkedList<>();
    }
    
    @Override
    public void onEnter() {
        mBackground = EntityFactory.getInstance().createBackground("/backgrounds/skyBG.png");
        double xOffset = 0.0;
        //double yOffset = 0.0;
        for(int i = 0; i < 1; ++i){
            mBalls.add(EntityFactory.getInstance().createStaticBall());
        }
    }
    
    @Override
    public void initialize(){

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
