package EntityHandling;

import EntityHandling.Components.*;
import java.awt.Color;
import java.awt.Font;

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
                new RenderComponent(true, 5),
                new ColorComponent(Color.blue),
                new PositionComponent(100.0, 100.0),
                new DimensionComponent(50.0, 50.0, 1.0),
                new AccelerationComponent(),
                new CollisionComponent(), 
                new ImageComponent("/sprites/circle.png"));
        
        PositionComponent pos =  EntityManager.getInstance().getComponent(e.ID, PositionComponent.class);
        DimensionComponent dim = EntityManager.getInstance().getComponent(e.ID, DimensionComponent.class);
        CollisionComponent coll = EntityManager.getInstance().getComponent(e.ID, CollisionComponent.class);
        ImageComponent img = EntityManager.getInstance().getComponent(e.ID, ImageComponent.class);
        
        dim.width = img.tex.getWidth();
        dim.height = img.tex.getHeight();
        
        coll.rect.x = pos.x;
        coll.rect.y = pos.y;
        coll.rect.width = dim.width * dim.scale;
        coll.rect.height = dim.height * dim.scale;
        
        return e;
    }
    
    public Entity createText(){
        Entity e = new Entity(new TextComponent());
        e.get(TextComponent.class).color = Color.blue;
        e.get(TextComponent.class).x = 10;
        e.get(TextComponent.class).y = 10;
        e.get(TextComponent.class).font = new Font("Comic Sans MS", Font.BOLD, 30);
        
        return e;
    }
}
