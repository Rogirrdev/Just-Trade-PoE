package cl.rabarca.main;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	private static final String RUTA_INVITE_IMG = "resources/img/new-user.png";
	private static final String RUTA_TRADE_IMG = "resources/img/reload-arrows.png";
	private static final String RUTA_QUIT_IMG = "resources/img/remove-user.png";
	private static final String RUTA_MESSAGE_IMG = "resources/img/text-messages.png";

	@Override
	public void start(Stage primaryStage) throws Exception {
		final TabPane tabPane = new TabPane();
        final Tab tab1 = crearTab(tabPane.getTabs().size());
        tabPane.getTabs().add(tab1);
        final Tab tab2 = crearTab(tabPane.getTabs().size());
        tabPane.getTabs().add(tab2);
        
        Button btnOpciones = new Button("O");
		btnOpciones.setTooltip(new Tooltip("Options"));
		
		Button btnMinimizar = new Button("-");
		btnMinimizar.setTooltip(new Tooltip("Minimize"));
		
		BorderPane bPane = new BorderPane();  
//        BPane.setTop(new Label("This will be at the top"));  
		bPane.setLeft(btnOpciones);  
		bPane.setCenter(new Label("Just Tradea"));  
		bPane.setRight(btnMinimizar);  
//		bPane.getRight().set
//        BPane.setBottom(new Label("This will be at the bottom"));  
		
        final HBox hBoxMenu = new HBox(bPane);
        hBoxMenu.setId("hBoxMenu");
//        hBoxMenu.setAlignment(Pos.CENTER);
		final Insets padding = new Insets(5, 5, 5, 5);
		hBoxMenu.setPadding(padding);
		hBoxMenu.setSpacing(5);
        
        final VBox vBoxTab = new VBox(hBoxMenu, tabPane);
        final Scene tabScene = new Scene(vBoxTab);
        
        tabScene.getStylesheets().add("resources/css/poe.css");  
		primaryStage.setScene(tabScene);
		primaryStage.resizableProperty().setValue(Boolean.FALSE);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("Just Trade");
		primaryStage.setAlwaysOnTop(true);
		// primaryStage.setOpacity(0.8d);
		primaryStage.setX(0);
		primaryStage.setY(0);

		
		primaryStage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				System.out.println("Minimizado:" + newValue.booleanValue());

			}
		});

		primaryStage.show();
		
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = bounds.getMinX() + (bounds.getWidth() - tabScene.getWidth());
		primaryStage.setX(x);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private static Button crearBotonAcccion(final String rutaImagen, final String tooltipText) {
		Button btnAccion = null;
		try (final FileInputStream input = new FileInputStream(rutaImagen)) {
            final Image image = new Image(input);
            final ImageView imgView = new ImageView(image);
            btnAccion = new Button(null, imgView);
    		final Tooltip toolAccion = new Tooltip(tooltipText);
    		btnAccion.setTooltip(toolAccion);
    		
    		return btnAccion;
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		return null;
	}
	
	private static Tab crearTab(int numero) {
		final Button btnInviteParty = crearBotonAcccion(RUTA_INVITE_IMG, "Invite to party");
		final Button btnTrade = crearBotonAcccion(RUTA_TRADE_IMG, "Trade");
		final Button btnQuitParty = crearBotonAcccion(RUTA_QUIT_IMG, "Quit party");
		final Button btnMessage = crearBotonAcccion(RUTA_MESSAGE_IMG, "Message to buyer");
		final HBox hBox = new HBox(btnInviteParty, btnTrade, btnQuitParty, btnMessage);
		final Insets padding = new Insets(5, 5, 5, 5);
		hBox.setPadding(padding);
		hBox.setSpacing(5);
		
		final VBox vBox = new VBox(new Label("<Name> wants to buy your"), new Label("<Quantity> <Name> for"), new Label("<Quantity> <Currency>"), hBox);
		
        final Tab tab1 = new Tab(String.valueOf(numero+1), vBox);
        tab1.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                System.out.println("Closed!");
            }
        });
        
        return tab1;
	}
	
	class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");

            closeBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });

            this.getChildren().add(closeBtn);
        }
    }
}