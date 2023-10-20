package log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App{

    private final String url = "jdbc:postgresql://localhost:5432/OsCrash";
    private final String user = "work";
    private final String password = "*****";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            System.out.println("Data Loading...");
            
            String sql = "SELECT msg FROM log.debug WHERE method ='movementSafetyCheck';";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            
            while(result.next()) {
            	String msg = result.getString("msg");
//            	System.out.println(msg);
            	
            	String[] parts = msg.split(", ");
                for (String part : parts) {
                    String[] keyValue = part.split(": ");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        double value = Double.parseDouble(keyValue[1].trim());

                        if (key.equals("actPosX")) {
                            System.out.print (value + " ");
                        } 
                        else if (key.equals("actPosY")) {
                        	System.out.print(value + " ");
                        } 
                        else if (key.equals("actPosZ")) {
                        	System.out.println( value + " ");
                        }
                    }
                }
            	
            
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Failed to Connect");
        }
        return conn;
    }
    


    public static void main(String[] args) {
        App app = new App();
        app.connect();
    }
}
