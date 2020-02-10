package FX;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class Controller {

    /**
     * @author Mike Bednarski This is the controller which controls the SQL
     * database results
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

    private final String[] sqlCats = {"None", "Aladdin", "Alice_in_Wonderland", "Animal", "Bearded", "Beauty_and_the_Beast",
        "BigEared", "Blue", "Boys", "Cat_or_Dog", "Coco", "Fairies", "Finding_NemoDory", "Flying", "FourLegged",
        "Frozen", "Girls", "Green", "Hatwearing", "Haunted_Mansion", "Hercules", "Inside_Out", "Jewelry",
        "Mickey_and_Friends", "Moana", "Monsters_Inc", "Mulan", "Nightmare_Before_Christmas", "Orange",
        "Outer_Space", "Pirates_of_the_Caribbean", "Pixar", "Pocahontas", "Prince", "Princess_and_the_Frog",
        "Princesses", "Purple", "Red", "Rescue_Rangers", "Robin_Hood", "Scary", "Sleeping_Beauty", "Snow_White",
        "Star_Wars", "Tangled", "The_Emperors_New_Groove", "The_Incredibles", "The_Jungle_Book", "The_Lion_King",
        "The_Little_Mermaid", "The_Muppets", "Toy_Story", "Underwater", "Up", "Winnie_the_Pooh", "WreckIt_Ralph",
        "Yellow", "Zootopia"};
    private final String[] displayCats = {"None", "Aladdin", "Alice in Wonderland", "Animal", "Bearded",
        "Beauty and the Beast", "Big Eared", "Blue", "Boys", "Cat or Dog", "Coco", "Fairies", "Finding Nemo/Dory",
        "Flying", "Four-Legged", "Frozen", "Girls", "Green", "Hat-wearing", "Haunted Mansion", "Hercules",
        "Inside Out", "Jewelry", "Mickey and Friends", "Moana", "Monsters Inc.", "Mulan",
        "Nightmare Before Christmas", "Orange", "Outer Space", "Pirates of the Caribbean", "Pixar", "Pocahontas",
        "Prince", "Princess and the Frog", "Princesses", "Purple", "Red", "Rescue Rangers", "Robin Hood", "Scary",
        "Sleeping Beauty", "Snow White", "Star Wars", "Tangled", "The Emperor's New Groove", "The Incredibles",
        "The Jungle Book", "The Lion King", "The Little Mermaid", "The Muppets", "Toy Story", "Underwater", "Up",
        "Winnie the Pooh", "Wreck-It Ralph", "Yellow", "Zootopia"};
    private final String[] boxes = {"None", "Gold", "Rainbow", "Series", "Silver", "Villain"};

    private final HashMap<String, String> catsMap = new HashMap<>();

    Connection conn = null;

    // To run on start up
    @FXML
    public void initialize() {
        assert (sqlCats.length == displayCats.length);
        for (int i = 0; i < sqlCats.length; i++) {
            catsMap.put(displayCats[i], sqlCats[i]);
        }
        cat1.getItems().addAll(displayCats);
        cat1.getSelectionModel().selectFirst();
        cat2.getItems().addAll(displayCats);
        cat2.getSelectionModel().selectFirst();
        cat3.getItems().addAll(displayCats);
        cat3.getSelectionModel().selectFirst();
        box.getItems().addAll(boxes);
        box.getSelectionModel().selectFirst();

        compute.setDisable(true);

        cat1.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
            computeButtonSetting();
        });
        cat2.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
            computeButtonSetting();
        });
        cat3.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
            computeButtonSetting();
        });
        box.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
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
        connect();
        resultList.getItems().clear();
        Object[] catsArray = cats.toArray();
        if (box.equals("None")) {
            if (catsArray.length == 1) {
                oneCategory((String) catsArray[0]);
            }
            if (catsArray.length == 2) {
                twoCategories((String) catsArray[0], (String) catsArray[1]);
            }
            if (catsArray.length == 3) {
                threeCategories((String) catsArray[0], (String) catsArray[1], (String) catsArray[2]);
            }

        } else {
            if (catsArray.length == 0) {
                selectFromBox(box);
            }
            if (catsArray.length == 1) {
                selectFromBoxAndOne(box, (String) catsArray[0]);
            }
            if (catsArray.length == 2) {
                selectFromBoxAndTwo(box, (String) catsArray[0], (String) catsArray[1]);
            }
        }
        if (resultList.getItems().isEmpty()) {
            resultList.getItems().add("No Emojis");
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("The catch has been made!\n");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        //String url = "jdbc:sqlite:src/main/resources/FX/DisneyEB.db";
        //System.out.println("URL: " + url);
        String url = "jdbc:sqlite:DisneyEB.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    void oneCategory(String cat) {
        cat = catsMap.get(cat);
        String sql = "SELECT Emoji FROM Categories WHERE " + cat + " = 'Yes'";
        categoryExecution(sql);

    }

    void twoCategories(String cat1, String cat2) {
        cat1 = catsMap.get(cat1);
        cat2 = catsMap.get(cat2);
        String sql = "SELECT Emoji FROM Categories WHERE " + cat1 + " = 'Yes' AND " + cat2 + " = 'Yes'";
        categoryExecution(sql);

    }

    void threeCategories(String cat1, String cat2, String cat3) {
        cat1 = catsMap.get(cat1);
        cat2 = catsMap.get(cat2);
        cat3 = catsMap.get(cat3);
        String sql = "SELECT Emoji FROM Categories WHERE " + cat1 + " = 'Yes' AND " + cat2 + " = 'Yes' AND " + cat3
                + " = 'Yes'";
        categoryExecution(sql);

    }

    private void selectFromBox(String box) {
        String sql = "SELECT Emoji FROM Categories WHERE Box = ?";
        boxExecution(box, sql);
    }

    private void selectFromBoxAndOne(String box, String cat1) {
        cat1 = catsMap.get(cat1);
        String sql = "SELECT Emoji FROM Categories WHERE Box = ? AND " + cat1 + " = 'Yes'";
        boxExecution(box, sql);
    }

    private void selectFromBoxAndTwo(String box, String cat1, String cat2) {
        cat1 = catsMap.get(cat1);
        cat2 = catsMap.get(cat2);
        String sql = "SELECT Emoji FROM Categories WHERE Box = ? AND " + cat1 + " = 'Yes' AND " + cat2 + " = 'Yes'";
        boxExecution(box, sql);

    }

    private void categoryExecution(String sql) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                resultList.getItems().add(rs.getString("Emoji"));
            }
        } catch (SQLException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    private void boxExecution(String box, String sql) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, box);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resultList.getItems().add(rs.getString("Emoji"));
            }
        } catch (SQLException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }
    

}
