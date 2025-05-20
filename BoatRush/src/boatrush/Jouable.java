package boatrush;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Jouable {

    private ArrayList<Joueur> listeJoueurs;

    /**
     * Initialise une liste vide de joueurs.
     */
    public Jouable() {
        this.listeJoueurs = new ArrayList<>();
    }

    /**
     * Ajoute un joueur à la liste.
     */
    public void addJoueur(Joueur j) {
        this.listeJoueurs.add(j);
    }

    /**
     * Remplace un joueur existant (même pseudo) en mettant à jour sa position,
     * ou l’ajoute s’il n’est pas encore dans la liste.
     */
    public void remplacerJoueur(Joueur j) {
        for (Joueur local : listeJoueurs) {
            if (local.getNom().equals(j.getNom())) {
                local.setPosition(j.getXCoord(), j.getYCoord());
                return;
            }
        }
        this.listeJoueurs.add(j);
    }

    /**
     * Retourne la liste actuelle des joueurs.
     */
    public ArrayList<Joueur> getListeJoueurs() {
        return this.listeJoueurs;
    }

    /**
     * Remplace la liste des joueurs par une nouvelle.
     */
    public void setListeJoueurs(ArrayList<Joueur> nouvelleListe) {
        this.listeJoueurs = nouvelleListe;
    }

    /**
     * Met à jour tous les joueurs (traitement des touches et déplacements).
     */
    public void miseAJour() {
        for (Joueur j : listeJoueurs) {
            j.miseAJour();
        }
    }

    /**
     * Dessine tous les joueurs sur le contexte graphique avec offset (scrolling).
     */
    public void rendu(Graphics2D contexte, double positionX, double positionY) {
        for (Joueur joueur : listeJoueurs) {
            int screenX = (int) (joueur.getXCoord() - positionX);
            int screenY = (int) (joueur.getYCoord() - positionY);
            contexte.drawImage(joueur.getAvatar().getCurrentSprite(), screenX, screenY, null);
        }
    }
}
