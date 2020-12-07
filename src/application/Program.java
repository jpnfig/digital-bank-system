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

public class Program {
	
//  unique identification for each operation:
	static int idTransaction = 0;
	static int numberAccount = 0;
	static int numberClients = 0;
	static double balance = 0.0;
	static char typeClient = ' ';
	static Account account = null;
	static String numberTextFormat = "";
		
	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		
		System.out.println("Welcome to digital Bank! \n");
		
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		List<Account> list = new ArrayList<>();
		
		try {
					
			verifyNumberOfClients(sc);
			
			System.out.println();
			
			for (int i=1; i<=numberClients; i++) {
				
				System.out.println("Enter the data client " + i + ":");
				
				verifyTypeClient(sc);
				
				if (typeClient == 'P') {
					account = physicalPersonTreatment(sc, sdf);				
				}else {
					account = legalPersonTreatment(sc);					
				}
				
				list.add(account);
				
				System.out.println();
				System.out.println("account successfully created! " + account + "\n");				
				
				deposit(sc);			
				withdraw(sc);
				
				System.out.print("Bank transfer (y/n)? ");
				
				char answerTransfer = sc.next().charAt(0);
				
				if (answerTransfer == 'y') {
					transfer(sc, list);
				}
				
				numberAccount = account.getNumberAccount();
				
				System.out.println();
				System.out.println("The digital bank thank you for your visit. System finished.");
			}
		}catch(DomainException e) {	
			System.out.println("Operation not completed: " + e.getMessage());	
		}catch(RuntimeException e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
		
		sc.close();
		
	}
	
	public static void incrementTransact() {
		idTransaction += 1;
	}
	
	private static boolean numericField(String field){           
        return field.matches("[0-9]+");   
	}  
	
	public static void verifyNumberOfClients(Scanner sc) {
		
		System.out.print("Enter the number of clients: ");
		
		numberTextFormat = sc.nextLine();
		
		while (numericField(numberTextFormat) == false) {
			System.out.println("Invalid number of client(s)!");
			System.out.print("Enter the number of clients: ");
			numberTextFormat = sc.nextLine();
		}
			
		numberClients = Integer.parseInt(numberTextFormat);
		
	}
	
	public static void verifyTypeClient(Scanner sc) {
		
		System.out.print("Type of client - Physical or Legal (P/L): ");
		
		typeClient = sc.next().charAt(0);
		
		while (typeClient != 'P' && typeClient != 'L') {
			System.out.println("Invalid type of client!");
			System.out.print("Type of client - Physical or Legal (P/L): ");
			typeClient = sc.next().charAt(0);
		}
		
		incrementTransact();
	}
	
	public static Account physicalPersonTreatment(Scanner sc, SimpleDateFormat sdf) throws ParseException {
		
		System.out.print("CPF (only numbers): ");
		sc.nextLine();
		numberTextFormat = sc.nextLine();
		
		while (numericField(numberTextFormat) == false) {
			System.out.println("Invalid CPF number!");
			System.out.print("CPF (only numbers): ");
			numberTextFormat = sc.nextLine();
		}
			
		long cpf = Long.parseLong(numberTextFormat);
		
		System.out.print("Client name: ");
		String personName = sc.nextLine();
		System.out.print("Birthday (DD/MM/YYYY): ");
		Date birthDate = sdf.parse(sc.next());
		
		numberAccount += 1;
		
		return account = new PhysicalAccount(numberAccount, balance, idTransaction, cpf, personName, birthDate);
	}
	
	public static Account legalPersonTreatment(Scanner sc) {
		
		System.out.print("CNPJ (only numbers): ");
		sc.nextLine();
		numberTextFormat = sc.nextLine();
		
		while (numericField(numberTextFormat) == false) {
			System.out.println("Invalid CNPJ number!");
			System.out.print("CNPJ (only numbers): ");
			numberTextFormat = sc.nextLine();
		}
		
		long cnpj = Long.parseLong(numberTextFormat);
		
		System.out.print("Employee name: ");
		String employeeName = sc.nextLine();
		
		System.out.print("State registration (only numbers): ");
		numberTextFormat = sc.nextLine();
		
		while (numericField(numberTextFormat) == false) {
			System.out.println("Invalid State Registration number!");
			System.out.print("State registration (only numbers): ");
			numberTextFormat = sc.nextLine();
		}
		
		long stateRegistration = Long.parseLong(numberTextFormat);
		
		numberAccount += 1;
		
		return account = new LegalAccount(numberAccount, balance, idTransaction, cnpj, employeeName, stateRegistration);
	}
	
	public static void deposit(Scanner sc) {
		
		System.out.print("Deposit amount: ");
		
		double depositAmount = sc.nextDouble();
		
		incrementTransact();
		
		account.deposit(depositAmount);
		
		System.out.println();
		System.out.println("successfully deposited! " + account + "\n");
	}
	
	public static void withdraw(Scanner sc) {
		
		System.out.print("Withdraw amount: ");
		
		double withdrawAmount = sc.nextDouble();
		
		incrementTransact();
		
		account.withdraw(withdrawAmount);
		
		System.out.println();
		System.out.println("successfully withdraw! " + account + "\n");
	}
	
	public static void transfer(Scanner sc, List<Account> list) {
		
		System.out.print("Enter the source number account: ");
		int accountSource = sc.nextInt();
		System.out.print("Enter the target number account: ");
		int accountTarget = sc.nextInt();
		System.out.print("Enter the transfer amount: ");
		double amount = sc.nextDouble();
		
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
		
		accSource.setIdTransaction(idTransaction);
		accTarget.setIdTransaction(idTransaction);
		
		accSource.transfer(accSource, accTarget, amount);
		
		System.out.println();
		System.out.println("successfully bank transfer! ");
		System.out.println("Source: " + accSource);
		System.out.println("Target: " + accTarget);			
	}
}
