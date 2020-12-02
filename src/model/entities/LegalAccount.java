package model.entities;

import model.exceptions.DomainException;

public class LegalAccount extends Account {
	
	private Long cnpj;
	private String employeeName;
	private Long ie;
	
	public LegalAccount() {
		super();
	}

	public LegalAccount(Integer number, Double balance, Integer idTransaction, Long cnpj, String employeeName,
			Long ie) {
		super(number, balance, idTransaction);
		this.cnpj = cnpj;
		this.employeeName = employeeName;
		this.ie = ie;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getIe() {
		return ie;
	}

	public void setIe(Long ie) {
		this.ie = ie;
	}

	@Override
	public void withdraw(double amount) {
		if (balance < amount) {
			throw new DomainException("Not enough balance");
		}
		balance -= amount;
		idTransaction += 1;
	}

	@Override
	public void deposit(double amount) {
		balance += amount;
		idTransaction += 1;	
	}

	@Override
	public void transfer(Account accountSource, Account accountTarget, double amount) {
		if (accountSource == null) {
			throw new DomainException("Source account not found!");
		}
		if (accountTarget == null) {
			throw new DomainException("Target account not found!");
		}
		if (accountSource.getBalance() < amount) {
			throw new DomainException("Insufficient balance for bank transfer!");
		}
		//Tax of $5 for the transfer of Legal (balance can be negative)
		accountTarget.idTransaction = accountSource.idTransaction;
		accountSource.withdraw(amount + 5.0);
		accountTarget.deposit(amount);
	}
	
	@Override
	public String toString() {
		return "Employee Name: " 
				+ employeeName
				+ ", CNPJ: "
				+ cnpj
				+ ", State registration: "
				+ ie
				+ ", Number account: "
				+ this.getNumber()
				+ ", Balance: "
				+ String.format("%.2f", this.getBalance())
				+ ", ID transaction: "
				+ this.idTransaction;		
	}

}
