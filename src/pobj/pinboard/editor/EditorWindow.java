package pobj.pinboard.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolRect;

public class EditorWindow implements EditorInterface {
	private Board board;
	private Canvas canvas;
	private Tool tool;
	
	public EditorWindow(Stage stage) {
		board = new Board();
		stage.setTitle("PinBoard - <untitled>");
		//MenuBar
		Menu file = new Menu("File");
		MenuItem new_ = new MenuItem("New");
		new_.setOnAction(e -> new EditorWindow(new Stage()));
		MenuItem close = new MenuItem("Close");
		file.getItems().addAll(new_,close);
		close.setOnAction(e -> stage.close());
		Menu edit = new Menu("Edit");
		Menu tools = new Menu("Tools");
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file,edit,tools);
		//ToolBar
		Button box = new Button("Box");
		Button ellipse = new Button("Ellipse");
		Button img = new Button("Img");
		ToolBar toolBar = new ToolBar();
		toolBar.getItems().add(box);
		toolBar.getItems().add(new Separator());
		toolBar.getItems().add(ellipse);
		toolBar.getItems().add(new Separator());
		toolBar.getItems().add(img);
		VBox vBox = new VBox(menuBar,toolBar);
		//Canvas
		canvas = new Canvas(800,600);
		vBox.getChildren().add(canvas);
		stage.setScene(new Scene(vBox));
		tool = new ToolRect();
		canvas.setOnMousePressed(e -> {tool.press(this, e); draw();});
		draw();
		canvas.setOnMouseDragged(e -> {tool.drag(this, e); draw();});
		draw();
		canvas.setOnMouseReleased(e-> {tool.release(this, e); draw();});

		Label label = new Label("LABEL");
		Separator separator = new Separator();
		vBox.getChildren().addAll(separator,label);
		stage.show();
	}
	@Override
	public Board getBoard() {
		return board;
	}
	@Override
	public Selection getSelection() {
		return null;
	}
	@Override
	public CommandStack getUndoStack() {
		return null;
	}
	
	public void draw() {
		tool.drawFeedback(this, canvas.getGraphicsContext2D());
	}
}
