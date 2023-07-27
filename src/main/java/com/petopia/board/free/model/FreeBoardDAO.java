package com.petopia.board.free.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.free.mapper.FreeBoardMapper;


@Repository
@MapperScan("com.petopia.board.free.mapper")
public class FreeBoardDAO {
	
	@Autowired
	private FreeBoardMapper mapper;
	
	public List<FreeBoardTO> latestList(){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.latestList();
		
		return datas;
	}
	
	public List<FreeBoardTO> subjectLatestList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectLatestList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> writerLatestList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerLatestList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> contentLatestList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentLatestList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> recList(){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.recList();
		
		return datas;
	}
	
	public List<FreeBoardTO> subjectRecList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectRecList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> writerRecList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerRecList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> contentRecList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentRecList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> cmtList(){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.cmtList();
		
		return datas;
	}
	public List<FreeBoardTO> subjectCmtList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectCmtList(to);
		
		return datas;
	}
	public List<FreeBoardTO> writerCmtList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerCmtList(to);
		
		return datas;
	}
	public List<FreeBoardTO> contentCmtList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentCmtList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> hitList(){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.hitList();
		
		return datas;
	}
	
	public List<FreeBoardTO> subjectHitList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectHitList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> writerHitList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerHitList(to);
		
		return datas;
	}
	
	public List<FreeBoardTO> contentHitList(FreeBoardTO to){
		List<FreeBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentHitList(to);
		
		return datas;
	}
	
	public int writeOk(FreeBoardTO to) {
		int flag = 1;
		
		int result = mapper.write(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public FreeBoardTO view(FreeBoardTO to) {
		FreeBoardTO data = new FreeBoardTO();
		
		data = mapper.view(to);
		
		return data;
	}
	
	public int upHit(FreeBoardTO to) {
		int flag = 1;
		
		int result = mapper.upHit(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upCmt(FreeBoardTO to) {
		int flag = 1;
		
		int result = mapper.upCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int downCmt(FreeBoardTO to) {
		int flag = 1;
		
		int result = mapper.downCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(FreeBoardTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(FreeBoardTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(FreeBoardTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public FreeBoardTO modify(FreeBoardTO to) {
		FreeBoardTO data = new FreeBoardTO();
		
		data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(FreeBoardTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
}
