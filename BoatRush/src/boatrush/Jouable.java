package boatrush;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Set;

public class Jouable {

    private ArrayList<Joueur> listeJoueurs;
    private ArrayList<Obstacle> listeObstacles;

    /**
     * Initialise la liste des joueurs.
     */
    public Jouable() {
        this.listeJoueurs = new ArrayList<>();
        this.listeObstacles = new ArrayList<>();
    }

    /**
     * Ajoute un joueur à la liste.
     */
    public void addJoueur(Joueur j) {
        this.listeJoueurs.add(j);
    }
    
    /**
     * Ajoute un obstacle à la liste.
     */
    public void addObstacle(Obstacle o) {
        this.listeObstacles.add(o);
    }

    /**
     * Remplace un joueur existant par une nouvelle version, ou l'ajoute s'il n'existe pas encore.
     */
    public void remplacerJoueur(Joueur j) {
    for (Joueur local : this.listeJoueurs) {
        if (local.getNom().equals(j.getNom())) {
            // Mettre à jour uniquement les coordonnées, pas l’objet entier
            local.setNom(j.getNom());
            local.setPosition(j.getXCoord(), j.getYCoord());
            return;
        }
    }
    // S'il n'existe pas, on l'ajoute
    this.listeJoueurs.add(j);
}
    
    /**
     * Remplace un obstacle existant par une nouvelle version, ou l'ajoute s'il n'existe pas encore.
     */
    public void remplacerObstacle(Obstacle o) {
    for (Obstacle local : this.listeObstacles) {
        if (local.getId()==o.getId()) {
            // Mettre à jour uniquement les coordonnées, pas l’objet entier
            local.setXCoord(o.getXCoord());
            local.setYCoord(o.getYCoord());
            return;
        }
    }
    // S'il n'existe pas, on l'ajoute
    this.listeObstacles.add(o);
}


    /**
     * Retourne la liste des joueurs.
     */
    public ArrayList<Joueur> getListeJoueurs() {
        return this.listeJoueurs;
    }
    
    /**
     * Retourne la liste des obstacles.
     */
    public ArrayList<Obstacle> getListeObstacles() {
        return this.listeObstacles;
    }

    /**
     * Remplace la liste des joueurs.
     */
    public void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
    }
    
    /**
     * Remplace la liste des obstacles.
     */
    public void setListeObstacles(ArrayList<Obstacle> listeObstacles) {
        this.listeObstacles = listeObstacles;
    }

    /**
     * Met à jour chaque joueur (ex: traitement des touches, déplacements).
     */
    public void miseAJour() {
        for (Joueur j : this.listeJoueurs) {
            j.miseAJour();
        }
        for (Obstacle o : this.listeObstacles) {
            o.miseAJour();
        }
    }

    /**
     * Dessine chaque joueur sur le contexte graphique.
     */
//    public void rendu(Graphics2D contexte) {
//        for (Joueurs j : this.listeJoueurs) {
//            j.rendu(contexte);
//        }
//    }
    
    public void rendu(Graphics2D contexte, double positionX, double positionY) {
    for (Joueur joueur : listeJoueurs) {
        int screenX = (int)(joueur.getXCoord() - positionX);
        int screenY = (int)(joueur.getYCoord() - positionY);
        contexte.drawImage(joueur.getAvatar().getCurrentSprite(), screenX, screenY, null);
    
    }
}

}
