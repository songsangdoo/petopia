package com.petopia.board.tip.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.tip.mapper.TipBoardCommentMapper;


@Repository
@MapperScan("com.petopia.board.free.mapper")
public class TipBoardCommentDAO {

	@Autowired
	private TipBoardCommentMapper mapper;
	
	public List<TipBoardCommentTO> latestList(TipBoardCommentTO to){
		List<TipBoardCommentTO> datas = new ArrayList<>();
		
		datas = mapper.latestList(to);
		
		return datas;
	}
	
	public List<TipBoardCommentTO> recList(TipBoardCommentTO to){
		List<TipBoardCommentTO> datas = new ArrayList<>();
		
		datas = mapper.recList(to);
		
		return datas;
	}
	
	public int writeOk(TipBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.wrightOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(TipBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;	
		}
		
		return flag;
	}
	
	public int upNoRec(TipBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.upNoRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(TipBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(TipBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAllOk(TipBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteAllOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}

}