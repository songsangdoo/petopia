package com.petopia.mypage.model;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.model.MemberTO;
import com.petopia.mypage.mapper.MypageMapperInter;

@Repository
@MapperScan(basePackages = {"com.petopia.mypage.mapper"})
public class MypageDAO {
	@Autowired
	private MypageMapperInter mapper; 
	
	public MyPageTO getViewInfo(String seq) {
		
		MyPageTO to = mapper.getMypageInfo(seq);
		
		return to;
	}
	
	public int setModifyInfo( String seq, MemberTO memberTO ) {
		
		int flag = mapper.setModifyInfo( seq, memberTO );
		return flag;
	}
	
	public int setPasswordModify( String m_seq, String m_password ) {
		
		int flag = mapper.setPasswordModify( m_seq, m_password );
		return flag;
	}
	
	public int delMember(String m_seq, String m_password) {
		int flag = mapper.delMember(m_seq, m_password);
		return flag;
	}
	
	public int badgeAllNoequip( String m_seq ) {
		
		int flag = mapper.badgeAllNoequip( m_seq );
		
		return flag;
	}
	
	public int skinAllNoequip( String m_seq ) {
		
		int flag = mapper.skinAllNoequip( m_seq );
		
		return flag;
	}
	
	public int equipBadge( String m_seq, String ps_seq ) {
		
		int flag = mapper.equipBadge( m_seq, ps_seq );
		
		return flag;
    }
	
	public int equipSkin( String m_seq, String ps_seq ) {
		
		int flag = mapper.equipSkin( m_seq, ps_seq );
		
		return flag;
	}

}
