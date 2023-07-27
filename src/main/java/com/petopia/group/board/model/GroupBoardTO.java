package com.petopia.group.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupBoardTO {

	private int GRB_SEQ;
	private int GR_SEQ;
	
	private String GR_SUBJECT;
	private String GR_CONTENT;
	private String GR_DATE;
	private int M_SEQ;
	private int GR_HIT;
	private int GR_COMMENT;
	private int GR_GOOD;
	
	private String M_NICKNAME;
	
	
}
