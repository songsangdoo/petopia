package com.petopia.board.qna.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.qna.model.QnABoardTO;

@Mapper
public interface QnABoardMapper {
	
	@Select("select qa_seq, qa_subject, qa_content, qa_secret, qa_password, qa_dt, qa_answer_check, qa_answer_content, qa_answer_dt, a.m_seq, m_nickname, qa_category_name from (qna a inner join member m on a.m_seq = m.m_seq) inner join qna_category c on a.qa_category_seq = c.qa_category_seq order by qa_seq desc")
	public List<QnABoardTO> list();
	
	@Select("select qa_seq, qa_subject, qa_content, qa_secret, qa_password, qa_dt, qa_answer_check, qa_answer_content, qa_answer_dt, a.m_seq, m_nickname, qa_category_name from (qna a inner join member m on a.m_seq = m.m_seq) inner join qna_category c on a.qa_category_seq = c.qa_category_seq where qa_secret = 'N' order by qa_seq desc")
	public List<QnABoardTO> noSecretList();
	
	@Select("select qa_seq, qa_subject, qa_content, qa_secret, qa_password, qa_dt, qa_answer_check, qa_answer_content, qa_answer_dt, a.m_seq, m_nickname, qa_category_name from (qna a inner join member m on a.m_seq = m.m_seq) inner join qna_category c on a.qa_category_seq = c.qa_category_seq where qa_answer_check = 'N' order by qa_seq desc")
	public List<QnABoardTO> noAnswerList();
	
	@Select("select qa_seq, qa_subject, qa_content, qa_secret, qa_password, qa_dt, qa_answer_check, qa_answer_content, qa_answer_dt, a.m_seq, m_nickname, qa_category_name from (qna a inner join member m on a.m_seq = m.m_seq) inner join qna_category c on a.qa_category_seq = c.qa_category_seq where a.m_seq = #{m_seq} order by qa_seq desc")
	public List<QnABoardTO> myList(QnABoardTO to);
	
	@Insert("insert into qna(qa_seq, qa_category_seq, qa_subject, qa_content, qa_secret, qa_password, qa_dt, qa_answer_check, qa_answer_content, qa_answer_dt, m_seq) values(default, #{qa_category_seq}, #{qa_subject}, #{qa_content}, #{qa_secret}, #{qa_password}, default, default, default, default, #{m_seq})")
	public int writeOk(QnABoardTO to);
	
	@Delete("delete from qna where qa_seq = #{qa_seq} m_seq = #{m_seq}")
	public int deleteOk(QnABoardTO to);
	
	@Delete("delete from qna where qa_seq = #{qa_seq}")
	public int adminDeleteOk(QnABoardTO to);
	
	@Select("select qa_seq, qa_subject, qa_content, qa_secret, qa_category_name from qna a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where qa_seq = #{qa_seq}")
	public QnABoardTO modify(QnABoardTO to);
	
	@Update("update qna set qa_category_seq = #{qa_category_seq}, qa_subject = #{qa_subject}, qa_content = #{qa_content}, qa_secret = #{qa_secret} where qa_seq = #{qa_seq}")
	public int modifyOk(QnABoardTO to);
	
	@Select("select qa_seq, qa_subject, qa_content, qa_category_name from qna a inner join qna_category c on a.qa_category_seq = c.qa_category_seq where qa_seq = #{qa_seq}")
	public QnABoardTO answer(QnABoardTO to);
	
	@Update("update qna set qa_answer_check = #{qa_answer_check}, qa_answer_content = #{qa_answer_content}, qa_answer_dt = default where qa_seq = #{qa_seq}")
	public int answerOk(QnABoardTO to);
}
