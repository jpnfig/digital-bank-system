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

	public PhysicalAccount(Integer numberAccount, Double balance, Integer idTransaction, Long cpf, String personsName,
			Date birthDate) {
		super(numberAccount, balance, idTransaction);
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

	public String getPersonsName() {
		return personsName;
	}

	public void setPersonsName(String name) {
		this.personsName = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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
				+ this.getNumberAccount()
				+ ", Balance: "
				+ String.format("%.2f", this.getBalance())
				+ ", ID transaction: "
				+ this.idTransaction;		
	}

}
