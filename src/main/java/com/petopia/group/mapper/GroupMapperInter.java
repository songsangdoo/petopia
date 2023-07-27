 package com.petopia.group.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.admin.model.AdminTO;
import com.petopia.board.tip.model.TipBoardTO;
import com.petopia.group.board.model.GroupBoardCommentTO;
import com.petopia.group.board.model.GroupBoardFileTO;
import com.petopia.group.board.model.GroupBoardTO;
import com.petopia.group.model.GroupHashTO;
import com.petopia.group.model.GroupTO;
import com.petopia.group.model.JoinGroupTO;
import com.petopia.group.model.MyGroupTO;


public interface GroupMapperInter {
	@Select("select a.gr_seq, a.m_seq, a.gr_name, a.gr_admin, a.gr_inwon, a.gr_explan, a.gr_filename, date_format(a.gr_date,'%Y-%m-%d') gr_date, a.gr_produce, a.gr_hash_cnt, b.has_content \r\n"
			+ "from group1 a, group1_hashtag b \r\n"
			+ "where A.GR_SEQ = B.GR_SEQ \r\n"
			+ "order by GR_SEQ desc")
	public ArrayList<GroupTO> groupList();
	

	@Select("select a.gr_seq, a.m_seq, a.gr_name, a.gr_admin, a.gr_inwon, a.gr_explan, a.gr_filename, date_format(a.gr_date,'%Y-%m-%d') gr_date, a.gr_produce, a.gr_hash_cnt, b.has_content \r\n"
			+ "from group1 a, group1_hashtag b \r\n"
			+ "where a.gr_name like #{GR_NAME} and A.GR_SEQ = B.GR_SEQ \r\n"
			+ "order by GR_SEQ desc")
	public ArrayList<GroupTO> subjectHashList(GroupTO to);
	
	@Select("select a.gr_seq, a.m_seq, a.gr_name, a.gr_admin, a.gr_inwon, a.gr_explan, a.gr_filename, date_format(a.gr_date,'%Y-%m-%d') gr_date, a.gr_produce, a.gr_hash_cnt, b.has_content \r\n"
			+ "from group1 a, group1_hashtag b \r\n"
			+ "where a.gr_explan like #{GR_EXPLAN} and A.GR_SEQ = B.GR_SEQ \r\n"
			+ "order by GR_SEQ desc ")
	public ArrayList<GroupTO> contentHashList(GroupTO to);
	
	@Select("select GR_SEQ FROM GROUP1_HASHTAG where HAS_CONTENT like #{HAS_CONTENT} order by gr_seq desc ")
	public ArrayList<GroupHashTO> HashList(GroupHashTO ghto);
	@Select("select a.gr_seq, a.m_seq, a.gr_name, a.gr_admin, a.gr_inwon, a.gr_explan, a.gr_filename, date_format(a.gr_date,'%Y-%m-%d') gr_date, a.gr_produce, a.gr_hash_cnt, b.has_content \r\n"
			+ "from group1 a, group1_hashtag b \r\n"
			+ "where a.GR_SEQ = #{GR_SEQ} AND b.GR_SEQ = #{GR_SEQ} \r\n"
			+ "order by GR_SEQ desc ")
	public ArrayList<GroupTO> hashSearchList(GroupTO gto);
	
	
	@Select("select group1.gr_seq, group1.m_seq, group1.gr_name, group1.gr_explan, group1.gr_filename, date_format(group1.gr_date,'%Y-%m-%d') gr_date, group1.gr_produce, member.m_nickname from group1, member where group1.m_seq = member.m_seq order by gr_produce asc")
	public ArrayList<GroupTO> mastergroupList();
	
	@Insert("insert into group1 values(0, #{M_SEQ}, #{GR_NAME}, 'gr_admin', 1, #{GR_EXPLAN}, #{GR_FILENAME},#{GR_FILESIZE}, DEFAULT,false,#{GR_HASH_CNT})")
	public int groupWriteOk(GroupTO to);
	@Select("select max(gr_seq) gr_seq from group1")
	public int groupRecentSeq();
	@Insert("insert into group1_hashtag values(0,#{GR_SEQ},#{HAS_CONTENT})")
	public int groupHashOk(GroupHashTO to);
	
