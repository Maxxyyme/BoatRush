package jdbc;

import java.sql.*;
import java.util.ArrayList;
import boatrush.Joueur;
import boatrush.Obstacle;
import java.util.Random;

public class ObstacleSQL {

    private final String adresseBase = "jdbc:mariadb://nemrod.ens2m.fr:3306/2024-2025_s2_vs1_tp2_boatrush";
    private final String user = "etudiant";
    private final String motdepasse = "YTDTvj9TR3CDYCmP";
    private Connection connexion;

    /**
     * Initialise la connexion à la base de données.
     */
    public ObstacleSQL() {
        try {
            this.connexion = DriverManager.getConnection(adresseBase, user, motdepasse);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Récupère tous les obstacles enregistrés en base.
     */
    public ArrayList<Obstacle> getTousLesObstacles() {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        try (PreparedStatement requete = connexion.prepareStatement("SELECT id, x_coordinate, y_coordinate FROM obstacles"); ResultSet resultat = requete.executeQuery()) {

            while (resultat.next()) {
                obstacles.add(new Obstacle(resultat.getInt("id"), resultat.getInt("x_coordinate"), resultat.getInt("y_coordinate")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obstacles;
    }

    /**
     * Crée un nouveau obstacle en base.
     */
    public void creerObstacle(Obstacle o) {
        try (PreparedStatement requete = connexion.prepareStatement("INSERT INTO obstacles VALUES (?, ?, ?)")) {
            requete.setInt(1, o.getId());
            requete.setInt(2, o.getXCoord());
            requete.setInt(3, o.getYCoord());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Met à jour la position d'un obstacle en base.
     */
    public void modifierObstacle(Obstacle o) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE obstacles SET id = ?, x_coordinate = ?, y_coordinate = ? WHERE id = ?")) {
            requete.setInt(1, o.getId());
            requete.setInt(2, o.getXCoord());
            requete.setInt(3, o.getYCoord());
            requete.setInt(4, o.getId());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Supprime un obstacle de la base.
     */
    public void supprimerObstacle(Obstacle o) {
        try (PreparedStatement requete = connexion.prepareStatement("DELETE FROM obstacles WHERE id = ?")) {
            requete.setInt(1, o.getId());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Recherche un obstacle par son id.
     */
    public Obstacle voirObstacle(int id) {
        Obstacle obstacleTrouve = null;
        try (PreparedStatement requete = connexion.prepareStatement("SELECT id, x_coordinate, y_coordinate FROM obstacles WHERE id = ?")) {
            requete.setInt(1, id);
            try (ResultSet resultat = requete.executeQuery()) {
                if (resultat.next()) {
                    obstacleTrouve = new Obstacle(resultat.getInt("id"), resultat.getInt("x_coordinate"), resultat.getInt("y_coordinate"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obstacleTrouve;
    }

    public void supprimerTousLesObstacles() {
        try (PreparedStatement requete = connexion.prepareStatement("DELETE FROM obstacles")) {
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void genererObstacles(int n) {
        Random rand = new Random();
        ArrayList<Obstacle> existants = getTousLesObstacles();
        int idDepart = existants.size() + 1; // continue après le dernier ID existant

        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(600 - 10 + 1) + 10; // À adapter selon les dimensions de la carte génération comprise entre 10 et 630
            int y = rand.nextInt(2800 - 200 + 1) + 200; // Y entre 200 et 2800
            Obstacle nouvelObstacle = new Obstacle(idDepart + i, x, y);
            creerObstacle(nouvelObstacle);
        }
    }

    /**
     * Ferme la connexion à la base.
     */
    public void closeTable() {
        try {
            if (this.connexion != null && !this.connexion.isClosed()) {
                this.connexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
