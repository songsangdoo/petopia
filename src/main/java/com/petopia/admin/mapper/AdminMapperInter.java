package com.petopia.admin.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.admin.model.AdminTO;
import com.petopia.pointshop.model.PointShopTO;


@Mapper
public interface AdminMapperInter {
	
	@Select("SELECT A.*, B.* FROM Member A LEFT JOIN Pro_card B ON A.M_SEQ = B.M_SEQ")
    @Results({
        @Result(property = "m_seq", column = "M_SEQ"),
        @Result(property = "m_id", column = "M_ID"),
        @Result(property = "m_nickname", column = "M_NICKNAME"),
        @Result(property = "m_name", column = "M_NAME"),
        @Result(property = "m_password", column = "M_PASSWORD"),
        @Result(property = "m_email", column = "M_EMAIL"),
        @Result(property = "m_addr", column = "M_ADDR"),
        @Result(property = "m_phone", column = "M_PHONE"),
        @Result(property = "m_age", column = "M_AGE"),
        @Result(property = "m_gender", column = "M_GENDER"),
        @Result(property = "m_date", column = "M_DATE"),
        @Result(property = "grade_seq", column = "GRADE_SEQ"),
        @Result(property = "m_totalPoint", column = "M_TOTALPOINT"),
        @Result(property = "m_point", column = "M_POINT"),
        @Result(property = "proComment", column = "PRO_COMMENT"),
        @Result(property = "proImg", column = "PRO_IMG")})
	public abstract ArrayList<AdminTO> getMemberProfile();
	
	@Select("SELECT A.*, B.* FROM Member A LEFT JOIN Pro_card B ON A.M_SEQ = B.M_SEQ WHERE A.M_NAME LIKE CONCAT('%', #{keyword}, '%')")
	@Results({
		@Result(property = "m_seq", column = "M_SEQ"),
		@Result(property = "m_id", column = "M_ID"),
		@Result(property = "m_nickname", column = "M_NICKNAME"),
		@Result(property = "m_name", column = "M_NAME"),
		@Result(property = "m_password", column = "M_PASSWORD"),
		@Result(property = "m_email", column = "M_EMAIL"),
		@Result(property = "m_addr", column = "M_ADDR"),
		@Result(property = "m_phone", column = "M_PHONE"),
		@Result(property = "m_age", column = "M_AGE"),
		@Result(property = "m_gender", column = "M_GENDER"),
		@Result(property = "m_date", column = "M_DATE"),
		@Result(property = "grade_seq", column = "GRADE_SEQ"),
		@Result(property = "m_totalPoint", column = "M_TOTALPOINT"),
		@Result(property = "m_point", column = "M_POINT"),
		@Result(property = "proComment", column = "PRO_COMMENT"),
		@Result(property = "proImg", column = "PRO_IMG")})
	public abstract ArrayList<AdminTO> getMemberFilteredName(String keyword);
	
	@Select("SELECT A.*, B.* FROM Member A LEFT JOIN Pro_card B ON A.M_SEQ = B.M_SEQ WHERE A.M_NICKNAME LIKE CONCAT('%', #{keyword}, '%')")
	@Results({
		@Result(property = "m_seq", column = "M_SEQ"),
		@Result(property = "m_id", column = "M_ID"),
		@Result(property = "m_nickname", column = "M_NICKNAME"),
		@Result(property = "m_name", column = "M_NAME"),
		@Result(property = "m_password", column = "M_PASSWORD"),
		@Result(property = "m_email", column = "M_EMAIL"),
		@Result(property = "m_addr", column = "M_ADDR"),
		@Result(property = "m_phone", column = "M_PHONE"),
		@Result(property = "m_age", column = "M_AGE"),
		@Result(property = "m_gender", column = "M_GENDER"),
		@Result(property = "m_date", column = "M_DATE"),
		@Result(property = "grade_seq", column = "GRADE_SEQ"),
		@Result(property = "m_totalPoint", column = "M_TOTALPOINT"),
		@Result(property = "m_point", column = "M_POINT"),
		@Result(property = "proComment", column = "PRO_COMMENT"),
		@Result(property = "proImg", column = "PRO_IMG")})
	public abstract ArrayList<AdminTO> getMemberFilteredNickName(String keyword);
	
