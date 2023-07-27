package com.petopia.board.recommend.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.recommend.model.RecCheckTO;

@Mapper
public interface RecCheckMapper {
	
	@Select("select * from rec_chck where board_no = #{board_no} and board_seq = #{board_seq} and m_seq = #{m_seq}")
	public RecCheckTO check(RecCheckTO to);
	
	@Insert("insert into rec_chck(board_no, board_seq, m_seq, w_dt) values(#{board_no}, #{board_seq}, #{m_seq}, default)")
	public int insert(RecCheckTO to);
	
	@Delete("delete from rec_chck where board_no = #{board_no} and board_seq = #{board_seq}")
	public int delete(RecCheckTO to);
	
	@Delete("delete from rec_chck where board_no = #{board_no} and board_seq = #{board_seq} and m_seq = #{m_seq}")
	public int deleteAns(RecCheckTO to);
	
	// 글쓰기 3회까지 포인트 제공
	@Select("select count(w_dt) w_count from rec_chck rc where w_dt = DATE_FORMAT(now(), '%Y%m%d') and m_seq = #{m_seq};")
	public RecCheckTO wCount(RecCheckTO to);
}
