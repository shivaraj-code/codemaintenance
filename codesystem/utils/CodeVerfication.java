package com.io.codesystem.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeVerfication {

	List<String> acceptedCodes;
	List<String> rejectedCodes;
	
	
}
