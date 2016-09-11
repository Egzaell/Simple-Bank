package simpleBank.userInterface;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import simpleBank.account.Account;
import simpleBank.client.Client;
import simpleBank.dataContainer.DataContainer;
import simpleBank.fileSupporter.FileSupporter;
import simpleBank.user.User;

public class UserInterface {

	private static final String WELCOME_MESSAGE = "Witaj w programie Simple Bank";
	private static final String LOGIN_MESSAGE = "Podaj login:";
	private static final String PASSWORD_MESSAGE = "oraz haslo:";
	private static final String SUCCESS_MESSAGE = "SUKCES!";
	private static final String ERROR_MESSAGE = "BLAD!";
	private static final String ADMIN_MENU = "1. Przeglad kont, 2. Dodanie uzytkownika, 3. Dodanie konta, 4. Zapis zmian, 5. Wyjscie";
	private static final String NEW_USER_MESSAGE = "DODAWANIE UZYTKOWNIA";
	private static final String NEW_ACCOUNT_MEASSAGE = "DODAWANIE KONTA";
	private static final String USER_MENU = "1. Wykonaj przelew, 2. Zapis Zmian, 3. Wyjscie";
	private static final String TRANSFER_MESSAGE_1 = "Podaj numer konta na ktore chcesz przelac pieniadze";
	private static final String TRANSFER_MESSAGE_2 = "Podaj kwote (00,00)";
	private DataContainer dataContainer;
	private FileSupporter fileSupporter;
	private Scanner scanner;
	private User user;
	private boolean isProgramRunning = true;

	public UserInterface(DataContainer dataContainer, FileSupporter fileSupporter) {
		this.dataContainer = dataContainer;
		this.fileSupporter = fileSupporter;
		init();
	}

	private void init() {
		scanner = new Scanner(System.in);
		System.out.println(WELCOME_MESSAGE);
		login();
	}

	private void login() {
		String login;
		String password;
		boolean isOperationSuccesfull = false;
		System.out.println(LOGIN_MESSAGE);
		login = scanner.nextLine();
		System.out.println(PASSWORD_MESSAGE);
		password = scanner.nextLine();
		isOperationSuccesfull = dataContainer.loginClient(login, password);
		if (isOperationSuccesfull) {
			user = User.getInstance(login);
			printMenu();
		} else {
			System.out.println(ERROR_MESSAGE);
			login();
		}
	}

	private void printMenu() {
		String userLogin = user.getLogin();
		if (userLogin.equals("admin")) {
			printAdminMenu();
		} else {
			printUserMenu();
		}
	}

	private void printAdminMenu() {
		int choice;
		while (isProgramRunning) {
			System.out.println(ADMIN_MENU);
			choice = scanner.nextInt();
			if (choice == 1) {
				printAccounts();
			} else if (choice == 2) {
				registerUser();
			} else if (choice == 3) {
				registerAccount();
			} else if (choice == 4) {
				saveProgress();
			} else if (choice == 5) {
				isProgramRunning = false;
			} else {
				System.out.println(ERROR_MESSAGE);
			}
		}
	}

	private void printUserMenu() {
		String ownerLogin = user.getLogin();
		Account account = dataContainer.getAccountByOwner(ownerLogin);
		int choice;
		while (isProgramRunning) {
			System.out.println(account);
			System.out.println(USER_MENU);
			choice = scanner.nextInt();
			if (choice == 1) {
				makeTransfer();
			} else if (choice == 2) {
				saveProgress();
			} else if (choice == 3) {
				isProgramRunning = false;
			} else {
				System.out.println(ERROR_MESSAGE);
			}
		}
	}

	private void registerUser() {
		String login;
		String password;
		String firstName;
		String lastName;
		String birthDate;
		Client client;
		boolean isOperationSuccesfull = false;
		System.out.println(NEW_USER_MESSAGE);
		System.out.println("Podaj login:");
		login = scanner.next();
		System.out.println("Podaj haslo:");
		password = scanner.next();
		System.out.println("Podaj imie:");
		firstName = scanner.next();
		System.out.println("Podaj nazwisko:");
		lastName = scanner.next();
		System.out.println("Podaj date urodzenia (dd-MM-rrrr)");
		birthDate = scanner.next();
		client = new Client.ClientBuilder().login(login).password(password).firstName(firstName).lastName(lastName)
				.birthDate(birthDate).build();
		isOperationSuccesfull = dataContainer.registerClient(client);
		if (isOperationSuccesfull) {
			System.out.println(SUCCESS_MESSAGE);
		} else {
			System.out.println(ERROR_MESSAGE);
		}
	}

	private void registerAccount() {
		int number;
		BigDecimal balance;
		String ownerLogin;
		Account account;
		boolean isOperationSuccesfull;
		System.out.println(NEW_ACCOUNT_MEASSAGE);
		System.out.println("Podaj numer:");
		number = scanner.nextInt();
		System.out.println("Podaj saldo: (00,00)");
		balance = scanner.nextBigDecimal();
		System.out.println("Podaj login wlasciciela");
		ownerLogin = scanner.next();
		account = new Account(number, balance, ownerLogin);
		isOperationSuccesfull = dataContainer.registerAccount(account);
		if (isOperationSuccesfull) {
			System.out.println(SUCCESS_MESSAGE);
		} else {
			System.out.println(ERROR_MESSAGE);
		}
	}

	private void makeTransfer() {
		int choice;
		String ownerLogin = user.getLogin();
		Account account = dataContainer.getAccountByOwner(ownerLogin);
		Account targetAccount;
		BigDecimal amount;
		printAccounts();
		System.out.println(TRANSFER_MESSAGE_1);
		choice = scanner.nextInt();
		targetAccount = dataContainer.getAccountByNumber(choice);
		System.out.println(TRANSFER_MESSAGE_2);
		amount = scanner.nextBigDecimal();
		account.makeTransfer(targetAccount, amount);
	}

	private void printAccounts() {
		dataContainer.sortAccounts();
		List<Account> accountsList = dataContainer.getAccountsList();
		for (Account account : accountsList) {
			String ownerLogin = account.getOwnerLogin();
			Client client = dataContainer.getClientByLogin(ownerLogin);
			System.out.println(account + " " + client);
		}
	}

	private void saveProgress() {
		fileSupporter.writeToFiles();
	}
}
