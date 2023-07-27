package com.petopia.board.question.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionTO {

	private int q_seq;
	
	private String q_subject;
	private String q_content;
	private String q_dt;
	private int m_seq;
	private int q_hit;
	private int q_ans_cnt;
	private String q_select_check;
	
	private String m_nickname;
}
