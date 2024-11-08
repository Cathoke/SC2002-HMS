import java.sql.Time;
import java.util.Date;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private Date appointmentDate;
    private Time appointmentTime;
    private String status; // e.g., "confirmed", "cancelled", "completed"
    private String serviceType; // e.g., "Consultation", "Follow-up"
    private String medicine;
    private String notes; // Notes for the appointment outcome

    // Constructor
    public Appointment(Patient patient, Doctor doctor, Date appointmentDate, Time appointmentTime, String status) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.serviceType = ""; // Default to empty
        this.medicine = "";
        this.notes = ""; // Default to empty
    }

    // Getters and Setters
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getMedicine(){
        return medicine;
    }

    public void setMedicine(String medicine){
        this.medicine = medicine;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Method to update the status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Appointment status updated to: " + newStatus);
    }

    @Override
    public String toString() {
        return "Appointment [Date: " + appointmentDate +
                ", Time: " + appointmentTime +
                ", Status: " + status +
                ", Service Type: " + serviceType +
                ", Notes: " + notes +
                ", Medicine: " + medicine +
                ", Patient: " + (patient != null ? patient.getName() : "N/A") +
                ", Doctor: " + (doctor != null ? doctor.getName() : "N/A")+ "]";
    }
}
