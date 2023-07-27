package com.petopia.board.recommend.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.recommend.model.RecCmtCheckTO;

@Mapper
public interface RecCmtCheckMapper {

	@Select("select * from rec_cmt_chck where board_no = #{board_no} and board_seq = #{board_seq} and cmt_seq = #{cmt_seq} and m_seq = #{m_seq}")
	public RecCmtCheckTO check(RecCmtCheckTO to);
	
	@Insert("insert into rec_cmt_chck(board_no, board_seq, cmt_seq, m_seq) values(#{board_no}, #{board_seq}, #{cmt_seq}, #{m_seq})")
	public int insert(RecCmtCheckTO to);
	
	@Delete("delete from rec_cmt_chck where board_no = #{board_no} and board_seq = #{board_seq}")
	public int post_deleteAll(RecCmtCheckTO to);

	@Delete("delete from rec_cmt_chck where board_no = #{board_no} and board_seq = #{board_seq} and cmt_seq = #{cmt_seq}")
	public int deleteAll(RecCmtCheckTO to);

}
