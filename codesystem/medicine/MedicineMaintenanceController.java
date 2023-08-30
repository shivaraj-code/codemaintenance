package com.io.codesystem.medicine;

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
public class MedicineMaintenanceController {

	@Autowired
	private MedicineMaintenanceService medicineMaintenanceService;

	@GetMapping(path = "/verification/medicine")
	public ResponseEntity<Page<MedicineDataVerificationModel>> getNDCorName(@RequestParam Integer fileId,
			@RequestParam String searchTerm, @RequestParam String status, @RequestParam int pageSize,
			@RequestParam int pageNumber) {

		HttpHeaders headers = new HttpHeaders();
		Page<MedicineDataVerificationModel> response = medicineMaintenanceService.getNDCorName(fileId, searchTerm,
				status, pageSize, pageNumber);
		return new ResponseEntity<>(response, headers, HttpStatus.OK);

	}

	@GetMapping("/postsyncresults/medicine")
	public ResponseEntity<Page<MedicinePostSyncResultsModel>> getMedicinePostSyncresults(@RequestParam int fileId,
			@RequestParam String status, @RequestParam int pageSize, @RequestParam int pageNumber) {
		HttpHeaders headers = new HttpHeaders();
		Page<MedicinePostSyncResultsModel> response = medicineMaintenanceService.getMedicinePostSyncresults(fileId,
				status, pageSize, pageNumber);
		return new ResponseEntity<>(response, headers, HttpStatus.OK);

	}

	@GetMapping("/tableRecordCounts/medicine")
	public ResponseEntity<List<TableRecordCounts>> getTableRecordCounts() {
		List<TableRecordCounts> tableRecordCounts = medicineMaintenanceService.getTableRecordCounts();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(tableRecordCounts, headers, HttpStatus.OK);

	}

}