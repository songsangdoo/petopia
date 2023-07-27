package com.petopia.mypage.model;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.mypage.mapper.MyPagePetMapperInter;

@Repository
@MapperScan(basePackages = {"com.petopia.mypage.mapper"})
public class MypagePetDAO {
	
	@Autowired
	private MyPagePetMapperInter mapper;
	
	public int addPetInfo(MyPagePetTO to) {
		int flag = 1;
		
		mapper.addPetInfo(to);
		
		return flag;
	}
	
	public ArrayList<MyPagePetTO> getPetList(String m_seq) {
		ArrayList<MyPagePetTO> lists = new ArrayList<>();
		lists = mapper.getPetList(m_seq);
		return lists;
	}
	
	public int initPetInfoDelete(String p_seq) {
		int flag = 0;
		
		flag = mapper.initPetInfoDelete(p_seq);
		return flag;
	}
	
	public String getOldImgName(String p_seq) {
		String oldImgName = mapper.getOldImgName(p_seq);
		return oldImgName;
	}
	
	public String getCountPet(String m_seq) {
		String getCountPet = mapper.getCountPet(m_seq);
		return getCountPet;
	}
	
	public int allNoSelect( String m_seq ) {
		
		int flag = mapper.allNoSelect( m_seq );
		
		return flag;
	}
	
	public int petSelect( String p_seq ) {
		
		int flag = mapper.petSelect( p_seq );
		
		return flag;
	}
	
	public MyPagePetTO equipCardPet( String p_seq ) {
		
		MyPagePetTO petTO = mapper.equipCardPet( p_seq );
		
		return petTO;
	}
}
