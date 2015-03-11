package EntityHandling;

import EntityHandling.Components.*;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
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
    
    public Entity createStaticBall(){
        Entity e = new Entity(
                new RenderComponent(true, 1),
                new ColorComponent(Color.blue),
                new PositionComponent((double)Game.SCR_WIDTH / 2, (double)Game.SCR_HEIGHT / 2),
                new DimensionComponent(50.0, 50.0, 1.0),
                new VelocityComponent(1.0, 1.0),
                new CollisionComponent());
        e.get(CollisionComponent.class).square = new Rectangle2D.Double((double)Game.SCR_WIDTH / 2, (double)Game.SCR_HEIGHT / 2 + 1, 50.0, 50.0);
        return e;
    }
    
    public Entity createBackground(String path){
        Entity e = new Entity(
                new RenderComponent(false, 0), 
                new PositionComponent((int)Game.SCR_WIDTH/2, (int)Game.SCR_HEIGHT/2), 
                new ImageComponent(AssetManager.getInstance().getBufferedImage(path)),
                new DimensionComponent((int)Game.SCR_WIDTH, (int)Game.SCR_HEIGHT, 1.0));
        
        return e;
    }
}
