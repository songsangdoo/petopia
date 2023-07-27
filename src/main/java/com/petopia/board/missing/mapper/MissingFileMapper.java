package com.petopia.board.missing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.missing.model.MissingFileTO;

@Mapper
public interface MissingFileMapper {

	@Select("select ms_file_img_path from missing_file where ms_seq = #{ms_seq}")
	public List<MissingFileTO> list(MissingFileTO to);

	@Insert("INSERT INTO missing_FILE(ms_SEQ, ms_FILE_SEQ, ms_FILE_IMG_PATH) SELECT #{ms_seq}, (SELECT IFNULL(MAX(ms_FILE_SEQ) + 1, 0) FROM missing_FILE WHERE ms_SEQ = #{ms_seq}), #{ms_file_img_path}")
	public int writeOk(MissingFileTO to);
	
	@Select("select ms_file_img_path from missing_file where ms_seq = #{ms_seq}")
	public List<MissingFileTO> delete(MissingFileTO to);
	
	@Delete("delete from missing_file where ms_seq = #{ms_seq}")
	public int deleteOk(MissingFileTO to);
}
