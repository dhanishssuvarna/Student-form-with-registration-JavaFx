package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StudentForm extends Application {
	// Scenes
	Scene scene1, scene2, scene3;
	//Layouts
	GridPane login,form,accepted;
	ScrollPane sp;
	
	// create a alert
    Alert a = new Alert(AlertType.NONE);
	
	// LoginPage variables
	Label studLogin;
	
	Label userIdLabel;
	Label passLabel;
	
	TextField userIdField;
	TextField passField;
	PasswordField passHideField;
	CheckBox show;
	
	Button LoginButton;
	
	// FormPage Variables
	boolean Calflag=false;
	
	Label enterDetails;
	
	Label nameLabel;
	Label emailLabel;
	Label phoneLabel;
	Label usnLabel;
	Label genderLabel;
	Label branchLabel;
	Label subLabel;
	Label DOBLabel;
	Label yearOfStudyLabel;
	
	TextField nameField;
	TextField emailField;
	TextField phoneFiled;
	TextField usnField;
	
	RadioButton male;
	RadioButton female;
	
	ToggleGroup tg;
	
	DatePicker dp;
	
	ObservableList<String> Branch = FXCollections.observableArrayList(
				"Information Science",
				"Computer Science",
				"Electronic and Communication",
				"Electrical and Electronics",
				"Civil",
				"Mechanical",
				"BioTechnology"
			);
	ComboBox<String> branchCombo;
	
	ObservableList<String> yr = FXCollections.observableArrayList(
			"First",
			"Second",
			"Third",
			"Forth"
		);
	ComboBox<String> yrCombo;
	
	Label Java;
	Label DAA;
	Label DC;
	Label DSD;
	Label Maths;
	
	TextField javaMark;
	TextField DAAMark;
	TextField DCMark;
	TextField DSDMark;
	TextField MathsMark;
	
	Button calcuButton;
	
	Label totalMark;
	Label perMark;
	
	CheckBox approve;
	
	Button submit;
	
	// Accepted Page
	Label ThankLabel;
	Label submitedText;
	
	InputStream imgStream;
	Image img;
	ImageView iv;
	
	Button LogoutButton;
	
	@Override
	public void start(Stage primaryStage) {
		
		//Scene 1
		LoginPage();
		
		//Scene 2
		FormPage();
		
		//Scene 3
		AcceptedPage();
		        
		// Styles and positions
		styles();
		
		// Event Handling    
		LoginButton.setOnAction(e ->{
			if(!((userIdField.getText()!="") && (passField.getText()!=""))) {
				a.setAlertType(AlertType.ERROR);
				a.setContentText("Invalid Credentials");
				a.show();
				return;
			}
			primaryStage.setScene(scene2);
		});
		
		calcuButton.setOnAction(e -> {
			int total = CalValidation();
			if(total!=-1) {
				totalMark.setText("Total Marks : "+total);
				perMark.setText("Percentage : "+((double)total/5)+" %");
			}
		});
		
		submit.setOnAction(e -> {
			boolean valid = FormValidation();
			
			if(valid) {
				try {
					writeToDB();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				primaryStage.setScene(scene3);
			}
		});
		
		LogoutButton.setOnAction(e -> {
			clearField();
			primaryStage.setScene(scene1);
		});
		
		// Initialize Field For Demo
		setField();
		
		primaryStage.getIcons().add(new Image("/icon.png"));
		primaryStage.setTitle("  Student Form");
		primaryStage.setScene(scene1);
		primaryStage.show();
		
	}
	
	private void LoginPage() {
		studLogin = new Label("Student Login");
		
		userIdLabel = new Label("User ID : ");
		userIdField = new TextField();
		
		passLabel = new Label("Password : ");
		passField = new TextField();
		passHideField = new PasswordField();
		show = new CheckBox("Show Password");
		
		// Set initial state
		passField.setManaged(false);
		passField.setVisible(false);
		
		// Defines whether or not this node's layout will be managed by it's parent
		passField.managedProperty().bind(show.selectedProperty());
		passField.visibleProperty().bind(show.selectedProperty());
	    passHideField.managedProperty().bind(show.selectedProperty().not());
	    passHideField.visibleProperty().bind(show.selectedProperty().not());

	    // Bind the textField and passwordField text values bi_directionally.
	    passField.textProperty().bindBidirectional(passHideField.textProperty());
		
		LoginButton = new Button("Login");
		
		login = new GridPane();
		
		login.add(studLogin, 0, 0,2,2);
		login.add(userIdLabel,0,3);
		login.add(userIdField,1,3);
		login.add(passLabel,0,4);
		login.add(passField,1,4);
		login.add(passHideField, 1, 4);
		login.add(show, 1, 5);
		login.add(LoginButton, 0, 6, 2, 2);
		
		scene1= new Scene(login, 800, 500);
	}
	
	private void FormPage() {
		enterDetails = new Label("Enter Details");
		
		nameLabel = new Label("Full Name : ");
		nameField = new TextField();
		
		emailLabel = new Label("Email ID : ");
		emailField = new TextField();
		
		phoneLabel = new Label("Phone Number : ");
		phoneFiled = new TextField();
		
		usnLabel = new Label("USN : ");
		usnField = new TextField();
		
		DOBLabel = new Label("DOB : ");
		dp = new DatePicker();
		
		genderLabel = new Label("Gender : ");
		male = new RadioButton("Male");
		female = new RadioButton("Female");
		tg = new ToggleGroup();
		male.setToggleGroup(tg);
		female.setToggleGroup(tg);
		
		branchLabel = new Label("Branch : ");
		branchCombo = new ComboBox<String>(Branch);
		branchCombo.setValue("Information Science");
		
		yearOfStudyLabel = new Label("Year : ");
		yrCombo = new ComboBox<String>(yr);
		yrCombo.setValue("First");
		
		subLabel = new Label("Enter mark in 100 : ");
		Java = new Label("Java : ");
		javaMark = new TextField();
		DAA = new Label("DAA : ");
		DAAMark = new TextField();
		DC = new Label("DC : ");
		DCMark = new TextField();
		DSD = new Label("DSD : ");
		DSDMark = new TextField();
		Maths = new Label("Maths : ");
		MathsMark = new TextField();
		
		calcuButton = new Button("Calculate");
		
		totalMark = new Label("Total Marks : ");
		perMark = new Label("Percentage : ");
		
		approve = new CheckBox("Above entered marks are valid");
		
		submit = new Button("Submit");
			
		form= new GridPane();
		
		form.add(enterDetails, 0, 0,3,2);
		form.add(nameLabel, 0, 3);
		form.add(nameField, 1, 3, 2, 1);
		form.add(emailLabel, 0, 4);
		form.add(emailField, 1, 4, 2, 1);
		form.add(phoneLabel, 0, 5);
		form.add(phoneFiled, 1, 5, 2, 1);
		form.add(usnLabel, 0, 6);
		form.add(usnField, 1, 6, 2, 1);
		form.add(DOBLabel, 0, 7);
		form.add(dp, 1, 7, 2, 1);
		form.add(genderLabel, 0, 8);
		form.add(male, 1, 8);
		form.add(female, 2, 8);
		form.add(branchLabel, 0, 9);
		form.add(branchCombo, 1, 9);
		form.add(yearOfStudyLabel, 0, 10);
		form.add(yrCombo, 1, 10);
		form.add(subLabel, 0, 11);
		form.add(Java, 0, 12);
		form.add(javaMark, 1, 12);
		form.add(DAA, 0, 13);
		form.add(DAAMark, 1, 13);
		form.add(DC, 0, 14);
		form.add(DCMark, 1, 14);
		form.add(DSD, 0, 15);
		form.add(DSDMark, 1, 15);
		form.add(Maths, 0, 16);
		form.add(MathsMark, 1, 16);
		form.add(totalMark, 0, 17, 2, 1);
		form.add(perMark, 0, 18, 2, 1);
		form.add(calcuButton, 2, 17, 1, 2);
		form.add(approve, 0, 19);
		form.add(submit, 0, 20,3,3);
		
		sp = new ScrollPane();
		sp.setContent(form);
		sp.setPannable(true);
		sp.setFitToWidth(true);
	    sp.setFitToHeight(true);
		
		scene2= new Scene(sp,800,500);
	}
	
	private void AcceptedPage() {
		ThankLabel = new Label("Thank You");
		try {
			imgStream = new FileInputStream("D:/My_Folder/Java Eclipse/JavaFx Project/StudentForm/src/success-green-check-mark.png");
		} catch (FileNotFoundException e) {}
		
		Image img = new Image(imgStream);
		ImageView iv = new ImageView();
		iv.setFitHeight(60);
		iv.setFitWidth(60);
		iv.setImage(img);
		
		submitedText = new Label("Form has been Successfully Submitted");
		LogoutButton = new Button("LogOut");
		
		accepted = new GridPane();
		
		accepted.add(ThankLabel, 0, 0, 3, 1);
		accepted.add(iv, 0, 1);
		accepted.add(submitedText, 1, 1);
		accepted.add(LogoutButton, 0, 2, 2, 2);
		
		scene3= new Scene(accepted, 800, 500);
	}
	
	private void styles() {
		RadialGradient gradient1 = new RadialGradient(
				0,
	            .1,  
	            100,
	            100,
	            500,
	            false,  
	            CycleMethod.REFLECT,
	            new Stop(0, Color.web("#c078f6")),
	            new Stop(1, Color.web("#e9d2fe"))
				);
		
		// create a background fill
	    BackgroundFill Background_fill = new BackgroundFill(gradient1, CornerRadii.EMPTY, Insets.EMPTY);
	    
	    // create Background
	    Background primaryColor = new Background(Background_fill);
		
		// Login Style
		login.setAlignment(Pos.CENTER);
		login.setHgap(15);
		login.setVgap(15);
		
		studLogin.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 30));
		userIdLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		passLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		
		GridPane.setHalignment(studLogin, HPos.CENTER);
		GridPane.setValignment(studLogin, VPos.CENTER);
		
		GridPane.setHalignment(LoginButton, HPos.CENTER);
		GridPane.setValignment(LoginButton, VPos.CENTER);
		
		// Form Style
		form.setAlignment(Pos.CENTER);
		form.setHgap(15);
		form.setVgap(15);
		
		enterDetails.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 30));
		
		GridPane.setHalignment(enterDetails, HPos.CENTER);
		GridPane.setValignment(enterDetails, VPos.CENTER);
		
		GridPane.setHalignment(calcuButton, HPos.CENTER);
		GridPane.setValignment(calcuButton, VPos.CENTER);
		
		GridPane.setHalignment(totalMark, HPos.CENTER);
		totalMark.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 15));
		
		GridPane.setHalignment(perMark, HPos.CENTER);
		perMark.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 15));
		
		GridPane.setHalignment(submit, HPos.CENTER);
		GridPane.setValignment(submit, VPos.CENTER);
		
		GridPane.setHalignment(Java, HPos.CENTER);
		GridPane.setHalignment(javaMark, HPos.CENTER);
		GridPane.setHalignment(DAA, HPos.CENTER);
		GridPane.setHalignment(DAAMark, HPos.CENTER);
		GridPane.setHalignment(DC, HPos.CENTER);
		GridPane.setHalignment(DCMark, HPos.CENTER);
		GridPane.setHalignment(DSD, HPos.CENTER);
		GridPane.setHalignment(DSDMark, HPos.CENTER);
		GridPane.setHalignment(Maths, HPos.CENTER);
		GridPane.setHalignment(MathsMark, HPos.CENTER);
		
		nameLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		emailLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		phoneLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		usnLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		DOBLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		genderLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		branchLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		yearOfStudyLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		subLabel.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		approve.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,14));
		
		Java.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,12));
		DC.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,12));
		DAA.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,12));
		DSD.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,12));
		Maths.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,12));
		
		// Accepted Style
		accepted.setAlignment(Pos.CENTER);
		accepted.setHgap(15);
		accepted.setVgap(15);
		
		GridPane.setHalignment(ThankLabel, HPos.CENTER);
		GridPane.setValignment(ThankLabel, VPos.CENTER);
		
		ThankLabel.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 60));
		submitedText.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 25));
		
		GridPane.setHalignment(LogoutButton, HPos.CENTER);
		GridPane.setValignment(LogoutButton, VPos.CENTER);
		
		login.setBackground(primaryColor);
		form.setBackground(primaryColor);
		accepted.setBackground(primaryColor);
		
		String BtnProp = "-fx-base: #c078f6; -fx-padding: 8px; -fx-text-fill: white; -fx-background-radius: 15px; -fx-border-color: #BC80E9; -fx-border-radius: 15px;";
		
		LoginButton.setStyle(BtnProp);
		LoginButton.setPrefWidth(80);
		calcuButton.setStyle(BtnProp);
		calcuButton.setPrefWidth(80);
		submit.setStyle(BtnProp);
		submit.setPrefWidth(80);
		LogoutButton.setStyle(BtnProp);
		LogoutButton.setPrefWidth(80);
		
		String FieldProp = "-fx-border-color: #000; -fx-padding: 5px;";
		userIdField.setStyle(FieldProp);
		passField.setStyle(FieldProp);
		passHideField.setStyle(FieldProp);
		nameField.setStyle(FieldProp);
		emailField.setStyle(FieldProp);
		phoneFiled.setStyle(FieldProp);
		usnField.setStyle(FieldProp);
		javaMark.setStyle(FieldProp);
		DAAMark.setStyle(FieldProp);
		DCMark.setStyle(FieldProp);
		DSDMark.setStyle(FieldProp);
		MathsMark.setStyle(FieldProp);
		
		form.setPadding(new Insets(20, 20, 20, 50));
		
	}
	
	private int CalValidation() {
		
		if(!(javaMark.getText()!="" && DAAMark.getText()!="" && DCMark.getText()!="" && DSDMark.getText()!="" && MathsMark.getText()!="")) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Enter the valid Marks");
			a.show();
			Calflag=false;
			return -1;
		}
		
		int total=0;
		try {
			total+= Integer.parseInt(javaMark.getText());
			total+= Integer.parseInt(DAAMark.getText());
			total+= Integer.parseInt(DCMark.getText());
			total+= Integer.parseInt(DSDMark.getText());
			total+= Integer.parseInt(MathsMark.getText());
		}catch (Exception ex) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Enter the valid Marks");
			a.show();
			Calflag=false;
			return -1;
		}
		
		if(total>500) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Enter the valid Marks");
			a.show();
			Calflag=false;
			return -1;
		}
		
		Calflag=true;
		return total;
	}
	
	private boolean FormValidation() {
		if(nameField.getText()=="") {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Enter a valid name");
			a.show();
			return false;
		}
		
		if(!isEmailValid(emailField.getText())) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Enter a valid email ID");
			a.show();
			return false;
		}
		
		if(!isPhoneValid(phoneFiled.getText())) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Enter a valid phone No.");
			a.show();
			return false;
		}
		
		if(usnField.getText()=="") {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Enter a valid USN");
			a.show();
			return false;
		}
		
		if(dp.getValue()==null) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Enter a valid DOB");
			a.show();
			return false;
		}
		
		if(male.isSelected()==false && female.isSelected()==false) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Select a Gender");
			a.show();
			return false;
		}
		
		if(Calflag==false) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Calculate Total and Percentage to Proceed");
			a.show();
			return false;
		}
		
		if(!(approve.isSelected())) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Please Check the Confirmation Box to Proceed");
			a.show();
			return false;
		}
		return true;
	}
	
	private boolean isEmailValid(String email) {
		if (email == null)
            return false;
		
		// ^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+ )*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2, 7} 
		
		// ^		Matches the beginning of the line.
		// $		Matches the end of the line.
		// X+		X occurs once or more times
		// X*		X occurs zero or more times
		// X{y,z}	X occurs at least y times but less than z times
		// (?: X)	Groups regular expressions without remembering the matched text
		// [a-zA-Z]	Any value from a to z or A through Z
		// .		Any character
		// \\.		Escaping the Special meaning of .
        String emailRegex = "^[a-zA-Z0-9_+&*-]+" +
                            "(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+" +
                            "[a-zA-Z]{2,7}$";
          
        // The compile(String) method of the Pattern class 
        // in Java is used to create a pattern from 
        // the regular expression passed as parameter to method.
        Pattern pattern = Pattern.compile(emailRegex);
        
        // check whether pattern and email matches
        // and returns boolean i.e true or false
        return pattern.matcher(email).matches();
    }
	
	private boolean isPhoneValid(String phone) {
		if (phone == null)
            return false;
	     
	    // The given argument to compile() method
	    // is regular expression. With the help of
	    // regular expression we can validate mobile
	    // number.
	    // 1) Begins with 0 or 91
	    // 2) Then contains 7 or 8 or 9 as first digit of a phone number.
	    // 3) Then contains any 9 digits from 0-9
		String phoneRegex="(0|91)?[7-9][0-9]{9}";
		
	    Pattern pattern = Pattern.compile(phoneRegex);
	    return pattern.matcher(phone).matches();
	}
	
	private void writeToDB() throws Exception{
		File DB = new File("C:\\Users\\DHANISH S SUVARNA\\OneDrive\\Desktop\\DB.txt");
		
		FileWriter out = new FileWriter(DB);
		String s="--------Student Login Credentials--------\n"+
				userIdLabel.getText()+userIdField.getText()+"\n"+
				passLabel.getText()+passField.getText()+"\n\n"+
				"---------Student Form Details---------\n"+
				nameLabel.getText()+nameField.getText()+"\n"+
				emailLabel.getText()+emailField.getText()+"\n"+
				phoneLabel.getText()+phoneFiled.getText()+"\n"+
				usnLabel.getText()+usnField.getText()+"\n"+
				DOBLabel.getText()+dp.getValue()+"\n"+
				genderLabel.getText()+(male.isSelected()?male.getText():female.getText())+"\n"+
				branchLabel.getText()+branchCombo.getValue()+"\n"+
				yearOfStudyLabel.getText()+yrCombo.getValue()+"\n\n"+
				"All Subject Marks : \n"+
				"\t"+Java.getText()+javaMark.getText()+"\n"+
				"\t"+DAA.getText()+DAAMark.getText()+"\n"+
				"\t"+DC.getText()+DCMark.getText()+"\n"+
				"\t"+DSD.getText()+DSDMark.getText()+"\n"+
				"\t"+Maths.getText()+MathsMark.getText()+"\n\n"+
				totalMark.getText()+"\n"+
				perMark.getText()+"\n";
		
		out.write(s);
		out.close();
	}
	
	private void clearField() {
		userIdField.setText("");
		passField.setText("");
		show.setSelected(false);
		nameField.setText("");
		emailField.setText("");
		phoneFiled.setText("");
		usnField.setText("");
		dp.setValue(null);
		male.setSelected(false);
		female.setSelected(false);
		branchCombo.setValue("Information Science");
		yrCombo.setValue("First");
		javaMark.setText("");
		DCMark.setText("");
		DAAMark.setText("");
		DSDMark.setText("");
		MathsMark.setText("");
		totalMark.setText("Total Marks : ");
		perMark.setText("Percentage : ");
		approve.setSelected(false);
	}
	
	private void setField() {
		nameField.setText("Dhanish S Suvarna");
		emailField.setText("dhanishssuvarna123@gmail.com");
		phoneFiled.setText("8073445263");
		usnField.setText("4NM19IS050");
		javaMark.setText("100");
		DCMark.setText("99");
		DAAMark.setText("100");
		DSDMark.setText("98");
		MathsMark.setText("100");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
