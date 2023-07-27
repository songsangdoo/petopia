package com.petopia.board.qna.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnABoardTO {

	private int qa_seq;
	
	private int qa_category_seq;
	private String qa_subject;
	private String qa_content;
	private String qa_secret;
	private String qa_password;
	private String qa_dt;
	private String qa_answer_check;
	private String qa_answer_content;
	private String qa_answer_dt;
	private int m_seq;
	
	private String m_nickname;
	private String qa_category_name;
}
