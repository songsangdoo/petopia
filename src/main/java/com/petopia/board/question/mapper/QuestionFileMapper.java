package com.petopia.board.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.question.model.QuestionFileTO;

@Mapper
public interface QuestionFileMapper {

	@Select("select q_file_img_path from question_file where q_seq = #{q_seq}")
	public List<QuestionFileTO> list(QuestionFileTO to);

	@Insert("INSERT INTO QUESTION_FILE(Q_SEQ, Q_FILE_SEQ, Q_FILE_IMG_PATH) SELECT #{q_seq}, (SELECT IFNULL(MAX(Q_FILE_SEQ) + 1, 0) FROM QUESTION_FILE WHERE Q_SEQ = #{q_seq}), #{q_file_img_path}")
	public int writeOk(QuestionFileTO to);
	
	@Select("select q_file_img_path from question_file where q_seq = #{q_seq}")
	public List<QuestionFileTO> delete(QuestionFileTO to);
	
	@Delete("delete from question_file where q_seq = #{q_seq}")
	public int deleteOk(QuestionFileTO to);
}
