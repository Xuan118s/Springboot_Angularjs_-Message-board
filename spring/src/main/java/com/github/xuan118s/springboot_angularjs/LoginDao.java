package com.github.xuan118s.springboot_angularjs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	public int logincheck(LoginOrder loginorder) {
		
		  List<LoginOrder> list = new ArrayList<>();
		  String sql = "select * from LoginNp";
		  
		  int checkrepeat=1;
		  int checkunrepeat=0;
		  
		  SqlRowSet rows = jdbcTemplate.queryForRowSet(sql,new Object[] {});
		  
		  while (rows.next()) {
			
				list.add(loginorder);
				
				int checksame=0;
				
				if(	loginorder.userName.equals(rows.getString("userName"))  ) {
					//System.out.println("No!!");
					checksame++;
				}if(loginorder.userPass.equals(rows.getString("userPass"))  ) {
					//System.out.println("No!!");
					checksame++;
				}
				if(checksame==2) {return checkrepeat;}
				
			}
		  return checkunrepeat;
	}
	
	public int Registercheck(LoginOrder loginorder) {
		
		  List<LoginOrder> list = new ArrayList<>();
		  String sql = "select * from LoginNp";
		  
		  int checkrepeat=1;
		  int checkunrepeat=0;
		  
		  SqlRowSet rows = jdbcTemplate.queryForRowSet(sql,new Object[] {});
		  
		  while (rows.next()) {
			
				list.add(loginorder);
		
				if(	loginorder.userName.equals(rows.getString("userName"))  ) {
					//System.out.println("No!!");
					return checkrepeat;
				}
			}
		  return checkunrepeat;
	}

	public void Registersave(LoginOrder loginorder) {
		
		  jdbcTemplate.update("EXECUTE New_LoginMember ?,?",loginorder.userName,loginorder.userPass);

	}
	
}
