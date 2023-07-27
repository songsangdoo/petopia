package com.petopia.group.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupBoardCommentTO {

	//group1_board_comment 테이블에 gr_seq 추가하기
	
	private int GR_SEQ;
	
	private int GRB_SEQ;
	private int GR_CO_SEQ;
	
	private String GR_CO_CONTENT;
	private String GR_CO_DATE;
	private int M_SEQ;
	private int GR_CO_REC_CNT;
	private int GR_CO_NO_REC_CNT;
	
	private String m_nickname;
	
	// 추가 데이터
	
}
