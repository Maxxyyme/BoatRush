package boatrush;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Avatar {

    private int skinId;
    private BufferedImage spriteSheet;
    private BufferedImage currentSprite;
    private int indiceSprite;
    public static final int LARGEUR_SPRITE = 32;
    public static final int HAUTEUR_SPRITE = 34;

    /**
     * Charge la feuille de sprites depuis les ressources.
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

    /**
     * Met à jour l'image affichée en fonction de l'indice courant.
     */
    private void updateSprite() {
        if (spriteSheet != null) {
            this.currentSprite = spriteSheet.getSubimage(indiceSprite * LARGEUR_SPRITE, 0, LARGEUR_SPRITE, HAUTEUR_SPRITE);
        }
    }

    /**
     * Fait avancer l'animation en changeant l'indice du sprite.
     */
    public void miseAJour() {
        updateSprite();
        indiceSprite = (indiceSprite + 1) % 4;
    }

    /**
     * Retourne l'image actuelle à afficher.
     */
    public BufferedImage getCurrentSprite() {
        return this.currentSprite;
    }
}
