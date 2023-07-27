package com.petopia.pointshop.model;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.pointshop.mapper.PointShopMapperInter;

@Repository
@MapperScan( basePackages = { "com.petopia.pointshop.mapper" } )
public class InventoryDAO {

	@Autowired
	private PointShopMapperInter pointShopMapperInter;
	
	public ArrayList<InventoryTO> inventoryCheck( String m_seq ) {
		ArrayList<InventoryTO> inventoryChecks = (ArrayList<InventoryTO>)pointShopMapperInter.inventoryCheck( m_seq );
		return inventoryChecks;
	}
	
	public ArrayList<InventoryTO> skinCheck( String m_seq ) {
		ArrayList<InventoryTO> skinCheck = (ArrayList<InventoryTO>)pointShopMapperInter.skinInvenCheck( m_seq );
		return skinCheck;
	}
	
	public ArrayList<InventoryTO> badgeCheck( String m_seq ) {
		ArrayList<InventoryTO> badgeCheck = (ArrayList<InventoryTO>)pointShopMapperInter.badgeInvenCheck( m_seq );
		return badgeCheck;
	}
	
	public ArrayList<String> myBadgeSeqList( String m_seq ) {
		ArrayList<String> myBadgeSeqList = (ArrayList<String>)pointShopMapperInter.myBadgeSeqList( m_seq );
		return myBadgeSeqList;
	}
	
	public String mySkinSeq( String m_seq ) {
		String mySkinSeq = pointShopMapperInter.mySkinSeq( m_seq );
		return mySkinSeq;
	}
}
