package boatrush;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Avatar {

    private BufferedImage spriteSheet;
    private BufferedImage currentSprite;
    private int indiceSprite;

    /**
     * Charge la feuille de sprites depuis les ressources.
     */
    public Avatar() {
        try {
            this.spriteSheet = ImageIO.read(getClass().getResource("../resources/SpritesVert.png"));
            this.indiceSprite = 0;
            updateSprite();
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, "Erreur de chargement du sprite", ex);
        }
    }

    /**
     * Met à jour l'image affichée en fonction de l'indice courant.
     */
    private void updateSprite() {
        if (spriteSheet != null) {
            this.currentSprite = spriteSheet.getSubimage(indiceSprite * 32, 0, 32, 50);
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
