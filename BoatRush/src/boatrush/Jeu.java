package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Jeu {
    
    BufferedImage decor;

    //private Carte carte;
    //private Pokemons pokemon;
    private Joueurs joueur;
    private Avatar avatar;
    private Obstacles obstacle;

    public Jeu() {
        try {
            this.decor = ImageIO.read(getClass().getResource("../resources/RiverBackground.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.carte = new Carte();
        //this.pokemon = new Pokemons(carte);
        this.joueur = new Joueurs();
        this.avatar = new Avatar();
        this.obstacle = new Obstacles();
    }

    public void miseAJour() {
        //this.carte.miseAJour();
//        this.pokemon.miseAJour();
        this.joueur.miseAJour();
        this.avatar.miseAJour();
        this.obstacle.miseAJour();
    }

    public void rendu(Graphics2D contexte) {
        //this.carte.rendu(contexte);
//        this.pokemon.rendu(contexte);
        
        contexte.drawImage(this.decor, 0, 0, null);
        this.joueur.rendu(contexte);
        this.avatar.rendu(contexte);
        this.obstacle.rendu(contexte);
    }
    
    public boolean estTermine() {
        return false;
    }

    public Avatar getAvatar() {
        return avatar;
    }
    
    

}
