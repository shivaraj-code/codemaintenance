package com.io.codesystem.icd;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IcdPostSyncResultsRepository  extends JpaRepository<IcdPostSyncResultsModel, Integer> {

	@Query(value="CALL GetIcdPostSyncData(:file_id,:status)",nativeQuery=true)
	public List<IcdPostSyncResultsModel> icdPostSyncDataResults(Integer file_id,String status);
	
}