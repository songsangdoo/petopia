package com.petopia.board.recommend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecCheckTO {

	private int board_no;
	private int board_seq;
	private int m_seq;
	
	// 글쓰기 3회 까지 포인트 제공
	private int w_count;
}
