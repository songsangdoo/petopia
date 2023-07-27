package com.petopia.api.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.api.mapper.ApiMapper;


@Repository
public class TripDAO {

    @Autowired
    private ApiMapper mapper;
    
    public List<TripTO> searchTrip() { 	
    	ArrayList<TripTO> grouplists = (ArrayList)mapper.searchTrip();    	
    	return grouplists;
    }
    
    public List<TripTO> searchTripPartname(TripTO to) { 	
    	ArrayList<TripTO> grouplists = (ArrayList)mapper.searchTripPartname(to);    	
    	return grouplists;
    }
    
    public List<TripTO> tripCount() { 	
    	ArrayList<TripTO> grouplists = (ArrayList)mapper.tripCount();
    	return grouplists;
    }

    public TripTO searchTripTitle(TripTO to) { 	
    	to = mapper.searchTripTitle(to);    	
    	return to;
    }

	public int tripDBInsert(TripTO to) {
		int insertValue = 0;
		insertValue += mapper.tripInsert(to);
        return insertValue;
		
	}
	
	public void tripDBCreate() {
		mapper.createTriplDB();
        System.out.println("table 생상완료");
		
	}

}
