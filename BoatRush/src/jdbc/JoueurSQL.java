package jdbc;

import boatrush.Joueur;

import java.sql.*;
import java.util.ArrayList;

public class JoueurSQL {

    private static final String DB_URL = "jdbc:mariadb://nemrod.ens2m.fr:3306/2024-2025_s2_vs1_tp2_boatrush";
    private static final String DB_USER = "etudiant";
    private static final String DB_PASSWORD = "YTDTvj9TR3CDYCmP";

    private Connection connexion;

    /**
     * Initialise la connexion à la base de données.
     */
    public JoueurSQL() {
        try {
            this.connexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Crée un nouveau joueur dans la base.
     */
    public void creerJoueur(Joueur j) {
        String sql = "INSERT INTO joueurs VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, j.getNom());
            stmt.setInt(2, j.getXCoord());
            stmt.setInt(3, j.getYCoord());
            stmt.setInt(4, 0); // classement
            stmt.setBoolean(5, false); // prêt
            stmt.setInt(6, 0); // points ou autre
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Met à jour la position d’un joueur.
     */
    public void modifierJoueur(Joueur j) {
        String sql = "UPDATE joueurs SET x_coordinate = ?, y_coordinate = ? WHERE pseudo = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, j.getXCoord());
            stmt.setInt(2, j.getYCoord());
            stmt.setString(3, j.getNom());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Supprime un joueur.
     */
    public void supprimerJoueur(Joueur j) {
        String sql = "DELETE FROM joueurs WHERE pseudo = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, j.getNom());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retourne un joueur par pseudo.
     */
    public Joueur voirJoueur(String nom) {
        String sql = "SELECT pseudo, x_coordinate, y_coordinate FROM joueurs WHERE pseudo = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Joueur(rs.getString("pseudo"), rs.getInt("x_coordinate"), rs.getInt("y_coordinate"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Retourne tous les joueurs.
     */
    public ArrayList<Joueur> getTousLesJoueurs() {
        ArrayList<Joueur> joueurs = new ArrayList<>();
<<<<<<< Updated upstream
        String sql = "SELECT pseudo, x_coordinate, y_coordinate FROM joueurs";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                joueurs.add(new Joueur(rs.getString("pseudo"), rs.getInt("x_coordinate"), rs.getInt("y_coordinate")));
=======
        try (PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x_coordinate, y_coordinate,skin FROM joueurs"); ResultSet resultat = requete.executeQuery()) {

            while (resultat.next()) {
                joueurs.add(new Joueur(resultat.getString("pseudo"), resultat.getInt("x_coordinate"), resultat.getInt("y_coordinate"),resultat.getInt("skin")));
>>>>>>> Stashed changes
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return joueurs;
    }

    /**
     * Retourne les joueurs non prêts.
     */
<<<<<<< Updated upstream
    public ArrayList<Joueur> getTousLesJoueursPasPret() {
        ArrayList<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT pseudo, x_coordinate, y_coordinate FROM joueurs WHERE pret = FALSE";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                joueurs.add(new Joueur(rs.getString("pseudo"), rs.getInt("x_coordinate"), rs.getInt("y_coordinate")));
=======
    public void creerJoueur(Joueur j) {
        try (PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueurs VALUES (?, ?, ?, ?, ?,?)")) {
            requete.setString(1, j.getNom());
            requete.setInt(2, j.getXCoord());
            requete.setInt(3, j.getYCoord());
            requete.setInt(4, 0);
            requete.setInt(5, 0);
            requete.setInt(6,1);
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Met à jour la position d'un joueur en base.
     */
    public void modifierJoueur(Joueur j) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE joueurs SET pseudo = ?, x_coordinate = ?, y_coordinate = ? WHERE pseudo = ?")) {
            requete.setString(1, j.getNom());
            requete.setInt(2, j.getXCoord());
            requete.setInt(3, j.getYCoord());
            requete.setString(4, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Supprime un joueur de la base.
     */
    public void supprimerJoueur(Joueur j) {
        try (PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueurs WHERE pseudo = ?")) {
            requete.setString(1, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Recherche un joueur par son pseudo.
     */
    public Joueur voirJoueur(String nom) {
        Joueur joueurTrouve = null;
        try (PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x_coordinate, y_coordinate FROM joueurs WHERE pseudo = ?")) {
            requete.setString(1, nom);
            try (ResultSet resultat = requete.executeQuery()) {
                if (resultat.next()) {
                    joueurTrouve = new Joueur(resultat.getString("pseudo"), resultat.getInt("x_coordinate"), resultat.getInt("y_coordinate"), resultat.getInt("skin"));
                }
>>>>>>> Stashed changes
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return joueurs;
    }

    // --------------------------------------------
    // === Gestion course =========================
    // --------------------------------------------

    public int getClassement(Joueur j) {
        String sql = "SELECT classement FROM joueurs WHERE pseudo = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, j.getNom());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt("classement");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void setClassement(Joueur j, int classement) {
        String sql = "UPDATE joueurs SET classement = ? WHERE pseudo = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, classement);
            stmt.setString(2, j.getNom());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getProchainClassement() {
        String sql = "SELECT MAX(classement) AS maxClassement FROM joueurs";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("maxClassement") + 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 1; // premier classement si table vide
    }

    // --------------------------------------------
    // === Prêt / statut ==========================
    // --------------------------------------------

    public void setPret(Joueur j, boolean etat) {
        String sql = "UPDATE joueurs SET pret = ? WHERE pseudo = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setBoolean(1, etat);
            stmt.setString(2, j.getNom());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean tousLesJoueursSontPrets() {
        String sql = "SELECT COUNT(*) AS nb FROM joueurs WHERE pret = FALSE";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("nb") == 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setPret(Joueur j, boolean etat) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE joueurs SET pret = ? WHERE pseudo = ?")) {
            requete.setBoolean(1, etat);
            requete.setString(2, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setSkin(Joueur j, int choix) {
        try (PreparedStatement requete = connexion.prepareStatement(
                "UPDATE joueurs SET skin = ? WHERE pseudo = ?")) {
            requete.setInt(1, choix);
            requete.setString(2, j.getNom());
            requete.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean tousLesJoueursSontPrets() {
        try (PreparedStatement requete = connexion.prepareStatement(
                "SELECT COUNT(*) as nb FROM joueurs WHERE pret = FALSE"); ResultSet resultat = requete.executeQuery()) {
            if (resultat.next()) {
                return resultat.getInt("nb") == 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Ferme proprement la connexion.
     */
    public void closeTable() {
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
