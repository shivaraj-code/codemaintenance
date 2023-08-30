package com.io.codesystem.medicine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.io.codesystem.icd.IcdPostSyncResultsModel;

@Repository
public interface MedicinePostSyncResultsRepository extends JpaRepository<MedicinePostSyncResultsModel, Integer> {

	@Query(value = "CALL GetMedicinePostSyncData(:file_id, :status)", nativeQuery = true)
	public List<MedicinePostSyncResultsModel> medicinePostSyncDataResults(Integer file_id, String status);
	
}