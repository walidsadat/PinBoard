package pobj.pinboard.editor;



import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolLine;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;

public class EditorWindow implements EditorInterface, ClipboardListener {
	private Board board;
	private Canvas canvas;
	private Tool tool;
	private Selection selection = new Selection();
	MenuItem paste = new MenuItem("Paste");
	
	public EditorWindow(Stage stage) {
		Clipboard.getInstance().addListener(this);
		board = new Board();
		stage.setTitle("PinBoard - <untitled>");
		//MenuBar
		Menu file = new Menu("File");
		MenuItem new_ = new MenuItem("New");
		new_.setOnAction(e -> {new EditorWindow(new Stage());draw();});
		MenuItem close = new MenuItem("Close");
		file.getItems().addAll(new_,close);
		close.setOnAction(e ->{Clipboard.getInstance().removeListener(this);stage.close();});
		Menu edit = new Menu("Edit");
		MenuItem copy = new MenuItem("Copy");
		copy.setOnAction(e->{Clipboard.getInstance().copyToClipboard(selection.getContents());draw();});
		if(Clipboard.getInstance().isEmpty())
			paste.setDisable(true);
		paste.setOnAction(e->{board.addClip(Clipboard.getInstance().copyFromClipboard());draw();});
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(e->{board.removeClip(selection.getContents());draw();});
		edit.getItems().addAll(copy,paste,delete);
		Menu tools = new Menu("Tools");
		MenuItem line = new MenuItem("Line");
		MenuItem box = new MenuItem("Rectangle");
		MenuItem ellipse = new MenuItem("Ellipse");
		MenuItem img = new MenuItem("Image");
		MenuItem select = new MenuItem("Selection");
		tools.getItems().addAll(line,box,ellipse,img,select);
		close.setOnAction(e -> stage.close());
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file,edit,tools);
		//ToolBar
		Button bLine = new Button("Line");
		Button bBox = new Button("Rectangle");
		Button bEllipse = new Button("Ellipse");
		Button bImg = new Button("Image");
		Button bSelect = new Button("Select");
		ToolBar toolBar = new ToolBar();
		//Palette de couleur
		ColorPicker colorPicker = new ColorPicker(Color.BLACK);
		toolBar.getItems().addAll(bLine,new Separator(),bBox,new Separator(),bEllipse,new Separator(),bImg,new Separator(),bSelect,new Separator(),colorPicker);
		tool = new ToolLine(colorPicker.getValue());
		colorPicker.setOnAction(e->{if(!selection.getContents().isEmpty()) {
										for(Clip c:selection.getContents())
											c.setColor(colorPicker.getValue());
									draw();
									}
									if(tool instanceof ToolLine)
										tool = new ToolLine(colorPicker.getValue());
									else if(tool instanceof ToolRect)
										tool = new ToolRect(colorPicker.getValue());
									else if(tool instanceof ToolEllipse)
										tool = new ToolEllipse(colorPicker.getValue());});
		//Label
		Label label = new Label("Filled Line tool");
		line.setOnAction(e->{tool = new ToolLine(colorPicker.getValue()); label.setText("Filled line tool");});
		box.setOnAction(e->{tool = new ToolRect(colorPicker.getValue()); label.setText("Filled box tool");});
		ellipse.setOnAction(e->{tool = new ToolEllipse(colorPicker.getValue());label.setText("Filled ellipse tool");});
		select.setOnAction(e->{tool = new ToolSelection(); label.setText("Filled select tool");});
		bLine.setOnAction(e->{tool = new ToolLine(colorPicker.getValue()); label.setText("Filled line tool");});
		bBox.setOnAction(e->{tool = new ToolRect(colorPicker.getValue()); label.setText("Filled box tool");});
		bEllipse.setOnAction(e->{tool = new ToolEllipse(colorPicker.getValue());label.setText("Filled ellipse tool");});
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
		bSelect.setOnAction(e->{tool = new ToolSelection(); label.setText("Filled select tool");});
		VBox vBox = new VBox(menuBar,toolBar);
		//Canvas
		canvas = new Canvas(800,600);
		vBox.getChildren().add(canvas);
		stage.setScene(new Scene(vBox));
		canvas.setOnMousePressed(e -> {tool.press(this, e); draw(); tool.drawFeedback(this, canvas.getGraphicsContext2D());});
		canvas.setOnMouseDragged(e -> {tool.drag(this, e); draw();tool.drawFeedback(this, canvas.getGraphicsContext2D());});
		canvas.setOnMouseReleased(e-> {tool.release(this, e); draw();});

		
		vBox.getChildren().addAll(new Separator(),label);
		stage.show();
	}
	@Override
	public Board getBoard() {
		return board;
	}
	@Override
	public Selection getSelection() {
		return selection;
	}
	@Override
	public CommandStack getUndoStack() {
		return null;
	}
	
	public void draw() {
		board.draw(canvas.getGraphicsContext2D());
	}
	
	@Override
	public void clipboardChanged() {
		if(!Clipboard.getInstance().isEmpty())
			paste.setDisable(false);
	}
}
