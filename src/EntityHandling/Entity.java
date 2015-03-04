package EntityHandling;

import EntityHandling.Components.Component;
import java.util.List;
import java.util.UUID;

public class Entity {
    public final UUID ID;
    private final EntityManager mEM;  // Only holds a pointer to the Singleton to avoid long code
    
    public Entity(){
        mEM = EntityManager.getInstance();
        ID = mEM.createEntity();
        System.out.println("New entity: " + ID);
    }
    
    public Entity(Component... components){
        this();
        for(Component c : components){
            add(c);
        }
    }
    
    public void add(Component component){
        mEM.addComponent(ID, component);
    }
    
    public <C extends Component> boolean has(Class<C> type){
        return mEM.hasComponent(ID, type);
    }
    
    public <C extends Component> C get(Class<C> type){
        return mEM.getComponent(ID, type);
    }
    
    public <C extends Component> List<C> getAll(){
        return mEM.getAllComponentsOfEntity(ID);
    }
    
    public <C extends Component> void remove(Class<C> type){
        mEM.removeComponent(ID, type);
    }
    
    public void removeAll(){
        for(Component c : getAll()){
            remove(c.getClass());
        }
    }
    
    public void kill(){
        mEM.killEntity(ID);
    }
}
