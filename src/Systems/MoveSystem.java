package Systems;

import EntityHandling.Components.AccelerationComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.VelocityComponent;
import java.util.Set;
import java.util.UUID;

public class MoveSystem extends LogicSystem {

    @Override
    public void update(double dt) {
        Set<UUID> entities = mEM.getAllEntitiesOwningType(PositionComponent.class);
        
        for(UUID e : entities){
            PositionComponent pos = mEM.getComponent(e, PositionComponent.class);
            
            if(mEM.hasComponent(e, VelocityComponent.class)){
                VelocityComponent vel = mEM.getComponent(e, VelocityComponent.class);
                
                pos.x += vel.x * dt;
                pos.y += vel.y * dt;
                
                if(mEM.hasComponent(e, AccelerationComponent.class)){ // velocity sounds physicy, it should be used there instead
                    AccelerationComponent acc = mEM.getComponent(e, AccelerationComponent.class);
                    vel.x += acc.xAcc * dt;
                    vel.y += acc.yAcc * dt;
                }
            }
        }
    }
}
