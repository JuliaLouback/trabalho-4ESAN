package main.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Principal extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/resources/view/CadastroCliente.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Clientes - Uni Cliente");
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.getIcons().add(new Image("/resources/img/customer.png"));

			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
