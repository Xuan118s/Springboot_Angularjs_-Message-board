<h1> Springboot_Angularjs_Messageboard_2021_12_24</h1>

<h2>程式碼介紹</h2>

>主程式main介紹
<details>
	<summary>MainController.java (更新代碼)</summary>
	
	package com.github.xuan118s.springboot_angularjs;

	import java.util.HashMap;
	import java.util.Map;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.ResponseBody;

	@Controller
	public class MainController {

		@Autowired
		private LoginDao loginDao;
	//主畫面登入控制
		@GetMapping("/")
		public String indexLogin() {
			return "Login";
		}

		@PostMapping("/Logincheck")
		public @ResponseBody Map<String, Object> Logincheck(@RequestBody LoginOrder loginorder) {
			Map<String, Object> result = new HashMap<>();

	//			System.out.print(loginorder.getUserName());
	//			System.out.print(loginorder.getUserPass());
				try {
				result.put("checkok", loginDao.logincheck(loginorder));
				result.put("username", loginorder.userName);}
				catch(Exception ex) {
					System.out.println(ex.toString());}

			return result;
		}

	//主畫面註冊控制
		@GetMapping("/Register")
		public String indexRegister() {
			return "Register";
		}

		@PostMapping("/Registercheck")
		public @ResponseBody Map<String, Object> Registercheck(@RequestBody LoginOrder loginorder) {
			Map<String, Object> result = new HashMap<>();

	//			System.out.print(loginorder.getUserName());
	//			System.out.print(loginorder.getUserPass());
				try {
					result.put("checkok", loginDao.Registercheck(loginorder));}
				catch(Exception ex) {
					System.out.println(ex.toString());}

			return result;
		}

		@PostMapping("/RegisterSave")
		public @ResponseBody Map<String, Object> RegisterSave(@RequestBody LoginOrder loginorder) {
			Map<String, Object> result = new HashMap<>();

			loginDao.Registersave(loginorder);

			return result;
		}

	//留言板控制
		@Autowired
		private OrderDao orderDao;

		@GetMapping("/messageboard")
		public String index() {
			return "messageboard";
		}

		@PostMapping("/save")
		public @ResponseBody Map<String, Object> save(@RequestBody Order order) {
			Map<String, Object> result = new HashMap<>();

			orderDao.update(order);

			result.put("id", order.id);
			return result;
		}

		@PostMapping("/newdata")
		public @ResponseBody Map<String, Object> newdata(@RequestBody Order order) {
			Map<String, Object> result = new HashMap<>();

			orderDao.insert(order);

			result.put("id", order.id);
			return result;
		}

		@PostMapping("/get")
		public @ResponseBody Object get(String id) {
			return orderDao.get(id);
		}

		@PostMapping("/findAll")
		public @ResponseBody Object findAll() {
			return orderDao.findAll();
		}

		@PostMapping("/delete")
		public @ResponseBody Map<String, Object> delete(String id) {
			Map<String, Object> result = new HashMap<>();
			orderDao.delete(id);
			result.put("id", id);
			return result;
		}

	//個人編輯
		@GetMapping("/personaledit")
		public String personaleedit() {
			return "personaledit";
		}

		@PostMapping("/findpersonal")
		public @ResponseBody String findpersonal(@RequestBody String username) {
		System.out.print(username);
		return "0";
		}


		@PostMapping("/findpersonaledit")
		public @ResponseBody Object findpersonaledit(@RequestBody String username) {
			return orderDao.findpersonaledit(username);
		}

	}


</details>

>所有Order介紹
<details>
	<summary>Order.java</summary>

		package com.github.xuan118s.springboot_angularjs;
		import java.util.Date;

		public class Order {
			    public int id;
			    public String name;
			    public String messages;
			    public Date create_date; 

				public int getId() {
					return id;
				}
				public void setId(int id) {
					this.id = id;
				}
				public String getName() {
					return name;
				}
				public void setName(String name) {
					this.name = name;
				}
				public String getMessage() {
					return messages;
				}
				public void setMessage(String messages) {
					this.messages = messages;
				}
				public Date getcreate_date() {
					return create_date;
				}
				public void setcreate_date(Date create_date) {
					this.create_date = create_date;
				}
		}

