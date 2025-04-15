/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author mmunier
 */

public class Jeu {

    private BufferedImage decor;
    private Avatar avatar;
    private int windowWidth;
    private int windowHeight;
    private Obstacles unObstacle; 
    
    
    public Jeu(int x, int y) {
        this.windowWidth = x;
        this.windowHeight = y;
        
        try {
           this.decor = ImageIO.read(getClass().getResource("../resources/RiverBackground.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.avatar = new Avatar(x,y);
        
        this.unObstacle = new Obstacles();
        
        int rangeX = this.windowWidth - (int)unObstacle.getLargeur();
        
        unObstacle.setY(100);
        unObstacle.setX((int)(Math.random()*rangeX));
    }

     public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.decor, 0, 0, null);
        this.avatar.rendu(contexte);
        this.unObstacle.rendu(contexte);
    }

    public void miseAJour() {
        this.avatar.miseAJour();
        this.unObstacle.miseAJour();
    }
    public Avatar getAvatar() {
        return this.avatar;
    }
}

