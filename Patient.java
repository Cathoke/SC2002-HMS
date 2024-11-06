
import java.util.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private String patientID;
    private String name;
    private String password;
    private String dateOfBirth;
    private String gender;
    private String contactInfo;
    private String bloodType;
    private List<Appointment> appointmentHistory;
    private List<MedicalRecord> medicalRecords;

    // Constructor
    public Patient(String patientID, String dateOfBirth, String gender, String contactInfo, String bloodType,
            String name, String password, List appointmentHistory, List medicalRecords) {
        super(patientID, password, name);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;
        this.medicalRecords = new ArrayList<>();
        this.appointmentHistory = new ArrayList<>();
    }

    // getter methods
    public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getBloodType() {
        return bloodType;
    }

    public List<Appointment> getAppointmentHistory() {
        return appointmentHistory;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    // Method to add a medical record
    public void addMedicalRecord(String diagnosis, String treatment, String date) {
        MedicalRecord medicalRecords = new MedicalRecord(diagnosis, treatment, date);
    }

    // To view Medical Records
    public void viewMedicalRecord() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Contact Information: " + contactInfo);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("\nMedical History:");
        if (medicalRecords.isEmpty()) {
            System.out.println("No past medical records found.");
        } else {
            for (MedicalRecord record : medicalRecords) {
                System.out.println(record); // Calls the toString() method of MedicalRecord
            }
        }
    }

    // Update Contact Information
    public void updateContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // To View Appointments
    public void viewAvailableAppointments(List<Appointment> allAppointments) {
        System.out.println("Available Appointment Slots:");
        boolean anyAvailable = false;
        for (Appointment appointment : allAppointments) {
            if (appointment.getStatus().equalsIgnoreCase("available")) { // Assuming 'available' indicates open slots
                System.out.println("Date: " + appointment.getAppointmentDate() +
                        ", Time: " + appointment.getAppointmentTime() +
                        ", Doctor: " + appointment.getDoctor().getName());
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("No available appointments at the moment.");
        }
    }

    public void scheduleAppointment(Doctor doctor, Date date, Time time) {
        // Create a new Appointment object to check availability
        Appointment newAppointment = new Appointment(this, doctor, date, time, "available");

        // Assuming each doctor has a list of available appointments
        List<Appointment> doctorAppointments = doctor.getAvailableAppointments();

        boolean isScheduled = false;

        for (Appointment appointment : doctorAppointments) {
            if (appointment.getAppointmentDate().equals(newAppointment.getAppointmentDate()) &&
                    appointment.getAppointmentTime().equals(newAppointment.getAppointmentTime()) &&
                    appointment.getStatus().equalsIgnoreCase("available")) {

                // Mark the appointment as confirmed
                appointment.updateStatus("confirmed");

                // Add the appointment to the patient's appointment history
                appointmentHistory.add(appointment);
                System.out.println("Appointment successfully scheduled on " + date + " at " + time + " with Dr. "
                        + doctor.getName());
                isScheduled = true;
                break;
            }
        }

        if (!isScheduled) {
            System.out.println("The selected appointment slot is unavailable. Please choose a different time.");
        }
    }

    public void rescheduleAppointment(Appointment appointment, Date newDate, Time newTime) {
        // Get the doctor associated with the current appointment
        Doctor doctor = appointment.getDoctor();

        // Check if the new date and time are available for the same doctor
        List<Appointment> doctorAppointments = doctor.getAvailableAppointments();
        boolean isRescheduled = false;

        for (Appointment availableAppointment : doctorAppointments) {
            if (availableAppointment.getAppointmentDate().equals(newDate) &&
                    availableAppointment.getAppointmentTime().equals(newTime) &&
                    availableAppointment.getStatus().equalsIgnoreCase("available")) {

                // Cancel the current appointment by updating its status
                appointment.updateStatus("cancelled");

                // Update the new appointment status to confirmed
                availableAppointment.updateStatus("confirmed");

                // Add the new appointment to the appointment history
                appointmentHistory.add(availableAppointment);

                System.out.println(
                        "Appointment rescheduled to " + newDate + " at " + newTime + " with Dr. " + doctor.getName());
                isRescheduled = true;
                break;
            }
        }

        if (!isRescheduled) {
            System.out.println("The selected slot on " + newDate + " at " + newTime
                    + " is unavailable. Please choose a different time.");
        }
    }

    public void cancelAppointment(Appointment appointment) {
        if (appointment == null) {
            System.out.println("Invalid appointment. Please provide a valid appointment to cancel.");
            return;
        }

        // Check if the appointment exists in the patient's history
        if (!appointmentHistory.contains(appointment)) {
            System.out.println("Appointment not found in your history.");
            return;
        }

        // Update the appointment status to cancelled
        appointment.updateStatus("cancelled");

        // Remove the appointment from the history
        appointmentHistory.remove(appointment);

        System.out.println(
                "Appointment on " + appointment.getAppointmentDate() + " at " + appointment.getAppointmentTime() +
                        " with Dr. " + appointment.getDoctor().getName() + " has been cancelled.");
    }

    public void viewScheduledAppointments() {
        System.out.println("Scheduled Appointments:");

        boolean hasScheduledAppointments = false;

        for (Appointment appointment : appointmentHistory) {
            if (appointment.getStatus().equalsIgnoreCase("confirmed")) {
                System.out.println("Date: " + appointment.getAppointmentDate() +
                        ", Time: " + appointment.getAppointmentTime() +
                        ", Doctor: " + appointment.getDoctor().getName());
                hasScheduledAppointments = true;
            }
        }

        if (!hasScheduledAppointments) {
            System.out.println("No scheduled appointments found.");
        }
    }

    // Abstract User Method
    @Override
    public void displayMenu() {
        System.out.println("Patient Menu:");
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Contact Information");
        System.out.println("3. View Available Appointments");
        System.out.println("4. Schedule Appointment");
        System.out.println("5. Reschedule Appointment");
        System.out.println("6. Cancel Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("0. Exit");
    }

}