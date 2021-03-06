package edu.wpi.off.by.one.errors.code.controller.menupanes;

import java.io.IOException;
import edu.wpi.off.by.one.errors.code.controller.customcontrols.ClearableTextField;
import edu.wpi.off.by.one.errors.code.controller.ControllerSingleton;
import edu.wpi.off.by.one.errors.code.model.Id;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import edu.wpi.off.by.one.errors.code.model.Node;
import edu.wpi.off.by.one.errors.code.model.TagMap;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * Created by jules on 11/28/2015.
 */
public class DirectionsMenuPane extends BorderPane {
	@FXML private ClearableTextField originTextField;
    @FXML private ClearableTextField destinationTextField;
	@FXML Button routeButton;
    @FXML private ListView<String> directionsListView;
    @FXML CheckBox accessibleCheckbox;
    @FXML ComboBox carrierChoiceBox;
	@FXML Button foodButton;
	@FXML Button mensRoomButton;
	@FXML Button womensRoomButton;
	@FXML Button genderNeutralRestroomButton;

    private SettingsMenuPane settingsMenuPane;
    Node originNode;
    Node destinationNode;
	
    public DirectionsMenuPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/off/by/one/errors/code/view/menupanes/DirectionsMenuPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
			this.getStylesheets().add(getClass().getResource("/edu/wpi/off/by/one/errors/code/resources/stylesheets/menupanes/DirectionsPaneStyleSheet.css").toExternalForm());
            setListeners();
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
        settingsMenuPane = ControllerSingleton.getInstance().getSettingsMenuPane();
		originTextField.setTagsSet(TagMap.getTagMap().getNames());
		destinationTextField.setTagsSet(TagMap.getTagMap().getNames());

    }

	private void setListeners(){ 
		this.routeButton.setOnAction(e -> {
			setDirectionsFromNode();
			setDirectionsToNode();
			ControllerSingleton.getInstance().getMapRootPane().placeStartMarker(originNode);
			ControllerSingleton.getInstance().getMapRootPane().drawPath(originNode.getId(), destinationNode.getId());
			ControllerSingleton.getInstance().getNavigationPane().start(ControllerSingleton.getInstance().getMapRootPane().getPath().getSteps());
			for(int i = 0; i < ControllerSingleton.getInstance().getMapRootPane().getPath().getSteps().size(); i++){
				System.out.println(ControllerSingleton.getInstance().getMapRootPane().getPath().getSteps().get(i).toString());
			}
			ControllerSingleton.getInstance().getNavigationPane().nodezoomcheck(true);
		});
		this.foodButton.setOnAction(e-> {
			//ControllerSingleton.getInstance().getMapRootPane().placeMarker(originNode);
			ControllerSingleton.getInstance().getMapRootPane().drawFoodPath();
		});
		this.mensRoomButton.setOnAction(e-> {
			//ControllerSingleton.getInstance().getMapRootPane().placeMarker(originNode);
			ControllerSingleton.getInstance().getMapRootPane().drawMensRoomPath();
		});
		this.womensRoomButton.setOnAction(e-> {
			//ControllerSingleton.getInstance().getMapRootPane().placeMarker(originNode);
			ControllerSingleton.getInstance().getMapRootPane().drawWomensRoomPath();
		});
		this.genderNeutralRestroomButton.setOnAction(e-> {
			//ControllerSingleton.getInstance().getMapRootPane().placeMarker(originNode);
			ControllerSingleton.getInstance().getMapRootPane().drawGenderNeutralRestroomPath();
		});
	}
	
	@FXML private void selectAccessible(){
		ControllerSingleton.getInstance().getMapRootPane().isAccessibleMode = accessibleCheckbox.isSelected() ? true : false;
		System.out.println("Accessibility: " + ControllerSingleton.getInstance().getMapRootPane().isAccessibleMode);
	}
	
	public Node getDirectionsToNode() { return null; }
	public Node getDirectionsFromNode() { return null; }
	
	public void setDirectionsToNode() {
		Id toId = TagMap.getTagMap().findName(destinationTextField.getText());
		destinationNode = ControllerSingleton.getInstance().getMapRootPane().getDisplay().getGraph().returnNodeById(toId);
	}
	
	public void setDirectionsFromNode(){
		Id fromId = TagMap.getTagMap().findName(originTextField.getText());
		originNode = ControllerSingleton.getInstance().getMapRootPane().getDisplay().getGraph().returnNodeById(fromId);
	}
	
	public void setDirectionsToNode(Node n){
		destinationTextField.setText(n.getName());
		destinationNode = n;
		//TODO Should take an input (String? NodeDisplay? Node?)
		//Put String name in the To box
		//set directionsTo variable (NOT MADE YET)
	}
	
	public void setDirectionsFromNode(Node n){
		originTextField.setText(n.getName());
		originNode = n;
		//TODO Should take an input (String? NodeDisplay? Node?)
		//Put String name in the From box
		//set directionsFrom variable (NOT MADE YET)
	}

    public ListView<String> getdirectionsListView(){
        return this.directionsListView;
    }

    @FXML private void onSwitchDirectionsButtonClick(){
        String originContent = originTextField.getText();
        originTextField.setText(destinationTextField.getText());
        destinationTextField.setText(originContent);
        Node buffer = originNode;
        originNode = destinationNode;
        destinationNode = buffer;
    }
    
    @FXML private void onEmailButtonClick(){
		ControllerSingleton.getInstance().getMainPane().showEmailPane(true);
        /*settingsMenuPane = ControllerSingleton.getInstance().getSettingsMenuPane();
        String userEmail = settingsMenuPane.getUserEmail();
        List<String> directions = ControllerSingleton.getInstance().getMapRootPane().getPath().getTextual();
        String userName = settingsMenuPane.getUserName();
        String body = "Hello "+ userName + "!\n";
        for (String s : directions){
            body += (s + "\n");
        }
        GoogleMail googleMail = new GoogleMail();
        googleMail.send(userEmail, "Directions from goatThere()", body);*/
    }
    
    @FXML private void onSMSButtonClick(){
		ControllerSingleton.getInstance().getMainPane().showSMSPane(true);

    }


}
