package EntityHandling;

import EntityHandling.Components.*;
import java.awt.Color;

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
                new ColorComponent(Color.blue),
                new PositionComponent(100.0, 100.0),
                new DimensionComponent(50.0, 50.0, 1.0),
                new CollisionComponent());
        PositionComponent pos =  EntityManager.getInstance().getComponent(e.ID, PositionComponent.class);
        DimensionComponent dim = EntityManager.getInstance().getComponent(e.ID, DimensionComponent.class);
        CollisionComponent coll = EntityManager.getInstance().getComponent(e.ID, CollisionComponent.class);

        coll.square.x = pos.x;
        coll.square.y = pos.y;
        coll.square.width = dim.width * dim.scale;
        coll.square.height = dim.height * dim.scale;
        
        return e;
    }
}
