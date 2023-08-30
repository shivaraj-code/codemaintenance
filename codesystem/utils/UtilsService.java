package com.io.codesystem.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.io.codesystem.codechanges.CodeChangeCounts;
import com.io.codesystem.codechanges.CodeChangeCountsRepository;
import com.io.codesystem.codemaintenancefile.CodeMaintenanceFile;
import com.io.codesystem.codemaintenancefile.CodeMaintenanceFileService;

@Service
public class UtilsService {

	@Value("${aws.s3.icd-folder}")
	private String icdFolderName;

	@Value("${aws.s3.medicine-folder}")
	private String medicineFolderName;


	@Value("${aws.s3.cpt-folder}")
	private String cptFolderName;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CodeChangeCountsRepository codeChangeCountsRepository;

	@Autowired
	CodeMaintenanceFileService codeMaintenanceFileService;
	
	@Autowired
	ValidationCheckRepository validationCheckRepository;

	public String getTargetCodeTypeFolderName(String codeType) {

		switch (codeType) {
		
		case "icd":
			return icdFolderName;

		case "medicine":
			return medicineFolderName;

		case "snomed":
			return "";

		default:
			return "invalid code type";

		}
	}

	public CodeMaintenanceFile prepareCodeMaintenaceFile(String codeType, String zipFileName, String filePath,
			String releaseVersion, Date releaseDate, int userId, String effectiveFrom, String effectiveTo) {

		CodeMaintenanceFile codeMaintenanceFile = new CodeMaintenanceFile();

		codeMaintenanceFile.setCodeStandard(codeType);
		codeMaintenanceFile.setFileName(zipFileName);
		codeMaintenanceFile.setFilePath(filePath);
		codeMaintenanceFile.setReleaseVersion(releaseVersion);
		codeMaintenanceFile.setReleaseDate(releaseDate);
		codeMaintenanceFile.setProcessedState("Uploaded");
		codeMaintenanceFile.setCurrentStatus("File Uploaded Successfully");
		codeMaintenanceFile.setNextState("Processed");
		codeMaintenanceFile.setActive(1);
		codeMaintenanceFile.setUserId(userId);
//TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		long currentTimeMillis = System.currentTimeMillis();
		Date currentDate = new Date(currentTimeMillis);
		codeMaintenanceFile.setProcessedDate(currentDate);
		codeMaintenanceFile.setSource("AMA");
		codeMaintenanceFile.setStatus("Success");
//TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		codeMaintenanceFile.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));
		codeMaintenanceFile.setModifiedUserId(userId);
		codeMaintenanceFile.setComments("");
		codeMaintenanceFile.setEffectiveFrom(effectiveFrom);
		codeMaintenanceFile.setEffectiveTo(effectiveTo);

		return codeMaintenanceFile;

	}


	public InputStream getTargetFileStreamFromZipFile(InputStream zipInputStream, String targetFilePath)
			throws IOException {
		try (ZipInputStream zipStream = new ZipInputStream(zipInputStream)) {
			ZipEntry zipEntry;

			while ((zipEntry = zipStream.getNextEntry()) != null) {
				String entryName = zipEntry.getName();
				System.out.println(entryName);
				System.out.println(targetFilePath);
				if (entryName.equalsIgnoreCase(targetFilePath)) {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					byte[] buffer = new byte[4096];
					int bytesRead;


					while ((bytesRead = zipStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
					}

					return new ByteArrayInputStream(outputStream.toByteArray());
				}
			}
		}

		return null;
	}

	@Transactional
	public String truncateTable(String tableName) {
		System.out.println("Truncating the Version Table");
		String status = "success";
		try {
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			status = "failed";
		}
		return status;
	}

	@Transactional
	public String dropTable(String tableName) {
		System.out.println("Dropping the dump Table if exists");
		String status = "success";
		try {
			entityManager.createNativeQuery("Drop TABLE IF EXISTS " + tableName).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			status = "failed";
		}
		return status;
	}

	@Transactional
	public String createNewTableFromExisting(String newTableName, String standardTableName) {
		System.out.println("New Table Creating from version Table");
		String status = "success";
		try {
			System.out.println("Table have to created with dumpname");



	        entityManager.createNativeQuery("CREATE TABLE "+newTableName+" AS SELECT * FROM " +standardTableName).executeUpdate();

		 }catch(Exception e) {
			 e.printStackTrace();
			 status="failed";
		 }
		 return status;
	    }


	public CodeChangeCounts getCodeChangeCounts(int fileId) {

		CodeMaintenanceFile file = codeMaintenanceFileService.getCodeMaintenanceFileById(fileId);
		String syncStatus = "";

		if (file != null) {

			if (file.getCurrentStatus().equalsIgnoreCase("Pending For Verification")) {
				syncStatus = "Pre Sync";
			}
			if (file.getCurrentStatus().equalsIgnoreCase("Synching Completed")) {
				syncStatus = "Post Sync";
			}

		}
		return codeChangeCountsRepository.findByStatus(syncStatus);
	}

	public String getDateInStringFormat(Date date, String format) {

		if (format.equalsIgnoreCase("default"))
			format = "yyyyMMdd";

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	public boolean prepareVerificationStatus(String verificationType, String codeStandard, Date releaseDate) {
		 
		  ValidationCheck status=validationCheckRepository.prepareVerificationStatus("version-validation", codeStandard, releaseDate);
		if ("failed".equalsIgnoreCase(status.getMessage()))
		   {
			
   		 return true;
		   } return false; 
		}
}