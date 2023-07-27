package com.petopia.board.question.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerTO {

	private int q_seq;
	private int q_ans_seq;
	
	private String q_ans_content;
	private String q_ans_dt;
	private int m_seq;
	private String q_ans_selected;
	
	private String m_nickname;
}
