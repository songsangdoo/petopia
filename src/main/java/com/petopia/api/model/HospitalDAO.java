package com.petopia.api.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.api.mapper.ApiMapper;


@Repository
public class HospitalDAO {
	
    @Autowired
    private ApiMapper mapper;
    
    public List<HospitalTO> searchHospital(String dong) { 	
    	ArrayList<HospitalTO> grouplists = (ArrayList)mapper.searchHospital("%" + dong + "%");    	
    	return grouplists;
    }
    

    public void animalHospitalDBCreate() { 	
    	try {
			mapper.createHospitalDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("table 생성완료");
    }
	
	public void animalHospitalDBInsert() {
		int insertValue = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/csv/fulldata_02_03_01_P_동물병원.csv"));
	        String data = "";
			while((data = br.readLine()) != null) { 
			String[] strs = data.split("\",");
			
			if(strs[7].replace("\"", "").equals("01")) {
				HospitalTO to = new HospitalTO();
				
				to.setMANAGE_NUM(strs[4].replace("\"", ""));
				to.setLOC_ADDR(strs[18].replace("\"", ""));
				to.setSTREET_ADDR(strs[19].replace("\"", ""));
				to.setSTREET_ZIPCODE(strs[20].replace("\"", ""));
				to.setBUSINESS_NAME(strs[21].replace("\"", ""));
				to.setLONGITUDEL(strs[26].replace("\"", ""));
				to.setLATITUDE(strs[27].replace("\"", ""));
				
				insertValue += mapper.amimalHospitalInsert(to);
				}		
	        }

            System.out.println("insert된 데이터 갯수 : " + insertValue);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
