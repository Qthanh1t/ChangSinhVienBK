package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import audio.AudioPlayer;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import ultiz.LoadSave;
import static ultiz.Constants.ObjectConstants.*;
import static ultiz.Constants.Projectiles.HEART_HEIGHT;
import static ultiz.Constants.Projectiles.HEART_WIDTH;
import static ultiz.HelpMethods.CanGirlSeePlayer;
import static ultiz.HelpMethods.IsProjectileHittingLevel;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[] bookImgs;
    private BufferedImage[] bookFx;
    private BufferedImage[] girlImgs;
    private BufferedImage testPosImg;
    private BufferedImage trapImg;
    private BufferedImage heartImg;
    private ArrayList<Book> knowledgeBooks;
    private ArrayList<TestPosition> testPosition;
    private ArrayList<GameAddict> traps;
    private ArrayList<Girl> girls;
    private ArrayList<Projectile> projectiles = new ArrayList<>();


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
                    playing.getGame().getAudioPlayer().playEffect(AudioPlayer.COLLECT_BOOK_FX);
                    b.setActive(false);
                    applyEffectToPlayer(b);
                    b.GetBookFX().active = true;
                }

        for (TestPosition t : testPosition)
            if (hitbox.intersects(t.getHitbox())) {
                if (playing.getPlayer().getBooks() == 3) 
                    playing.setLevelCompleted(true);
            }
    }

    public void applyEffectToPlayer(Book b) {
        if (b.getObjType() == KNOWLEDGE_BOOK) {
            playing.getPlayer().increaseKnowledge();
        }
    }

    public void loadObjects(Level newLevel) {
        knowledgeBooks = newLevel.getBooks();
        testPosition = newLevel.getTestPos();
        traps = newLevel.getTraps();
        girls = newLevel.getGirls();
        projectiles.clear();
    }

    private void loadImgs() {
        BufferedImage bookSprite = LoadSave.GetSpriteAtlas(LoadSave.KNOWLEDGE_BOOK_ATLAS);
        bookImgs = new BufferedImage[42];

        for (int i = 0; i < bookImgs.length; i++) 
            bookImgs[i] = bookSprite.getSubimage(28 * i, 0, 28, 35);

        testPosImg = LoadSave.GetSpriteAtlas(LoadSave.TEST_POSITION_IMG);

        trapImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

        girlImgs = new BufferedImage[6];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.GIRL);
        for(int i = 0; i < 6; i++){
            girlImgs[i] = temp.getSubimage(i*192 + 228,720, 192, 150);
        }
        
        heartImg = LoadSave.GetSpriteAtlas(LoadSave.HEART);

        bookFx = new BufferedImage[7];
        temp = LoadSave.GetSpriteAtlas(LoadSave.BOOK_FX);
        for(int i = 0; i < bookFx.length; i++){
            bookFx[i] = temp.getSubimage(10*i, 0, 10, 8);
        }
    }

    public void update(int [][] lvlData, Player player) {
        for (Book b : knowledgeBooks){
            if (b.isActive())
                b.update();
            if(b.GetBookFX().active)
                b.GetBookFX().update();
        }
            
        updateGirl(lvlData, player);
        updateProjectile(lvlData, player);
    }

    private void updateProjectile(int[][] lvlData, Player player) {
        for(Projectile p:projectiles) {
            if(p.isActive()){
                p.updatePos();
                if(p.getHitbox().intersects(player.getHitbox())) {
                    if (player.getHealth() > 1)
                        player.blinkAni();
                    player.getPlaying().getGame().getAudioPlayer().playEffect(AudioPlayer.KISS_GIRL_FX);
                    player.changeHealth(-1);
                    p.setActive(false);
                }else if(IsProjectileHittingLevel(p, lvlData))
                    p.setActive(false);
            }
               
        }
    }

    private boolean isPlayerInFrontOfGirl(Girl g, Player player) {
        if(g.getObjType() == GIRL_LEFT){
            if(g.getHitbox().x>player.getHitbox().x) return true;
            
        } else if(g.getHitbox().x<player.getHitbox().x) return true;
        return false;
    }

    private void updateGirl(int[][] lvlData, Player player) {
        for(Girl g:girls){
            if(!g.doAnimation)
                if(g.getTileY()==player.getTileY())
                    if(isPlayerInRange(g, player))
                        if(isPlayerInFrontOfGirl(g, player))
                            if(CanGirlSeePlayer(lvlData, player.getHitbox(), g.getHitbox(), g.getTileY())){
                                g.setAnimation(true);
                            }
            g.update();
            if(g.getAniIndex() == 5 && g.getAniTick() == 0)
                kissGirl(g);
        }
    }


    private void kissGirl(Girl g) {
        int dir = 1;
        if(g.getObjType() == GIRL_LEFT) {
            dir = -1;
        }
        projectiles.add(new Projectile((int)g.getHitbox().x,(int) g.getHitbox().y, dir));
    }

    private boolean isPlayerInRange(Girl g, Player player) {
        int absValue =(int) Math.abs(player.getHitbox().x - g.getHitbox().x);
        return absValue <= Game.TILES_SIZE*5;
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBook(g, xLvlOffset);
        drawBookFx(g, xLvlOffset);
        drawTestPos(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawGirls(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for(Projectile p: projectiles)
            if(p.isActive()){
                g.drawImage(heartImg,(int) (p.getHitbox().x - xLvlOffset),(int) (p.getHitbox().y), HEART_WIDTH, HEART_HEIGHT, null);
                //p.drawHitbox(g, xLvlOffset);
            }
    }

    private void drawGirls(Graphics g, int xLvlOffset) {
        for(Girl gi : girls){
            int x = (int) (gi.getHitbox().x - xLvlOffset);
            int width =(int) (GIRL_WIDTH * 1.5);
            if(gi.getObjType() == GIRL_LEFT){
                x += width;
                width *= -1;
            }
            g.drawImage(girlImgs[gi.getAniIndex()], 
                        x - 10, 
                        (int) (gi.getHitbox().y), 
                        width,
                        (int) (GIRL_HEIGHT* 1.5), 
                        null);
            // gi.drawHitbox(g, xLvlOffset);
        }
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (GameAddict t : traps){
            g.drawImage(trapImg,
                (int) (t.getHitbox().x - xLvlOffset - 9),
                (int) (t.getHitbox().y - t.getyDrawOffset()), 
                TRAP_WIDTH,
                TRAP_HEIGHT,
                null);
            // t.drawHitbox(g, xLvlOffset);
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

    private void drawBookFx(Graphics g, int xLvlOffset){
        for (Book b : knowledgeBooks)
            if (b.GetBookFX().active && !b.isActive())
                g.drawImage(bookFx[b.GetBookFX().getAniIndex()],
                    (int) (b.getHitbox().x - b.getxDrawOffset() - xLvlOffset),
                    (int) (b.getHitbox().y - b.getyDrawOffset()), 
                    BOOK_FX_WIDTH,
                    BOOK_FX_HEIGHT,
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
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Book b : knowledgeBooks)
            b.reset();

        for (TestPosition t : testPosition)
            t.reset();
        for (Girl gi : girls){
            gi.reset();
        }
    }
}
