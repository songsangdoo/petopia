package com.petopia.api.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.petopia.api.model.AbandonmentDAO;
import com.petopia.api.model.AbandonmentTO;
import com.petopia.api.model.HospitalDAO;
import com.petopia.api.model.HospitalTO;
import com.petopia.api.model.TripDAO;
import com.petopia.api.model.TripTO;
import com.petopia.api.service.PetopiaApiService;

@RestController
@PropertySource("classpath:API-KEY.properties")
@MapperScan("com.petopia.api.mapper")
public class APIController {
	
	@Autowired
	private PetopiaApiService petopiaApiService;	
	@Autowired
	private HospitalDAO hospitalDao;
	@Autowired
	private TripDAO tripDao;	
	@Autowired
	private AbandonmentDAO abandonmentDAO;	
    @Value("${kakao-js-key}")
    private String kakao_js_key;    
	
    //동물병원
    @RequestMapping("/animalHospital.do")    
    public ModelAndView hospital(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        
        String dong = request.getParameter("dong");
        List<HospitalTO> lists = null;
        if(dong != null) {
        	lists = hospitalDao.searchHospital(dong);
            modelAndView.addObject("lists", lists);
        }
        
        modelAndView.setViewName("api_views/animalHospital");      
        modelAndView.addObject("kakao_js_key", kakao_js_key);  
        modelAndView.addObject("dong", dong);  
        return modelAndView;
    }
    
    @RequestMapping(value="/animalHospital.do", method = { RequestMethod.POST})
    public String hospitalMAP(HospitalTO to) {
        ModelAndView mav = new ModelAndView();
        String jsonData ="";
       try {
		jsonData = petopiaApiService.coordToAddr(to.getSTREET_ADDR());
		
       } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        
        return jsonData;
    }

    // 여행지
    @RequestMapping("/trip.do")
    public ModelAndView trip(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String part = request.getParameter("part");
        String cpage = request.getParameter("cpage");
        String seq = request.getParameter("seq");
        List<TripTO> lists = new ArrayList<>();        
        if(part == null) {
        	lists = tripDao.searchTrip();
        } else {
        	TripTO to = new TripTO();
        	to.setPARTNAME(part);
        	lists = tripDao.searchTripPartname(to);
        }

        List<TripTO> count = tripDao.tripCount();
        modelAndView.addObject("lists", lists);
        modelAndView.addObject("count", count);
        modelAndView.addObject("part", part);
        modelAndView.addObject("seq", seq);
        modelAndView.setViewName("api_views/trip");
        return modelAndView;
    }

    /*
    @RequestMapping(value="/trip.do/detail", method = { RequestMethod.POST})
    public TripTO tripDetail(TripTO to) {
        ModelAndView modelAndView = new ModelAndView();
        String petopia = "petopia";
        to = tripDao.searchTripTitle(to);
        modelAndView.addObject("to", to);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("to",to);
        return to;
    }
    */
    
    // 유기동물

    @RequestMapping("/abandonment.do")
    public ModelAndView abandonment(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String cpage = request.getParameter("cpage");
        String seq = request.getParameter("seq");
        String kind = request.getParameter("kind");
        List<AbandonmentTO> lists = new ArrayList<>();  
        if(kind == null) {
            lists = abandonmentDAO.searchAbandonment();;
        } else {
            lists = abandonmentDAO.searchKindAbandonment(kind);
        }  
        modelAndView.addObject("lists", lists);
        modelAndView.addObject("seq", seq);
        modelAndView.addObject("kind", kind);
        modelAndView.addObject("cpage", cpage);
        modelAndView.addObject("kakao_js_key", kakao_js_key);  
        modelAndView.setViewName("api_views/abandonment");
        return modelAndView;
    }

    
    // DB생성
    
    @RequestMapping("/createAbandonment.do")    
    public String createAbandonmentDB(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        
        petopiaApiService.abandonment();
              
        return "생성완료";
    }
    
    @RequestMapping("/createTrip.do")    
    public String createTripDB(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        petopiaApiService.trip();
              
        return "생성완료";
    }

    
    @RequestMapping("/createHospital.do")
    public String createHospitalDB() {
        String json = "";
        try {
        	hospitalDao.animalHospitalDBCreate();
			hospitalDao.animalHospitalDBInsert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        return "생성완료";
    }
    
}
