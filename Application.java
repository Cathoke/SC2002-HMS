import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // Initialize Users
        Administrator admin = new Administrator("admin1", "adminPass", "Alice");
        Doctor doctor = new Doctor("doc1", "Dr. Bob", "docPass", new ArrayList<>());
        Patient patient = new Patient("pat1", "2000-01-01", "Male", "1234567890", "O+", "John", "patPass", new ArrayList<>(), new ArrayList<>());
        Pharmacist pharmacist = new Pharmacist("pharm1", "pharmPass", "Eve");

        AppointmentManager manager = new AppointmentManager(); // Initialize AppointmentManager

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Hospital Management System!");

        while (true) {  // Keep running the application to allow multiple users to log in and out
            User currentUser = authenticateUser(scanner, admin, doctor, patient, pharmacist);

            if (currentUser != null) {
                System.out.println("\nLogin successful! Welcome, " + currentUser.name + ".");
                displayRoleMenu(currentUser, scanner, manager, doctor);
            }
        }
    }

    private static User authenticateUser(Scanner scanner, Administrator admin, Doctor doctor, Patient patient, Pharmacist pharmacist) {
        User currentUser = null;

        while (currentUser == null) {
            System.out.println("Please select your role to log in:");
            System.out.println("1. Administrator");
            System.out.println("2. Doctor");
            System.out.println("3. Patient");
            System.out.println("4. Pharmacist");
            System.out.print("Enter your choice: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter ID: ");
            String hospitalID = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            switch (roleChoice) {
                case 1:
                    if (admin.login(hospitalID, password)) {
                        currentUser = admin;
                    }
                    break;
                case 2:
                    if (doctor.login(hospitalID, password)) {
                        currentUser = doctor;
                    }
                    break;
                case 3:
                    if (patient.login(hospitalID, password)) {
                        currentUser = patient;
                    }
                    break;
                case 4:
                    if (pharmacist.login(hospitalID, password)) {
                        currentUser = pharmacist;
                    }
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

            if (currentUser == null) {
                System.out.println("Invalid credentials. Please try again.");
            }
        }

        return currentUser;
    }

    private static void displayRoleMenu(User user, Scanner scanner, AppointmentManager manager, Doctor doctor) {
        boolean logout = false;  // Track if the user chooses to logout
        while (!logout) {
            user.displayMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (user instanceof Administrator) {
                Administrator admin = (Administrator) user;
                handleAdminMenu(admin, choice, scanner);
            } else if (user instanceof Doctor) {
                Doctor docUser = (Doctor) user;
                logout = handleDoctorMenu(docUser, choice, scanner, manager);  // Capture logout status
            } else if (user instanceof Patient) {
                Patient patUser = (Patient) user;
                logout = handlePatientMenu(patUser, choice, doctor, scanner, manager);  // Capture logout status
            } else if (user instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) user;
                handlePharmacistMenu(pharmacist, choice, scanner);
            } else {
                System.out.println("Invalid role.");
            }

            if (!logout) {  // If user hasn't logged out, ask if they want to continue
                System.out.println("\nDo you want to continue? (yes/no): ");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("no")) {
                    logout = true;
                }
            }
        }

        System.out.println("Logging out. Returning to main menu...");
    }

    private static void handleAdminMenu(Administrator admin, int choice, Scanner scanner) {
        switch (choice) {
            case 1: // Manage Staff
                System.out.println("Managing staff...");
                break;
            case 2: // View Appointments
                admin.viewAppointments();
                break;
            case 3: // Manage Inventory
                admin.manageInventory("Paracetamol", 100);
                break;
            case 4: // Approve Replenishment
                admin.approveReplenishmentRequest("Ibuprofen");
                break;
            default:
                System.out.println("Invalid option for Administrator.");
        }
    }

    private static boolean handleDoctorMenu(Doctor doctor, int choice, Scanner scanner, AppointmentManager manager) {
    switch (choice) {
        case 1: /* View Patient Medical Records
            System.out.print("Enter Patient ID to view records: ");
            String patientID = scanner.nextLine();
            Patient patient = AppointmentManager.findPatientById(patientID); // Placeholder for method to find a patient
            if (patient != null) {
                doctor.viewPatientRecords(patient);
            } else {
                System.out.println("Patient not found.");
            }
            */
            break;

        case 2: /* Update Patient Medical Records
            System.out.print("Enter Patient ID to update records: ");
            patientID = scanner.nextLine();
            patient = AppointmentManager.findPatientById(patientID); // Placeholder for method to find a patient
            if (patient != null) {
                System.out.print("Enter Diagnosis: ");
                String diagnosis = scanner.nextLine();
                System.out.print("Enter Treatment: ");
                String treatment = scanner.nextLine();
                doctor.updatePatientRecord(patient, diagnosis, treatment);
            } else {
                System.out.println("Patient not found.");
            }
            */
            break;

        case 3: // View Personal Schedule
            List<Appointment> appointments = doctor.getUpcomingAppointments();
            if (appointments.isEmpty()) {
                System.out.println("No upcoming appointments.");
            } else {
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            }
            break;

        case 4: // Set Availability for Appointments
            System.out.println("Set availability as True(1) or False(0): ");
            int availabilityInput = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline
            boolean availability = availabilityInput == 1;

            try {
                System.out.print("Enter available date (dd/MM/yyyy): ");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                System.out.print("Enter available time (HH:mm): ");
                Time time = Time.valueOf(scanner.nextLine() + ":00");

                doctor.setAvailability(date, time, availability, manager);
            } catch (ParseException | IllegalArgumentException e) {
                System.out.println("Invalid date or time format. Please try again.");
            }
            break;

        case 5: // Accept or Decline Appointment Requests
            List<Appointment> pendingAppointments = doctor.getAvailableAppointments(); // Fetching only available ones
            if (pendingAppointments.isEmpty()) {
                System.out.println("No pending appointment requests.");
            } else {
                for (int i = 0; i < pendingAppointments.size(); i++) {
                    System.out.println((i + 1) + ". " + pendingAppointments.get(i));
                }
                System.out.print("Select an appointment to process (1-" + pendingAppointments.size() + "): ");
                int selection = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (selection >= 1 && selection <= pendingAppointments.size()) {
                    Appointment selectedAppointment = pendingAppointments.get(selection - 1);
                    System.out.print("Accept or Decline (accept/decline): ");
                    String decision = scanner.nextLine();
                    if (decision.equalsIgnoreCase("accept")) {
                        doctor.acceptAppointment(selectedAppointment);
                    } else if (decision.equalsIgnoreCase("decline")) {
                        doctor.declineAppointment(selectedAppointment);
                    } else {
                        System.out.println("Invalid input. Returning to menu.");
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            }
            break;

        case 6: // View Upcoming Appointments
            appointments = doctor.getUpcomingAppointments();
            if (appointments.isEmpty()) {
                System.out.println("No upcoming appointments.");
            } else {
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            }
            break;

        case 7: /* Record Appointment Outcome
            System.out.print("Enter Patient ID to record outcome: ");
            patientID = scanner.nextLine();
            patient = AppointmentManager.findPatientById(patientID); // Placeholder for patient fetching
            if (patient != null) {
                try {
                    System.out.print("Enter appointment date (dd/MM/yyyy): ");
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                    System.out.print("Enter appointment time (HH:mm): ");
                    Time time = Time.valueOf(scanner.nextLine() + ":00");

                    Appointment appointment = AppointmentManager.findAppointment(patient, date, time); // Placeholder
                    if (appointment != null) {
                        System.out.print("Enter service type: ");
                        String serviceType = scanner.nextLine();
                        System.out.print("Enter notes: ");
                        String notes = scanner.nextLine();
                        doctor.recordAppointmentOutcome(appointment, serviceType, notes);
                    } else {
                        System.out.println("Appointment not found.");
                    }
                } catch (ParseException | IllegalArgumentException e) {
                    System.out.println("Invalid date/time format.");
                }
            } else {
                System.out.println("Patient not found.");
            }
            */
            break;

        case 8: // Logout
            return true;

        default:
            System.out.println("Invalid option for Doctor.");
    }
    return false; // Continue in the Doctor menu
    }


    private static boolean handlePatientMenu(Patient patient, int choice, Doctor doctor, Scanner scanner, AppointmentManager manager) {
        switch (choice) {
            case 1: // View Medical Record
                patient.viewMedicalRecord();
                break;
            case 2: // Update Contact Information
                System.out.println("Enter the new contact information: ");
                String newContactInfo = scanner.nextLine();
                patient.updateContactInfo(newContactInfo);
                break;
            case 3: // View Available Appointments
                patient.viewAvailableAppointments(manager);
                break;
            case 4: // Schedule Appointment
                try {
                    System.out.println("Enter the appointment date (dd/MM/yyyy): ");
                    Date appointmentDate = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                    System.out.println("Enter the appointment time (HH:mm): ");
                    Time appointmentTime = Time.valueOf(scanner.nextLine() + ":00");
                    patient.scheduleAppointment(doctor, appointmentDate, appointmentTime);
                } catch (ParseException | IllegalArgumentException e) {
                    System.out.println("Invalid date/time format. Please try again.");
                }
                break;
            case 5: // Reschedule Appointment
                try {
                    System.out.println("Enter the current appointment date (dd/MM/yyyy): ");
                    Date currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                    System.out.println("Enter the current appointment time (HH:mm): ");
                    Time currentTime = Time.valueOf(scanner.nextLine() + ":00");

                    System.out.println("Enter the new appointment date (dd/MM/yyyy): ");
                    Date newDate = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                    System.out.println("Enter the new appointment time (HH:mm): ");
                    Time newTime = Time.valueOf(scanner.nextLine() + ":00");

                    Appointment appointment = manager.findAppointment(currentDate, currentTime);
                    if (appointment != null) {
                        patient.rescheduleAppointment(appointment, newDate, newTime);
                    } else {
                        System.out.println("Appointment not found.");
                    }
                } catch (ParseException | IllegalArgumentException e) {
                    System.out.println("Invalid date/time format. Please try again.");
                }
                break;
            case 6: // Cancel Appointment
                try {
                    System.out.println("Enter the appointment date to cancel (dd/MM/yyyy): ");
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                    System.out.println("Enter the appointment time to cancel (HH:mm): ");
                    Time time = Time.valueOf(scanner.nextLine() + ":00");

                    Appointment appointmentToCancel = manager.findAppointment(date, time);
                    if (appointmentToCancel != null) {
                        patient.cancelAppointment(appointmentToCancel);
                    } else {
                        System.out.println("No appointment found for the provided date and time.");
                    }
                } catch (ParseException | IllegalArgumentException e) {
                    System.out.println("Invalid date/time format. Please try again.");
                }
                break;
            case 7: // View scheduled appointment
                System.out.println("still making this");
                break;
            case 8: // Logout
                return true;  // Indicate logout
            default:
                System.out.println("Invalid option for Patient.");
        }
        return false;  // Continue in Patient menu
    }

    private static void handlePharmacistMenu(Pharmacist pharmacist, int choice, Scanner scanner) {
        switch (choice) {
            case 1: // Update Prescriptions
                System.out.println("Prescription updated.");
                break;
            default:
                System.out.println("Invalid option for Pharmacist.");
        }
    }
}
