package model.services;

import java.util.List;

import model.dao.CandidateDao;
import model.dao.DaoFactory;
import model.entities.Candidate;

public class CandidateService {
	
	private CandidateDao dao = DaoFactory.createCandidateDao();
	
	public List<Candidate> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Candidate obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Candidate obj) {
		dao.deleteById(obj.getId());
	}
}
