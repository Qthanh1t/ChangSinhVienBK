package entities;

import static ultiz.Constants.Directions.*;
import static ultiz.Constants.EnemyConstants.*;
import static ultiz.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Trap extends Enemy {

    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    
    public Trap(float x, float y) {
        super(x, y, TRAP_WIDTH, TRAP_HEIGHT, TRAP);
        initHitbox(x, y,(int) (20*Game.SCALE), (int) (20*Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int) (20*Game.SCALE), (int) (20*Game.SCALE));
        attackBoxOffsetX = 0;
    }

    public void update(int[][] lvlData, Player player){
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }


    private void updateAttackBox() {
        attackBox.x=hitbox.x;
        attackBox.y=hitbox.y;
    }

    public void updateBehavior(int[][] lvlData, Player player){
        if(firstUpdate){
            firstUpdateCheck(lvlData);
        }
    }

    public void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.BLUE);
        g.drawRect((int)(attackBox.x - xLvlOffset),(int) attackBox.y,(int) attackBox.width,(int) attackBox.height);
       /*
            g.setColor(new Color(255, 255, 0, 100)); // Màu và mờ
            g.fillRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
        */

    }
}
