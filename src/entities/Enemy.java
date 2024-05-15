package entities;


import static ultiz.Constants.EnemyConstants.*;
import static ultiz.Constants.Directions.*;
import static ultiz.HelpMethods.*;

import main.Game;
import ultiz.Constants.Directions;
public abstract class Enemy extends Entity {
    private int aniIndex, enemyState=RUN_LEFT, enemyType;
    private int aniTick, aniSpeed=25;
    private boolean firstUpdate=true;
    private boolean inAir;
    private float fallSpeed=0;
    private float gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 0.35f * Game.SCALE;
    private int walkDir=LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType){
        super(x, y, width, height);
        this.enemyType=enemyType;
        initHitbox(x, y, width, height);
    }
    private void updateAnimationTick(){
        aniTick++;
        if(aniTick>=aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(enemyType, enemyState)){
                aniIndex=0;
            }
        }
    }
    public void update(int[][] lvlData){
        updateMove(lvlData);
        updateAnimationTick();
    }

    public void updateMove(int[][] lvlData){
        if(firstUpdate){
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
            firstUpdate = false;
        }

        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y+fallSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y+=fallSpeed;
                fallSpeed+=gravity;
            }
            else{
                inAir = false;
                hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        }
        else{
            switch (enemyState) {
                // case Directions.LEFT:
                //     enemyState=RUN_LEFT;
                //     break;
                // case Directions.RIGHT:
                //     enemyState=RUN_RIGHT;
                //     break;
                case RUN_LEFT:
                case RUN_RIGHT:
                    float xSpeed=0;

                    if(walkDir==LEFT){
                        xSpeed = -walkSpeed;
                    }
                    else{
                        xSpeed = walkSpeed;
                    }
                    if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
                        if(IsFloor(hitbox, xSpeed, lvlData)){
                            hitbox.x+=xSpeed;
                            return;
                        }
                    }
                    changeWalkDir();

                    break;
                default:
                    break;
            }
        }
    }

    private void changeWalkDir() {
        if(walkDir==LEFT){
            walkDir=RIGHT;
            enemyState=RUN_RIGHT;
        } 
        else{
            walkDir=LEFT;
            enemyState=RUN_LEFT;
        } 
    }
    public int getAniIndex(){
        return aniIndex;
    }
    public int getEnemyState(){
        return enemyState;
    }
}
