package com.scf.dto;

import lombok.Data;

@Data
public class IMActivateRequest {
	private String imCode;
	private Boolean isImInactive;
}