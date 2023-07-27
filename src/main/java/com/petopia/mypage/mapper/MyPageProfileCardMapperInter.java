package com.petopia.mypage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.petopia.mypage.model.MypageProfileCardTO;

@Mapper
public interface MyPageProfileCardMapperInter {

	@Select( "select pro_comment, pro_img from pro_card where m_seq = #{m_seq}" )
	public abstract MypageProfileCardTO profileCardInfo( String m_seq );
	
	@Select( "select pro_img from pro_card where m_seq = #{m_seq}" )
	public abstract String searchDeleteFile( String m_seq );
	
	@Insert( "update pro_card set pro_comment = #{pro_comment}, pro_img = #{pro_img} where m_seq = #{m_seq}" )
	public abstract int updateCardInfo( MypageProfileCardTO mypageProfileCardTO );
}
