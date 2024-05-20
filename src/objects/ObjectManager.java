package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import ultiz.LoadSave;
import static ultiz.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[] bookImgs;
    private BufferedImage testPosImg;
    private ArrayList<Book> knowledgeBook;
    private ArrayList<TestPosition> testPosition;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();

        knowledgeBook = new ArrayList<>();
        knowledgeBook.add(new Book(300, 300, KNOWLEDGE_BOOK));
        knowledgeBook.add(new Book(400, 300, KNOWLEDGE_BOOK));
        
        testPosition = new ArrayList<>();
        testPosition.add(new TestPosition(500, 300, TEST_POSITION));
    }

    private void loadImgs() {
        BufferedImage bookSprite = LoadSave.GetSpriteAtlas(LoadSave.KNOWLEDGE_BOOK_ATLAS);
        bookImgs = new BufferedImage[42];

        for (int i = 0; i < bookImgs.length; i++) 
            bookImgs[i] = bookSprite.getSubimage(28 * i, 0, 28, 35);

        testPosImg = LoadSave.GetSpriteAtlas(LoadSave.TEST_POSITION_IMG);
    }

    public void update() {
        for (Book b : knowledgeBook)
            if (b.isActive())
                b.update();
        
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBook(g, xLvlOffset);
        drawTestPos(g, xLvlOffset);
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
        for (Book b : knowledgeBook)
            if (b.isActive())
                g.drawImage(bookImgs[b.getAniIndex()],
                    (int) (b.getHitbox().x - b.getxDrawOffset() - xLvlOffset),
                    (int) (b.getHitbox().y - b.getyDrawOffset()), 
                    KNOWLEDGE_BOOK_WIDTH,
                    KNOWLEDGE_BOOK_HEIGHT,
                    null);

    }

}
