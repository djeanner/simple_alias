import java.awt.*;
import java.awt.event.*;

public class QuestionDialog extends WorkDialog 
                            implements ActionListener{
	static private int  _defaultTextFieldSize = 20;
	private Button      okButton;
	private Button      cancelButton;
	private String      question,text;
	private TextField   textField;
	private TextArea	textArea;
	private boolean     wasCancelled;
	private int			i,nbc;
	private ButtonPanel buttonPanel = new ButtonPanel();

	
	public QuestionDialog(Frame  frame, DialogClient client, 
							String title, String question, 
							String mdata[], 
							int textFieldSize, Image image, 
							boolean modal) {
		super(frame, client, title, modal);

		QuestionPanel questionPanel;

		okButton     = addButton("Ok");
		cancelButton = addButton("Cancel");

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		nbc=Integer.parseInt(mdata[5]);text="";
		for(i=0;i<nbc;i++) {
			text=text+mdata[6+i];
			text=text+" "+mdata[6+nbc+i]+" "+mdata[7+nbc+nbc+i];
			if(i+1<nbc) text=text+"\n";

		}
		//System.out.println(":.. nbc ..:" +nbc);

//System.out.println(text);
		questionPanel = new QuestionPanel(this, question, mdata[0], textFieldSize, image, text);
		textField = questionPanel.getTextField();
		textArea = questionPanel.getTextArea();
		setWorkPanel(questionPanel);
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == cancelButton) 
			wasCancelled = true;
		else                             
			wasCancelled = false;

		dispose();
	}
	public void setVisible(boolean b) {
		textField.requestFocus();
		super.setVisible(b);
	}
	public void returnInTextField() {
		okButton.requestFocus();
	}
	public TextField getTextField() {
		return textField;
	}
	public TextArea getTextArea() {
		return textArea;
	}
	public String getAnswer() {
		return textField.getText();
	}
	public boolean wasCancelled() {
		return wasCancelled;
	}
	private void setQuestion(String question) {
		this.question = question;
	}
}






class QuestionPanel extends Postcard {
	private TextField      field;
	private TextArea      area;
	private QuestionDialog dialog;

	public QuestionPanel(QuestionDialog dialog, 
							String question, Image image, String text) {
		this(dialog, question, null, 0, image, " " );
	}
	public QuestionPanel(QuestionDialog dialog, String question,
							int columns, Image image, String text) {
		this(dialog, question, null, columns, image, " " );
	}
	public QuestionPanel(QuestionDialog myDialog, 
							String question,
							String initialResponse, int cols, 
							Image image, String text) {
		super(image, new Panel());

		Panel panel = getPanel();
		this.dialog = myDialog;

		panel.setLayout(new ColumnLayout());
		panel.add(new Label(question));

		if(initialResponse != null) {
			if(cols != 0) 
				panel.add(field = 
							new TextField(initialResponse, cols));
			else          
				panel.add(field = 
							new TextField(initialResponse));
		}
		else {
			if(cols != 0) panel.add(field = new TextField(cols));
			else          panel.add(field = new TextField());
		}
        panel.add(area = new TextArea(text,20,60,TextArea.SCROLLBARS_BOTH));
 //       panel.add(area = new TextArea(text));

		area.setFont(new Font("Monospaced",Font.PLAIN,12));
		field.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dialog.returnInTextField();
			}
		});
	}
	public TextArea getTextArea() {
		return area;
	}
	public TextField getTextField() {
		return field;
	}
}
