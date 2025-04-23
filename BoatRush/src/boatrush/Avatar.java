package boatrush;

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
import outils.SingletonJDBC;

public class Avatar {

    private boolean toucheHaut, toucheBas, toucheDroite, toucheGauche;
    private String pseudo;
    protected BufferedImage spriteSheet;
    protected BufferedImage currentsprite;
    protected int x, y;
    private int indiceSprite;

    public Avatar() {

        this.toucheHaut = false;
        this.toucheBas = false;
        this.toucheDroite = false;
        this.toucheGauche = false;
        this.pseudo = "maxime";

        try {
            this.spriteSheet = ImageIO.read(getClass().getResource("../resources/SpritesVert.png"));

        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.indiceSprite = 0;

    }

    public void updateSprite() {
        this.currentsprite = spriteSheet.getSubimage(indiceSprite * 32, 0, 32, 50);
    }

    public void miseAJour() {

        this.updateSprite();
        this.indiceSprite = (this.indiceSprite + 1) % 4;

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
        if (newX > 640 - currentsprite.getWidth()) {
            newX = 640 - currentsprite.getWidth();
        }
        if (newY < 0) {
            newY = 0;
        }
        if (newY > 945 - currentsprite.getHeight()) {
            newY = 945 - currentsprite.getHeight();
        }

        // Mettre à jour les coordonnées
        x = newX;
        y = newY;

        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();
            PreparedStatement requete = connexion.prepareStatement(
                    "UPDATE joueurs SET x_coordinate = ?, y_coordinate = ? WHERE pseudo = ?"
            );
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setString(3, pseudo);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Réinitialiser les touches
        toucheHaut = toucheBas = toucheGauche = toucheDroite = false;
    }

    public void rendu(Graphics2D contexte) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();

            PreparedStatement requete = connexion.prepareStatement("SELECT x_coordinate, y_coordinate FROM joueurs WHERE pseudo = ?");
            requete.setString(1, pseudo);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()) {
                int x_coordinate = resultat.getInt("x_coordinate");
                int y_coordinate = resultat.getInt("y_coordinate");
                //System.out.println(pseudo + " = (" + latitude + "; " + longitude + ")");

                //contexte.setColor(Color.red);
                //contexte.drawOval(x_coordinate - 7, y_coordinate - 7, 14, 14);
                //contexte.drawString(pseudo, x + 8, y - 8);
                contexte.drawImage(this.currentsprite, (int) x_coordinate, (int) y_coordinate, null);
            }
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

}
