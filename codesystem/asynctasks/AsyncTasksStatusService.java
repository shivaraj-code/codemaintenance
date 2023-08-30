package com.io.codesystem.asynctasks;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncTasksStatusService {

	@Autowired
	AsyncTasksStatusRepository asyncTasksStatusRepository;

	public void saveAsyncTaskStatusLog(AsyncTasksStatusModel asyncTaskLog) {
		asyncTasksStatusRepository.save(asyncTaskLog);
	}

	public void saveAsyncTaskStatusLog(int fileId, String taskType, String currentStatusDesc, String completionStatus,
			int completionPercent, int userId) {

		AsyncTasksStatusModel model = new AsyncTasksStatusModel();
		model.setFileId(fileId);
		model.setTaskType(taskType);
		model.setCurrentStatusDesc(currentStatusDesc);
		model.setCompletionStatus(completionStatus);
		model.setCompletionPercent(completionPercent);
		model.setUserId(userId);
		Timestamp insertedDate = Timestamp.valueOf(LocalDateTime.now());
		model.setInsertedDate(insertedDate);
		AsyncTasksStatusModel existingLog = getAsyncTaskStatus(fileId, taskType);

		if (existingLog != null) {
			existingLog.setCurrentStatusDesc(currentStatusDesc);
			existingLog.setCompletionStatus(completionStatus);
			existingLog.setCompletionPercent(completionPercent);
			asyncTasksStatusRepository.save(existingLog);
		} else {
			asyncTasksStatusRepository.save(model);
		}
	}

	public AsyncTasksStatusModel getAsyncTaskStatus(int fileId, String taskType) {

		return asyncTasksStatusRepository.getByFileIdAndTaskType(fileId, taskType);

	}

}
