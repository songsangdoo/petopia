package com.petopia.board.free.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.free.model.FreeBoardFileTO;

@Mapper
public interface FreeBoardFileMapper {

	@Select("select fb_file_img_path from freeboard_file where fb_seq = #{fb_seq}")
	public List<FreeBoardFileTO> list(FreeBoardFileTO to);

	@Insert("INSERT INTO FREEBOARD_FILE(FB_SEQ, FB_FILE_SEQ, FB_FILE_IMG_PATH) SELECT #{fb_seq}, (SELECT IFNULL(MAX(FB_FILE_SEQ) + 1, 0) FROM FREEBOARD_FILE WHERE FB_SEQ = #{fb_seq}), #{fb_file_img_path}")
	public int writeOk(FreeBoardFileTO to);
	
	@Select("select fb_file_img_path from freeboard_file where fb_seq = #{fb_seq}")
	public List<FreeBoardFileTO> delete(FreeBoardFileTO to);
	
	@Delete("delete from freeboard_file where fb_seq = #{fb_seq}")
	public int deleteOk(FreeBoardFileTO to);
}
