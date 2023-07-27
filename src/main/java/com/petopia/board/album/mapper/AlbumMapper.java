package com.petopia.board.album.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.album.model.AlbumTO;

@Mapper
public interface AlbumMapper {
	
	@Select("select ab_seq, ab_subject, ab_content, ab_dt, ab_rep_img_path, m.m_seq, ab_hit, ab_cmt_cnt, ab_rec_cnt, m_nickname from album a inner join member m where a.m_seq = m.m_seq order by ab_rec_cnt desc ,ab_seq desc")
	public List<AlbumTO> recList();
	
	@Select("select ab_seq, ab_subject, ab_content, ab_dt, ab_rep_img_path, m.m_seq, ab_hit, ab_cmt_cnt, ab_rec_cnt, m_nickname from album a inner join member m where a.m_seq = m.m_seq order by ab_cmt_cnt desc, ab_seq desc")
	public List<AlbumTO> cmtList();
	
	@Select("select ab_seq, ab_subject, ab_content, ab_dt, ab_rep_img_path, m.m_seq, ab_hit, ab_cmt_cnt, ab_rec_cnt, m_nickname from album a inner join member m where a.m_seq = m.m_seq order by ab_hit desc, ab_seq desc")
	public List<AlbumTO> hitList();
	
	@Select("select ab_seq, ab_subject, ab_content, ab_dt, ab_rep_img_path, m.m_seq, ab_hit, ab_cmt_cnt, ab_rec_cnt, m_nickname from album a inner join member m where a.m_seq = m.m_seq order by ab_seq desc")
	public List<AlbumTO> list();
	
	@Insert("INSERT INTO ALBUM(AB_SEQ, AB_SUBJECT, AB_CONTENT, AB_DT, AB_REP_IMG_PATH, M_SEQ, AB_HIT, AB_CMT_CNT, AB_REC_CNT) VALUES (DEFAULT, #{ab_subject}, #{ab_content}, DEFAULT, #{ab_rep_img_path}, #{m_seq}, DEFAULT, DEFAULT, DEFAULT)")
	public int write(AlbumTO to);
		
	@Select("select a.ab_seq, ab_subject, ab_content, ab_dt, ab_rep_img_path, ab_hit, ab_cmt_cnt, ab_rec_cnt, m.m_seq, m_nickname from album a inner join member m where ab_seq = #{ab_seq} and a.m_seq = m.m_seq")
	public AlbumTO view(AlbumTO to);
	
	@Select("select ab_rep_img_path from album where ab_seq = #{ab_seq}")
	public AlbumTO delete(AlbumTO to);
	
	@Delete("delete from album where ab_seq = #{ab_seq} and m_seq = #{m_seq}")
	public int deleteOk(AlbumTO to);
	
	@Delete("delete from album where ab_seq = #{ab_seq}")
	public int adminDeleteOk(AlbumTO to);
	
	@Update("update album set ab_hit = ab_hit + 1 where ab_seq= #{ab_seq}")
	public int upHit(AlbumTO to);
	
	@Update("update album set ab_cmt_cnt = ab_cmt_cnt + 1 where ab_seq = #{ab_seq}")
	public int upCmt(AlbumTO to);
	
	@Update("update album set ab_cmt_cnt = ab_cmt_cnt - 1 where ab_seq = #{ab_seq}")
	public int downCmt(AlbumTO to);
	
	@Update("update album set ab_rec_cnt = ab_rec_cnt + 1 where ab_seq = #{ab_seq}")
	public int upRec(AlbumTO to);
	
	@Select("select ab_seq, ab_subject, ab_content, m_seq from album where ab_seq = #{ab_seq}")
	public AlbumTO modify(AlbumTO to);
	
	@Update("update album set ab_subject = #{ab_subject}, ab_content = #{ab_content}, ab_rep_img_path = #{ab_rep_img_path} where ab_seq = #{ab_seq}")
	public int modifyOk(AlbumTO to);
	
	@Update("update album set ab_subject = #{ab_subject}, ab_content = #{ab_content} where ab_seq = #{ab_seq}")
	public int modifyOkNoRep(AlbumTO to);
	
	@Select("select ab_seq, ab_subject, ab_content, ab_dt, ab_rep_img_path, m.m_seq, ab_hit, ab_cmt_cnt, ab_rec_cnt, m_nickname from album a inner join member m on a.m_seq = m.m_seq where DATE_FORMAT(now(), '%Y%m%d%H%i%s') - ab_dt < 14000000 order by ab_hit desc, ab_seq desc limit 9")
	public List<AlbumTO> mainList();
}
