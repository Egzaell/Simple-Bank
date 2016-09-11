package simpleBank.dataContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simpleBank.account.Account;
import simpleBank.client.Client;

public class DataContainer {

	private List<Client> clientsList = new ArrayList<>();
	private List<Account> accountsList = new ArrayList<>();

	public boolean registerClient(Client client) {
		boolean isOperationSuccesfull = false;
		boolean isClientOnList = isClientOnList(client);
		if (!isClientOnList) {
			clientsList.add(client);
			isOperationSuccesfull = true;
		}
		return isOperationSuccesfull;
	}

	public boolean registerAccount(Account account) {
		boolean isOperationSuccesfull = false;
		boolean isAccountOnTheList = isAccountOnTheList(account);
		if (!isAccountOnTheList) {
			accountsList.add(account);
			sortAccounts();
			isOperationSuccesfull = true;
		}
		return isOperationSuccesfull;
	}
	
	public boolean loginClient(String login, String password) {
		boolean isOperationSuccesfull = false;
		for (Client client : clientsList) {
			String testLogin = client.getLogin();
			String testPassword = client.getPassword();
			if (login.equals(testLogin) && password.equals(testPassword)) {
				isOperationSuccesfull = true;
			}
		}
		return isOperationSuccesfull;
	}
	
	public Client getClientByLogin(String login) {
		Client loggedClient = null;
		for (Client client : clientsList) {
			String testLogin = client.getLogin();
			if(login.equals(testLogin)) {
				loggedClient = client;
			}
		}
		return loggedClient;
	}
	
	public Account getAccountByNumber(int number) {
		Account testAccount = null;
		for (Account account : accountsList) {
			Integer testNumber = account.getNumber();
			if (testNumber.equals(number)) {
				testAccount = account;
			}
		}
		return testAccount;
	}
	
	public Account getAccountByOwner(String login) {
		Account testAccount = null;
		for (Account account : accountsList) {
			String ownerLogin = account.getOwnerLogin();
			if (ownerLogin.equals(login)) {
				testAccount = account;
			}
		}
		return testAccount;
	}
	
	public void sortAccounts() {
		Collections.sort(accountsList);
		Collections.reverse(accountsList);
	}
	
	public ArrayList<Client> getClientsList() {
		return (ArrayList<Client>) clientsList;
	}
	
	public ArrayList<Account> getAccountsList() {
		return (ArrayList<Account>) accountsList;
	}

	private boolean isAccountOnTheList(Account account) {
		if (accountsList.contains(account)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isClientOnList(Client client) {
		if (clientsList.contains(client)) {
			return true;
		} else {
			return false;
		}
	}
}
