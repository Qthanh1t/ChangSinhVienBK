package objects;

import main.Game;

public class GameAddict extends GameObject {

    public GameAddict(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(25, 25);
        xDrawOffset = 0;
        yDrawOffset = 0;
        hitbox.y += yDrawOffset;
    }

}