# 功用與介紹
1.定義一個public get/set(讀/寫) ，方便外部訪問<br/>
2.所需定義值有ID(身分編號)、NAME(用戶名稱)、MESSAGE(留言訊息)、CREATE_DATE(創建留言時間)<br/>
3. toString() 做資料庫在Java裡的列表頁印與自我檢查<br/>
</details>

<details>
<summary>LoginOrder.java</summary>
	
	package com.github.xuan118s.springboot_angularjs;
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
	}
# 功用與介紹

1.定義一個public get/set(讀/寫) ，方便外部訪問<br/>
2.所需定義值有userName(登入者名稱)、userPass(登入者密碼)
</details>

>所有Dao介紹

<details>
<summary>OrderDao.java (更新代碼)</summary>
	
	package com.github.xuan118s.springboot_angularjs;

	import java.util.ArrayList;
	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.jdbc.core.JdbcTemplate;
	import org.springframework.jdbc.support.rowset.SqlRowSet;
	import org.springframework.stereotype.Repository;

	@Repository
	public class OrderDao {

		@Autowired
		private JdbcTemplate jdbcTemplate;

		public List<Order> findAll() {
			List<Order> list = new ArrayList<>();
			//1.按照編號做列表排序
			String sql = "SELECT * FROM MemTable ORDER BY ID ASC";
			//2.新增Try...catch防止sqlerror讓程式碼collapse	
			try {
				SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, new Object[] {});
				while (rows.next()) {
					Order order = new Order();
					list.add(order);
					order.id = rows.getInt("ID");
					order.name = rows.getString("NAME");
					order.messages = rows.getString("MESSAGE");
					order.create_date = rows.getDate("DATETIME");}}
			catch(Exception ex) {
				System.out.println(ex.toString());}

			return list;
		}

		public void insert(Order order) {
			  //3. 轉為使用預存程式
			  jdbcTemplate.update("EXECUTE New_Member ?,?",order.name,order.messages);

			  String sql = "select * from MemTable WHERE NAME=? AND MESSAGE=? ";
			  try {
				  List<Order> list = new ArrayList<>();
				  SqlRowSet rows = jdbcTemplate.queryForRowSet(sql,order.name,order.messages);
				  while (rows.next()) {
					list.add(order);
					order.id= rows.getInt("ID");
					order.name = rows.getString("NAME");
					order.messages = rows.getString("MESSAGE");
					order.create_date = rows.getDate("DATETIME");}}
			catch(Exception ex) {
					System.out.println(ex.toString());}
		}

		public void update(Order order) {
			try {//同上使用預存程式
				jdbcTemplate.update("EXECUTE Save_Member ?,?",
					order.messages,order.id);}
			catch(Exception ex) {
				System.out.println(ex.toString());}
		}

		public Order get(String id) {
			Order order = null;
			String sql = "SELECT * FROM MemTable WHERE ID=?";

			try {
				SqlRowSet rows = jdbcTemplate.queryForRowSet(sql,id);
				while (rows.next()) {
					order = new Order();
					order.id = rows.getInt("ID");
					order.name = rows.getString("NAME");
					order.messages = rows.getString("MESSAGE");
					order.create_date = rows.getDate("DATETIME");}}
			catch(Exception ex) {
				System.out.println(ex.toString());}
			return order;
		}

		public void delete(String id) {
			String sql = "DELETE FROM MemTable WHERE ID = ? ";
			try {
				jdbcTemplate.update(sql,
					 id );}
			catch(Exception ex) {
				System.out.println(ex.toString());}
		}

	}
# 功用與介紹

(更新內容筆記)
  
1.按照編號列表排序
>上一次沒有顧慮到順序問題，後來用ASC做小至大排序

2.Try...catch例外處理
>程式中的異常表明一些錯誤或者異常情況發生了，異常如果沒有被處理，繼續程式流程是沒有意義的，也可以方便編程者做檢查與除錯。

3.SQL Stored Procedure
>示範連結:https://zh.wikipedia.org/wiki/%E5%AD%98%E5%82%A8%E7%A8%8B%E5%BA%8F  
>使用方法:"EXECUTE [預存程式名稱] ?,InputIndex"
<hr>
(函式庫簡單功能介紹)

1.findAll()
>1.查詢所有資料  
>2.jdbcTemplate.queryForRowSet方法的調用，返回的則是SqlRowSet對象，進而給予rows變數<br>
>3.rows.next()幾筆資料去迴圈，並且送資料到我們的order()，資料從rows讀取並給予

