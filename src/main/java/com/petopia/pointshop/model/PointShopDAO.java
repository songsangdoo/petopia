package com.petopia.pointshop.model;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.board.album.model.AlbumTO;
import com.petopia.pointshop.mapper.PointShopMapperInter;

@Repository
@MapperScan( basePackages = { "com.petopia.pointshop.mapper" } )
public class PointShopDAO {

	@Autowired
	private PointShopMapperInter pointShopMapperInter;
	
	public ArrayList<PointShopTO> itemList() {
		ArrayList<PointShopTO> itemLists = (ArrayList<PointShopTO>)pointShopMapperInter.itemList();
		return itemLists;
	}
	
	public ArrayList<PointShopTO> badgeList() {
		ArrayList<PointShopTO> badgeLists = (ArrayList<PointShopTO>)pointShopMapperInter.badgeList();
		return badgeLists;
	}
	
	public ArrayList<PointShopTO> skinList() {
		ArrayList<PointShopTO> skinLists = (ArrayList<PointShopTO>)pointShopMapperInter.skinList();
		return skinLists;
	}
	
	public PointShopTO skinCheck( String skinSeq ) {
		PointShopTO skinChecks = (PointShopTO)pointShopMapperInter.skinCheck( skinSeq );
		return skinChecks;
	}
	
	public String itemCheck( String ps_seq ) {
		String itemCategory = (String)pointShopMapperInter.itemCheck( ps_seq );
		return itemCategory;
	}
	
	public String myBadgeImg( String ps_seq ) {
		String myBadgeImg = (String)pointShopMapperInter.myBadgeImg( ps_seq );
		return myBadgeImg;
	}
	
	public String mySkinImg( String ps_seq ) {
		String mySkinImg = pointShopMapperInter.mySkinImg( ps_seq );
		return mySkinImg;
	}
	
	public ArrayList<PointShopTO> itemSearch( String searchItem ) {
		ArrayList<PointShopTO> itemSearchs = (ArrayList<PointShopTO>)pointShopMapperInter.itemSearch( searchItem );
		return itemSearchs;
	}
	
	public int itemBuy( String memberPoint, String m_seq ) {
		
		int flag = 1;
		int result = pointShopMapperInter.itemBuy( memberPoint, m_seq );

		if( result == 1 ) {
			flag = 0;
		}

		return flag;
	}
	
	public int itemAdd( String m_seq, String ps_seq, String category ) {
		
		int flag = 1;
		int result = pointShopMapperInter.itemAdd( m_seq, ps_seq, category );

		if( result == 1 ) {
			flag = 0;
		}

		return flag;
	}
	
	public int adminAddItem( PointShopTO pointshopTO ) {
		
		int flag = 1;
		int result = pointShopMapperInter.adminAddItem( pointshopTO );

		if( result == 1 ) {
			flag = 0;
		}

		return flag;
	}
}
