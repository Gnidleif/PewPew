package pewpew;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class AssetManager {
    private final HashMap<String, BufferedImage> mBufferedImages;
    
    private static class AssetManagerHolder{
        public static final AssetManager instance = new AssetManager();
    }
    
    public static AssetManager getInstance(){
        return AssetManagerHolder.instance;
    }
    
    private AssetManager(){
        mBufferedImages = new HashMap<>();
        addBufferedImage("/sprites/placeholder.png");
    }
    
    private boolean addBufferedImage(String path) {
        BufferedImage img;
        try{
            img = ImageIO.read(getClass().getResourceAsStream(path));
            if(img != null){
                mBufferedImages.put(path, img);
                return true;
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace(System.out);
        }
        catch(IllegalArgumentException iae){
            return false;
        }
        return false;
    }
    
    public BufferedImage getBufferedImage(String path){
        BufferedImage img = mBufferedImages.get("/sprites/placeholder.png");
        if(!mBufferedImages.containsKey(path)){
            if(addBufferedImage(path)){
                img = mBufferedImages.get(path);
            }
        }
        else{
            img = mBufferedImages.get(path);
        }
        return img;
    }
}
