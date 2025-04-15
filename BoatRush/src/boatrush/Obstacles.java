/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. tttttttttttttttttt
 */
package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Exemple de classe lutin
 *
 * @author guillaume.laurent
 */
public class Obstacles {

    protected BufferedImage sprite;
    protected double x, y;

    public Obstacles() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/oB2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Obstacles.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 0;
        this.y = 0;
    }

    public void miseAJour() {
    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public double getLargeur() {
        return sprite.getHeight();
    }

    public double getHauteur() {
        return sprite.getWidth();
    }
    
   public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    

}

