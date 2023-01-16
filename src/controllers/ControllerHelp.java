/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Tonny
 */
public class ControllerHelp implements Initializable {

    private Scene scene;

    private Stage stage;

    @FXML
    private BorderPane help;
    @FXML
    private ImageView img;
    @FXML
    private HBox btnBox;

    @FXML
    public void switchToSetting() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SceneSetting.fxml"));
        Parent root = loader.load();

        ControllerSetting set = (ControllerSetting) loader.getController();
        String css = help.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");

        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        set.exemple.getStylesheets().clear();
        set.exemple.getStylesheets().add(css);

        if (css.toLowerCase().contains("light")) {
            css = "Light Mode";
        } else if (css.toLowerCase().contains("dark")) {
            css = "Dark Mode";
        } else {
            css = "";
        }
        set.modeSwitch.setValue(css);

        stage = (Stage) help.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void switchToConverter() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneConverter.fxml"));

        String css = help.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) help.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToHistory() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/SceneHistory.fxml"));

        String css = help.getStylesheets().toString().replaceAll("[^a-zA-Z0-9/:.]", "");
        root.getStylesheets().clear();
        root.getStylesheets().add(css);

        stage = (Stage) help.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void nextBtnHandler() throws IOException {

        String url = img.getImage().getUrl();
        String imgID = imgID(url);

        if (!"11".equals(imgID)) {

            FadeTransition fader1 = createFader2(img);
            fader1.play();

            int imgNewID = Integer.valueOf(imgID) + 1;

            Image image = new Image("img/" + imgNewID + ".png");
            img.setImage(image);

            FadeTransition fader2 = createFader1(img);
            fader2.play();
        } else {
            switchToConverter();
        }

    }

    @FXML
    private void previousBtnHandler() throws IOException {

        String url = img.getImage().getUrl();
        String imgID = imgID(url);
        if (!"1".equals(imgID)) {

            FadeTransition fader1 = createFader2(img);
            fader1.play();

            int imgNewID = Integer.valueOf(imgID) - 1;

            Image image = new Image("img/" + imgNewID + ".png");
            img.setImage(image);

            FadeTransition fader2 = createFader1(img);
            fader2.play();
        }
    }

    private FadeTransition createFader1(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), node);
        fade.setFromValue(0);
        fade.setToValue(1);

        return fade;
    }

    private FadeTransition createFader2(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1), node);
        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }

    private String imgID(String url) {
        String imgID = "";
        String[] sort = url.split("/");
        for (String a : sort)
            if (a.contains(".png")) {
                imgID = a.replaceAll("[^0-9]", "");
            }

        return imgID;
    }
}
