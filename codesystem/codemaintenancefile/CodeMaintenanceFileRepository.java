package com.io.codesystem.codemaintenancefile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CodeMaintenanceFileRepository extends JpaRepository<CodeMaintenanceFile, Integer> {

	public Page<CodeMaintenanceFile> findByProcessedState(String processedState,Pageable pageable); 

}
