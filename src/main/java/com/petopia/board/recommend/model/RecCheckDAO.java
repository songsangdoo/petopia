package com.petopia.board.recommend.model;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.recommend.mapper.RecCheckMapper;

@Repository
@MapperScan( "com.petopia.board.recommend.mapper" )
public class RecCheckDAO {

	@Autowired
	private RecCheckMapper mapper;
	
	public RecCheckTO check(RecCheckTO to) {
		RecCheckTO rec_check = mapper.check(to);
		
		return rec_check;
	}
	
	public int insert(RecCheckTO to) {
		int flag = 1;
		
		int result = mapper.insert(to);
		System.out.println(result + "댓글 쓰기");
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAll(RecCheckTO to) {
		int flag = 1;
		
		int result = mapper.delete(to);
		System.out.println(result + "댓글 삭제 check");
		if(result != 0) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAns(RecCheckTO to) {
		int flag = 1; 
		
		int result = mapper.deleteAns(to);
		
		if(result == 0) {
			flag = 0;
		}
		
		return flag;
	}
	
	// 글쓰기 3회까지 포인트 제공
	public RecCheckTO wCount(RecCheckTO to) {
		RecCheckTO data = mapper.wCount(to);
		
		return data;
	}
}
