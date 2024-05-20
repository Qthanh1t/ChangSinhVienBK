package objects;

import static ultiz.Constants.ObjectConstants.TRAP;

public class GameAddict extends GameObject {

    public GameAddict(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(32, 32);
        xDrawOffset = 0;
        yDrawOffset = 0;
        hitbox.y += yDrawOffset;
    }

}
