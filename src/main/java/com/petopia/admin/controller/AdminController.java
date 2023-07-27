package com.petopia.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.admin.model.AdminDAO;
import com.petopia.admin.model.AdminTO;
import com.petopia.pointshop.model.PointShopDAO;
import com.petopia.pointshop.model.PointShopTO;

@RestController
public class AdminController {
	
	@Autowired
	private AdminDAO dao;
	
	@Autowired
	private PointShopDAO pointShopDAO;
	
	@RequestMapping("/master_dashboard.do")
    public ModelAndView manage() {
    	ModelAndView modelAndView = new ModelAndView();
    	String petopia = "totalactive";
    	modelAndView.addObject("activeMenu", petopia);
    	modelAndView.setViewName("admin_views/dashboard");
    	return modelAndView;
    }
    
    @RequestMapping("/master_userboard.do")
    public ModelAndView manageUser(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView();
    	ArrayList<AdminTO> userList = new ArrayList<AdminTO>();
    	String filter = request.getParameter("filter");
    	String keyword = request.getParameter("keyword");
    	
    	if(filter == null || keyword == null) {
    		userList = dao.userData();
    	} else {
    		userList = dao.userData(filter, keyword);
    	}

    	String petopia = "useractive";
    	
    	modelAndView.addObject("userList", userList);
    	modelAndView.addObject("activeMenu", petopia);
    	modelAndView.setViewName("admin_views/dashboard_user");
    	return modelAndView;
    }
    
    @RequestMapping("/master_product.do")
    public ModelAndView manageProduct() {
    	ModelAndView modelAndView = new ModelAndView();
    	String petopia = "productactive";
    	modelAndView.addObject("activeMenu", petopia);
    	modelAndView.setViewName("admin_views/dashboard_product");
    	return modelAndView;
    }
    
    @RequestMapping("/master_pointshop.do")
    public ModelAndView managePointshop(HttpServletRequest request) {
    	
    	ModelAndView modelAndView = new ModelAndView();
    	String petopia = "pointshopactive";
    	String category = request.getParameter( "selectedValue" );
    	String searchItem = request.getParameter( "searchItem" );
    	
    	if( searchItem != null ) {
    		ArrayList<PointShopTO> itemLists = pointShopDAO.itemSearch( searchItem );
    		modelAndView.addObject("activeMenu", petopia );
    		modelAndView.setViewName("admin_views/dashboard_pointshop");
    		modelAndView.addObject( "itemLists", itemLists );
    	} else {
    		if ( category == null ) {
    			ArrayList<PointShopTO> itemLists = pointShopDAO.itemList();
    			modelAndView.addObject("activeMenu", petopia);
    			modelAndView.setViewName("admin_views/dashboard_pointshop");
    			modelAndView.addObject( "itemLists", itemLists );
    			
    		} else if ( category.equals( "0" ) ) {
    			ArrayList<PointShopTO> itemLists = pointShopDAO.badgeList();
    			modelAndView.addObject("activeMenu", petopia);
    			modelAndView.setViewName("admin_views/dashboard_pointshop");
    			modelAndView.addObject( "itemLists", itemLists );
    			modelAndView.addObject( "category", category );
    		} else if ( category.equals( "1" ) ) {
    			ArrayList<PointShopTO> itemLists = pointShopDAO.skinList();
    			modelAndView.addObject("activeMenu", petopia);
    			modelAndView.setViewName("admin_views/dashboard_pointshop");
    			modelAndView.addObject( "itemLists", itemLists );
    			modelAndView.addObject( "category", category );
    		} else {
    			ArrayList<PointShopTO> itemLists = pointShopDAO.itemList();
    			modelAndView.addObject("activeMenu", petopia);
    			modelAndView.setViewName("admin_views/dashboard_pointshop");
    			modelAndView.addObject( "itemLists", itemLists );
    		}
    	}
    	return modelAndView;
    }
    
