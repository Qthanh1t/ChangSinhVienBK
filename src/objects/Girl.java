package objects;

import static ultiz.Constants.ObjectConstants.*;

import main.Game;

public class Girl extends GameObject {

    private int tileY;

    public Girl(int x, int y, int objType) {
        super(x, y, objType);
        tileY =(int) (y/Game.TILES_SIZE);
        initHitbox(GIRL_WIDTH, GIRL_HEIGHT);

        hitbox.y -= (int) (2 * Game.SCALE);
        // sua vi tri hit box
    }

    public void update(){
        if(doAnimation)
            updateAnimationTick();
    }
    public int getTileY(){
        return tileY;
    }

}
