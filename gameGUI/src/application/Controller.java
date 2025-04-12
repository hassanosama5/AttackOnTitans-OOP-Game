package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimerTask;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    
    
    
    @FXML
    private Button minimizeButton;
    @FXML
    private Button exitButton;
    @FXML
    private TitledPane detailsTitledPane;
    @FXML
    private Button letsBeginbtn;
    @FXML
    private Button easy;
    @FXML
    private Button hard; 
    @FXML
    private Button passTurnBtn; 
    @FXML
    private Button sniperCannonBtn; 
    @FXML
    private Button piercingBtn;
    @FXML
    private Button volleyBtn;
    @FXML
    private Button wallTrapBtn;
    @FXML
    private Button nextbtn;
    @FXML
    private ComboBox<String> comboBox;
    
    private String[] lanesEasy = {"Lane 1","Lane 2","Lane 3"};
    private String[] lanesHard = {"Lane 1","Lane 2","Lane 3","Lane 4","Lane 5"};
    @FXML
    private Label score;
    @FXML
    private Label turn;
    @FXML
    private Label phase; 
    @FXML
    private Label resources;
    @FXML
    private Label lanedngrlvl1;
    @FXML
    private Label lanedngrlvl2;
    @FXML
    private Label lanedngrlvl3;
    @FXML
    private Label lanedngrlvl4;
    @FXML
    private Label lanedngrlvl5;
    @FXML
    private Label lanehealth1;
    @FXML
    private Label lanehealth2;
    @FXML
    private Label lanehealth3;
    @FXML
    private Label lanehealth4;
    @FXML
    private Label lanehealth5;
    
    @FXML
    private AnchorPane lane1lostAnchor;
    @FXML
    private AnchorPane lane2lostAnchor;
    @FXML
    private AnchorPane lane3lostAnchor;
    @FXML
    private AnchorPane lane4lostAnchor;
    @FXML
    private AnchorPane lane5lostAnchor;
    

    private static int iResourcesPerLane;
    private static int iNumOfLanes;
    private static String nextScene;
    private static Battle mainBattle;
    private static String currentMode;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing Controller");
        
        if (comboBox != null) {
            
            if(currentMode == "easy") {
            comboBox.getItems().addAll(lanesEasy);
            }
            if(currentMode == "hard") {
                comboBox.getItems().addAll(lanesHard);
                }
            
            comboBox.setOnAction(event -> handleComboBoxSelection());
        } 
        
        exitButton.setOnAction(event -> {
            // Get the stage from the button
            Stage stage = (Stage) exitButton.getScene().getWindow();
            // Close the application
            stage.close();
        });
        
        if(detailsTitledPane!=null) {
        detailsTitledPane.setExpanded(false);
        }
        
        
    }
    
    @FXML
    private void handleMinimizeButtonAction() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }
    
    
	
    private String selectedItem;
    
    private void handleComboBoxSelection() {
    	selectedItem = comboBox.getSelectionModel().getSelectedItem();
        System.out.println("Selected item: " + selectedItem);
        
    }
    
    
    public void gotoGame(ActionEvent e) throws IOException {
        mainBattle = new Battle(1, 0, 1330, iNumOfLanes, iResourcesPerLane);  
        loadScene(e,nextScene);
        setvisualLanes();
        //System.out.println(""+lanewithleastdng());
    }

    public void goBack(ActionEvent e) {
        try {
            loadScene(e, "pickmodeScene.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void pickMode(ActionEvent e) throws IOException {
        try {
            if (e.getSource() == easy) {
            	currentMode = "easy";
                iResourcesPerLane = 250;
                iNumOfLanes = 3;
                nextScene = "scene2.fxml";
                
                
                
            } else if (e.getSource() == hard) {
            	currentMode = "hard";
                iResourcesPerLane = 125;
                iNumOfLanes = 5;
                nextScene = "scene3.fxml";
                
            }
            loadScene(e, "instructionsScene.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void letsBegin(ActionEvent event) throws IOException {
        loadScene(event, "pickmodeScene.fxml");
    }

    //////////////////////////////////
    
    public void passturnActionBtn(ActionEvent event) {
        mainBattle.passTurn();
        updateBattleDetails();

        addTitansToAnchors();
        //updateTitans();
        
        checklost();
        
    }

    /////////////////////////////////
    
    private static Lane lane1;
    private static Lane lane2;
    private static Lane lane3;
    private static Lane lane4;
    private static Lane lane5;
        
    public void setvisualLanes() {
    	ArrayList<Lane> tmp = new ArrayList<>();
		
		while(!mainBattle.getLanes().isEmpty())
		{
			Lane l = mainBattle.getLanes().poll();
			tmp.add(l);
		}
		mainBattle.getLanes().addAll(tmp); /////IMPORTANT////////////
		
		if(currentMode == "easy") {
			lane1 = tmp.get(0);
			lane2 = tmp.get(1);
			lane3 = tmp.get(2);
		}
		if(currentMode == "hard") {
			lane1 = tmp.get(0);
			lane2 = tmp.get(1);
			lane3 = tmp.get(2);
			lane4 = tmp.get(3);
			lane5 = tmp.get(4);
	
		}
  
		
    }
    
    public void checklost() {
    	if(lane1.isLaneLost()) {
    		lane1lostAnchor.setVisible(true);
    	}
    	if(lane2.isLaneLost()) {
    		lane2lostAnchor.setVisible(true);
    	}
    	if(lane3.isLaneLost()) {
    		lane3lostAnchor.setVisible(true);
    	}
    	if(lane4!=null && lane4.isLaneLost()) {
    		lane4lostAnchor.setVisible(true);
    	}
    	if(lane5!=null && lane5.isLaneLost()) {
    		lane5lostAnchor.setVisible(true);
    	}
    	
    }
    
    
    public void updateBattleDetails() {
    	
        if (score != null && turn != null && phase != null && resources != null) {
            score.setText("Score: " + mainBattle.getScore());
            turn.setText("Turn: " + mainBattle.getNumberOfTurns());
            phase.setText("Phase: " + mainBattle.getBattlePhase());
            resources.setText("Resources: " + mainBattle.getResourcesGathered());
        }
        
		
        if(currentMode == "easy") {
        	lanedngrlvl1.setText("Danger Level: "+ lane1.getDangerLevel());
        	lanedngrlvl2.setText("Danger Level: "+ lane2.getDangerLevel());
        	lanedngrlvl3.setText("Danger Level: "+ lane3.getDangerLevel());
        	lanehealth1.setText("Wall Health: " + lane1.getLaneWall().getCurrentHealth());
        	lanehealth2.setText("Wall Health: " + lane2.getLaneWall().getCurrentHealth());
        	lanehealth3.setText("Wall Health: " + lane3.getLaneWall().getCurrentHealth());
        }
        if(currentMode == "hard") {
        	
    		
        	lanedngrlvl1.setText("Danger Level: "+ lane1.getDangerLevel());
        	lanedngrlvl2.setText("Danger Level: "+ lane2.getDangerLevel());
        	lanedngrlvl3.setText("Danger Level: "+ lane3.getDangerLevel());
        	lanedngrlvl4.setText("Danger Level: "+ lane4.getDangerLevel());
        	lanedngrlvl5.setText("Danger Level: "+ lane5.getDangerLevel());
        	lanehealth1.setText("Wall Health: " + lane1.getLaneWall().getCurrentHealth());
        	lanehealth2.setText("Wall Health: " + lane2.getLaneWall().getCurrentHealth());
        	lanehealth3.setText("Wall Health: " + lane3.getLaneWall().getCurrentHealth());
        	lanehealth4.setText("Wall Health: " + lane4.getLaneWall().getCurrentHealth());
        	lanehealth5.setText("Wall Health: " + lane5.getLaneWall().getCurrentHealth());
        }
               
        
         
    }
    

    
    ////load the scene that I want
    private void loadScene(ActionEvent e, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene); 
        stage.show();
    }
    
  
    //helper method thats gets tha Lane when want to add the weapon to depending on which comboBox
    //item was selected
    private static Lane laneToBeAddedTo;
    
    public Lane getLaneToBeAddedTo() {
	ArrayList<Lane> tmp = new ArrayList<>();
	
	while(!mainBattle.getLanes().isEmpty())
	{
		Lane l = mainBattle.getLanes().poll();
		tmp.add(l);
	}
	mainBattle.getLanes().addAll(tmp);
	/////////
	if(iNumOfLanes==3) {
		if(selectedItem=="Lane 1") {
		laneToBeAddedTo = tmp.get(0);
		}
		if(selectedItem=="Lane 2") {
		laneToBeAddedTo = tmp.get(1);
		}
		if(selectedItem=="Lane 3") {
		laneToBeAddedTo = tmp.get(2);
		}
			}
	if(iNumOfLanes==5) {
		if(selectedItem=="Lane 1") {
			laneToBeAddedTo = tmp.get(0);
		}
		if(selectedItem=="Lane 2") {
			laneToBeAddedTo = tmp.get(1);
		}
		if(selectedItem=="Lane 3") {
			laneToBeAddedTo = tmp.get(2);
		}
		if(selectedItem=="Lane 4") {
			laneToBeAddedTo = tmp.get(3);
		}
		if(selectedItem=="Lane 5") {
			laneToBeAddedTo = tmp.get(4);
		}
			}
	
		return laneToBeAddedTo;
    }
    
    @FXML
    private AnchorPane lane1anchor;
	@FXML
    private AnchorPane lane2anchor;
	@FXML
    private AnchorPane lane3anchor;
	@FXML
    private AnchorPane lane4anchor;
	@FXML
    private AnchorPane lane5anchor;

    private static AnchorPane laneAnchor;//ToBeAddedTo
    
    
    
    public AnchorPane getSpecificAnchorPane() {
    	if(iNumOfLanes==3) {
    		if(selectedItem=="Lane 1") {
    		laneAnchor = lane1anchor;
    		}
    		if(selectedItem=="Lane 2") {
    		laneAnchor = lane2anchor;
    		}
    		if(selectedItem=="Lane 3") {
    		laneAnchor = lane3anchor;
    		}
    			}
    	if(iNumOfLanes==5) {
    		if(selectedItem=="Lane 1") {
    			laneAnchor = lane1anchor;
    		}
    		if(selectedItem=="Lane 2") {
    			laneAnchor = lane2anchor;
    		}
    		if(selectedItem=="Lane 3") {
    			laneAnchor = lane3anchor;
    		}
    		if(selectedItem=="Lane 4") {
    			laneAnchor = lane4anchor;
    		}
    		if(selectedItem=="Lane 5") {
    			laneAnchor = lane5anchor;
    		}
    	
    	}
		return laneAnchor;
		
    }
    
	public static int numOfwpnsinLane1 =0  ;
	public static int numOfwpnsinLane2 =0;
	public static int numOfwpnsinLane3 =0;
	public static int numOfwpnsinLane4 =0;
	public static int numOfwpnsinLane5 =0;
	
	public void incrementNumOfwpn() {
			if(selectedItem=="Lane 1") {
			numOfwpnsinLane1++;
			}
			if(selectedItem=="Lane 2") {
			numOfwpnsinLane2++;
			}
			if(selectedItem=="Lane 3") {
			numOfwpnsinLane3++;
			}
			if(selectedItem=="Lane 4") {
			numOfwpnsinLane4++;
			}
			if(selectedItem=="Lane 5") {
			numOfwpnsinLane5++;
			}	
	}
	
	@FXML
	private Label warningMaxNum;
	
	public void buyWeaponAction(ActionEvent e) throws InsufficientResourcesException, InvalidLaneException {

		if(e.getSource()==sniperCannonBtn) {
			if(selectedItem=="Lane 1" && numOfwpnsinLane1<8 ||selectedItem=="Lane 2" && numOfwpnsinLane2<8
					||selectedItem=="Lane 3" && numOfwpnsinLane3<8||selectedItem=="Lane 4" && numOfwpnsinLane4<8
					||selectedItem=="Lane 5" && numOfwpnsinLane5<8) {
			mainBattle.purchaseWeapon(2,getLaneToBeAddedTo());
			System.out.println("sniperCannon added");
			incrementNumOfwpn();
			//numOfwpnsinLane1++;
			addwpnImgToAnchor(getSpecificAnchorPane(),2);
			}
			else {
				warningMaxNum.setVisible(true);
			}
		}
		if(e.getSource()==piercingBtn) {
			if(selectedItem=="Lane 1" && numOfwpnsinLane1<8 ||selectedItem=="Lane 2" && numOfwpnsinLane2<8
					||selectedItem=="Lane 3" && numOfwpnsinLane3<8||selectedItem=="Lane 4" && numOfwpnsinLane4<8
					||selectedItem=="Lane 5" && numOfwpnsinLane5<8) {
			mainBattle.purchaseWeapon(1,getLaneToBeAddedTo());
			System.out.println("piercingCannon added");
			incrementNumOfwpn();
			addwpnImgToAnchor(getSpecificAnchorPane(),1);
			
			}
			else {
				warningMaxNum.setVisible(true);
			}
		}
		if(e.getSource()==volleyBtn) {
			if(selectedItem=="Lane 1" && numOfwpnsinLane1<8 ||selectedItem=="Lane 2" && numOfwpnsinLane2<8
					||selectedItem=="Lane 3" && numOfwpnsinLane3<8||selectedItem=="Lane 4" && numOfwpnsinLane4<8
					||selectedItem=="Lane 5" && numOfwpnsinLane5<8) {
			mainBattle.purchaseWeapon(3,getLaneToBeAddedTo());
			System.out.println("volley added");
			incrementNumOfwpn();
			addwpnImgToAnchor(getSpecificAnchorPane(),3);
			
			}
			else {
				warningMaxNum.setVisible(true);
			}
		}
		if(e.getSource()==wallTrapBtn) {
			if(selectedItem=="Lane 1" && numOfwpnsinLane1<8 ||selectedItem=="Lane 2" && numOfwpnsinLane2<8
					||selectedItem=="Lane 3" && numOfwpnsinLane3<8||selectedItem=="Lane 4" && numOfwpnsinLane4<8
					||selectedItem=="Lane 5" && numOfwpnsinLane5<8) {
			mainBattle.purchaseWeapon(4,getLaneToBeAddedTo());
			System.out.println("wallTrap added");
			incrementNumOfwpn();
			addwpnImgToAnchor(getSpecificAnchorPane(),4);
			
			
			}
			else {
				warningMaxNum.setVisible(true);
			}
		}
		
	}
	
	
	
	
	
	///add weapon Image to anchorPane
	public void addwpnImgToAnchor(AnchorPane ap, int weaponCode) {
	    String imagePath = null;
	    
	    // Determine the image path based on the weaponCode
	    switch (weaponCode) {
	        case 1:
	            imagePath = "piercingCannonSMALL.png";
	            break;
	        case 2:
	            imagePath = "sniperCannonSMALL.png";
	            break;
	        case 4:
	            imagePath = "TRapSMALL.png";
	            break;
	        case 3:
	            imagePath = "volleyGunSmall.png";
	            break;
	        default:
	            System.out.println("Invalid weaponCode: " + weaponCode);
	            return;
	    }
	        ImageView imageView = new ImageView(imagePath);
	        
	       
	        
	        if(selectedItem =="Lane 1") {
	        ///positioning Criteria
	        int xpos = 1240;
	        int yPos = numOfwpnsinLane1 % 2 == 0 && numOfwpnsinLane1 != 0 ? 60 : 0;
	        switch(numOfwpnsinLane1) {
	        case 3,4: xpos=1150;
	        break;
	        case 5,6: xpos=1050;
	        break;
	        case 7,8: xpos=950;
	        break;
	        }
	        
	        imageView.setLayoutX(xpos);
	        imageView.setLayoutY(yPos);
	        }
	        if(selectedItem =="Lane 2") {
		        ///positioning Criteria
		        int xpos = 1240;
		        int yPos = numOfwpnsinLane2 % 2 == 0 && numOfwpnsinLane2 != 0 ? 65 : 0;
		        switch(numOfwpnsinLane2) {
		        case 3,4: xpos=1150;
		        break;
		        case 5,6: xpos=1050;
		        break;
		        case 7,8: xpos=950;
		        break;
		        }
		        
		        imageView.setLayoutX(xpos);
		        imageView.setLayoutY(yPos);
		        }
	        if(selectedItem =="Lane 3") {
		        
		        int xpos = 1240;
		        int yPos = numOfwpnsinLane3 % 2 == 0 && numOfwpnsinLane3 != 0 ? 65 : 0;
		        switch(numOfwpnsinLane3) {
		        case 3,4: xpos=1150;
		        break;
		        case 5,6: xpos=1050;
		        break;
		        case 7,8: xpos=950;
		        break;
		        }
		        
		        imageView.setLayoutX(xpos);
		        imageView.setLayoutY(yPos);
		        }
	        if(selectedItem =="Lane 4") {
		        ///positioning Criteria
		        int xpos = 1240;
		        int yPos = numOfwpnsinLane4 % 2 == 0 && numOfwpnsinLane4 != 0 ? 65 : 0;
		        switch(numOfwpnsinLane4) {
		        case 3,4: xpos=1150;
		        break;
		        case 5,6: xpos=1050;
		        break;
		        case 7,8: xpos=950;
		        break;
		        }
	
		        imageView.setLayoutX(xpos);
		        imageView.setLayoutY(yPos);
		        }
	        if(selectedItem =="Lane 5") {
		        ///positioning Criteria
		        int xpos = 1240;
		        int yPos = numOfwpnsinLane5 % 2 == 0 && numOfwpnsinLane5 != 0 ? 65 : 0;
		        switch(numOfwpnsinLane5) {
		        case 3,4: xpos=1150;
		        break;
		        case 5,6: xpos=1050;
		        break;
		        case 7,8: xpos=950;
		        break;
		        }
		        
		        imageView.setLayoutX(xpos);
		        imageView.setLayoutY(yPos);
		        }
	       
	        
	        // Add the ImageView to the AnchorPane
	        ap.getChildren().add(imageView);
	    
	}

	///////////////////////////////
	
	private Map<Titan, ImageView> titanImageViewMap = new HashMap<>();

    private String getImagePathForTitan(Titan t) {
        if (t instanceof PureTitan) {
            return "PureTitan small.png";
        } else if (t instanceof AbnormalTitan) {
            return "AbnormalTitan.png";
        } else if (t instanceof ArmoredTitan) {
            return "ArmoredTitan small.png";
        } else if (t instanceof ColossalTitan) {
            return "ColossalTitan 150.png";
        }
        return "";
    }

    public void addTitansToAnchors() {
        ArrayList<Lane> lanes = mainBattle.getOriginalLanes();
        
        for (Lane lane : lanes) {
            AnchorPane anchorPane = getAnchorPaneForLane(lane);
            
            // Safeguard against null AnchorPane
            if (anchorPane != null) {
                for (Titan t : lane.getTitans()) {
                    ImageView iv = titanImageViewMap.get(t);
                    AnchorPane ap = new AnchorPane();
                    
                    if (iv == null) {
                        String imgpath = getImagePathForTitan(t);
                        iv = new ImageView(imgpath);
                        titanImageViewMap.put(t, iv);
                        
                        Label lbl = new Label(t.getCurrentHealth() + "");
                        lbl.setLayoutX(60);
                        lbl.setStyle("-fx-font-weight: bold;");
                        
                        ap.getChildren().addAll(iv, lbl);
                        anchorPane.getChildren().add(ap);
                    } else {
                        ap = (AnchorPane) iv.getParent();
                    }
                    
                    // Update position
                    int d = t.getDistance();
                    ap.setLayoutX(1320 - d);
                }
            }
        }
    }

    private AnchorPane getAnchorPaneForLane(Lane lane) {
        if (lane.equals(lane1)) {
            return lane1anchor;
        } else if (lane.equals(lane2)) {
            return lane2anchor;
        } else if (lane.equals(lane3)) {
            return lane3anchor;
        } else if (lane.equals(lane4)) {
            return lane4anchor;
        } else if (lane.equals(lane5)) {
            return lane5anchor;
        }
        return null;
    }
	
	
	
	
	
		
}
