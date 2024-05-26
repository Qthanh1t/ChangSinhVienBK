package ultiz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "huster.png";
	public static final String LEVEL_ATLAS = "khung_nen.png";
	public static final String MENU_BUTTONS = "button_atlas_1.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "background_menu.jpg";
	public static final String PLAYING_BG_IMG = "playing_bg_img_1.png";
	public static final String SMALL_CLOUDS = "small_clouds.png";
	public static final String PROFESSOR_SPRITE = "professor_sprite.png";
	public static final String STATUS_BAR = "health.png";
	public static final String COMPLETED_IMG = "completed_sprite.png";
	public static final String KNOWLEDGE_BOOK_ATLAS = "knowledge_book.png";
	public static final String BOOKS_COLLECTION_FX = "book_fx.png";
	public static final String TEST_POSITION_IMG = "exam.png";
	public static final String TRAP_ATLAS = "trap.png";
	public static final String GIRL = "girl.png";
	public static final String HEART = "heart.png";
	public static final String DEATH_SCREEN = "death_screen.png";
	public static final String OPTIONS_MENU = "options_background.png";
	public static final String GUIDES_MENU = "guides.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static BufferedImage[] GetAllLevels() {
        List<String> levelNames = Arrays.asList("1.png", "2.png", "3.png", "4.png");
        BufferedImage[] levels = new BufferedImage[levelNames.size()];

        for(int i = 0; i < levels.length; i++) {
            try (InputStream is = LoadSave.class.getResourceAsStream("/lvls/" + levelNames.get(i))) {
                if (is == null) {
                    System.err.println("File not found:\n" + levelNames.get(i));
                    System.exit(1);
                }
                levels[i] = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return levels;
    }

}
