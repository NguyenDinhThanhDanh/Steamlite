package steam.serviceauth.databaseKC;

import java.sql.*;

public class MysqlDataBaseKC {
    private Connection mySqlConnection;
    public Connection getMySqlConnection(){
        return mySqlConnection;
    }
    public MysqlDataBaseKC(){
        try{
            this.mySqlConnection=getMySqlConnection();
        }catch (Exception e){

        }
    }

    public void setMySqlConnection(Connection mySqlConnection){
        this.mySqlConnection=mySqlConnection;
    }
    public static Connection getConnection() throws Exception{
        try{
            String driver="com.mysql.cj.jdbc.Driver";
            String url="jdbc:mysql://localhost:3366/projet-8testpull_mysql-kc_1";
            String username="root";
            String password="root_password";
            Connection connection = DriverManager.getConnection(url,username, password);
            return connection;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
        }
        public String getUserIDinKC(String pseudo) throws SQLException {
        String sql="SELECT ID from USER_ENTITY WHERE username='pseudo'";
            PreparedStatement st = this.mySqlConnection.prepareStatement(sql);
            ResultSet resultSet =st.executeQuery();
            if(resultSet.next()){
                return resultSet.getString(1);
            }
            return null;
        }
    }

