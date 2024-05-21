package objects;

import main.Game;

public class GameAddict extends GameObject {

    public GameAddict(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(22, 14);
        xDrawOffset = (int) (6 * Game.SCALE);
        yDrawOffset = (int) (9 * Game.SCALE);

        hitbox.y += yDrawOffset;
    }

}
