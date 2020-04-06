package pobj.pinboard.editor;



import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolLine;
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
		MenuItem line = new MenuItem("Ligne");
		MenuItem box = new MenuItem("Rectangle");
		MenuItem ellipse = new MenuItem("Ellipse");
		MenuItem img = new MenuItem("Image");
		tools.getItems().addAll(line,box,ellipse,img);
		close.setOnAction(e -> stage.close());
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file,edit,tools);
		//ToolBar
		Button bLine = new Button("Ligne");
		Button bBox = new Button("Rectangle");
		Button bEllipse = new Button("Ellipse");
		Button bImg = new Button("Image");
		ToolBar toolBar = new ToolBar();
		toolBar.getItems().add(bLine);
		toolBar.getItems().add(new Separator());
		toolBar.getItems().add(bBox);
		toolBar.getItems().add(new Separator());
		toolBar.getItems().add(bEllipse);
		toolBar.getItems().add(new Separator());
		toolBar.getItems().add(bImg);
		tool = new ToolLine();
		Label label = new Label("Filled Line tool");
		line.setOnAction(e->{tool = new ToolLine(); label.setText("Filled line tool");});
		box.setOnAction(e->{tool = new ToolRect(); label.setText("Filled box tool");});
		ellipse.setOnAction(e->{tool = new ToolEllipse();label.setText("Filled ellipse tool");});
		bLine.setOnAction(e->{tool = new ToolLine(); label.setText("Filled line tool");});
		bBox.setOnAction(e->{tool = new ToolRect(); label.setText("Filled box tool");});
		bEllipse.setOnAction(e->{tool = new ToolEllipse();label.setText("Filled ellipse tool");});
		img.setOnAction(e->{FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("Open Resource File");
						fileChooser.getExtensionFilters().addAll(
						new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
						new ExtensionFilter("All Files", "*.*"));
						tool = new ToolImage(fileChooser.showOpenDialog(stage));label.setText("Filled image tool");});
		bImg.setOnAction(e->{FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.tif"),
					new ExtensionFilter("All Files", "*.*"));
			tool = new ToolImage(fileChooser.showOpenDialog(stage));label.setText("Filled image tool");});
		VBox vBox = new VBox(menuBar,toolBar);
		//Canvas
		canvas = new Canvas(800,600);
		vBox.getChildren().add(canvas);
		stage.setScene(new Scene(vBox));
		canvas.setOnMousePressed(e -> {tool.press(this, e); draw(); tool.drawFeedback(this, canvas.getGraphicsContext2D());});
		canvas.setOnMouseDragged(e -> {tool.drag(this, e); draw();tool.drawFeedback(this, canvas.getGraphicsContext2D());});
		canvas.setOnMouseReleased(e-> {tool.release(this, e); draw();});

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
		board.draw(canvas.getGraphicsContext2D());
	}
}
