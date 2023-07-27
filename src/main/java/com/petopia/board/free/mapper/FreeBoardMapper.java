package com.petopia.board.free.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.free.model.FreeBoardTO;

@Mapper
public interface FreeBoardMapper {

	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq order by fb_seq desc")
	public List<FreeBoardTO> latestList();
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and fb_subject like #{fb_subject} order by fb_seq desc")
	public List<FreeBoardTO> subjectLatestList(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and  m_nickname = #{m_nickname} order by fb_seq desc")
	public List<FreeBoardTO> writerLatestList(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and fb_content like #{fb_content} order by fb_seq desc")
	public List<FreeBoardTO> contentLatestList(FreeBoardTO to);

	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq order by fb_rec_cnt desc, fb_seq desc")
	public List<FreeBoardTO> recList();
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and fb_subject like #{fb_subject} order by fb_rec_cnt desc, fb_seq desc")
	public List<FreeBoardTO> subjectRecList(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by fb_rec_cnt desc, fb_seq desc")
	public List<FreeBoardTO> writerRecList(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and fb_content like #{fb_content} order by fb_rec_cnt desc, fb_seq desc")
	public List<FreeBoardTO> contentRecList(FreeBoardTO to);

	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq order by fb_cmt_cnt desc, fb_seq desc")
	public List<FreeBoardTO> cmtList();
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and fb_subject like #{fb_subject} order by fb_cmt_cnt desc, fb_seq desc")
	public List<FreeBoardTO> subjectCmtList(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by fb_cmt_cnt desc, fb_seq desc")
	public List<FreeBoardTO> writerCmtList(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and fb_content like #{fb_content} order by fb_cmt_cnt desc, fb_seq desc")
	public List<FreeBoardTO> contentCmtList(FreeBoardTO to);

	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq order by fb_hit desc, fb_seq desc")
	public List<FreeBoardTO> hitList();
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and fb_subject like #{fb_subject} order by fb_hit desc, fb_seq desc")
	public List<FreeBoardTO> subjectHitList(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by fb_hit desc, fb_seq desc")
	public List<FreeBoardTO> writerHitList(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m_nickname from freeboard a inner join member m where a.m_seq = m.m_seq and fb_content like #{fb_content} order by fb_hit desc, fb_seq desc")
	public List<FreeBoardTO> contentHitList(FreeBoardTO to);
	
	@Insert("INSERT INTO FREEBOARD(FB_SEQ, FB_SUBJECT, FB_CONTENT, FB_DT, M_SEQ, FB_HIT, FB_CMT_CNT, FB_REC_CNT) VALUES (DEFAULT, #{fb_subject}, #{fb_content}, DEFAULT, #{m_seq}, DEFAULT, DEFAULT, DEFAULT)")
	public int write(FreeBoardTO to);
		
	@Select("select a.fb_seq, fb_subject, fb_content, fb_dt, fb_hit, fb_cmt_cnt, fb_rec_cnt, m.m_seq, m_nickname from freeboard a inner join member m where fb_seq = #{fb_seq} and a.m_seq = m.m_seq")
	public FreeBoardTO view(FreeBoardTO to);
	
	@Delete("delete from freeboard where fb_seq = #{fb_seq} and m_seq = #{m_seq}")
	public int deleteOk(FreeBoardTO to);
	
	@Delete("delete from freeboard where fb_seq = #{fb_seq}")
	public int adminDeleteOk(FreeBoardTO to);
	
	@Update("update freeboard set fb_hit = fb_hit + 1 where fb_seq= #{fb_seq}")
	public int upHit(FreeBoardTO to);
	
	@Update("update freeboard set fb_cmt_cnt = fb_cmt_cnt + 1 where fb_seq = #{fb_seq}")
	public int upCmt(FreeBoardTO to);
	
	@Update("update freeboard set fb_cmt_cnt = fb_cmt_cnt - 1 where fb_seq = #{fb_seq}")
	public int downCmt(FreeBoardTO to);
	
	@Update("update freeboard set fb_rec_cnt = fb_rec_cnt + 1 where fb_seq = #{fb_seq}")
	public int upRec(FreeBoardTO to);
	
	@Select("select fb_seq, fb_subject, fb_content, m_seq from freeboard where fb_seq = #{fb_seq}")
	public FreeBoardTO modify(FreeBoardTO to);
	
	@Update("update freeboard set fb_subject = #{fb_subject}, fb_content = #{fb_content} where fb_seq = #{fb_seq}")
	public int modifyOk(FreeBoardTO to);
	
}
