package Systems;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.RenderComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.Entity;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

public class CollisionSystem extends LogicSystem {
    // Class used to define a rectangular field for comparison of collisions between objects
    private class Bound {
        public Entity drawBounds;
        public LinkedList<UUID> entities = new LinkedList<>();
        
        public Bound(){
            drawBounds = new Entity(
                    new RenderComponent(true, 10), // the render component here is only required for debugging
                    new ColorComponent(Color.black),
                    new CollisionComponent(false, new Rectangle2D.Double()));
        }
    }
    
    private final LinkedList<Bound> mBounds = new LinkedList<>();
    
    public void createScreenBounds(double x, double y, int splits){
        double offsetY = y / (float)splits;
        for(int i = 0; i < splits; i++){
            mBounds.add(new Bound());
            CollisionComponent coll = mBounds.get(i).drawBounds.get(CollisionComponent.class);
            double top = offsetY * i;
            coll.square.x = 0;
            coll.square.y = top;
            coll.square.width = x;      
            coll.square.height = offsetY;
        }
    }
    
    private void distributeEntities(Set<UUID> entities){        
        for(Bound bound : mBounds){
            CollisionComponent bColl = bound.drawBounds.get(CollisionComponent.class);
            
            for(UUID e : entities){
                CollisionComponent eColl = mEM.getComponent(e, CollisionComponent.class);
                
                if(!eColl.collidable){ // unnecessary to check for collisions on objects that can't collide
                    continue;
                }
                
                if(bColl.square.intersects(eColl.square)){
                    bound.entities.add(e);
                }
            }
        }
    }
    
    private boolean isColliding(Rectangle2D.Double r1, Rectangle2D.Double r2){
        double 
                top1 = r1.y,
                bot1 = r1.y + r1.height,
                left1 = r1.x,
                right1 = r1.x + r1.width;
        double
                top2 = r2.y,
                bot2 = r2.y + r2.height,
                left2 = r2.x,
                right2 = r2.x + r2.width;
        
        // Horizontal collision
        if(left1 > right2 || left2 > right1)
            return false;
        
        // Vertical collision
        if(top1 > bot2 || top2 > bot1)
            return false;
        
        return true;
    }
    
    private void clearBounds(){
        for(Bound bound : mBounds){
            bound.entities.clear();
        }
    }
    
    private static int counter = 0;
    
    public void update() {
        Set<UUID> entities = mEM.getAllEntitiesOwningType(CollisionComponent.class);
        this.distributeEntities(entities);
        
        for(Bound bound : mBounds){
            if(bound.entities.size() <= 1){ // No need to check for collisions if there's only one or zero entities in the box
                continue;
            }
            
            for(UUID e1 : bound.entities){
                CollisionComponent coll1 = mEM.getComponent(e1, CollisionComponent.class);
                
                if(!mEM.hasComponent(e1, VelocityComponent.class)){ // Skip if there's no VelocityComponent to change
                    continue;
                }
                VelocityComponent vel = mEM.getComponent(e1, VelocityComponent.class);
                
                for(UUID e2 : bound.entities){
                    if(e1 == e2){ // No need to check collision on yourself
                        continue;
                    }
                    CollisionComponent coll2 = mEM.getComponent(e2, CollisionComponent.class);
                    
                    if(this.isColliding(coll1.square, coll2.square)){
                        System.out.println("Hit! " + counter);
                        //EntityManager.getInstance().killEntity(e1);
                        counter++;
                        break;
                    }
                }
            }
        }
        this.clearBounds();
    }
}
