package objects;

import main.Game;

public class GameAddict extends GameObject {

    public GameAddict(int x, int y, int objType) {
        super(x, y, objType);
<<<<<<< HEAD
        initHitbox(22, 14);
        xDrawOffset = (int) (6 * Game.SCALE);
        yDrawOffset = (int) (9 * Game.SCALE);

=======
        initHitbox(25, 25);
        xDrawOffset = 0;
        yDrawOffset = 0;
>>>>>>> origin/dev
        hitbox.y += yDrawOffset;
    }

}
