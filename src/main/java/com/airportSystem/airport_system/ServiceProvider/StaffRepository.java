package com.airportSystem.airport_system.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airportSystem.airport_system.Dao.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    
    @Query("SELECT s FROM Staff s WHERE s.username = ?1 AND s.password = ?2")
    public Staff findByUsernameAndPassword(String username, String password);

    @Query(value="UPDATE Staff s SET s.password = ?3 WHERE s.username = ?1 AND s.password = ?2",nativeQuery = true)
    public String changingPassword(String username,String oldPassword, String newPassword);

    public Staff findByUsername(String username);
    
}
