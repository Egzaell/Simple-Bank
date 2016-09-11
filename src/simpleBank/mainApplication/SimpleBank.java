package simpleBank.mainApplication;

import simpleBank.dataContainer.DataContainer;
import simpleBank.fileSupporter.FileSupporter;
import simpleBank.userInterface.UserInterface;

public class SimpleBank {

	private DataContainer dataContainer;
	private FileSupporter fileSupporter;
	private UserInterface userInterface;
	
	public SimpleBank() {
		dataContainer = new DataContainer();
		fileSupporter = new FileSupporter(dataContainer);
		fileSupporter.readFromFiles();
		userInterface = new UserInterface(dataContainer, fileSupporter);
	}
	
	public static void main(String[] args) {
		SimpleBank simpleBank = new SimpleBank();
	}
}
