package com.petopia.pointshop.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Select;

import com.petopia.model.MemberTO;
import com.petopia.pointshop.model.PointShopTO;

public interface MemberPointMapperInter {
	
	@Select( "select m_seq, m_nickname, m_point from member where m_seq=#{m_seq}" )
	public ArrayList<MemberTO> memberPoint();
	
}
