package objects;

import static ultiz.Constants.ObjectConstants.*;

import main.Game;

public class TestPosition extends GameObject {

    public TestPosition(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(68, 61);
        doAnimation = false;
        xDrawOffset = (int) (0 * Game.SCALE);
        yDrawOffset = (int) (0 * Game.SCALE);
    }

    // public void update() {

    // }

    

}
