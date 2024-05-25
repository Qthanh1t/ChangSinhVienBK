package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.PauseButton;
import ui.UrmButton;
import ultiz.LoadSave;

import static ultiz.Constants.UI.URMButtons.URM_SIZE;

public class Guides extends State implements Statemethods {

    private BufferedImage backgroundImg, guidesImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;
    
    public Guides(Game game) {
        super(game);
        loadImg();
        loadButtons();
    }

    private void loadButtons() {
        int menuX = (int) (387 * Game.SCALE);
        int menuY = (int) (325 * Game.SCALE);
        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImg() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        guidesImg = LoadSave.GetSpriteAtlas(LoadSave.GUIDES_MENU);

        bgW = (int) (guidesImg.getWidth() / 1.2f * Game.SCALE);
        bgH = (int) (guidesImg.getHeight() / 1.2f * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (33 * Game.SCALE);
    }

    @Override
    public void update() {
        menuB.update();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(guidesImg, bgX, bgY, bgW, bgH, null);
        
        menuB.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(e, menuB))
            if(menuB.isMousePressed())
                Gamestate.state = Gamestate.MENU;
        menuB.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        if (isIn(e, menuB))
            menuB.setMouseOver(true);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Gamestate.state = Gamestate.MENU;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));
    }

}
