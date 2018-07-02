package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class AccountsDAOImpl implements AccountsDAO{
	
	UserDAO userDAO = new UserDAOImpl();

	@Override
	public ArrayList<Account> getAllAccounts() {
		
		ArrayList<Account> allAccounts = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "SELECT * FROM accounts ORDER BY accountId";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				allAccounts.add(new Account(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allAccounts;
	}

	@Override		// returns an ArrayList of users authorized on one account (the account of the active user)
	public ArrayList<User> getAllAccountUsers(User activeUser) {
		
		ArrayList<User> allAccountUsers = new ArrayList<>();
		
		int activeUserAccountId = getAccountIdByUser(activeUser);
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "SELECT userId FROM userAccount WHERE accountId = ? ORDER BY userId";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, activeUserAccountId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				sql = "SELECT userId, firstName, lastName FROM users WHERE userId = ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, rs.getInt(1));
				
				ResultSet userRS = pstmt.executeQuery();
				userRS.next();
				allAccountUsers.add(new User(userRS.getInt(1), userRS.getString(2), userRS.getString(3), null, null, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allAccountUsers;
	}

	@Override
	public int getAccountHolderId(int accountId) {
		
		int accountHolderId = 0;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "SELECT * FROM accounts WHERE accountId = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, accountId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				accountHolderId = rs.getInt(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountHolderId;
	}

	@Override
	public int getUserCheckingId(int userId) {
		
		int checkingId = 0;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			System.out.println("getting user checking id");
			String sql = "SELECT checkingId FROM accounts WHERE accountHolderId = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			checkingId = rs.getInt(1);
			System.out.println("the checking id is " + checkingId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkingId;
	}

	@Override
	public int getUserSavingsId(int userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override		// this should be called when a user is created and this should call authorize user to initially authorize the new account holder
	public boolean createAccount(User accountHolder) {
		
		boolean success = false;
		
		int accountHolderId = userDAO.getUserId(accountHolder);
		
		System.out.println("Creating an account for " + accountHolder.getUserName() + "...");
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){

			String sql = "INSERT INTO accounts (accountHolderId) VALUES (?)";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, accountHolderId);

			if(pstmt.executeUpdate() != 0) {
				success = true;
			}else {
				success = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(success) {
			System.out.println("Account created for " + accountHolder.getUserName());
			return true;
		}else {
			System.out.println("Failed to create account for " + accountHolder.getUserName());
			return false;
		}
	}

	@Override
	public boolean authorizeUser(User accountHolder, int newUserId) {
		
		int accountId = getAccountIdByUser(accountHolder);
		
		System.out.println("Authorizing user " + newUserId + " to account " + accountId + "...");
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){

			String sql = "INSERT INTO userAccount VALUES (?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newUserId);
			pstmt.setInt(2, accountId);

			if(pstmt.executeUpdate() != 0) {
				System.out.println("authorization given to user " + newUserId);
				return true;
			}else {
				System.out.println("Failed to authorize user " + newUserId);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean deAuthorizeUser(User accountHolder, int oldUserId) {
		
		if(accountHolder.getUserId() == oldUserId) {
			System.out.println("Cannot deauthorized the account holder.");
			return false;
		}
		
		int accountId = getAccountIdByUser(accountHolder);
		
		System.out.println("Deauthorizing user " + oldUserId + "...");
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){

			String sql = "DELETE FROM userAccount WHERE userId = ? AND accountId = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, oldUserId);
			pstmt.setInt(2, accountId);

			if(pstmt.executeUpdate() != 0) {
				System.out.println("deauthorized user " + oldUserId);
				return true;
			}else {
				System.out.println("Failed to deauthorize user " + oldUserId);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public int getAccountIdByUser(User myUser) {
		
		int accountId = 0;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "SELECT accountId FROM accounts WHERE accountHolderId = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, myUser.getUserId());
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next())
				accountId = rs.getInt(1);
			else
				System.out.println("Could not get AccoutId for this user");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountId;
	}

}
