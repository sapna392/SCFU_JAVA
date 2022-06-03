package com.scf.service;

import com.scf.dto.IMDeactivateReq;
import com.scf.dto.IMDetailsResponseDto;
import com.scf.dto.ResponseDto;
import com.scf.model.IM;

public interface IMService {

	IMDetailsResponseDto getAllIM();

	IMDetailsResponseDto getIMByCode(String imCode);

	ResponseDto delete(String imCode);

	ResponseDto addIm(IM im);

	ResponseDto isImInactive(IMDeactivateReq request);

	ResponseDto updateIm(IM im);

}
