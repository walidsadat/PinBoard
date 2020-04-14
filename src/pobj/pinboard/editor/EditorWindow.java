package pobj.pinboard.editor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolLine;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;

public class EditorWindow implements EditorInterface, ClipboardListener {
	private Board board;
	private Canvas canvas;
	private Selection selection;
	private CommandStack stack;
	private MenuItem paste;
	private Tool tool;
	
	public EditorWindow(Stage stage) {
		Clipboard.getInstance().addListener(this);
		board = new Board();
		selection = new Selection();
		stack = new CommandStack();
		stage.setTitle("PinBoard - <untitled>");
		
		//Label
		Label label = new Label("Line tool");
		
		//ColorPicker
		ColorPicker colorPicker = new ColorPicker(Color.BLACK);
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
		tool = new ToolLine(colorPicker.getValue());
		
		//MenuBar
			//Menu File
				//Ouverture nouvelle fenêtre
				MenuItem new_ = new MenuItem("New");
				new_.setOnAction(e -> {new EditorWindow(new Stage());draw();});
				//Fermeture fenêtre courante
				MenuItem close = new MenuItem("Close");
				close.setOnAction(e ->{Clipboard.getInstance().removeListener(this);stage.close();});
				//Lecture depuis un fichier
				MenuItem open = new MenuItem("Open");
				open.setOnAction(e ->{try {
										FileInputStream file = new FileInputStream((new FileChooser()).showOpenDialog(stage));
										ObjectInputStream obj = new ObjectInputStream(file);
										board = (Board) obj.readObject();
										obj.close();
										draw();
									} catch (Exception e1) {
										e1.printStackTrace();
									}
									});
				//Sauvegarde dans un fichier
				MenuItem save = new MenuItem("Save");
				save.setOnAction(e ->{
								try {
									FileOutputStream file = new FileOutputStream((new FileChooser()).showSaveDialog(stage));
									ObjectOutputStream obj = new ObjectOutputStream(file);
									obj.writeObject(board);
									obj.close();
									draw();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							});
			Menu file = new Menu("File");
			file.getItems().addAll(new_,close,open,save);
			//Menu Edit
				//Copie d'élements
				MenuItem copy = new MenuItem("Copy");
				copy.setOnAction(e->{Clipboard.getInstance().copyToClipboard(selection.getContents());draw();});
				//Collage d'elements
				paste = new MenuItem("Paste");
				if(Clipboard.getInstance().isEmpty())
					paste.setDisable(true);
				paste.setOnAction(e->{CommandAdd cmd = new CommandAdd(this,Clipboard.getInstance().copyFromClipboard());
									cmd.execute();
									stack.addCommand(cmd);
									draw();});
				//Suppression d'elements
				MenuItem delete = new MenuItem("Delete");
				delete.setOnAction(e->{CommandAdd cmd = new CommandAdd(this,selection.getContents()); 
								cmd.undo(); stack.addCommand(cmd);
								draw();});
				//Group
				MenuItem group = new MenuItem("Group");
				/*group.setOnAction(e->{
					(new CommandAdd(this,selection.getContents())).undo();
					ClipGroup g = new ClipGroup();
					for(Clip c: selection.getContents())
						g.addClip(c);
					(new CommandAdd(this,g)).execute();
					draw();
				});*/
				group.setOnAction(e->{
					CommandGroup cmd = new CommandGroup(this,selection.getContents());
					cmd.execute(); stack.addCommand(cmd);
					draw();});
				
				//Ungroup
				MenuItem ungroup = new MenuItem("Ungroup");
				/*ungroup.setOnAction(e->{
					for(Clip c: selection.getContents())
						if(c instanceof ClipGroup) {
							board.addClip(((ClipGroup) c).getClips());
							board.removeClip(c);
						}
						draw();
				});*/
				
				ungroup.setOnAction(e->{
					CommandUngroup cmd;
					for(Clip c : selection.getContents())
						if(c instanceof ClipGroup) {
							cmd = new CommandUngroup(this,(ClipGroup) c);
							cmd.execute();
							stack.addCommand(cmd);
						}
					draw();});
				//Undo
				MenuItem undo = new MenuItem("Undo");
				undo.setOnAction(e->{stack.undo();draw();});
				//Redo
				MenuItem redo = new MenuItem("Redo");
				redo.setOnAction(e->{stack.redo();draw();});
			Menu edit = new Menu("Edit");
			edit.getItems().addAll(copy,paste,delete,group,ungroup,undo,redo);
			//Menu Tools
				//Ligne
				MenuItem line = new MenuItem("Line");
				line.setOnAction(e->{tool = new ToolLine(colorPicker.getValue()); label.setText("Line tool");});
				//Rectangle
				MenuItem box = new MenuItem("Rectangle");
				box.setOnAction(e->{tool = new ToolRect(colorPicker.getValue()); label.setText("Filled box tool");});
				//Ellipse
				MenuItem ellipse = new MenuItem("Ellipse");
				ellipse.setOnAction(e->{tool = new ToolEllipse(colorPicker.getValue());label.setText("Filled ellipse tool");});
				//Image
				MenuItem img = new MenuItem("Image");
				img.setOnAction(e->{FileChooser fileChooser = new FileChooser();
									fileChooser.setTitle("Open Resource File");
									fileChooser.getExtensionFilters().addAll(
											new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
											new ExtensionFilter("All Files", "*.*"));
									tool = new ToolImage(fileChooser.showOpenDialog(stage));label.setText("Image tool");});
				//Selection
				MenuItem select = new MenuItem("Selection");
				select.setOnAction(e->{tool = new ToolSelection(); label.setText("Selection tool");});
			Menu tools = new Menu("Tools");
			tools.getItems().addAll(line,box,ellipse,img,select);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file,edit,tools);
		
		//ToolBar
			//Ligne
			Button bLine = new Button("Line");
			bLine.setOnAction(e->{tool = new ToolLine(colorPicker.getValue()); label.setText("Line tool");});
			//Rectangle
			Button bBox = new Button("Rectangle");
			bBox.setOnAction(e->{tool = new ToolRect(colorPicker.getValue()); label.setText("Filled box tool");});
			//Ellipse
			Button bEllipse = new Button("Ellipse");
			bEllipse.setOnAction(e->{tool = new ToolEllipse(colorPicker.getValue());label.setText("Filled ellipse tool");});
			//Image
			Button bImg = new Button("Image");
			bImg.setOnAction(e->{FileChooser fileChooser = new FileChooser();
								fileChooser.setTitle("Open Resource File");
								fileChooser.getExtensionFilters().addAll(
										new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
										new ExtensionFilter("All Files", "*.*"));
								tool = new ToolImage(fileChooser.showOpenDialog(stage));label.setText("Image tool");});
			//Selection
			Button bSelect = new Button("Select");
			bSelect.setOnAction(e->{tool = new ToolSelection(); label.setText("Selection tool");});
		ToolBar toolBar = new ToolBar();
		toolBar.getItems().addAll(bLine,new Separator(),bBox,new Separator(),bEllipse,new Separator(),bImg,new Separator(),bSelect,new Separator(),colorPicker);
		
		//Canvas
		canvas = new Canvas(800,600);
		canvas.setOnMousePressed(e -> {tool.press(this, e); draw(); tool.drawFeedback(this, canvas.getGraphicsContext2D());});
		canvas.setOnMouseDragged(e -> {tool.drag(this, e); draw();tool.drawFeedback(this, canvas.getGraphicsContext2D());});
		canvas.setOnMouseReleased(e-> {tool.release(this, e); draw();});

		//VBox
		VBox vBox = new VBox(menuBar,toolBar,canvas,new Separator(),label);
		
		//Stage
		stage.setScene(new Scene(vBox));
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
		return stack;
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
