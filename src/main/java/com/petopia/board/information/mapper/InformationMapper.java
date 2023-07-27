package com.petopia.board.information.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.information.model.InformationTO;

@Mapper
public interface InformationMapper {
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq order by info_seq desc")
	public List<InformationTO> latestList();
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and info_subject like #{info_subject} order by info_seq desc")
	public List<InformationTO> subjectLatestList(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and  m_nickname = #{m_nickname} order by info_seq desc")
	public List<InformationTO> writerLatestList(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and info_content like #{info_content} order by info_seq desc")
	public List<InformationTO> contentLatestList(InformationTO to);

	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq order by info_rec_cnt desc, info_seq desc")
	public List<InformationTO> recList();
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and info_subject like #{info_subject} order by info_rec_cnt desc, info_seq desc")
	public List<InformationTO> subjectRecList(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by info_rec_cnt desc, info_seq desc")
	public List<InformationTO> writerRecList(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and info_content like #{info_content} order by info_rec_cnt desc, info_seq desc")
	public List<InformationTO> contentRecList(InformationTO to);

	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq order by info_cmt_cnt desc, info_seq desc")
	public List<InformationTO> cmtList();
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and info_subject like #{info_subject} order by info_cmt_cnt desc, info_seq desc")
	public List<InformationTO> subjectCmtList(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by info_cmt_cnt desc, info_seq desc")
	public List<InformationTO> writerCmtList(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and info_content like #{info_content} order by info_cmt_cnt desc, info_seq desc")
	public List<InformationTO> contentCmtList(InformationTO to);

	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq order by info_hit desc, info_seq desc")
	public List<InformationTO> hitList();
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and info_subject like #{info_subject} order by info_hit desc, info_seq desc")
	public List<InformationTO> subjectHitList(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and m_nickname = #{m_nickname} order by info_hit desc, info_seq desc")
	public List<InformationTO> writerHitList(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m where a.m_seq = m.m_seq and info_content like #{info_content} order by info_hit desc, info_seq desc")
	public List<InformationTO> contentHitList(InformationTO to);
	
	@Insert("INSERT INTO INFORMATION(INFO_SEQ, INFO_SUBJECT, INFO_CONTENT, INFO_DT, INFO_REP_IMG_PATH, M_SEQ, INFO_HIT, INFO_CMT_CNT, INFO_REC_CNT) VALUES (DEFAULT, #{info_subject}, #{info_content}, DEFAULT, #{info_rep_img_path}, #{m_seq}, DEFAULT, DEFAULT, DEFAULT)")
	public int write(InformationTO to);
		
	@Select("select info_seq, info_subject, info_content, info_dt, info_rep_img_path, info_hit, info_cmt_cnt, info_rec_cnt, a.m_seq, m_nickname from information a inner join member m where info_seq = #{info_seq} and a.m_seq = m.m_seq")
	public InformationTO view(InformationTO to);
	
	@Select("select info_rep_img_path from information where info_seq = #{info_seq}")
	public InformationTO delete(InformationTO to);
	
	@Delete("delete from information where info_seq = #{info_seq}")
	public int deleteOk(InformationTO to);
	
	@Update("update information set info_hit = info_hit + 1 where info_seq= #{info_seq}")
	public int upHit(InformationTO to);
	
	@Update("update information set info_rec_cnt = info_rec_cnt + 1 where info_seq = #{info_seq}")
	public int upRec(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content from information where info_seq = #{info_seq}")
	public InformationTO modify(InformationTO to);
	
	@Update("update information set info_subject = #{info_subject}, info_content = #{info_content}, info_rep_img_path = #{info_rep_img_path} where info_seq = #{info_seq}")
	public int modifyOk(InformationTO to);
	
	@Update("update information set info_subject = #{info_subject}, info_content = #{info_content} where info_seq = #{info_seq}")
	public int modifyOkNoRep(InformationTO to);
	
	@Select("select info_seq, info_subject, info_content, info_rep_img_path, info_dt, m.m_seq, info_hit, info_cmt_cnt, info_rec_cnt, m_nickname from information a inner join member m on a.m_seq = m.m_seq where DATE_FORMAT(now(), '%Y%m%d%H%i%s') - info_dt < 14000000 order by info_hit desc, info_seq desc limit 6")
	public List<InformationTO> mainList();
	
}
