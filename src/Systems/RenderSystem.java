package Systems;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.RenderComponent;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class RenderSystem extends LogicSystem {
    
    private ArrayList<UUID> getSorted(Set<UUID> entities){
        HashMap<Integer, ArrayList<UUID>> layerMap = new HashMap<>();
        
        for(UUID e : entities){
            int key = mEM.getComponent(e, RenderComponent.class).layer;
            if(!layerMap.containsKey(key)){
                layerMap.put(key, new ArrayList<>());
            }
            layerMap.get(key).add(e);
        }
        
        ArrayList<UUID> sorted = new ArrayList<>();
        for(ArrayList<UUID> list : layerMap.values()){
            for(UUID e : list){
                sorted.add(e);
            }
        }
        
        return sorted;
    }

    public void draw(Graphics g) {
        ArrayList<UUID> entities = getSorted(mEM.getAllEntitiesOwningType(RenderComponent.class));
        for(UUID e : entities){
            if(!mEM.getComponent(e, RenderComponent.class).visible){
                continue;
            }
            
            if(mEM.hasComponent(e, ColorComponent.class)){
                ColorComponent color = mEM.getComponent(e, ColorComponent.class);
                g.setColor(mEM.getComponent(e, ColorComponent.class).color);
            }

            // Only used for debugging collisionsquares
            if(mEM.hasComponent(e, CollisionComponent.class)){
                CollisionComponent coll = mEM.getComponent(e, CollisionComponent.class);

                g.drawRect(
                        (int) coll.square.x, 
                        (int) coll.square.y, 
                        (int) coll.square.width, 
                        (int) coll.square.height);
            }
            
            // TODO: Add code for rendering images
        }
    }
}
