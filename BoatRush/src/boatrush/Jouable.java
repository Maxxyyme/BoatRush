package boatrush;

import java.awt.Graphics2D;
import java.util.ArrayList;


public class Jouable {

    private ArrayList<Joueur> listeJoueurs;

    //Initialise la liste des joueurs
    public Jouable() {
        this.listeJoueurs = new ArrayList<>();
    }

    //Ajoute un joueur à la liste
    public void addJoueur(Joueur j) {
        this.listeJoueurs.add(j);
    }

    //Remplace un joueur existant par une nouvelle version, ou l'ajoute s'il n'existe pas encore
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

    //Retourne la liste des joueurs
    public ArrayList<Joueur> getListeJoueurs() {
        return this.listeJoueurs;
    }

    //Remplace la liste des joueurs
    public void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
        this.listeJoueurs = listeJoueurs;
    }

    //Met à jour chaque joueur
    public void miseAJour() {
        for (Joueur j : this.listeJoueurs) {
            j.miseAJour();
        }
    }

    //Dessine les joueurs de la liste locale 
    public void rendu(Graphics2D contexte, double positionX, double positionY) {
        for (Joueur joueur : listeJoueurs) {
            int screenX = (int) (joueur.getXCoord() - positionX);
            int screenY = (int) (joueur.getYCoord() - positionY);
            contexte.drawImage(joueur.getAvatar().getCurrentSprite(), screenX, screenY, null);

        }
    }

}
