package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Professor;
import main.Game;
import objects.Book;
import objects.BookFX;
import objects.GameAddict;
import objects.Girl;
import objects.TestPosition;
import ultiz.HelpMethods;

import static ultiz.HelpMethods.GetLevelData;
import static ultiz.HelpMethods.GetPlayerSpawn;
import static ultiz.HelpMethods.GetProfessors;
import static ultiz.HelpMethods.GetTraps;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Professor> professors;
	private ArrayList<Book> knowledgeBooks;
	private ArrayList<BookFX> BookFx;
	private ArrayList<TestPosition> testPosition;
	private ArrayList<GameAddict> traps;
	private ArrayList<Girl> girls;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createBooks();
		createTestPos();
		createTraps();
		createGirls();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	
	private void createGirls() {
		girls = HelpMethods.GetGirls(img);
	}

	private void createTraps() {
		traps = GetTraps(img);
	}

	private void createTestPos() {
		testPosition = HelpMethods.GetTestPos(img);
	}

	private void createBooks() {
		knowledgeBooks = HelpMethods.GetBooks(img);
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

	public ArrayList<GameAddict> getTraps() {
		return traps;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public ArrayList<Book> getBooks() {
		return knowledgeBooks;
	}

	public ArrayList<TestPosition> getTestPos() {
		return testPosition;
	}

	public ArrayList<Girl> getGirls(){
		return girls;
	}
	
}
