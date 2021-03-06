package com.revature.dao;

import java.util.ArrayList;

import com.revature.models.AccountRegistrar;

public interface AccountRegistrarDAO {

	AccountRegistrar registerUserToAccount(AccountRegistrar acctReg);
	ArrayList<AccountRegistrar> getUsersOnAccount(int acctId);
	ArrayList<AccountRegistrar> getUserAccounts(int userId);
	void removeUserFromAccount(int userId);
	
}
