package com.petopia.board.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.notice.model.NoticeTO;

@Mapper
public interface NoticeMapper {
	
	@Select("select n_seq, n_subject, n_dt, n_hit from notice where n_fix = 'Y' order by n_seq desc")
	public List<NoticeTO> fixedList();

	@Select("select n_seq, n_subject, n_dt, n_hit from notice where n_fix = 'N' order by n_seq desc")
	public List<NoticeTO> latestList();
	
	@Select("select n_seq, n_subject, n_dt, n_hit from notice where n_fix = 'N' and n_subject like #{n_subject} order by n_seq desc")
	public List<NoticeTO> subjectLatestList(NoticeTO to);
	
	@Select("select n_seq, n_subject, n_dt, n_hit from notice where n_fix = 'N' and n_content like #{n_content} order by n_seq desc")
	public List<NoticeTO> contentLatestList(NoticeTO to);

	@Select("select n_seq, n_subject, n_dt, n_hit from notice where n_fix = 'N' order by n_hit desc, n_seq desc")
	public List<NoticeTO> hitList();
	
	@Select("select n_seq, n_subject, n_dt, n_hit from notice where n_fix = 'N' and n_subject like #{n_subject} order by n_hit desc, n_seq desc")
	public List<NoticeTO> subjectHitList(NoticeTO to);
	
	@Select("select n_seq, n_subject, n_dt, n_hit from notice where n_fix = 'N' and n_content like #{n_content} order by n_hit desc, n_seq desc")
	public List<NoticeTO> contentHitList(NoticeTO to);
	
	@Insert("INSERT INTO NOTICE(N_SEQ, n_subject, n_content, n_fix, n_dt, m_seq, n_hit) VALUES (DEFAULT, #{n_subject}, #{n_content}, #{n_fix}, default, #{m_seq}, default)")
	public int write(NoticeTO to);
		
	@Select("select a.n_seq, n_subject, n_content, n_fix, n_dt, n_hit, m_seq from notice a where n_seq = #{n_seq}")
	public NoticeTO view(NoticeTO to);
	
	@Delete("delete from notice where n_seq = #{n_seq}")
	public int deleteOk(NoticeTO to);
	
	@Update("update notice set n_hit = n_hit + 1 where n_seq= #{n_seq}")
	public int upHit(NoticeTO to);

	@Select("select n_seq, n_subject, n_content, n_fix from notice where n_seq = #{n_seq}")
	public NoticeTO modify(NoticeTO to);
	
	@Update("update notice set n_subject = #{n_subject}, n_content = #{n_content}, n_fix = #{n_fix}, n_dt = default where n_seq = #{n_seq}")
	public int modifyOk(NoticeTO to);
}
