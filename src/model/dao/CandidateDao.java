package model.dao;

import java.util.List;

import model.entities.Candidate;

public interface CandidateDao {

	void insert(Candidate obj);
	void update(Candidate obj);
	void deleteById(Integer id);
	Candidate findById(Integer id);
	Candidate findByNumber(String number);
	List<Candidate> findAll();
}
