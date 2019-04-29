import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ForgotPassword extends Application {
	
	Scene scene; 
	Button submit; 
	TextField usernameInput; 
	Label username; 

	public static void main(String[] args) {
		
		launch(args); 

	}

	@Override
	public void start(Stage window) throws Exception {
		
		window.setTitle("Forgot Password?");
		username = new Label("Please enter your username: "); 
		usernameInput = new TextField(); 
		submit = new Button("Submit"); 
		
		GridPane layout = new GridPane(); 
		layout.setPadding(new Insets(50,30,15,30));
		layout.setVgap(30);
		layout.setHgap(30);
		layout.setAlignment(Pos.BASELINE_CENTER);
		GridPane.setConstraints(username, 0, 0);
		GridPane.setConstraints(usernameInput, 1, 0);
		layout.getChildren().addAll(username, usernameInput); 
		
		HBox buttonMenu = new HBox(15); 
		buttonMenu.getChildren().add(submit); 
		buttonMenu.setAlignment(Pos.BASELINE_CENTER);
		buttonMenu.setPadding(new Insets(30,30,50,30));
		
		BorderPane borderPane = new BorderPane(); 
		borderPane.setCenter(layout);
		borderPane.setBottom(buttonMenu);
		
		scene = new Scene(borderPane, 500, 200); 
		window.setScene(scene);
		window.show();
		
		submit.setOnAction(e -> {
			
			String databaseURL = "jdbc:mysql://localhost:3306/myFlightApp?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String databaseUsername = "root";
			String databasePassword = "1234abcd";
			
			try {
				
				if (usernameInput.getText() != null) {
					
					Connection myConn = DriverManager.getConnection(databaseURL,databaseUsername, databasePassword);
					String sql = "Select `question` from createAccount where `username` = ?";
					PreparedStatement myStmt = myConn.prepareStatement(sql); 
					myStmt.setString(1,  usernameInput.getText());
					ResultSet rs = myStmt.executeQuery(); 
					
					if(rs.next()) {
						
						String securityQuestion = rs.getString("question");
						window.close();
						SecurityQuestion.display("Security Question",securityQuestion,usernameInput.getText()); 
						
					}
				}
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				
			}
		});
	}
}
