package com.chess.gui;

import com.chess.Authenticator;
import com.chess.engine.player.Member;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Asus on 2017-07-25.
 */
public class LoginPage extends Application implements Initializable {

    private static Stage primaryStage;
    @FXML
    private Hyperlink loginHyperlink;
    @FXML
    private Hyperlink signupHyperlin;
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXTextField passwordTextField;
    @FXML
    private Label errMessage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = (Stage) primaryStage;
        if (primaryStage == null) System.err.print("Nulllllllllllllllllllllll");
        gotoLogin();
        this.primaryStage.show();
    }

    @FXML
    public void handleLoginHyperlink(){
        String username = usernameTextField.getText(); String password = passwordTextField.getText();
        if(!userLogging(username, password)) errMessage.setText("Username OR Password is incorrect!");
    }
    @FXML
    public void handleSignupHyperlink(){
        gotoProfile(null);
    }

    public boolean userLogging(String userId, String password){
        if (Authenticator.validate(userId, password)) {
            Member loggedMember = Member.of(userId);
            gotoProfile(loggedMember);
            return true;
        } else {
            return false;
        }
    }

    private void gotoProfile(Member member) {
        primaryStage.setTitle("Memebr Profile");
        try {
            ProfilePage profilePage = (ProfilePage) replaceSceneContent("ProfilePage.fxml");
            profilePage.setLoggedMember(member);

        } catch (Exception ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void gotoLogin() {
        try {
            primaryStage.setTitle("Login ChessGame");
            LoginPage loginPage = (LoginPage) replaceSceneContent("LoginPage.fxml");
            } catch (Exception ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = LoginPage.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(LoginPage.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        return (Initializable) loader.getController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





}
