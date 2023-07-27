package com.petopia.group.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.group.board.model.GroupBoardTO;

@Mapper
public interface GroupBoardMapper {

	@Select("select GRB_SEQ,  GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and a.GR_SEQ = #{GR_SEQ} order by GRB_SEQ desc")
	public List<GroupBoardTO> latestList(GroupBoardTO to);//매개변수 추가 gr_seq
	
	@Select("select GR_DATE, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and GR_SUBJECT like #{GR_SUBJECT} and a.GR_SEQ = #{GR_SEQ} order by GRB_SEQ desc")
	public List<GroupBoardTO> subjectLatestList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and  M_NICKNAME = #{M_NICKNAME} and a.GR_SEQ = #{GR_SEQ}  order by GRB_SEQ desc")
	public List<GroupBoardTO> writerLatestList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and GR_CONTENT like #{GR_CONTNET} and a.GR_SEQ = #{GR_SEQ}  order by GRB_SEQ desc")
	public List<GroupBoardTO> contentLatestList(GroupBoardTO to);

	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and a.GR_SEQ = #{GR_SEQ} order by GR_GOOD desc, GRB_SEQ desc")
	public List<GroupBoardTO> recList(GroupBoardTO to); //매개변수 추가 gr_seq
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and GR_SUBJECT like #{GR_SUBJECT} and a.GR_SEQ = #{GR_SEQ}  order by GR_GOOD desc, GRB_SEQ desc")
	public List<GroupBoardTO> subjectRecList(GroupBoardTO to);
	
	@Select("select GRB_SEQ,, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and M_NICKNAME = #{M_NICKNAME} and a.GR_SEQ = #{GR_SEQ}  order by GR_GOOD desc, GRB_SEQ desc")
	public List<GroupBoardTO> writerRecList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and GR_CONTENT like #{GR_CONTENT} and a.GR_SEQ = #{GR_SEQ}  order by GR_GOOD desc, GRB_SEQ desc")
	public List<GroupBoardTO> contentRecList(GroupBoardTO to);

	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and a.GR_SEQ = #{GR_SEQ}  order by GR_COMMENT desc, GRB_SEQ desc")
	public List<GroupBoardTO> cmtList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and GR_SUBJECT like #{GR_SUBJECT} and a.GR_SEQ = #{GR_SEQ} order by GR_COMMENT desc, GRB_SEQ desc")
	public List<GroupBoardTO> subjectCmtList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and M_NICKNAME = #{M_NICKNAME} and a.GR_SEQ = #{GR_SEQ} order by GR_COMMENT desc, GRB_SEQ desc")
	public List<GroupBoardTO> writerCmtList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and GR_CONTENT like #{GR_CONTENT} and a.GR_SEQ = #{GR_SEQ} order by GR_COMMENT desc, GRB_SEQ desc")
	public List<GroupBoardTO> contentCmtList(GroupBoardTO to);

	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and a.GR_SEQ = #{GR_SEQ} order by GR_HIT desc, GRB_SEQ desc")
	public List<GroupBoardTO> hitList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and GR_SUBJECT like #{GR_SUBJECT} and a.GR_SEQ = #{GR_SEQ} order by GR_HIT desc, GRB_SEQ desc")
	public List<GroupBoardTO> subjectHitList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and M_NICKNAME = #{M_NICKNAME} and a.GR_SEQ = #{GR_SEQ} order by GR_HIT desc, GRB_SEQ desc")
	public List<GroupBoardTO> writerHitList(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, M_NICKNAME from group1_board a inner join member m where a.M_SEQ = m.M_SEQ and GR_CONTENT like #{GR_CONTENT} and a.GR_SEQ = #{GR_SEQ} order by GR_HIT desc, GRB_SEQ desc")
	public List<GroupBoardTO> contentHitList(GroupBoardTO to);
	
	@Insert("INSERT INTO group1_board(GRB_SEQ, GR_SEQ, GR_SUBJECT, GR_CONTENT, GR_DATE, M_SEQ, GR_HIT, GR_COMMENT, GR_GOOD) VALUES (DEFAULT, #{GR_SEQ}, #{GR_SUBJECT}, #{GR_CONTENT}, DEFAULT, #{M_SEQ}, DEFAULT, DEFAULT, DEFAULT)")
	public int write(GroupBoardTO to);
		
	@Select("select a.GRB_SEQ, GR_SUBJECT, GR_CONTENT, GR_DATE, GR_HIT, GR_COMMENT, GR_GOOD, m.M_SEQ, M_NICKNAME from group1_board a inner join member m where GRB_SEQ = #{GRB_SEQ} and a.M_SEQ = m.M_SEQ")
	public GroupBoardTO view(GroupBoardTO to);
	
	@Delete("delete from group1_board where GRB_SEQ = #{GRB_SEQ}")
	public int deleteOk(GroupBoardTO to);
	
	@Update("update group1_board set GR_HIT = GR_HIT + 1 where GRB_SEQ= #{GRB_SEQ}")
	public int upHit(GroupBoardTO to);
	
	@Update("update group1_board set GR_COMMENT = GR_COMMENT + 1 where GRB_SEQ = #{GRB_SEQ}")
	public int upCmt(GroupBoardTO to);
	
	@Update("update group1_board set GR_COMMENT = GR_COMMENT - 1 where GRB_SEQ = #{GRB_SEQ}")
	public int downCmt(GroupBoardTO to);
	
	@Update("update group1_board set GR_GOOD = GR_GOOD + 1 where GRB_SEQ = #{GRB_SEQ}")
	public int upRec(GroupBoardTO to);
	
	@Select("select GRB_SEQ, GR_SUBJECT, GR_CONTENT from group1_board where GRB_SEQ = #{GRB_SEQ}")
	public GroupBoardTO modify(GroupBoardTO to);
	
	@Update("update group1_board set GR_SUBJECT = #{GR_SUBJECT}, GR_CONTENT = #{GR_CONTENT} where GRB_SEQ = #{GRB_SEQ}")
	public int modifyOk(GroupBoardTO to);
	
}
