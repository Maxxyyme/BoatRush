package boatrush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import jdbc.JoueurSQL;

public class Jeu {
    
    Carte carte;

    //private Carte carte;
    //private Pokemons pokemon;
    private Joueurs joueur;
    private Obstacles obstacle;
    public Jouable listeJoueur;

    public Jeu() {
 
        this.carte = new Carte("CarteOcean.txt");
               
        //this.pokemon = new Pokemons(carte);
        //this.listeJoueur = new Jouable();
        this.joueur = new Joueurs("Simon", 0, 0);
        this.obstacle = new Obstacles();
        //this.listeJoueur.add(joueur);
        //JoueurSQL test = new JoueurSQL();
       // test.creerJoueur(this.joueur);
    }

    public void miseAJour() {
        this.carte.miseAJour();
//        this.pokemon.miseAJour();
        this.joueur.miseAJour();
        this.obstacle.miseAJour();
    }

    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte);
//        this.pokemon.rendu(contexte);
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
