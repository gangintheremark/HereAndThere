package com.ssafy.trip.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Content;
import com.ssafy.trip.model.dto.Gugun;
import com.ssafy.trip.model.dto.Search;
import com.ssafy.trip.model.dto.Sido;


public interface TripService {
	public List<Attraction> getAttListByParams(Search info) throws Exception;
	public List<Sido> getSido() throws SQLException;
	public List<Gugun> getGugun(int sido_code) throws SQLException;
	public List<Content> getContent() throws SQLException;
	public String getContentName(int contentTypeId) throws SQLException;
	
}
