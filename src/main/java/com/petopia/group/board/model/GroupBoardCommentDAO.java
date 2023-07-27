package com.petopia.group.board.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.free.mapper.FreeBoardCommentMapper;
import com.petopia.group.board.mapper.GroupBoardCommentMapper;

@Repository
@MapperScan("com.petopia.group.board.mapper")
public class GroupBoardCommentDAO {

	@Autowired
	private GroupBoardCommentMapper mapper;
	
	public List<GroupBoardCommentTO> latestList(GroupBoardCommentTO to){
		List<GroupBoardCommentTO> datas = new ArrayList<>();
		
		datas = mapper.latestList(to);
		
		return datas;
	}
	
	public List<GroupBoardCommentTO> recList(GroupBoardCommentTO to){
		List<GroupBoardCommentTO> datas = new ArrayList<>();
		
		datas = mapper.recList(to);
		
		return datas;
	}
	
	public int writeOk(GroupBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.wrightOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(GroupBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;	
		}
		
		return flag;
	}
	
	public int upNoRec(GroupBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.upNoRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(GroupBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAllOk(GroupBoardCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteAllOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}

}
