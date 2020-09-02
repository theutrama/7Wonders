package application;
	
import controller.SevenWondersController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.SampleViewController;
import view.menu.MainMenuViewController;


public class Main extends Application {
	
	public static Stage primaryStage;

	private static SevenWondersController swController;
	
	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;

		try {
			MainMenuViewController mainMenuViewController = new MainMenuViewController();
			Scene scene = new Scene(mainMenuViewController,1000,800);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setFullScreen(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SevenWondersController getSWController() {
		return swController;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
