package com.io.codesystem.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.io.codesystem.utils.TableRecordCounts;

@RestController
public class PharmacyCodeMaintenanceController {

	@Autowired
	PharmacyCodeMaintenanceService pharmacyCodeMaintenanceService;
	
	@GetMapping("/verification/pharmacy")
    public ResponseEntity<Page<PharmacyDataVerificationModel>> searchPharmacyFromVerification(@RequestParam Integer fileId,
    		                                                   @RequestParam String ncpdpId,
    		                                                   @RequestParam String name,
    		                                                   @RequestParam String address,
    		                                                   @RequestParam String zip,
    		                                                   @RequestParam String status,
    		                                                   @RequestParam(defaultValue = "10") int pageSize,
    		                                                   @RequestParam(defaultValue = "0") int pageNo){
    	HttpHeaders headers = new HttpHeaders();
		Page<PharmacyDataVerificationModel> response = pharmacyCodeMaintenanceService.searchPharmacyFromVerification(fileId,ncpdpId,name,address,zip,status, pageSize, pageNo);
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
	   
	    @GetMapping("/postsyncresults/pharmacy")
	   	public ResponseEntity<Page<PharmacyPostSyncResultsModel>> getPharmacyPostSyncresults(@RequestParam int fileId,@RequestParam String status, 
	   			@RequestParam int pageSize, @RequestParam int pageNumber)
	   	{
	    	Page<PharmacyPostSyncResultsModel> response = pharmacyCodeMaintenanceService.getPharmacyPostSyncresults(fileId, status, pageSize, pageNumber);
	    		HttpHeaders headers = new HttpHeaders();
	   			return new ResponseEntity<>(response, headers, HttpStatus.OK);
	   		
	   	}
	    
	    @GetMapping("/pharmacy/tableRecordCounts")
		public ResponseEntity<List<TableRecordCounts>> getTableRecordCounts() {
			List<TableRecordCounts> tableRecordCounts = pharmacyCodeMaintenanceService.getTableRecordCounts();
			HttpHeaders headers = new HttpHeaders();
			return new ResponseEntity<>(tableRecordCounts, headers, HttpStatus.OK);

		}
	    
	   
}
