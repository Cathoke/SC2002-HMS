public class Staff {
    private String staffID;
    private String name;
    private String role; // e.g., Nurse, Pharmacist, Receptionist
    private String contactInfo;

    // Constructor
    public Staff(String staffID, String name, String role, String contactInfo) {
        this.staffID = staffID;
        this.name = name;
        this.role = role;
        this.contactInfo = contactInfo;
    }

    // Getter for Staff ID
    public String getStaffID() {
        return staffID;
    }

    // Setter for Staff ID
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    // Getter for Name
    public String getName() {
        return name;
    }

    // Setter for Name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for Role
    public String getRole() {
        return role;
    }

    // Setter for Role
    public void setRole(String role) {
        this.role = role;
    }

    // Getter for Contact Info
    public String getContactInfo() {
        return contactInfo;
    }

    // Setter for Contact Info
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Staff [ID: " + staffID + ", Name: " + name + ", Role: " + role + ", Contact Info: " + contactInfo + "]";
    }
}