    @RequestMapping("/master_pointshop_write_ok.do")
	public ModelAndView master_pointshop_write_ok( HttpServletRequest request, MultipartFile addItemUpload, MultipartFile addItemCardUpload ) {
		
    	String itemCategory = request.getParameter( "itemRadioButton" );
    	String itemName = request.getParameter( "addItemName" );
    	String itemContent = request.getParameter( "addItemContent" );
    	String itemPrice = request.getParameter( "addItemPrice" );

    	String fileCardNameResult = "";
    	
    	if( itemCategory.equals( "skin" ) ) {
    		itemCategory = "1";
    	} else {
    		itemCategory = "0";
    	}
    	
    	String fileUpload_ext = addItemUpload.getOriginalFilename().substring( addItemUpload.getOriginalFilename().lastIndexOf(".") );
		String fileName = addItemUpload.getOriginalFilename().substring(0, addItemUpload.getOriginalFilename().indexOf(fileUpload_ext));
		UUID uuidRepImg = UUID.randomUUID(); // 파일 이름 중복방지
		String fileNameResult = fileName + "_" + uuidRepImg.toString() + "_listSample" + fileUpload_ext;
		
		if( addItemCardUpload.getOriginalFilename().length() != 0 ) {
			String fileCardUpload_ext = addItemCardUpload.getOriginalFilename().substring( addItemCardUpload.getOriginalFilename().lastIndexOf(".") );
			fileCardNameResult = fileName + "_" + uuidRepImg.toString() + fileCardUpload_ext;			
			System.out.println( "프로필카드 이미지 : " + fileCardNameResult );
		}
		
		System.out.println( "리스트 이미지 : " + fileNameResult );
		System.out.println( "카테고리 : " + itemCategory );
		
		PointShopTO pointshopTO = new PointShopTO();
		pointshopTO.setPs_cate( itemCategory );
		pointshopTO.setPs_name( itemName );
		pointshopTO.setPs_content( itemContent );
		pointshopTO.setPs_price( itemPrice );
		pointshopTO.setPs_img( fileNameResult );
		
		int flag = 1;
		flag = pointShopDAO.adminAddItem( pointshopTO );
		if(flag == 0) {
			try {
				if( itemCategory.equals("0") ) {
					String fileBaseAddr = "C:/Java/boot-workspace/Petopia/src/main/webapp/images/point_shop_badge/";
					fileNameResult = fileBaseAddr + fileNameResult;
					addItemUpload.transferTo( new File( fileNameResult ) );
				} else {
					String fileBaseAddr = "C:/Java/boot-workspace/Petopia/src/main/webapp/images/point_shop_skin/";
					fileNameResult = fileBaseAddr + fileNameResult;
					fileCardNameResult = fileBaseAddr + fileCardNameResult;
					addItemUpload.transferTo( new File( fileNameResult ) );
					addItemCardUpload.transferTo( new File( fileCardNameResult ) );
				}
				System.out.println( "최종파일 경로 : " + fileNameResult );
			} catch (IllegalStateException e) {
				System.out.println( "[에러] " + e.getMessage() );
			} catch (IOException e) {
				System.out.println( "[에러] " + e.getMessage() );
			}
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "admin_views/dashboard_pointshop_write_ok" );
		return modelAndView;
    }
    
