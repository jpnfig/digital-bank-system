package application;

public class Transaction {
	
	private static int idTransaction = 0;

    public int getIdTransaction() {
        return idTransaction;
    }
    
    public void incrementTransact() {
        idTransaction += 1;
    }
    
}
