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

public class Joueurs {
    
    protected BufferedImage spriteSheet;
    protected BufferedImage currentsprite;
    private int indiceSprite;


    public Joueurs() {
        
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
    }

    public void rendu(Graphics2D contexte) {

        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x_coordinate, y_coordinate FROM joueurs;");
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) {
                
                String pseudo = resultat.getString("pseudo");
                int x_coordinate = resultat.getInt("x_coordinate");
                int y_coordinate = resultat.getInt("y_coordinate");
                //System.out.println(pseudo + " = (" + latitude + "; " + longitude + ")");
                
                contexte.drawImage(this.currentsprite, (int) x_coordinate, (int) y_coordinate, null);
            }

            requete.close();           

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
