package entities;

import static ultiz.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import ultiz.LoadSave;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] professorArr;
    private ArrayList<Professor> professors = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing=playing;
        loadEnemyImgs();
    }

    public void update(){
        for(Professor p:professors){
            p.update();
        }
    }

    public void draw(Graphics g){
        drawProfessors(g);
    }

    private void drawProfessors(Graphics g) {
        for(Professor p:professors){
            g.drawImage(professorArr[p.getEnemyState()][p.getAniIndex()], (int) p.getHitbox().x, (int) p.getHitbox().y, PROFESSOR_WIDTH, PROFESSOR_HEIGHT, null);
        }
    }

    private void loadEnemyImgs() {
        professorArr = new BufferedImage[4][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.PROFESSOR_SPRITE);
        for(int i=0;i<9;i++){
            professorArr[2][i] = temp.getSubimage(PROFESSOR_WIDTH_DEFAULT*i, PROFESSOR_HEIGHT_DEFAULT, PROFESSOR_WIDTH_DEFAULT, PROFESSOR_HEIGHT_DEFAULT);
            professorArr[3][i] = temp.getSubimage(PROFESSOR_WIDTH_DEFAULT*i, PROFESSOR_HEIGHT_DEFAULT*3, PROFESSOR_WIDTH_DEFAULT, PROFESSOR_HEIGHT_DEFAULT);
        }
        professorArr[0][0]=professorArr[2][0];
        professorArr[1][0]=professorArr[3][0];
    }
}
