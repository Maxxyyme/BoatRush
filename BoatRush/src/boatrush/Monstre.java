package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Classe représentant un monstre (pirate) qui se déplace horizontalement.
 */
public class Monstre {

    private BufferedImage sprite;
    private int x, y;
    private int id;
    private int direction = 1; // 1 = droite, -1 = gauche

    /**
     * Constructeur du monstre.
     */
    public Monstre(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/pirate.png"));
        } catch (IOException ex) {
            Logger.getLogger(Monstre.class.getName()).log(Level.SEVERE, "Erreur de chargement du sprite monstre", ex);
        }
    }

    /**
     * Met à jour la position du monstre (mouvement horizontal).
     */
    public void miseAJour() {
        this.x += direction * 8;

        // Collision avec les bords de l'écran
        if (this.x > 640 - getLargeur()) {
            this.x = 640 - getLargeur();
            direction = -1;
        } else if (this.x < 0) {
            this.x = 0;
            direction = 1;
        }
    }

    /**
     * Dessine le monstre avec décalage de la caméra.
     */
    public void rendu(Graphics2D contexte, double offsetX, double offsetY) {
        contexte.drawImage(this.sprite, (int) (x - offsetX), (int) (y - offsetY), null);
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargeur() {
        return sprite.getWidth();
    }

    public int getHauteur() {
        return sprite.getHeight();
    }

    // --- Setters ---
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
