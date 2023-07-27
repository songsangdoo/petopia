package com.petopia.board.information.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.information.model.InformationFileTO;

@Mapper
public interface InformationFileMapper {
	
	@Select("select info_file_img_path from information_file where info_seq = #{info_seq}")
	public List<InformationFileTO> list(InformationFileTO to);

	@Insert("INSERT INTO INFORMATION_FILE(INFO_SEQ, INFO_FILE_SEQ, INFO_FILE_IMG_PATH) SELECT #{info_seq}, (SELECT IFNULL(MAX(INFO_FILE_SEQ) + 1, 0) FROM INFORMATION_FILE WHERE INFO_SEQ = #{info_seq}), #{info_file_img_path}")
	public int writeOk(InformationFileTO to);
	
	@Select("select info_file_img_path from information_file where info_seq = #{info_seq}")
	public List<InformationFileTO> delete(InformationFileTO to);
	
	@Delete("delete from information_file where info_seq = #{info_seq}")
	public int deleteOk(InformationFileTO to);
	
}
