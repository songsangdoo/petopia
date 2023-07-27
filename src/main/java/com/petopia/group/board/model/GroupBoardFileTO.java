package com.petopia.group.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupBoardFileTO {
	
	
	//group1_board_file테이블에 gr_seq 추가하기
	private int GR_SEQ;
	private int GRB_SEQ;
	private int GRB_FILE_SEQ;
	
	private String GRB_FILE_IMG_PATH;
	
}
