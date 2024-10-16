package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CandidateDao;
import model.entities.Candidate;

public class CandidateDaoJDBC implements CandidateDao{
	
	private Connection conn;
	
	public CandidateDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Candidate obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO candidates "+
					"(name, party, number, date_of_birth) "+
					"VALUES "+
					"(?, ?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, obj.getName());
			st.setString(2, obj.getParty());
			st.setInt(3, obj.getNumber());
			st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime()));
			
			int rowsAffectedd = st.executeUpdate();
			
			if(rowsAffectedd > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Candidate obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE candidates "+
			        "SET name = ?, party = ?, number = ?, date_of_birth = ? "+
					"WHERE id = ? "
					);
			st.setString(1, obj.getName());
			st.setString(2, obj.getParty());
			st.setInt(3, obj.getNumber());
			st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setInt(5, obj.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}	
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM candidates WHERE id = ?");
			
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Candidate findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM candidates WHERE id = ?"
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Candidate obj = instantiateCandidate(rs);
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}


	@Override
	public List<Candidate> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM candidates ORDER BY name"
					);
			rs = st.executeQuery();
			
			List<Candidate> list = new ArrayList<>();
			
			while(rs.next()) {
				Candidate obj = instantiateCandidate(rs);
				list.add(obj);
			}
			return list;			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public Candidate instantiateCandidate(ResultSet rs)throws SQLException {
		Candidate obj = new Candidate();
		obj.setId(rs.getInt("id"));
		obj.setName(rs.getString("name"));
		obj.setParty(rs.getString("party"));
		obj.setNumber(rs.getInt("number"));
		obj.setNumVotes(rs.getInt("num_votes"));
		obj.setBirthDate(new java.util.Date(rs.getTimestamp("date_of_birth").getTime()));
		return obj;
	}

	@Override
	public Candidate findByNumber(String number) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM candidates WHERE number = ?");
			st.setString(1, number);
			
			rs = st.executeQuery();
			if(rs.next()) {
				Candidate obj = instantiateCandidate(rs);
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void addVote(String number) {
	    PreparedStatement st = null;
	    try {
	        st = conn.prepareStatement(
	                "UPDATE candidates " +
	                "SET num_votes = num_votes + 1 " +
	                "WHERE number = ?"
	        );
	        st.setString(1, number);
	        int rowsAffected = st.executeUpdate();
	        
	        if (rowsAffected == 0) {
	            throw new DbException("No candidate found with the given number!");
	        }
	    }
	    catch(SQLException e) {
	        throw new DbException(e.getMessage());
	    }
	    finally {
	        DB.closeStatement(st);
	    }    
	}

}
