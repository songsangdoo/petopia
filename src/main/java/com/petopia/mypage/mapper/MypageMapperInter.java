package com.petopia.mypage.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.model.MemberTO;
import com.petopia.mypage.model.MyPageTO;

@Mapper
public interface MypageMapperInter {
	
	@Select("SELECT * FROM MEMBER WHERE M_SEQ = #{seq}")
	public abstract MyPageTO getMypageInfo(String seq);
	
	@Update("UPDATE MEMBER SET M_NAME = #{memberTO.m_name}, M_PHONE = #{memberTO.m_phone}, M_EMAIL = #{memberTO.m_email}, M_ADDR = #{memberTO.m_addr} WHERE M_SEQ = #{seq}" )
	public abstract int setModifyInfo( String seq, @Param( "memberTO" ) MemberTO memberTO );
	
	@Update("UPDATE MEMBER SET M_PASSWORD = #{m_password} WHERE M_SEQ = #{m_seq}" )
	public abstract int setPasswordModify( String m_seq, String m_password );
	
	@Delete("DELETE FROM MEMBER WHERE M_SEQ = #{m_seq} AND M_PASSWORD =#{m_password}")
	public abstract int delMember(String m_seq, String m_password);
	
	@Update( "update inventory set in_use = 'N' where m_seq = #{m_seq} and ps_cate = 0" )
	public abstract int badgeAllNoequip( String m_seq );
	
	@Update( "update inventory set in_use = 'N' where m_seq = #{m_seq} and ps_cate = 1" )
	public abstract int skinAllNoequip( String m_seq );
	
	@Update( "update inventory set in_use = 'Y' where m_seq = #{m_seq} and ps_seq = #{ps_seq}" )
	public abstract int equipBadge( String m_seq, String ps_seq );
	
	@Update( "update inventory set in_use = 'Y' where m_seq = #{m_seq} and ps_seq = #{ps_seq}" )
	public abstract int equipSkin( String m_seq, String ps_seq );

	
}
