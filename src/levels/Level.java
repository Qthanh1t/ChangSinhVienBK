package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Professor;
import main.Game;
import static ultiz.HelpMethods.GetLevelData;
import static ultiz.HelpMethods.GetPlayerSpawn;
import static ultiz.HelpMethods.GetProfessors;;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Professor> professors;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	
	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		professors = GetProfessors(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public ArrayList<Professor> getProfessors() {
		return professors;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
}
