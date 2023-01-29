package com.example.jdom_java_project;
//OUSSAMA BOUSSAID & WAFA ABENAY

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;
//OUSSAMA BOUSSAID & WAFA ABENAY
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TextArea;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.ImageView;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("XMLPROJECT");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        //
        uploadXML_File(gridPane);

        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void uploadXML_File(GridPane gridPane) throws FileNotFoundException {
        // Add Header
        Label headerLabel = new Label("Welcome To Your Application");
        headerLabel.setFont(Font.font("Barlow", FontWeight.BOLD, 32));
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        gridPane.add(headerLabel, 0, 0, 4, 1); // change column index to 0

        Label titleLabel = new Label("Get Started By Uploading Your XML File");
        titleLabel.setFont(Font.font("Barlow", 24));
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        gridPane.add(titleLabel, 0, 6, 6, 1);

        // Add File Input
        Label fileLabel = new Label("Select XML File:");
        gridPane.add(fileLabel, 0, 10, 1, 1);

        TextField fileField = new TextField();
        fileField.setPromptText("No file selected");
        gridPane.add(fileField, 1, 10);

        Button browseButton = new Button("Browse");
        gridPane.add(browseButton, 2, 10);
        GridPane.setMargin(headerLabel, new Insets(0, 0, 0, 20));
        browseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select XML File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("XML Files", "*.xml"));
                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null) {
                    fileField.setText(selectedFile.getAbsolutePath());
                    System.out.println("Selected File: " + selectedFile.getAbsolutePath());
                }

            }
        });

        Button AddFilmButton = new Button("Add New Film To XML File");
        AddFilmButton.setPrefHeight(40);
        AddFilmButton.setMinWidth(200);
        AddFilmButton.setMaxWidth(200);
        GridPane.setHalignment(AddFilmButton, HPos.CENTER);
        gridPane.add(AddFilmButton, 0, 16, 4, 1);
        AddFilmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (fileField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                            "Please Select XML File");
                    return;
                }
                Stage stage = new Stage();
                GridPane gridPane = createRegistrationFormPane();
                // Add UI controls to the registration form grid pane
                addUIControls(gridPane);
                Scene scene = new Scene(gridPane, 800, 500);
                stage.setScene(scene);
                stage.setTitle("Add Form");
                stage.show();
            }
        });

        Button VisualizeData = new Button("Visualize Data");
        VisualizeData.setPrefHeight(40);
        VisualizeData.setPrefWidth(100);
        GridPane.setHalignment(VisualizeData, HPos.RIGHT);
        gridPane.add(VisualizeData, 0, 16, 4, 1);

        VisualizeData.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent event) {
                if (fileField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please Select XML File");
                    return;
                }
                try {

                    Stage stage = new Stage();
                    stage.setTitle("Visualize Data");
                    stage.setMinWidth(1070);
                    stage.setMinHeight(((550)));
                    final Label label = new Label("FILM DATA");
                    label.setFont(new Font("Arial", 20));

                    SAXBuilder saxBuilder = new SAXBuilder();
                    Document document = saxBuilder.build(new File(fileField.getText()));
                    Element rootNode = document.getRootElement();
                    List<Element> list = rootNode.getChildren("FILM");
                    TableView<Person> table = new TableView<>();
                    table.setPrefWidth(1070);
                    table.setPrefHeight(500);
                    table.setEditable(true);

                    TableColumn col1 = new TableColumn<>("TITRE");
                    col1.setMinWidth(150);
                    col1.setCellValueFactory(new PropertyValueFactory<>("titel"));
                    TableColumn col2 = new TableColumn<>("ANNEE");
                    col2.setMinWidth(150);
                    col2.setCellValueFactory(new PropertyValueFactory<>("annee"));
                    TableColumn col3 = new TableColumn<>("GENRE");
                    col3.setMinWidth(150);
                    col3.setCellValueFactory(new PropertyValueFactory<>("genre"));
                    TableColumn col4 = new TableColumn<>("PAYS");
                    col4.setMinWidth(150);
                    col4.setCellValueFactory(new PropertyValueFactory<>("pays"));
                    TableColumn col5 = new TableColumn<>("PRENOM");
                    col5.setMinWidth(150);
                    col5.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                    TableColumn col6 = new TableColumn<>("NOM");
                    col6.setMinWidth(150);
                    col6.setCellValueFactory(new PropertyValueFactory<>("nom"));
                    TableColumn col7 = new TableColumn<>("RESUME");
                    col7.setMinWidth(150);
                    col7.setCellValueFactory(new PropertyValueFactory<>("resume"));


                    table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);

                    for (int i = 0; i < list.size(); i++) {
                        Element node = (Element) list.get(i);

                        String prenom = "", nom = "";
                        Element role = node.getChild("ROLES").getChild("ROLE");
                        if (role != null) {
                            Element prenomNode = role.getChild("PRENOM");
                            if (prenomNode != null) {
                                prenom = prenomNode.getText();
                            }
                            Element nomNode = role.getChild("NOM");
                            if (nomNode != null) {
                                nom = nomNode.getText();
                            }
                                                  }

                        ObservableList<Person> row;
                        row = FXCollections.observableArrayList(new Person(node.getChildText("TITRE"),
                                node.getAttributeValue("Annee"),
                                node.getChildText("GENRE"), node.getChildText("PAYS"),
                                prenom, nom,
                                node.getChildText("RESUME"),
                                node.getAttributeValue("mesIdRef")));

                        System.out.println(row);

                        table.getItems().addAll(row);
                    }
                    VBox vBox = new VBox();
                    vBox.getChildren().add(table);

                    vBox.setStyle("-fx-padding: 10;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 5;");

                    Scene scene = new Scene(vBox);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException | JDOMException io) {
                    System.out.println(io.getMessage());
                }

            }

        }

        );

        Button VisualizeXMLFile = new Button("Visualize XML File");
        VisualizeXMLFile.setPrefHeight(40);
        VisualizeXMLFile.setPrefWidth(150);
        GridPane.setHalignment(VisualizeXMLFile, HPos.LEFT);
        gridPane.add(VisualizeXMLFile, 0, 16, 4, 1);

        VisualizeXMLFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (fileField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                            "Please Select XML File");
                    return;
                }

                try {

                    Stage stage = new Stage();
                    stage.setTitle("Visualize XML File");
                    stage.setMinWidth(1000);
                    stage.setMinHeight(500);

                    VBox vBox = new VBox();
                    vBox.getChildren().add(readXMLFile());

                    vBox.setStyle("-fx-padding: 10;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 5;");

                    Scene scene = new Scene(vBox, 600, 400);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException | JDOMException io) {
                    System.out.println(io.getMessage());
                }

            }

            public TextArea readXMLFile() throws JDOMException, IOException {

                TextArea textArea = new TextArea();
                textArea.setMinHeight(440);
                textArea.setMaxHeight(440);

                Element FilmElement = null;
                Element artistElement = null;
                Element RolesElement = null;
                Element RoleElement = null;

                Element FilmsElement = ((Document) (new SAXBuilder()).build(new File(fileField.getText())))
                        .getRootElement();
                textArea.appendText("<" + FilmsElement.getName() + ">\n");

                List<Element> FilmsList = FilmsElement.getChildren("FILM");
                for (int i = 0; i < FilmsList.size(); i++) {
                    FilmElement = (Element) FilmsList.get(i);
                    textArea.appendText(
                            "\t<" + FilmElement.getName() + " Annee=" + FilmElement.getAttributeValue("Annee") + ">\n");
                    textArea.appendText("\t\t<TITRE>" + FilmElement.getChildText("TITRE") + "</TITRE>\n");
                    textArea.appendText("\t\t<GENRE>" + FilmElement.getChildText("GENRE") + "</GENRE>\n");
                    textArea.appendText("\t\t<PAYS>" + FilmElement.getChildText("PAYS") + "</PAYS>\n");
                    textArea.appendText("\t\t<MES>" + FilmElement.getChildText("MES") + "</MES>\n");
                    textArea.appendText("\t\t<RESUME>" + FilmElement.getChildText("RESUME") + "</RESUME>\n");
                    RolesElement = FilmsList.get(i).getChild("ROLES");

                    textArea.appendText("\t\t<" + RolesElement.getName() + ">\n");
                    List<Element> RoleList = RolesElement.getChildren("ROLE");
                    for (int z = 0; z < RoleList.size(); z++) {
                        RoleElement = (Element) RoleList.get(z);
                        textArea.appendText("\t\t<" + RoleElement.getName() + ">");
                        textArea.appendText("\t\t<PRENOM>" + RoleElement.getChildText("PRENOM") + "</PRENOM>\n");
                        textArea.appendText("\t\t<NOM>" + RoleElement.getChildText("NOM") + "</NOM>\n");
                        textArea.appendText("\t\t<INTITULE>" + RoleElement.getChildText("INTITULE") + "</INTITULE>\n");
                        textArea.appendText("</" + RoleElement.getName() + ">\n");
                    }

                    textArea.appendText("\t\t</" + RolesElement.getName() + ">\n");
                    textArea.appendText("\t</" + FilmElement.getName() + ">\n");

                }

                List<Element> artistlist = FilmsElement.getChildren("ARTISTE");
                for (int j = 0; j < artistlist.size(); j++) {
                    artistElement = artistlist.get(j);
                    textArea.appendText(
                            "\t<" + artistElement.getName() + " id=" + artistElement.getAttributeValue("id") + ">\n");
                    textArea.appendText("\t\t<ACTNOM>" + artistElement.getChildText("ACTNOM") + "</ACTNOM>\n");
                    textArea.appendText("\t\t<ACTPNOM>" + artistElement.getChildText("ACTPNOM") + "</ACTPNOM>\n");
                    textArea.appendText(
                            "\t\t<ANNEENAISS>" + artistElement.getChildText("ANNEENAISS") + "</ANNEENAISS>\n");
                    textArea.appendText("\t</" + artistElement.getName() + ">\n");

                }

                textArea.appendText("<" + FilmsElement.getName() + ">\n");

                return textArea;
            }
        }

        );

    }

    public class Person {
        public final SimpleStringProperty titel;
        public final SimpleStringProperty annee;
        public final SimpleStringProperty genre;
        public final SimpleStringProperty pays;
        public final SimpleStringProperty prenom;
        public final SimpleStringProperty nom;
        public final SimpleStringProperty resume;

        public final SimpleStringProperty mesIdRef;

        Person(String ftitel, String fannee, String fgenre, String fpays, String fprenom, String fnom, String fresume,
               String fmesIdRef) {
            this.titel = new SimpleStringProperty(ftitel);
            this.annee = new SimpleStringProperty(fannee);
            this.genre = new SimpleStringProperty(fgenre);
            this.pays = new SimpleStringProperty(fpays);
            this.nom = new SimpleStringProperty(fnom);
            this.prenom = new SimpleStringProperty(fprenom);
            this.resume = new SimpleStringProperty(fresume);
            this.mesIdRef = new SimpleStringProperty(fmesIdRef);
        }

        // getters and setters for each field
        public String getTitel() {
            return titel.get();
        }

        public void setTitel(String ftitel) {
            titel.set(ftitel);
        }

        public String getAnnee() {
            return annee.get();
        }

        public void setAnnee(String fannee) {
            annee.set(fannee);
        }

        public String getGenre() {
            return genre.get();
        }

        public void setGenre(String fgenre) {
            genre.set(fgenre);
        }

        public String getPays() {
            return pays.get();
        }

        public void setPays(String fpays) {
            pays.set(fpays);
        }

        public String getPrenom() {
            return prenom.get();
        }

        public void setPrenom(String fprenom) {
            prenom.set(fprenom);
        }

        public String getNom() {
            return nom.get();
        }

        public void setNom(String fnom) {
            nom.set(fnom);
        }

        public String getResume() {
            return resume.get();
        }

        public void setResume(String fresume) {
            resume.set(fresume);
        }

        public String getMesIdRef() {
            return mesIdRef.get();
        }

        public void setMesIdRef(String fmesIdRef) {
            mesIdRef.set(fmesIdRef);
        }

    }

    private void addUIControls(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Add Films");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        Label titleLabel = new Label("Title: ");
        gridPane.add(titleLabel, 0, 1);

        TextField titleField = new TextField();
        titleField.setPrefHeight(40);
        gridPane.add(titleField, 1, 1);

        Label anneeLabel = new Label("Annee : ");
        gridPane.add(anneeLabel, 0, 2);

        DatePicker AnneeField = new DatePicker();
        AnneeField.setPrefHeight(40);
        gridPane.add(AnneeField, 1, 2);

        Label genreLabel = new Label("Genre : ");
        gridPane.add(genreLabel, 0, 3);

        TextField genreField = new TextField();
        genreField.setPrefHeight(40);
        gridPane.add(genreField, 1, 3);

        Label paysLabel = new Label("Pays : ");
        gridPane.add(paysLabel, 0, 4);

        TextField paysField = new TextField();
        paysField.setPrefHeight(40);
        gridPane.add(paysField, 1, 4);

        Label MESLabel = new Label("MES : ");
        gridPane.add(MESLabel, 0, 5);

        TextField MESField = new TextField();
        MESField.setPrefHeight(40);
        gridPane.add(MESField, 1, 5);

        Label RolesLabel = new Label("Roles : ");
        gridPane.add(RolesLabel, 0, 6);

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prenom");
        prenomField.setPrefHeight(40);
        gridPane.add(prenomField, 1, 6);
        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        nomField.setPrefHeight(40);
        gridPane.add(nomField, 1, 7);
        TextField INTITULEField = new TextField();
        INTITULEField.setPromptText("Intitules");
        INTITULEField.setPrefHeight(40);
        gridPane.add(INTITULEField, 1, 8);

        Label resumeLabel = new Label("Resume : ");
        gridPane.add(resumeLabel, 0, 9);

        TextArea resumeField = new TextArea();
        resumeField.setPrefHeight(400);
        gridPane.add(resumeField, 1, 9);

        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 11, 2, 1);

        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (titleField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                            "Please enter the Title");
                    return;
                }

                if (resumeField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                            "Please enter a Summary");
                    return;
                }
                if (genreField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                            "Please enter a Genre");
                    return;
                }
                if (paysField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                            "Please enter a Pays");
                    return;
                }
                if (MESField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                            "Please enter a MES");
                    return;
                }
                if (AnneeField.getValue() == null) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                            "Please enter a Year");
                    return;
                }

                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!",
                        "Movie " + titleField.getText() + " has been registered successfully.");
                save();
            }

            public void save() {
                // save form to Films xml file
                // using saxbuilder
                try {
                    SAXBuilder builder = new SAXBuilder();
                    Document doc = builder.build(new File("Films2.xml"));

                    Element root = doc.getRootElement();
                    Element FILM = new Element("FILM");

                    FILM.setAttribute("annee", AnneeField.getValue().toString());

                    root.addContent(FILM);

                    // save values and attributes

                    Element title = new Element("TITRE");
                    title.setText(titleField.getText());
                    FILM.addContent(title);
                    Element annee = new Element("ANNEE");
                    annee.setText(AnneeField.getValue().toString());
                    FILM.addContent(annee);
                    Element genre = new Element("GENRE");
                    genre.setText(genreField.getText());
                    FILM.addContent(genre);
                    Element pays = new Element("PAYS");
                    pays.setText(paysField.getText());
                    FILM.addContent(pays);
                    Element MES = new Element("MES");
                    MES.setText(MESField.getText());
                    FILM.addContent(MES);
                    Element resume = new Element("RESUME");
                    resume.setText(resumeField.getText());
                    FILM.addContent(resume);
                    // save roles
                    Element roles = new Element("ROLES");
                    FILM.addContent(roles);
                    Element role = new Element("ROLE");
                    role.setAttribute("NOM", prenomField.getText());
                    role.setAttribute("PRENOM", prenomField.getText());
                    role.setAttribute("INTITULE", prenomField.getText());
                    roles.addContent(role);
                    // save to xml file

                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(doc, new FileWriter("Films2.xml"));
                    System.out.println("File Saved!");
                } catch (JDOMException | IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//OUSSAMA BOUSSAID & WAFA ABENAY

}
