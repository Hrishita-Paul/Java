import java.util.Scanner;

class ATM {
    private static int balance = 60000;

    public static void main(String[] args) {
        Pin pin = new Pin();
        CashWithdrawal cashWithdrawal = new CashWithdrawal();
        MoneyTransfer moneyTransfer = new MoneyTransfer();
        Deposit deposit = new Deposit();
        BalanceEnquiry balanceEnquiry = new BalanceEnquiry();
        BalanceEnquiryReceipt balanceEnquiryReceipt = new BalanceEnquiryReceipt();

        System.out.println("\n\n*******Insert your card*******");
        System.out.println("Wait, your card information is getting processed...");
        pin.enterPin();

        System.out.println("\n1. Cash withdrawal");
        System.out.println("2. Money transfer");
        System.out.println("3. Deposit");
        System.out.println("4. Balance Enquiry");
        System.out.println("5. Cancel\n");

        System.out.print("Enter your choice: ");
        int choice;
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                cashWithdrawal.cashWithdrawal();
                if (cashWithdrawal.getCurrentWithdrawal() || cashWithdrawal.getSavingsWithdrawal() || cashWithdrawal.getCreditWithdrawal()) {
                    System.out.print("Do you want a receipt? Choose Y for Yes or N for No: ");
                    char receiptChoice = scanner.next().charAt(0);
                    if (receiptChoice == 'Y' || receiptChoice == 'y') {
                        WithdrawalReceipt withdrawalReceipt = new WithdrawalReceipt();
                        withdrawalReceipt.withdrawalReceipt(cashWithdrawal);
                    }
                }
                break;
            case 2:
                moneyTransfer.moneyTransfer();
                if (moneyTransfer.getTransferSuccess()) {
                    System.out.print("Do you want a receipt? Choose Y for Yes or N for No: ");
                    char receiptChoice = scanner.next().charAt(0);
                    if (receiptChoice == 'Y' || receiptChoice == 'y') {
                        TransferReceipt transferReceipt = new TransferReceipt();
                        transferReceipt.transferReceipt(moneyTransfer);
                    }
                }
                break;
            case 3:
                deposit.deposit();
                if (deposit.getDepositSuccess()) {
                    System.out.print("Do you want a receipt? Choose Y for Yes or N for No: ");
                    char receiptChoice = scanner.next().charAt(0);
                    if (receiptChoice == 'Y' || receiptChoice == 'y') {
                        DepositReceipt depositReceipt = new DepositReceipt();
                        depositReceipt.depositReceipt(deposit);
                    }
                }
                break;
            case 4:
                balanceEnquiry.balanceEnquiry();
                System.out.print("Do you want a receipt? Choose Y for Yes or N for No: ");
                char receiptChoice = scanner.next().charAt(0);
                if (receiptChoice == 'Y' || receiptChoice == 'y') {
                    balanceEnquiryReceipt.balanceReceipt(cashWithdrawal, moneyTransfer, deposit, pin);
                }
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    public static int getBalance() {
        return balance;
    }

    public static void setBalance(int newBalance) {
        balance = newBalance;
    }
}

class Pin {
    public void enterPin() {
        int i;
        for (i = 1; i < 4; i++) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your PIN: ");
            String pinNum = scanner.next();
            if (pinNum.equals("3400")) {
                break;
            } else {
                System.out.println("Access Denied!!! You have " + (3 - i) + " more chances left");
            }
        }
        if (i == 4) {
            System.out.println("Sorry! You have exhausted your chances of entering the PIN. You cannot access the ATM anymore.");
            System.exit(0);
        }
    }
}

class CashWithdrawal {
    private int amount;
    private boolean currentWithdrawal = false;
    private boolean savingsWithdrawal = false;
    private boolean creditWithdrawal = false;

