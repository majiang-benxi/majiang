package com.mahjong.server.netty.model;

import java.util.List;

import com.mahjong.server.vo.UserLatestPlayRecord;

/**
 * 历史战绩
 */
public class HistoryRecordRespModel {
	private List<UserLatestPlayRecord> userLatestPlayRecord;

	public List<UserLatestPlayRecord> getUserLatestPlayRecord() {
		return userLatestPlayRecord;
	}

	public void setUserLatestPlayRecord(List<UserLatestPlayRecord> userLatestPlayRecord) {
		this.userLatestPlayRecord = userLatestPlayRecord;
	}


}
