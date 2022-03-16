package application;
	
import java.util.EmptyStackException;
import java.util.Scanner;

import analyzer.Computer;
import analyzer.Parser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.close();
			
			Scanner scanner = new Scanner(System.in);
			String request = "";
			
			while(!request.equals("stop")) {
				request = scanner.nextLine();
				Parser parser = new Parser(request);
				Computer computer = new Computer(parser.parse());
				try {
					System.out.println(":: RESULT -> " + computer.compute());
				} catch(EmptyStackException emp) {
					System.err.println("Error : missing token");
					emp.printStackTrace();
				} catch (IllegalArgumentException ill) {
					System.err.println(ill.getMessage());
				}
			}
			scanner.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
