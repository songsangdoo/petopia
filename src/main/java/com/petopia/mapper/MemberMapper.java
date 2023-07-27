package com.petopia.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.model.MemberTO;

@Mapper
public interface MemberMapper {

	@Select("select m_seq, m_nickname from member where m_seq = #{m_seq}")
	public MemberTO member(MemberTO to);

	@Select("select count(*) from member where m_id=#{m_id}")  
	 public int checkIdDup(String m_id);
	 
	 @Select("select count(*) from member where m_email=#{m_email}")  
	 public int checkEmailDup(String m_email);
	 
	 @Select("select count(*) from member where m_nickname=#{m_nickname}")  
	 public int checkNicknameDup(String m_nickname);
	 
	 @Select("select count(*) from member where m_email=#{m_email}")
	 public int kakaoLogin(String m_email);
	
	 @Select("select * from member where m_id=#{m_id}")
	 public String idCheck(String m_id);
	 
	 @Select("select * from member where m_id=#{m_id}")
	 public MemberTO userTransfer(String m_id);
	 
	 @Select( "select m_seq from member order by m_seq desc limit 1" )
	 public String selectSeq();
	 
	 @Insert( "insert into pro_card values ( #{m_seq}, '한 마디 글을 작성해 주세요.', 'no_image.png' )" )
	 public int profileCardInsertOk( String m_id );
	
}
