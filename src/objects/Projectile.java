package objects;

import static ultiz.Constants.ObjectConstants.*;
import static ultiz.Constants.Projectiles.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Projectile {
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active=true;

    public Projectile(int x, int y, int dir){
        int xOffset=(int)(-3 * Game.SCALE);
        int yOffset=(int)(2 * Game.SCALE);
        if(dir==1)
            xOffset = (int)(29*Game.SCALE);
        hitbox = new Rectangle2D.Float(x+xOffset,y+yOffset,HEART_WIDTH,HEART_HEIGHT);
        this.dir=dir;
    }
    public void updatePos(){
        hitbox.x += dir*SPEED;
    }
    public void setPos(int x, int y){
        hitbox.x=x;
        hitbox.y=y;
    }
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
    public void setActive(boolean active){
        this.active=active;
    }
    public boolean isActive(){
        return active;
    }
    public void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }
}
