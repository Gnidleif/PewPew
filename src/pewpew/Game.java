package pewpew;

import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.Entity;
import GameStates.StateMachine;
import Systems.SystemManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable {
    public static final int
            SCR_WIDTH   = 800,
            SCR_HEIGHT  = 600;
    
    private boolean mRunning;
    private Thread mThread;

    public static Graphics gGraphics;
    private BufferedImage mImage;
    
    private Entity mCamera;
    
    public Game() throws IOException {
        super();
        setPreferredSize(new Dimension(SCR_WIDTH, SCR_HEIGHT));
        setFocusable(true);
        requestFocus();
    }
    
    public void initialize() throws IOException{
        mImage = new BufferedImage(SCR_WIDTH, SCR_HEIGHT, BufferedImage.TYPE_INT_RGB);
        gGraphics = (Graphics2D) mImage.getGraphics();
        StateMachine.getInstance().initialize();
        SystemManager.getInstance().initialize();
        mRunning = true;
        
        mCamera = new Entity(new PositionComponent(0.0, 0.0), new VelocityComponent(0.0, 0.0));
    }
    
    @Override
    public void addNotify(){
        super.addNotify();
        if(mThread == null){
            mThread = new Thread(this);
            mThread.start();
        }
    }
    
    public void update(double dt){
        SystemManager.getInstance().update(dt);
        StateMachine.getInstance().update(dt);
    }
    
    public void draw(){
        StateMachine.getInstance().render(gGraphics);
    }
    
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(
                mImage, 
                (int)mCamera.get(PositionComponent.class).x, 
                (int)mCamera.get(PositionComponent.class).y,
                SCR_WIDTH + (int)mCamera.get(PositionComponent.class).x,
                SCR_HEIGHT + (int)mCamera.get(PositionComponent.class).y,
                null);
        g.dispose();
    }

    @Override
    public void run() {
        try{
            this.initialize();
        }
        catch(IOException ioe){
            ioe.printStackTrace(System.out);
        }
        
        long lastLoopTime =  System.nanoTime();
        int target_fps = 60;
        int fps = 0;
        long optimal_time = 1000000000 / target_fps;
        long lastFpsTime = 0;
        long timeSinceStart = 1;
        
        while(mRunning){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double dt = updateLength / ((double)optimal_time);
            
            lastFpsTime += updateLength;
            fps++;
            
            if(lastFpsTime >= 1000000000){
                System.out.println("FPS: " + fps);
                System.out.println("Seconds running: " + timeSinceStart++);
                lastFpsTime = 0;
                fps = 0;
            }
            
            this.update(dt);
            this.draw();
            this.drawToScreen();
            
            try{
                Thread.sleep(Math.abs((lastLoopTime - System.nanoTime() + optimal_time) / 1000000));
            }
            catch(Exception e){
                
            }
        }
    }
}
