package com.petopia.controller;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.board.album.model.AlbumDAO;
import com.petopia.board.album.model.AlbumTO;
import com.petopia.board.information.model.InformationDAO;
import com.petopia.board.information.model.InformationTO;
import com.petopia.group.model.MyGroupDAO;
import com.petopia.group.model.MyGroupTO;
import com.petopia.mapper.MemberMapper;
import com.petopia.model.MemberDAO;
import com.petopia.model.MemberTO;
import com.petopia.session.SessionConst;

@RestController
@MapperScan("com.project.mapper")
public class BoardController {
	
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private MemberDAO dao;
	
	@Autowired
	private MyGroupDAO mgdao;
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private InformationDAO informationDAO;
	
    @RequestMapping("/kakao.do")
    public ModelAndView kakao() {
        ModelAndView modelAndView = new ModelAndView();
        String petopia = "petopia";
        modelAndView.addObject("name", petopia);
        modelAndView.setViewName("ajax");
        return modelAndView;
    }
    
    @RequestMapping( "index.do" )
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        
        HttpSession session = request.getSession();
        MemberTO userData = (MemberTO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (userData != null) {
            modelAndView.addObject("userData", userData);
        }
        
        String petopia = "petopia";
        modelAndView.addObject("name", petopia);
        modelAndView.setViewName("Landing_views/index");
        
        // index 페이지 information, album data
        
        List<InformationTO> info_datas = informationDAO.mainList();
        
        List<AlbumTO> album_datas = albumDAO.mainList();
        
        request.setAttribute("info_datas", info_datas);
        request.setAttribute("album_datas", album_datas);
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/login.do", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        if (request.getMethod().equals("GET")) {
            // GET 요청 처리 로직
            modelAndView.setViewName("Landing_views/login");
            return modelAndView;
        }
        String username = request.getParameter("id");
        String password = request.getParameter("password");

        MemberTO userData = dao.loginVerified(username, password);
        
        if (userData != null) {
            HttpSession session = request.getSession();
            System.out.println(userData);
            session.setAttribute(SessionConst.LOGIN_MEMBER, userData);
            MyGroupTO to = new MyGroupTO();
            to.setM_SEQ(userData.getM_seq());

            ArrayList<MyGroupTO> lists = mgdao.mygroupListSidebar(to);
            session.setAttribute(SessionConst.MYGROUP_LIST, lists);

            modelAndView.setViewName("redirect:/index.do");
           
        } else {
            modelAndView.addObject("error", "Invalid username or password");
            modelAndView.setViewName("Landing_views/login");
        }

        return modelAndView;
    }
    
 // 카카오 로그인
    @RequestMapping("/KLogin.do")
    public int KLogin(HttpServletRequest request) {
    	
    	MemberTO to = new MemberTO();
    	
    	to.setGrade_seq("2");
    	
    	int flag = dao.idCheck(request.getParameter("m_id")) == null? 0:1 ;
    	
    	if(flag == 0) {  
    		to = MemberTO.builder()
    			.m_seq("0")
    			.m_id(request.getParameter("m_id"))
    			.m_nickname(request.getParameter("m_nickname"))
    			.m_name("원식")
    			.m_password("1234")
    			.m_email(request.getParameter("m_email"))
    			.m_addr("삼선동")
    			.m_phone("01020758593")
    			.m_age("28")
    			.m_gender(request.getParameter("m_gender"))
    			.m_date("DATE_FORMAT(now(), '%Y%m%d%H%i%s')")
    			.grade_seq("2")
    			.m_totalPoint("0")
    			.m_point("0")
    			.build();
    	
		    	flag = dao.kakaoAddMember(to);
    			HttpSession session = request.getSession();
    			session.setAttribute(SessionConst.LOGIN_MEMBER, to);
    			
    			String newMemberSeq = dao.selectSeq();
    			flag = dao.profileCardInsertOk( newMemberSeq );
    			
    	}else {
    		
    		
    		HttpSession session = request.getSession();
    		
    		MemberTO userInfo = dao.userTransfer( request.getParameter("m_id") );
    		System.out.println( userInfo.getM_email() );
    			
    		session.setAttribute(SessionConst.LOGIN_MEMBER, userInfo);
    		
    		
    			flag = 1; 

    	}
		
    	//System.out.println("카카오로그인 : " + to.getM_nickname());
    	//System.out.println("카카오로그인 : " + to.getM_email());
    	//System.out.println("카카오로그인 : " + to.getM_gender());
    	
    	return flag;
    	
    }
    
    @RequestMapping("/logout.do")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // 세션을 무효화하여 로그아웃 처리
        HttpSession session = request.getSession();
        session.invalidate();

        modelAndView.setViewName("Landing_views/index");
        return modelAndView;
    }
	
	@RequestMapping("/member_insertOk.do")
	public ModelAndView member_insertOk(HttpServletRequest request) {
		
		MemberTO member = new MemberTO();
		ModelAndView modelAndView = new ModelAndView();
		member.setM_id(request.getParameter("m_id"));
		member.setM_nickname(request.getParameter("m_nickname"));
		member.setM_name(request.getParameter("m_name"));
		member.setM_password(request.getParameter("m_password"));
		member.setM_email(request.getParameter("m_email"));
		member.setM_addr(request.getParameter("m_addr"));
		member.setM_phone(request.getParameter("m_phone"));
		member.setM_age(request.getParameter("m_age"));
		member.setM_gender(request.getParameter("m_gender"));
		System.out.println(member.getM_nickname());
		
		modelAndView.setViewName("Landing_views/index");
		dao.MemberInsertOk( member );
		
		String newMemberSeq = dao.selectSeq();
		int flag = dao.profileCardInsertOk( newMemberSeq );
		
		return modelAndView;
		
	}
	
	@RequestMapping("/member_insert.do")
	public ModelAndView member_insert(MemberTO memberTo) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("Landing_views/member_insert");
	
		return modelAndView;
	}
	
    
	@RequestMapping("/dupId.do")
	@ResponseBody
	public int idDuplicateCheck(String m_id) {
		MemberTO member = new MemberTO();
		
		member.setM_id(m_id);
		
		return dao.checkIdDup(member);
	}
	
	@RequestMapping("/dupEmail.do")
	@ResponseBody
	public int emailDuplicate(String m_email) {
		
		MemberTO member = new MemberTO();
		
		member.setM_email(m_email);
		
		
		return dao.checkEmailDup(member);
	}
	
	@RequestMapping("/dupNickname.do")
	@ResponseBody
	public int nicknameDuplicate(String m_nickname) {
		
		MemberTO member = new MemberTO();
		
		member.setM_nickname(m_nickname);
		
		
		return dao.checkNicknameDup(member);
	}
	
	
	
	
    @RequestMapping("/introduce.do")
    public ModelAndView introduce() {
    	ModelAndView modelAndView = new ModelAndView();
    	String petopia = "petopia";
    	modelAndView.addObject("name", petopia);
    	modelAndView.setViewName("Landing_views/introduce");
    	return modelAndView;
    }
    
    @RequestMapping("/guide.do")
    public ModelAndView guide() {
    	ModelAndView modelAndView = new ModelAndView();
    	String petopia = "petopia";
    	modelAndView.addObject("name", petopia);
    	modelAndView.setViewName("Landing_views/guide");
    	return modelAndView;
    }
    
}
