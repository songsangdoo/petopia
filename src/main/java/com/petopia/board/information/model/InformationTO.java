package com.petopia.board.information.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InformationTO {

	private int info_seq;
	
	private String info_subject;
	private String info_content;
	private String info_rep_img_path;
	private String info_dt;
	private int m_seq;
	private int info_hit;
	private int info_cmt_cnt;
	private int info_rec_cnt;
	
	// 추가 데이터
	private String m_nickname;
	private int info_dgap;
}
