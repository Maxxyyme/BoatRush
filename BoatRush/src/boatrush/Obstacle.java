package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Représente un obstacle statique dans le jeu.
 */
public class Obstacle {

    // Sprite de l'obstacle
    private BufferedImage sprite;

    // Position
    private int x, y;

    // Identifiant unique en base de données
    private int id;

    // Dimensions de collision
    public static final int LARGEUR_OBSTACLE = 21;
    public static final int HAUTEUR_OBSTACLE = 17;

    /**
     * Constructeur avec coordonnées et identifiant.
     */
    public Obstacle(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;

        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/caillou.png"));
        } catch (IOException ex) {
            Logger.getLogger(Obstacle.class.getName()).log(Level.SEVERE, "Erreur chargement image obstacle", ex);
        }
    }

    // Getters
    public int getXCoord() {
        return this.x;
    }

    public int getYCoord() {
        return this.y;
    }

    public int getId() {
        return this.id;
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public int getLargeur() {
        return LARGEUR_OBSTACLE;
    }

    public int getHauteur() {
        return HAUTEUR_OBSTACLE;
    }

    // Setters
    public void setXCoord(int x) {
        this.x = x;
    }

    public void setYCoord(int y) {
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Mise à jour éventuelle de l’obstacle (pas utilisée ici).
     */
    public void miseAJour() {
        // L'obstacle est statique, mais cette méthode existe pour compatibilité future.
    }

    /**
     * Dessine l’obstacle à l’écran.
     */
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, x, y, null);
    }
}
