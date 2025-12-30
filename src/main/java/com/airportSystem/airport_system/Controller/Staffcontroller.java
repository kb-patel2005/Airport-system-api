package com.airportSystem.airport_system.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.airportSystem.airport_system.Dao.LoginStaff;
import com.airportSystem.airport_system.Dao.Staff;
import com.airportSystem.airport_system.Dao.Stafftextdata;
import com.airportSystem.airport_system.ServiceProvider.StaffService;

@RestController
@CrossOrigin("*")
public class Staffcontroller {
    
    @Autowired
    StaffService staffService;

    @PostMapping("/staffLogin")
    public Staff getStaffMember(@RequestBody LoginStaff loginStaff) {
        Staff s = staffService.staffLogin(loginStaff.getUsername(), loginStaff.getPassword());
        if(s == null) {
            return null;
        }
        return s;
    }

    @PostMapping(value = "/staffRegister", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String staffRegister(@ModelAttribute Stafftextdata staffdata,@RequestParam("image") MultipartFile image) {
        Staff staff = new Staff();
        staff.setUsername(staffdata.getUsername());
        staff.setPassword(staffdata.getPassword());
        staff.setGender(staffdata.getGender());
        staff.setRole(staffdata.getRole());
        staff.setEmail(staffdata.getEmail());
        staff.setPhone(staffdata.getPhone());
        staff.setImgname(image.getOriginalFilename());
        staff.setImgcontenttype(image.getContentType());
        try {
            staff.setImage(image.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffService.staffRegister(staff);
    }

    // @PutExchange("/changePassword/{newPassword}")
    // public String changePassword(@RequestBody LoginStaff loginStaff) {
    //     return staffService.PasswordMethod(loginStaff.getUsername(), loginStaff.getPassword(), loginStaff.getNewPassword());
    // }

    @DeleteMapping("/staffLogout/{id}")
    public String staffLogout(@PathVariable int id) {
        return staffService.deleteStaff(id);
    }

}
