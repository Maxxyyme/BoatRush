package boatrush;

import boatrush.Avatar;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import jdbc.JoueurSQL;
import outils.SingletonJDBC;

public class Joueurs {

    private boolean toucheHaut, toucheBas, toucheDroite, toucheGauche;
    protected String pseudo;
    protected int x, y;
    private JoueurSQL JSQL;
    private Avatar avatar;

    public Joueurs(String nom, int x, int y) {

        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheDroite = false;
        this.toucheGauche = false;
        this.pseudo = nom;
        this.x = x;
        this.y = y;
        this.JSQL = new JoueurSQL(); //Link à la BDD
        this.avatar = new Avatar(); // Link a l'avatar
        JSQL.creerJoueur(this);
        avatar.setPseudo(this.pseudo); 
        //JSQL.creerJoueur(this);

    }

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

    public void miseAJour() {

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

        // Collision avec les bords
        if (newX < 0) {
            newX = 0;
        }
        if (newX > 630) {
            newX = 630;
        }
        if (newY < 0) {
            newY = 0;
        }
        if (newY > 935) {
            newY = 935;
        }

        // Mettre à jour les coordonnées
        this.x = newX;
        this.y = newY;
        //MAJ de l'avatar
        this.JSQL.modifierJoueur(this);
        this.avatar.miseAJour();

        // Réinitialiser les touches
        toucheHaut = toucheBas = toucheGauche = toucheDroite = false;
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



//    public void rendu(Graphics2D contexte) {
//
//        try {
//
//            Connection connexion = SingletonJDBC.getInstance().getConnection();
//
//            PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x_coordinate, y_coordinate FROM joueurs;");
//            ResultSet resultat = requete.executeQuery();
//            while (resultat.next()) {
//                
//                String pseudo = resultat.getString("pseudo");
//                int x_coordinate = resultat.getInt("x_coordinate");
//                int y_coordinate = resultat.getInt("y_coordinate");
//                //System.out.println(pseudo + " = (" + latitude + "; " + longitude + ")");
//                
//                contexte.drawImage(this.currentsprite, (int) x_coordinate, (int) y_coordinate, null);
//            }
//
//            requete.close();           
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//    }

        }