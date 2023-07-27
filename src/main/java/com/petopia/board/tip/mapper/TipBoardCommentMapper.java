package com.petopia.board.tip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.tip.model.TipBoardCommentTO;

@Mapper
public interface TipBoardCommentMapper {

	@Select("select tb_cmt_seq, tb_cmt_content, tb_cmt_dt, m.m_seq, tb_cmt_rec_cnt, tb_cmt_no_rec_cnt, m_nickname from tipboard_comment ac inner join member m where tb_seq = #{tb_seq} and ac.m_seq = m.m_seq order by tb_cmt_seq desc")
	public List<TipBoardCommentTO> latestList(TipBoardCommentTO to);
	
	@Select("select tb_cmt_seq, tb_cmt_content, tb_cmt_dt, m.m_seq, tb_cmt_rec_cnt, tb_cmt_no_rec_cnt, m_nickname from tipboard_comment ac inner join member m where tb_seq = #{tb_seq} and ac.m_seq = m.m_seq order by tb_cmt_rec_cnt desc, tb_cmt_seq desc")
	public List<TipBoardCommentTO> recList(TipBoardCommentTO co);
	
	@Insert("INSERT INTO TIPBOARD_COMMENT(TB_SEQ, TB_CMT_SEQ, TB_CMT_CONTENT, TB_CMT_DT, M_SEQ, TB_CMT_REC_CNT, TB_CMT_NO_REC_CNT) SELECT #{tb_seq}, (SELECT IFNULL(MAX(TB_CMT_SEQ) + 1, 0) FROM TIPBOARD_COMMENT	WHERE TB_SEQ = #{tb_seq}), #{tb_cmt_content}, DATE_FORMAT(now(), '%Y%m%d%H%i%s') TB_CMT_DT, #{m_seq}, 0 TB_CMT_REC_CNT, 0 TB_CMT_NO_REC_CNT")
	public int wrightOk(TipBoardCommentTO to);
	
	@Update("update tipboard_comment set tb_cmt_rec_cnt = tb_cmt_rec_cnt + 1 where tb_seq = #{tb_seq} and tb_cmt_seq = #{tb_cmt_seq}")
	public int upRec(TipBoardCommentTO to);
	
	@Update("update tipboard_comment set tb_cmt_no_rec_cnt = tb_cmt_no_rec_cnt + 1 where tb_seq = #{tb_seq} and tb_cmt_seq = #{tb_cmt_seq}")
	public int upNoRec(TipBoardCommentTO to);
	
	@Delete("delete from tipboard_comment where tb_seq = #{tb_seq} and tb_cmt_seq = #{tb_cmt_seq} and m_seq = #{m_seq}")
	public int deleteOk(TipBoardCommentTO to);
	
	@Delete("delete from tipboard_comment where tb_seq = #{tb_seq} and tb_cmt_seq = #{tb_cmt_seq}")
	public int adminDeleteOk(TipBoardCommentTO to);
	
	@Delete("delete from tipboard_comment where tb_seq = #{tb_seq}")
	public int deleteAllOk(TipBoardCommentTO to);
}