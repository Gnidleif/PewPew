package pewpew;

import EntityHandling.Components.CollisionComponent;
import EntityHandling.Components.ColorComponent;
import EntityHandling.Components.PositionComponent;
import EntityHandling.Components.RenderComponent;
import EntityHandling.Components.VelocityComponent;
import EntityHandling.Entity;
import GameStates.StateMachine;
import Systems.CollisionSystem;
import Systems.MoveSystem;
import Systems.RenderSystem;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable {
    public static final int
            SCR_WIDTH   = 800,
            SCR_HEIGHT  = 600;
    
    private boolean mRunning;
    private boolean mPaused;
    private Thread mThread;

    private Graphics mGraphics;
    private BufferedImage mImage;
    
    private Entity mCamera;
    
    // SubSystems
    private final CollisionSystem mCS = new CollisionSystem();
    private final MoveSystem mMS = new MoveSystem();
    private final RenderSystem mRS = new RenderSystem();
    
    public Game() throws IOException {
        super();
        setPreferredSize(new Dimension(SCR_WIDTH, SCR_HEIGHT));
        setFocusable(true);
        requestFocus();
    }
    
    public void initialize() throws IOException{
        mImage = new BufferedImage(SCR_WIDTH, SCR_HEIGHT, BufferedImage.TYPE_INT_RGB);
        mGraphics = (Graphics2D) mImage.getGraphics();
        StateMachine.getInstance().initialize();
        mRunning = true;
        mPaused = false;
        mCamera = new Entity(new PositionComponent(0.0, 0.0), new VelocityComponent(0.0, 0.0));
        mCS.createScreenBounds((double)SCR_WIDTH, (double)SCR_HEIGHT, 10);
    }
    
    @Override
    public void addNotify(){
        super.addNotify();
        if(mThread == null){
            mThread = new Thread(this);
            mThread.start();
        }
    }
    
    public void update(){
        mMS.update();
        mCS.update();
    }
    
    public void drawGame(){
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        mRS.draw(g);
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
        
        final int ONE_BILLION = 1000000000;
        final double GAME_HERTZ = 30.0;
        final double TIME_BETWEEN_UPDATES = ONE_BILLION / GAME_HERTZ;
        final int MAX_UPDATES_BEFORE_RENDER = 5;
        double lastUpdateTime = System.nanoTime();
        double lastRenderTime = System.nanoTime();
        
        final double TARGET_FPS = 60;
        final double TARGET_TIME_BETWEEN_RENDERS = ONE_BILLION / TARGET_FPS;
        
        while(mRunning){
            double now = System.nanoTime();
            int updateCount = 0;
            
            if(!mPaused){
                while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER){
                    update();
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }
                
                if(now - lastUpdateTime > TIME_BETWEEN_UPDATES){
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }
                
                drawGame();
                
                while(now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES){
                    Thread.yield();
                    try{
                        Thread.sleep(1);
                    }
                    catch(Exception e){
                        e.printStackTrace(System.out);
                    }
                    now = System.nanoTime();
                }
            }
        }
    }
}
