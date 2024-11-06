import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Administrator extends User {
    private List<Staff> staffList;
    private List<Appointment> appointments;
    private Map<String, Integer> medicationInventory;

    // Constructor
    public Administrator(String hospitalID, String password, String name) {
        super(hospitalID, password, name);
        this.staffList = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.medicationInventory = new HashMap<>();
    }

    // Method to manage staff (add, update, remove)
    public void manageStaff(Staff staff, String action) {
        if (staff == null || action == null || action.isEmpty()) {
            System.out.println("Invalid staff or action. Please provide valid details.");
            return;
        }

        switch (action.toLowerCase()) {
            case "add":
                staffList.add(staff);
                System.out.println("Staff added: " + staff.getName());
                break;

            case "update":
                boolean found = false;
                for (int i = 0; i < staffList.size(); i++) {
                    if (staffList.get(i).getStaffID().equals(staff.getStaffID())) {
                        staffList.set(i, staff);
                        System.out.println("Staff updated: " + staff.getName());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Staff not found for update.");
                }
                break;

            case "remove":
                if (staffList.removeIf(existingStaff -> existingStaff.getStaffID().equals(staff.getStaffID()))) {
                    System.out.println("Staff removed: " + staff.getName());
                } else {
                    System.out.println("Staff not found for removal.");
                }
                break;

            default:
                System.out.println("Invalid action. Please use 'add', 'update', or 'remove'.");
        }
    }

    // Method to view all appointments
    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            System.out.println("List of Appointments:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }

    // Method to manage inventory (update quantity)
    public void manageInventory(String medicationName, int quantity) {
        if (medicationName == null || medicationName.isEmpty() || quantity < 0) {
            System.out.println("Invalid medication name or quantity.");
            return;
        }

        medicationInventory.put(medicationName, quantity);
        System.out.println("Inventory updated: " + medicationName + " -> Quantity: " + quantity);
    }

    // Method to approve replenishment request
    public void approveReplenishmentRequest(String medicationName) {
        if (medicationName == null || !medicationInventory.containsKey(medicationName)) {
            System.out.println("Invalid medication or not found in inventory.");
            return;
        }

        int currentStock = medicationInventory.getOrDefault(medicationName, 0);
        int replenishedStock = currentStock + 100; // Assuming fixed replenishment quantity
        medicationInventory.put(medicationName, replenishedStock);

        System.out.println("Replenishment approved for " + medicationName + ". New quantity: " + replenishedStock);
    }

    @Override
    public String toString() {
        return "Administrator Name: " + name + ", ID: " + hospitalID;
    }

    // Abstract method from User class
    @Override
    public void displayMenu() {
        System.out.println("Administrator Menu:");
        System.out.println("1. Manage Staff");
        System.out.println("2. View Appointments");
        System.out.println("3. Manage Inventory");
        System.out.println("4. Approve Replenishment Requests");
    }
}
