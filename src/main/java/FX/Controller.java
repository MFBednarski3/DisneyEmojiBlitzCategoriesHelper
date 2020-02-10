package FX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Controller {

	/**
	 * @author Mike Bednarski This is the controller which retrieves the CSV file results.
	 */
	@FXML
	private ComboBox<String> cat1;
	@FXML
	private ComboBox<String> cat2;
	@FXML
	private ComboBox<String> cat3;
	@FXML
	private ComboBox<String> box;
	@FXML
	private Button compute;

	@FXML
	private ListView<String> resultList;

	private final ArrayList<String> displayCats = new ArrayList<>(Arrays.asList("None", "Aladdin",
			"Alice in Wonderland", "Animal", "Bearded", "Beauty and the Beast", "Big Eared", "Blue", "Boys",
			"Cat or Dog", "Coco", "Fairies", "Finding Nemo/Dory", "Flying", "Four-legged", "Frozen", "Girls", "Green",
			"Hat-wearing", "Haunted Mansion", "Hercules", "Inside Out", "Jewelry", "Mickey and Friends", "Moana",
			"Monsters Inc.", "Mulan", "Nightmare Before Christmas", "Orange", "Outer Space", "Pirates of the Caribbean",
			"Pixar", "Pocahontas", "Prince", "Princess and the Frog", "Princesses", "Purple", "Red", "Rescue Rangers",
			"Robin Hood", "Scary", "Sleeping Beauty", "Snow White", "Star Wars", "Tangled", "The Emperor's New Groove",
			"The Incredibles", "The Jungle Book", "The Lion King", "The Little Mermaid", "The Muppets", "Toy Story",
			"Underwater", "Up", "Winnie the Pooh", "Wreck-It Ralph", "Yellow", "Zootopia"));
	private final String[] boxes = { "None", "Gold", "Rainbow", "Series", "Silver", "Villain" };

	private ArrayList<String> results = new ArrayList<>();

	BufferedReader br = null;
	
	// To run on start up
	@FXML
	public void initialize() {
		cat1.getItems().addAll(displayCats);
		cat1.getSelectionModel().selectFirst();
		cat2.getItems().addAll(displayCats);
		cat2.getSelectionModel().selectFirst();
		cat3.getItems().addAll(displayCats);
		cat3.getSelectionModel().selectFirst();
		box.getItems().addAll(boxes);
		box.getSelectionModel().selectFirst();

		compute.setDisable(true);

		cat1.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
					computeButtonSetting();
				});
		cat2.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
					computeButtonSetting();
				});
		cat3.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
					computeButtonSetting();
				});
		box.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
					computeButtonSetting();
				});
	}

	private void computeButtonSetting() {
		String s1 = cat1.getSelectionModel().getSelectedItem();
		String s2 = cat2.getSelectionModel().getSelectedItem();
		String s3 = cat3.getSelectionModel().getSelectedItem();
		String sBox = box.getSelectionModel().getSelectedItem();
		if (!s1.equals("None") && !s2.equals("None") && !s3.equals("None") && !sBox.equals("None")) {
			compute.setDisable(true);
		} else if (s1.equals("None") && s2.equals("None") && s3.equals("None") && sBox.equals("None")) {
			compute.setDisable(true);
		} else {
			compute.setDisable(false);
		}

	}

	@FXML
	public void computeButtonPressed() {
		String s1 = cat1.getSelectionModel().getSelectedItem();
		String s2 = cat2.getSelectionModel().getSelectedItem();
		String s3 = cat3.getSelectionModel().getSelectedItem();
		String sBox = box.getSelectionModel().getSelectedItem();
		HashSet<String> catsToCompute = new HashSet<>();
		catsToCompute.add(s1);
		catsToCompute.add(s2);
		catsToCompute.add(s3);
		if (catsToCompute.contains("None")) {
			catsToCompute.remove("None");
		}
		setUpComputation(catsToCompute, sBox);

	}

	private void setUpComputation(HashSet<String> cats, String box) {
		openFile();
		results.clear();
		resultList.getItems().clear();
		Object[] catsArray = cats.toArray();
		if (box.equals("None")) {
			if (catsArray.length == 1) {
				categoryExecution(displayCats.indexOf(catsArray[0]), -1, -1);
			}
			if (catsArray.length == 2) {
				categoryExecution(displayCats.indexOf(catsArray[0]), displayCats.indexOf(catsArray[1]), -1);
			}
			if (catsArray.length == 3) {
				categoryExecution(displayCats.indexOf(catsArray[0]), displayCats.indexOf(catsArray[1]),
						displayCats.indexOf(catsArray[2]));
			}

		} else {
			if (catsArray.length == 0) {
				boxExecution(box, -1, -1);
			}
			if (catsArray.length == 1) {
				boxExecution(box, displayCats.indexOf(catsArray[0]), -1);
			}
			if (catsArray.length == 2) {
				boxExecution(box, displayCats.indexOf(catsArray[0]), displayCats.indexOf(catsArray[1]));
			}
		}
		if (resultList.getItems().isEmpty()) {
			resultList.getItems().add("No Emojis");
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void openFile() {
		try {
			br = new BufferedReader(new FileReader("DisneyEmojiBlitzCategories1.3.csv"));
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		}
	}

	private void categoryExecution(int c1, int c2, int c3) {
		try {
			String line = "";
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] conditions = line.split(",");
				if (c2 == -1 && c3 == -1) {
					if (conditions[c1 + 2].equals("Yes"))
						results.add(conditions[0]);
				} else if (c3 == -1) {
					if (conditions[c1 + 2].equals("Yes") && conditions[c2 + 2].equals("Yes"))
						results.add(conditions[0]);
				} else {
					if (conditions[c1 + 2].equals("Yes") && conditions[c2 + 2].equals("Yes")
							&& conditions[c3 + 2].equals("Yes"))
						results.add(conditions[0]);
				}

			}
			for (String emoji : results) {
				resultList.getItems().add(emoji);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void boxExecution(String box, int c1, int c2) {
		try {
			String line = "";
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] conditions = line.split(",");
				if (c1 == -1 && c2 == -1) {
					if (conditions[1].equals(box))
						results.add(conditions[0]);
				} else if (c2 == -1) {
					if (conditions[1].equals(box) && conditions[c1 + 2].equals("Yes"))
						results.add(conditions[0]);
				} else {
					if (conditions[1].equals(box) && conditions[c1 + 2].equals("Yes")
							&& conditions[c2 + 2].equals("Yes"))
						results.add(conditions[0]);
				}

			}
			for (String emoji : results) {
				resultList.getItems().add(emoji);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
