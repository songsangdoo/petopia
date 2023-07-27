package com.petopia.mypage.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.model.MemberTO;
import com.petopia.mypage.model.MyPagePetTO;
import com.petopia.mypage.model.MyPageTO;
import com.petopia.mypage.model.MypageDAO;
import com.petopia.mypage.model.MypagePetDAO;
import com.petopia.mypage.model.MypageProfileCardDAO;
import com.petopia.mypage.model.MypageProfileCardTO;
import com.petopia.pointshop.model.GradeDAO;
import com.petopia.pointshop.model.GradeTO;
import com.petopia.pointshop.model.InventoryDAO;
import com.petopia.pointshop.model.InventoryTO;
import com.petopia.pointshop.model.PointShopDAO;
import com.petopia.pointshop.model.PointShopTO;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@RestController
@ComponentScan({"com.petopia.mypage.model"})
public class MypageController {
	
	@Autowired
	private MypageDAO mypageDAO;
	
	@Autowired
	private MypagePetDAO mypetDAO;
	
	@Autowired
	private GradeDAO gradeDAO;
	
	@Autowired
	private MypageProfileCardDAO mypageProfileCardDAO;
	
	@Autowired
	private InventoryDAO inventoryDAO;
	
	@Autowired
	private PointShopDAO pointShopDAO;
	
	@RequestMapping( "/mypage.do" )
	public ModelAndView profile( HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		String userGrade = userData.getGrade_seq();
		
		String nextGrade = Integer.toString( Integer.parseInt( userData.getGrade_seq() ) + 1 ) ;
		
		GradeTO gradeChecks = gradeDAO.gradeCheck( userGrade ); // 필요
		GradeTO nextGradeCheck = gradeDAO.gradeCheck( nextGrade );
		
		MypageProfileCardTO profileCardInfo = mypageProfileCardDAO.profileCardInfoCheck( userData.getM_seq() );
		
		ArrayList<InventoryTO> userInvenSkin = inventoryDAO.skinCheck( userData.getM_seq() );
		ArrayList<InventoryTO> userInvenBadge = inventoryDAO.badgeCheck( userData.getM_seq() );
		
		ArrayList<PointShopTO> skinPointShopList = new ArrayList<>();
		ArrayList<PointShopTO> badgePointShopList = new ArrayList<>();
		
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
		
		if( profileCardInfo == null ) {
			profileCardInfo = new MypageProfileCardTO();
			profileCardInfo.setPro_comment( "한 마디 글을 작성해 주세요." );
			profileCardInfo.setPro_img( "no_image.png" );
		}
		
		ArrayList<String> myBadgeSeqList = inventoryDAO.myBadgeSeqList( userData.getM_seq() );
		ArrayList<String> myBadgeImgList = new ArrayList<>();
				
		String mySkinSeq = inventoryDAO.mySkinSeq( userData.getM_seq() );
		String mySkinImg = "";
		
		if( myBadgeSeqList != null ) {
			for (int i = 0; i < myBadgeSeqList.size(); i++ ) {
				String myBadgeImg = pointShopDAO.myBadgeImg( myBadgeSeqList.get(i).toString() );
				myBadgeImgList.add( myBadgeImg );
			}
		}
		
		if( mySkinSeq != null ) {
			mySkinImg = pointShopDAO.mySkinImg( mySkinSeq );
		}
		
		
		/* * * * * * * * *
		 *  내 펫 정보 	*/
		ArrayList<MyPagePetTO> lists = new ArrayList<>();
		lists = mypetDAO.getPetList(userData.getM_seq());
		
		ArrayList<MyPagePetTO> petCheckLists = new ArrayList<>();
		if( lists.size() != 0 ) {
			for( int i = 0; i<lists.size(); i++ ) { 
				MyPagePetTO checkSearch = mypetDAO.equipCardPet( lists.get(i).getP_seq() );
				petCheckLists.add ( checkSearch );
			}
		}
		
		System.out.println( "텟텟 : " + petCheckLists );
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("/mypage_views/mypage_profile");
		modelAndView.addObject("gradeChecks", gradeChecks);
		modelAndView.addObject("nextGradeCheck", nextGradeCheck);
		modelAndView.addObject("petList", lists);
		modelAndView.addObject( "profileCardInfo", profileCardInfo );
		modelAndView.addObject( "skinPointShopList", skinPointShopList );
		modelAndView.addObject( "badgePointShopList", badgePointShopList );
		modelAndView.addObject( "myBadgeImgList", myBadgeImgList );
		modelAndView.addObject( "mySkinImg", mySkinImg );
		
		modelAndView.addObject( "petCheckLists", petCheckLists );
		
		
		
		return modelAndView;
	}
	
