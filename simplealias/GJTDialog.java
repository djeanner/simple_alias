import java.awt.*;
import java.awt.event.*;

public class GJTDialog extends Dialog {
	protected DialogClient client;
	protected boolean      centered;

	public GJTDialog(Frame frame, String title, 
					DialogClient aClient, boolean modal) {
		this(frame, title, aClient, true, modal);
	}
	public GJTDialog(Frame frame, String title,
					DialogClient aClient, boolean centered,
					boolean modal) {
		super(frame, title, modal);

		setClient(aClient);
		setCentered(centered);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				dispose();
				if(client != null)
					client.dialogCancelled(GJTDialog.this);
			}
		});
	}
	public void setCentered(boolean centered) {
		this.centered = centered;
	}
	public void setClient(DialogClient client) {
		this.client = client;
	}
	public void dispose() {
		Frame f = Util.getFrame(this);

		super.dispose();

		f.toFront();

		if(client != null)
			client.dialogDismissed(this);
	}
	public void setVisible(boolean visible) {
		pack();

		if(centered) {
			Dimension frameSize = getParent().getSize();
			Point frameLoc  = getParent().getLocation();
			Dimension mySize    = getSize();
			int x,y;

			x = frameLoc.x + (frameSize.width/2) -
							(mySize.width/2);
			y = frameLoc.y + (frameSize.height/2) -
							(mySize.height/2);

			setBounds(x,y,getSize().width,getSize().height);
		}
		super.setVisible(visible);
	}
}
