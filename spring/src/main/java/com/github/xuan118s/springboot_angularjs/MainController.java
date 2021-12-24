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
