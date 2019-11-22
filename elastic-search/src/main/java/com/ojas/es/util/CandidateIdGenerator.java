package com.ojas.es.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CandidateIdGenerator implements IdentifierGenerator {

  

	@Override
	public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		 Connection connection = arg0.connection();
	        long generatedId = 10001;
	        try {
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement.executeQuery("select count(*) from candidate");

	            if (rs.next()) {
	            	System.err.println("Entered First if Condition");
	                ResultSet rs1 = statement.executeQuery("select max(id) from candidate");
	                if (rs1.next()) {
	                	System.err.println("Entered Nested if Condition");
	                	if(rs1.getInt(1)==0) {
	                		generatedId =10001;
	                	}else {
	                		generatedId = rs1.getInt(1) + 1;
	                	}
	                    
	                    System.err.println("generatedId="+generatedId);
	                }
	                // String generatedId = prefix + new Integer(id).toString();
	            }
	            System.err.println("generatedId="+generatedId);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return generatedId;
	}
}