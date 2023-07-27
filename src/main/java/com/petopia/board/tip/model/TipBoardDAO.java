package com.petopia.board.tip.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.tip.mapper.TipBoardMapper;


@Repository
@MapperScan("com.petopia.board.tip.mapper")
public class TipBoardDAO {
	
	@Autowired
	private TipBoardMapper mapper;
	
	public List<TipBoardTO> latestList(){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.latestList();
		
		return datas;
	}
	
	public List<TipBoardTO> subjectLatestList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectLatestList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> writerLatestList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerLatestList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> contentLatestList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentLatestList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> recList(){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.recList();
		
		return datas;
	}
	
	public List<TipBoardTO> subjectRecList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectRecList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> writerRecList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerRecList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> contentRecList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentRecList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> cmtList(){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.cmtList();
		
		return datas;
	}
	public List<TipBoardTO> subjectCmtList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectCmtList(to);
		
		return datas;
	}
	public List<TipBoardTO> writerCmtList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerCmtList(to);
		
		return datas;
	}
	public List<TipBoardTO> contentCmtList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentCmtList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> hitList(){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.hitList();
		
		return datas;
	}
	
	public List<TipBoardTO> subjectHitList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.subjectHitList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> writerHitList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.writerHitList(to);
		
		return datas;
	}
	
	public List<TipBoardTO> contentHitList(TipBoardTO to){
		List<TipBoardTO> datas = new ArrayList<>();
		
		datas = mapper.contentHitList(to);
		
		return datas;
	}
	
	public int writeOk(TipBoardTO to) {
		int flag = 1;
		
		int result = mapper.write(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public TipBoardTO view(TipBoardTO to) {
		TipBoardTO data = new TipBoardTO();
		
		data = mapper.view(to);
		
		return data;
	}
	
	public int upHit(TipBoardTO to) {
		int flag = 1;
		
		int result = mapper.upHit(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upCmt(TipBoardTO to) {
		int flag = 1;
		
		int result = mapper.upCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int downCmt(TipBoardTO to) {
		int flag = 1;
		
		int result = mapper.downCmt(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(TipBoardTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(TipBoardTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(TipBoardTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public TipBoardTO modify(TipBoardTO to) {
		TipBoardTO data = new TipBoardTO();
		
		data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(TipBoardTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
}
