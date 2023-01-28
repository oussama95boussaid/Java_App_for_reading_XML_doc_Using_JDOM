package com.example.jdom_java_project;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TextArea;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        // horizontally
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

    private void uploadXML_File(GridPane gridPane) {
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
        gridPane.add(VisualizeData, 0, 18, 4, 1);

        VisualizeData.setOnAction(new EventHandler<ActionEvent>() {
                                      @Override


                                      public void handle(ActionEvent event) {
                                          if (fileField.getText().isEmpty()) {
                                              showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
                                                      "Please Select XML File");
                                              return;
                                          }


                                          try {

                                              Stage stage = new Stage();
                                              stage.setTitle("Visualize Data");
                                              stage.setMinWidth(1200);
                                              stage.setMinHeight(((550)));
                                              final Label label = new Label("FILM DATA");
                                              label.setFont(new Font("Arial", 20));


//                                              SAXBuilder builder = new SAXBuilder();
//                                              File xmlFile = new File("Films.xml");
//                                              Document document = (Document) builder.build(xmlFile);
//                                              Element rootNode = document.getRootElement();


                                              SAXBuilder saxBuilder = new SAXBuilder();
                                              Document document = saxBuilder.build(new File("Films.xml"));
                                              Element rootNode = document.getRootElement();
                                              List<Element> list = rootNode.getChildren("FILM");


// Create table
                                              TableView<Person> table = new TableView<>();
                                              table.setPrefWidth(1200);
                                              table.setPrefHeight(500);
                                              table.setEditable(true);

// Create columns
                                              TableColumn col1 = new TableColumn<>("TITRE");
                                              col1.setMinWidth(150);
                                              col1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//                                              TableColumn<ObservableList<String>, String> col2 = new TableColumn<>("ANNEE");
//                                              col2.setMinWidth(150);
//                                              TableColumn<ObservableList<String>, String> col3 = new TableColumn<>("GENRE");
//                                              col3.setMinWidth(150);
//                                              TableColumn<ObservableList<String>, String> col4 = new TableColumn<>("PAYS");
//                                              col4.setMinWidth(150);
//                                              TableColumn<ObservableList<String>, String> col8 = new TableColumn<>("MES");
//                                              col8.setMinWidth(150);
//                                              TableColumn<ObservableList<String>, String> col5 = new TableColumn<>("PRENOM");
//                                              col5.setMinWidth(150);
//                                              TableColumn<ObservableList<String>, String> col6 = new TableColumn<>("NOM");
//                                              col6.setMinWidth(150);
//                                              TableColumn<ObservableList<String>, String> col7 = new TableColumn<>("RESUME");
//                                              col7.setMinWidth(150);
//                                              , col2, col3, col4, col5, col6, col7
                                              table.getColumns().addAll(col1);

//                                              DefaultTableModel model = new DefaultTableModel();
//                                              model.addColumn("Titre");
//                                              model.addColumn("Genre");
//                                              model.addColumn("Pays");
//                                              model.addColumn("Rôles");
//                                              model.addColumn("Résumé");
//
//                                              for (Element film : rootNode.getChildren("FILM")) {
//                                                  String titre = film.getChildText("TITRE");
//
//                                                  System.out.println("mkayna m3ana");
//                                                  System.out.println(titre);
//
//                                                  String genre = film.getChildText("GENRE");
//                                                  String pays = film.getChildText("PAYS");
//                                                  String roles = "";
//                                                  for (Element role : film.getChild("ROLES").getChildren("ROLE")) {
//                                                      roles += role.getChildText("PRENOM") + " " + role.getChildText("NOM") + " (" + role.getChildText("INTITULE") + ") ";
//                                                  }
//                                                  ObservableList<String> row = FXCollections.observableArrayList();
//                                                  String resume = film.getChildText("RESUME");
//                                                  row.addAll(Arrays.asList(titre, genre, pays, roles, resume));
//                                                  model.addRow(new Object[]{titre, genre, pays, roles, resume});
                                              //}
                                              ObservableList<Person> row;
                                              row = FXCollections.observableArrayList(
                                                      new Person()
                                              );

                                              for (int i = 0; i < list.size(); i++) {
                                                  Element node = (Element) list.get(i);
                                                  ObservableList<Person> row;
                                                  row = FXCollections.observableArrayList(
                                                          new Person(node.getChildText("TITRE"))
                                                  );
                                                  System.out.println("mkayna m3ana");
                                             System.out.println(row);
//                                                  row.add(node.getChildText("TITRE"));
//                                                  System.out.println(node.getChildText("TITRE"));
//                                                  row.add(node.getChildText("ANNEE"));
//                                                  row.add(node.getChildText("GENRE"));
//                                                  row.add(node.getChildText("PAYS"));
//                                                  row.add(node.getChildText("MES"));
//                                                  row.add(node.getChildText("PRENOM"));
//                                                  row.add(node.getChildText("NOM"));
//                                                  row.add(node.getChildText("RESUME"));

                                                  //table.getItems().add(new Person(new String(table.setItems(row))));
                                                  table.getItems().add(new Person(table.getItems().add(row).toString()));


                                              }
                                              table.setItems(row);

                                              VBox vBox = new VBox();
                                              vBox.getChildren().add(table);
                                              //getData(vBox);


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


                                      public VBox getData(VBox vBox) {
                                          // read XML file and display it

                                          try {
                                              SAXBuilder builder = new SAXBuilder();
                                              File xmlFile = new File("Films.xml");
                                              Document document = (Document) builder.build(xmlFile);
                                              Element rootNode = document.getRootElement();
                                              List<Element> list = rootNode.getChildren("FILM");
                                              for (int i = 0; i < list.size(); i++) {
                                                  Element node = (Element) list.get(i);
                                                  Label title = new Label("Title: " + node.getChildText("TITRE"));
                                                  vBox.getChildren().add(title);
                                                  Label annee = new Label("Annee: " + node.getChildText("ANNEE"));
                                                  vBox.getChildren().add(annee);

                                                  Label genre = new Label("Genre: " + node.getChildText("GENRE"));
                                                  gridPane.add(genre, 2, i);
                                                  Label pays = new Label("Pays: " + node.getChildText("PAYS"));
                                                  gridPane.add(pays, 3, i);
                                                  Label MES = new Label("MES: " + node.getChildText("MES"));
                                                  gridPane.add(MES, 4, i);
                                                  Label prenom = new Label("Prenom: " + node.getChildText("PRENOM"));
                                                  gridPane.add(prenom, 5, i);
                                                  Label nom = new Label("Nom: " + node.getChildText("NOM"));
                                                  gridPane.add(nom, 6, i);
                                                  Label resume = new Label("Resume: " + node.getChildText("RESUME"));
                                                  gridPane.add(resume, 7, i);

                                              }

                                              return vBox;
                                          } catch (IOException | JDOMException io) {
                                              System.out.println(io.getMessage());
                                          }
                                          return vBox;
                                      }
                                  }

        );

        Button VisualizeXMLFile = new Button("Visualize XML File");
        VisualizeXMLFile.setPrefHeight(40);
        VisualizeXMLFile.setPrefWidth(180);
        GridPane.setHalignment(VisualizeXMLFile, HPos.RIGHT);
        gridPane.add(VisualizeXMLFile, 0, 20, 4, 1);

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

                                             Element FilmsElement = ((Document) (new SAXBuilder()).build(new File("Films.xml"))).getRootElement();
                                             textArea.appendText("<" + FilmsElement.getName() + ">\n");

                                             List<Element> FilmsList = FilmsElement.getChildren("FILM");
                                             for (int i = 0; i < FilmsList.size(); i++) {
                                                 FilmElement = (Element) FilmsList.get(i);
                                                 textArea.appendText("\t<" + FilmElement.getName() + " Annee=" + FilmElement.getAttributeValue("Annee") + ">\n");
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
                                                 textArea.appendText("\t<" + artistElement.getName() + " id=" + artistElement.getAttributeValue("id") + ">\n");
                                                 textArea.appendText("\t\t<ACTNOM>" + artistElement.getChildText("ACTNOM") + "</ACTNOM>\n");
                                                 textArea.appendText("\t\t<ACTPNOM>" + artistElement.getChildText("ACTPNOM") + "</ACTPNOM>\n");
                                                 textArea.appendText("\t\t<ANNEENAISS>" + artistElement.getChildText("ANNEENAISS") + "</ANNEENAISS>\n");
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

        Person(String ftitel) {
            this.titel = new SimpleStringProperty(ftitel);
        }

        public String getFirstName() {
            return titel.get();
        }

        public void setFirstName(String ftitel) {
            titel.set(ftitel);
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

        Button addRolesButton = new Button("Add Roles");
        gridPane.add(addRolesButton, 1, 6);
        addRolesButton.setOnAction(arg0 -> {
            TextField prenomField = new TextField();
            prenomField.setPrefHeight(40);
            gridPane.add(prenomField, 1, 6);
            TextField nomField = new TextField();
            nomField.setPrefHeight(40);
            gridPane.add(nomField, 1, 7);
        });
        Label resumeLabel = new Label("Resume : ");
        gridPane.add(resumeLabel, 0, 8);

        TextArea resumeField = new TextArea();
        resumeField.setPrefHeight(400);
        gridPane.add(resumeField, 1, 8);

        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 9, 2, 1);

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
                    role.setAttribute("NOM", "NOM");
                    role.setAttribute("PRENOM", "PRENOM");
                    role.setAttribute("INTITULE", "INTITULE");
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

}