    @RequestMapping("/master_pointshop_modify_ok.do")
    public ModelAndView master_pointshop_modify_ok( HttpServletRequest request, MultipartFile modifyItemUpload, MultipartFile modifyItemCardUpload ) {
    	
    	String ps_img = modifyItemUpload.getOriginalFilename();
    	String ps_imgCard = modifyItemCardUpload.getOriginalFilename();
    	
    	String ps_seq = request.getParameter( "hiddenSeq" );
    	String ps_cate = request.getParameter( "hiddenCate" );
    	
    	PointShopTO pointShopTO = new PointShopTO();
    	pointShopTO.setPs_seq( ps_seq );
    	pointShopTO.setPs_name( request.getParameter( "modifyItemName" ) );
    	pointShopTO.setPs_content( request.getParameter( "modifyItemContent" ) );
    	pointShopTO.setPs_price( request.getParameter( "modifyItemPrice" ) );
    	
    	int flag = 1;
    	
    	File file = null;
    	
    	if ( ps_img.equals( "" ) && ps_imgCard.equals( "" ) ) {
    		flag = dao.adminModifyItem( pointShopTO );	
    	} else if ( !ps_img.equals( "" ) && !ps_imgCard.equals( "" ) ) {
    		
    		String baseFileName_ext = ps_img.substring( ps_img.lastIndexOf(".") );
			String baseFileName = ps_img.substring( 0, ps_img.indexOf( baseFileName_ext ) );
			UUID uuidRepImg = UUID.randomUUID();
			String fileNameResult = baseFileName + "_" + uuidRepImg.toString() + "_listSample" + baseFileName_ext;
			String fileCardNameResult = baseFileName + "_" + uuidRepImg.toString() + baseFileName_ext;
			
			pointShopTO.setPs_img( fileNameResult );
			
			String deleteFile = dao.adminFileSearch( ps_seq );
			String deleteCardFile = deleteFile.replace( "_listSample", "" );
			deleteFile = "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_skin/" + deleteFile;
			deleteCardFile = "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_skin/" + deleteCardFile;
			
			file = new File( deleteFile );
			file.delete();
			
			file = new File( deleteCardFile );
			file.delete();
			
			flag = dao.adminFileModifyItem( pointShopTO );

			try {
				String filePath = "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_skin/" + fileNameResult;
				file = new File( filePath );
				
				modifyItemUpload.transferTo( file );
				
				filePath = "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_skin/" + fileCardNameResult;
				file = new File( filePath );
				
				modifyItemCardUpload.transferTo( file );
				
			} catch (IllegalStateException e) {
				System.out.println( "[에러] : " + e.getMessage() );
			} catch (IOException e) {
				System.out.println( "[에러] : " + e.getMessage() );
			}
    		
    	} else if( ps_img.equals( "" ) && ps_cate.equals( "0" ) ) {
    		flag = dao.adminModifyItem( pointShopTO );	
    	} else if ( !ps_img.equals( "" ) && ps_cate.equals( "0" ) ) {
    			
			String baseFileName_ext = ps_img.substring( ps_img.lastIndexOf(".") );
			String baseFileName = ps_img.substring( 0, ps_img.indexOf( baseFileName_ext ) );
			UUID uuidRepImg = UUID.randomUUID();
			String fileNameResult = baseFileName + "_" + uuidRepImg.toString() + "_listSample" + baseFileName_ext;
			pointShopTO.setPs_img( fileNameResult );
			
			String deleteFile = dao.adminFileSearch( ps_seq );
			deleteFile = "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_badge/" + deleteFile;
			file = new File( deleteFile );
			file.delete();
			
			flag = dao.adminFileModifyItem( pointShopTO );
			
			String filePath = "C:/java/boot-workspace/Petopia/src/main/webapp/images/point_shop_badge/" + fileNameResult;
			file = new File( filePath );
			
			try {
				modifyItemUpload.transferTo( file );
			} catch (IllegalStateException e) {
				System.out.println( "[에러] : " + e.getMessage() );
			} catch (IOException e) {
				System.out.println( "[에러] : " + e.getMessage() );
			}
    	} else {
    		System.out.println( "X" );
    	}

    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("admin_views/dashboard_pointshop_modify_ok");
    	return modelAndView;
    }
    
    @RequestMapping("/deleteItem.do")
    public void deleteBySeq(@RequestBody List<Long> seqList) {
    	dao.deleteObjectsBySeqList( seqList );
    }
    
    @RequestMapping(value = "/getUserinfo.do", method = RequestMethod.GET)
    public ModelAndView getUserinfo(@RequestParam("value") String value) {
    	
    	AdminTO to = dao.userInfo(value);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", to);
        modelAndView.setViewName("Landing_views/MemberTypeJson");

        return modelAndView;
    }
    
    @RequestMapping(value = "/getUserDelete.do", method = RequestMethod.GET)
    public ModelAndView getUserDelete(@RequestParam("value") String value) {
    	
    	AdminTO to = dao.userDelete(value);
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("result", to);
    	modelAndView.setViewName("admin_views/MemberTypeJson");
    	
    	return modelAndView;
    }
}
