package GameStates;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

public class StateMachine {
    private BaseState mCurrentState = null;
    private Map<Class<? extends BaseState>, BaseState> mStates;
    
    private static class StateMachineHolder{
        public final static StateMachine instance = new StateMachine();
    }
    
    public static StateMachine getInstance(){
        return StateMachineHolder.instance;
    }
    
    private StateMachine(){
        mStates = new HashMap<>();
    }
    
    public void addState(BaseState state){
        if(!this.hasState(state.getClass())){
            mStates.put(state.getClass(), state);
        }
    }
    
    public <S extends BaseState> void changeState(Class<S> type){
        if(this.hasState(type) && mCurrentState.getClass() != type){
            mCurrentState.onExit();
            mCurrentState = mStates.get(type);
            mCurrentState.onEnter();
        }
    }
    
    private <S extends BaseState> boolean hasState(Class<S> type){
        return mStates.containsKey(type);
    }
    
    public void initialize(){
        this.addState(new StartState());
        
        for(BaseState bs : mStates.values()){
            bs.initialize();
        }
        mCurrentState = mStates.get(StartState.class);
        this.onEnter();
    }
    
    public void onEnter(){
        mCurrentState.onEnter();
    }
    
    public void update(){
        mCurrentState.update();
    }
    
    public void render(Graphics g){
        mCurrentState.render(g);
    }
    
    public void onExit(){
        mCurrentState.onExit();
    }
    
    public void reset(){
        mCurrentState.reset();
    }
}
