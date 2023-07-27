package com.petopia.group.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.admin.model.AdminTO;
import com.petopia.group.board.model.GroupBoardCommentTO;
import com.petopia.group.board.model.GroupBoardFileDAO;
import com.petopia.group.board.model.GroupBoardFileTO;
import com.petopia.group.board.model.GroupBoardTO;
import com.petopia.group.model.GroupDAO;
import com.petopia.group.model.GroupHashDAO;
import com.petopia.group.model.GroupHashTO;
import com.petopia.group.model.GroupTO;
import com.petopia.group.model.JoinGroupDAO;
import com.petopia.group.model.JoinGroupTO;
import com.petopia.group.model.MyGroupDAO;
import com.petopia.group.model.MyGroupTO;
import com.petopia.model.MemberTO;

@RestController
public class PetopiaController {

	@Autowired
	private GroupDAO gdao;

	@Autowired
	private JoinGroupDAO jdao;

	@Autowired
	private MyGroupDAO mgdao;

	@Autowired
	private GroupHashDAO ghdao;

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/group_index.do")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("group_views/group_index");
		return modelAndView;
	}

	@RequestMapping("/group_list.do")
	public ModelAndView list(HttpServletRequest request) {

		ArrayList<GroupTO> lists = null;
		ArrayList<GroupHashTO> lists2 = null;
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("group_views/group_list");

		GroupHashTO ghto = new GroupHashTO();
		GroupTO gto = new GroupTO();
		String option = request.getParameter("option");
		String searchStr = request.getParameter("searchStr");
		System.out.println("\n searchStr의 값은 " + searchStr + "   " + option);

		if (option == null) {
			lists = gdao.groupList();
		} else if (option.equals("subject")) {
			System.out.println("subject 들어왔다감");
			gto.setGR_NAME("%" + searchStr + "%");
			lists = gdao.subjectHashList(gto);
		} else if (option.equals("content")) {
			gto.setGR_EXPLAN("%" + searchStr + "%");
			lists = gdao.contentHashList(gto);
		} else if (option.equals("hashtag")) {
			ghto.setHAS_CONTENT("%" + searchStr + "%");
			lists = gdao.hashSearchList(ghto);
		}

		// modelAndView.addObject("lists2",lists2);
		modelAndView.addObject("lists", lists);
		return modelAndView;
	}

	@RequestMapping("/group_write.do")
	public ModelAndView write(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			modelAndView.setViewName("group_views/group_write");
		}

		return modelAndView;
	}

	@RequestMapping("/group_write_ok.do")
	public ModelAndView writeOk(MultipartFile upload, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			String member_seq = userData.getM_seq();
			GroupTO gto = new GroupTO();
			gto.setM_SEQ(member_seq);
			gto.setGR_NAME(request.getParameter("gr_name"));
			gto.setGR_EXPLAN(request.getParameter("gr_explan"));
			gto.setGR_ADMIN(request.getParameter("gr_admin")); // list 정렬 할거 받기

			String extension = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));

			String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().lastIndexOf("."));

			long timestamp = System.currentTimeMillis();

			String newfilename = filename + "-" + timestamp + extension;

			try {
				upload.transferTo(new File(newfilename));
			} catch (IllegalStateException e) {
				System.out.println("[에러] " + e.getMessage());
			} catch (IOException e) {
				System.out.println("[에러] " + e.getMessage());
			}

			gto.setGR_FILENAME(newfilename);
			gto.setGR_FILESIZE(upload.getSize());

			String[] hash = request.getParameterValues("hash");
			if (hash == null) {
				gto.setGR_HASH_CNT(0);
			} else {
				gto.setGR_HASH_CNT(hash.length);
			} // 해쉬 받아오기
			int flag = gdao.groupWriteOk(gto);

			int seq = gdao.groupRecentSeq();
			GroupHashTO ghto = new GroupHashTO();
			ghto.setGR_SEQ(seq);
			int flag2 = 0;
			for (int i = 0; i < hash.length; i++) {
				ghto.setHAS_CONTENT(hash[i]);
				flag2 = ghdao.groupHashOk(ghto);
			}

			modelAndView.setViewName("group_views/group_write_ok");
			modelAndView.addObject("flag", flag);
		}
		return modelAndView;
	}

	@RequestMapping("/group_view.do")
	public ModelAndView view(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		

		if (userData != null) {
			String gr_seq = request.getParameter("gr_seq");
			GroupTO gto = new GroupTO();
			gto.setGR_SEQ(gr_seq);
			gto = gdao.groupViewLogout(gto);

			GroupHashTO ghto = new GroupHashTO();
			ghto.setGR_SEQ(Integer.parseInt(gr_seq));
			ArrayList<GroupHashTO> lists = ghdao.groupHashView(ghto);
			
			JoinGroupTO jto = new JoinGroupTO();
			String member_seq = userData.getM_seq();
			jto.setGR_SEQ(request.getParameter("gr_seq"));
			jto.setM_SEQ(member_seq);
			Boolean flag = jdao.joinCheck(jto);
			modelAndView.addObject("flag", flag);
			modelAndView.addObject("to", gto);
			modelAndView.addObject("lists", lists);
		}else {
			String gr_seq = request.getParameter("gr_seq");
			GroupTO gto = new GroupTO();
			gto.setGR_SEQ(gr_seq);
			gto = gdao.groupViewLogout(gto);

			GroupHashTO ghto = new GroupHashTO();
			ghto.setGR_SEQ(Integer.parseInt(gr_seq));
			ArrayList<GroupHashTO> lists = ghdao.groupHashView(ghto);
			modelAndView.addObject("to", gto);
			modelAndView.addObject("lists", lists);
		}
		
		modelAndView.setViewName("group_views/group_view");
		return modelAndView;
	}
	
	@RequestMapping("/group_modify.do")
	public ModelAndView groupModify(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		

		if (userData != null) {
			String gr_seq = request.getParameter("gr_seq");
			GroupTO gto = new GroupTO();
			gto.setGR_SEQ(gr_seq);
			gto.setM_SEQ(userData.getM_seq());
			gto = gdao.groupViewLogin(gto);

			GroupHashTO ghto = new GroupHashTO();
			ghto.setGR_SEQ(Integer.parseInt(gr_seq));
			ArrayList<GroupHashTO> lists = ghdao.groupHashView(ghto);
			System.out.println("modify   : "+gto);
			modelAndView.addObject("to", gto);
			modelAndView.addObject("lists", lists);
			modelAndView.setViewName("group_views/group_modify");
			
		}else {

			modelAndView.setViewName("default_bar/alert_please_login");
		}
		
		
		return modelAndView;
	}
	
	@RequestMapping("/group_modify_ok.do")
	public ModelAndView groupModifyOk(MultipartFile upload,HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		

		if (userData != null) {
			String gr_seq = request.getParameter("gr_seq");
			String m_seq = userData.getM_seq();
			GroupTO gto = new GroupTO();
			gto.setM_SEQ(m_seq);
			gto.setGR_SEQ(gr_seq);
			gto.setGR_NAME(request.getParameter("gr_name"));
			gto.setGR_EXPLAN(request.getParameter("gr_explan"));
			

			String extension = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));

			String filename = upload.getOriginalFilename().substring(0, upload.getOriginalFilename().lastIndexOf("."));

			long timestamp = System.currentTimeMillis();

			String newfilename = filename + "-" + timestamp + extension;

			try {
				upload.transferTo(new File(newfilename));
			} catch (IllegalStateException e) {
				System.out.println("[에러] " + e.getMessage());
			} catch (IOException e) {
				System.out.println("[에러] " + e.getMessage());
			}

			gto.setGR_FILENAME(newfilename);
			gto.setGR_FILESIZE(upload.getSize());
			
			
			
			String[] hash = request.getParameterValues("hash");
			if (hash == null) {
				gto.setGR_HASH_CNT(0);
			} else {
				gto.setGR_HASH_CNT(hash.length);
			} // 해쉬 받아오기
			int flag1 = gdao.groupModifyOk1(gto);
			int flag3 = gdao.groupModifyOk2(gr_seq);

			
			GroupHashTO ghto = new GroupHashTO();
			ghto.setGR_SEQ(Integer.parseInt(gr_seq));
			int flag2 = 0;
			for (int i = 0; i < hash.length; i++) {
				ghto.setHAS_CONTENT(hash[i]);
				flag2 = ghdao.groupHashOk(ghto);
			}
			
			modelAndView.addObject("flag1", flag1);
			modelAndView.addObject("flag2", flag2);
			modelAndView.setViewName("group_views/group_modify_ok");
			
			
		}else {
			modelAndView.setViewName("default_bar/alert_please_login");
		}
		
		return modelAndView;
	}

	@RequestMapping("/group_produce.do")
	public ModelAndView produce(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			GroupTO gto = new GroupTO();
			gto.setGR_SEQ(request.getParameter("gr_seq"));
			int flag = gdao.groupProduce(gto);

			GroupTO gto1 = new GroupTO();
			gto1.setGR_SEQ(request.getParameter("gr_seq"));
			gto1 = gdao.findAdminSeq(gto1);
			String admin_seq = gto1.getM_SEQ();
			String gr_admin = gto1.getGR_ADMIN();

			JoinGroupTO jto = new JoinGroupTO();
			jto.setM_SEQ(admin_seq);
			jto.setGR_SEQ(request.getParameter("gr_seq"));
			jto.setGR_ADMIN(gr_admin);
			jto.setJG_EXPLAIN("admin");
			int flag2 = jdao.groupProduceAdmin(jto);

			modelAndView.setViewName("group_views/group_produce");
			modelAndView.addObject("flag", flag);
			modelAndView.addObject("flag2", flag2);
		}
		return modelAndView;

	}

	@RequestMapping("/group_produce_delete.do")
	public ModelAndView produceDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			GroupTO to = new GroupTO();
			to.setGR_SEQ(request.getParameter("gr_seq"));

			int flag = gdao.groupProduceDelete(to);

			modelAndView.setViewName("group_views/group_produce_delete");
			modelAndView.addObject("flag", flag);
		}
		return modelAndView;
	}

	@RequestMapping("/join_group_ok.do")
	public ModelAndView joinGroupOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			modelAndView.setViewName("group_views/join_group_ok");
		}
		return modelAndView;
	}

	@RequestMapping("/join_group_write.do")
	public ModelAndView joinGroupWrite(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			modelAndView.setViewName("group_views/join_group_write");
		}
		return modelAndView;
	}

	@RequestMapping("/join_group_write_ok.do")
	public ModelAndView joinGroupWriteOk(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			String member_seq = userData.getM_seq();

			GroupTO gto = new GroupTO();
			gto.setGR_SEQ(request.getParameter("gr_seq"));
			gto = gdao.findAdmin(gto);
			System.out.println("====================" + request.getParameter("gr_seq") + "===========");

			JoinGroupTO jto = new JoinGroupTO();
			jto.setJG_EXPLAIN(request.getParameter("jg_explain"));
			jto.setGR_SEQ(request.getParameter("gr_seq"));
			jto.setM_SEQ(member_seq);
			jto.setGR_ADMIN(gto.getGR_ADMIN());

			int flag = jdao.joinGroupWriteOk(jto, gto);

			modelAndView.setViewName("group_views/join_group_write_ok");
			modelAndView.addObject("flag", flag);
		}
		return modelAndView;
	}

	@RequestMapping("/mygroup_list.do")
	public ModelAndView mygrouplist(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			if(userData.getGrade_seq().equals("1")) {
				ArrayList<MyGroupTO> lists = mgdao.mygroupListAdmin();
				modelAndView.addObject("lists", lists);
			}else {
				String member_seq = userData.getM_seq();
				MyGroupTO to = new MyGroupTO();
				to.setM_SEQ(member_seq);

				ArrayList<MyGroupTO> lists = mgdao.mygroupList(to);
				modelAndView.addObject("lists", lists);
			}
			modelAndView.setViewName("group_views/mygroup_list");
		}
		return modelAndView;
	}

	@RequestMapping("/mygroup_manage.do")
	public ModelAndView mygroupmangelist(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {

			modelAndView.setViewName("default_bar/alert_please_login");
			System.out.println("not null");
		} else {
			MyGroupTO to = new MyGroupTO();
			to.setGR_SEQ(request.getParameter("gr_seq"));
			System.out.println("to.getgr : " + to.getGR_SEQ());

			ArrayList<MyGroupTO> lists = mgdao.mygroupMangeList(to);

			int totalRecode = lists.size();
			System.out.println("totalRecode : " + totalRecode);

			modelAndView.setViewName("group_views/mygroup_manage");
			modelAndView.addObject("lists", lists);
		}
		return modelAndView;
	}

	@RequestMapping("/mygroup_manage_join.do")
	public ModelAndView mygroupMangeJoin(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {

			String member_seq = userData.getM_seq();

			JoinGroupTO to = new JoinGroupTO();
			to.setGR_SEQ(request.getParameter("gr_seq"));
			// 신청인의 m_seq가져오기
			to.setM_SEQ(request.getParameter("m_seq"));
			int flag = mgdao.mygroupManageJoin(to);

			GroupTO gto = new GroupTO();
			gto.setGR_SEQ(request.getParameter("gr_seq"));
			int flag2 = gdao.upInwon(gto);

			modelAndView.setViewName("group_views/mygroup_manage_join");
			modelAndView.addObject("flag", flag);
			modelAndView.addObject("flag2", flag2);
		}
		return modelAndView;
	}

	@RequestMapping("/mygroup_manage_delete.do")
	public ModelAndView mygroupMangeDelete(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {


			JoinGroupTO to = new JoinGroupTO();
			to.setGR_SEQ(request.getParameter("gr_seq"));
			to.setM_SEQ(request.getParameter("m_seq"));
			int flag = mgdao.mygroupManageDelete(to);

			modelAndView.setViewName("group_views/mygroup_manage_delete");
			modelAndView.addObject("flag", flag);
		}
		return modelAndView;
	}

	@RequestMapping("/group_view_css.do")
	public ModelAndView groupViewCss(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			modelAndView.setViewName("group_views/group_view_css");
		}
		return modelAndView;
	}

	@RequestMapping("/master_group.do")
	public ModelAndView dashgroup(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			ArrayList<GroupTO> lists = gdao.masterGroupList();
			String petopia = "groupactive";
			modelAndView.setViewName("admin_views/dashboard_group");

			modelAndView.addObject("lists", lists);
			modelAndView.addObject("activeMenu", petopia);
			System.out.println("master_group.do의 controller");
		}
		return modelAndView;
	}

	@RequestMapping("/mygroup_manage_adc.do")
	public ModelAndView mygroupMangeAdc(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			JoinGroupTO to = new JoinGroupTO();
			to.setGR_SEQ(request.getParameter("gr_seq"));
			// 신청인의 m_seq가져오기
			to.setM_SEQ(request.getParameter("m_seq"));
			int flag = mgdao.mygroupManageAdc(to);

			modelAndView.setViewName("group_views/mygroup_manage_adc");
			modelAndView.addObject("flag", flag);
		}
		return modelAndView;
	}

	@RequestMapping("/mygroup_manage_dem.do")
	public ModelAndView mygroupMangeDem(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		MemberTO userData = (MemberTO) session.getAttribute("loginMember");
		if (userData == null) {
			modelAndView.setViewName("default_bar/alert_please_login");
		} else {
			JoinGroupTO to = new JoinGroupTO();
			to.setGR_SEQ(request.getParameter("gr_seq"));
			// 신청인의 m_seq가져오기
			to.setM_SEQ(request.getParameter("m_seq"));
			int flag = mgdao.mygroupManageDem(to);

			modelAndView.setViewName("group_views/mygroup_manage_dem");
			modelAndView.addObject("flag", flag);
		}
		return modelAndView;
	}

	@RequestMapping("/alert_please_login.do")
	public ModelAndView alert(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("default_bar/alert_please_login");
		return modelAndView;
	}

	
	/*
	 * @RequestMapping("/*.do") public ModelAndView
	 * mygrouplistside(HttpServletRequest request) { ModelAndView modelAndView = new
	 * ModelAndView(); MemberTO userData =
	 * (MemberTO)session.getAttribute("loginMember"); if(userData == null){
	 * modelAndView.setViewName("default_bar/sidebar"); }else{ String member_seq =
	 * userData.getM_seq(); MyGroupTO to = new MyGroupTO(); to.setM_SEQ(member_seq);
	 * ArrayList<MyGroupTO> lists1234 = mgdao.mygroupList(to);
	 * 
	 * modelAndView.setViewName("default_bar/sidebar");
	 * modelAndView.addObject("lists1234",lists1234); } return modelAndView; }
	 */
	  
	  @RequestMapping(value = "/getGroupDelete.do", method = RequestMethod.GET)
	    public ModelAndView getGroupDelete(HttpServletRequest request) {
	    	String value = request.getParameter("value");
	    	int flag = gdao.groupDelete(value);
	    	ModelAndView modelAndView = new ModelAndView();
	    	modelAndView.addObject("result", flag);
	    	modelAndView.setViewName("admin_views/GroupTypeJson");
	    	
	    	return modelAndView;
	    }

}
