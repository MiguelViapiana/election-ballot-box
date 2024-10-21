package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Candidate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String party;
	private Integer number;
	private Date birthDate;
	private Integer numVotes = 0;
	private double percentage;
	
	public Candidate() {
		
	}

	public Candidate(Integer id, String name,Date birthDate , String party, Integer number) {
		this.id = id;
		this.name = name;
		this.party = party;
		this.number = number;
		this.setBirthDate(birthDate);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(Integer numVotes) {
		this.numVotes = numVotes;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidate other = (Candidate) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Candidate [id=" + id + ", name=" + name + ", party=" + party + ", number=" + number + "]";
	}
	
	
}