2.insert()
>1.新增新的資料<br>
>2.jdbcTemplate.queryForRowSet方法的調用，返回的則是SqlRowSet對象，進而給予rows變數<br>
>3.讀取input框裡的值進行新增資料

3.update()
>1.更新資料<br>
>2.資料從NAME與MESSAGE從input裡讀取

4.get()
>1.讀取資料<br>
>2.後續步驟同insert()的方法<br>

5.delete()
>1.刪除指定ID的所有資料欄資料

</details>
	
<details>
<summary>LoginDao.java</summary>

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
						checksame++;
					}if(
						loginorder.userPass.equals(rows.getString("userPass"))  ) {						
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
					if(	
						loginorder.userName.equals(rows.getString("userName"))  ) {
						return checkrepeat;
					}
				}
			  return checkunrepeat;
		}

		public void Registersave(LoginOrder loginorder) {
			  jdbcTemplate.update("EXECUTE New_LoginMember ?,?",loginorder.userName,loginorder.userPass);
		}

	}


# 功用與介紹
(函式庫簡單功能介紹)<br/>
1.logincheck()<br/>
>1.用於判斷登入時該帳號有無註冊過的判斷<br/>
>2.並且判斷密碼是不是正確的

2.Registercheck()<br/>
>1.用於判斷註冊時該帳號名稱是否有重複過的判斷

3.Registersave()<br/>
>1.用於儲存一個新的帳號

</details>
<hr>

<h1>實體範例演示</h1>
<h2>註冊畫面</h2>
	
<h3>1. 註冊帳號未輸入提醒</h3>
	
![image](https://github.com/Xuan118s/Springboot_Angularjs_Messageboard_2021_12_24/blob/main/GIF/%E8%A8%BB%E5%86%8A%E5%B8%B3%E8%99%9F%E7%A9%BA%E7%99%BD%E9%8C%AF%E8%AA%A4.gif)
	
<h3>2. 註冊密碼重複不一致提醒</h3>
	
![image](https://github.com/Xuan118s/Springboot_Angularjs_Messageboard_2021_12_24/blob/main/GIF/%E8%A8%BB%E5%86%8A%E5%B8%B3%E8%99%9F%E9%87%8D%E8%A4%87%E9%8C%AF%E8%AA%A4.gif)
	
<h3>3. 註冊成功後跳轉</h3>
	
	
![image](https://github.com/Xuan118s/Springboot_Angularjs_Messageboard_2021_12_24/blob/main/GIF/%E8%A8%BB%E5%86%8A%E5%B8%B3%E8%99%9F%E6%88%90%E5%8A%9F%E8%B7%B3%E8%BD%89.gif)
	
<h2>登入畫面</h2>
	
<h3>1. 登入帳號未輸入提醒</h3>
	
![image](https://github.com/Xuan118s/Springboot_Angularjs_Messageboard_2021_12_24/blob/main/GIF/%E6%9C%AA%E8%BC%B8%E5%85%A5%E8%B3%87%E6%96%99%E9%8C%AF%E8%AA%A4.gif)
	
<h3>2. 登入帳號未註冊提示</h3>
	
![image](https://github.com/Xuan118s/Springboot_Angularjs_Messageboard_2021_12_24/blob/main/GIF/%E6%9C%AA%E8%A8%BB%E5%86%8A%E9%81%8E%E7%9A%84%E5%B8%B3%E8%99%9F.gif)

<h3>3. 登入成功跳轉</h3>
	
![image](https://github.com/Xuan118s/Springboot_Angularjs_Messageboard_2021_12_24/blob/main/GIF/%E7%99%BB%E5%85%A5%E6%88%90%E5%8A%9F%E8%B7%B3%E8%BD%89.gif)
	
<h2>編輯畫面</h2>
	
<h3>1. 新增、刪除、修改資料</h3>
	
![image](https://github.com/Xuan118s/Springboot_Angularjs_Messageboard_2021_12_24/blob/main/GIF/%E6%96%B0%E5%A2%9E%E5%88%AA%E9%99%A4%E4%BF%AE%E6%94%B9.gif)
	
<h2>其他動作畫面</h2>

<h3>1.登出並清除資料</h3>
	
![image](https://github.com/Xuan118s/Springboot_Angularjs_Messageboard_2021_12_24/blob/main/GIF/%E7%99%BB%E5%87%BA.gif)
