package com.petopia.mypage.model;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.mypage.mapper.MyPagePetMapperInter;
import com.petopia.mypage.mapper.MyPageProfileCardMapperInter;

@Repository
@MapperScan( basePackages = { "com.petopia.mypage.mapper" } )
public class MypageProfileCardDAO {
	
	@Autowired
	private MyPageProfileCardMapperInter profileCardMapper;
	
	public MypageProfileCardTO profileCardInfoCheck( String m_seq ) {

		MypageProfileCardTO profileCardInfo = profileCardMapper.profileCardInfo( m_seq );
		return profileCardInfo;
	}
	
	public String searchDeleteFile( String m_seq ) {
		
		String searchDeleteFile = profileCardMapper.searchDeleteFile( m_seq );
		return searchDeleteFile;
	}
	
	public int updateCardInfo( MypageProfileCardTO mypageProfileCardTO ) {
		
		int flag = 0;
		
		flag = profileCardMapper.updateCardInfo( mypageProfileCardTO );
		return flag;
	}
}
