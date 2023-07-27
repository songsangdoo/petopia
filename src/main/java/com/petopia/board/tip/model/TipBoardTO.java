package com.petopia.board.tip.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipBoardTO {

	private int tb_seq;
	
	private String tb_subject;
	private String tb_content;
	private String tb_dt;
	private int m_seq;
	private int tb_hit;
	private int tb_cmt_cnt;
	private int tb_rec_cnt;
	
	// 추가 데이터
	private String m_nickname;
}
