package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Account;
import model.entities.LegalAccount;
import model.entities.PhysicalAccount;
import model.exceptions.DomainException;

public class Application {
	
	private Scanner sc;
	private SimpleDateFormat sdf;
	private Transaction transaction;
	private ValidateNumericField validateNumericField;
	
	private static int numberAccount = 0;
	private static int numberClients = 0;
	private static double balance = 0.0;
	private static char typeClient = ' ';
	private static Account account = null;
	private static String numberTextFormat = "";
	
	public void processar() throws ParseException {
		
		configureApplication();
		
		printConsole(" - Welcome to digital Bank! \n");
		
		List<Account> list = new ArrayList<>();
		
		try {
					
			verifyNumberOfClients();
			
			printConsole("");
			
			for (int i=1; i<=numberClients; i++) {
				
				printConsole(" - Enter the data client " + i + ":");
				
				verifyTypeClient();
				
				if (typeClient == 'P') {
					account = physicalPersonTreatment(sdf);				
				}else {
					account = legalPersonTreatment();					
				}
				
				list.add(account);
				
				printConsole("");
				printConsole(" - account successfully created! " + account + "\n");				
				
				deposit();			
				withdraw();
				
				System.out.print(" - Bank transfer (y/n)? ");
				
				char answerTransfer = readRow().charAt(0);
				
				if (answerTransfer == 'y') {
					transfer(list);
				}
				
				numberAccount = account.getNumberAccount();
				
				printConsole("");
	
			}
			
			printConsole(" - The digital bank thank you for your visit. System finished.");
			
		}catch(DomainException e) {	
			printConsole(" - Operation not completed: " + e.getMessage());	
		}catch(RuntimeException e) {
			printConsole(" - Unexpected error: " + e.getMessage());
		}
		
		this.sc.close();
	}
	
	private void configureApplication() {
		
		Locale.setDefault(Locale.US);
		
		this.sc = new Scanner(System.in);
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		this.transaction = new Transaction();
		this.validateNumericField = new ValidateNumericField();
	}
	
	public void verifyNumberOfClients() {
		
		System.out.print(" - Enter the number of clients: ");
		
		numberTextFormat = readRow();
		
		while (!this.validateNumericField.isNumericField(numberTextFormat)) {
			printConsole(" - Invalid number of client(s)!");
			System.out.print(" - Enter the number of clients: ");
			numberTextFormat = readRow();
		}
			
		numberClients = Integer.parseInt(numberTextFormat);
		
	}
	
	public void verifyTypeClient() {
		
		System.out.print(" - Type of client - Physical or Legal (P/L): ");
		
		typeClient = readRow().charAt(0);
		
		while (typeClient != 'P' && typeClient != 'L') {
			printConsole(" - Invalid type of client!");
			System.out.print(" - Type of client - Physical or Legal (P/L): ");
			typeClient = readRow().charAt(0);
		}
		
		this.transaction.incrementTransact();
	}
	
	public Account physicalPersonTreatment(SimpleDateFormat sdf) throws ParseException {
		
		System.out.print(" - CPF (only numbers): ");
//		readRow();
		numberTextFormat = readRow();
		
		while (!this.validateNumericField.isNumericField(numberTextFormat)) {
			printConsole(" - Invalid CPF number!");
			System.out.print(" - CPF (only numbers): ");
			numberTextFormat = readRow();
		}
			
		long cpf = Long.parseLong(numberTextFormat);
		
		System.out.print(" - Client name: ");
		String personName = readRow();
		System.out.print(" - Birthday (DD/MM/YYYY): ");
		Date birthDate = sdf.parse(readRow());
		
		numberAccount += 1;
		
		return account = 
				new PhysicalAccount(
						numberAccount, 
						balance, 
						this.transaction.getIdTransaction(), 
						cpf, 
						personName, 
						birthDate);
	}
	
	public Account legalPersonTreatment() {
		
		System.out.print(" - CNPJ (only numbers): ");
//		readRow();
		numberTextFormat = readRow();
		
		while (!this.validateNumericField.isNumericField(numberTextFormat)) {
			printConsole(" - Invalid CNPJ number!");
			System.out.print(" - CNPJ (only numbers): ");
			numberTextFormat = readRow();
		}
		
		long cnpj = Long.parseLong(numberTextFormat);
		
		System.out.print(" - Employee name: ");
		String employeeName = readRow();
		
		System.out.print(" - State registration (only numbers): ");
		numberTextFormat = readRow();
		
		while (!this.validateNumericField.isNumericField(numberTextFormat)) {
			printConsole(" - Invalid State Registration number!");
			System.out.print(" - State registration (only numbers): ");
			numberTextFormat = readRow();
		}
		
		long stateRegistration = Long.parseLong(numberTextFormat);
		
		numberAccount += 1;
		
		return account = 
				new LegalAccount(
						numberAccount, 
						balance, 
						this.transaction.getIdTransaction(), 
						cnpj, 
						employeeName, 
						stateRegistration);
	}
	
	public void deposit() {
		
		System.out.print(" - Deposit amount: ");
		
		double depositAmount = Double.parseDouble(readRow());
		
		this.transaction.incrementTransact();
		
		account.deposit(depositAmount);
		
		printConsole("");
		printConsole(" - successfully deposited! " + account + "\n");
	}
	
	public void withdraw() {
		
		System.out.print(" - Withdraw amount: ");
		
		double withdrawAmount = Double.parseDouble(readRow());
		
		this.transaction.incrementTransact();
		
		account.withdraw(withdrawAmount);
		
		printConsole("");
		printConsole(" - successfully withdraw! " + account + "\n");
	}
	
	public void transfer(List<Account> list) {
		
		System.out.print(" - Enter the source number account: ");
		int accountSource = Integer.parseInt(readRow());
		
		System.out.print(" - Enter the target number account: ");
		int accountTarget = Integer.parseInt(readRow());
		
		System.out.print(" - Enter the transfer amount: ");
		double amount = Double.parseDouble(readRow());
		
		Account accSource = null;
		Account accTarget = null;
		
		for(Account acc : list) {
			if (acc.getNumberAccount() == accountSource) {
				accSource = acc;
			}
			if (acc.getNumberAccount() == accountTarget) {
				accTarget = acc;
			}
		}
		
		accSource.setIdTransaction(this.transaction.getIdTransaction());
		accTarget.setIdTransaction(this.transaction.getIdTransaction());
		
		accSource.transfer(accSource, accTarget, amount);
		
		printConsole("");
		printConsole(" - successfully bank transfer! ");
		printConsole(" - Source: " + accSource);
		printConsole(" - Target: " + accTarget);			
	}
	
	private void printConsole(String msg){
        System.out.println(msg);
    }
	
	public String readRow(){
        return this.sc.nextLine();
    }
}
