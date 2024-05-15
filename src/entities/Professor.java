package entities;

import static ultiz.Constants.EnemyConstants.*;

import main.Game;

public class Professor extends Enemy {

    public Professor(float x, float y) {
        super(x, y, PROFESSOR_WIDTH, PROFESSOR_HEIGHT, PROFESSOR);
        initHitbox(x, y,(int) (20*Game.SCALE), (int) (31*Game.SCALE));
    }

}
