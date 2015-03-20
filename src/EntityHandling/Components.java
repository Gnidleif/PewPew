package EntityHandling;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import pewpew.AssetManager;

public class Components {
    public static abstract class Component{
    }   
    
    // Component used by any object requiring a position in the world
    public static class PositionComponent extends Component{
        public double x = 0.0, y = 0.0;
        
        public PositionComponent(){}
        public PositionComponent(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
    
    // Component used by any object requiring a width/height/scale in the world
    public static class DimensionComponent extends Component{
        public double width = 0.0, height = 0.0, scale = 1.0;
        
        public DimensionComponent(){}
        public DimensionComponent(double w, double h, double s){
            this.width = w;
            this.height = h;
            this.scale = s;
        }
    }
    
    // Component used by any object requiring any sort of movement
    public static class VelocityComponent extends Component{
        public double x = 0.0, y = 0.0;
        
        public VelocityComponent(){}
        public VelocityComponent(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
    
    // Component used by any object requiring any acceleration
    public static class AccelerationComponent extends Component{
        public double xAcc = 1.0, yAcc = 1.0;
        
        public AccelerationComponent(){}
        public AccelerationComponent(double xAcc, double yAcc){
            this.xAcc = xAcc;
            this.yAcc = yAcc;
        }
    }
    
    // Any object who's supposed to be rendered on the screen needs this component
    public static class RenderComponent extends Component{
        public boolean visible = true; // Can be set to false if the object's supposed to "turn invisible"
        public int layer = 0; // Used to create an order in which objects are supposed to be rendered, lower number means "further back" on the screen
        
        public RenderComponent(){}
        public RenderComponent(boolean visible, int layer){
            this.visible = visible;
            this.layer = layer;
        }
    }
    
    // Any object that needs the ability to collide with any other object
    // Note: The collision rectangle shouldn't be used as a substitute for the Position- and Dimension Components
    public static class CollisionComponent extends Component{
        public boolean collidable = true;
        public Rectangle2D.Double square = new Rectangle2D.Double();
        
        public CollisionComponent(){}
        public CollisionComponent(boolean collidable, Rectangle2D.Double square){
            this.collidable = collidable;
            this.square = square;
        }
    }
    
    // Any object requring a single color to be drawn on them needs this
    public static class ColorComponent extends Component{
        public Color color = Color.red;
        
        public ColorComponent(){}
        public ColorComponent(Color color){
            this.color = color;
        }
    }
    
    // If an object needs a texture to be drawn on it, this is required
    public static class ImageComponent extends Component{
        public BufferedImage tex = AssetManager.getInstance().getBufferedImage("/sprites/placeholder.png");
        
        public ImageComponent(){}
        public ImageComponent(BufferedImage tex){
            this.tex = tex;
        }
    }
}