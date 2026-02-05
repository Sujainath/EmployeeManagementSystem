import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    // 1. Database URL: 'ems_db' is the name of the database created in MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/employee_datas";
    
    // 2. Database credentials (Default is usually 'root')
    private static final String USER = "root"; 
    
    // 3. Database password (The one you set during MySQL installation)
    private static final String PASS = ""; 

    public static Connection getConnection() {
        Connection con = null;
        try {
            // Loading the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establishing the connection to the database
            con = DriverManager.getConnection(URL, USER, PASS);
            
        } catch (Exception e) {
            // Printing error message if connection fails
            System.out.println("Database Connection Error: " + e.getMessage());
        }
        return con;
    }
}