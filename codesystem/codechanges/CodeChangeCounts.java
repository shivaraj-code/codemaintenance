package com.io.codesystem.codechanges;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "code_change_counts")
public class CodeChangeCounts {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	public int id;
    
    @Column(name = "file_id")
	public int fileId;
    
    @Column(name = "added_records")
	public int addedRecords;
    
    @Column(name = "updated_records")
	public int updatedRecords;
    
    @Column(name = "deleted_records")
	public int deletedRecords;
    
    @Column(name = "status")
   	public String status;
    
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "inserted_date")
	public Timestamp insertedDate;
    
}
