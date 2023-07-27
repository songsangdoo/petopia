 package com.petopia.group.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.admin.model.AdminTO;
import com.petopia.group.board.model.GroupBoardCommentTO;
import com.petopia.group.board.model.GroupBoardFileTO;
import com.petopia.group.board.model.GroupBoardTO;
import com.petopia.group.mapper.GroupMapperInter;


@Repository
@MapperScan(basePackages = {"com.petopia.group.mapper"})
public class GroupDAO {
	
	@Autowired
	private GroupMapperInter mapper;
	
	
	public ArrayList<GroupTO> groupList(){
		ArrayList<GroupTO> grouplists = (ArrayList)mapper.groupList();
		return grouplists;
	}
	
	public ArrayList<GroupTO> masterGroupList(){
		ArrayList<GroupTO> grouplists = (ArrayList)mapper.mastergroupList();
		return grouplists;
	}
	
	public int groupWriteOk(GroupTO to) {
		
		int flag = 1;
		int result = mapper.groupWriteOk(to);
		if(result == 1) {
			flag = 0;
		}
		System.out.println(flag);
		return flag;
	}
	

	public GroupTO groupViewLogin(GroupTO to) {
		to = mapper.groupViewLogin(to);
		return to;
	}
	public GroupTO groupViewLogout(GroupTO to) {
		to = mapper.groupViewLogout(to);
		return to;
	}
	
	public int groupProduce(GroupTO gto) {
		
		int flag = 1;
		int result = mapper.groupProduce(gto);
		
		if(result == 1) {
			flag = 0;
		}else {
			flag = 1;
		}
		return flag;
	}
	
	
	
	public GroupTO findAdminSeq(GroupTO gto) {
		gto = mapper.findAdminSeq(gto);
		return gto;
	}
	
	public int groupProduceDelete(GroupTO to) {
		int flag = 1;
		int result = mapper.groupProduceDelete(to);
		
		if(result == 1) {
			flag = 0;
		}
		return flag;
	}
	
	public GroupTO findAdmin(GroupTO to) {
		to = mapper.findAdmin(to);
		return to;
	}
	
	public int upInwon(GroupTO to) {
		
		int flag = 1;
		int result = mapper.upInwon(to);
		if(result == 1) {
			flag = 0;
		}
		System.out.println(flag);
		return flag;
	}
	public int groupRecentSeq() {
		int seq = mapper.groupRecentSeq();
		return seq;
	}
	
	public ArrayList<GroupTO> subjectHashList(GroupTO to){
		ArrayList<GroupTO> grouplists = (ArrayList)mapper.subjectHashList(to);
		return grouplists;
	}
	
	public ArrayList<GroupTO> contentHashList(GroupTO to){
		ArrayList<GroupTO> grouplists = (ArrayList)mapper.contentHashList(to);
		return grouplists;
	}
	
	public ArrayList<GroupTO> hashSearchList(GroupHashTO ghto){
		ArrayList<GroupHashTO> grouplists = (ArrayList)mapper.HashList(ghto);
		ArrayList<GroupTO> lists = new ArrayList<>();
		GroupTO gto = new GroupTO();
		for(GroupHashTO to : grouplists) {
			gto.setGR_SEQ(String.valueOf(to.getGR_SEQ()));
			System.out.println("\ndao에서의 hash value 검색 : "+ to.getGR_SEQ());
			lists.addAll((ArrayList<GroupTO>)mapper.hashSearchList(gto));
		}
		return lists;
	}
	
	public int groupModifyOk1(GroupTO to) {
		int flag1 = mapper.modifyOk(to);
		
		return flag1;
	}
	public int groupModifyOk2(String gr_seq) {
		GroupHashTO ghto = new GroupHashTO();
		ghto.setGR_SEQ(Integer.parseInt(gr_seq));
    	int flag = mapper.groupHashDelete(ghto);
		return flag;
	}
	
	public int groupDelete(String value) {
		GroupTO to = new GroupTO();
    	GroupHashTO ghto = new GroupHashTO();
    	GroupBoardCommentTO gcto = new GroupBoardCommentTO();
    	GroupBoardTO gbto = new GroupBoardTO();
    	GroupBoardFileTO gbfto = new GroupBoardFileTO();
    	JoinGroupTO jgto = new JoinGroupTO();
 
    	to.setGR_SEQ(value);
    	ghto.setGR_SEQ(Integer.parseInt(value));
    	gcto.setGR_SEQ(Integer.parseInt(value));
    	gbto.setGR_SEQ(Integer.parseInt(value));
    	gbfto.setGR_SEQ(Integer.parseInt(value));
    	jgto.setGR_SEQ(value);
    	
    	int flag1 = mapper.groupBoardCommentDelete(gcto);
    	int flag2 = mapper.groupDelete(to);
    	int flag3 = mapper.joinGroupDelete(jgto);
    	int flag4 = mapper.groupHashDelete(ghto);
    	int flag5 = mapper.groupBoardCommentDelete(gcto);
    	int flag6 = mapper.groupBoardDelete(gbto);
    	
    	System.out.println("flag1 : "+flag1+"flag2 : "+flag2+"flag3 : "+flag3+"flag4 : "+flag4+"flag5 : "+flag5+"flag6 : "+flag6);
		int flag =0;
		return flag;
	}
}
