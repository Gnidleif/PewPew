package EntityHandling;

import EntityHandling.Components.*;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import pewpew.AssetManager;
import pewpew.Game;

public class EntityFactory {
    
    private static class EntityFactoryHolder{
        public static EntityFactory instance = new EntityFactory();
    }
    
    public static EntityFactory getInstance(){
        return EntityFactoryHolder.instance;
    }
    
    private EntityFactory(){
        
    }
}
