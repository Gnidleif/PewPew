package Systems;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.RadiusComponent;
import EntityHandling.Components.ScreenCollisionComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.EntityManager;
import java.util.Set;
import java.util.UUID;

public class CollisionSystem extends LogicSystem {
    
    private double calcLength(PositionComponent p1, PositionComponent p2){
        double xDist = p1.x - p2.x;
        double yDist = p1.y - p2.y;
        return Math.sqrt(xDist*xDist + yDist*yDist);
    }
    
    public void update() {
        Set<UUID> entities = mEM.getAllEntitiesOwningType(CollisionComponent.class);
        for(UUID e1 : entities){
            if(!mEM.getComponent(e1, CollisionComponent.class).collidable){
                continue;
            }
            if(mEM.hasComponent(e1, PositionComponent.class) && mEM.hasComponent(e1, RadiusComponent.class) && mEM.hasComponent(e1, VelocityComponent.class)){
                PositionComponent pos1 = mEM.getComponent(e1, PositionComponent.class);
                RadiusComponent rad = mEM.getComponent(e1, RadiusComponent.class);
                VelocityComponent vel = mEM.getComponent(e1, VelocityComponent.class);
                
                for(UUID e2 : entities){
                    if(e1 == e2 || !mEM.getComponent(e2, CollisionComponent.class).collidable){
                        continue;
                    }
                    if(mEM.hasComponent(e2, PositionComponent.class)){
                        PositionComponent pos2 = mEM.getComponent(e2, PositionComponent.class);
                        
                        double length = calcLength(pos1, pos2);
                        if(length < rad.radius){
                            vel.x *= -1.0;
                            vel.y *= -1.0;
                            break;
                        }
                    }
                }
                
                if(mEM.hasComponent(e1, ScreenCollisionComponent.class)){
                    if(mEM.getComponent(e1, ScreenCollisionComponent.class).collidable){
                        
                    }
                }
            }
        }
    }
}
