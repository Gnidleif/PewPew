package Systems;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.ImageComponent;
import EntityHandling.Components.RenderComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.Entity;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

public class CollisionSystem extends LogicSystem {
    // Class used to define a rectangular field for comparison of collisions between objects
    private class Bound {
        public Entity collisionRect;
        public LinkedList<UUID> entities = new LinkedList<>();
        
        public Bound(){
            collisionRect = new Entity(
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
            CollisionComponent coll = mBounds.get(i).collisionRect.get(CollisionComponent.class);
            double top = offsetY * i;
            coll.rect.x = 0;
            coll.rect.y = top;
            coll.rect.width = x;      
            coll.rect.height = offsetY;
        }
    }
    
    private void distributeEntities(Set<UUID> entities){        
        for(Bound bound : mBounds){
            CollisionComponent bColl = bound.collisionRect.get(CollisionComponent.class);
            
            for(UUID e : entities){
                CollisionComponent eColl = mEM.getComponent(e, CollisionComponent.class);
                
                if(!eColl.collidable){ // unnecessary to check for collisions on objects that can't collide
                    continue;
                }
                
                if(bColl.rect.intersects(eColl.rect)){
                    bound.entities.add(e);
                }
            }
        }
    }
    
    private boolean isColliding(UUID e1, UUID e2){
        Rectangle2D.Double r1 = mEM.getComponent(e1, CollisionComponent.class).rect;
        Rectangle2D.Double r2 = mEM.getComponent(e2, CollisionComponent.class).rect;
        
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
        
        if(mEM.hasComponent(e1, ImageComponent.class) && mEM.hasComponent(e2, ImageComponent.class)){
            return isPixelColliding(e1, e2);
        }
        return true;
    }
    
    private boolean isPixelColliding(UUID e1, UUID e2){
        Rectangle2D.Double r1 = mEM.getComponent(e1, CollisionComponent.class).rect;
        Rectangle2D.Double r2 = mEM.getComponent(e2, CollisionComponent.class).rect;
        BufferedImage img1 = mEM.getComponent(e1, ImageComponent.class).tex;
        BufferedImage img2 = mEM.getComponent(e2, ImageComponent.class).tex;

        double
                width1 = r1.x + img1.getWidth(),
                height1 = r1.y + img1.getHeight(),
                width2 = r2.x + img2.getWidth(),
                height2 = r2.y + img2.getHeight();
        
        int
                startX = (int) Math.max(r1.x, r2.x),
                startY = (int) Math.max(r1.y, r2.y),
                endX   = (int) Math.min(width1, width2),
                endY   = (int) Math.min(height1, height2);
        
        int totY = Math.abs(endY - startY);
        int totX = Math.abs(endX - startX);
        
        for(int y = 0; y < totY; y++){
            int ny1 = Math.abs(startY - (int)r1.y) + y;
            int ny2 = Math.abs(startY - (int)r2.y) + y;
            
            for(int x = 0; x < totX; x++){
                int nx1 = Math.abs(startX - (int)r1.x) + x;
                int nx2 = Math.abs(startX - (int)r2.x) + x;
                if(!isTransparent(img1, nx1, ny1) && !isTransparent(img2, nx2, ny2)){
                    return true;
                }
            }            
        }
        
        return false;
    }
    
    private boolean isTransparent(BufferedImage img, int x, int y){
        return (img.getRGB(x, y) & 0xFF000000) == 0x000000;
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
                    
                    if(this.isColliding(e1, e2)){
                        System.out.println("Hit! " + counter);
                        vel.x *= -1.0;
                        vel.y *= -1.0;
                        counter++;
                        break;
                    }
                }
            }
        }
        this.clearBounds();
    }
}
