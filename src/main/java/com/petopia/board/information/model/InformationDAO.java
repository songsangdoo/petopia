package com.petopia.board.information.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.information.mapper.InformationMapper;

@Repository
@MapperScan("com.petopia.board.information.mapper")
public class InformationDAO {

	@Autowired
	private InformationMapper mapper;
	
	public List<InformationTO> latestList(){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.latestList();
		
		return datas;
	}
	
	public List<InformationTO> subjectLatestList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.subjectLatestList(to);
		
		return datas;
	}
	
	public List<InformationTO> writerLatestList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.writerLatestList(to);
		
		return datas;
	}
	
	public List<InformationTO> contentLatestList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.contentLatestList(to);
		
		return datas;
	}
	
	public List<InformationTO> recList(){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.recList();
		
		return datas;
	}
	
	public List<InformationTO> subjectRecList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.subjectRecList(to);
		
		return datas;
	}
	
	public List<InformationTO> writerRecList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.writerRecList(to);
		
		return datas;
	}
	
	public List<InformationTO> contentRecList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.contentRecList(to);
		
		return datas;
	}
	
	public List<InformationTO> cmtList(){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.cmtList();
		
		return datas;
	}
	public List<InformationTO> subjectCmtList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.subjectCmtList(to);
		
		return datas;
	}
	public List<InformationTO> writerCmtList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.writerCmtList(to);
		
		return datas;
	}
	public List<InformationTO> contentCmtList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.contentCmtList(to);
		
		return datas;
	}
	
	public List<InformationTO> hitList(){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.hitList();
		
		return datas;
	}
	
	public List<InformationTO> subjectHitList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.subjectHitList(to);
		
		return datas;
	}
	
	public List<InformationTO> writerHitList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.writerHitList(to);
		
		return datas;
	}
	
	public List<InformationTO> contentHitList(InformationTO to){
		List<InformationTO> datas = new ArrayList<>();
		
		datas = mapper.contentHitList(to);
		
		return datas;
	}
	
	public int writeOk(InformationTO to) {
		int flag = 1;
		
		int result = mapper.write(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public InformationTO view(InformationTO to) {
		InformationTO data = new InformationTO();
		
		data = mapper.view(to);
		
		return data;
	}
	
	public int upHit(InformationTO to) {
		int flag = 1;
		
		int result = mapper.upHit(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(InformationTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(InformationTO to) {
		int flag = 1;
		
		InformationTO data = mapper.delete(to);
		
		File file = new File("C:\\java\\boot-workspace\\Petopia\\src\\main\\webapp\\upload\\" + data.getInfo_rep_img_path());
		
		if(!file.delete()) {
			System.out.println("파일 삭제 실패");
			return flag;
		}else {
			System.out.println("파일 삭제 성공");
		}
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public InformationTO modify(InformationTO to) {
		InformationTO data = new InformationTO();
		
		data = mapper.modify(to);
		
		return data;
	}
	
	public int modifyOk(InformationTO to) {
		int flag = 1;
		
		int result = mapper.modifyOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int modifyOkNoRep(InformationTO to) {
		int flag = 1;
		
		int result = mapper.modifyOkNoRep(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public List<InformationTO> mainList(){
		List<InformationTO> datas = mapper.mainList();
		
		return datas;
	}
}
