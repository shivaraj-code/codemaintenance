package com.io.codesystem;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.io.codesystem.codechanges.CodeChangeCounts;
import com.io.codesystem.codemaintenancefile.CodeMaintenanceFile;
import com.io.codesystem.codemaintenancefile.CodeMaintenanceFileService;
import com.io.codesystem.config.CodeMaintenanceServiceConfig;
import com.io.codesystem.utils.CodeVerfication;
import com.io.codesystem.utils.CustomResponse;
import com.io.codesystem.utils.UtilsService;

@RestController
public class CodeMaintenanceController {

	@Autowired
	CodeMaintenanceServiceConfig codeMaintenanceServiceConfig;

	@Autowired
	CodeMaintenanceFileService codeMaintenanceFileService;

	@Autowired
	UtilsService utilsService;
		
	@PostMapping("/fileupload")
	public ResponseEntity<CustomResponse> uploadFileToS3(@RequestParam String codeType,
			@RequestParam String releaseVersion, @RequestParam Date releaseDate,
			@RequestParam MultipartFile releaseFile, @RequestParam int userId, @RequestParam String effectiveFrom,
			@RequestParam String effectiveTo) {

		CodeMaintenanceService codeMaintenaceService = codeMaintenanceServiceConfig
				.getCodeMaintenanceServiceByCodeType(codeType, 0);
		CustomResponse customResponse = codeMaintenaceService.uploadFileToS3(codeType, releaseVersion, releaseDate,
				releaseFile, userId, effectiveFrom, effectiveTo);
		    
	    return new ResponseEntity<>(customResponse, new HttpHeaders(), customResponse.getStatusCode());
	  }

	@GetMapping("/codestandardfile/list")
	public ResponseEntity<Page<CodeMaintenanceFile>> getCodeStandardFileDetails(@RequestParam String processedState,
			Pageable pageable) {
		Page<CodeMaintenanceFile> files = null;

		if (processedState.isBlank()) {
			files = codeMaintenanceFileService.getCodeStandardFileDetails(pageable);

		} else {

			files = codeMaintenanceFileService.getByProcessedState(processedState, pageable);
		}

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Page<CodeMaintenanceFile>>(files, headers, HttpStatus.OK);

	}

	@PostMapping("/fileprocess")

	public ResponseEntity<String> processAndPrepareVerificationData(@RequestParam int fileId,
			@RequestParam int userId) {

		CodeMaintenanceService codeMaintenaceService = codeMaintenanceServiceConfig
				.getCodeMaintenanceServiceByCodeType(null, fileId);
		codeMaintenaceService.processData(fileId, userId);

		return ResponseEntity.status(HttpStatus.ACCEPTED)

	               .header("Location", "/asynctasks/status/"+fileId+ "/FileProcess")
	               .build();
	} // markAsVerified

	@PostMapping("/fileverified")
	public ResponseEntity<CustomResponse> markAsVerified(@RequestParam int fileId, @RequestParam String verifiedType,
			@RequestParam int userId) {

		CodeMaintenanceService codeMaintenaceService = codeMaintenanceServiceConfig
				.getCodeMaintenanceServiceByCodeType(null, fileId);
		CustomResponse customResponse = codeMaintenaceService.markAsVerified(fileId, verifiedType, userId);

		return new ResponseEntity<>(customResponse, new HttpHeaders(), customResponse.getStatusCode());
	}

	@PostMapping("/filesynch")
	public ResponseEntity<String> syncVerifiedData(@RequestParam int fileId, @RequestParam int userId) {

		CodeMaintenanceService codeMaintenaceService = codeMaintenanceServiceConfig
				.getCodeMaintenanceServiceByCodeType(null, fileId);
		// CustomResponse customResponse=
		// codeMaintenaceService.synchVerifiedData(fileId, userId);
		codeMaintenaceService.syncVerifiedData(fileId, userId);

		// return new ResponseEntity<>(customResponse, new
		// HttpHeaders(),customResponse.getStatusCode());
		return ResponseEntity.status(HttpStatus.ACCEPTED)
	               .header("Location", "/asynctasks/status/"+fileId+"/FileSync")
	               .build();
	}

	@GetMapping("/changecounts")
	public ResponseEntity<CodeChangeCounts> getCodeChangeCounts(@RequestParam int fileId, @RequestParam int userId) {

		CodeChangeCounts changeCounts = utilsService.getCodeChangeCounts(fileId);
		return new ResponseEntity<>(changeCounts, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/filedelete")
	public ResponseEntity<CustomResponse> deleteCodeMaintenanceFile(@RequestParam int fileId,
			@RequestParam int userId) {

		CustomResponse customResponse = codeMaintenanceFileService.deleteCodeMaintenanceFile(fileId, userId);
		return new ResponseEntity<>(customResponse, new HttpHeaders(), customResponse.getStatusCode());
	}

	@PostMapping("/codeverification")
	public ResponseEntity<CustomResponse> saveCodeVerificationLog(@RequestBody CodeVerfication codes,
			@RequestParam String codeset, @RequestParam int fileId, @RequestParam int userId,
			@RequestParam String notes) {

		CodeMaintenanceService codeMaintenaceService = codeMaintenanceServiceConfig
				.getCodeMaintenanceServiceByCodeType(null, fileId);
		CustomResponse customResponse = codeMaintenaceService.saveCodeVerificationLogDetails(codes, codeset, fileId,
				userId, notes);
		// CustomResponse customResponse = new CustomResponse("", "", HttpStatus.OK);
		return new ResponseEntity<>(customResponse, new HttpHeaders(), customResponse.getStatusCode());

	}


}