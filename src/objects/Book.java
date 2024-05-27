package objects;

import static ultiz.Constants.ObjectConstants.BOOK_FX;

import main.Game;

public class Book extends GameObject {

    private float hoverOffset;
    private int maxHoverOffset, hoverDir = 1;
    private BookFX bookFX;

    public Book(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(28, 35);
        xDrawOffset = (int) (0 * Game.SCALE);
        yDrawOffset = (int) (0 * Game.SCALE);

        // hitbox.y += yDrawOffset - (int) (Game.SCALE * 7);
        hitbox.x += (int) (Game.SCALE * 3);

        maxHoverOffset = (int) (10 * Game.SCALE);
        bookFX = new BookFX(x, y, BOOK_FX);
    }

    public void update() {
        updateAnimationTick();
        updateHover();
    }

    public void updateHover() {
        hoverOffset += (0.05f * Game.SCALE * hoverDir);

        if (hoverOffset >= maxHoverOffset) 
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;

        hitbox.y = y + hoverOffset - (int) (15 * Game.SCALE);
    }
    public BookFX GetBookFX(){
        return bookFX;
    }
}
