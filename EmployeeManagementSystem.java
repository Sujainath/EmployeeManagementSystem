import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class Employee {

    private int id;
    private String name;
    private String role;
    private double basicSalary;
    private double pf;
    private int leaveDays;
    private double leaveLoss;
    private double totalSalary;

    public Employee(int id, String name, String role, double basicSalary, int leaveDays) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.basicSalary = basicSalary;
        this.leaveDays = leaveDays;
        calculateSalary();
    }

    public int getId() {
        return id;
    }

    
    private void calculateSalary() {
        pf = basicSalary * 0.05;

        if (leaveDays > 2) {
            int extraLeaves = leaveDays - 2;
            leaveLoss = extraLeaves * 500;
        } else {
            leaveLoss = 0;
        }

        totalSalary = basicSalary - pf - leaveLoss;
    }

    public void updateSalary(double newSalary) {
        this.basicSalary = newSalary;
        calculateSalary();
    }

    public void updateLeave(int newLeave) {
        this.leaveDays = newLeave;
        calculateSalary();
    }

    public void display() {
        System.out.println("---------------------------------------");
        System.out.println("ID           : " + id);
        System.out.println("Name         : " + name);
        System.out.println("Role         : " + role);
        System.out.println("Basic Salary : " + basicSalary);
        System.out.println("PF (5%)      : " + pf);
        System.out.println("Leave Days   : " + leaveDays);
        System.out.println("Leave Loss   : " + leaveLoss);
        System.out.println("Total Salary : " + totalSalary);
        System.out.println("---------------------------------------");
    }
}

public class EmployeeManagementSystem {

    static ArrayList<Employee> employees = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee Salary");
            System.out.println("5. Update Employee Leave");
            System.out.println("6. Delete Employee");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewAll();
                case 3 -> searchEmployee();
                case 4 -> updateSalary();
                case 5 -> updateLeave();
                case 6 -> deleteEmployee();
                case 7 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 7);
    }

    static void addEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Role: ");
        String role = sc.nextLine();

        System.out.print("Enter Basic Salary: ");
        double salary = sc.nextDouble();

        System.out.print("Enter Leave Days Taken: ");
        int leaveDays = sc.nextInt();

        employees.add(new Employee(id, name, role, salary, leaveDays));
        System.out.println("Employee Added Successfully!");
    }

    static void viewAll() {
        if (employees.isEmpty()) {
            System.out.println("No employees available!");
            return;
        }
        for (Employee e : employees) {
            e.display();
        }
    }

    static void searchEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        for (Employee e : employees) {
            if (e.getId() == id) {
                e.display();
                return;
            }
        }
        System.out.println("Employee NOT found!");
    }

    static void updateSalary() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        for (Employee e : employees) {
            if (e.getId() == id) {
                System.out.print("Enter New Basic Salary: ");
                double newSalary = sc.nextDouble();
                e.updateSalary(newSalary);
                System.out.println("Salary Updated Successfully!");
                return;
            }
        }
        System.out.println("Employee NOT found!");
    }

    static void updateLeave() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        for (Employee e : employees) {
            if (e.getId() == id) {
                System.out.print("Enter New Leave Days: ");
                int newLeave = sc.nextInt();
                e.updateLeave(newLeave);
                System.out.println("Leave Updated Successfully!");
                return;
            }
        }
        System.out.println("Employee NOT found!");
    }

    static void deleteEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        Iterator<Employee> it = employees.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                System.out.println("Employee Deleted Successfully!");
                return;
            }
        }
        System.out.println("Employee NOT found!");
    }
}
