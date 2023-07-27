package com.petopia.board.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.notice.model.NoticeFileTO;

@Mapper
public interface NoticeFileMapper {

	@Select("select n_file_img_path from notice_file where n_seq = #{n_seq}")
	public List<NoticeFileTO> list(NoticeFileTO to);

	@Insert("INSERT INTO NOTICE_FILE(N_SEQ, N_FILE_SEQ, N_FILE_IMG_PATH) SELECT #{n_seq}, (SELECT IFNULL(MAX(N_FILE_SEQ) + 1, 0) FROM NOTICE_FILE WHERE N_SEQ = #{n_seq}), #{n_file_img_path}")
	public int writeOk(NoticeFileTO to);
	
	@Select("select n_file_img_path from notice_file where n_seq = #{n_seq}")
	public List<NoticeFileTO> delete(NoticeFileTO to);
	
	@Delete("delete from notice_file where n_seq = #{n_seq}")
	public int deleteOk(NoticeFileTO to);
}