    public void cashWithdrawal() {
        System.out.println("\nChoose one of the following:-");
        System.out.println("1. Current");
        System.out.println("2. Savings");
        System.out.println("3. Credit");
        System.out.println("4. Cancel");

        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter the amount to be withdrawn: ");
                amount = scanner.nextInt();
                int limit;
                if (ATM.getBalance() > amount) {
                    limit = 50000;
                    if (amount < limit) {
                        System.out.println("Transaction in progress...");
                        System.out.println("Amount withdrawn\n");
                        ATM.setBalance(ATM.getBalance() - amount);
                        currentWithdrawal = true;
                    } else {
                        System.out.println("Limit exceeded. Enter an amount within 50000\n");
                    }
                } else {
                    System.out.println("Insufficient balance\n");
                }
                break;
            case 2:
                System.out.print("Enter the amount to be withdrawn: ");
                amount = scanner.nextInt();
                if (ATM.getBalance() > amount) {
                    limit = 24000;
                    if (amount < limit) {
                        System.out.println("Transaction in progress...");
                        System.out.println("Amount withdrawn\n");
                        ATM.setBalance(ATM.getBalance() - amount);
                        savingsWithdrawal = true;
                    } else {
                        System.out.println("Limit exceeded. Enter an amount within 24000!");
                    }
                } else {
                    System.out.println("Insufficient balance!\n");
                }
                break;
            case 3:
                System.out.print("Enter the amount to be withdrawn: ");
                amount = scanner.nextInt();
                System.out.println("Cash advance fee of 2.5% is levied on the cash withdrawn");
                creditWithdrawal = true;
                break;
            case 4:
                break;
            default:
                System.out.println("Press again");
                break;
        }
    }

    public boolean getCurrentWithdrawal() {
        return currentWithdrawal;
    }

    public boolean getSavingsWithdrawal() {
        return savingsWithdrawal;
    }

    public boolean getCreditWithdrawal() {
        return creditWithdrawal;
    }

    public int getAmount() {
        return amount;
    }
}

class WithdrawalReceipt {
    public void withdrawalReceipt(CashWithdrawal cashWithdrawal) {
        System.out.println("Cash withdrawn of Rs. " + cashWithdrawal.getAmount() + " from the account");
    }
}

class MoneyTransfer {
    private int amount;
    private boolean transferSuccess = false;
    private long accountNum;

    public void moneyTransfer() {
        System.out.print("Enter the amount: ");
        Scanner scanner = new Scanner(System.in);
        amount = scanner.nextInt();
        System.out.println("Validating balance from the bank...");
        if (ATM.getBalance() > amount) {
            System.out.print("Enter the account number of the receiver: ");
            accountNum = scanner.nextLong();
            System.out.println("Waiting for the money to get transferred...\n");
            System.out.println("Money transfer successful\n");
            transferSuccess = true;
            ATM.setBalance(ATM.getBalance() - amount);
        } else {
            System.out.println("Insufficient balance. Transfer not possible");
        }
    }

    public int getAmount() {
        return amount;
    }

    public boolean getTransferSuccess() {
        return transferSuccess;
    }

    public long getAccountNum() {
        return accountNum;
    }
}

class TransferReceipt {
    public void transferReceipt(MoneyTransfer moneyTransfer) {
        System.out.println("Cash transferred of Rs." + moneyTransfer.getAmount() + " to account number " + moneyTransfer.getAccountNum());
    }
}

class Deposit {
    private int amount;
    private boolean depositSuccess = false;

    public void deposit() {
        System.out.print("Enter the amount: ");
        Scanner scanner = new Scanner(System.in);
        amount = scanner.nextInt();
        int limit = 100000;
        if (amount < limit) {
            System.out.println("Amount deposited successfully");
            depositSuccess = true;
            ATM.setBalance(ATM.getBalance() + amount);
        } else {
            System.out.println("Limit exceeded. Enter an amount within 100000!");
        }
    }

    public int getAmount() {
        return amount;
    }

    public boolean getDepositSuccess() {
        return depositSuccess;
    }
}

class DepositReceipt {
    public void depositReceipt(Deposit deposit) {
        System.out.println("\nCash of Rs." + deposit.getAmount() + " deposited into the account");
    }
}

class BalanceEnquiry {
    public void balanceEnquiry() {
        System.out.println("Balance of Rs. " + ATM.getBalance() + " remaining in the account");
    }
}

class BalanceEnquiryReceipt {
    public void balanceReceipt(CashWithdrawal cashWithdrawal, MoneyTransfer moneyTransfer, Deposit deposit, Pin pin) {
        if (cashWithdrawal.getCurrentWithdrawal()) {
            System.out.println("\nBalance of Rs." + ATM.getBalance() + " remaining in current account");
        } else if (cashWithdrawal.getSavingsWithdrawal()) {
            System.out.println("\nBalance of Rs." + ATM.getBalance() + " remaining in savings account");
        } else if (moneyTransfer.getTransferSuccess()) {
            System.out.println("\nBalance of Rs." + ATM.getBalance() + " remaining in the account");
        } else if (deposit.getDepositSuccess()) {
            System.out.println("\nBalance of Rs." + ATM.getBalance() + " remaining in the account");
        }
    }
}
