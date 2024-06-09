package com.ssafy.plan.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.plan.model.dao.PlanDAO;
import com.ssafy.plan.model.dto.PlanPlace;
import com.ssafy.plan.model.dto.PlanDetail;
import com.ssafy.plan.model.dto.PlanLikeRequest;
import com.ssafy.trip.model.dto.Attraction;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanDAO planDao;
	
	@Override
	public List<PlanDetail> searchByTag(int sidoCode) throws SQLException {
		return planDao.searchByTag(sidoCode);
	}

	@Override
	public List<PlanDetail> getBestPlans() throws SQLException {
		return planDao.getBestPlans();
	}

	@Override
	public List<PlanDetail> getPlansByLikes(int sidoCode) throws SQLException {
		return planDao.getPlansByLikes(sidoCode);
	}

	@Override
	public List<PlanDetail> getRecentPlans(int sidoCode) throws SQLException {
		return planDao.getRecentPlans(sidoCode);
	}

	@Override
	public PlanDetail searchByPlanId(int planId) throws SQLException {
		return planDao.searchByPlanId(planId);
	}

	@Override
	public List<Attraction> searchCourseByPlanId(int planId) throws SQLException {
		return planDao.searchCourseByPlanId(planId);
	}

	@Override
	public boolean checkPlanLike(PlanLikeRequest request) throws SQLException {
		return planDao.checkPlanLike(request);
	}

	@Override
	public void writePlan(PlanDetail planDetail) throws SQLException {
		planDao.writePlan(planDetail);
	}

	@Override
	public void writePlanCourse(PlanPlace request) throws SQLException {
		planDao.writePlanCourse(request);
	}

	@Override
	public int getPlanId() throws SQLException {
		return planDao.getPlanId();
	}

	@Override
	public void deletePlan(int planId) throws SQLException {
		planDao.deletePlan(planId);
	}

	@Override
	public void likePlan(PlanLikeRequest planLike) throws SQLException {
		planDao.likePlan(planLike);
	}

	@Override
	public void cancelLikePlan(PlanLikeRequest planLike) throws SQLException {
		planDao.cancelLikePlan(planLike);
	}

}
