package com.github.xuan118s.springboot_angularjs;
import java.util.Date;

public class LoginOrder {


		public String userName;

		public String userPass;
		
		public String getUserName() {
			return userName;
		}



		public void setUserName(String userName) {
			this.userName = userName;
		}



		public String getUserPass() {
			return userPass;
		}



		public void setUserPass(String userPass) {
			this.userPass = userPass;
		}



	

		
		@Override
		public String toString() {
			return "Login [name=" + userName + ", pass="+userPass+"]";
			}
	    
	

}
