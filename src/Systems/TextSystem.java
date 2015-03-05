package Systems;

import EntityHandling.Components.TextComponent;
import java.awt.Graphics;
import java.util.Set;
import java.util.UUID;

public class TextSystem extends LogicSystem {
    
    public void drawText(Graphics g){
        Set<UUID> entities = mEM.getAllEntitiesOwningType(TextComponent.class);
        for(UUID e : entities){
            TextComponent text = mEM.getComponent(e, TextComponent.class);
            
            g.setColor(text.color);
            g.setFont(text.font);
            g.drawString(text.text, text.x, text.y + text.font.getSize());
        }
    }
}
