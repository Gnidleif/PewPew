package Systems;

import EntityHandling.EntityManager;

public abstract class LogicSystem {
    private boolean mRunning;
    protected final EntityManager mEM;
    
    public LogicSystem(){
        mRunning = true;
        mEM = EntityManager.getInstance();
        SystemManager.getInstance().addSystem(this);
    }
    
    public void start(){
        mRunning = true;
    }
    
    public void stop(){
        mRunning = false;
    }
    
    public boolean isRunning(){
        return mRunning;
    }
        
    public abstract void update(double dt);
}
