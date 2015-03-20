package Systems;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.DimensionComponent;
import EntityHandling.Components.ImageComponent;
import EntityHandling.Components.PositionComponent;
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
                        (int) coll.rect.x, 
                        (int) coll.rect.y, 
                        (int) coll.rect.width, 
                        (int) coll.rect.height);
            }
            
            // TODO: Add code for rendering images
            
            if(mEM.hasComponent(e, ImageComponent.class) && mEM.hasComponent(e, PositionComponent.class)){
                ImageComponent img = mEM.getComponent(e, ImageComponent.class);
                PositionComponent pos = mEM.getComponent(e, PositionComponent.class);
                DimensionComponent dims = new DimensionComponent();
                
                if(mEM.hasComponent(e, DimensionComponent.class)){
                    dims = mEM.getComponent(e, DimensionComponent.class);
                }
                else{
                    dims.height = img.tex.getHeight();
                    dims.width = img.tex.getWidth();
                }
                
                g.drawImage(
                        img.tex, 
                        (int)pos.x, 
                        (int)pos.y, 
                        (int)(dims.width * dims.scale),
                        (int)(dims.height * dims.scale),
                        null);
            }
        }
    }
}
