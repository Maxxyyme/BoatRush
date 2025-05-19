/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package boatrush;

import java.awt.Graphics2D;
import java.util.ArrayList;
import jdbc.JoueurSQL;
import jdbc.MonstreSQL;
import jdbc.ObstacleSQL;

public class Jeu {

    private Carte carte;
    private Jouable listeJoueur;
    private ArrayList<Obstacle> listeObstacle;
    private ArrayList<Monstre> listeMonstres;
    private Joueur joueurActif;
    private JoueurSQL joueurSQL;
    private ObstacleSQL obstacleSQL;
    private MonstreSQL monstreSQL;

    public Jeu(Joueur joueurActif) {
        this.carte = new Carte("CarteOcean.txt");
        this.listeJoueur = new Jouable();

        this.joueurSQL = new JoueurSQL();
        this.joueurActif = joueurActif;
        listeJoueur.addJoueur(joueurActif);

        this.obstacleSQL = new ObstacleSQL();
        //obstacleSQL.genererObstacles(150);  // Par exemple, pour créer 50 obstacles dans la bdd

        this.listeObstacle = obstacleSQL.getTousLesObstacles();
        obstacleSQL.closeTable();

        this.monstreSQL = new MonstreSQL();
        this.listeMonstres = monstreSQL.getTousLesMonstres();

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

        // Met à jour uniquement en local
        listeJoueur.miseAJour();

        // Vérifie les collisions avec les autres joueurs
        for (Joueur autreJoueur : listeJoueur.getListeJoueurs()) {
            if (!autreJoueur.equals(joueurActif)) {
                if (verifierCollisionEntreJoueurs(joueurActif, autreJoueur)) {
                    System.out.println("Collision avec un autre joueur : " + autreJoueur.getNom());
                    joueurActif.annulerDernierDeplacement();
                }
            }
        }

        // Vérifie les collisions et annule le mouvement si besoin
        for (Obstacle o : listeObstacle) {
            if (verifierCollision(joueurActif, o)) {
                joueurActif.annulerDernierDeplacement();
            }
        }

        // Met à jour le joueur actif en BDD
        joueurSQL.modifierJoueur(joueurActif);

        if (joueurActif.estArrive()) {
            int i = joueurSQL.getProchainClassement();
            joueurSQL.setClassement(joueurActif, i);
        }

        for (Monstre m : listeMonstres) {
            m.miseAJour();
            monstreSQL.modifierMonstre(m);  // Sauvegarde la position mise à jour

            // Vérifie les collisions avec le joueur actif
            if (verifierCollisionAvecMonstre(joueurActif, m)) {
                System.out.println("Collision avec un monstre !");
                joueurActif.annulerDernierDeplacement();
            }
        }

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

        for (Monstre m : listeMonstres) {
            m.rendu(contexte, positionX, positionY);
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
        int largeurObstacle = obstacle.LARGEUR_OBSTACLE;
        int hauteurObstacle = obstacle.HAUTEUR_OBSTACLE;

        // Collision basique par chevauchement des rectangles
        return joueurX < obstacleX + largeurObstacle / 2
                && joueurX + largeurJoueur > obstacleX + largeurObstacle / 2
                && joueurY < obstacleY + hauteurObstacle / 2
                && joueurY + hauteurJoueur > obstacleY + hauteurObstacle / 2;

    }

    private boolean verifierCollisionAvecMonstre(Joueur joueur, Monstre monstre) {
        int joueurX = joueur.getXCoord();
        int joueurY = joueur.getYCoord();
        int largeurJoueur = joueur.getAvatar().LARGEUR_SPRITE;
        int hauteurJoueur = joueur.getAvatar().HAUTEUR_SPRITE;

        int monstreX = monstre.getX();
        int monstreY = monstre.getY();
        int largeurMonstre = monstre.getLargeur();
        int hauteurMonstre = monstre.getHauteur();

        return joueurX < monstreX + largeurMonstre
                && joueurX + largeurJoueur > monstreX
                && joueurY < monstreY + hauteurMonstre
                && joueurY + hauteurJoueur > monstreY;
    }

    private boolean verifierCollisionEntreJoueurs(Joueur j1, Joueur j2) {
        int x1 = j1.getXCoord();
        int y1 = j1.getYCoord();
        int largeur1 = j1.getAvatar().LARGEUR_SPRITE;
        int hauteur1 = j1.getAvatar().HAUTEUR_SPRITE;

        int x2 = j2.getXCoord();
        int y2 = j2.getYCoord();
        int largeur2 = j2.getAvatar().LARGEUR_SPRITE;
        int hauteur2 = j2.getAvatar().HAUTEUR_SPRITE;

        // Collision par chevauchement
        return x1 < x2 + largeur2 / 2
                && x1 + largeur1 / 2 > x2
                && y1 < y2 + hauteur2 / 2
                && y1 + hauteur1 / 2 > y2;
    }

    /**
     * Indique si la partie est terminée.
     */
    public boolean estTermine() {
        if (!joueurActif.estArrive()) {
            return false; // Pas encore arrivé
        }

        int classementActuel = joueurSQL.getClassement(joueurActif);
        if (classementActuel == 0) {
            int prochainClassement = joueurSQL.getProchainClassement();
            joueurSQL.setClassement(joueurActif, prochainClassement);
        }

        return true;  // Il a fini la course
    }

    /**
     * Retourne le joueur actuellement contrôlé.
     */
    public Joueur getJoueur() {
        return this.joueurActif;
    }
}
