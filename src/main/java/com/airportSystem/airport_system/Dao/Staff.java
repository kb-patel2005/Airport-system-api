package com.airportSystem.airport_system.Dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    //  @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "flights_gen") @TableGenerator( name = "flights_gen", table = "flights_seq", pkColumnName = "seq_name", valueColumnName = "seq_count", pkColumnValue = "flights", allocationSize = 1 


    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.TABLE, generator = "staff_gen")
    @jakarta.persistence.TableGenerator(
        name = "staff_gen",
        table = "staff_seq",
        pkColumnName = "seq_name",
        valueColumnName = "seq_count",
        pkColumnValue = "staff",
        allocationSize = 1
    )
    private int staffId;
    private String password;
    private String username;
    private String gender;
    private String role;
    private String phone;
    private String email;
    private String imgname;
    private String imgcontenttype;
    @Lob
    private byte[] image;
}
