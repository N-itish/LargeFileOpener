package com.nitish.UserInterface;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import com.nitish.FileEnum.Status;
import com.nitish.FileInteface.FileOpener;
import com.nitish.MainProgram.TextOpener;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.DragEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.input.TransferMode;
import javafx.scene.input.Dragboard;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

@SuppressWarnings("restriction")
public class UserInterface extends Application{
	private Scene fileInputScene,FileViewScene;
	private Dimension screenSize;
	private Label fileDrop;
	private Button addFile;
	private Button readFile;
	private TextField filePath;
	private FileChooser fileChooser;
	private GridPane gridPane,fileViewGrid;
	private TextArea DataArea;
	private Button next;
	private Button previous;
	private FileOpener fileReader;
	
	
	public UserInterface() {
		screenSize   	= Toolkit.getDefaultToolkit().getScreenSize();
		fileChooser 	= new FileChooser();
		fileDrop	    = new Label("Drop File Here!!!");
		addFile     	= new Button("Choose");
		readFile 		= new Button("readFile");
		next     		= new Button("next");
	    previous 		= new Button("previous");
		filePath 	 	= new TextField();
		DataArea 	 	= new TextArea();
		fileViewGrid 	= new GridPane();
		gridPane 	 	= new GridPane();		
		//Setting the height and width of the textArea for displaying data
		DataArea.setPrefHeight(screenSize.getHeight()-200);
	    DataArea.setPrefWidth(screenSize.getWidth()-200);
		 
	}
	@Override
	public void start(final Stage primaryStage) throws Exception {
		//--Setting up the first scene--
		setFileInputLayout();
		//--Setting up the second scene-- 
		setFileViewLayout();
		fileInputScene = new Scene(gridPane);
		FileViewScene = new Scene(fileViewGrid);
		primaryStage.setScene(fileInputScene);
		primaryStage.show();
		//All the event handlers for the first scene are placed below
	    
	    readFile.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				//Setting NEW STAGE for the file to view
				primaryStage.setScene(FileViewScene);
				//Placing the scene in middle of the screen
				primaryStage.setFullScreen(true);
				primaryStage.centerOnScreen();
				primaryStage.show();
				fileReadHandler(filePath.getText().toString(),Status.BEGINING);
				//filePath.getText().toString()
				
			}
		});
	    addFile.setOnAction(new EventHandler<ActionEvent>() {
			   public void handle(ActionEvent event) {
				 File file = fileChooser.showOpenDialog(primaryStage);
	               filePath.setText(file.getAbsolutePath());
			}
		});
	    
	    
	    
	    fileInputScene.setOnDragOver(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                if (event.getGestureSource() != fileInputScene
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

	    fileInputScene.setOnDragDropped(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                	for(File F : db.getFiles())
                	{
                		//Setting NEW STAGE for the file to view
                		primaryStage.setScene(FileViewScene);
                		primaryStage.centerOnScreen();
                		primaryStage.setFullScreen(true);
                		primaryStage.show();
                		fileReadHandler(F.getAbsolutePath().toString(),Status.BEGINING);
                		
                	}
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();
            }
        });
	    //All the event handlers for the second scene are placed below
	    
	    //In the two event handlers below
	    //Default is a dummy string that has single use
		//Its job is to fulfill the methods parameter
	    next.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				fileReadHandler("Default", Status.NEXT);
			}
		});
	    previous.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				fileReadHandler("Default", Status.PREVIOUS);
			}
		});
	}
	
	private void setFileInputLayout() {
		gridPane.setMinSize(800, 500);  
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
        gridPane.setAlignment(Pos.CENTER); 
        gridPane.add(filePath, 0, 0);
        gridPane.add(addFile, 1, 0);
        gridPane.add(readFile, 2, 0);
        gridPane.add(fileDrop, 0, 1);
        		
	}
	private void setFileViewLayout() {
		fileViewGrid.setMinSize(screenSize.getWidth(), screenSize.getHeight());  
	    fileViewGrid.setVgap(5); 
	    fileViewGrid.setHgap(5);       
	    fileViewGrid.add(DataArea, 0, 0);
	    fileViewGrid.add(previous,0, 1);
	    fileViewGrid.add(next, 1, 1);
     }
	//Handles the calls for file openeing
	private void fileReadHandler(String filePath,Status status) {
		if(fileReader ==  null)
		{
			fileReader = new TextOpener(filePath);
		}
		DataArea.setText(fileReader.ReadFile(status));
	}
}
