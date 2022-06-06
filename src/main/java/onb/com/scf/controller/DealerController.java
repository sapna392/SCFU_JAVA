package onb.com.scf.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import onb.com.scf.entity.DealerEntity;
import onb.com.scf.service.impl.DealerService;

/**
 * @author Naseem
 *
 */
@RestController
@RequestMapping("scfu/api")
public class DealerController {
	@Autowired
	DealerService dealerService;

	/**
	 * This api retrieves all the dealer detail from the database
	 * 
	 * @return success or failure response
	 */
	@GetMapping("/dealer")
	public ResponseEntity<List<DealerEntity>> getAllDealer() {
		try {
			List<DealerEntity> dealer = dealerService.getAllDealer();
			if (dealer.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(dealerService.getAllDealer(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This api retrieves the detail of a specific dealer based on
	 * 
	 * @param dealerId
	 * @return success or failure response
	 */
	@GetMapping("/dealer/{dealerid}")
	public ResponseEntity<DealerEntity> getDealer(@PathVariable("detailid") int detailId) {
		Optional<DealerEntity> detailData = dealerService.getDealerByCode(detailId);
		if (detailData.isPresent()) {
			return new ResponseEntity<>(detailData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This api deletes the detail of a specific dealer based on
	 * 
	 * @param dealerId
	 * @return success or failure response
	 */
	@DeleteMapping("/dealer/{dealerid}")
	public ResponseEntity<HttpStatus> deleteDealer(@PathVariable("dealerid") int dealerId) {
		try {
			dealerService.delete(dealerId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This api creates all the dealer detail in the database
	 * 
	 * @return dealerCode
	 */
	@PostMapping("/dealer")
	public int saveDealer(@RequestBody DealerEntity dealer) {
		dealerService.saveOrUpdate(dealer);
		return dealer.getDealerCode();
	}

	/**
	 * This api updates the dealer detail in the database
	 * 
	 * @param dealer
	 * @return dealer
	 */
	@PutMapping("/dealer")
	public DealerEntity update(@RequestBody DealerEntity dealer) {
		dealerService.saveOrUpdate(dealer);
		return dealer;
	}

	/**
	 * This api deactivates the dealer detail in the database
	 * 
	 * @param dealer
	 * @return dealer
	 */
	@PutMapping("/dealer/deactivate")
	public DealerEntity deActivate(@RequestBody DealerEntity dealer) {
		dealer.setIsDealerInactive(false);
		dealerService.saveOrUpdate(dealer);
		return dealer;
	}

	/**
	 * This api activates the dealer detail in the database
	 * 
	 * @param dealer
	 * @return dealer
	 */
	@PutMapping("/dealer/activate")
	public DealerEntity activate(@RequestBody DealerEntity dealer) {
		dealer.setIsDealerInactive(true);
		dealerService.saveOrUpdate(dealer);
		return dealer;
	}
}
