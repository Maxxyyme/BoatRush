/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
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

    public Jeu(Joueurs joueurActif) {
        this.carte = new Carte("CarteOcean.txt");
        this.listeJoueur = new Jouable();
        this.obstacle = new Obstacles();
        this.joueurSQL = new JoueurSQL();
        this.joueurActif = joueurActif;
        listeJoueur.addJoueur(joueurActif);

    }

    /**
     * Fonction de debug/test
     */
    private void afficherListeJoueurs(String contexte) {
        System.out.println("=== " + contexte + " ===");
        for (Joueurs j : listeJoueur.getListeJoueurs()) {
            System.out.println("- " + j.getNom() + " (" + j.getXCoord() + ", " + j.getYCoord() + ")");
        }
        System.out.println("Nombre total: " + listeJoueur.getListeJoueurs().size());
    }

    /**
     * Met à jour la carte, les obstacles, les joueurs distants et le joueur
     * actif.
     */
    public void miseAJour() {
        carte.miseAJour();
        //obstacle.miseAJour();

        // Rafraîchit les autres joueurs depuis la BDD
        ArrayList<Joueurs> joueursDepuisBDD = joueurSQL.getTousLesJoueurs();

        // Met à jour les joueurs distants (sauf le joueur actif)
        for (Joueurs j : joueursDepuisBDD) {
            if (!j.getNom().equals(joueurActif.getNom())) {
                listeJoueur.remplacerJoueur(j); //On remplace juste les coordonnées des joueurs en les actualisant via la bdd
            }
        }

        // Supprime les joueurs qui n'existent plus en BDD
        ArrayList<Joueurs> joueursASupprimer = new ArrayList<>();
        for (Joueurs joueurLocal : listeJoueur.getListeJoueurs()) {
            boolean existeEnBDD = false;
            for (Joueurs joueurBDD : joueursDepuisBDD) {
                if (joueurBDD.getNom().equals(joueurLocal.getNom())) {
                    existeEnBDD = true;
                    break;
                }
            }
            if (!existeEnBDD) {
                joueursASupprimer.add(joueurLocal);
            }
        }
        listeJoueur.getListeJoueurs().removeAll(joueursASupprimer);

        // Met à jour uniquement le joueur actif en local
        listeJoueur.miseAJour();

        // Met à jour le joueur actif en BDD
        joueurSQL.modifierJoueur(joueurActif);
    }

    /**
     * Effectue le rendu graphique du jeu.
     */
    public void rendu(Graphics2D contexte) {
        carte.rendu(contexte);
        listeJoueur.rendu(contexte);
        //obstacle.rendu(contexte);
    }


    public Jouable getListeJoueur() {
        return this.listeJoueur;
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
