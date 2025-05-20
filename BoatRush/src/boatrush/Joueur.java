package boatrush;

import java.awt.Graphics2D;

/**
 * Représente un joueur dans le jeu BoatRush.
 */
public class Joueur {

    // État des touches directionnelles
    private boolean toucheHaut, toucheBas, toucheDroite, toucheGauche;

    // Identité et position du joueur
    private String pseudo;
    private int x, y;           // Position actuelle
    private int oldX, oldY;     // Dernière position (pour annuler déplacement)

    // Apparence graphique du joueur
    private Avatar avatar;

    /**
     * Constructeur du joueur avec pseudo et position initiale.
     */
<<<<<<< Updated upstream
    public Joueur(String nom, int x, int y) {
=======
    public Joueur(String nom, int x, int y, int choixSkin) {
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheDroite = false;
        this.toucheGauche = false;
>>>>>>> Stashed changes
        this.pseudo = nom;
        this.x = x;
        this.y = y;
        this.avatar = new Avatar(choixSkin);
    }

    // ======= Getters =======
    public String getNom() {
        return this.pseudo;
    }

    public int getXCoord() {
        return this.x;
    }

    public int getYCoord() {
        return this.y;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    // ======= Setters =======
    public void setNom(String nom) {
        this.pseudo = nom;
    }

    public void setToucheHaut(boolean etat) {
        this.toucheHaut = etat;
    }

    public void setToucheBas(boolean etat) {
        this.toucheBas = etat;
    }

    public void setToucheGauche(boolean etat) {
        this.toucheGauche = etat;
    }

    public void setToucheDroite(boolean etat) {
        this.toucheDroite = etat;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // ======= Logique de déplacement =======

    /**
     * Met à jour la position du joueur selon les touches appuyées
     * et anime l'avatar.
     */
    public void miseAJour() {
        sauvegarderPosition();

        int newX = this.x;
        int newY = this.y;

        if (toucheHaut) newY -= 15;
        if (toucheBas) newY += 15;
        if (toucheDroite) newX += 15;
        if (toucheGauche) newX -= 15;

        // Collision avec les bords de la fenêtre
        newX = Math.max(0, Math.min(newX, FenetreDeJeu.LARGEUR_FENETRE - Avatar.LARGEUR_SPRITE));
        newY = Math.max(0, Math.min(newY, 3200 - Avatar.HAUTEUR_SPRITE));

        this.x = newX;
        this.y = newY;

        avatar.miseAJour();
    }

    /**
     * Sauvegarde la position actuelle avant déplacement.
     */
    public void sauvegarderPosition() {
        this.oldX = this.x;
        this.oldY = this.y;
    }

    /**
     * Restaure la dernière position (utile si collision).
     */
    public void annulerDernierDeplacement() {
        this.x = this.oldX;
        this.y = this.oldY;
    }

    /**
     * Réinitialise toutes les touches directionnelles.
     */
    public void resetTouches() {
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheGauche = false;
        this.toucheDroite = false;
    }

    /**
     * Indique si le joueur a atteint l'arrivée (haut de la carte).
     */
    public boolean estArrive() {
        return this.y <= 50;
    }

    /**
     * Dessine l'avatar du joueur sur le contexte graphique.
     */
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(avatar.getCurrentSprite(), x, y, null);
    }

    // ======= Override : égalité sur le pseudo =======

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Joueur other = (Joueur) obj;
        return this.pseudo.equals(other.pseudo);
    }

    @Override
    public int hashCode() {
        return pseudo.hashCode();
    }
}
