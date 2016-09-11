package simpleBank.fileSupporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import simpleBank.account.Account;
import simpleBank.client.Client;
import simpleBank.dataContainer.DataContainer;

public class FileSupporter {

	private static final String CLIENT_FILE_NAME = "clients.csv";
	private static final String ACCOUNT_FILE_NAME = "accounts.csv";
	private static final String CLIENT_FILE_HEADER = "login,password,firstName,lastName,birthDate";
	private static final String ACCOUNT_FILE_HEADER = "number,balance,ownerLogin";
	private static final String SEPARATOR = ",";
	private static final String NEW_LINE = System.getProperty("line.separator");
	private File file;
	private FileWriter fileWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private DataContainer dataContainer;

	public FileSupporter(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	public void writeToFiles() {
		try {
			writeClientsToFile();
			writeAccountsToFile();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeFileWriter();
		}
	}

	public void readFromFiles() {
		try {
			readClientsFromFile();
			readAccountsFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeReadingFromFile();
		}
	}

	private void closeFileWriter() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeReadingFromFile() {
		try {
			fileReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeClientsToFile() throws IOException {
		List<Client> clientsList = dataContainer.getClientsList();
		file = new File(CLIENT_FILE_NAME);
		fileWriter = new FileWriter(file);
		fileWriter.append(CLIENT_FILE_HEADER);
		fileWriter.append(NEW_LINE);
		for (Client client : clientsList) {
			String login = client.getLogin();
			String password = client.getPassword();
			String firstName = client.getFirstName();
			String lastName = client.getLastName();
			String birthDate = client.getBirthDate();
			fileWriter.append(login);
			fileWriter.append(SEPARATOR);
			fileWriter.append(password);
			fileWriter.append(SEPARATOR);
			fileWriter.append(firstName);
			fileWriter.append(SEPARATOR);
			fileWriter.append(lastName);
			fileWriter.append(SEPARATOR);
			fileWriter.append(birthDate);
			fileWriter.append(NEW_LINE);
		}
		fileWriter.flush();
	}

	private void writeAccountsToFile() throws IOException {
		List<Account> accountsList = dataContainer.getAccountsList();
		file = new File(ACCOUNT_FILE_NAME);
		fileWriter = new FileWriter(file);
		fileWriter.append(ACCOUNT_FILE_HEADER);
		fileWriter.append(NEW_LINE);
		for (Account account : accountsList) {
			Integer number = account.getNumber();
			BigDecimal balance = account.getBalance();
			String ownerLogin = account.getOwnerLogin();
			fileWriter.append(number.toString());
			fileWriter.append(SEPARATOR);
			fileWriter.append(balance.toString());
			fileWriter.append(SEPARATOR);
			fileWriter.append(ownerLogin);
			fileWriter.append(NEW_LINE);
		}
		fileWriter.flush();
	}

	private void readClientsFromFile() throws IOException {
		String line;
		file = new File(CLIENT_FILE_NAME);
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		line = bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null) {
			String[] clientValues = line.split(SEPARATOR);
			String login = clientValues[0];
			String password = clientValues[1];
			String firstName = clientValues[2];
			String lastName = clientValues[3];
			String birthDate = clientValues[4];
			Client client = new Client.ClientBuilder().login(login).password(password).firstName(firstName)
					.lastName(lastName).birthDate(birthDate).build();
			dataContainer.registerClient(client);
		}
	}

	private void readAccountsFromFile() throws IOException {
		String line;
		file = new File(ACCOUNT_FILE_NAME);
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		line = bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null) {
			String[] accountValues = line.split(SEPARATOR);
			int number = Integer.parseInt(accountValues[0]);
			BigDecimal balance = new BigDecimal(accountValues[1]);
			String ownerLogin = accountValues[2];
			Account account = new Account(number, balance, ownerLogin);
			dataContainer.registerAccount(account);
		}
	}
}
