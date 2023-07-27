package com.petopia.board.question.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerFileTO {

	private int q_seq;
	private int q_ans_seq;
	private int q_ans_file_seq;
	
	private String q_ans_file_img_path;
}
