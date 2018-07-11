package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.models.Account;
import com.revature.util.ConnectionFactory;

public class AccountDAOImpl implements AccountDAO{


	
	@Override
	public ArrayList<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountById(int ind) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account addAccount(Account newAccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateAccount(Account updatedAccount) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			conn.setAutoCommit(false);
			
			String sql = "Update account set account_id = ?, checking =?, saving =? where account_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, updatedAccount.getAccountid());
			pstmt.setDouble(2, updatedAccount.getChecking());
			pstmt.setDouble(3, updatedAccount.getSaving());
			pstmt.setInt(4, updatedAccount.getAccountid());
			
			int rowsUpdated = pstmt.executeUpdate();
			
			if(rowsUpdated != 0) {
				conn.commit();
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean removeAccountById(Account accountForRemoval) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAccountByName(String accountName) {
		// TODO Auto-generated method stub
		return false;
	}

}
