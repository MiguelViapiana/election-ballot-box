package model.dao;

import db.DB;
import model.dao.impl.CandidateDaoJDBC;

public class DaoFactory {

	public static CandidateDao createCandidateDao() {
		return new CandidateDaoJDBC(DB.getConnection());
	}
}
