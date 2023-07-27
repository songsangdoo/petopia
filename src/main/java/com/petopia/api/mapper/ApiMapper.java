 package com.petopia.api.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.petopia.api.model.AbandonmentTO;
import com.petopia.api.model.HospitalTO;
import com.petopia.api.model.TripTO;


public interface ApiMapper {

	// 동물병원DB
	@Insert("create table ANIMAL_HOSPITAL_DATA(MANAGE_NUM  Varchar(20), LOC_ADDR TEXT, STREET_ADDR TEXT, "
			+ "STREET_ZIPCODE Varchar(8), BUSINESS_NAME TEXT, LONGITUDEL Varchar(20), LATITUDE Varchar(20))")
	public void createHospitalDB();
	
	@Insert("INSERT INTO ANIMAL_HOSPITAL_DATA VALUES (#{MANAGE_NUM},#{LOC_ADDR},#{STREET_ADDR},"
			+ "#{STREET_ZIPCODE},#{BUSINESS_NAME},#{LONGITUDEL},#{LATITUDE})")
	public int amimalHospitalInsert(HospitalTO to);
	
	
	@Select("SELECT * FROM ANIMAL_HOSPITAL_DATA WHERE STREET_ADDR LIKE #{dong}")
	public ArrayList<HospitalTO> searchHospital(String dong);
	
	// 여행DB
	@Insert("create table TRIP_ANIMAL_DATA(CONTENTSEQ Int(4), AREANAME Varchar(5), PARTNAME Varchar(10), "
			+ "TITLE Varchar(30), ADDRESS TEXT, LATITUDE TEXT, LONGITUDE TEXT, TEL Varchar(20), KEYWORD TEXT, USEDTIME TEXT, "
			+ "HOMEPAGE TEXT, CONTENT TEXT, MAINFACILITY TEXT, USEDCOST TEXT, POLICYCAUTIONS TEXT, EMERGENCYRESPONSE TEXT, MEMO TEXT, "
			+ "PETWEIGHT Varchar(5), PARKINGFLAG Char(1), IMAGE TEXT)")
	public void createTriplDB();
	
	@Insert("INSERT INTO TRIP_ANIMAL_DATA VALUES (#{CONTENTSEQ},#{AREANAME},#{PARTNAME},"
			+ "#{TITLE},#{ADDRESS},#{LATITUDE},#{LONGITUDE},"
			+ "#{TEL},#{KEYWORD},#{USEDTIME},#{HOMEPAGE},"
			+ "#{CONTENT},#{MAINFACILITY},#{USEDCOST},#{POLICYCAUTIONS},"
			+ "#{EMERGENCYRESPONSE},#{MEMO},#{PETWEIGHT},#{PARKINGFLAG},#{IMAGE})"
			)
	public int tripInsert(TripTO to);

	@Select("SELECT * FROM TRIP_ANIMAL_DATA")
	public ArrayList<TripTO> searchTrip();
	
	@Select("SELECT * FROM TRIP_ANIMAL_DATA WHERE PARTNAME LIKE #{PARTNAME}")
	public ArrayList<TripTO> searchTripPartname(TripTO to);

	@Select("SELECT * FROM TRIP_ANIMAL_DATA WHERE TITLE LIKE #{TITLE}")
	public TripTO searchTripTitle(TripTO to);

	
	@Select("SELECT PARTNAME, COUNT(*) AS COUNT FROM TRIP_ANIMAL_DATA GROUP BY PARTNAME")
	public ArrayList<TripTO> tripCount();
	
	// 유기동물DB
	@Insert("create table ABANDONMENT_ANIMAL_DATA(DESERTIONNO Varchar(20),"
			+ "FILENAME TEXT,  HAPPENDT Varchar(8), HAPPENPLACE Varchar(50), KINDCD Varchar(30),"
			+ "COLORCD Varchar(20), AGE Varchar(20), WEIGHT Varchar(20), NOTICENO Varchar(30), NOTICESDT Varchar(8),"
			+ "NOTICEEDT Varchar(8), POPFILE TEXT, PROCESSSTATE Varchar(20), SEXCD char(1), NEUTERYN char(1), SPECIALMARK TEXT,"
			+ "CARENM Varchar(20), CARETEL Varchar(20), CAREADDR TEXT, ORGNM Varchar(20), CHARGENM Varchar(20), OFFICETEL Varchar(20))")
	public void createAbandonmentDB();
	
	@Insert("INSERT INTO ABANDONMENT_ANIMAL_DATA VALUES(#{DESERTIONNO},#{FILENAME},#{HAPPENDT},"
			+ "#{HAPPENPLACE},#{KINDCD},#{COLORCD},#{AGE},"
			+ "#{WEIGHT},#{NOTICENO},#{NOTICESDT},#{NOTICEEDT},"
			+ "#{POPFILE},#{PROCESSSTATE},#{SEXCD},#{NEUTERYN},"
			+ "#{SPECIALMARK},#{CARENM},#{CARETEL},#{CAREADDR},"
			+ "#{ORGNM}, #{CHARGENM},#{OFFICETEL})")
	public int abandonmentInsert(AbandonmentTO to);

	@Select("SELECT DISTINCT * FROM ABANDONMENT_ANIMAL_DATA WHERE processState = '보호중'")
	public ArrayList<AbandonmentTO> searchAbandonment();

	@Select("SELECT DISTINCT * FROM ABANDONMENT_ANIMAL_DATA WHERE processState = '보호중' and kINDCD LIKE #{KINDCD}")
	public ArrayList<AbandonmentTO> searchKindAbandonment(AbandonmentTO to);
}