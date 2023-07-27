package com.petopia.board.notice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoticeTO {
	
	private int n_seq;
	
	private String n_subject;
	private String n_content;
	private String n_fix;
	private String n_dt;
	private int m_seq;
	private int n_hit;
}
