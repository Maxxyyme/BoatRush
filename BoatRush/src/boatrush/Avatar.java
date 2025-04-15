/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class Avatar {

    protected BufferedImage spriteSheet;
    protected BufferedImage currentsprite;
    protected double x, y;
    private boolean toucheDroite;
    private boolean toucheGauche;
    private boolean toucheHaut;
    private boolean toucheBas;

    private int indiceSprite;
            
    public Avatar() {
        try {
            this.spriteSheet = ImageIO.read(getClass().getResource("../resources/SpritesVert.png"));

        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 240;
        this.y = 500;
        this.toucheGauche = false;
        this.toucheDroite = false;
        this.indiceSprite = 0;
    }
    
    public void updateSprite(){
        this.currentsprite = spriteSheet.getSubimage(indiceSprite*32, 0, 32, 50);
    }

    public void miseAJour() {
        this.updateSprite();
        this.indiceSprite = (this.indiceSprite+1)%4;
        if (this.toucheGauche) {
            x -= 10;
        }
        if (this.toucheDroite) {
            x += 10;
        }
        if (this.toucheHaut) {
            y -= 10;
        }
        if (this.toucheBas) {
            y += 10;
        }
        if (x > 640 - currentsprite.getWidth()) { // collision avec le bord droit de la scene
            x = 640 - currentsprite.getWidth();
        }
        if (x < 0) { // collision avec le bord gauche de la scene
            x = 0;
        }
        if (y > 1440 - currentsprite.getHeight()) {
            y = 1440 - currentsprite.getHeight();
        }
        if (y < 0) {
            y = 0;
        }
    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.currentsprite, (int) x, (int) y, null);
    }

    public void setToucheGauche(boolean etat) {
        this.toucheGauche = etat;
    }

    public void setToucheDroite(boolean etat) {
        this.toucheDroite = etat;
    }

    public void setToucheHaut(boolean etat) {
        this.toucheHaut = etat;
    }

    public void setToucheBas(boolean etat) {
        this.toucheBas = etat;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLargeur() {
        return currentsprite.getHeight();
    }

    public double getHauteur() {
        return currentsprite.getWidth();
    }
}
