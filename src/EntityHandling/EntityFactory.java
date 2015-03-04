package EntityHandling;

import EntityHandling.Components.*;
import java.awt.Color;
import pewpew.AssetManager;
import pewpew.Game;

public class EntityFactory {
    
    private static class EntityFactoryHolder{
        public static EntityFactory instance = new EntityFactory();
    }
    
    public static EntityFactory getInstance(){
        return EntityFactoryHolder.instance;
    }
    
    private EntityFactory(){
        
    }
    
    public Entity createBall(){
        Entity e = new Entity(
                new RenderComponent(true, 1),
                new ColorComponent(Color.red),
                new PositionComponent(0.0, (int)Game.SCR_HEIGHT / 2),
                new VelocityComponent(1.0, 0.0),
                new CollisionComponent(),
                new RadiusComponent(50.0));
        
        e.get(RenderComponent.class).layer = 1;
        return e;
    }
    
    public Entity createBackground(String path){
        Entity e = new Entity(
                new RenderComponent(), 
                new PositionComponent((int)Game.SCR_WIDTH/2, (int)Game.SCR_HEIGHT/2), 
                new ImageComponent(AssetManager.getInstance().getBufferedImage(path)),
                new DimensionComponent((int)Game.SCR_WIDTH, (int)Game.SCR_HEIGHT, 1.0));
        
        return e;
    }
}
