import java.awt.*;
import java.awt.event.*;
//import java.lang.Math;
//import java.lang.Math.*;


//import java.lang.Math;
//import java.applet.Applet;

public class Slider extends BorderedPanel implements Adjustable {
 DjCanvas canvas;
   Panel graph,in;
   double sfo;
	Scrollbar scrollbar,scrollbar2;
	Label     valueLabel,comment;

	double sw;
        public void paint(Graphics g2) {
        
                Dimension       size2 = getSize();

//canvas.repaint();
                //g2.drawLine(0,size2.height-1,size2.width-1,0);
                //g2.drawRect(10,10,10,15);
               // g2.drawLine((scrollbar.getValue()+scrollbar2.getValue())/1000,0,(scrollbar.getValue()+scrollbar2.getValue())/1000,size2.height-1);
        }
        
	public Slider(int initialValue, int visible, int min, int max, double sfoi) {
	sfo=sfoi;

		String initialValueStr = Integer.toString(initialValue);
	    Panel graph = new Panel();
		canvas = new DjCanvas(sw,sfo);

		valueLabel = new Label(initialValueStr, Label.CENTER);
 		scrollbar  = new Scrollbar(Scrollbar.HORIZONTAL,   initialValue,     visible, min, max);
 		scrollbar2  = new Scrollbar(Scrollbar.HORIZONTAL, 0, visible,-1000, 1000);		
		setLayout(new BorderLayout());
		add(graph, "North");
		//valueLabel.setSize(100,50);

		add(scrollbar, "Center");
        add(scrollbar2, "South");
	//	canvas.addComponentListener(new DbgComponentListener());
		graph.add(canvas,"Center");
	//	graph.add(in,"Center");

		graph.add(valueLabel,"South");
		canvas.setValue(1d);
			//	canvas.addComponentListener(new DbgComponentListener());
		
		scrollbar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e1) {
				 valueLabel.setText("SWa = "+
Float.toString((float)((0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000))) +" ppm " +
Float.toString((float)(sfo*(0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000))) +
" Hz"  );
                                Slider.this.repaint();  
								sw=((0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000));
								canvas.setValue(sw);     
								System.out.println("1 "+Double.toString(sw));

			}
		});
		scrollbar2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e2) {
                             valueLabel.setText("SWa = "+
Float.toString((float)((0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000))) +
" ppm " +
Float.toString((float)(sfo*(0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000))) +
" Hz"  );
								Slider.this.repaint();       
								sw=((0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000));
								canvas.setValue(sw);
								System.out.println("2 "+Double.toString(sw));

			}
		});                
	}

	public void addAdjustmentListener(AdjustmentListener l) {
		scrollbar.addAdjustmentListener(l);
                scrollbar2.addAdjustmentListener(l);
	}
	public void removeAdjustmentListener(AdjustmentListener l) {
		scrollbar.removeAdjustmentListener(l);
                scrollbar2.removeAdjustmentListener(l);
	}
	public int getOrientation() {
		return scrollbar.getOrientation();	
	}
	public void setOrientation(int orient) {
		scrollbar.setOrientation(orient);	
			}
	public int getValue() {
		return ( scrollbar.getValue()+scrollbar2.getValue() );
	}
	public int getVisibleAmount() {
		return scrollbar.getVisibleAmount();
	}
	public int getMinimum() {
		return scrollbar.getMinimum();
	}
	public int getMaximum() {
		return scrollbar.getMaximum();
	}
	public int getUnitIncrement() {
		return scrollbar.getUnitIncrement();
	}
	public int getBlockIncrement() {
		return scrollbar.getBlockIncrement();
	}
	public void setValue(int value) {
		scrollbar.setValue(value);
                scrollbar2.setValue(0);
                Slider.this.repaint();
		valueLabel.setText(">SWa = "+
Float.toString((float)((0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000))) +
" ppm " +
Float.toString((float)(sfo*(0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000))) +
" Hz"  );
	canvas.setValue(((0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000)));

	}
	public void setVisibleAmount(int value) {
		scrollbar.setVisibleAmount(value);
	}
	public void setMinimum(int min) {
		scrollbar.setMinimum(min);
	}
	public void setMaximum(int max) {
		scrollbar.setMaximum(max);
	}
	public void setUnitIncrement(int inc) {
		scrollbar.setUnitIncrement(inc);
	}
	public void setBlockIncrement(int inc) {
		scrollbar.setBlockIncrement(inc);
	}
	public void setSfo(double sfo2) {
		sfo=sfo2;
	}
	//public Dimension getPreferredSize() {return new Dimension(500,100);}

}


class DjCanvas extends Canvas {
public double sfo2,sw2;
	public void paint(Graphics gg){
		Dimension size2=getSize();
		size2.width-=50;
		int i,j,p;
		float val[]={1000f,100f,10f,1f,0.1f};
		float vah[]={10000f,1000f,100f,10f,1f};

		gg.drawLine(0,25,size2.width,25);
		//gg.drawLine((int)(sw2/200*size2.width),0,(int)(sw2/200*size2.width),size2.height-1);
	//	p=  (int)(0.1d/sw2*(double)size2.width);
		p= (int)(60*(5-Math.log((double)sw2)/1d));
		gg.drawLine(p,0,p,size2.height);	

		for(i=0;i<5;i++){  
		for(j=1;j<10;j++){
			p=(int)(60*(5-(Math.log((double)j*val[i])/1d)));
			gg.drawLine(p,25,p,23);	}
			p=(int)(60*(5-(Math.log((double)val[i])/1d)));
			gg.drawLine(p,25,p,20);	

		String tata = Float.toString(val[i])+"";if(i==4) tata+=" ppm";
		gg.drawString(tata,p-10,12);
		}

		for(i=0;i<4;i++){ 
			for(j=1;j<10;j++){
			p=(int)(60*(5-(Math.log((double)j*vah[i]/sfo2)/1d)));
			gg.drawLine(p,25,p,27);	}
			p= (int)(60*(5-(Math.log((double)vah[i]/sfo2)/1d)));
		gg.drawLine(p,25,p,30);	
		  	String tata = Float.toString(vah[i])+"";if(i==3) tata+=" Hz";
             gg.drawString(tata,p-10,43);
		}

	}
	public  DjCanvas(double sw,double sfo){sw2=sw;sfo2=sfo;}
	public void setValue(double sw){
			sw2=sw; 
					//						System.out.println("= "+Double.toString(sw));

			//System.out.println("call to repaint  here");
			DjCanvas.this.repaint();
	}	
	public void setSfo(double sfo3){
			sfo2=sfo3;
	}

	public Dimension getPreferredSize() {return new Dimension(500,50);}
}

