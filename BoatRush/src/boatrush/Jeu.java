/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package boatrush;

import java.awt.Graphics2D;
import java.util.ArrayList;
import jdbc.JoueurSQL;
import jdbc.ObstacleSQL;

public class Jeu {

    private Carte carte;
    private Jouable listeJoueur;
    private ArrayList<Obstacle> listeObstacle;
    private Joueur joueurActif;
    private JoueurSQL joueurSQL;
    private ObstacleSQL obstacleSQL;

    public Jeu(Joueur joueurActif) {
        this.carte = new Carte("CarteOcean.txt");
        this.listeJoueur = new Jouable();

        this.joueurSQL = new JoueurSQL();
        this.joueurActif = joueurActif;
        listeJoueur.addJoueur(joueurActif);

        this.obstacleSQL = new ObstacleSQL();
        //obstacleSQL.genererObstacles(150);  // Par exemple, pour créer 50 obstacles dans la bdd

        this.listeObstacle = obstacleSQL.getTousLesObstacles();

    }

    /**
     * Fonction de debug/test
     */
    private void afficherListeJoueurs(String contexte) {
        System.out.println("=== " + contexte + " ===");
        for (Joueur j : listeJoueur.getListeJoueurs()) {
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
        ArrayList<Joueur> joueursDepuisBDD = joueurSQL.getTousLesJoueurs();

        // Met à jour les joueurs distants (sauf le joueur actif)
        for (Joueur j : joueursDepuisBDD) {
            if (!j.getNom().equals(joueurActif.getNom())) {
                listeJoueur.remplacerJoueur(j); //On remplace juste les coordonnées des joueurs en les actualisant via la bdd
            }
        }

        // Supprime les joueurs qui n'existent plus en BDD
        ArrayList<Joueur> joueursASupprimer = new ArrayList<>();
        for (Joueur joueurLocal : listeJoueur.getListeJoueurs()) {
            boolean existeEnBDD = false;
            for (Joueur joueurBDD : joueursDepuisBDD) {
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

        // Vérifie les collisions et annule le mouvement si besoin
        for (Obstacle o : listeObstacle) {
            if (verifierCollision(joueurActif, o)) {
                joueurActif.annulerDernierDeplacement();
            }
        }

        // Met à jour le joueur actif en BDD
        joueurSQL.modifierJoueur(joueurActif);
    }

    /**
     * Effectue le rendu graphique du jeu.
     */
    public void rendu(Graphics2D contexte) {
        double positionX = 0;  // Pas de scrolling horizontal
        double positionY = joueurActif.getYCoord() - FenetreDeJeu.HAUTEUR_FENETRE / 2.0;

        // Limiter le scrolling vertical aux bords haut et bas de la carte
        positionY = Math.max(0, Math.min(positionY, carte.getHauteurEnPixels() - FenetreDeJeu.HAUTEUR_FENETRE));

        int nbTuilesX = FenetreDeJeu.LARGEUR_FENETRE / 32;
        int nbTuilesY = FenetreDeJeu.HAUTEUR_FENETRE / 32;

        carte.rendu(contexte, positionX, positionY, nbTuilesX, nbTuilesY);
        listeJoueur.rendu(contexte, positionX, positionY);

        //Rendu des différents obstacles
        for (Obstacle o : listeObstacle) {
            int screenX = (int) (o.getXCoord() - positionX);
            int screenY = (int) (o.getYCoord() - positionY);

            contexte.drawImage(o.getSprite(), screenX, screenY, null);
        }

    }

    public Jouable getListeJoueur() {
        return this.listeJoueur;
    }

    private boolean verifierCollision(Joueur joueur, Obstacle obstacle) {
        int joueurX = joueur.getXCoord();
        int joueurY = joueur.getYCoord();
        int largeurJoueur = joueur.getAvatar().LARGEUR_SPRITE;
        int hauteurJoueur = joueur.getAvatar().HAUTEUR_SPRITE;  // Ou adapte si besoin

        int obstacleX = obstacle.getXCoord();
        int obstacleY = obstacle.getYCoord();
        int largeurObstacle = obstacle.getLargeur();
        int hauteurObstacle = obstacle.getHauteur();

        // Collision basique par chevauchement des rectangles
        return joueurX < obstacleX + largeurObstacle
                && joueurX + largeurJoueur > obstacleX
                && joueurY < obstacleY + hauteurObstacle
                && joueurY + hauteurJoueur > obstacleY;
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
    public Joueur getJoueur() {
        return this.joueurActif;
    }
}
