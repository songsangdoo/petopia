package com.petopia.board.freq.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnABoardFreqTO {

	private int qa_freq_seq;
	
	private int qa_category_seq;
	private String qa_freq_subject;
	private String qa_freq_content;
	private int m_seq;
	
	private String qa_category_name;
}
