package boatrush;

import java.awt.Graphics2D;

public class Joueur {

    // Etat des touches
    private boolean toucheHaut, toucheBas, toucheDroite, toucheGauche;

    // Identité et position
    private String pseudo;
    private int x, y;
    private int oldX, oldY;

    // Avatar graphique du joueur
    private Avatar avatar;

    /**
     * Constructeur du joueur avec son pseudo et sa position initiale.
     */
    public Joueur(String nom, int x, int y) {
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheDroite = false;
        this.toucheGauche = false;
        this.pseudo = nom;
        this.x = x;
        this.y = y;
        this.avatar = new Avatar();
    }

    // Getters
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

    // Setters
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Joueur other = (Joueur) obj;
        return this.pseudo.equals(other.pseudo);
    }

    @Override
    public int hashCode() {
        return pseudo.hashCode();
    }

    /**
     * Applique les mouvements selon les touches et met à jour l'avatar.
     */
    public void miseAJour() {
        sauvegarderPosition();
        int newX = x;
        int newY = y;

        if (toucheHaut) {
            newY -= 15;
        }
        if (toucheBas) {
            newY += 15;
        }
        if (toucheDroite) {
            newX += 15;
        }
        if (toucheGauche) {
            newX -= 15;
        }

//        // Gère les collisions avec les bords
        newX = Math.max(0, Math.min(newX, FenetreDeJeu.LARGEUR_FENETRE - Avatar.LARGEUR_SPRITE));
        newY = Math.max(0, Math.min(newY, 3200 - Avatar.HAUTEUR_SPRITE));

        // Applique les nouvelles coordonnées
        this.x = newX;
        this.y = newY;

        // Met à jour l'avatar (animation)
        this.avatar.miseAJour();

        // Réinitialise les touches après application
        toucheHaut = toucheBas = toucheGauche = toucheDroite = false;
    }

    public void sauvegarderPosition() {
        this.oldX = this.x;
        this.oldY = this.y;
    }

    public void annulerDernierDeplacement() {
        this.x = this.oldX;
        this.y = this.oldY;
    }

    public void resetTouches() {
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheGauche = false;
        this.toucheDroite = false;
    }

    /**
     * Dessine l'avatar du joueur sur le contexte graphique.
     */
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.avatar.getCurrentSprite(), this.x, this.y, null);
    }
}
