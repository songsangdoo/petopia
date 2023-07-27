package com.petopia.board.qna.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QnABoardFileTO {

	private int qa_seq;
	private int qa_file_seq;
	
	private String qa_file_img_path;
}
