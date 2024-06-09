package com.ssafy.plan.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.plan.model.dto.PlanPlace;
import com.ssafy.plan.model.dto.PlanDetail;
import com.ssafy.plan.model.dto.PlanLikeRequest;
import com.ssafy.trip.model.dto.Attraction;

@Mapper
public interface PlanDAO {
	List<PlanDetail> searchByTag(int sidoCode) throws SQLException;

	List<PlanDetail> getBestPlans() throws SQLException;

	List<PlanDetail> getPlansByLikes(int sidoCode) throws SQLException;

	List<PlanDetail> getRecentPlans(int sidoCode) throws SQLException;

	PlanDetail searchByPlanId(int planId) throws SQLException;
	
	List<Attraction> searchCourseByPlanId(int planId) throws SQLException;
	
	boolean checkPlanLike(PlanLikeRequest request) throws SQLException;
	
	void writePlan(PlanDetail planDetail) throws SQLException;
	
	void writePlanCourse(PlanPlace request) throws SQLException;
	
	int getPlanId() throws SQLException;
	
	void deletePlan(int planId) throws SQLException;

	void likePlan(PlanLikeRequest planLike) throws SQLException;

	void cancelLikePlan(PlanLikeRequest planLike) throws SQLException;
}
