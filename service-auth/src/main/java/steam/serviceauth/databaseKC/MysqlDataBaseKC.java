package steam.serviceauth.databaseKC;

import java.sql.*;

public class MysqlDataBaseKC {
    private Connection mySqlConnection;

    public MysqlDataBaseKC(){
        try{
            this.mySqlConnection=getConnection();
        }catch (Exception e){

        }
    }

    public static Connection getConnection() throws Exception{
        try{
            String driver="com.mysql.cj.jdbc.Driver";
            String url="jdbc:mysql://0.0.0.0:3366/keycloak_db";
            String username="root";
            String password="root_password";
            Connection connection = DriverManager.getConnection(url,username, password);
            System.out.println("test");
            return connection;
        }
        catch (Exception e){
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
        }
        public String getUserIDinKC(String pseudo) throws SQLException {
        String sql="SELECT ID from USER_ENTITY WHERE username=? ";
            PreparedStatement st = this.mySqlConnection.prepareStatement(sql);
            st.setString(1,pseudo);
            ResultSet resultSet =st.executeQuery();
            if(resultSet.next()){
                return resultSet.getString(1);
            }
            return null;
        }
    }