	@Select("SELECT A.*, B.* FROM Member A LEFT JOIN Pro_card B ON A.M_SEQ = B.M_SEQ WHERE A.M_SEQ LIKE CONCAT('%', #{keyword}, '%')")
	@Results({
		@Result(property = "m_seq", column = "M_SEQ"),
		@Result(property = "m_id", column = "M_ID"),
		@Result(property = "m_nickname", column = "M_NICKNAME"),
		@Result(property = "m_name", column = "M_NAME"),
		@Result(property = "m_password", column = "M_PASSWORD"),
		@Result(property = "m_email", column = "M_EMAIL"),
		@Result(property = "m_addr", column = "M_ADDR"),
		@Result(property = "m_phone", column = "M_PHONE"),
		@Result(property = "m_age", column = "M_AGE"),
		@Result(property = "m_gender", column = "M_GENDER"),
		@Result(property = "m_date", column = "M_DATE"),
		@Result(property = "grade_seq", column = "GRADE_SEQ"),
		@Result(property = "m_totalPoint", column = "M_TOTALPOINT"),
		@Result(property = "m_point", column = "M_POINT"),
		@Result(property = "proComment", column = "PRO_COMMENT"),
		@Result(property = "proImg", column = "PRO_IMG")})
	public abstract ArrayList<AdminTO> getMemberFilteredSeq(String keyword);
	
	@Select("SELECT A.*, B.* FROM Member A LEFT JOIN Pro_card B ON A.M_SEQ = B.M_SEQ WHERE A.M_SEQ = #{mSeq}")
	@Results({
	    @Result(property = "m_seq", column = "M_SEQ"),
	    @Result(property = "m_id", column = "M_ID"),
	    @Result(property = "m_nickname", column = "M_NICKNAME"),
	    @Result(property = "m_name", column = "M_NAME"),
	    @Result(property = "m_password", column = "M_PASSWORD"),
	    @Result(property = "m_email", column = "M_EMAIL"),
	    @Result(property = "m_addr", column = "M_ADDR"),
	    @Result(property = "m_phone", column = "M_PHONE"),
	    @Result(property = "m_age", column = "M_AGE"),
	    @Result(property = "m_gender", column = "M_GENDER"),
	    @Result(property = "m_date", column = "M_DATE"),
	    @Result(property = "grade_seq", column = "GRADE_SEQ"),
	    @Result(property = "m_totalPoint", column = "M_TOTALPOINT"),
	    @Result(property = "m_point", column = "M_POINT"),
	    @Result(property = "proComment", column = "PRO_COMMENT"),
	    @Result(property = "proImg", column = "PRO_IMG")})
	public abstract AdminTO getInfo(@Param("mSeq") String mSeq);
	
	@Delete("DELETE FROM MEMBER WHERE M_SEQ=#{seq}")
	public abstract AdminTO initDelete(String seq);
	
	@Select("SELECT M_SEQ, M_ID, M_NICKNAME, M_NAME, M_PASSWORD, M_EMAIL, M_ADDR, M_PHONE, M_AGE, M_GENDER, M_DATE, GRADE_SEQ, M_TOTALPOINT, M_POINT FROM MEMBER WHERE M_ID=#{id} AND M_PASSWORD=#{password}")
	public abstract AdminTO loginVerified(String id, String password);
	
	/* 회원가입 */
	@Insert("INSERT INTO member (m_Seq, m_Id, m_Nickname, m_Name, m_Password, m_Email, m_Addr, m_Phone, m_Age, m_Gender, m_Date, GRADE_SEQ, m_TotalPoint, m_Point) " +
            "VALUES (0, #{m_id}, #{m_nickname}, #{m_name}, #{m_password}, #{m_email}, #{m_addr}, #{m_phone}, #{m_age}, #{m_gender}, now(), Default,0,0)")
	public int member_insert(AdminTO to);
	
	/* 중복체크 */
	@Select("select count(*) from member where m_id=#{m_id}")
	public void checkIdDup(AdminTO to);
	
	/* 중복체크 */
	@Select("select count(*) from member where m_id=#{m_id}")
	public void checkPasswordDup(AdminTO to);
	
	/* 관리자 포인트샵 */
	@Delete("DELETE FROM POINTSHOP WHERE PS_SEQ IN (${seqList})")
    void deleteObjectsBySeqList(@Param("seqList") String seqList);
	
	@Select("select ps_img FROM POINTSHOP WHERE PS_SEQ IN (${seqList})")
	public List<String> deleteFileImgPaths(@Param("seqList") String seqList);
	
	@Select("select ps_cate from pointshop where ps_img = #{imgPaths}")
	public String deleteCategoryCheck( String imgPaths );
	
	@Update("update pointshop set ps_name = #{ps_name}, ps_content = #{ps_content}, ps_price = #{ps_price} where ps_seq = #{ps_seq}")
	public int adminModifyItem( PointShopTO pointShopTO );
	
	@Update("update pointshop set ps_name = #{ps_name}, ps_content = #{ps_content}, ps_img = #{ps_img}, ps_price = #{ps_price} where ps_seq = #{ps_seq}")
	public int adminFileModifyItem( PointShopTO pointShopTO );
	
	@Select("select ps_img from pointshop where ps_seq = #{ps_seq}")
	public String adminFileSearch( String ps_seq );
	
}
