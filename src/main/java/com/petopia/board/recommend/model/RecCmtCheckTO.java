package com.petopia.board.recommend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecCmtCheckTO {

	private int board_no;
	private int board_seq;
	private int cmt_seq;
	private int m_seq;
	private String cmt_rec_chck;
	private String cmt_no_rec_chck;
}
