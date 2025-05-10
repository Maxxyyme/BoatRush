package boatrush;

import java.awt.Graphics2D;
import java.util.ArrayList;
import jdbc.JoueurSQL;

public class Jeu {

    private Carte carte;
    private Obstacles obstacle;
    private Jouable listeJoueur;
    private Joueurs joueurActif;
    private JoueurSQL joueurSQL;

    public Jeu() {
        this.carte = new Carte("CarteOcean.txt");
        this.listeJoueur = new Jouable();
        this.obstacle = new Obstacles();
        this.joueurSQL = new JoueurSQL();
        initialiserJoueurs();
    }

    /**
     * Charge les joueurs depuis la base ou initialise des joueurs de test si la base est vide.
     */
    private void initialiserJoueurs() {
        ArrayList<Joueurs> joueursExistants = joueurSQL.getTousLesJoueurs();

        if (joueursExistants.isEmpty()) {
            Joueurs j1 = new Joueurs("Simon", 100, 100);
            Joueurs j2 = new Joueurs("Maxime", 200, 200);
            joueurSQL.creerJoueur(j1);
            joueurSQL.creerJoueur(j2);
            listeJoueur.addJoueur(j1);
            listeJoueur.addJoueur(j2);
        } else {
            for (Joueurs j : joueursExistants) {
                listeJoueur.addJoueur(j);
            }
        }

        // Sélectionne le premier joueur comme actif par défaut
        if (!listeJoueur.getListeJoueurs().isEmpty()) {
            joueurActif = listeJoueur.getListeJoueurs().get(1);
        }
    }

    /**
     * Met à jour la carte, les obstacles, les joueurs distants et le joueur actif.
     */
    public void miseAJour() {
        carte.miseAJour();
        obstacle.miseAJour();

        // Rafraîchit les autres joueurs depuis la BDD
        ArrayList<Joueurs> joueursDepuisBDD = joueurSQL.getTousLesJoueurs(); //On récupère les positions les plus récentes des autres joueurs depuis la BDD.
        for (Joueurs j : joueursDepuisBDD) {
            if (!j.getNom().equals(joueurActif.getNom())) { //On ne remplace PAS le joueur actif pour ne pas écraser ses actions.
                listeJoueur.remplacerJoueur(j);
            }
        }

        // Met à jour uniquement le joueur actif en local modif des coordonnées dans la classe Joueur directement
        listeJoueur.miseAJour();

        // Met a jour le joueur actif dans le base de données
        joueurSQL.modifierJoueur(joueurActif);
    }

    /**
     * Effectue le rendu graphique du jeu.
     */
    public void rendu(Graphics2D contexte) {
        carte.rendu(contexte);
        listeJoueur.rendu(contexte);
        obstacle.rendu(contexte);
    }

    /**
     * Indique si la partie est terminée.
     */
    public boolean estTermine() {
        return false; // À définir plus tard selon les règles du jeu
    }

    /**
     * Retourne le joueur actuellement contrôlé.
     */
    public Joueurs getJoueur() {
        return this.joueurActif;
    }
}
