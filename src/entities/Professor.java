package entities;

import static ultiz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Professor extends Enemy {

    private int attackBoxOffsetX;
    private int count=0;
    public Professor(float x, float y) {
        super(x, y, PROFESSOR_WIDTH, PROFESSOR_HEIGHT, PROFESSOR);
        initHitbox(20, 31);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int) (20 * Game.SCALE), (int) (31 * Game.SCALE));
        attackBoxOffsetX = 0;
    }

    public void update(int[][] lvlData, Player player){
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }


    private void updateAttackBox() {
        attackBox.x=hitbox.x-attackBoxOffsetX;
        attackBox.y=hitbox.y;
    }

    public void updateBehavior(int[][] lvlData, Player player){
        if(firstUpdate){
            firstUpdateCheck(lvlData);
        }

        if(inAir){
            updateInAir(lvlData);
        }
        else{
            switch (state) {
                case RUN_LEFT:
                case RUN_RIGHT:
                    // if(canSeePlayer(lvlData, player)){
                    //     turnTowardsPlayer(player);
                    // }
                    // if(isPlayerCloseForAttack(player)){
                    //     newState(ATTACK);
                    // }
                    move(lvlData);
                    if(!attackChecked){
                        CheckEnemyHit(attackBox,player);
                    }
                    else {
                        if(count >= 200){
                            attackChecked = false;
                            count = 0;
                        } 
                        else count++;
                    }
                    break;
                default:
                    newState(RUN_LEFT);
                    break;
            }
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)(attackBox.x - xLvlOffset),(int) attackBox.y,(int) attackBox.width,(int) attackBox.height);
    }
}
