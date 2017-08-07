package com.chess.gui;

import com.chess.engine.player.Member;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Asus on 2017-07-26.
 */
public class ProfilePage extends AnchorPane implements Initializable {
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private JFXButton startGameButton;
    @FXML
    private JFXButton setProfilePictureButton;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXTextField nameTextField;
    @FXML
    private JFXTextField emailTextField;
    @FXML
    private StackPane photoContainer;
    private Member loggedMember;
    private Background background;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    @FXML
    public void handleSaveButton(){

    }
    @FXML
    public void handleStartGameButton(){
        FxTable.setHostMember(loggedMember);
        Table.setHostMember(loggedMember);
        Table.get().show();
    }
    @FXML
    public void handleSetProfilePicture() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        loggedMember.setImagePath(fileChooser.showOpenDialog(FxTable.primaryStage).toString());
        System.out.print(loggedMember.getImagePath());
//
//        Image image = new Image(loggedMember.getImagePath());
//        ImageView imageView = new ImageView(image);
//        imageView.setClip(new Circle());
        photoContainer.setMaxSize(100,100);
//        photoContainer.getChildren().addAll(imageView);




    }

    public void setLoggedMember(Member loggedMember) {
        this.loggedMember = loggedMember;
        fillTextFields(loggedMember);

    }

    private void fillTextFields(Member loggedMember) {
        if (loggedMember == null) {
            nameTextField.setText("");
            emailTextField.setText("");
        } else {
            usernameTextField.setEditable(false);
            usernameTextField.setText(loggedMember.getId());
            nameTextField.setText(loggedMember.getName());
            emailTextField.setText(loggedMember.getEmail());
        }
    }
}
