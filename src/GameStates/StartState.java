package GameStates;

import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.RadiusComponent;
import EntityHandling.Components.TextComponent;
import EntityHandling.Entity;
import EntityHandling.EntityFactory;
import java.awt.Graphics;
import java.util.LinkedList;
import pewpew.Game;

public class StartState extends BaseState {
    private Entity mText = null;
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
        for(int i = 0; i < 10; ++i){
            mBalls.add(EntityFactory.getInstance().createBall());
            xOffset = i * mBalls.getLast().get(RadiusComponent.class).radius * 2;
            mBalls.getLast().get(PositionComponent.class).x += xOffset;
        }
        mText = EntityFactory.getInstance().createText();
        mText.get(TextComponent.class).y += mText.get(TextComponent.class).font.getSize();
    }
    
    @Override
    public void initialize(){

    }

    @Override
    public void update() {
        int counter = 0;
        for(Entity e : mBalls){
            PositionComponent pos = e.get(PositionComponent.class);
            if(pos.x > 0.0 && pos.x < (double)Game.SCR_WIDTH && pos.y > 0.0 && pos.y < (double)Game.SCR_HEIGHT){
                counter++;
            }
        }
        mText.get(TextComponent.class).text = "Balls left: " + counter;
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
