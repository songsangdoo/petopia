package com.petopia.group.model;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.group.mapper.GroupMapperInter;

import lombok.Getter;
import lombok.Setter;


@Repository
@MapperScan(basePackages = {"com.petopia.group.mapper"})
public class GroupHashDAO{
	
	@Autowired
	private GroupMapperInter mapper;

	public int groupHashOk(GroupHashTO to) {
		
		int flag = 1;
		int result = mapper.groupHashOk(to);
		if(result == 1) {
			flag = 0;
		}
		System.out.println(flag);
		return flag;
	}
	
	public ArrayList<GroupHashTO> groupHashView(GroupHashTO to){
		ArrayList<GroupHashTO> grouplists = (ArrayList)mapper.groupHashView(to);
		return grouplists;
	}
		
	public ArrayList<GroupHashTO> HashList(GroupHashTO to){
		ArrayList<GroupHashTO> grouplists = (ArrayList)mapper.HashList(to);
		return grouplists;
	}
}
