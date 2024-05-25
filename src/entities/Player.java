package entities;

import static ultiz.Constants.*;
import static ultiz.Constants.PlayerConstants.*;
import static ultiz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Playing;
import main.Game;
import ultiz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;

	private boolean moving = false; //attacking = false;
	private boolean left, right, jump;
	private int[][] lvlData;
	private float xDrawOffSet = 15 * Game.SCALE;
	private float yDrawOffSet = 9 * Game.SCALE;
	
	// Jumping / Gravity
	private float jumpSpeed = -2.4f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.4f * Game.SCALE;

	// StatusBar
	private BufferedImage[] statusBarImg;
	private int statusBarWidth = (int) (32 * Game.SCALE);
	private int statusBarHeight = (int) (32 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	// Knowledge
	private BufferedImage knowledgeBook;
	private int bookCountWidth = (int) (28 * Game.SCALE);
	private int bookCountHeight = (int) (35 * Game.SCALE);
	private int bookCountX = (int) (50 * Game.SCALE);
	private int bookCountY = (int) (7 * Game.SCALE);

	private int flipX = 0;
	private int flipW = 1;

	private Playing playing;
	private int tileY=0;

	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.health = 3;
		this.books = 0;
		this.walkSpeed = 1.0f * Game.SCALE;
		loadAnimations();
		initHitbox(17, 30);
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}
	
	public void update() {
		//updateHealthBar();
		if(health <= 0){
			if (state != DEAD) {
				state = DEAD;
				aniTick = 0;
				aniIndex = 0;
				playing.setPlayerDying(true);
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
			}
			else if (aniIndex == GetSpriteAmount(DEAD)-1 && aniTick >= ANI_SPEED - 1){
				playing.setGameOver(true);
				// playing.getGame().getAudioPlayer().stopSong();
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
			} else {
				updateAnimationTick();
			}
			
			return;
		}
		updatePos();
		if (moving) {
			checkBooksTouched();
			checkTrapsTouched();
			tileY = (int) (hitbox.y/Game.TILES_SIZE);
		}
		updateAnimationTick();
		setAnimation();

	}
	
	// private void updateHealthBar() {	
	// }

	private void checkTrapsTouched() {
		playing.checkTrapsTouched(this);
	}

	private void checkBooksTouched() {
		playing.checkBooksTouched(hitbox);
	}

	public void render(Graphics g, int lvlOffSet) {
		g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xDrawOffSet) - lvlOffSet + flipX, 
										(int) (hitbox.y - yDrawOffSet), width*flipW, height, null);
		//drawHitbox(g, lvlOffSet);
		drawUI(g);
	}
	
	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg[health], statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		if (books >= 1)
			g.drawImage(knowledgeBook, bookCountX, bookCountY, bookCountWidth, bookCountHeight, null);
		if (books >= 2)
			g.drawImage(knowledgeBook, bookCountX + 35, bookCountY, bookCountWidth, bookCountHeight, null);
	 	if (books == 3)
			g.drawImage(knowledgeBook, bookCountX + 70, bookCountY, bookCountWidth, bookCountHeight, null);
	}

	private void updateAnimationTick() {
		
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
				//attacking = false;
			}
			
		}
		
	}
	
	private void setAnimation() {
		int startAni = state;
		
		if(moving) 
			state = RUNNING;
		else 
			state = IDLE;
		
		if(inAir) {
			if(airSpeed < 0)
				state = JUMP;
			else
				state = FALLING;
		}
		
		// if(attacking)
		// 	playerAction = ATTACK_1;
		
		if (startAni != state) 
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

		if (!inAir)
			if ((!left && !right) || (right && left))
				return;
		
		float xSpeed = 0;
		
		if (left) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
			
		if (right) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}
		
		if (!inAir) 
			if (!IsEntityOnFloor(hitbox, lvlData)) 
				inAir = true;
	
		if (inAir) {
			
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
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
		playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
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

	public void changeHealth(int value){
		this.health += value;
		if (this.health <= 0){
			this.health = 0;
			//game over
		} else if (this.health >= 3) { 
			this.health = 3;
		}
	}

	public void kill() {
        this.health = 0;
    }

	public void increaseKnowledge() {
		this.books++;
		if (this.books >= 3)
			this.books = 3;
	}

	private void loadAnimations() {
	
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[6][9];
			// IDLE	
			for (int i = 0; i < 9; i++) 
				animations[0][i] = img.getSubimage(i * 32, 0, 32, 32);
			for (int i = 0; i < 6; i++) 
				animations[1][i] = img.getSubimage((i+9) * 32, 0, 32, 32);
			for (int i = 0; i < 1; i++) 
				animations[2][i] = img.getSubimage((i+15) * 32, 0, 32, 32);
			for (int i = 0; i < 1; i++) 
				animations[3][i] = img.getSubimage((i+15) * 32, 0, 32, 32);
			for (int i = 0; i < 2; i++) 
				animations[4][i] = img.getSubimage((i+16) * 32, 0, 32, 32);
			for (int i = 0; i < 5; i++) 
				animations[5][i] = img.getSubimage((i+18) * 32, 0, 32, 32);
			
			// Health
			BufferedImage statusBar = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
			statusBarImg = new BufferedImage[4];
			for (int i = 0; i < 4; i++) 
				statusBarImg[i] = statusBar.getSubimage(i * 16, 0, 16,16);

			// Knowledge
			BufferedImage book = LoadSave.GetSpriteAtlas(LoadSave.KNOWLEDGE_BOOK_ATLAS);
			knowledgeBook = book.getSubimage(0, 0, 28, 35);
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
	}
	
	// public void setAttacking(boolean attacking) {
	// 	this.attacking = attacking;
	// }
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public int getBooks() {
		return books;
	}

	public void resetAll() {
		resetDirBooleans();
		resetInAir();
		moving = false;
		airSpeed = 0f;
		jump = false;
		state = IDLE;
		health = 3;
		books = 0;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvlData)) 
				inAir = true;
	}
	
	public int getTileY(){
		return tileY;
	}
}
