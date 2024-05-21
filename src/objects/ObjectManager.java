package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import ultiz.LoadSave;
import static ultiz.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[] bookImgs;
    private BufferedImage testPosImg;
    private BufferedImage trapImg;
    private ArrayList<Book> knowledgeBooks;
    private ArrayList<TestPosition> testPosition;
    private ArrayList<GameAddict> traps;


    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void checkTrapsTouched(Player p) {
        for (GameAddict t : traps)
            if (t.getHitbox().intersects(p.getHitbox()))
                p.kill(); 
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Book b : knowledgeBooks)
            if (b.isActive())
                if (hitbox.intersects(b.getHitbox())) {
                    b.setActive(false);
                    applyEffectToPlayer(b);
                }

        for (TestPosition t : testPosition)
            if (hitbox.intersects(t.getHitbox())) {
                if (playing.getPlayer().getBooks() == 3) 
                    playing.setLevelCompleted(true);
            }
    }

    public void applyEffectToPlayer(Book b) {
        if (b.getObjType() == KNOWLEDGE_BOOK)
            playing.getPlayer().increaseKnowledge();
    }

    public void loadObjects(Level newLevel) {
        knowledgeBooks = newLevel.getBooks();
        testPosition = newLevel.getTestPos();
        traps = newLevel.getTraps();
    }

    private void loadImgs() {
        BufferedImage bookSprite = LoadSave.GetSpriteAtlas(LoadSave.KNOWLEDGE_BOOK_ATLAS);
        bookImgs = new BufferedImage[42];

        for (int i = 0; i < bookImgs.length; i++) 
            bookImgs[i] = bookSprite.getSubimage(28 * i, 0, 28, 35);

        testPosImg = LoadSave.GetSpriteAtlas(LoadSave.TEST_POSITION_IMG);

        trapImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
    }

    public void update() {
        for (Book b : knowledgeBooks)
            if (b.isActive())
                b.update();
        
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBook(g, xLvlOffset);
        drawTestPos(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (GameAddict t : traps) {
            g.drawImage(trapImg,
                (int) (t.getHitbox().x - xLvlOffset),
                (int) (t.getHitbox().y - t.getyDrawOffset()), 
                TRAP_WIDTH,
                TRAP_HEIGHT,
                null);
            t.drawHitbox(g, xLvlOffset);
        }
    }
    
    private void drawTestPos(Graphics g, int xLvlOffset) {
        for (TestPosition t : testPosition)
            g.drawImage(testPosImg,
                    (int) (t.getHitbox().x - t.getxDrawOffset() - xLvlOffset),
                    (int) (t.getHitbox().y - t.getyDrawOffset()), 
                    TEST_POSITION_WIDTH,
                    TEST_POSITION_HEIGHT,
                    null);
    }

    private void drawBook(Graphics g, int xLvlOffset) {
        for (Book b : knowledgeBooks)
            if (b.isActive())
                g.drawImage(bookImgs[b.getAniIndex()],
                    (int) (b.getHitbox().x - b.getxDrawOffset() - xLvlOffset),
                    (int) (b.getHitbox().y - b.getyDrawOffset()), 
                    KNOWLEDGE_BOOK_WIDTH,
                    KNOWLEDGE_BOOK_HEIGHT,
                    null);

    }

    public void resetAllObjects() {
        for (Book b : knowledgeBooks)
            b.reset();

    }
}
