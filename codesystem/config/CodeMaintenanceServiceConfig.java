package com.io.codesystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.io.codesystem.CodeMaintenanceService;
import com.io.codesystem.codemaintenancefile.CodeMaintenanceFile;
import com.io.codesystem.codemaintenancefile.CodeMaintenanceFileService;
import com.io.codesystem.icd.IcdCodeMaintenanceService;
import com.io.codesystem.medicine.MedicineMaintenanceService;
import com.io.codesystem.pharmacy.PharmacyCodeMaintenanceService;

@Configuration
public class CodeMaintenanceServiceConfig {

	@Autowired
	IcdCodeMaintenanceService icdCodeMaintenanceService;

	@Autowired
	MedicineMaintenanceService medicineMaintenanceService;

	@Autowired
	PharmacyCodeMaintenanceService pharmacyCodeMaintenanceService;

	@Autowired
	CodeMaintenanceFileService codeMaintenanceFileService;

	public CodeMaintenanceService getCodeMaintenanceServiceByCodeType(String codeType, int fileId) {

		if (codeType == null) {
			CodeMaintenanceFile file = codeMaintenanceFileService.getCodeMaintenanceFileById(fileId);
			codeType = file.getCodeStandard();
		}

		switch (codeType) {

		case "icd":
			return icdCodeMaintenanceService;

		case "medicine":
			return medicineMaintenanceService;

		case "pharmacy":
			return pharmacyCodeMaintenanceService;

		// Add Remaining Services here

		default:
			return null;

		}

	}

}
