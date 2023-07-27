package com.petopia.pointshop.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.model.MemberTO;
import com.petopia.mypage.model.MypageProfileCardDAO;
import com.petopia.mypage.model.MypageProfileCardTO;
import com.petopia.pointshop.model.GradeDAO;
import com.petopia.pointshop.model.GradeTO;
import com.petopia.pointshop.model.InventoryDAO;
import com.petopia.pointshop.model.InventoryTO;
import com.petopia.pointshop.model.PointShopDAO;
import com.petopia.pointshop.model.PointShopTO;

@RestController
public class PointShopController {
	
	@Autowired
	private PointShopDAO pointShopDAO;
	
	@Autowired
	private GradeDAO gradeDAO;
	
	@Autowired
	private InventoryDAO inventoryDAO;
	
	@Autowired
	MypageProfileCardDAO mypageProfileCardDAO;
	
	@RequestMapping( "/point_shop_info.do" )
	public ModelAndView draw( HttpServletRequest request ) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "point_shop_views/point_shop_info" );
		return modelAndView;
	}
	
	@RequestMapping( "/point_shop_badge.do" )
	public ModelAndView badge( HttpServletRequest request ) {
		
		ArrayList<PointShopTO> badgeLists = pointShopDAO.badgeList();
		
		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		
		GradeTO gradeChecks = null;
		ArrayList<InventoryTO> inventoryChecks = null;
		String userGrade = null;
		String m_seq = null;
		
		if( userData != null ) {
			userGrade = userData.getGrade_seq();
			m_seq = userData.getM_seq();
			gradeChecks = gradeDAO.gradeCheck( userGrade );
			inventoryChecks = inventoryDAO.inventoryCheck( m_seq );
		}
		
		MypageProfileCardTO profileCardInfo = new MypageProfileCardTO();
		
		ArrayList<PointShopTO> skinPointShopList = new ArrayList<>();
		ArrayList<PointShopTO> badgePointShopList = new ArrayList<>();
		ArrayList<String> myBadgeImgList = new ArrayList<>();
		String mySkinImg = "";
		
		if( userData != null ) {
			profileCardInfo = mypageProfileCardDAO.profileCardInfoCheck( userData.getM_seq() );
			
			if( profileCardInfo == null ) {
				profileCardInfo = new MypageProfileCardTO();
				profileCardInfo.setPro_comment( "한 마디 글을 작성해 주세요." );
				profileCardInfo.setPro_img( "no_image.png" );
			}

			ArrayList<InventoryTO> userInvenSkin = inventoryDAO.skinCheck( userData.getM_seq() );
			ArrayList<InventoryTO> userInvenBadge = inventoryDAO.badgeCheck( userData.getM_seq() );
			
			skinPointShopList = new ArrayList<>();
			badgePointShopList = new ArrayList<>();
			
			if( userInvenSkin != null ) {
				for (int i = 0; i < userInvenSkin.size(); i++ ) {
					PointShopTO skinInfo = pointShopDAO.skinCheck( userInvenSkin.get(i).getPs_seq() );
					skinPointShopList.add( skinInfo );
				}
			}
			
			if( userInvenBadge != null ) {
				for (int i = 0; i < userInvenBadge.size(); i++ ) {
					PointShopTO badgeInfo = pointShopDAO.skinCheck( userInvenBadge.get(i).getPs_seq() );
					badgePointShopList.add( badgeInfo );
				}
			}
			
			
			ArrayList<String> myBadgeSeqList = inventoryDAO.myBadgeSeqList( userData.getM_seq() );
			myBadgeImgList = new ArrayList<>();
			
			String mySkinSeq = inventoryDAO.mySkinSeq( userData.getM_seq() );
			
			if( myBadgeSeqList != null ) {
				for (int i = 0; i < myBadgeSeqList.size(); i++ ) {
					String myBadgeImg = pointShopDAO.myBadgeImg( myBadgeSeqList.get(i).toString() );
					myBadgeImgList.add( myBadgeImg );
				}
			}
			
			if( mySkinSeq != null ) {
				mySkinImg = pointShopDAO.mySkinImg( mySkinSeq );
			}
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "point_shop_views/point_shop_badge" );
		modelAndView.addObject( "badgeLists", badgeLists );
		modelAndView.addObject( "gradeChecks", gradeChecks );
		modelAndView.addObject( "inventoryChecks", inventoryChecks );
		modelAndView.addObject( "profileCardInfo", profileCardInfo );
		modelAndView.addObject( "skinPointShopList", skinPointShopList );
		modelAndView.addObject( "badgePointShopList", badgePointShopList );
		modelAndView.addObject( "myBadgeImgList", myBadgeImgList );
		modelAndView.addObject( "mySkinImg", mySkinImg );
		return modelAndView;
	}
	
	@RequestMapping( "/point_shop_skin.do" )
	public ModelAndView skin( HttpServletRequest request ) {
		
		ArrayList<PointShopTO> skinLists = pointShopDAO.skinList();
		
		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		
		GradeTO gradeChecks = null;
		String userGrade = null;
		
		if( userData != null ) {
			userGrade = userData.getGrade_seq();
			gradeChecks = gradeDAO.gradeCheck( userGrade );
		}
		
		MypageProfileCardTO profileCardInfo = new MypageProfileCardTO();
		
		ArrayList<PointShopTO> skinPointShopList = new ArrayList<>();
		ArrayList<PointShopTO> badgePointShopList = new ArrayList<>();
		ArrayList<String> myBadgeImgList = new ArrayList<>();
		String mySkinImg = "";
		
		if( userData != null ) {
			
			profileCardInfo = mypageProfileCardDAO.profileCardInfoCheck( userData.getM_seq() );
			if( profileCardInfo == null ) {
				profileCardInfo = new MypageProfileCardTO();
				profileCardInfo.setPro_comment( "한 마디 글을 작성해 주세요." );
				profileCardInfo.setPro_img( "no_image.png" );
			}
			ArrayList<InventoryTO> userInvenSkin = inventoryDAO.skinCheck( userData.getM_seq() );
			ArrayList<InventoryTO> userInvenBadge = inventoryDAO.badgeCheck( userData.getM_seq() );
			
			skinPointShopList = new ArrayList<>();
			badgePointShopList = new ArrayList<>();
			
			if( userInvenSkin != null ) {
				for (int i = 0; i < userInvenSkin.size(); i++ ) {
					PointShopTO skinInfo = pointShopDAO.skinCheck( userInvenSkin.get(i).getPs_seq() );
					skinPointShopList.add( skinInfo );
				}
			}
			
			if( userInvenBadge != null ) {
				for (int i = 0; i < userInvenBadge.size(); i++ ) {
					PointShopTO badgeInfo = pointShopDAO.skinCheck( userInvenBadge.get(i).getPs_seq() );
					badgePointShopList.add( badgeInfo );
				}
			}
			
			
			ArrayList<String> myBadgeSeqList = inventoryDAO.myBadgeSeqList( userData.getM_seq() );
			myBadgeImgList = new ArrayList<>();
			
			String mySkinSeq = inventoryDAO.mySkinSeq( userData.getM_seq() );
			
			if( myBadgeSeqList != null ) {
				for (int i = 0; i < myBadgeSeqList.size(); i++ ) {
					String myBadgeImg = pointShopDAO.myBadgeImg( myBadgeSeqList.get(i).toString() );
					myBadgeImgList.add( myBadgeImg );
				}
			}
			
			if( mySkinSeq != null ) {
				mySkinImg = pointShopDAO.mySkinImg( mySkinSeq );
			}
			
		}
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "point_shop_views/point_shop_skin" );
		modelAndView.addObject( "skinLists", skinLists );
		modelAndView.addObject( "gradeChecks", gradeChecks );
		modelAndView.addObject( "profileCardInfo", profileCardInfo );
		modelAndView.addObject( "skinPointShopList", skinPointShopList );
		modelAndView.addObject( "badgePointShopList", badgePointShopList );
		modelAndView.addObject( "myBadgeImgList", myBadgeImgList );
		modelAndView.addObject( "mySkinImg", mySkinImg );
		return modelAndView;
	}
	
	@RequestMapping( "/skin_view.do" )
	public ModelAndView skinView( HttpServletRequest request ) {

		String skinSeq = request.getParameter( "ps_seq" );
		PointShopTO skinChecks = pointShopDAO.skinCheck( skinSeq );
		
		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		
		String m_seq = null;
		GradeTO gradeChecks = null;
		ArrayList<InventoryTO> inventoryChecks = null;
		
		if ( userData != null ) {
			String userGrade = userData.getGrade_seq();
			gradeChecks = gradeDAO.gradeCheck( userGrade );
			m_seq = userData.getM_seq();
			inventoryChecks = inventoryDAO.inventoryCheck( m_seq );
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "point_shop_views/skin_view" );
		modelAndView.addObject( "gradeChecks", gradeChecks );
		modelAndView.addObject( "skinChecks", skinChecks );
		modelAndView.addObject( "inventoryChecks", inventoryChecks );
		return modelAndView;
	}
	
	@RequestMapping( "/inventory_preview.do" )
	public ModelAndView inventoryPreview( HttpServletRequest request ) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "point_shop_views/inventory_preview" );
		return modelAndView;
	}
	
	@RequestMapping( "/item_reg.do" )
	public ModelAndView itemRegistration( HttpServletRequest request ) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "point_shop_views/item_reg" );
		return modelAndView;
	}
	
	@RequestMapping( "/test.do" )
	public ModelAndView test( HttpServletRequest request ) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "point_shop_views/test" );
		return modelAndView;
	}
	
	@RequestMapping( "/item_buy.do" )
	public ModelAndView itemBuy( HttpServletRequest request ) {

		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		
		String memberPoint = request.getParameter( "memberPoint" );
		String ps_seq = request.getParameter( "ps_seq" );
		String m_seq = userData.getM_seq();
		
		String itemCategory = pointShopDAO.itemCheck( ps_seq );
		
		System.out.println( "아이템 카테고리 : " + itemCategory );
		
		pointShopDAO.itemBuy( memberPoint, m_seq );
		pointShopDAO.itemAdd( m_seq, ps_seq, itemCategory );
		
		userData.setM_point( memberPoint );
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "point_shop_views/item_buy" );
		modelAndView.addObject( "memberPoint", memberPoint );
		modelAndView.addObject( "itemCategory", itemCategory );
		modelAndView.addObject( "ps_seq", ps_seq );
		return modelAndView;
	}
	
}
