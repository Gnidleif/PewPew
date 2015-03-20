package Systems;

import EntityHandling.Components.AccelerationComponent;
import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.EntityManager;
import java.util.Set;
import java.util.UUID;

public class MoveSystem extends LogicSystem {
    private EntityManager mEM = EntityManager.getInstance();

    public void update() {
        Set<UUID> entities = mEM.getAllEntitiesOwningType(PositionComponent.class);
        
        for(UUID e : entities){
            PositionComponent pos = mEM.getComponent(e, PositionComponent.class);
            
            if(mEM.hasComponent(e, VelocityComponent.class)){
                VelocityComponent vel = mEM.getComponent(e, VelocityComponent.class);
                
                pos.x += vel.x;
                pos.y += vel.y;
                
                if(mEM.hasComponent(e, AccelerationComponent.class)){ // velocity sounds physicy, it should be used there instead, when there's a physics system
                    AccelerationComponent acc = mEM.getComponent(e, AccelerationComponent.class);
                    vel.x *= acc.xAcc;
                    vel.y *= acc.yAcc;
                }
            }
            
            // The part below will later be handled by events to optimize
            if(mEM.hasComponent(e, CollisionComponent.class)){
                CollisionComponent coll = mEM.getComponent(e, CollisionComponent.class);
                coll.square.x = pos.x;
                coll.square.y = pos.y;
            }
            /* Move this to whatever system might handle the change in dimensions of an object
            if(mEM.hasComponent(e1, DimensionComponent.class)){
                DimensionComponent dims = mEM.getComponent(e1, DimensionComponent.class);
                coll1.square.width = dims.width * dims.scale;
                coll1.square.height = dims.height * dims.scale;
            }
            */
        }
    }
}
