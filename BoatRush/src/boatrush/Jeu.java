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
        this.carte = new Carte("CarteOcean.txt"); //On charge la carte
        this.listeJoueur = new Jouable(); //On créé une ArrayList de Joueurs vide --> Liste locale

        this.joueurSQL = new JoueurSQL(); //On établit la connexion à la table Joueur
        this.joueurActif = joueurActif; //On définit le joueur actif 
        listeJoueur.addJoueur(joueurActif); //On rajoute le joueur actif à la liste de joueur locale

        this.obstacleSQL = new ObstacleSQL(); //On établit la connexion à la table obstacle
        this.listeObstacle = obstacleSQL.getTousLesObstacles(); //On récupère les obstacles en bdd pour les mettre dans la liste d'obstacle locale
        obstacleSQL.closeTable(); //On ferme la connexion

        this.monstreSQL = new MonstreSQL(); //On établit la connexion à la table monstre
        this.listeMonstres = monstreSQL.getTousLesMonstres(); //On récupère les monstres en bdd pour les mettre dans la liste de monstres locale
    }

    
    //Fonction de debug/test
    private void afficherListeJoueurs(String contexte) {
        System.out.println("=== " + contexte + " ===");
        for (Joueur j : listeJoueur.getListeJoueurs()) {
            System.out.println("- " + j.getNom() + " (" + j.getXCoord() + ", " + j.getYCoord() + ")");
        }
        System.out.println("Nombre total: " + listeJoueur.getListeJoueurs().size());
    }


    //On met à jour 
    public void miseAJour() {
        carte.miseAJour(); //Mise à jour de la carte

        // On récupère les joueurs de la bdd dans une ArrayList
        ArrayList<Joueur> joueursDepuisBDD = joueurSQL.getTousLesJoueurs();

        // On met a jour les joueurs de la liste locale en les remplacant par ceux de la liste récupérée depuis la bdd (sauf le joueur actif = joueur contrôlé)
        //Synchronise les joueurs distants avec les joueurs locaux
        for (Joueur j : joueursDepuisBDD) {
            if (!j.getNom().equals(joueurActif.getNom())) {
                listeJoueur.remplacerJoueur(j); //On remplace juste les coordonnées des joueurs en les actualisant via la bdd
            }
        }

        // Supprime les joueurs qui n'existent plus en BDD --> supprime les joueurs qui ont quitté
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

        // On met à jour la liste locale des joueurs donc le joueur actif + joueur importés depuis la bdd
        listeJoueur.miseAJour();

        // Vérifie les collisions avec les autres joueurs
        for (Joueur autreJoueur : listeJoueur.getListeJoueurs()) {
            if (!autreJoueur.equals(joueurActif)) {
                if (verifierCollisionEntreJoueurs(joueurActif, autreJoueur)) {
                    joueurActif.annulerDernierDeplacement();
                }
            }
        }

        // Vérifie les collisions entre les obstacles et le joueur et annule le mouvement si besoin
        for (Obstacle o : listeObstacle) {
            if (verifierCollision(joueurActif, o)) {
                joueurActif.annulerDernierDeplacement();
            }
        }
        
        //On met à jour la position des monstre en local et en base de données
        for (Monstre m : listeMonstres) {
            m.miseAJour(); //MAJ locale
            monstreSQL.modifierMonstre(m);  //MAJ en bdd

            // Vérifie les collisions entre le monstre et le joueur actif
            if (verifierCollisionAvecMonstre(joueurActif, m)) {
                joueurActif.annulerDernierDeplacement();
            }
        }

        //On met à jour le joueur actif dans la base de donnée
        joueurSQL.modifierJoueur(joueurActif);
       
        //On vérifie si le joueur est arrivé, si c'est le cas, on set son classement dans la bdd
        if (joueurActif.estArrive()) {
            int i = joueurSQL.getProchainClassement();
            joueurSQL.setClassement(joueurActif, i);
        }

    }


    //Rendu graphique du jeu
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

    //Getter
    public Jouable getListeJoueur() {
        return this.listeJoueur;
    }

    //Fonction de vérification de collision entre joueur et obstacle
    private boolean verifierCollision(Joueur joueur, Obstacle obstacle) {
        int joueurX = joueur.getXCoord();
        int joueurY = joueur.getYCoord();
        int largeurJoueur = joueur.getAvatar().LARGEUR_SPRITE;
        int hauteurJoueur = joueur.getAvatar().HAUTEUR_SPRITE;  

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

    //Fonction de vérification de collision entre joueur et monstre
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
    
    //Fonction de vérification de collision entre joueur et joueur
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

    //Indique si la partie est terminée
    public boolean estTermine() {
        if (!joueurActif.estArrive()) {
            return false; // Pas encore arrivé
        }

        int classementActuel = joueurSQL.getClassement(joueurActif);
        if (classementActuel == 0) {
            int prochainClassement = joueurSQL.getProchainClassement();
            joueurSQL.setClassement(joueurActif, prochainClassement);
        }

        // Fermer proprement les connexions 
        monstreSQL.closeTable();
        return true;
    }

    //Retourne le joueur actuellement contrôlé
    public Joueur getJoueur() {
        return this.joueurActif;
    }
}
