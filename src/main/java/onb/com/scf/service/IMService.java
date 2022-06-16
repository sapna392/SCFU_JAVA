package onb.com.scf.service;

import java.util.List;
import org.springframework.stereotype.Service;

import onb.com.scf.dto.IMActivateRequest;
import onb.com.scf.dto.IMAndVendorResponse;
import onb.com.scf.dto.IMDeactivateReq;
import onb.com.scf.dto.IMDetailsResponseDto;
import onb.com.scf.dto.IMForApproveResponse;
import onb.com.scf.dto.ResponseDto;
import onb.com.scf.entity.IMEntity;

/**
 * @author Sapna Singh
 *
 */
@Service
public interface IMService {

	IMDetailsResponseDto getAllIM();

	IMDetailsResponseDto getIMByCode(String imCode);

	ResponseDto addImByMaker(IMEntity im);

	ResponseDto updateImByMaker(IMEntity im);

	ResponseDto activeIM(IMActivateRequest request);

	ResponseDto validateIMOBNumberAndEmail(IMActivateRequest request);

	IMForApproveResponse getAllUnAuthorisedTxnOfIM();

	ResponseDto authoriseIM(List<IMEntity> approvedIMList);

	ResponseDto deactivateIMByMaker(IMDeactivateReq request);

	ResponseDto deleteIMByMaker(String imCode);

	IMAndVendorResponse getAllIMCodeAndVendorCode(short identifier);
}
