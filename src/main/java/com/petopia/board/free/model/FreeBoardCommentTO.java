package com.petopia.board.free.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeBoardCommentTO {

	private int fb_seq;
	private int fb_cmt_seq;
	
	private String fb_cmt_content;
	private String fb_cmt_dt;
	private int m_seq;
	private int fb_cmt_rec_cnt;
	private int fb_cmt_no_rec_cnt;
	
	// 추가 데이터
	private String m_nickname;
}
