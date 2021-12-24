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
		
		String sql = "SELECT * FROM MemTable ORDER BY ID ASC";
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
		try {
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


//編輯的資料讀出
	public List<Order> findpersonaledit(String username) {
		List<Order> list = new ArrayList<>();
		String sql = "SELECT * FROM MemTable WHERE NAME=?";
		try {
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql,username);
		while (rows.next()) {
			Order order = new Order();
			list.add(order);
			order.id = rows.getInt("ID");
			order.name = rows.getString("NAME");
			order.messages = rows.getString("MESSAGE");
			order.create_date = rows.getDate("DATETIME");
		}}
		catch(Exception ex) {
			System.out.println(ex.toString());}
		return list;
	}

}
