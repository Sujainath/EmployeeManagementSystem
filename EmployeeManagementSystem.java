import java.sql.*;
import java.util.Scanner;

public class EmployeeManagementSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM (SQL MODE) =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee Salary");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewAll();
                case 3 -> searchEmployee();
                case 4 -> updateSalary();
                case 5 -> deleteEmployee();
                case 6 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    // 1. ADD EMPLOYEE TO SQL
    static void addEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Role: ");
        String role = sc.nextLine();
        System.out.print("Enter Basic Salary: ");
        double salary = sc.nextDouble();

        double pf = salary * 0.05;
        double totalSalary = salary - pf;

        String sql = "INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setString(3, role);
            pst.setDouble(4, salary);
            pst.setDouble(5, pf);
            pst.setDouble(6, totalSalary);

            pst.executeUpdate();
            System.out.println("Employee Added Successfully to SQL Database!");

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // 2. VIEW ALL FROM SQL
    static void viewAll() {
        String sql = "SELECT * FROM employees";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                displayFormat(rs);
            }
            if (!hasData) System.out.println("No employees available in Database!");

        } catch (SQLException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }

    // 3. SEARCH BY ID
    static void searchEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                displayFormat(rs);
            } else {
                System.out.println("Employee NOT found!");
            }
        } catch (SQLException e) {
            System.out.println("Error searching employee: " + e.getMessage());
        }
    }

    // 4. UPDATE SALARY IN SQL
    static void updateSalary() {
        System.out.print("Enter Employee ID to update salary: ");
        int id = sc.nextInt();
        System.out.print("Enter New Basic Salary: ");
        double newSalary = sc.nextDouble();

        double pf = newSalary * 0.05;
        double total = newSalary - pf;

        String sql = "UPDATE employees SET basic_salary = ?, pf = ?, total_salary = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setDouble(1, newSalary);
            pst.setDouble(2, pf);
            pst.setDouble(3, total);
            pst.setInt(4, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) System.out.println("Salary Updated in SQL!");
            else System.out.println("Employee NOT found!");

        } catch (SQLException e) {
            System.out.println("Error updating salary: " + e.getMessage());
        }
    }

    // 5. DELETE FROM SQL
    static void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) System.out.println("Employee Deleted from SQL!");
            else System.out.println("Employee NOT found!");

        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }

    // Helper method to show data
    private static void displayFormat(ResultSet rs) throws SQLException {
        System.out.println("---------------------------------------");
        System.out.println("ID           : " + rs.getInt("id"));
        System.out.println("Name         : " + rs.getString("name"));
        System.out.println("Role         : " + rs.getString("role"));
        System.out.println("Basic Salary : " + rs.getDouble("basic_salary"));
        System.out.println("PF (5%)      : " + rs.getDouble("pf"));
        System.out.println("Total Salary : " + rs.getDouble("total_salary"));
        System.out.println("---------------------------------------");
    }
}