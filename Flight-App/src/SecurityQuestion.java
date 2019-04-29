import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SecurityQuestion {
	
	static GridPane layout; 
	static HBox buttonMenu;

	public static void display(String title, String message, String username) {
		
		Stage window = new Stage(); 

		window.initModality(Modality.APPLICATION_MODAL); 
		window.setTitle(title); 
			
		Label label = new Label(); 
		label.setText(message);
		TextField answer = new TextField(); 
		
		Button submitAnswer = new Button("Submit"); 
		submitAnswer.setOnAction(e -> {
			
			String databaseURL = "jdbc:mysql://localhost:3306/myFlightApp?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String databaseUsername = "root";
			String databasePassword = "1234abcd";
			
			try {
				
				if (answer.getText() != null) {
					
					Connection myConn = DriverManager.getConnection(databaseURL,databaseUsername, databasePassword);
					String sql = "Select `answer` from createAccount where `username` = ?";
					PreparedStatement myStmt = myConn.prepareStatement(sql); 
					myStmt.setString(1, username);
					ResultSet rs = myStmt.executeQuery();
					rs.next(); 
					String correctAnswer = rs.getString("answer"); 
					if (answer.getText().equals(correctAnswer)) {
						
						window.close();
						DisplayPassword.displayPassword(username);
						
					}
					
				}
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				
			}
		});
			
		layout = new GridPane(); 
		layout.setPadding(new Insets(50,30,15,30));
		layout.setVgap(30);
		layout.setHgap(30);
		layout.setAlignment(Pos.BASELINE_CENTER);
		GridPane.setConstraints(label, 0, 0);
		GridPane.setConstraints(answer, 1, 0);
		layout.getChildren().addAll(label, answer); 
		
		buttonMenu = new HBox(15); 
		buttonMenu.getChildren().add(submitAnswer); 
		buttonMenu.setAlignment(Pos.BASELINE_CENTER);
		buttonMenu.setPadding(new Insets(30,30,50,30));
		
		BorderPane borderPane = new BorderPane(); 
		borderPane.setCenter(layout);
		borderPane.setBottom(buttonMenu);
		
		Scene scene = new Scene(borderPane, 500, 200); 
		window.setScene(scene);
		window.show();
		
	}

}
