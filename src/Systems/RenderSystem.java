package Systems;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.DimensionComponent;
import EntityHandling.Components.ImageComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.RenderComponent;
import java.awt.Color;
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
            RenderComponent render = mEM.getComponent(e, RenderComponent.class);
            if(!render.visible){
                continue;
            }
            
            if(mEM.hasComponent(e, ColorComponent.class)){
                ColorComponent color = mEM.getComponent(e, ColorComponent.class);
                g.setColor(mEM.getComponent(e, ColorComponent.class).color);
            }
            else{
                g.setColor(Color.red);
            }

            if(mEM.hasComponent(e, CollisionComponent.class)){
                CollisionComponent coll = mEM.getComponent(e, CollisionComponent.class);

                g.drawRect(
                        (int) coll.square.x, 
                        (int) coll.square.y, 
                        (int) coll.square.width, 
                        (int) coll.square.height);
            }
            
            if(mEM.hasComponent(e, PositionComponent.class)){
                PositionComponent pos = mEM.getComponent(e, PositionComponent.class);
                
                DimensionComponent dim = new DimensionComponent();
                if(mEM.hasComponent(e, DimensionComponent.class)){
                    dim = mEM.getComponent(e, DimensionComponent.class);
                }      
                if(mEM.hasComponent(e, ImageComponent.class)){
                    ImageComponent img = mEM.getComponent(e, ImageComponent.class);
                    
                    if(dim.width == 0.0 && dim.height == 0.0){ // If this component doesn't have a DimensionComponent, take the dims from the image
                        dim.width = img.tex.getWidth();
                        dim.height = img.tex.getHeight();
                    }
                    int scaledWidth = (int)(dim.width * dim.scale);
                    int scaledHeight = (int)(dim.height * dim.scale);
                    g.drawImage(
                            img.tex, 
                            (int)pos.x - scaledWidth/2,
                            (int)pos.y - scaledHeight/2, 
                            (int)(dim.width * dim.scale), 
                            (int)(dim.height * dim.scale), 
                            null);
                }   
            }
        }
    }
}
