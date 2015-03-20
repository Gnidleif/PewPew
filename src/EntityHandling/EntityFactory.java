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
    
    public Entity createSquare(){
        Entity e = new Entity(
                new RenderComponent(true, 5),
                new ColorComponent(Color.black),
                new VelocityComponent(1.0, 1.0),
                new PositionComponent(100.0, 100.0),
                new DimensionComponent(50.0, 50.0, 1.0),
                new CollisionComponent(true, new Rectangle2D.Double(100.0, 100.0, 50.0, 50.0)));
        
        return e;
    }
}
