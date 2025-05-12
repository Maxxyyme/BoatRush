package boatrush;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Jouable {

    private ArrayList<Joueurs> listeJoueurs;

    /**
     * Initialise la liste des joueurs.
     */
    public Jouable() {
        this.listeJoueurs = new ArrayList<>();
    }

    /**
     * Ajoute un joueur à la liste.
     */
    public void addJoueur(Joueurs j) {
        this.listeJoueurs.add(j);
    }

    /**
     * Remplace un joueur existant par une nouvelle version, ou l'ajoute s'il n'existe pas encore.
     */
    public void remplacerJoueur(Joueurs j) {
    for (Joueurs local : this.listeJoueurs) {
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
     * Retourne la liste des joueurs.
     */
    public ArrayList<Joueurs> getListeJoueurs() {
        return this.listeJoueurs;
    }

    /**
     * Remplace la liste des joueurs.
     */
    public void setListeJoueurs(ArrayList<Joueurs> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
    }

    /**
     * Met à jour chaque joueur (ex: traitement des touches, déplacements).
     */
    public void miseAJour() {
        for (Joueurs j : this.listeJoueurs) {
            j.miseAJour();
        }
    }

    /**
     * Dessine chaque joueur sur le contexte graphique.
     */
    public void rendu(Graphics2D contexte) {
        for (Joueurs j : this.listeJoueurs) {
            j.rendu(contexte);
        }
    }
}
