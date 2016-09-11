package simpleBank.account;

import java.math.BigDecimal;

public class Account implements Comparable<Account> {

	private Integer number;
	private BigDecimal balance;
	private String ownerLogin;

	public Account(int number, BigDecimal balance, String ownerLogin) {
		this.number = number;
		this.balance = balance;
		this.ownerLogin = ownerLogin;
	}

	public void makeTransfer(Account targetAccount, BigDecimal amount) {
		int test = balance.compareTo(amount);
		if (test >= 0) {
			balance = balance.subtract(amount);
			targetAccount.receiveTransfer(amount);
		} else {
			System.out.println("za malo srodkow!");
		}
	}

	@Override
	public boolean equals(Object anAccount) {
		Account testAccount = (Account) anAccount;
		Integer testNumber = testAccount.getNumber();
		return this.number.equals(testNumber);
	}

	@Override
	public String toString() {
		return number + ": " + balance + "; wlasciciel - ";
	}

	@Override
	public int compareTo(Account account) {
		BigDecimal targetBalance = account.getBalance();
		return this.balance.compareTo(targetBalance);
	}

	public int getNumber() {
		return number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getOwnerLogin() {
		return ownerLogin;
	}

	private void receiveTransfer(BigDecimal amount) {
		this.balance = balance.add(amount);
	}
}
