package Systems;

import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.DimensionComponent;
import EntityHandling.Components.ImageComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.RadiusComponent;
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
            RenderComponent render = mEM.getComponent(e, RenderComponent.class);
            if(!render.visible){
                continue;
            }
            if(mEM.hasComponent(e, PositionComponent.class)){
                PositionComponent pos = mEM.getComponent(e, PositionComponent.class);

                if(mEM.hasComponent(e, ColorComponent.class) && mEM.hasComponent(e, RadiusComponent.class)){ // YOU CAN ONLY DRAW COLORED CIRCLES OKAY!?
                    ColorComponent color = mEM.getComponent(e, ColorComponent.class);
                    RadiusComponent rad = mEM.getComponent(e, RadiusComponent.class);
                    
                    int diameter = (int)(rad.radius * 2);
                    g.setColor(color.color);
                    g.fillOval(
                            (int)(pos.x - rad.radius), 
                            (int)(pos.y - rad.radius),
                            diameter,
                            diameter);
                }
                
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
