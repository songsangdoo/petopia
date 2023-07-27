package com.petopia.group.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.board.free.model.FreeBoardCommentTO;
import com.petopia.group.board.model.GroupBoardCommentTO;

@Mapper
public interface GroupBoardCommentMapper {

	@Select("select GR_CO_SEQ, GR_CO_CONTENT, GR_CO_DATE, m.M_SEQ, GR_CO_REC_CNT, GR_CO_NO_REC_CNT, m_nickname from group1_board_comment ac inner join member m where GR_SEQ = #{GR_SEQ} and ac.M_SEQ = m.M_SEQ order by GR_CO_SEQ desc")
	public List<GroupBoardCommentTO> latestList(GroupBoardCommentTO to);	
	
	@Select("select GR_CO_SEQ, GR_CO_CONTENT, GR_CO_DATE, m.M_SEQ, GR_CO_REC_CNT, GR_CO_NO_REC_CNT, m_nickname from group1_board_comment ac inner join member m where GRB_SEQ = #{GRB_SEQ} and ac.M_SEQ = m.M_SEQ order by GR_CO_REC_CNT desc, GR_CO_SEQ desc")
	public List<GroupBoardCommentTO> recList(GroupBoardCommentTO co);
	
	@Insert("INSERT INTO group1_board_comment(GR_SEQ, GRB_SEQ, GR_CO_SEQ, GR_CO_CONTENT, GR_CO_DATE, M_SEQ, GR_CO_REC_CNT, GR_CO_NO_REC_CNT) SELECT #{GR_SEQ}, #{GRB_SEQ}, (SELECT IFNULL(MAX(GR_CO_SEQ) + 1, 0) FROM group1_board_comment	WHERE GRB_SEQ = #{GRB_SEQ}), #{GR_CO_CONTENT}, DATE_FORMAT(now(), '%Y%m%d%H%i%s') GR_CO_DATE, #{M_SEQ}, 0 GR_CO_REC_CNT, 0 GR_CO_NO_REC_CNT")
	public int wrightOk(GroupBoardCommentTO to);
	
	@Update("update group1_board_comment set GR_CO_REC_CNT = GR_CO_REC_CNT + 1 where GRB_SEQ = #{GRB_SEQ} and GR_CO_SEQ = #{GR_CO_SEQ}")
	public int upRec(GroupBoardCommentTO to);
	
	@Update("update group1_board_comment set GR_CO_NO_REC_CNT = GR_CO_NO_REC_CNT + 1 where GRB_SEQ = #{GRB_SEQ} and GR_CO_SEQ = #{GR_CO_SEQ}")
	public int upNoRec(GroupBoardCommentTO to);
	
	@Delete("delete from group1_board_comment where grb_seq = #{GRB_SEQ} and gr_co_seq = #{GR_CO_SEQ}")
	public int deleteOk(GroupBoardCommentTO to);
	
	@Delete("delete from group1_board_comment where grb_seq = #{GRB_SEQ}")
	public int deleteAllOk(GroupBoardCommentTO to);
}
