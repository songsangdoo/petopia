package com.petopia.board.album.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumTO {
	
	private int ab_seq;
	
	private String ab_subject;
	private String ab_content;
	private String ab_dt;
	private String ab_rep_img_path;
	private int m_seq;
	private int ab_hit;
	private int ab_file_cnt;
	private int ab_cmt_cnt;
	private int ab_rec_cnt;
	
	// 추가 데이터
	private String m_nickname;
	private int ab_date_gap;
}
