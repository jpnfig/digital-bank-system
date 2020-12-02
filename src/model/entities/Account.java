package model.entities;

public abstract class Account {
	
	private Integer number;
	protected Double balance;
	protected Integer idTransaction;
	
	public Account() {
	}

	public Account(Integer number, Double balance, Integer idTransaction) {
		this.number = number;
		this.balance = balance;
		this.idTransaction = idTransaction;
	}

	public Integer getNumber() {
		return number;
	}

	public Double getBalance() {
		return balance;
	}

	public Integer getIdTransaction() {
		return idTransaction;
	}

	public abstract void withdraw(double amount);
	
	public abstract void deposit(double amount);
	
	public abstract void transfer(Account accountSource, Account accountTarget, double amount);
	
}
