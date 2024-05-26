package objects;

public class BookFX extends GameObject {

    public BookFX(int x, int y, int objType) {
        super(x, y, objType);
        this.active=false;
    }
    public void update(){
        updateAnimationTick();
    }
}
