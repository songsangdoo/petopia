package com.petopia.pointshop.model;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.pointshop.mapper.PointShopMapperInter;

@Repository
@MapperScan( basePackages = { "com.petopia.pointshop.mapper" } )
public class GradeDAO {
	
	@Autowired
	private PointShopMapperInter pointShopMapperInter;
	
	public GradeTO gradeCheck( String userGrade ) {
		GradeTO gradeChecks = pointShopMapperInter.gradeCheck( userGrade );
		return gradeChecks;
	}
	
	// question_view
	public List<GradeTO> question_gradeCheck(){
		List<GradeTO> gradeChecks = pointShopMapperInter.question_gradeCheck();
		
		return gradeChecks;
	}
}