	@Select("select a.gr_seq, a.m_seq, a.gr_name, a.gr_admin, a.gr_inwon, a.gr_explan, c.m_nickname, a.gr_filename, date_format(a.gr_date,'%Y-%m-%d') gr_date, a.gr_produce, a.gr_hash_cnt, b.jg_grade"
			+ " from group1 a join join_group b "
			+ "on a.gr_seq=#{GR_SEQ} "
			+ "and b.gr_seq=#{GR_SEQ} "
			+ "and a.m_seq=#{M_SEQ} "
			+ "and b.m_seq=#{M_SEQ} "
			+ "join member c "
			+ "on a.m_seq = c.m_seq")
	public GroupTO groupViewLogin(GroupTO to);
	@Select("select a.gr_seq, a.m_seq, a.gr_name, a.gr_admin, a.gr_inwon, member.m_nickname, a.gr_explan, a.gr_filename, date_format(a.gr_date,'%Y-%m-%d') gr_date, a.gr_produce, a.gr_hash_cnt "
			+ " from group1 a join member"
			+ " on a.gr_seq=#{GR_SEQ} "
			+ "and a.m_seq = member.m_seq")
	public GroupTO groupViewLogout(GroupTO to);
	@Select("select jg_join from join_group where gr_seq = #{GR_SEQ} and m_seq = #{M_SEQ} ")
	public Boolean joinCheck(JoinGroupTO to);
	@Select("select HAS_CONTENT FROM GROUP1_HASHTAG WHERE GR_SEQ = #{GR_SEQ}")
	public ArrayList<GroupHashTO> groupHashView(GroupHashTO to);
	
	@Update("update group1 set gr_produce = true where gr_seq=#{GR_SEQ}")
	public int groupProduce(GroupTO to);
	@Select("select m_seq, gr_admin from group1 where gr_seq = #{GR_SEQ}")
	public GroupTO findAdminSeq(GroupTO to);
	@Insert("insert into join_group values(#{GR_SEQ},#{M_SEQ},#{GR_ADMIN},TRUE,#{JG_EXPLAIN},1)")
	public int groupProduceAdmin(JoinGroupTO to);
	
	@Delete("delete from group1 where gr_seq=#{GR_SEQ}")
	public int groupProduceDelete(GroupTO to);
	
	@Insert("INSERT INTO JOIN_GROUP VALUES(#{GR_SEQ},#{M_SEQ},#{GR_ADMIN},FALSE,#{JG_EXPLAIN},DEFAULT)")
	public int joinGroupWriteOk(JoinGroupTO to);
	
	@Select("select gr_admin from group1 where gr_seq = #{GR_SEQ}")
	public GroupTO findAdmin(GroupTO to);
	
	
	
	@Select("SELECT group1.gr_name,group1.m_seq gr_admin_seq,group1.gr_seq, join_group.GR_ADMIN, join_group.M_SEQ, date_format(group1.gr_date,'%Y-%m-%d') gr_date, group1.gr_filename, group1.gr_inwon, group1.gr_explan, join_group.jg_grade, group1.gr_hash_cnt, group1_hashtag.has_content "
			+ "FROM group1 join join_group "
			+ "on group1.gr_seq = join_group.GR_SEQ "
			+ "AND join_group.JG_JOIN = TRUE "
			+ "AND join_group.M_SEQ = #{M_SEQ} "
			+ "JOIN GROUP1_HASHTAG "
			+ "ON GROUP1.GR_SEQ = GROUP1_HASHTAG.GR_SEQ "
			+ "ORDER BY GR_SEQ DESC")
	public ArrayList<MyGroupTO> mygroupList(MyGroupTO to);
	@Select("SELECT group1.gr_name,group1.m_seq gr_admin_seq,group1.gr_seq, join_group.GR_ADMIN, join_group.M_SEQ, date_format(group1.gr_date,'%Y-%m-%d') gr_date, group1.gr_filename, group1.gr_inwon, group1.gr_explan, join_group.jg_grade "
			+ "FROM group1, join_group "
			+ "WHERE group1.gr_seq = join_group.GR_SEQ "
			+ "AND join_group.JG_JOIN = TRUE "
			+ "AND join_group.M_SEQ = #{M_SEQ} "
			+ "ORDER BY GR_SEQ DESC")
	public ArrayList<MyGroupTO> mygroupListSidebar(MyGroupTO to);
	@Select("SELECT group1.gr_name,group1.m_seq gr_admin_seq,group1.gr_seq, join_group.GR_ADMIN, join_group.M_SEQ, date_format(group1.gr_date,'%Y-%m-%d') gr_date, group1.gr_filename, group1.gr_inwon, group1.gr_explan, join_group.jg_grade\r\n"
			+ "FROM group1, join_group \r\n"
			+ "WHERE group1.gr_seq = join_group.GR_SEQ AND join_group.JG_JOIN = TRUE AND JOIN_GROUP.JG_GRADE = 1\r\n"
			+ "GROUP BY GROUP1.GR_SEQ")
	public ArrayList<MyGroupTO> mygroupListAdmin();
	
