import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        Patient patient = new Patient("pat1", "2000-01-01", "Male", "1234567890", "O+", "John", "patPass",
                new ArrayList<>(), new ArrayList<>());
        Pharmacist pharmacist = new Pharmacist("pharm1", "pharmPass", "Eve");

        AppointmentManager manager = new AppointmentManager(); // Initialize AppointmentManager

        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        System.out.println("Welcome to the Hospital Management System!");

        // User Authentication
        while (currentUser == null) {
            System.out.println("Please select your role to log in:");
            System.out.println("1. Administrator");
            System.out.println("2. Doctor");
            System.out.println("3. Patient");
            System.out.println("4. Pharmacist");
            System.out.print("Enter your choice: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter Hospital ID: ");
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

        System.out.println("\nLogin successful! Welcome, " + currentUser.name + ".");
        displayRoleMenu(currentUser, scanner, manager, doctor);
    }

    private static void displayRoleMenu(User user, Scanner scanner, AppointmentManager manager, Doctor doctor) {
        boolean exit = false;
        while (!exit) {
            user.displayMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (user instanceof Administrator) {
                Administrator admin = (Administrator) user;
                handleAdminMenu(admin, choice, scanner);
            } else if (user instanceof Doctor) {
                Doctor docUser = (Doctor) user;
                handleDoctorMenu(docUser, choice, scanner);
            } else if (user instanceof Patient) {
                Patient patient = (Patient) user;
                handlePatientMenu(patient, choice, doctor, scanner, manager);
            } else if (user instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) user;
                handlePharmacistMenu(pharmacist, choice, scanner);
            } else {
                System.out.println("Invalid role.");
            }

            System.out.println("\nDo you want to continue? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("no")) {
                exit = true;
            }
        }

        System.out.println("Thank you for using the Hospital Management System. Goodbye!");
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

    private static void handleDoctorMenu(Doctor doctor, int choice, Scanner scanner) {
        switch (choice) {
            case 1: // View Patient Medical Records
                System.out.println("Viewing Patient Records...");
                break;
            case 2: // Update Patient Medical Records
                System.out.println("Manage appointments here.");
                break;
            case 3: // View Personal Schedule
                break;
            case 4: // Set Availability for Appointments
                break;
            case 5: // Accept or Decline Appointment Requests
                break;
            case 6: // View Upcoming Appointments
                break;
            case 7: // Record Appointment Outcome
                break;
            case 8: // logout
                break;
            default:
                System.out.println("Invalid option for Doctor.");
        }
    }

    private static void handlePatientMenu(Patient patient, int choice, Doctor doctor, Scanner scanner,
            AppointmentManager manager) {
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
                List<Appointment> availableAppointments = doctor.getAvailableAppointments();
                patient.viewAvailableAppointments(availableAppointments);
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
            default:
                System.out.println("Invalid option for Patient.");
        }
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
