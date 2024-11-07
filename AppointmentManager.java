import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentManager {
    private List<Appointment> appointments; // List to store all appointments

    // Constructor to initialize the appointment list
    public AppointmentManager() {
        this.appointments = new ArrayList<>();
    }

    // Method to add a new appointment to the list
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("Appointment added successfully.");
    }

    // Method to remove an appointment from the list
    public void removeAppointment(Appointment appointment) {
        if (appointments.remove(appointment)) {
            System.out.println("Appointment removed successfully.");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    // Method to find an appointment by date and time
    public Appointment findAppointment(Date appointmentDate, Time appointmentTime) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentDate().equals(appointmentDate) &&
                    appointment.getAppointmentTime().equals(appointmentTime)) {
                return appointment; // Appointment found
            }
        }
        return null; // No matching appointment found
    }

    // Method to list all appointments
    public void listAllAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }

    // Method to retrieve all available appointments
    public List<Appointment> getAvailableAppointments() {
        List<Appointment> availableAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equalsIgnoreCase("available")) {
                availableAppointments.add(appointment);
            }
        }
        return availableAppointments;
    }

    // Method to retrieve all pending appointments (e.g., for approval by doctors)
    public List<Appointment> getPendingAppointments() {
        List<Appointment> pendingAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equalsIgnoreCase("requested")) {
                pendingAppointments.add(appointment);
            }
        }
        return pendingAppointments;
    }

    // Method to retrieve appointments for a specific doctor
    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().equals(doctor)) {
                doctorAppointments.add(appointment);
            }
        }
        return doctorAppointments;
    }

    // Method to retrieve appointments for a specific patient
    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(patient)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }
}
