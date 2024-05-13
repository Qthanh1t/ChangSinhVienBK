package entities;

import static ultiz.Constants.PlayerConstants.*;
import static ultiz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import ultiz.LoadSave;

public class Player extends Entity {

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.0f * Game.SCALE;
	private int[][] lvlData;
	private float xDrawOffSet = 9 * Game.SCALE;
	private float yDrawOffSet = 9 * Game.SCALE;
	
	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.3f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.4f * Game.SCALE;
	private boolean inAir = false;
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, (int) (23 * Game.SCALE), (int) (30 * Game.SCALE));
	}
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();

	}
	
	public void render(Graphics g, int lvlOffSet) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffSet) - lvlOffSet, 
										(int) (hitbox.y - yDrawOffSet), width, height, null);
		// drawHitbox(g);
	}
	
	private void updateAnimationTick() {
		
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
			
		}
		
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		
		if(moving) 
			playerAction = RUNNING;
		else 
			playerAction = IDLE;
		
		if(inAir) {
			if(airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}
		
		if(attacking)
			playerAction = ATTACK_1;
		
		if (startAni != playerAction) 
			resetAniTick();
			
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
		
	}

	private void updatePos() {
		moving = false;
		
		if (jump)
			jump();

		// if (!left && !right && !inAir)
		// 	return;

		if (!inAir)
			if ((!left && !right) || (right && left))
				return;
		
		float xSpeed = 0;
		
		if (left) 
			xSpeed -= playerSpeed;
		if (right) 
			xSpeed += playerSpeed;
		
		if (!inAir) 
			if (!IsEntityOnFloor(hitbox, lvlData)) 
				inAir = true;
	
		if (inAir) {
			
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0) 
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
			
		} else 
			updateXPos(xSpeed);
		moving = true;
		
	}
	
	private void jump() {
		if (inAir) 
			return;
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
		
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXNextToWall(hitbox, xSpeed);
		}
		
	}

	private void loadAnimations() {
	
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[6][9];
			//idle	
			for (int i = 0; i < 9; i++) 
				animations[0][i] = img.getSubimage(i * 32, 0, 32, 32);
			for (int i = 0; i < 6; i++) 
				animations[1][i] = img.getSubimage((i+9) * 32, 0, 32, 32);
			for (int i = 0; i < 1; i++) 
				animations[2][i] = img.getSubimage((i+15) * 32, 0, 32, 32);
			for (int i = 0; i < 1; i++) 
				animations[3][i] = img.getSubimage((i+15) * 32, 0, 32, 32);
			
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
}
