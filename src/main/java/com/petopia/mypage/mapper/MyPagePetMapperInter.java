package com.petopia.mypage.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.mypage.model.MyPagePetTO;

@Mapper
public interface MyPagePetMapperInter {
	
	
	@Insert("INSERT INTO PET_INFO (m_seq, p_name, p_age, p_birth, p_species, p_gender, p_img) " +
            "VALUES (#{m_seq}, #{p_name}, #{p_age}, #{p_birth}, #{p_species}, #{p_gender}, #{p_img})")
    public abstract int addPetInfo(MyPagePetTO to);
	
	@Select("SELECT P_SEQ, P_NAME, P_AGE, P_BIRTH, P_SPECIES, P_GENDER, P_IMG FROM PET_INFO WHERE M_SEQ = #{m_seq}")
	public abstract ArrayList<MyPagePetTO> getPetList(String m_seq);
	
	@Select("SELECT P_IMG FROM PET_INFO WHERE P_SEQ = #{p_seq}")
	public abstract String getOldImgName(String p_seq);
	
	@Select("SELECT COUNT(*) FROM PET_INFO WHERE M_SEQ = #{m_seq}")
	public abstract String getCountPet(String m_seq);
	
	@Select("SELECT * FROM PET_INFO WHERE p_seq = #{p_seq} and in_use = 'Y'")
	public abstract MyPagePetTO equipCardPet(String p_seq);
	
	@Update("update pet_info set in_use = 'Y' WHERE p_seq = #{p_seq}")
	public abstract int petSelect( String p_seq );
	
	@Delete("DELETE FROM PET_INFO WHERE P_SEQ = #{p_seq}")
	public abstract int initPetInfoDelete(String p_seq);
	
	@Update( "update pet_info set in_use = 'N' where m_seq = #{m_seq}" )
	public abstract int allNoSelect( String m_seq );
	
}

