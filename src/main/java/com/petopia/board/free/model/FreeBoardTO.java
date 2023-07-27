package com.petopia.board.free.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeBoardTO {

	private int fb_seq;
	
	private String fb_subject;
	private String fb_content;
	private String fb_dt;
	private int m_seq;
	private int fb_hit;
	private int fb_cmt_cnt;
	private int fb_rec_cnt;
	
	// 추가 데이터
	private String m_nickname;
}
