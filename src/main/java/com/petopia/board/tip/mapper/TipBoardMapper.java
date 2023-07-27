package com.petopia.board.tip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.tip.model.TipBoardTO;

@Mapper
public interface TipBoardMapper {

	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq order by tb_seq desc")
	public List<TipBoardTO> latestList();
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and tb_subject like #{tb_subject} order by tb_seq desc")
	public List<TipBoardTO> subjectLatestList(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and  m_nickname = #{m_nickname} order by tb_seq desc")
	public List<TipBoardTO> writerLatestList(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and tb_content like #{tb_content} order by tb_seq desc")
	public List<TipBoardTO> contentLatestList(TipBoardTO to);

	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq order by tb_rec_cnt desc, tb_seq desc")
	public List<TipBoardTO> recList();
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and tb_subject like #{tb_subject} order by tb_rec_cnt desc, tb_seq desc")
	public List<TipBoardTO> subjectRecList(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by tb_rec_cnt desc, tb_seq desc")
	public List<TipBoardTO> writerRecList(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and tb_content like #{tb_content} order by tb_rec_cnt desc, tb_seq desc")
	public List<TipBoardTO> contentRecList(TipBoardTO to);

	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq order by tb_cmt_cnt desc, tb_seq desc")
	public List<TipBoardTO> cmtList();
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and tb_subject like #{tb_subject} order by tb_cmt_cnt desc, tb_seq desc")
	public List<TipBoardTO> subjectCmtList(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by tb_cmt_cnt desc, tb_seq desc")
	public List<TipBoardTO> writerCmtList(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and tb_content like #{tb_content} order by tb_cmt_cnt desc, tb_seq desc")
	public List<TipBoardTO> contentCmtList(TipBoardTO to);

	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq order by tb_hit desc, tb_seq desc")
	public List<TipBoardTO> hitList();
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and tb_subject like #{tb_subject} order by tb_hit desc, tb_seq desc")
	public List<TipBoardTO> subjectHitList(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by tb_hit desc, tb_seq desc")
	public List<TipBoardTO> writerHitList(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m_nickname from tipboard a inner join member m where a.m_seq = m.m_seq and tb_content like #{tb_content} order by tb_hit desc, tb_seq desc")
	public List<TipBoardTO> contentHitList(TipBoardTO to);
	
	@Insert("INSERT INTO TIPBOARD(TB_SEQ, TB_SUBJECT, TB_CONTENT, TB_DT, M_SEQ, TB_HIT, TB_CMT_CNT, TB_REC_CNT) VALUES (DEFAULT, #{tb_subject}, #{tb_content}, DEFAULT, #{m_seq}, DEFAULT, DEFAULT, DEFAULT)")
	public int write(TipBoardTO to);
		
	@Select("select a.tb_seq, tb_subject, tb_content, tb_dt, tb_hit, tb_cmt_cnt, tb_rec_cnt, m.m_seq, m_nickname from tipboard a inner join member m where tb_seq = #{tb_seq} and a.m_seq = m.m_seq")
	public TipBoardTO view(TipBoardTO to);
	
	@Delete("delete from tipboard where tb_seq = #{tb_seq} and m_seq = #{m_seq}")
	public int deleteOk(TipBoardTO to);
	
	@Delete("delete from tipboard where tb_seq = #{tb_seq}")
	public int adminDeleteOk(TipBoardTO to);
	
	@Update("update tipboard set tb_hit = tb_hit + 1 where tb_seq= #{tb_seq}")
	public int upHit(TipBoardTO to);
	
	@Update("update tipboard set tb_cmt_cnt = tb_cmt_cnt + 1 where tb_seq = #{tb_seq}")
	public int upCmt(TipBoardTO to);
	
	@Update("update tipboard set tb_cmt_cnt = tb_cmt_cnt - 1 where tb_seq = #{tb_seq}")
	public int downCmt(TipBoardTO to);
	
	@Update("update tipboard set tb_rec_cnt = tb_rec_cnt + 1 where tb_seq = #{tb_seq}")
	public int upRec(TipBoardTO to);
	
	@Select("select tb_seq, tb_subject, tb_content, m_seq from tipboard where tb_seq = #{tb_seq}")
	public TipBoardTO modify(TipBoardTO to);
	
	@Update("update tipboard set tb_subject = #{tb_subject}, tb_content = #{tb_content} where tb_seq = #{tb_seq}")
	public int modifyOk(TipBoardTO to);
	
}