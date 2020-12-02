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
	
	//unique identification for each operation
	static int idTransaction = 0;
	
	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		List<Account> list = new ArrayList<>();
		
		System.out.println("Welcome to digital Bank!");
		System.out.println();		
		int number = 0;		
		double balance = 0.0;
	
		try {
			System.out.print("Enter the number of clients: ");
			int n = sc.nextInt();
			System.out.println();
			for (int i=1; i<=n; i++) {
				System.out.println("Enter the data client " + i + ":");
				System.out.print("Type of client - Physical or Legal (P/L): ");
				char type = sc.next().charAt(0);				
				while (type != 'P' && type != 'L') {
					System.out.println("Invalid type!");
					System.out.print("Type of client - Physical or Legal (P/L): ");
					type = sc.next().charAt(0);
				}
				Account ac;
				incrementTransact();
				number += 1;
				if (type == 'P') {
					System.out.print("CPF (only numbers): ");
					long cpf = sc.nextLong();
					sc.nextLine();
					System.out.print("Client name: ");
					String personsName = sc.nextLine();
					System.out.print("Birthday (DD/MM/YYYY): ");
					Date birthDate = sdf.parse(sc.next());
					ac = new PhysicalAccount(number, balance, idTransaction, cpf, personsName, birthDate);
					list.add(ac);
				}
				else {
					System.out.print("CNPJ (only numbers): ");
					long cnpj = sc.nextLong();
					sc.nextLine();
					System.out.print("Employee name: ");
					String employeeName = sc.nextLine();
					System.out.print("State registration (only numbers): ");
					long ie = sc.nextLong();
					ac = new LegalAccount(number, balance, idTransaction, cnpj, employeeName, ie);
					list.add(ac);
				}
				System.out.println();
				System.out.println("account successfully created! " + ac);
				System.out.println();
				System.out.print("Deposit amount: ");
				double depositAmount = sc.nextDouble();
				incrementTransact();
				ac.deposit(depositAmount);
				System.out.println();
				System.out.println("successfully deposited! " + ac);
				System.out.println();
				System.out.print("Withdraw amount: ");
				double withdrawAmount = sc.nextDouble();
				incrementTransact();
				ac.withdraw(withdrawAmount);
				System.out.println();
				System.out.println("successfully withdraw! " + ac);
				System.out.println();
				System.out.print("Bank transfer (y/n)? ");
				char resp = sc.next().charAt(0);
				if (resp == 'y') {
					System.out.print("Enter the source number account: ");
					int accountSource = sc.nextInt();
					System.out.print("Enter the target number account: ");
					int accountTarget = sc.nextInt();
					System.out.print("Enter the transfer amount: ");
					double amount = sc.nextDouble();
					Account accSource = null;
					Account accTarget = null;
					for(Account acc : list) {
						if (acc.getNumber() == accountSource) {
							accSource = acc;
						}
						if (acc.getNumber() == accountTarget) {
							accTarget = acc;
						}
					}
					incrementTransact();
					ac.transfer(accSource, accTarget, amount);
					System.out.println();
					System.out.println("successfully bank transfer! ");
					System.out.println("Source: " + accSource);
					System.out.println("Target: " + accTarget);
				}
				number = ac.getNumber();
				System.out.println();
			}
		}
		catch(DomainException e) {
			System.out.println("Operation not completed: " + e.getMessage());	
		}catch(RuntimeException e) {
			System.out.println("Unexpected error");
		}		
		sc.close();
	}
	public static void incrementTransact() {
		idTransaction += 1;
	}
}
