package Systems;

import java.util.HashMap;

public class SystemManager {
    private HashMap<Class, LogicSystem> mSystems;
    
    private static class SystemManagerHolder{
        public static final SystemManager instance = new SystemManager();
    }
    
    public static SystemManager getInstance(){
        return SystemManagerHolder.instance;
    }
    
    private SystemManager(){
        mSystems = new HashMap<>();
    }
    
    public void initialize(){
        new Render2DSystem();
        new MoveSystem();
        new CollisionSystem();
    }
    
    public <L extends LogicSystem> void addSystem(L system){
        if(!mSystems.containsKey(system.getClass())){
            mSystems.put(system.getClass(), system);
            System.out.println("System added");
        }
    }
    
    public <L extends LogicSystem> L getSystem(Class<L> type){
        if(mSystems.containsKey(type)){
            return (L) mSystems.get(type);
        }
        return null;
    }
    
    public <L extends LogicSystem> void removeSystem(Class<L> type){
        if(mSystems.containsKey(type)){
            mSystems.remove(type);
        }
    }
    
    public <L extends LogicSystem> void startSystem(Class<L> type){
        if(mSystems.containsKey(type)){
            mSystems.get(type).start();
        }
    }
    
    public <L extends LogicSystem> void stopSystem(Class<L> type){
        if(mSystems.containsKey(type)){
            mSystems.get(type).stop();
        }
    }
    
    public <L extends LogicSystem> boolean isSystemRunning(Class<L> type){
        if(!mSystems.containsKey(type)){
            throw new IllegalArgumentException("IsSystemRunning fail: There's no existing system of that class.");
        }
        return mSystems.get(type).isRunning();
    }
    
    public void update(double dt){
        for(LogicSystem ls : mSystems.values()){
            if(ls.isRunning()){
                ls.update(dt);
            }
        }
    }
}
