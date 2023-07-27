package com.petopia.board.album.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.album.model.AlbumCommentTO;

@Mapper
public interface AlbumCommentMapper {
	
	@Select("select ab_cmt_seq, ab_cmt_content, ab_cmt_dt, m.m_seq, ab_cmt_rec_cnt, ab_cmt_no_rec_cnt, m_nickname from album_comment ac inner join member m where ab_seq = #{ab_seq} and ac.m_seq = m.m_seq order by ab_cmt_seq desc")
	public List<AlbumCommentTO> latestList(AlbumCommentTO to);
	
	@Select("select ab_cmt_seq, ab_cmt_content, ab_cmt_dt, m.m_seq, ab_cmt_rec_cnt, ab_cmt_no_rec_cnt, m_nickname from album_comment ac inner join member m where ab_seq = #{ab_seq} and ac.m_seq = m.m_seq order by ab_cmt_rec_cnt desc, ab_cmt_seq desc")
	public List<AlbumCommentTO> recList(AlbumCommentTO co);
	
	@Insert("INSERT INTO ALBUM_COMMENT(AB_SEQ, AB_CMT_SEQ, AB_CMT_CONTENT, AB_CMT_DT, M_SEQ, AB_CMT_REC_CNT, AB_CMT_NO_REC_CNT) SELECT #{ab_seq}, (SELECT IFNULL(MAX(AB_CMT_SEQ) + 1, 0) FROM ALBUM_COMMENT	WHERE AB_SEQ = #{ab_seq}), #{ab_cmt_content}, DATE_FORMAT(now(), '%Y%m%d%H%i%s') AB_CMT_DT, #{m_seq}, 0 AB_CMT_REC_CNT, 0 AB_CMT_NO_REC_CNT")
	public int wrightOk(AlbumCommentTO to);
	
	@Update("update album_comment set ab_cmt_rec_cnt = ab_cmt_rec_cnt + 1 where ab_seq = #{ab_seq} and ab_cmt_seq = #{ab_cmt_seq}")
	public int upRec(AlbumCommentTO to);
	
	@Update("update album_comment set ab_cmt_no_rec_cnt = ab_cmt_no_rec_cnt + 1 where ab_seq = #{ab_seq} and ab_cmt_seq = #{ab_cmt_seq}")
	public int upNoRec(AlbumCommentTO to);
	
	@Delete("delete from album_comment where ab_seq = #{ab_seq} and ab_cmt_seq = #{ab_cmt_seq} and m_seq = #{m_seq}")
	public int deleteOk(AlbumCommentTO to);
	
	@Delete("delete from album_comment where ab_seq = #{ab_seq} and ab_cmt_seq = #{ab_cmt_seq}")
	public int adminDeleteOk(AlbumCommentTO to);
	
	@Delete("delete from album_comment where ab_seq = #{ab_seq}")
	public int deleteAllOk(AlbumCommentTO to);
	
}
