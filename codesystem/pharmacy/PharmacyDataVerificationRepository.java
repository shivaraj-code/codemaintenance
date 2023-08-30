package com.io.codesystem.pharmacy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface PharmacyDataVerificationRepository extends JpaRepository<PharmacyDataVerificationModel, Integer>{

	@Query(value="CALL GetPharmacyVerificationDetails(:file_id,:ncpdp_id,:name, :address, :zip, :status)",nativeQuery=true)
	public List<PharmacyDataVerificationModel> getPharmacyVerificationDetails(Integer file_id, String ncpdp_id, String name,
			String address, String zip, String status);

	public PharmacyDataVerificationModel findByNcpdpid(String ncpdpCode);
}