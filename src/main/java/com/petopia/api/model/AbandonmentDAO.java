package com.petopia.api.model;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petopia.api.mapper.ApiMapper;


@Repository
public class AbandonmentDAO {

    @Autowired
    private ApiMapper mapper;
    
    public List<AbandonmentTO> searchAbandonment() { 	
    	ArrayList<AbandonmentTO> grouplists = (ArrayList)mapper.searchAbandonment();    	
    	return grouplists;
    }

    public List<AbandonmentTO> searchKindAbandonment(String kind) {
    	AbandonmentTO to = new AbandonmentTO();
    	if(kind.equals("개")) {
    		to.setKINDCD("[개]%");
    	} else if(kind.equals("고양이")) {
    		to.setKINDCD("[고양이]%");
    	} else if(kind.equals("기타")) {
    		to.setKINDCD("[기타%");
    	}
    	ArrayList<AbandonmentTO> grouplists = (ArrayList)mapper.searchKindAbandonment(to);    	
    	//System.out.println(grouplists);
    	return grouplists;
    }
    
    public List<AbandonmentTO> abandonmentCount() { 	
    	ArrayList<AbandonmentTO> grouplists = (ArrayList)mapper.tripCount();
    	return grouplists;
    }

	public int abandonmentDBInsert(AbandonmentTO to) {
		int insertValue = 0;
		insertValue += mapper.abandonmentInsert(to);
		return insertValue;
	}
	
	public void abandonmentDBCreate() {
		mapper.createAbandonmentDB();
        System.out.println("table 생상완료");
		
	}

}
