package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Obstacle {

    protected BufferedImage sprite;
    protected int x, y;
    protected int id;
    public static final int LARGEUR_OBSTACLE = 21;
    public static final int HAUTEUR_OBSTACLE = 17;

    //Constructeur
    public Obstacle(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/caillou.png"));
        } catch (IOException ex) {
            Logger.getLogger(Obstacle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Getters
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
        return sprite.getHeight();
    }

    public int getHauteur() {
        return sprite.getWidth();
    }

    //Setters
    public void setXCoord(int x) {
        this.x = x;
    }

    public void setYCoord(int y) {
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Pas utile car les obstacles sont fixes et sont enregistrés en base
    public void miseAJour() {
    }

    //Rendu graphique des obstacle
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }
    
}
