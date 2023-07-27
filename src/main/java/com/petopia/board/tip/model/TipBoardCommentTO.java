package com.petopia.board.tip.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipBoardCommentTO {

	private int tb_seq;
	private int tb_cmt_seq;
	
	private String tb_cmt_content;
	private String tb_cmt_dt;
	private int m_seq;
	private int tb_cmt_rec_cnt;
	private int tb_cmt_no_rec_cnt;
	
	// 추가 데이터
	private String m_nickname;
}