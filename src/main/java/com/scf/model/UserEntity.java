package com.scf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Naseem
 *
 */
@Entity
@Data
@Table (name = "USER_ENTITY")
public class UserEntity {
	
	@Id
	@Column
	private String typeOfEntity;
	@Column
	private int stratValue;
	@Column
	private String prefix;
	
	

}
