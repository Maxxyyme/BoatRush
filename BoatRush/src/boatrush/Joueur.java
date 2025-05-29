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

    
    //Constructeur du joueur avec son pseudo, sa position initiale et son skin
    public Joueur(String nom, int x, int y, int choixSkin) {
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheDroite = false;
        this.toucheGauche = false;
        this.pseudo = nom;
        this.x = x;
        this.y = y;
        this.avatar = new Avatar(choixSkin);
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


    //Applique les mouvements selon les touches et met à jour l'avatar
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

        // Gère les collisions avec les bords
        newX = Math.max(0, Math.min(newX, FenetreDeJeu.LARGEUR_FENETRE - Avatar.LARGEUR_SPRITE));
        newY = Math.max(0, Math.min(newY, 3200 - Avatar.HAUTEUR_SPRITE));

        // Applique les nouvelles coordonnées
        this.x = newX;
        this.y = newY;

        // Met à jour l'avatar 
        this.avatar.miseAJour();

    }
    
    //Sauvegarde la position actuelle avant déplacement
    public void sauvegarderPosition() {
        this.oldX = this.x;
        this.oldY = this.y;
    }

    //Restaure la dernière position (utile si collision)
    public void annulerDernierDeplacement() {
        this.x = this.oldX;
        this.y = this.oldY;
    }

    //Réinitialise toutes les touches directionnelles
    public void resetTouches() {
        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheGauche = false;
        this.toucheDroite = false;
    }

    //Indique si le joueur a atteint l'arrivée (haut de la carte)
    public boolean estArrive() {
        return this.y <= 50;  
    }


    //Dessine l'avatar du joueur sur le contexte graphique
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.avatar.getCurrentSprite(), this.x, this.y, null);
    }
    
    //Egalité sur le pseudo
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
    
}
