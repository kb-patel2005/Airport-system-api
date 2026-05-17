package com.airportSystem.airport_system.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airportSystem.airport_system.Dao.StaffRepository;
import com.airportSystem.airport_system.Entities.Staff;
import com.airportSystem.airport_system.Service.StaffService;

@Service
public class StaffImplementation implements StaffService{

    @Autowired
    StaffRepository staffRepository;

    @Override
    public Staff staffLogin(String email, String password) {
        return staffRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public String PasswordMethod(String email, String oldPassword, String newPassword) {

        Staff existingStaff = staffRepository.findByEmailAndPassword(email, newPassword);
        if(existingStaff == null){
            return "Staff not found";
        }
        if (oldPassword != existingStaff.getPassword()) {
            return "Old password is incorrect";
        } else {
            staffRepository.changingPassword(email, oldPassword, newPassword);
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
