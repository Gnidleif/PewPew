package EntityHandling;

import EntityHandling.Components.*;
import java.awt.Color;
import java.util.Random;
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
                new PositionComponent((int)Game.SCR_WIDTH / 2, (int)Game.SCR_HEIGHT / 2),
                new CollisionComponent(),
                new RadiusComponent(50.0));
        
        return e;
    }
    
    public Entity createBall(){
        Entity e = new Entity(
                new RenderComponent(true, 1),
                new ColorComponent(Color.red),
                new PositionComponent(100.0, (int)Game.SCR_HEIGHT / 2),
                new VelocityComponent(),
                new CollisionComponent(),
                new AccelerationComponent(),
                new RadiusComponent(20.0), 
                new ScreenCollisionComponent());
        VelocityComponent vel = e.get(VelocityComponent.class);
        double start = 5.0;
        double end = 15.0;
        
        double random = new Random().nextDouble();
        double result = start + (random * (end - start));
        boolean dir = new Random().nextBoolean();
        if(dir){
            result = -result;
        }
        vel.x = result;
        
        random = new Random().nextDouble();
        result = start + (random * (end - start));
        dir = new Random().nextBoolean();
        if(dir){
            result = -result;
        }
        vel.y = result;
        
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
