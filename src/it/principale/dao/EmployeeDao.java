package it.principale.dao;  

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

  
public class EmployeeDao {  
	private static Logger log= Logger.getLogger(EmployeeDao.class);
	
	
	private JdbcTemplate jdbcTemplateRiferimento;  
	private NamedParameterJdbcTemplate jdbcParametrico;  
  
	public EmployeeDao(NamedParameterJdbcTemplate jdbcTemplateParametrico) {
		this.jdbcParametrico=jdbcTemplateParametrico;
	}
	
	public void setJdbcTemplateRiferimento(JdbcTemplate jdbcTemplate) {  
		log.debug("Chiamato setJdbcTemplateRiferimento ");
		this.jdbcTemplateRiferimento = jdbcTemplate;  
	}  
  
	public int saveEmployee(Employee e){  
		String query="insert into employee values('"+e.getId()+"','"+e.getName()+"','"+e.getSalary()+"')"; 
		return jdbcTemplateRiferimento.update(query);  
	}  
	public int updateEmployee(Employee e){  
		String query="update employee set name='"+e.getName()+"',salary='"+e.getSalary()+"' where id='"+e.getId()+"' ";  
		return jdbcTemplateRiferimento.update(query);  
	}  
	public int deleteEmployee(Employee e){  
		String query="delete from employee where id='"+e.getId()+"' ";  
		return jdbcTemplateRiferimento.update(query);  
	}  
	
	public List<Employee> getAllEmployees(){
		String query="SELECT * from employee";
		return jdbcTemplateRiferimento.query(query, new ResultSetExtractor<List<Employee>>(){

			@Override
			public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Employee> list=new ArrayList<Employee>();  
		        while(rs.next()){  
		        Employee e=new Employee();  
		        e.setId(rs.getInt("id"));  
		        e.setName(rs.getString("name"));  
		        e.setSalary(rs.getFloat("salary"));  
		        list.add(e);  
		        }  
		        return list;  
			}			
		});
	}
	
	
	public Boolean saveEmployeeByPreparedStatement(Employee e){  
	    String query="insert into employee values(?,?,?)";
	    
	    return jdbcTemplateRiferimento.execute(query,new PreparedStatementCallback<Boolean>(){  
	  
	    	@Override
	    	public Boolean doInPreparedStatement(java.sql.PreparedStatement ps) throws SQLException, DataAccessException {
			   ps.setInt(1,e.getId());  
		       ps.setString(2,e.getName());  
		       ps.setFloat(3,e.getSalary());         
		       return ps.execute();  
	    	}  
	    });  
	}
	
	public List<Employee> getEmployeeByName(String nome){  // uso PreparedStatement 
	    String query="SELECT * FROM employee WHERE employee.name=?";
	    
	    return jdbcTemplateRiferimento.execute(query,new PreparedStatementCallback<List<Employee>>(){  
	  
	    	@Override
	    	public List<Employee> doInPreparedStatement(java.sql.PreparedStatement ps) throws SQLException, DataAccessException {
			   ps.setString(1,nome);          
		       ResultSet rs=ps.executeQuery();
		       List<Employee> list=new ArrayList<Employee>();
		       while(rs.next()) {
		    	   list.add(new Employee(rs.getInt("id"), rs.getString("name"),rs.getFloat("salary")));
		       }
		       rs.close();
		       return list;
	    	}  
	    });  
	}
	
	
	public List<Employee> getAllEmployeesRowMapper(){  
		 return jdbcTemplateRiferimento.query("select * from employee",new RowMapper<Employee>(){  
			 
		    @Override  
		    public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {  
		        Employee e=new Employee();  
		        e.setId(rs.getInt("id"));  
		        e.setName(rs.getString("name"));  
		        e.setSalary(rs.getInt("salary"));  
		        return e;  
		    }  
		  });  
	}  
	
	
	//ORA USO NamedParameterJdbcTemplate
	public  void savewithParamater (Employee e){  
		String query="insert into employee values (:id,:name,:salary)";  
		  
		Map<String,Object> map=new HashMap<String,Object>();  
		map.put("id",e.getId());  
		map.put("name",e.getName());  
		map.put("salary",e.getSalary());  
		
		jdbcParametrico.execute(query,map,new PreparedStatementCallback<Object>() {  
		    
			@Override  
		    public Object doInPreparedStatement(PreparedStatement ps)  
		            throws SQLException, DataAccessException {  
		        return ps.executeUpdate();  
		    }  
		});  
	}
	
	//ORA USO NamedParameterJdbcTemplate
	public List<Employee> getEmployeeByNamewithParamater(String name) {
		String query="SELECT * FROM employee WHERE name= :name ";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name",name);
		return jdbcParametrico.execute(query, map, new PreparedStatementCallback<List<Employee>>(){

			@Override
			public List<Employee> doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ResultSet rs=ps.executeQuery();
				List<Employee> list=new ArrayList<Employee>();
				while(rs.next()) {
					list.add(new Employee(rs.getInt("id"),rs.getString("name"),rs.getFloat("salary")));
				}
				return list;
			}
			
		});
	}
	
	//uso BeanProperyRowMapper
	public List<Employee> findAll() {
	  String query="Select * FROM employee";
	  List<Employee> employees = jdbcTemplateRiferimento.query(query, new BeanPropertyRowMapper<Employee>(Employee.class));
	  return employees;
	}
  
}
