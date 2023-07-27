package com.petopia.board.missing.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingTO {

	private int ms_seq;
	
	private String ms_pet_name;
	private int ms_pet_age;
	private String ms_pet_gender;
	private String ms_subject;
	private String ms_content;
	private String ms_missing_date;
	private String ms_missing_loc;
	private String ms_rep_img_path;
	private String ms_open_phone;
	private String ms_dt;
	private int m_seq;
	private int ms_hit;
	private int ms_cmt_cnt;

	// 추가 데이터
	private String m_nickname;
	private String m_phone;
	private int ms_date_gap;
}
