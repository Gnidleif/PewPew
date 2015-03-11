package EntityHandling;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import pewpew.AssetManager;

public class Components {
    public static abstract class Component{
    }   
    
    public static class PositionComponent extends Component{
        public double x = 0.0, y = 0.0;
        
        public PositionComponent(){}
        public PositionComponent(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
    
    public static class DimensionComponent extends Component{
        public double width = 0.0, height = 0.0, scale = 1.0;
        
        public DimensionComponent(){}
        public DimensionComponent(double w, double h, double s){
            this.width = w;
            this.height = h;
            this.scale = s;
        }
    }
    
    public static class VelocityComponent extends Component{
        public double x = 0.0, y = 0.0;
        
        public VelocityComponent(){}
        public VelocityComponent(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
    
    public static class AccelerationComponent extends Component{
        public double xAcc = 1.0, yAcc = 1.0;
        
        public AccelerationComponent(){}
        public AccelerationComponent(double xAcc, double yAcc){
            this.xAcc = xAcc;
            this.yAcc = yAcc;
        }
    }
    
    public static class RenderComponent extends Component{
        public boolean visible = true;
        public int layer = 0;
        
        public RenderComponent(){}
        public RenderComponent(boolean visible, int layer){
            this.visible = visible;
            this.layer = layer;
        }
    }
    
    public static class CollisionComponent extends Component{
        public boolean collidable = true;
        public Rectangle2D.Double square = new Rectangle2D.Double();
        
        public CollisionComponent(){}
        public CollisionComponent(boolean collidable, Rectangle2D.Double square){
            this.collidable = collidable;
            this.square = square;
        }
    }
    
    public static class ScreenCollisionComponent extends Component{
        public boolean collidable = true;
        
        public ScreenCollisionComponent(){}
        public ScreenCollisionComponent(boolean collidable){
            this.collidable = collidable;
        }
    }
    
    public static class ColorComponent extends Component{
        public Color color = Color.red;
        
        public ColorComponent(){}
        public ColorComponent(Color color){
            this.color = color;
        }
    }
    
    public static class ImageComponent extends Component{
        public BufferedImage tex = AssetManager.getInstance().getBufferedImage("/sprites/placeholder.png");
        
        public ImageComponent(){}
        public ImageComponent(BufferedImage tex){
            this.tex = tex;
        }
    }
}