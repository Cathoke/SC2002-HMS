import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pharmacist extends User {
    private List<Prescription> prescriptions;
    private Map<String, Integer> medicationInventory;

    // Constructor
    public Pharmacist(String hospitalID, String password, String name) {
        super(hospitalID, password, name);
        this.prescriptions = new ArrayList<>();
        this.medicationInventory = new HashMap<>();
    }

    // Method to view appointment outcome record
    public void viewAppointmentOutcomeRecord(Appointment appointment) {
        if (appointment == null) {
            System.out.println("Invalid appointment. Please provide a valid appointment.");
            return;
        }

        System.out.println("Appointment Outcome:");
        System.out.println("Date: " + appointment.getAppointmentDate() +
                ", Time: " + appointment.getAppointmentTime() +
                ", Service Type: " + appointment.getServiceType() +
                ", Notes: " + appointment.getNotes());
    }

    // Method to update prescription status
    public void updatePrescriptionStatus(Prescription prescription, String status) {
        if (prescription == null) {
            System.out.println("Invalid prescription. Please provide a valid prescription.");
            return;
        }

        prescription.updateStatus(status);
        System.out.println("Prescription for " + prescription.getMedicationName() +
                " has been updated to status: " + status);
    }

    // Method to submit replenishment request
    public void submitReplenishmentRequest(String medicationName, int quantity) {
        if (medicationName == null || medicationName.isEmpty() || quantity <= 0) {
            System.out.println("Invalid medication name or quantity. Please provide valid details.");
            return;
        }

        // Update the inventory or add a new medication entry
        medicationInventory.put(medicationName,
                medicationInventory.getOrDefault(medicationName, 0) + quantity);

        System.out.println("Replenishment request submitted for " + quantity +
                " units of " + medicationName + ".");
    }

    @Override
    public String toString() {
        return "Pharmacist Name: " + name + ", ID: " + hospitalID;
    }

    // Abstract method from User class
    @Override
    public void displayMenu() {
        System.out.println("1. View Appointment Outcome");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. Submit Replenishment Request");
    }
}