	@Select("SELECT GROUP1.GR_NAME, JOIN_GROUP.M_SEQ, JOIN_GROUP.JG_EXPLAIN , JOIN_GROUP.JG_JOIN, MEMBER.M_NICKNAME, JOIN_GROUP.JG_GRADE "
			+ "FROM GROUP1 JOIN JOIN_GROUP "
			+ "ON GROUP1.GR_SEQ = #{GR_SEQ} "
			+ "AND JOIN_GROUP.GR_SEQ = #{GR_SEQ} "
			+ "JOIN MEMBER "
			+ "ON JOIN_GROUP.M_SEQ = MEMBER.M_SEQ")
	public ArrayList<MyGroupTO> mygroupManageList(MyGroupTO to);
	
	@Update("update join_group "
			+ "set JG_JOIN = true "
			+ "where gr_seq=#{GR_SEQ} and m_seq = #{M_SEQ}")
	public int mygroupManageJoin(JoinGroupTO to);
	
	@Update("update join_group "
			+ "set JG_GRADE = JG_GRADE-1 "
			+ "where gr_seq=#{GR_SEQ} and m_seq = #{M_SEQ}")
	public int mygroupManageAdc(JoinGroupTO to);
	
	@Update("update join_group "
			+ "set JG_GRADE = JG_GRADE+1 "
			+ "where gr_seq=#{GR_SEQ} and m_seq = #{M_SEQ}")
	public int mygroupManageDem(JoinGroupTO to);
	
	@Update("update GROUP1 set gr_inwon = gr_inwon + 1 where gr_seq= #{GR_SEQ}")
	public int upInwon(GroupTO to);
	
	@Delete("delete from join_group where gr_seq=#{GR_SEQ} and m_seq = #{M_SEQ}")
	public int mygroupManageDelete(JoinGroupTO to);
	
	@Delete("DELETE FROM GROUP1 WHERE GR_SEQ=#{GR_SEQ}")
	public int groupDelete(GroupTO to);
	@Delete("DELETE FROM JOIN_GROUP WHERE GR_SEQ=#{GR_SEQ}")
	public int joinGroupDelete(JoinGroupTO to);
	@Delete("DELETE FROM GROUP1_HASHTAG WHERE GR_SEQ=#{GR_SEQ}")
	public int groupHashDelete(GroupHashTO to);
	@Delete("DELETE FROM GROUP1_BOARD_COMMENT WHERE GR_SEQ=#{GR_SEQ}")
	public int groupBoardCommentDelete(GroupBoardCommentTO to);
	@Delete("DELETE FROM GROUP1_BOARD_FILE WHERE GR_SEQ=#{GR_SEQ}")
	public int groupBoardFileDelete(GroupBoardFileTO to);
	@Delete("DELETE FROM GROUP1_BOARD WHERE GR_SEQ=#{GR_SEQ}")
	public int groupBoardDelete(GroupBoardTO to);
	
	@Update("update group1 set GR_NAME = #{GR_NAME}, GR_EXPLAN = #{GR_EXPLAN}, GR_FILENAME = #{GR_FILENAME}, GR_FILESIZE = #{GR_FILESIZE}, GR_HASH_CNT = #{GR_HASH_CNT} "
			+ "where GR_SEQ = #{GR_SEQ}")
	public int modifyOk(GroupTO to);
	
	
	
}
