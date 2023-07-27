package com.petopia.board.album.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumCommentTO {
	
	private int ab_seq;
	private int ab_cmt_seq;
	
	private String ab_cmt_content;
	private String ab_cmt_dt;
	private int m_seq;
	private int ab_cmt_rec_cnt;
	private int ab_cmt_no_rec_cnt;
	
	// 추가 데이터
	private String m_nickname;
}
