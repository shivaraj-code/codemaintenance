package com.io.codesystem.icd;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.io.codesystem.utils.TableRecordCounts;

@RestController
public class IcdCodeMaintenanceController {

	@Autowired
	private IcdCodeMaintenanceService icdCodeMaintenanceService;

	@GetMapping(path = "/verification/icd")
	public ResponseEntity<Page<IcdDataVerificationModel>> getIcdCodeOrDescOrstatus(@RequestParam Integer fileId,
			@RequestParam String searchTerm,
			@RequestParam String status, 
			@RequestParam int pageSize,
			@RequestParam int pageNumber)
	{
		HttpHeaders headers = new HttpHeaders();
		Page<IcdDataVerificationModel> response = icdCodeMaintenanceService.getIcdCodeOrDescOrstatus(fileId, searchTerm,
				status, pageSize, pageNumber);
		return new ResponseEntity<>(response, headers, HttpStatus.OK);

	}

	@GetMapping("/postsyncresults/icd")
	public ResponseEntity<Page<IcdPostSyncResultsModel>> getIcdPostSyncresults(@RequestParam int fileId,
			@RequestParam String status, @RequestParam int pageSize, @RequestParam int pageNumber) {
		Page<IcdPostSyncResultsModel> response = icdCodeMaintenanceService.getIcdPostSyncresults(fileId, status,
				pageSize, pageNumber);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(response, headers, HttpStatus.OK);

	}

	@GetMapping("/tableRecordCounts")
	public ResponseEntity<List<TableRecordCounts>> getTableRecordCounts() {
		List<TableRecordCounts> tableRecordCounts = icdCodeMaintenanceService.getTableRecordCounts();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(tableRecordCounts, headers, HttpStatus.OK);

	}

	}
