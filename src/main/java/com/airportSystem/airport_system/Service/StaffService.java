package com.airportSystem.airport_system.Service;

import com.airportSystem.airport_system.Entities.Staff;

public interface StaffService {
    
    public Staff staffLogin(String username, String password);

    public String staffRegister(Staff staff);

    public String PasswordMethod(String username, String oldPassword, String newPassword);

    public String staffLogout(Staff staff);

    public String deleteStaff(int id);

    // public Passenger findByUsernameAndPassword(String username, String password);

}
