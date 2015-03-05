package Systems;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.RadiusComponent;
import EntityHandling.Components.ScreenCollisionComponent;
import EntityHandling.Components.VelocityComponent;
import java.util.Set;
import java.util.UUID;
import pewpew.Game;

public class CollisionSystem extends LogicSystem {
    
    private double calcLength(PositionComponent p1, PositionComponent p2){
        double xDist = p2.x - p1.x;
        double yDist = p2.y - p1.y;
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
                        
                        if(calcLength(pos1, pos2) < rad.radius*2){
                            if(mEM.hasComponent(e2, VelocityComponent.class)){
                                // Code handling two moving objects colliding
                            }
                            vel.x = -vel.x;
                            vel.y = -vel.y;
                            break;
                        }
                    }
                }
                
                if(mEM.hasComponent(e1, ScreenCollisionComponent.class)){
                    if(mEM.getComponent(e1, ScreenCollisionComponent.class).collidable){
                        PositionComponent sPos = new PositionComponent((double)Game.SCR_WIDTH, (double)Game.SCR_HEIGHT);
                        if(calcLength(pos1, new PositionComponent(0.0, pos1.y)) < rad.radius || calcLength(pos1, new PositionComponent(sPos.x, pos1.y)) < rad.radius){
                            vel.x *= -1.0;
                        }
                        if(calcLength(pos1, new PositionComponent(pos1.x, 0.0)) < rad.radius || calcLength(pos1, new PositionComponent(pos1.x, sPos.y)) < rad.radius){
                            vel.y *= -1.0;
                        }
                    }
                }
            }
        }
    }
}
