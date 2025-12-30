package com.airportSystem.airport_system.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.airportSystem.airport_system.Dao.Passenger;
import com.airportSystem.airport_system.Dao.Staff;

@Service
public class StaffImplementation implements StaffService{

    @Autowired
    StaffRepository staffRepository;

    @Override
    public Staff staffLogin(String username, String password) {
        return staffRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public String PasswordMethod(String username, String oldPassword, String newPassword) {

        Staff existingStaff = staffRepository.findByUsername(username);
        if(existingStaff == null){
            return "Staff not found";
        }
        if (oldPassword != existingStaff.getPassword()) {
            return "Old password is incorrect";
        } else {
            staffRepository.changingPassword(username, oldPassword, newPassword);
            return null;
        }

        
    }

    @Override
    public String staffLogout(Staff staff) {
        if(staff == null){
            return "Staff not found";
        }
        staffRepository.delete(staff);
        return "Logged out successfully";
    }

    @Override
    public String staffRegister(Staff staff) {
        staffRepository.save(staff);
        return "Staff registered successfully";
    }

    @Override
    public String deleteStaff(int id) {
        staffRepository.deleteById(id);
        return "Staff deleted successfully";
    }

    
}
