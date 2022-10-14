package com.cyberazon.jewellery.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cyberazon.jewellery.utils.Role;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class AppUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String mobile;	
	private String emailId;	
	//private String dob;
	
	private String address;
	private String city;
	private String state;
	private String code;
	
	
	
	private String gender;
	private int forceChange;
	private String activationCode;
	private int active;
	

}
