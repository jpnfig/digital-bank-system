package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.exceptions.DomainException;

public class PhysicalAccount extends Account {
	
	private Long cpf;
	private String personsName;
	private Date birthDate;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public PhysicalAccount() {
		super();
	}

	public PhysicalAccount(Integer number, Double balance, Integer idTransaction, Long cpf, String personsName,
			Date birthDate) {
		super(number, balance, idTransaction);
		this.cpf = cpf;
		this.personsName = personsName;
		this.birthDate = birthDate;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return personsName;
	}

	public void setName(String name) {
		this.personsName = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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
		//Tax of $10 for the transfer of Legal (balance can be negative)
		accountTarget.idTransaction = accountSource.idTransaction;
		accountSource.withdraw(amount + 10.0);
		accountTarget.deposit(amount);
	}
	
	@Override
	public String toString() {
		return "Cliente name: " 
				+ personsName
				+ ", CPF: "
				+ cpf
				+ ", Birthday: "
				+ sdf.format(birthDate)
				+ ", Number account: "
				+ this.getNumber()
				+ ", Balance: "
				+ String.format("%.2f", this.getBalance())
				+ ", ID transaction: "
				+ this.idTransaction;		
	}

}
