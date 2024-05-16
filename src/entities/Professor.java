package entities;

import static ultiz.Constants.Directions.*;
import static ultiz.Constants.EnemyConstants.*;
import static ultiz.HelpMethods.*;
import main.Game;

public class Professor extends Enemy {

    public Professor(float x, float y) {
        super(x, y, PROFESSOR_WIDTH, PROFESSOR_HEIGHT, PROFESSOR);
        initHitbox(x, y,(int) (20*Game.SCALE), (int) (31*Game.SCALE));
    }

    public void update(int[][] lvlData, Player player){
        updateMove(lvlData, player);
        updateAnimationTick();
    }


    public void updateMove(int[][] lvlData, Player player){
        if(firstUpdate){
            firstUpdateCheck(lvlData);
        }

        if(inAir){
            updateInAir(lvlData);
        }
        else{
            switch (enemyState) {
                case RUN_LEFT:
                case RUN_RIGHT:
                    // if(canSeePlayer(lvlData, player)){
                    //     turnTowardsPlayer(player);
                    // }
                    // if(isPlayerCloseForAttack(player)){
                    //     newState(ATTACK);
                    // }
                    move(lvlData);

                    break;
                default:
                    newState(RUN_LEFT);
                    break;
            }
        }
    }
}
