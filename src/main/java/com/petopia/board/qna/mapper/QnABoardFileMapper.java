package com.petopia.board.qna.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.qna.model.QnABoardFileTO;

@Mapper
public interface QnABoardFileMapper {
	
	@Select("select qa_seq, qa_file_img_path from qna_file")
	public List<QnABoardFileTO> allList();
	
	@Select("select qa_seq, qa_file_img_path from qna_file where qa_seq = #{qa_seq}")
	public List<QnABoardFileTO> list(QnABoardFileTO to);

	@Insert("INSERT INTO QNA_FILE(QA_SEQ, QA_FILE_SEQ, QA_FILE_IMG_PATH) SELECT #{qa_seq}, (SELECT IFNULL(MAX(QA_FILE_SEQ) + 1, 0) FROM QNA_FILE WHERE QA_SEQ = #{qa_seq}), #{qa_file_img_path}")
	public int writeOk(QnABoardFileTO to);
	
	@Select("select qa_file_img_path from qna_file where qa_seq = #{qa_seq}")
	public List<QnABoardFileTO> delete(QnABoardFileTO to);
	
	@Delete("delete from qna_file where qa_seq = #{qa_seq}")
	public int deleteOk(QnABoardFileTO to);
}