	@RequestMapping(value = "mypageModify.do", produces = "text/html; charset=UTF-8")
	public ModelAndView myPageModify(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		
		resp.setCharacterEncoding( "UTF-8" );
		request.setCharacterEncoding( "UTF-8" );
		
		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );

		String seq = userData.getM_seq();
		
		userData.setM_name( request.getParameter( "name" ) );
		userData.setM_phone( request.getParameter( "phone" ) );
		userData.setM_email( request.getParameter( "email" ) );
		userData.setM_addr( request.getParameter( "address1" ) + " " + request.getParameter( "address2" ) );
		
		int flag = mypageDAO.setModifyInfo( seq, userData );

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "mypage_views/mypage_edit_ok" );
		modelAndView.addObject( "flag", flag );
		
		return modelAndView;
	}
	
	@RequestMapping( "/profileModify.do" )
	public ModelAndView your_other_jsp_file( HttpServletRequest request ) {
		
		System.out.println( "Modal 소환" );
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "mypage_views/profile_modify" );
		return modelAndView;
	}

	@RequestMapping( "/passwordModify.do" )
	public ModelAndView passwordModify( HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		
		userData.setM_password( request.getParameter( "newPassword" ) );
		
		String m_seq = userData.getM_seq();
		String m_password = request.getParameter( "newPassword" );
		
		int flag = mypageDAO.setPasswordModify( m_seq, m_password );
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "mypage_views/mypage_edit_ok" );
		modelAndView.addObject( "flag", flag );
		return modelAndView;
	}
	
	
	
	
	
	
	@RequestMapping( "/profileEdit.do" )
	public ModelAndView profileEdit( HttpServletRequest request, MultipartFile editCardImage ) {

		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );

		File file = null;
		String fileFullName = editCardImage.getOriginalFilename();
		
		String deleteFile = mypageProfileCardDAO.searchDeleteFile( userData.getM_seq() );
		if( !(deleteFile.equals("no_image.png")) ) {
			deleteFile = "C:/java/boot-workspace/Petopia/src/main/webapp/images/profile_img/" + deleteFile;
	
			file = new File( deleteFile );
			file.delete();
		}
		String baseFileName_ext = fileFullName.substring( fileFullName.lastIndexOf(".") );
		String baseFileName = fileFullName.substring( 0, fileFullName.indexOf( baseFileName_ext ) );
		UUID uuidRepImg = UUID.randomUUID();
		String fileNameResult = baseFileName + "_" + uuidRepImg.toString() + baseFileName_ext;
		
		String filePath = "C:/java/boot-workspace/Petopia/src/main/webapp/images/profile_img/" + fileNameResult;
		file = new File( filePath );
		
		try {
			editCardImage.transferTo( file );
		
		} catch (IllegalStateException e) {
			System.out.println( "[에러] : " + e.getMessage() );
		} catch (IOException e) {
			System.out.println( "[에러] : " + e.getMessage() );
		}
		
		MypageProfileCardTO mypageProfileCardTO = new MypageProfileCardTO();
		mypageProfileCardTO.setM_seq( userData.getM_seq() );
		mypageProfileCardTO.setPro_comment( request.getParameter( "editCardComment" ) );
		mypageProfileCardTO.setPro_img( fileNameResult );
		
		int flag = mypageProfileCardDAO.updateCardInfo( mypageProfileCardTO );
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName( "mypage_views/mypage_edit_ok" );
		modelAndView.addObject( "flag", flag );
		return modelAndView;
	}
	
	
	@RequestMapping("/addPetInfo.do")
	public ModelAndView addPetInfo(HttpServletRequest request, MultipartFile uploads) {

	    int flag = 0;

	    ModelAndView modelAndView = new ModelAndView();

	    String originalFilename = uploads.getOriginalFilename();
	    String extension = StringUtils.getFilenameExtension(originalFilename);
	    String uuid = UUID.randomUUID().toString();
	    String modifiedFilename = uuid + "." + extension;

	    String directoryPath = Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "images", "profile_pet").toString();
	    Path directory = Paths.get(directoryPath);

	    HttpSession session = request.getSession();
	    MemberTO userData = (MemberTO) session.getAttribute("loginMember");
	    System.out.println(mypetDAO.getCountPet(userData.getM_seq()));
	    if (Integer.parseInt(mypetDAO.getCountPet(userData.getM_seq())) >= 4) {
	        flag = 1;
	        modelAndView.addObject("flag", flag);
	        modelAndView.setViewName("mypage_views/mypage_pet_ok");
	        return modelAndView;
	    } else {
	        // 4 이하인 경우의 처리
	        if (!Files.exists(directory)) {
	            try {
	                Files.createDirectories(directory);
	            } catch (IOException e) {
	                e.printStackTrace();
	                // 디렉토리 생성 실패 시 예외 처리
	            }
	        }

	        String filePath = Paths.get(directoryPath, modifiedFilename).toString();

	        if (uploads != null && !uploads.isEmpty()) {
	            try {
	                uploads.transferTo(new File(filePath));

	                MyPagePetTO to = new MyPagePetTO();
	                to.setM_seq(userData.getM_seq());
	                to.setP_name(request.getParameter("name"));
	                to.setP_age(request.getParameter("age"));
	                to.setP_birth(request.getParameter("birthday"));
	                to.setP_species(request.getParameter("type"));
	                to.setP_gender(request.getParameter("gender"));
	                to.setP_img(modifiedFilename);
	                mypetDAO.addPetInfo(to);
	                

	                modelAndView.addObject("flag", flag);
	                modelAndView.setViewName("mypage_views/mypage_pet_ok");
	                return modelAndView;

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } else {
	            // 파일이 첨부되지 않은 경우에 대한 예외 처리
	            System.out.println("파일이 첨부되지 않았습니다.");
	        }
	    }

	    // 그 외의 경우의 처리
	    // (4 이상이 아닌 경우, 파일이 첨부되지 않은 경우 등)
	    modelAndView.addObject("flag", flag);
	    modelAndView.setViewName("mypage_views/mypage_edit_ok");
	    return modelAndView;
	}
	
	@RequestMapping("/deletePetInfo.do")
	public ModelAndView delPetInfo(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		int flag = 0;
		System.out.println(request.getParameter("seq"));
		String getOldImgName = mypetDAO.getOldImgName(request.getParameter("seq"));
		String filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "images", "profile_pet", getOldImgName).toString();
		Path pathToDelete = Paths.get(filePath);
		try {
            // 파일 삭제
            Files.delete(pathToDelete);
            System.out.println("파일 삭제 성공: " + filePath);
        } catch (IOException e) {
        	flag = 2;
            System.err.println("파일 삭제 실패: " + e.getMessage());
        }
		
		modelAndView.addObject("flag", flag);
	    modelAndView.setViewName("mypage_views/mypage_pet_ok");
		mypetDAO.initPetInfoDelete(request.getParameter("seq"));
		return modelAndView;
	}
	
	@RequestMapping("/deleteProfile.do")
	public ModelAndView deleteProfile(HttpServletRequest request) {
		
		int flag = 0;
		
		String seq = request.getParameter("seq");
		String password = request.getParameter("DeleteCheckPassword");
		
		System.out.println("아이디와 비밀번호 : " + seq + " : "+ password);
		
		int value = mypageDAO.delMember(seq, password);
		if(value == 0) {
			flag = 1;
		}
		HttpSession session = request.getSession();
        session.invalidate();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName("mypage_views/mypage_del_member_ok");
		return modelAndView;
	}
	
	@RequestMapping( "/equip_badge_ok.do" )
	public ModelAndView equipBadge( HttpServletRequest request ) {

		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		
		String[] selectedBadges = request.getParameterValues( "badgeCheckbox" );

		int flag = 1;
		
		if ( selectedBadges != null ) {
			flag = mypageDAO.badgeAllNoequip( userData.getM_seq() );
			for ( String badge : selectedBadges ) {
				flag = mypageDAO.equipBadge( userData.getM_seq(), badge );
			}
		} else {
			mypageDAO.badgeAllNoequip( userData.getM_seq() );
			System.out.println("선택된 뱃지가 없습니다.");
		}

    	ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mypage_views/equip_badge_ok");
		modelAndView.addObject( "flag", flag );
		return modelAndView;
    }
	
	
	@RequestMapping( "/equip_skin_ok.do" )
	public ModelAndView equipSkin( HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		
		String selectedSkin = request.getParameter( "skinRadio" );
		
		int flag = 1;
		
		flag = mypageDAO.skinAllNoequip( userData.getM_seq() );
		
		if ( selectedSkin != null ) {
			flag = mypageDAO.equipSkin( userData.getM_seq(), selectedSkin );
		} else {
			System.out.println("선택된 스킨이 없습니다.");
			flag = 1;
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mypage_views/equip_skin_ok");
		modelAndView.addObject( "flag", flag );
		return modelAndView;
	}
	
	@RequestMapping( "/petCard.do" )
	public ModelAndView petCard( HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		MemberTO userData = (MemberTO) session.getAttribute( "loginMember" );
		
		String[] selectedSkins = request.getParameterValues("petCheckBox");
				
		int flag = 1;
		
		mypetDAO.allNoSelect( userData.getM_seq() );
		
		if( selectedSkins == null ) {
			selectedSkins = new String[0];
		} else {
			for ( String p_seq : selectedSkins )
			flag = mypetDAO.petSelect( p_seq );
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mypage_views/mypage_edit_ok");
		modelAndView.addObject( "flag", flag );
		return modelAndView;
	}
	
}

