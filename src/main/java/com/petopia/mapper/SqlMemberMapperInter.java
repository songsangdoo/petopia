package com.petopia.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.model.MemberTO;

@Mapper
public interface SqlMemberMapperInter {
	
	@Select("SELECT M_SEQ, M_ID, M_NICKNAME, M_NAME, M_PASSWORD, M_EMAIL, M_ADDR, M_PHONE, M_AGE, M_GENDER, M_DATE, GRADE_SEQ, M_TOTALPOINT, M_POINT FROM MEMBER")
	public abstract ArrayList<MemberTO> getMember();
	
	@Select("SELECT M_SEQ, M_ID, M_NICKNAME, M_NAME, M_PASSWORD, M_EMAIL, M_ADDR, M_PHONE, M_AGE, M_GENDER, M_DATE, GRADE_SEQ, M_TOTALPOINT, M_POINT FROM MEMBER WHERE M_SEQ=#{seq}")
	public abstract MemberTO getInfo(String seq);
	
	@Delete("DELETE FROM MEMBER WHERE M_SEQ=#{seq}")
	public abstract MemberTO initDelete(String seq);
	
	@Select("SELECT M_SEQ, M_ID, M_NICKNAME, M_NAME, M_PASSWORD, M_EMAIL, M_ADDR, M_PHONE, M_AGE, M_GENDER, M_DATE, GRADE_SEQ, M_TOTALPOINT, M_POINT FROM MEMBER WHERE M_ID=#{id} AND M_PASSWORD=#{password}")
	public abstract MemberTO loginVerified(String id, String password);
	
	/* 회원가입 */
    @Insert("INSERT INTO member (m_Seq, m_Id, m_Nickname, m_Name, m_Password, m_Email, m_Addr, m_Phone, m_Age, m_Gender, m_Date, GRADE_SEQ, m_TotalPoint, m_Point) "
            + "VALUES (0, #{m_id}, #{m_nickname}, #{m_name}, #{m_password}, #{m_email}, #{m_addr}, #{m_phone}, #{m_age}, #{m_gender}, DATE_FORMAT(now(), '%Y%m%d%H%i%s'), Default,0,0)")
    public int member_insert(MemberTO to);
    
    @Insert("INSERT INTO member (m_Seq, m_Id, m_Nickname, m_Name, m_Password, m_Email, m_Addr, m_Phone, m_Age, m_Gender, m_Date, GRADE_SEQ, m_TotalPoint, m_Point) "
            + "VALUES (0, #{m_id}, #{m_nickname}, #{m_name}, #{m_password}, #{m_email}, #{m_addr}, #{m_phone}, #{m_age}, #{m_gender}, DATE_FORMAT(now(), '%Y%m%d%H%i%s'), Default,0,0)")
	public int member_kakaoInsert(MemberTO to2);
    
    // 게시판 관련
    @Update("update member set m_totalPoint = m_totalPoint + 10, m_point = m_point + 10 where m_seq = #{m_seq}")
    public int upPoint10(MemberTO to);
    
    @Update("update member set m_totalPoint = m_totalPoint + 20, m_point = m_point + 20 where m_seq = #{m_seq}")
    public int upPoint20(MemberTO to);

    // 승급 관련
    static final int knightPoint = 300;
    static final int elderPoint = 1000;
    static final int honorPoint = 5000;
    static final int royalstarPoint = 10000;
    
    @Update("update member set grade_seq = 3 where grade_seq = 2 and m_totalPoint >= " + knightPoint + " and m_totalPoint < " + elderPoint + " and m_seq = #{m_seq}")
    public int becomeKnight(MemberTO to);
    
    @Update("update member set grade_seq = 4 where grade_seq = 3 and m_totalPoint >= " + elderPoint + " and m_totalPoint < " + honorPoint + " and m_seq = #{m_seq}")
    public int becomeElder(MemberTO to);
    
    @Update("update member set grade_seq = 5 where grade_seq = 4 and m_totalPoint >= " + honorPoint + " and m_totalPoint < " + royalstarPoint + " and m_seq = #{m_seq}")
    public int becomeHonor(MemberTO to);
    
    @Update("update member set grade_seq = 6 where grade_seq = 5 and m_totalPoint >= " + royalstarPoint + " and m_seq = #{m_seq}")
    public int becomeRoyalStar(MemberTO to);
    
}
