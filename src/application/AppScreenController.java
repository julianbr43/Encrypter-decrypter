package application;

import java.io.File;

import javax.crypto.SecretKey;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppScreenController {
	
	@FXML
	private TextField password;
	@FXML
	private Label path;
	private File file;
	private SecretKey sk;
	
	
	public void initialize() {
		
	}
	
	/**
	 * Method to select a file from the computer
	 */
	public void choosefile() {
		
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		
		if (selectedFile != null) {
			file = selectedFile.getAbsoluteFile();
			path.setText(selectedFile.getName());
		}else {
			alertFile();
		}
	}
	
	public void encrypter() {
		
		if(file!=null) {
			if(!password.getText().equals("")) {
				sk = Main.getmodel().getKey(password.getText());
				Main.getmodel().encryptFile(file, sk);
				alertSuccess();
				password.clear();
			}else {
				alertPassword();
			}
		}else {
			alertFile();
		}

	}
	
	public void decrypter() {
		
		if(file!=null) {
			if(!password.getText().equals("")) {
				sk = Main.getmodel().getKey(password.getText());
				Main.getmodel().decryptFile(file, sk);
				validation();
			}else {
				alertPassword();
			}
		}else {
			alertFile();
		}

	}
	
	public void alertFile() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Choose file");
		alert.setHeaderText(null);
		alert.setContentText("No file has been selected");
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();
	}
	
	public void alertPassword() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Password");
		alert.setHeaderText(null);
		alert.setContentText("Enter the password for the file");
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();
	}
	
	public void validation() {
		boolean isShaOk = Main.getmodel().validateSHA();
		if (isShaOk) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Successful validation");
			alert.setHeaderText(null);
			alert.setContentText("The SHA-1 of the encrypted and decrypted file match");
			alert.initStyle(StageStyle.UTILITY);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Failed validation");
			alert.setHeaderText(null);
			alert.setContentText("The SHA-1 of the encrypted and decrypted file DOES NOT match");
			alert.initStyle(StageStyle.UTILITY);
			alert.showAndWait();
		}
	}
	
	public void alertSuccess() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText("Encryption successful. The file with the SHA-1 hash has been created");
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();
	}
	

}
