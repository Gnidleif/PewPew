package Systems;

import EntityHandling.EntityManager;

public abstract class LogicSystem {
    protected EntityManager mEM = EntityManager.getInstance();
    private boolean mRunning = true;
    
    public boolean isRunning(){
        return mRunning;
    }
    
    public void start(){
        mRunning = true;
    }
    
    public void stop(){
        mRunning = false;
    }
}
