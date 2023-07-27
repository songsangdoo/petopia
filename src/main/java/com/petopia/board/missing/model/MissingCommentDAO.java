package com.petopia.board.missing.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.missing.mapper.MissingCommentMapper;

@Repository
@MapperScan("com.petopia.board.missing.mapper")
public class MissingCommentDAO {

	@Autowired
	private MissingCommentMapper mapper;
	
	public List<MissingCommentTO> latestList(MissingCommentTO to){
		List<MissingCommentTO> datas = new ArrayList<>();
		
		datas = mapper.latestList(to);
		
		return datas;
	}
	
	public int writeOk(MissingCommentTO to) {
		int flag = 1;
		
		int result = mapper.wrightOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(MissingCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(MissingCommentTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAllOk(MissingCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteAllOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
}
