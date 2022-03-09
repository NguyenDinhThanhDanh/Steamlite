package steam.serviceauth.dao;
import jdk.jshell.execution.Util;
import steam.serviceauth.client.AES;
import steam.serviceauth.client.Client;
import steam.serviceauth.exception.UtilisateurPasInscritException;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MysqlClient {
    private static Connection mysqlConnection;

    public Connection getMysqlConnection() {

        return mysqlConnection;
    }

    public MysqlClient() {
        try {
            this.mysqlConnection = getConnection();
        }
        catch (Exception e){

        }
    }

    public void setMysqlConnection(Connection mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }

    public static Connection getConnection() throws Exception {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://0.0.0.0:3306/service_client";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println(connection);
            System.out.println("Connection a la base de données réussie");
            return connection;
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception(e);
        }
    }

    public long createUtilisateur(String motDePasse, String pseudo,String dateInscrit) {
        try{
            String sql = "INSERT INTO CLIENT (pseudo,mdp,dateInscrit) values (?, ?, ?)";
            PreparedStatement st = this.mysqlConnection.prepareStatement(sql);

            DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) ;
            LocalDate localDate = LocalDate.parse( dateInscrit , f ) ;
            java.sql.Date date = java.sql.Date.valueOf(localDate);

            String mdp = AES.encrypt(motDePasse, pseudo) ;

            st.setString(1, pseudo);
            st.setString(2, mdp);
            st.setDate(3, date);
            st.executeUpdate();
            return 1;
        }
        catch(Exception e) {
            System.out.println(e);
            return -1;
        }

    }

    public long deleteUtilisateur(String pseudo, String MDP) {
        try {
            PreparedStatement deleteUser = this.mysqlConnection.prepareStatement("DELETE from CLIENT where pseudo = ? and mdp = ?");

            String mdp = AES.encrypt(MDP, pseudo) ;

            deleteUser.setString(1, pseudo);
            deleteUser.setString(2, mdp);
            deleteUser.executeUpdate();
            return 1;
        }catch (Exception e){
            System.out.println(e);
            return -1;
        }
    }

    public boolean verifUser(String pseudo, String mdp) {
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;

        mdp = AES.encrypt(mdp, pseudo);

        try {
            String queryUser = "SELECT pseudo, mdp FROM CLIENT where pseudo=? and mdp=?";
            pstmt1 = this.mysqlConnection.prepareStatement(queryUser);
            pstmt1.setString(1, pseudo);
            pstmt1.setString(2, mdp);
            rs = pstmt1.executeQuery();
            if (rs.next()) {
                return true;
            }
            else{
                return false;
            }
        }catch (Exception e){
            System.out.println("erreur");
            System.out.println(e);
            return false;
        }
    }

    public Collection<Client> getAllUsers() {
        Collection<Client> users = new ArrayList<>();
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        try {
            String queryUser = "SELECT * FROM Client";
            pstmt1 = this.mysqlConnection.prepareStatement(queryUser);
            rs = pstmt1.executeQuery();
            while( rs.next()){
                int id = rs.getInt(1);
                String mdp = rs.getString(3);
                String pseudo = rs.getString(2);
                String dateInscrit = rs.getString(4);
                Client user = new Client(pseudo,mdp,dateInscrit);
                user.setIdC(id);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }


    public Client getUserById(int idUser){
        ResultSet result = null;
        Client user=null;
        try {
            PreparedStatement requeteUtilisateur = this.mysqlConnection.prepareStatement("SELECT * FROM UTILISATEUR where idU = ?");
            requeteUtilisateur.setInt(1,idUser);

            result = requeteUtilisateur.executeQuery();
            while( result.next()) {
                String pseudo = result.getString(2);
                String motDePasse = AES.decrypt(result.getString(3),pseudo);
                String dateInscrit = result.getString(4);
                user = new Client(pseudo, motDePasse,dateInscrit);
                user.setIdC(idUser);
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;

    }





    public int getUserId(String pseudo, String mdp) throws UtilisateurPasInscritException {
        ResultSet result = null;
        try{
            PreparedStatement getId = this.mysqlConnection.prepareStatement("SELECT idC FROM Client where pseudo = ? and mdp = ?");
            mdp = AES.encrypt(mdp, pseudo) ;
            getId.setString(1, pseudo);
            getId.setString(2, mdp);

            result = getId.executeQuery();

            if(result.next()){
                return result.getInt(1);
            }

        }
        catch (Exception e){
            System.out.println(e);
            return -1;
        }
        return 0;
    }

    public Client getUserByPseudo(String pseudo){
        ResultSet result = null;
        Client user=null;
        try {
            PreparedStatement requeteUtilisateur = this.mysqlConnection.prepareStatement("SELECT * FROM UTILISATEUR where pseudo = ?");
            requeteUtilisateur.setString(1,pseudo);

            result = requeteUtilisateur.executeQuery();
            while( result.next()) {
                String motDePasse = AES.decrypt(result.getString(2),pseudo);
                String dateInscrit = result.getString(4);
                user = new Client(pseudo, motDePasse,dateInscrit);
                user.setIdC(result.getInt(1));
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;

    }

}