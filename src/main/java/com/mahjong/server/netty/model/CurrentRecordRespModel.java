package com.mahjong.server.netty.model;

import java.util.List;

import com.mahjong.server.vo.ScoreRecordVO;

/**
 * 本局战绩
 */
public class CurrentRecordRespModel {
	
	private List<ScoreRecordVO> playScordRecords;

	public List<ScoreRecordVO> getPlayScordRecords() {
		return playScordRecords;
	}

	public void setPlayScordRecords(List<ScoreRecordVO> playScordRecords) {
		this.playScordRecords = playScordRecords;
	}

}
