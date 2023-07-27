package com.petopia.board.recommend.model;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.recommend.mapper.RecCmtCheckMapper;

@Repository
@MapperScan( "com.petopia.board.recommend.mapper" )
public class RecCmtCheckDAO {

	@Autowired
	private RecCmtCheckMapper mapper;
	
	public RecCmtCheckTO check(RecCmtCheckTO to) {
		RecCmtCheckTO rec_cmt_check = mapper.check(to);
		
		return rec_cmt_check;
	}
	
	public int insert(RecCmtCheckTO to) {
		int flag = 1;
		
		int result = mapper.insert(to);
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAll(RecCmtCheckTO to) {
		int flag = 1;
		
		int result = mapper.deleteAll(to);
		
		if(result != 0) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int postDeleteAll(RecCmtCheckTO to) {
		int flag = 1;
		
		int result = mapper.deleteAll(to);
		
		if(result != 0) {
			flag = 0;
		}
		
		return flag;
	}
}
