package com.petopia.board.missing.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingCommentTO {

	private int ms_seq;
	private int ms_cmt_seq;
	
	private String ms_cmt_content;
	private String ms_cmt_dt;
	private int m_seq;
	private int ms_cmt_rec_cnt;
	private int ms_cmt_no_rec_cnt;
	
	// 추가 데이터
	private String m_nickname;
}
