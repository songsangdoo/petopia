package com.petopia.board.free.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.free.mapper.FreeBoardCommentMapper;

@Repository
@MapperScan("com.petopia.board.free.mapper")
public class FreeBoardCommentDAO {

	@Autowired
	private FreeBoardCommentMapper mapper;
	
	public List<FreeBoardCommentTO> latestList(FreeBoardCommentTO to){
		List<FreeBoardCommentTO> datas = new ArrayList<>();
		
		datas = mapper.latestList(to);
		
		return datas;
	}
	
	public List<FreeBoardCommentTO> recList(FreeBoardCommentTO to){
		List<FreeBoardCommentTO> datas = new ArrayList<>();
		
		datas = mapper.recList(to);
		
		return datas;
	}
	
	public int writeOk(FreeBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.wrightOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(FreeBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;	
		}
		
		return flag;
	}
	
	public int upNoRec(FreeBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.upNoRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(FreeBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(FreeBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAllOk(FreeBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteAllOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}

}
