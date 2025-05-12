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
import jdbc.JoueurSQL;

public class Jeu {
    
    BufferedImage decor;

    //private Carte carte;
    //private Pokemons pokemon;
    private Joueurs joueur;
    private Obstacles obstacle;
    public Jouable listeJoueur;

    public Jeu() {
        try {
            this.decor = ImageIO.read(getClass().getResource("../resources/RiverBackground.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.carte = new Carte();
        //this.pokemon = new Pokemons(carte);
        this.listeJoueur = new Jouable();
        this.joueur = new Joueurs("Simon", 0, 0);
        this.obstacle = new Obstacles();
        this.listeJoueur.add(joueur);
        //JoueurSQL test = new JoueurSQL();
       // test.creerJoueur(this.joueur);
    }

    public void miseAJour() {
        //this.carte.miseAJour();
//        this.pokemon.miseAJour();
        this.joueur.miseAJour();
        this.obstacle.miseAJour();
    }

    public void rendu(Graphics2D contexte) {
        //this.carte.rendu(contexte);
//        this.pokemon.rendu(contexte);
        
        contexte.drawImage(this.decor, 0, 0, null);
        this.joueur.getAvatar().rendu(contexte);
        this.obstacle.rendu(contexte);
    }
    
    public boolean estTermine() {
        
        return false;
    }

//    public avatar getAvatar() {
//        return this.avatar;
//    }
    
    public Joueurs getJoueur() {
        return this.joueur;
    }
    
    

}
