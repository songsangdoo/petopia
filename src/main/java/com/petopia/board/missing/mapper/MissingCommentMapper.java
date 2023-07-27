package com.petopia.board.missing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.missing.model.MissingCommentTO;

@Mapper
public interface MissingCommentMapper {
	
	@Select("select ms_cmt_seq, ms_cmt_content, ms_cmt_dt, m.m_seq, m_nickname from missing_comment ac inner join member m where ms_seq = #{ms_seq} and ac.m_seq = m.m_seq order by ms_cmt_seq desc")
	public List<MissingCommentTO> latestList(MissingCommentTO to);
	
	@Insert("INSERT INTO MISSING_COMMENT(MS_SEQ, MS_CMT_SEQ, MS_CMT_CONTENT, MS_CMT_DT, M_SEQ) SELECT #{ms_seq}, (SELECT IFNULL(MAX(MS_CMT_SEQ) + 1, 0) FROM MISSING_COMMENT	WHERE MS_SEQ = #{ms_seq}), #{ms_cmt_content}, DATE_FORMAT(now(), '%Y%m%d%H%i%s') MS_CMT_DT, #{m_seq}")
	public int wrightOk(MissingCommentTO to);

	@Delete("delete from missing_comment where ms_seq = #{ms_seq} and ms_cmt_seq = #{ms_cmt_seq} and m_seq = #{m_seq}")
	public int deleteOk(MissingCommentTO to);
	
	@Delete("delete from missing_comment where ms_seq = #{ms_seq} and ms_cmt_seq = #{ms_cmt_seq}")
	public int adminDeleteOk(MissingCommentTO to);
	
	@Delete("delete from missing_comment where ms_seq = #{ms_seq}")
	public int deleteAllOk(MissingCommentTO to);
	
}
