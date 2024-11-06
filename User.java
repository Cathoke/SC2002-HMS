public abstract class User {
    protected String hospitalID;
    protected String password;
    protected String name;

    // Constructor method
    public User(String hospitalID, String password, String name) {
        this.hospitalID = hospitalID;
        this.password = "password";
        this.name = name;
    }

    // Setter for Hospital ID
    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    // Getter for Hospital ID
    public String getHospitalID() {
        return hospitalID;
    }

    // Login Method
    public boolean login(String hospitalID, String password) {
        return this.hospitalID.equals(hospitalID) && this.password.equals(password);
    }

    // Change Password Method
    public void changePassword(String newPassword) {
        password = newPassword;
    }

    // Getter for Password
    public String getPassword() {
        return password;
    }

    // Abstract Method
    public abstract void displayMenu();

}