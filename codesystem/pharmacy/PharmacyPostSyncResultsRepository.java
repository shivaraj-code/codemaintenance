package com.io.codesystem.pharmacy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface PharmacyPostSyncResultsRepository extends JpaRepository<PharmacyPostSyncResultsModel, Integer>{

	@Query(value="CALL GetPharmacyPostSyncData(:file_id,:status)",nativeQuery=true)
	public List<PharmacyPostSyncResultsModel> pharmacyPostSyncDataResults(Integer file_id,String status);
}
