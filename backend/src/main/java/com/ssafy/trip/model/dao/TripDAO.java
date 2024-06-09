package com.ssafy.trip.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Content;
import com.ssafy.trip.model.dto.Gugun;
import com.ssafy.trip.model.dto.Search;
import com.ssafy.trip.model.dto.Sido;

@Mapper
public interface TripDAO {
	public List<Attraction> getAttListByParams(Search info);
	public List<Sido> getSido() throws SQLException;
	public List<Gugun> getGugun(int sido_code) throws SQLException;
	public List<Content> getContent() throws SQLException;
	public String getContentName(int contentTypeId) throws SQLException;
}
