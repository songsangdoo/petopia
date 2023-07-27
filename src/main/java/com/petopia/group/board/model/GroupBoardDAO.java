package com.petopia.group.board.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.group.board.mapper.GroupBoardMapper;


@Repository
@MapperScan("com.petopia.group.board.mapper")
public class GroupBoardDAO {
	

	@Autowired
	private GroupBoardMapper mapper;
	
	public List<GroupBoardTO> latestList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.latestList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> subjectLatestList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectLatestList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> writerLatestList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerLatestList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> contentLatestList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentLatestList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> recList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.recList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> subjectRecList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectRecList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> writerRecList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerRecList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> contentRecList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentRecList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> cmtList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.cmtList(to);
		
		return datas;
	}
	public List<GroupBoardTO> subjectCmtList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectCmtList(to);
		
		return datas;
	}
	public List<GroupBoardTO> writerCmtList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerCmtList(to);
		
		return datas;
	}
	public List<GroupBoardTO> contentCmtList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentCmtList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> hitList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.hitList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> subjectHitList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectHitList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> writerHitList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerHitList(to);
		
		return datas;
	}
	
	public List<GroupBoardTO> contentHitList(GroupBoardTO to){
		List<GroupBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentHitList(to);
		
		return datas;
	}
	
	public int writeOk(GroupBoardTO to) {
		int flag = 1;
		
		int result = mapper.write(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public GroupBoardTO view(GroupBoardTO to) {
		GroupBoardTO data = new GroupBoardTO();
		
		data = mapper.view(to);
		
		return data;
	}
	
	public int upHit(GroupBoardTO to) {
		int flag = 1;
		
		int result = mapper.upHit(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upCmt(GroupBoardTO to) {
		int flag = 1;
		
		int result = mapper.upCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int downCmt(GroupBoardTO to) {
		int flag = 1;
		
		int result = mapper.downCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(GroupBoardTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(GroupBoardTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public GroupBoardTO modify(GroupBoardTO to) {
		GroupBoardTO data = new GroupBoardTO();
		
		data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(GroupBoardTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
}
