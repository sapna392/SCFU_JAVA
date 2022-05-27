package com.scf.dto;

import java.util.List;

import com.scf.model.IM;

import lombok.Data;

@Data
public class IMDetailsResponseDto {
	private String status;
	 private String statusCode;
	 private String msg;
	 List<IM>listOfIM;

}
