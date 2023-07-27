package com.petopia.board.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.question.model.QuestionAnswerTO;

@Mapper
public interface QuestionAnswerMapper {

	@Select("select q_ans_seq, q_ans_content, q_ans_dt, a.m_seq, q_ans_selected, m_nickname from question_answer a inner join member m on a.m_seq = m.m_seq where q_seq = #{q_seq} and q_ans_selected = 'N' order by q_ans_seq desc")
	public List<QuestionAnswerTO> view(QuestionAnswerTO to);
	
	@Update("update question_answer set q_ans_selected = 'Y' where q_seq = #{q_seq} and q_ans_seq = #{q_ans_seq}")
	public int selectAns(QuestionAnswerTO to);
	
	@Select("select q_ans_seq, q_ans_content, q_ans_dt, a.m_seq, q_ans_selected, m_nickname from question_answer a inner join member m on a.m_seq = m.m_seq where q_seq = #{q_seq} and q_ans_selected = 'Y'")
	public QuestionAnswerTO viewSelected(QuestionAnswerTO to);
	
	@Insert("insert into question_answer(q_seq, q_ans_seq, q_ans_content, q_ans_dt, m_seq, q_ans_selected) select #{q_seq}, (select ifnull(max(q_ans_seq) + 1, 0) from question_answer where q_seq = #{q_seq}), #{q_ans_content}, DATE_FORMAT(now(), '%Y%m%d%H%i%s'), #{m_seq}, 'N'")
	public int writeOk(QuestionAnswerTO to);
	
	@Delete("delete from question_answer where q_seq = #{q_seq} and q_ans_seq = #{q_ans_seq} and m_seq = #{m_seq}")
	public int deleteOk(QuestionAnswerTO to);
	
	@Delete("delete from question_answer where q_seq = #{q_seq} and q_ans_seq = #{q_ans_seq}")
	public int adminDeleteOk(QuestionAnswerTO to);
	
	@Delete("delete from question_answer where q_seq = #{q_seq}")
	public int deleteAllOk(QuestionAnswerTO to);
	
	@Select("select q_ans_seq, q_ans_content, m_seq from question_answer where q_seq = #{q_seq} and q_ans_seq = #{q_ans_seq}")
	public QuestionAnswerTO modify(QuestionAnswerTO to);
	
	@Update("update question_answer set q_ans_content = #{q_ans_content}, q_ans_dt = default where q_seq = #{q_seq} and q_ans_seq = #{q_ans_seq}")
	public int modifyOk(QuestionAnswerTO to);
}
