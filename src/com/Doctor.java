package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import config.DBConnector;

public class Doctor {

	public String insertDoctor(String nic, String fname, String lname, String email, String gender, String liscen,
			String special, String phone, String charge) {
		String output = "";

		try (Connection con = DBConnector.getConnection()) {

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " INSERT INTO doctor(`doc_id`, `doc_nic`, `doc_fname`, `doc_lname`, `doc_email`, `doc_gender`, `liscen_no`, `specialization`, `phone`, `doc_charge`)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, nic);
			preparedStmt.setString(3, fname);
			preparedStmt.setString(4, lname);
			preparedStmt.setString(5, email);
			preparedStmt.setString(6, gender);
			preparedStmt.setString(7, liscen);
			preparedStmt.setString(8, special);
			preparedStmt.setString(9, phone);
			preparedStmt.setFloat(10, Float.parseFloat(charge));
			

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Inserted Doctor Successfully";
		} catch (Exception e) {
			output = "Error while inserting the doctor.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateDoctor(String d_id, String nic, String fname, String lname, String email, String gender, String liscen, String special,  String phone, String charge) 
	{   
		String output = ""; 
	 
		try(Connection con  = DBConnector.getConnection())   
		{    
					 
	        if (con == null)    
	        {return "Error while connecting to the database for updating."; } 
	 
	        // create a prepared statement    
	        String updatequery = "UPDATE doctor SET doc_nic =?, doc_fname =?, doc_lname =?, doc_email=?, doc_gender=?, liscen_no=?, specialization=?, phone=?, doc_charge=? WHERE doc_id=?"; 
	 
	        PreparedStatement preparedStmt = con.prepareStatement(updatequery); 
	 
	        // binding values
	        
	        preparedStmt.setString(1, nic);    
	        preparedStmt.setString(2, fname);
	        preparedStmt.setString(3, lname);
	        preparedStmt.setString(4, email);
	        preparedStmt.setString(5, gender);
	        preparedStmt.setString(6, liscen);
	        preparedStmt.setString(7, special);
	        preparedStmt.setString(8,phone);
	        preparedStmt.setDouble(9, Double.parseDouble(charge));
	       
	        preparedStmt.setInt(10, Integer.parseInt(d_id));
	        
	 
	        // execute the statement    
	        preparedStmt.execute();    
	        con.close(); 
	 
	        output = "Updated doctor successfully";   
	    }   
		catch (Exception e)   
		{    
			output = "Error while updating the Doctor";    
			System.err.println(e.getMessage());   
		} 
	 
	    return output;  
	}
	
	

	public String deleteDoctor(String d_id) {
		String output = null;
		try (Connection con = DBConnector.getConnection()) {
			if (con == null) {
				return "Error while connecting to the database";
			} else {
				String query = "delete from doctor where doc_id=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, Integer.parseInt(d_id));

				preparedStmt.execute();
				con.close();
				output = "Deleted doctor Successfully";
			}
		} catch (Exception e) {
			output = "Error while deleting Doctor";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readDoctors() {
		String output = "";

		try (Connection con = DBConnector.getConnection()) {

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>D_NIC</th><th>D_fname</th><th>D_lanme</th><th>D_email</th><th>D_gender</th><th>Liscen_No</th><th>D_specialization</th><th>D_phone</th><th>D_charge</th><th>Update</th><th>Remove</th></tr>";

			String query = "SELECT * FROM doctor";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("doc_id"));
				String nic = rs.getString("doc_nic");
				String fname = rs.getString("doc_fname");
				String lname = rs.getString("doc_lname");
				String email = rs.getString("doc_email");
				String gender = rs.getString("doc_gender");
				String liscen = rs.getString("liscen_No");
				String special = rs.getString("specialization");
				String phone = rs.getString("phone");
				String charge = Double.toString(rs.getDouble("doc_charge"));
				

				// Add into the html table
				output += "<tr><td><input id=\"hidDocIDUpdate\" name=\"hidDocIDUpdate\" type=\"hidden\" value=\""
						+ id + "\">" + nic + "</td>";
				output += "<td>" + fname + "</td>";
				output += "<td>" + lname + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + gender + "</td>";
				output += "<td>" + liscen + "</td>";
				output += "<td>" + special + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + charge + "</td>";
			

					// buttons
					output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\" btnUpdate btn btn-secondary\"></td>"
							+ "<td><form method=\"post\" action=\"doctor.jsp\">"
							+ "<input name=\"btnRemove\" type=\"submit\"value=\"Remove\" class=\"btn btn-danger\">"
							+ "<input name=\"hidDocIDDelete\" type=\"hidden\"value=\"" + id + "\">"
							+ "</form></td></tr>";

				}
				con.close();
				output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
}
