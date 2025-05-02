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

public class Obstacles {

    protected BufferedImage sprite;
    protected int x, y;
    protected int id;

    public Obstacles() {
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/oB2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Obstacles.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.id = 1; //a modif plus tard
        random_spawn();
    }
    
    public void random_spawn() {
        this.x = (int) (Math.random() * 500);
        this.y = 10;
        
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();
            PreparedStatement requete = connexion.prepareStatement(
                    "UPDATE obstacles SET x_coordinate = ?, y_coordinate = ? WHERE id = ?"
            );
            requete.setInt(1, this.x);
            requete.setInt(2, this.y);
            requete.setInt(3, id);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void miseAJour() {
        
        if (y>1400) {
            random_spawn();
        }
        else {
        y = y + 30; // Vitesse de déplacement du caillou

        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();
            PreparedStatement requete = connexion.prepareStatement(
                    "UPDATE obstacles SET y_coordinate = ? WHERE id = ?"
            );
            requete.setInt(1, y);
            requete.setInt(2, id);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

    public void rendu(Graphics2D contexte) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();

            PreparedStatement requete = connexion.prepareStatement("SELECT x_coordinate, y_coordinate FROM obstacles WHERE id = ?");
            requete.setInt(1, id);
            ResultSet resultat = requete.executeQuery();
            if (resultat.next()) {
                int x_coordinate = resultat.getInt("x_coordinate");
                int y_coordinate = resultat.getInt("y_coordinate");
                //System.out.println(pseudo + " = (" + latitude + "; " + longitude + ")");

                //contexte.setColor(Color.red);
                //contexte.drawOval(x_coordinate - 7, y_coordinate - 7, 14, 14);
                //contexte.drawString(pseudo, x + 8, y - 8);
                contexte.drawImage(this.sprite, (int) x_coordinate, (int) y_coordinate, null);
            }
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public double getLargeur() {
        return sprite.getHeight();
    }

    public double getHauteur() {
        return sprite.getWidth();
    }
   
    
   public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    

}

