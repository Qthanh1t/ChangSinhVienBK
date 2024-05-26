package objects;

import static ultiz.Constants.ANI_SPEED;
import static ultiz.Constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class GameObject {

    protected int x, y, objType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset = 5, yDrawOffset = 5;

    public GameObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if(aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(objType)) {
                aniIndex = 0;
                if(objType == GIRL_LEFT || objType == GIRL_RIGHT)
                    doAnimation = false;
                if(objType == BOOK_FX) active = false;
            }   
        }
    }

    public void reset() {
        aniIndex = 0;
        aniTick = 0;
        active = true;

        if (objType == KNOWLEDGE_BOOK)
            doAnimation = true;
        else doAnimation = false;
        
    }

    protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));	
	}

    public void drawHitbox(Graphics g, int xLvlOffset) {
		// For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

    public int getObjType() {
        return objType;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public int getAniIndex() {
        return aniIndex;
    }
    public int getAniTick(){
        return aniTick;
    }
    public void setAnimation(boolean doAnimation){
        this.doAnimation=doAnimation;
    }
}
