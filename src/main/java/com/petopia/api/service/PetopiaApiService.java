package com.petopia.api.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.petopia.api.model.AbandonmentDAO;
import com.petopia.api.model.AbandonmentTO;
import com.petopia.api.model.TripDAO;
import com.petopia.api.model.TripTO;

@Service
@PropertySource("classpath:API-KEY.properties")
public class PetopiaApiService {

    @Value("${kakao-rest-key}")
    private String kakao_rest_key;
    @Value("${data-encoding-key}")
    private String data_encoding_key;
    @Autowired
    private TripDAO tripDao;
    @Autowired
    private AbandonmentDAO abandonmentDAO;
    
    // 주소에서 위도, 경도 가져오기
	public String coordToAddr(String LOC_ADDR){
	    String json = "";
	    try {	    	
			String addr = URLEncoder.encode(LOC_ADDR, "UTF-8"); // 매개변수 인코딩시키기
			String url ="https://dapi.kakao.com/v2/local/search/address.json?query=" + addr;
			json = getCoordJSONData(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return json;
	}

	public String getCoordJSONData(String apiurl) throws Exception{
		String jsonData = "";
		String buf;
		
		URL url = new URL(apiurl);
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		String auth = "KakaoAK " + kakao_rest_key;
		conn.setRequestMethod("GET");
		conn.setRequestProperty("X-Requested-With", "curl");
		conn.setRequestProperty("Authorization", auth);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		while ((buf = br.readLine()) != null) {
			jsonData += buf;
		}		
		return jsonData;
	}
	

	// 유기동물 데이터 가져오기
	
	public void abandonment(){
		abandonmentDAO.abandonmentDBCreate();
	    try {	    	
			String url ="http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic?serviceKey=" 
						+ data_encoding_key
						+ "&pageNo=1&numOfRows=1&_type=json";
			getAbandonmentTotalData(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getAbandonmentTotalData(String apiurl) throws Exception{
		String jsonData = "";
		String buf;
		
		URL url = new URL(apiurl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		while ((buf = br.readLine()) != null) {
			jsonData += buf;
		}

		JSONObject json = new JSONObject(jsonData);

		int totalCount = Integer.parseInt(json.getJSONObject("response").getJSONObject("body").getString("totalCount"));
		System.out.println("총데이터 : "+totalCount);
		getAbandonmentDetailData(totalCount);
		
	} 
	
	public void getAbandonmentDetailData(int totalCount) throws Exception{
		String jsonData = "";
		String buf;
		
		int totalInsertData = 0;
		int page = 1;
		int pageBlock = 1000;
		int totalBlock = totalCount/pageBlock;
		int remainBlock = totalCount%pageBlock;
		
		for(int i = 0; i <= totalBlock; i++) {
			if(i != totalBlock) {
				String apiURL ="http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic?serviceKey=" 
						+ data_encoding_key
						+ "&pageNo=" + page 
						+ "&numOfRows=" + pageBlock
						+"&_type=json";
				URL url = new URL(apiURL);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				while ((buf = br.readLine()) != null) {
					jsonData += buf;
				}

				JSONObject json = new JSONObject(jsonData);
				JSONArray jsonArr = json.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
				for(int j = 0; j < pageBlock; j++) {
					AbandonmentTO to = new AbandonmentTO();
					to.setDESERTIONNO(jsonArr.getJSONObject(j).getString("desertionNo"));
					to.setFILENAME(jsonArr.getJSONObject(j).getString("filename"));
					to.setHAPPENDT(jsonArr.getJSONObject(j).getString("happenDt"));
					to.setHAPPENPLACE(jsonArr.getJSONObject(j).getString("happenPlace"));
					to.setKINDCD(jsonArr.getJSONObject(j).getString("kindCd"));
					to.setCOLORCD(jsonArr.getJSONObject(j).getString("colorCd"));
					to.setAGE(jsonArr.getJSONObject(j).getString("age"));
					to.setWEIGHT(jsonArr.getJSONObject(j).getString("weight"));
					to.setNOTICENO(jsonArr.getJSONObject(j).getString("noticeNo"));
					to.setNOTICESDT(jsonArr.getJSONObject(j).getString("noticeSdt"));
					to.setNOTICEEDT(jsonArr.getJSONObject(j).getString("noticeEdt"));
					to.setPOPFILE(jsonArr.getJSONObject(j).getString("popfile"));
					to.setPROCESSSTATE(jsonArr.getJSONObject(j).getString("processState"));
					to.setSEXCD(jsonArr.getJSONObject(j).getString("sexCd"));
					to.setNEUTERYN(jsonArr.getJSONObject(j).getString("neuterYn"));
					to.setSPECIALMARK(jsonArr.getJSONObject(j).getString("specialMark"));
					to.setCARENM(jsonArr.getJSONObject(j).getString("careNm"));
					to.setCARETEL(jsonArr.getJSONObject(j).getString("careTel"));
					to.setCAREADDR(jsonArr.getJSONObject(j).getString("careAddr"));
					to.setORGNM(jsonArr.getJSONObject(j).getString("orgNm"));
					to.setOFFICETEL(jsonArr.getJSONObject(j).getString("officetel"));
					
					totalInsertData += abandonmentDAO.abandonmentDBInsert(to);
				}
				page += pageBlock;
			} else if(i == totalBlock) {

				String apiURL ="http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic?serviceKey=" 
						+ data_encoding_key
						+ "&pageNo=" + page 
						+ "&numOfRows=" + pageBlock
						+"&_type=json";
				URL url = new URL(apiURL);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				while ((buf = br.readLine()) != null) {
					jsonData += buf;
				}

				JSONObject json = new JSONObject(jsonData);
				JSONArray jsonArr = json.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
				for(int j = 0; j < remainBlock; j++) {
					AbandonmentTO to = new AbandonmentTO();
					to.setDESERTIONNO(jsonArr.getJSONObject(j).getString("desertionNo"));
					to.setFILENAME(jsonArr.getJSONObject(j).getString("filename"));
					to.setHAPPENDT(jsonArr.getJSONObject(j).getString("happenDt"));
					to.setHAPPENPLACE(jsonArr.getJSONObject(j).getString("happenPlace"));
					to.setKINDCD(jsonArr.getJSONObject(j).getString("kindCd"));
					to.setCOLORCD(jsonArr.getJSONObject(j).getString("colorCd"));
					to.setAGE(jsonArr.getJSONObject(j).getString("age"));
					to.setWEIGHT(jsonArr.getJSONObject(j).getString("weight"));
					to.setNOTICENO(jsonArr.getJSONObject(j).getString("noticeNo"));
					to.setNOTICESDT(jsonArr.getJSONObject(j).getString("noticeSdt"));
					to.setNOTICEEDT(jsonArr.getJSONObject(j).getString("noticeEdt"));
					to.setPOPFILE(jsonArr.getJSONObject(j).getString("popfile"));
					to.setPROCESSSTATE(jsonArr.getJSONObject(j).getString("processState"));
					to.setSEXCD(jsonArr.getJSONObject(j).getString("sexCd"));
					to.setNEUTERYN(jsonArr.getJSONObject(j).getString("neuterYn"));
					to.setSPECIALMARK(jsonArr.getJSONObject(j).getString("specialMark"));
					to.setCARENM(jsonArr.getJSONObject(j).getString("careNm"));
					to.setCARETEL(jsonArr.getJSONObject(j).getString("careTel"));
					to.setCAREADDR(jsonArr.getJSONObject(j).getString("careAddr"));
					to.setORGNM(jsonArr.getJSONObject(j).getString("orgNm"));
					to.setOFFICETEL(jsonArr.getJSONObject(j).getString("officetel"));
					
					totalInsertData += abandonmentDAO.abandonmentDBInsert(to);
				}
			}
		}
	    System.out.println("insert된 데이터 : " + totalInsertData);				 
	}

	
	// 여행정보 데이터 가져오기
	
	public void trip(){

		int totalInsertData = 0;
		tripDao.tripDBCreate();
	    try {	    	
			String[] partCode = {"PC01", "PC02", "PC03", "PC04", "PC05"};
			for(int i = 0; i < partCode.length; i++) {
				totalInsertData += getTripTotalData(partCode[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("insert된 데이터 : " + totalInsertData);
	}

	public int getTripTotalData(String partCode) throws Exception{
		String jsonData = "";
		String buf = "";
		String apiURL = "https://pettravel.kr/api/listPart.do?page=1&pageBlock=1&partCode=" + partCode;
		URL url = new URL(apiURL);
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		while ((buf = br.readLine()) != null) {
			jsonData += buf;
		}
		String replaceData = jsonData.substring(1).substring(0, jsonData.length()-1);
		JSONObject json = new JSONObject(replaceData);
		int totalCount = Integer.parseInt(json.getString("totalCount"));
		int page = 0;
		int pageBlock = 50;
		int totalBlock = totalCount/pageBlock;
		int remainBlock = totalCount%pageBlock;
		int totalInsertData = 0;

		 
		for(int i = 0; i < totalBlock ; i++) {	
			jsonData = "";
			apiURL = "https://pettravel.kr/api/listPart.do?page="+page+"&pageBlock="+pageBlock+"&partCode=" + partCode;
			url = new URL(apiURL);
			conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			while ((buf = br.readLine()) != null) {
				jsonData += buf;
			}
			 JSONArray jsonArray = new JSONArray(jsonData);
			 TripTO to = new TripTO();
			 for(int j = 0; j < pageBlock; j++) {
				 to.setCONTENTSEQ(jsonArray.getJSONObject(0).getJSONArray("resultList").getJSONObject(j).getString("contentSeq"));
				 totalInsertData += getTripDetailData(to, partCode);
			 }
			 page += 50; 
		}
		
		if(remainBlock != 0) {
				jsonData = "";
				apiURL = "https://pettravel.kr/api/listPart.do?page="+page+"&pageBlock="+remainBlock+"&partCode=" + partCode;
				url = new URL(apiURL);
				conn = (HttpsURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				br = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				while ((buf = br.readLine()) != null) {
					jsonData += buf;
				}
				 JSONArray jsonArray = new JSONArray(jsonData);
				 TripTO to = new TripTO();
				 int i = 0;
				 for(int j = 0; j < remainBlock; j++) {
					 to.setCONTENTSEQ(jsonArray.getJSONObject(0).getJSONArray("resultList").getJSONObject(j).getString("contentSeq"));
					 totalInsertData += getTripDetailData(to, partCode);
				 }
				
			}
		
		return totalInsertData;
		
	}

	
	public int getTripDetailData(TripTO to, String partCode) throws Exception{
		String jsonData = "";
		String buf;

		String apiURL = "https://www.pettravel.kr/api/detailSeqPart.do?partCode="+partCode+"&contentNum=" + to.getCONTENTSEQ();
		URL url = new URL(apiURL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		while ((buf = br.readLine()) != null) {
			jsonData += buf;
		}

		 JSONArray jsonArray = new JSONArray(jsonData);
		 JSONObject json = jsonArray.getJSONObject(0).getJSONObject("resultList");
		 to.setAREANAME(json.getString("areaName"));
		 to.setPARTNAME(json.getString("partName"));
		 to.setTITLE(json.getString("title"));
		 to.setKEYWORD(json.getString("keyword"));
		 to.setADDRESS(json.getString("address"));
		 to.setLATITUDE(json.getString("latitude"));
		 to.setLONGITUDE(json.getString("longitude"));
		 to.setTEL(json.getString("tel"));
		 to.setUSEDTIME(json.getString("usedTime"));
		 to.setHOMEPAGE(json.getString("homePage"));
		 to.setCONTENT(json.getString("content"));
		 to.setMAINFACILITY(json.getString("mainFacility"));
		 to.setUSEDCOST(json.getString("usedCost"));
		 to.setPOLICYCAUTIONS(json.getString("policyCautions"));
		 to.setEMERGENCYRESPONSE(json.getString("emergencyResponse"));
		 to.setMEMO(json.getString("memo"));
		 to.setPETWEIGHT(json.getString("petWeight"));
		 to.setPARKINGFLAG(json.getString("parkingFlag"));
		 if(partCode != "PC05") {
			 to.setIMAGE(json.getJSONArray("imageList").getJSONObject(0).getString("image"));
		 }
		 
		 return tripDao.tripDBInsert(to);
	}

	
}
