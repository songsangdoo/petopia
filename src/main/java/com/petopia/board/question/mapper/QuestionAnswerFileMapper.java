package com.petopia.board.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.question.model.QuestionAnswerFileTO;
import com.petopia.board.question.model.QuestionFileTO;

@Mapper
public interface QuestionAnswerFileMapper {

	@Select("select q_ans_seq, q_ans_file_img_path from question_answer_file where q_seq = #{q_seq}")
	public List<QuestionAnswerFileTO> list(QuestionAnswerFileTO to);

	@Insert("INSERT INTO QUESTION_ANSWER_FILE(Q_SEQ, Q_ANS_SEQ, Q_ANS_FILE_SEQ, Q_ANS_FILE_IMG_PATH) SELECT #{q_seq}, #{q_ans_seq}, (SELECT IFNULL(MAX(Q_ANS_FILE_SEQ) + 1, 0) FROM QUESTION_ANSWER_FILE WHERE Q_SEQ = #{q_seq} AND Q_ANS_SEQ = #{q_ans_seq}), #{q_ans_file_img_path}")
	public int writeOk(QuestionAnswerFileTO to);
	
	@Select("select q_ans_file_img_path from question_answer_file where q_seq = #{q_seq} and q_ans_seq = #{q_ans_seq}")
	public List<QuestionAnswerFileTO> delete(QuestionAnswerFileTO to);
	
	@Delete("delete from question_answer_file where q_seq = #{q_seq} and q_ans_seq = #{q_ans_seq}")
	public int deleteOk(QuestionAnswerFileTO to);
	
	@Select("select q_ans_file_img_path from question_answer_file where q_seq = #{q_seq}")
	public List<QuestionAnswerFileTO> deleteAll(QuestionAnswerFileTO to);
	
	@Delete("delete from question_answer_file where q_seq = #{q_seq}")
	public int deleteAllOk(QuestionAnswerFileTO to);
	
	@Select("select q_ans_file_img_path from question_answer_file where q_seq = #{q_seq} and q_ans_seq = #{q_ans_seq}")
	public List<QuestionAnswerFileTO> modify(QuestionAnswerFileTO to);
}
