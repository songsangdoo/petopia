package com.petopia.board.album.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.album.mapper.AlbumCommentMapper;

@Repository
@MapperScan("com.petopia.board.album.mapper")
public class AlbumCommentDAO {

	@Autowired
	private AlbumCommentMapper mapper;
	
	public List<AlbumCommentTO> latestList(AlbumCommentTO to){
		List<AlbumCommentTO> datas = new ArrayList<>();
		
		datas = mapper.latestList(to);
		
		return datas;
	}
	
	public List<AlbumCommentTO> recList(AlbumCommentTO to){
		List<AlbumCommentTO> datas = new ArrayList<>();
		
		datas = mapper.recList(to);
		
		return datas;
	}
	
	public int writeOk(AlbumCommentTO to) {
		int flag = 1;
		
		int result = mapper.wrightOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int upRec(AlbumCommentTO to) {
		int flag = 1;
		
		int result = mapper.upRec(to);
		
		if(result == 1) {
			flag = 0;	
		}
		
		return flag;
	}
	
	public int upNoRec(AlbumCommentTO to) {
		int flag = 1;
		
		int result = mapper.upNoRec(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteOk(AlbumCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int adminDeleteOk(AlbumCommentTO to) {
		int flag = 1;
		
		int result = mapper.adminDeleteOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	public int deleteAllOk(AlbumCommentTO to) {
		int flag = 1;
		
		int result = mapper.deleteAllOk(to);
		
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}

}
