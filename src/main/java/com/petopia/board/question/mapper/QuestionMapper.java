package com.petopia.board.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.question.model.QuestionTO;

@Mapper
public interface QuestionMapper {

	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq order by q_seq desc")
	public List<QuestionTO> latestList();
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where q_subject like #{q_subject} order by q_seq desc")
	public List<QuestionTO> subjectLatestList(QuestionTO to);

	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where q_content like #{q_content} order by q_seq desc")
	public List<QuestionTO> contentLatestList(QuestionTO to);
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq order by q_ans_cnt desc, q_seq desc")
	public List<QuestionTO> ansList();
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where q_subject like #{q_subject} order by q_ans_cnt desc, q_seq desc")
	public List<QuestionTO> subjectAnsList(QuestionTO to);

	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where q_content like #{q_content} order by q_ans_cnt desc, q_seq desc")
	public List<QuestionTO> contentAnsList(QuestionTO to);
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq order by q_hit desc, q_seq desc")
	public List<QuestionTO> hitList();
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where q_subject like #{q_subject} order by q_hit desc, q_seq desc")
	public List<QuestionTO> subjectHitList(QuestionTO to);
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where q_content like #{q_content} order by q_hit desc, q_seq desc")
	public List<QuestionTO> contentHitList(QuestionTO to);
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where a.m_seq = #{m_seq} order by q_seq desc")
	public List<QuestionTO> myList(QuestionTO to);
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where a.m_seq = #{m_seq} and q_subject like #{q_subject} order by q_seq desc")
	public List<QuestionTO> subjectMyList(QuestionTO to);

	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, m_nickname from question a inner join member m on a.m_seq = m.m_seq where a.m_seq = #{m_seq} and q_content like #{q_content} order by q_seq desc")
	public List<QuestionTO> contentMyList(QuestionTO to);

	@Insert("insert into question(q_seq, q_subject, q_content, q_dt, m_seq, q_hit, q_ans_cnt, q_select_check) values(default, #{q_subject}, #{q_content}, default, #{m_seq}, default, default, default)")
	public int writeOk(QuestionTO to);
	
	@Select("select q_seq, q_subject, q_content, q_dt, q_hit, q_ans_cnt, q_select_check, a.m_seq, m_nickname from question a inner join member m on a.m_seq = m.m_seq where q_seq = #{q_seq}")
	public QuestionTO view(QuestionTO to);
	
	@Update("update question set q_hit = q_hit + 1 where q_seq = #{q_seq}")
	public int upHit(QuestionTO to);
	
	@Update("update question set q_ans_cnt = q_ans_cnt + 1 where q_seq = #{q_seq}")
	public int upAns(QuestionTO to);
	
	@Update("update question set q_ans_cnt = q_ans_cnt - 1 where q_seq = #{q_seq}")
	public int downAns(QuestionTO to);
	
	@Update("update question set q_select_check = 'Y' where q_seq = #{q_seq} and m_seq = #{m_seq}")
	public int selectAns(QuestionTO to);
	
	@Delete("delete from question where q_seq = #{q_seq} and m_seq = #{m_seq}")
	public int deleteOk(QuestionTO to);
	
	@Delete("delete from question where q_seq = #{q_seq}")
	public int adminDeleteOk(QuestionTO to);
	
	@Select("select q_seq, q_subject, q_content, m_seq from question where q_seq = #{q_seq}")
	public QuestionTO modify(QuestionTO to);
	
	@Update("update question set q_subject = #{q_subject}, q_content = #{q_content}, q_dt = default where q_seq = #{q_seq}")
	public int modifyOk(QuestionTO to);
}
