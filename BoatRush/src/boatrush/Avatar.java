package boatrush;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gère l'apparence graphique animée du joueur à partir d'une feuille de sprites.
 */
public class Avatar {

<<<<<<< Updated upstream
    // Constantes des dimensions du sprite
=======
    private int skinId;
    private BufferedImage spriteSheet;
    private BufferedImage currentSprite;
    private int indiceSprite;
>>>>>>> Stashed changes
    public static final int LARGEUR_SPRITE = 32;
    public static final int HAUTEUR_SPRITE = 34;

    // Feuille de sprites contenant toutes les frames
    private BufferedImage spriteSheet;

    // Sprite actuellement affiché
    private BufferedImage currentSprite;

    // Indice pour l'animation (entre 0 et 3)
    private int indiceSprite;

    /**
     * Constructeur : charge la feuille de sprites et initialise le premier sprite.
     */
    public Avatar(int choixSkin) {
        this.skinId = choixSkin;
        try {
            String nomFichier = getNomSprite(choixSkin);
            this.spriteSheet = ImageIO.read(getClass().getResource("../resources/" + nomFichier));
            this.indiceSprite = 0;
            updateSprite();
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, "Erreur de chargement du sprite", ex);
        }
    }
<<<<<<< Updated upstream
=======
    
     private String getNomSprite(int skinId) {
        switch (skinId) {
            case 1:
                return "SpritesVert.png";
            case 2:
                return "SpritesViolet.png";
            case 3:
                return "SpritesGris.png";
            case 4:
                return "SpritesBrun.png";
            default:
                return "SpritesVert.png"; // par défaut
        }
    }
>>>>>>> Stashed changes

    /**
     * Met à jour l'image affichée à partir de l'indice actuel.
     */
    private void updateSprite() {
        if (spriteSheet != null) {
            this.currentSprite = spriteSheet.getSubimage(indiceSprite * LARGEUR_SPRITE, 0, LARGEUR_SPRITE, HAUTEUR_SPRITE);
        }
    }

    /**
     * Fait avancer l'animation du sprite (appelée à chaque tick de mise à jour).
     */
    public void miseAJour() {
        updateSprite();
        indiceSprite = (indiceSprite + 1) % 4; // Boucle sur 4 frames
    }

    /**
     * Retourne le sprite à afficher actuellement.
     */
    public BufferedImage getCurrentSprite() {
        return this.currentSprite;
    }
}
