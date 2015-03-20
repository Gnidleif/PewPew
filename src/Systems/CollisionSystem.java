package Systems;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.RenderComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.Entity;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

public class CollisionSystem extends LogicSystem {
    private class Bound {
        public Entity drawBounds;
        public LinkedList<UUID> entities = new LinkedList<>();
        
        public Bound(){
            drawBounds = new Entity(
                    new RenderComponent(true, 10), // the render component here is only required for debugging
                    new CollisionComponent(false, new Rectangle2D.Double()));
        }
    }
    
    private final LinkedList<Bound> mBounds = new LinkedList<>();
    
    public void createScreenBounds(double x, double y, int splits){
        double offsetY = y / (float)splits;
        for(int i = 0; i < splits; i++){
            mBounds.add(new Bound());
            CollisionComponent coll = mBounds.get(i).drawBounds.get(CollisionComponent.class);
            double lowY = offsetY * i;
            double maxY = offsetY * (i + 1);
            coll.square.x = 0;
            coll.square.y = lowY;
            coll.square.width = x;      
            coll.square.height = offsetY;
            System.out.println(coll.square.toString());
        }
    }
    
    private void addEntitiesToBounds(Set<UUID> entities){
        LinkedList<UUID> temp = new LinkedList<>();
        for(UUID e : entities){
            temp.add(e);
        }
        
        for(int i = 0; i < mBounds.size(); i++){
            CollisionComponent bound = mBounds.get(i).drawBounds.get(CollisionComponent.class);
            for(int k = 0; k < temp.size(); k++){
                CollisionComponent coll = mEM.getComponent(temp.get(k), CollisionComponent.class);
                if(!mEM.hasComponent(temp.get(k), ColorComponent.class) || !coll.collidable)
                    continue;
                
                if(bound.square.intersects(coll.square)){
                    mBounds.get(i).entities.add(temp.get(k));
                }
            }
        }
    }
    
    public void update() {
        Set<UUID> entities = mEM.getAllEntitiesOwningType(CollisionComponent.class);
        addEntitiesToBounds(entities);
        
        for(UUID e1 : entities){
            if(!mEM.getComponent(e1, CollisionComponent.class).collidable){
                continue;
            }
            if(mEM.hasComponent(e1, PositionComponent.class) && mEM.hasComponent(e1, CollisionComponent.class) && mEM.hasComponent(e1, VelocityComponent.class)){
                PositionComponent pos1 = mEM.getComponent(e1, PositionComponent.class);
                CollisionComponent coll1 = mEM.getComponent(e1, CollisionComponent.class);
                VelocityComponent vel = mEM.getComponent(e1, VelocityComponent.class);
                
                for(UUID e2 : entities){
                    if(e1 == e2 || !mEM.getComponent(e2, CollisionComponent.class).collidable){
                        continue;
                    }
                    CollisionComponent coll2 = mEM.getComponent(e2, CollisionComponent.class);
                    if(mEM.hasComponent(e2, PositionComponent.class)){
                        PositionComponent pos2 = mEM.getComponent(e2, PositionComponent.class);
                        
                        
                    }
                }
            }
        }
    }
}
