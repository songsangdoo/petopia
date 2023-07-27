 package com.petopia.group.model;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.group.mapper.GroupMapperInter;



@Repository
@MapperScan(basePackages = {"com.petopia.group.mapper"})
public class JoinGroupDAO {
	
	@Autowired
	private GroupMapperInter mapper;
	
	public int joinGroupWriteOk(JoinGroupTO jto,GroupTO gto) {
		
		int flag = 1;
		int result = mapper.joinGroupWriteOk(jto);
		
		if(result == 1) {
			flag = 0;
		}
		System.out.println(flag);
		return flag;
	}
	
	public int groupProduceAdmin(JoinGroupTO to) {
		int flag = 1;
		int result = mapper.groupProduceAdmin(to);
		if(result == 1) {
			flag = 0;
		}
		System.out.println(flag);
		return flag;
	}
	
	public Boolean joinCheck(JoinGroupTO to) {
		Boolean result = mapper.joinCheck(to);
		return result;
	}
	
	
}
