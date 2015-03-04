package EntityHandling;

import EntityHandling.Components.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EntityManager {
    private final List<UUID> mEntities;
    private final HashMap<Class, HashMap<UUID, ? extends Component>> mComponentStores;
    
    private static class EntityManagerHolder{
        public static final EntityManager instance = new EntityManager();
    }
    
    public static EntityManager getInstance(){
        return EntityManagerHolder.instance;
    }
    
    private EntityManager(){
        mEntities = new LinkedList<>();
        mComponentStores = new HashMap<>();
    }
    
    public UUID createEntity(){
        UUID id = UUID.randomUUID();
        mEntities.add(id);
        return id;
    }
    
    public <C extends Component> void addComponent(UUID entity, C component){
        HashMap<UUID, ? extends Component> store = mComponentStores.get(component.getClass());
        
        if(store == null){
            store = new HashMap<UUID, C>();
            mComponentStores.put(component.getClass(), store);
        }
        ((HashMap<UUID, C>)store).put(entity, component);
    }
    
    public <C extends Component> boolean hasComponent(UUID entity, Class<C> type){
        HashMap<UUID, ? extends Component> store = mComponentStores.get(type);
        if(store == null){
            return false;
        }
        return store.containsKey(entity);
    }
    
    public <C extends Component> C getComponent(UUID entity, Class<C> type){
        HashMap<UUID, ? extends Component> store = mComponentStores.get(type);
        
        if(store == null){
            throw new IllegalArgumentException("Get fail: No components of that class exists.");
        }
        C result = (C) store.get(entity);
        if(result == null){
            throw new IllegalArgumentException("Get fail: Entity does not contain that component.");
        }
        return result;
    }
    
    public <C extends Component> void removeComponent(UUID entity, Class<C> type){
        HashMap<UUID, ? extends Component> store = mComponentStores.get(type);
        if(store == null){
            throw new IllegalArgumentException("Remove fail: No component of that class exists.");
        }
        
        C result = (C)store.remove(entity);
        if(result == null){
            throw new IllegalArgumentException("Remove fail: Entity does not contain that component.");
        }
    }
    
    public <C extends Component> List<C> getAllComponentsOfEntity(UUID entity){
        LinkedList<C> components = new LinkedList<>();
        for(HashMap<UUID, ? extends Component> store : mComponentStores.values()){
            if(store == null){
                continue;
            }
            C componentsOfEntity = (C)store.get(entity);
            if(componentsOfEntity != null){
                components.addLast(componentsOfEntity);
            }
        }
        return components;
    }
    
    public <C extends Component> List<C> getAllComponentsOfType(Class<C> type){
        HashMap<UUID, ? extends Component> store = mComponentStores.get(type);
        
        if(store == null){
            return new LinkedList<C>();
        }
        List<C> result = new ArrayList<C>((Collection<C>)store.values());
        return result;
    }
    
    public <C extends Component> Set<UUID> getAllEntitiesOwningType(Class<C> type){
        HashMap<UUID, ? extends Component> store = mComponentStores.get(type);
        
        if(store == null){
            return new HashSet<UUID>();
        }
        
        return store.keySet();
    }
    
    public void killEntity(UUID entity){
        for(HashMap<UUID, ? extends Component> store : mComponentStores.values()){
            store.remove(entity);
        }
        mEntities.remove(entity);
    }
}
