package com.petopia.board.notice.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.notice.mapper.NoticeMapper;

@Repository
@MapperScan("com.petopia.board.notice.mapper")
public class NoticeDAO {

	@Autowired
	private NoticeMapper mapper;
	
	public List<NoticeTO> fixedList(){
		List<NoticeTO> datas = new ArrayList<>();
		
		datas = mapper.fixedList();
		
		return datas;
	}
	
	public List<NoticeTO> latestList(){
		List<NoticeTO> datas = new ArrayList<>();
		
		datas = mapper.latestList();
		
		return datas;
	}
	
	public List<NoticeTO> subjectLatestList(NoticeTO to){
		List<NoticeTO> datas = new ArrayList<>();
		
		datas = mapper.subjectLatestList(to);
		
		return datas;
	}
	
	public List<NoticeTO> contentLatestList(NoticeTO to){
		List<NoticeTO> datas = new ArrayList<>();
		
		datas = mapper.contentLatestList(to);
		
		return datas;
	}
	
	public List<NoticeTO> hitList(){
		List<NoticeTO> datas = new ArrayList<>();
		
		datas = mapper.hitList();
		
		return datas;
	}
	
	public List<NoticeTO> subjectHitList(NoticeTO to){
		List<NoticeTO> datas = new ArrayList<>();
		
		datas = mapper.subjectHitList(to);
		
		return datas;
	}
	
	public List<NoticeTO> contentHitList(NoticeTO to){
		List<NoticeTO> datas = new ArrayList<>();
		
		datas = mapper.contentHitList(to);
		
		return datas;
	}
	
	public int writeOk(NoticeTO to) {
		int flag = 1;
		
		int result = mapper.write(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public NoticeTO view(NoticeTO to) {
		NoticeTO data = new NoticeTO();
		
		data = mapper.view(to);
		
		return data;
	}
	
	public int upHit(NoticeTO to) {
		int flag = 1;
		
		int result = mapper.upHit(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(NoticeTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public NoticeTO modify(NoticeTO to) {
		NoticeTO data = new NoticeTO();
		
		data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(NoticeTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
}
