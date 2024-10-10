package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Candidate;

public class CandidateService {
	
	public List<Candidate> findAll(){
		List<Candidate> list = new ArrayList<>();
		list.add(new Candidate(1, "Jair Messias Bolsonaro", "PL", 22));
		list.add(new Candidate(2, "Luiz Inácio Lula Da Silva", "PT", 13));
		list.add(new Candidate(3, "Enéas Carneiro", "PRONA", 56));
		return list;
	}
}
