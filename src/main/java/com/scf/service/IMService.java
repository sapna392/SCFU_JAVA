package com.scf.service;

import java.util.List;
import java.util.Optional;

import com.scf.dto.IMDeactivateReq;
import com.scf.dto.IMDetailsResponseDto;
import com.scf.dto.ResponseDto;
import com.scf.model.IM;

public interface IMService {

	IMDetailsResponseDto getAllIM();

	Optional<IM> getIMByCode(String imCode);

	ResponseDto delete(String imCode);

	void addIm(IM im);

	ResponseDto isImInactive(IMDeactivateReq request);

	ResponseDto updateIm(IM im);

}
