package entities;

import static ultiz.Constants.EnemyConstants.*;

public class Professor extends Enemy {

    public Professor(float x, float y) {
        super(x, y, PROFESSOR_WIDTH, PROFESSOR_HEIGHT, PROFESSOR);
    }

}
