package onb.com.scf.service;

import onb.com.scf.dto.IMActivateRequest;
import onb.com.scf.dto.IMDeactivateReq;
import onb.com.scf.dto.IMDetailsResponseDto;
import onb.com.scf.dto.ResponseDto;
import onb.com.scf.entity.IMEntity;

public interface IMService {

	IMDetailsResponseDto getAllIM();

	IMDetailsResponseDto getIMByCode(String imCode);

	ResponseDto delete(String imCode);

	ResponseDto addIm(IMEntity im);

	ResponseDto isImInactive(IMDeactivateReq request);

	ResponseDto updateIm(IMEntity im);

	ResponseDto activeIM(IMActivateRequest request);

	ResponseDto validateIMOBNumberAndEmail(IMActivateRequest request);

}
