import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DisplayPassword {
	
	public static void displayPassword(String username) {
		
		Stage window = new Stage(); 
		Label yourPassword, password; 
		Button login; 
		GridPane layout; 
		HBox buttonMenu; 
		
		String databaseURL = "jdbc:mysql://localhost:3306/myFlightApp?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String databaseUsername = "root";
		String databasePassword = "1234abcd";
		
		try {
			
			Connection myConn = DriverManager.getConnection(databaseURL,databaseUsername, databasePassword);
			String sql = "Select `pass` from createAccount where `username` = ?";
			PreparedStatement myStmt = myConn.prepareStatement(sql); 
			myStmt.setString(1, username);
			ResultSet rs = myStmt.executeQuery();
			rs.next(); 
			String pass = rs.getString("pass"); 
			yourPassword = new Label("Your password is: ");
			password = new Label(pass); 
			login = new Button("Log In"); 
			layout = new GridPane(); 
			buttonMenu = new HBox(15, login); 
			GridPane.setConstraints(yourPassword, 0, 0); 
			GridPane.setConstraints(password, 1, 0); 
			layout.getChildren().addAll(yourPassword, password);
			layout.setAlignment(Pos.BASELINE_CENTER);
			layout.setPadding(new Insets(50,30,15,30));
			layout.setVgap(30);
			layout.setHgap(30);
			
			buttonMenu.setAlignment(Pos.BASELINE_CENTER);
			buttonMenu.setPadding(new Insets(30,30,50,30));
			
			BorderPane borderPane = new BorderPane(); 
			borderPane.setCenter(layout);
			borderPane.setBottom(buttonMenu);
			
			Scene scene = new Scene(borderPane, 500, 200); 
			window.setScene(scene);
			window.show();
			
			login.setOnAction(e -> {
				
				LoginWindow lw = new LoginWindow(); 
				Stage loginStage = new Stage(); 
				try {
					
					lw.start(loginStage);
					window.close();
					loginStage.show();
					
				} catch (Exception e1) {
					
					e1.printStackTrace();
					
				} 
			});
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}

	}
	
}
