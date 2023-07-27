package com.petopia.pointshop.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.pointshop.model.GradeTO;
import com.petopia.pointshop.model.InventoryTO;
import com.petopia.pointshop.model.PointShopTO;

public interface PointShopMapperInter {

	@Select( "select grade_cnt, grade_img, grade_point from grade where grade_seq = #{userGrade}" )
	public GradeTO gradeCheck( String userGrade );
	
	@Update( "update member set m_point = #{memberPoint} where m_seq= #{m_seq}" )
	public int itemBuy( String memberPoint, String m_seq );
	
	@Select( "select ps_seq, ps_cate, ps_name, ps_price, ps_content, ps_img, ps_dt from pointshop order by ps_seq desc" )
	public ArrayList<PointShopTO> itemList();
	
	@Select( "select ps_seq, ps_cate, ps_name, ps_price, ps_content, ps_img, ps_dt from pointshop where ps_cate=0 order by ps_seq desc" )
	public ArrayList<PointShopTO> badgeList();
	
	@Select( "select ps_seq, ps_cate, ps_name, ps_price, ps_content, ps_img, ps_dt from pointshop where ps_cate=1 order by ps_seq desc" )
	public ArrayList<PointShopTO> skinList();
	
	@Select( "select ps_seq, ps_cate, ps_name, ps_price, ps_content, ps_img, ps_dt from pointshop where ps_seq = #{ps_seq}" )
	public PointShopTO skinCheck( String ps_seq );
	
	@Select( "select ps_cate from pointshop where ps_seq = #{ps_seq}" )
	public String itemCheck( String ps_seq );
	
	@Insert( "insert into inventory values ( #{m_seq}, #{ps_seq}, default, default, #{ps_cate} )" )
	public int itemAdd( String m_seq, String ps_seq, String ps_cate );
	
	@Insert( "insert into pointshop values ( 0, #{ps_cate}, #{ps_name}, #{ps_price}, #{ps_content}, #{ps_img}, default )" )
	public int adminAddItem( PointShopTO pointShopTO );
	
	@Select( "select m_seq, ps_seq, in_dt, in_use from inventory where m_seq = #{m_seq}" )
	public ArrayList<InventoryTO> inventoryCheck( String m_seq );
	
	@Select( "select m_seq, ps_seq, in_dt, in_use, ps_cate from inventory where m_seq = #{m_seq} and ps_cate=1" )
	public ArrayList<InventoryTO> skinInvenCheck( String m_seq );
	
	@Select( "select m_seq, ps_seq, in_dt, in_use, ps_cate from inventory where m_seq = #{m_seq} and ps_cate=0" )
	public ArrayList<InventoryTO> badgeInvenCheck( String m_seq );
	
	@Select( "select ps_seq, ps_cate, ps_name, ps_price, ps_content, ps_img, ps_dt from pointshop where ps_name like CONCAT('%', #{searchItem}, '%') order by ps_seq desc" )
	public ArrayList<PointShopTO> itemSearch( String searchItem );
	
	@Select( "select ps_seq from inventory where m_seq = #{m_seq} and in_use = 'Y' and ps_cate = 0" )
	public ArrayList<String> myBadgeSeqList( String m_seq );
	
	@Select( "select ps_seq from inventory where m_seq = #{m_seq} and in_use = 'Y' and ps_cate = 1" )
	public String mySkinSeq( String m_seq );
	
	@Select( "select ps_img from pointshop where ps_seq = #{ps_seq}" )
	public String myBadgeImg( String ps_seq );
	
	@Select( "select ps_img from pointshop where ps_seq = #{ps_seq}" )
	public String mySkinImg( String ps_seq );
	
	// question_view
	@Select( "select * from grade" )
	public List<GradeTO> question_gradeCheck();
	
}
