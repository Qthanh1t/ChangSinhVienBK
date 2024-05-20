package objects;

import main.Game;

public class Book extends GameObject {

    public Book(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(28, 35);
        xDrawOffset = (int) (0 * Game.SCALE);
        yDrawOffset = (int) (0 * Game.SCALE);
    }

    public void update() {
        updateAnimationTick();
    }

}
