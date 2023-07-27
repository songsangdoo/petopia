package com.petopia.board.qna.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnABoardCommentTO {

	private int qu_co_seq;
	private int qu_seq;
	private int m_seq;
	private String qu_co_content;
	private String qu_co_date;
}
