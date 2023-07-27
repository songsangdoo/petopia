package com.petopia.group.model;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.group.mapper.GroupMapperInter;


@Repository
@MapperScan(basePackages = {"com.petopia.group.mapper"})
public class MyGroupDAO {
	@Autowired
	private GroupMapperInter mapper;
	
	public ArrayList<MyGroupTO> mygroupList(MyGroupTO to){
		ArrayList<MyGroupTO> mygrouplists = (ArrayList)mapper.mygroupList(to);
		return mygrouplists;
	}
	
	public ArrayList<MyGroupTO> mygroupListSidebar(MyGroupTO to){
		ArrayList<MyGroupTO> mygrouplists = (ArrayList)mapper.mygroupListSidebar(to);
		return mygrouplists;
	}
	
	public ArrayList<MyGroupTO> mygroupListAdmin(){
		ArrayList<MyGroupTO> mygrouplists = (ArrayList)mapper.mygroupListAdmin();
		return mygrouplists;
	}
	
	public ArrayList<MyGroupTO> mygroupMangeList(MyGroupTO to){
		ArrayList<MyGroupTO> mygrouplists = (ArrayList)mapper.mygroupManageList(to);
		return mygrouplists;
	}
	
	public int mygroupManageJoin(JoinGroupTO jto) {
		
		int flag = 1;
		int result = mapper.mygroupManageJoin(jto);
		
		if(result == 1) {
			flag = 0;
		}else {
			flag = 1;
		}
		return flag;
	}

	public int mygroupManageDelete(JoinGroupTO jto) {
	
		int flag = 1;
		int result = mapper.mygroupManageDelete(jto);
		
		if(result == 1) {
			flag = 0;
		}else {
			flag = 1;
		}
		return flag;
	}
	
	public int mygroupManageAdc(JoinGroupTO jto) {
		
		int flag = 1;
		int result = mapper.mygroupManageAdc(jto);
		
		if(result == 1) {
			flag = 0;
		}else {
			flag = 1;
		}
		return flag;
	}
	
public int mygroupManageDem(JoinGroupTO jto) {
		
		int flag = 1;
		int result = mapper.mygroupManageDem(jto);
		
		if(result == 1) {
			flag = 0;
		}else {
			flag = 1;
		}
		return flag;
	}
	
	
}
