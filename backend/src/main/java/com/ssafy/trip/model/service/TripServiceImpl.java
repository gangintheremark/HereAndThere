package com.ssafy.trip.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.TripDAO;
import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Content;
import com.ssafy.trip.model.dto.Gugun;
import com.ssafy.trip.model.dto.Search;
import com.ssafy.trip.model.dto.Sido;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripDAO tripDao;
	
	@Override
	public List<Attraction> getAttListByParams(Search info) throws Exception {
		return tripDao.getAttListByParams(info);
	}

	@Override
	public List<Sido> getSido() throws SQLException {
		return tripDao.getSido();
	}

	@Override
	public List<Gugun> getGugun(int sido_code) throws SQLException {
		return tripDao.getGugun(sido_code);
	}

	@Override
	public List<Content> getContent() throws SQLException {
		return tripDao.getContent();
	}

	@Override
	public String getContentName(int contentTypeId) throws SQLException {
		return tripDao.getContentName(contentTypeId);
	}

}
