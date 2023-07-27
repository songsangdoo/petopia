package com.petopia.board.free.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.free.model.FreeBoardCommentTO;

@Mapper
public interface FreeBoardCommentMapper {

	@Select("select fb_cmt_seq, fb_cmt_content, fb_cmt_dt, m.m_seq, fb_cmt_rec_cnt, fb_cmt_no_rec_cnt, m_nickname from freeboard_comment ac inner join member m where fb_seq = #{fb_seq} and ac.m_seq = m.m_seq order by fb_cmt_seq desc")
	public List<FreeBoardCommentTO> latestList(FreeBoardCommentTO to);
	
	@Select("select fb_cmt_seq, fb_cmt_content, fb_cmt_dt, m.m_seq, fb_cmt_rec_cnt, fb_cmt_no_rec_cnt, m_nickname from freeboard_comment ac inner join member m where fb_seq = #{fb_seq} and ac.m_seq = m.m_seq order by fb_cmt_rec_cnt desc, fb_cmt_seq desc")
	public List<FreeBoardCommentTO> recList(FreeBoardCommentTO co);
	
	@Insert("INSERT INTO FREEBOARD_COMMENT(FB_SEQ, FB_CMT_SEQ, FB_CMT_CONTENT, FB_CMT_DT, M_SEQ, FB_CMT_REC_CNT, FB_CMT_NO_REC_CNT) SELECT #{fb_seq}, (SELECT IFNULL(MAX(FB_CMT_SEQ) + 1, 0) FROM FREEBOARD_COMMENT	WHERE FB_SEQ = #{fb_seq}), #{fb_cmt_content}, DATE_FORMAT(now(), '%Y%m%d%H%i%s') FB_CMT_DT, #{m_seq}, 0 FB_CMT_REC_CNT, 0 FB_CMT_NO_REC_CNT")
	public int wrightOk(FreeBoardCommentTO to);
	
	@Update("update freeboard_comment set fb_cmt_rec_cnt = fb_cmt_rec_cnt + 1 where fb_seq = #{fb_seq} and fb_cmt_seq = #{fb_cmt_seq}")
	public int upRec(FreeBoardCommentTO to);
	
	@Update("update freeboard_comment set fb_cmt_no_rec_cnt = fb_cmt_no_rec_cnt + 1 where fb_seq = #{fb_seq} and fb_cmt_seq = #{fb_cmt_seq}")
	public int upNoRec(FreeBoardCommentTO to);
	
	@Delete("delete from freeboard_comment where fb_seq = #{fb_seq} and fb_cmt_seq = #{fb_cmt_seq} and m_seq = #{m_seq}")
	public int deleteOk(FreeBoardCommentTO to);
	
	@Delete("delete from freeboard_comment where fb_seq = #{fb_seq} and fb_cmt_seq = #{fb_cmt_seq}")
	public int adminDeleteOk(FreeBoardCommentTO to);
	
	@Delete("delete from freeboard_comment where fb_seq = #{fb_seq}")
	public int deleteAllOk(FreeBoardCommentTO to);
}
