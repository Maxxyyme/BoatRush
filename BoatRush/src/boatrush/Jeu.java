package boatrush;

import java.awt.Graphics2D;
import java.util.ArrayList;
import jdbc.JoueurSQL;
import jdbc.MonstreSQL;
import jdbc.ObstacleSQL;

/**
 * Gère la logique centrale du jeu : mise à jour des entités, collisions,
 * synchronisation avec la base de données, rendu.
 */
public class Jeu {

    private Carte carte;
    private Jouable listeJoueur;
    private ArrayList<Obstacle> listeObstacle;
    private ArrayList<Monstre> listeMonstres;

    private Joueur joueurActif;
    private JoueurSQL joueurSQL;
    private ObstacleSQL obstacleSQL;
    private MonstreSQL monstreSQL;

    /**
     * Initialise le jeu avec un joueur principal.
     */
    public Jeu(Joueur joueurActif) {
        this.carte = new Carte("CarteOcean.txt");
        this.joueurActif = joueurActif;

        this.listeJoueur = new Jouable();
        this.listeJoueur.addJoueur(joueurActif);

        this.joueurSQL = new JoueurSQL();

        this.obstacleSQL = new ObstacleSQL();
        this.listeObstacle = obstacleSQL.getTousLesObstacles();
        obstacleSQL.closeTable();

        this.monstreSQL = new MonstreSQL();
        this.listeMonstres = monstreSQL.getTousLesMonstres();
    }

    /**
     * Met à jour les entités du jeu : joueurs, obstacles, monstres.
     */
    public void miseAJour() {
        carte.miseAJour();

        // Synchronise les joueurs distants
        ArrayList<Joueur> joueursDepuisBDD = joueurSQL.getTousLesJoueurs();

        for (Joueur j : joueursDepuisBDD) {
            if (!j.getNom().equals(joueurActif.getNom())) {
                listeJoueur.remplacerJoueur(j);
            }
        }

        // Supprime les joueurs qui ont quitté
        listeJoueur.getListeJoueurs().removeIf(joueurLocal ->
                joueursDepuisBDD.stream().noneMatch(j -> j.getNom().equals(joueurLocal.getNom()))
        );

        // Met à jour les mouvements des joueurs
        listeJoueur.miseAJour();

        // Collisions avec joueurs distants
        for (Joueur autre : listeJoueur.getListeJoueurs()) {
            if (!autre.equals(joueurActif) && verifierCollisionEntreJoueurs(joueurActif, autre)) {
                joueurActif.annulerDernierDeplacement();
            }
        }

        // Collisions avec obstacles
        for (Obstacle o : listeObstacle) {
            if (verifierCollision(joueurActif, o)) {
                joueurActif.annulerDernierDeplacement();
            }
        }

        // Enregistre la position du joueur actif
        joueurSQL.modifierJoueur(joueurActif);

        if (joueurActif.estArrive()) {
            int classement = joueurSQL.getProchainClassement();
            joueurSQL.setClassement(joueurActif, classement);
        }

        // Mise à jour et collision avec les monstres
        for (Monstre m : listeMonstres) {
            m.miseAJour();
            monstreSQL.modifierMonstre(m);

            if (verifierCollisionAvecMonstre(joueurActif, m)) {
                joueurActif.annulerDernierDeplacement();
            }
        }
    }

    /**
     * Affiche les éléments du jeu à l'écran.
     */
    public void rendu(Graphics2D contexte) {
        double positionX = 0; // Pas de scrolling horizontal
        double positionY = joueurActif.getYCoord() - FenetreDeJeu.HAUTEUR_FENETRE / 2.0;
        positionY = Math.max(0, Math.min(positionY, carte.getHauteurEnPixels() - FenetreDeJeu.HAUTEUR_FENETRE));

        int nbTuilesX = FenetreDeJeu.LARGEUR_FENETRE / 32;
        int nbTuilesY = FenetreDeJeu.HAUTEUR_FENETRE / 32;

        carte.rendu(contexte, positionX, positionY, nbTuilesX, nbTuilesY);
        listeJoueur.rendu(contexte, positionX, positionY);

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

    public Joueur getJoueur() {
        return this.joueurActif;
    }

    /**
     * Détermine si la course est terminée pour ce joueur.
     */
    public boolean estTermine() {
        if (!joueurActif.estArrive()) return false;

        int classementActuel = joueurSQL.getClassement(joueurActif);
        if (classementActuel == 0) {
            int prochainClassement = joueurSQL.getProchainClassement();
            joueurSQL.setClassement(joueurActif, prochainClassement);
        }

        // Fermer proprement les connexions 
        monstreSQL.closeTable();
        return true;
    }

    // ========== COLLISIONS ==========

    private boolean verifierCollision(Joueur j, Obstacle o) {
        return j.getXCoord() < o.getXCoord() + Obstacle.LARGEUR_OBSTACLE / 2 &&
               j.getXCoord() + j.getAvatar().LARGEUR_SPRITE > o.getXCoord() + Obstacle.LARGEUR_OBSTACLE / 2 &&
               j.getYCoord() < o.getYCoord() + Obstacle.HAUTEUR_OBSTACLE / 2 &&
               j.getYCoord() + j.getAvatar().HAUTEUR_SPRITE > o.getYCoord() + Obstacle.HAUTEUR_OBSTACLE / 2;
    }

    private boolean verifierCollisionAvecMonstre(Joueur j, Monstre m) {
        return j.getXCoord() < m.getX() + m.getLargeur() &&
               j.getXCoord() + j.getAvatar().LARGEUR_SPRITE > m.getX() &&
               j.getYCoord() < m.getY() + m.getHauteur() &&
               j.getYCoord() + j.getAvatar().HAUTEUR_SPRITE > m.getY();
    }

    private boolean verifierCollisionEntreJoueurs(Joueur j1, Joueur j2) {
        return j1.getXCoord() < j2.getXCoord() + j2.getAvatar().LARGEUR_SPRITE / 2 &&
               j1.getXCoord() + j1.getAvatar().LARGEUR_SPRITE / 2 > j2.getXCoord() &&
               j1.getYCoord() < j2.getYCoord() + j2.getAvatar().HAUTEUR_SPRITE / 2 &&
               j1.getYCoord() + j1.getAvatar().HAUTEUR_SPRITE / 2 > j2.getYCoord();
    }
}
