package com.petopia.board.tip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.board.tip.model.TipBoardFileTO;

@Mapper
public interface TipBoardFileMapper {

	@Select("select tb_file_img_path from tipboard_file where tb_seq = #{tb_seq}")
	public List<TipBoardFileTO> list(TipBoardFileTO to);

	@Insert("INSERT INTO TIPBOARD_FILE(TB_SEQ, TB_FILE_SEQ, TB_FILE_IMG_PATH) SELECT #{tb_seq}, (SELECT IFNULL(MAX(TB_FILE_SEQ) + 1, 0) FROM TIPBOARD_FILE WHERE TB_SEQ = #{tb_seq}), #{tb_file_img_path}")
	public int writeOk(TipBoardFileTO to);
	
	@Select("select tb_file_img_path from tipboard_file where tb_seq = #{tb_seq}")
	public List<TipBoardFileTO> delete(TipBoardFileTO to);
	
	@Delete("delete from tipboard_file where tb_seq = #{tb_seq}")
	public int deleteOk(TipBoardFileTO to);
}
