package ultiz;

import static ultiz.Constants.EnemyConstants.*;

import main.Game;

public class Constants {

	public static class EnemyConstants {
		public static final int PROFESSOR=0;
		public static final int TRAP = 1;

		public static final int IDLE_LEFT=0;
		public static final int IDLE_RIGHT=1;
		public static final int RUN_LEFT=2;
		public static final int RUN_RIGHT=3;
		public static final int ATTACK=4;
		
		public static final int PROFESSOR_WIDTH_DEFAULT=64;
		public static final int PROFESSOR_HEIGHT_DEFAULT=64;
		public static final int TRAP_WIDTH_DEFAULT=32;
		public static final int TRAP_HEIGHT_DEFAULT=32;

		public static final int PROFESSOR_WIDTH=(int)(0.8*PROFESSOR_WIDTH_DEFAULT*Game.SCALE);
		public static final int PROFESSOR_HEIGHT=(int)(0.7*PROFESSOR_HEIGHT_DEFAULT*Game.SCALE);
		public static final int TRAP_WIDTH=(int)(0.8*TRAP_WIDTH_DEFAULT*Game.SCALE);
		public static final int TRAP_HEIGHT=(int)(0.7*TRAP_HEIGHT_DEFAULT*Game.SCALE);

		public static final int PROFESSOR_DRAWOFFSET_X=(int)(0.8*20*Game.SCALE);
		public static final int PROFESSOR_DRAWOFFSET_Y=(int)(1.0*10*Game.SCALE);
		

		public static int GetSpriteAmount(int enemy_type, int enemy_state){
			switch (enemy_type) {
				case PROFESSOR:
					switch (enemy_state) {
						case RUN_LEFT:
						case RUN_RIGHT:
							return 9;
						case IDLE_LEFT:
						case IDLE_RIGHT:
							return 1;
						
					}
				case TRAP:
                    return 1;  
			}
			return 0;
		}

	}

	public static class Environment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
		
		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);

	}
	
	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}

		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;	
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);	
		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
			
			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER__WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}
	
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
		
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int HIT = 4;
		public static final int DEAD = 5;
	
		
		public static int GetSpriteAmount(int player_action) {
			
			switch(player_action) {
			
			case RUNNING:
				return 6;
			case IDLE:
				return 9;
			case HIT:
				return 2;
			case DEAD:
				return 5;
			case JUMP:
			case FALLING:
			default:
				return 1;
			
			}			
		}
		
	}
	
	public static int GetEnemyDmg(int enemy_type){
		switch (enemy_type) {
			case PROFESSOR:
				return -1;
			case TRAP:
			    return -3;
			default:
				return 0;
		}
	}
}
