package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Monstre {

    protected BufferedImage sprite;
    protected int x, y;
    protected int id;
    private int direction = 1; // 1 pour droite, -1 pour gauche


    //Constructeur
    public Monstre(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/pirate.png"));
        } catch (IOException ex) {
            Logger.getLogger(Obstacle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Gestion du déplacement de gauche à droite
    public void miseAJour() {
        this.x += direction * 8;

        if (this.x > 640 - this.getLargeur()) {
            this.x = 640 - this.getLargeur();
            direction = -1;
        } else if (this.x < 0) {
            this.x = 0;
            direction = 1;
        }

    }
    
    
    //Rendu
    public void rendu(Graphics2D contexte, double offsetX, double offsetY) {
        contexte.drawImage(this.sprite, (int) (x - offsetX), (int) (y - offsetY), null);
    }

    //Getters
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
        return this.sprite.getWidth();
    }

    public int getHauteur() {
        return this.sprite.getHeight();
    }

    //Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
