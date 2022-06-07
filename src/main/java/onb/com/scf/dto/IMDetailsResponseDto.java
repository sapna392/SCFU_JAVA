package onb.com.scf.dto;

import java.util.List;

import lombok.Data;
import onb.com.scf.entity.IMEntity;
/**
 * @author Sapna Singh
 *
 */
@Data
public class IMDetailsResponseDto {
	private String status;
	 private String statusCode;
	 private String msg;
	 List<IMEntity> data;

}
