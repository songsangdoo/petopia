package com.petopia.group.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.group.board.model.GroupBoardFileTO;

@Mapper
public interface GroupBoardFileMapper {

	@Select("select grb_file_img_path from group1_board_file where grb_seq = #{GRB_SEQ}")
	public List<GroupBoardFileTO> list(GroupBoardFileTO to);

	@Insert("INSERT INTO GROUP1_BOARD_FILE(GR_SEQ, GRB_SEQ, GRB_FILE_SEQ, GRB_FILE_IMG_PATH) SELECT #{GR_SEQ}, #{GRB_SEQ}, (SELECT IFNULL(MAX(GRB_FILE_SEQ) + 1, 0) FROM GROUP1_BOARD_FILE WHERE GRB_SEQ = #{GRB_SEQ}), #{GRB_FILE_IMG_PATH}")
	public int writeOk(GroupBoardFileTO to);
	
	@Select("select grb_file_img_path from group1_board_file where grb_seq = #{GRB_SEQ}")
	public List<GroupBoardFileTO> delete(GroupBoardFileTO to);
	
	@Delete("delete from group1_board_file where grb_seq = #{GRB_SEQ}")
	public int deleteOk(GroupBoardFileTO to);
}
