package model.entities;

import model.exceptions.DomainException;

public abstract class Account {
	
	private Integer numberAccount;
	protected Double balance;
	protected Integer idTransaction;
	
	public Account() {
	}

	public Account(Integer numberAccount, Double balance, Integer idTransaction) {
		this.numberAccount = numberAccount;
		this.balance = balance;
		this.idTransaction = idTransaction;
	}

	public Integer getNumberAccount() {
		return numberAccount;
	}

	public Double getBalance() {
		return balance;
	}

	public Integer getIdTransaction() {
		return idTransaction;
	}
	
	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
	}

	public void deposit(double amount) {
		balance += amount;
		idTransaction += 1;	
	}
	
	public void withdraw(double amount) {
		if (balance < amount) {
			throw new DomainException("Not enough balance");
		}
		balance -= amount;
		idTransaction += 1;
	}
	
	public abstract void transfer(Account accountSource, Account accountTarget, double amount);
	
}
