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

public class Obstacle {

    protected BufferedImage sprite;
    protected int x, y;
    protected int id;
    public static final int LARGEUR_OBSTACLE = 21;
    public static final int HAUTEUR_OBSTACLE = 17;


    public Obstacle(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        try {
            this.sprite = ImageIO.read(getClass().getResource("../resources/caillou.png"));
        } catch (IOException ex) {
            Logger.getLogger(Obstacle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getXCoord() {
        return this.x;
    }

    public int getYCoord() {
        return this.y;
    }

    public int getId() {
        return this.id;
    }

    public void setXCoord(int x) {
        this.x = x;
    }

    public void setYCoord(int y) {
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public void random_spawn() {
//        this.x = (int) (Math.random() * 500);
//        this.y = 10;
//
//        try {
//            Connection connexion = SingletonJDBC.getInstance().getConnection();
//            PreparedStatement requete = connexion.prepareStatement(
//                    "UPDATE obstacles SET x_coordinate = ?, y_coordinate = ? WHERE id = ?"
//            );
//            requete.setInt(1, this.x);
//            requete.setInt(2, this.y);
//            requete.setInt(3, id);
//            requete.executeUpdate();
//            requete.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void miseAJour() {
    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }
    
    public BufferedImage getSprite() {
        return this.sprite;
    }


    public double getLargeur() {
        return sprite.getHeight();
    }

    public double getHauteur() {
        return sprite.getWidth();
    }

}
