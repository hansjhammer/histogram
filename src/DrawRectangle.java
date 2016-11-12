import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {
	ArrayList<Tuple> sortedList;
	double minX;
	double maxX;
	double minY;
	double maxY;
	int numBins;
	double binWidth;
	double binHeigth;
	Color[][] intensity;
	public MyCanvas(ArrayList<Tuple> sortedList, double minX, double maxX,double minY, double maxY, int numBins, double binWidth, double binHeigth){
		this.sortedList=sortedList;
		this.minX=minX;
		this.maxX=maxX;
		this.maxY=maxY;
		this.minY=minY;
		this.numBins=numBins;
		this.binWidth=binWidth;
		this.binHeigth=binHeigth;
	}

public void paint(Graphics g) {
	
	double xStep = super.getWidth()/Math.sqrt(numBins);
	double yStep=super.getHeight()/Math.sqrt(numBins);
	
	
	for (Tuple t: sortedList){
		int xCounter =(int) t.getX();
		int yCounter=(int)t.getY();
		g.setColor(t.getColor());
		g.fillRect((int)(xCounter*xStep), (int)(yCounter*yStep), (int)xStep, (int)yStep);
		 g.drawRect((int)(xCounter*xStep), (int)(yCounter*yStep), (int)xStep, (int)yStep);
		
	}
	
	for (Tuple t: sortedList){
		int xCounter =(int) t.getX();
		int yCounter=(int)t.getY();
		g.setColor(Color.BLACK);
		g.drawString("value:"+Double.toString(t.getValue()), (int)(xCounter*xStep), (int)(0.6*yStep+yCounter*yStep));
	

	}
	for (int i=0; i<numBins;i++){
		g.drawString("x:"+Double.toString(minX+binWidth*i).substring(0,5), (int) (xStep*i), (int)(super.getHeight())/2+15 );
		g.drawString("y:"+Double.toString(minY+binHeigth*i).substring(0,5), (int) (super.getHeight()/2+15), (int)(yStep*(6-i))-10);

	}
//	for (int i=0;i<Math.sqrt(numBins);i++){
//		for (int z=0; z<Math.sqrt(numBins),z++){
//			g.setColor(c);
//			g.fillRect((int)(xCounter*xStep), (int)(yCounter*yStep), (int)xStep, (int)yStep);
//			 g.setColor(Color.BLACK);
//			 g.drawRect((int)(xCounter*xStep), (int)(yCounter*yStep), (int)xStep, (int)yStep);
//			yCounter++;
//		}
//		yCounter=0;
//		xCounter++;
//	}
//	g.setColor(new Color(212, 212, 212));
	g.setColor(Color.BLUE);
	g.drawString("min_x: "+Double.toString(minX).substring(0,5), 5, super.getHeight()/2);
	g.drawString("max_x:"+Double.toString(maxX).substring(0,5), super.getWidth()-80, super.getHeight()/2);
	g.drawString("min_y:"+Double.toString(minY).substring(0,5), super.getWidth()/2, super.getHeight());
	g.drawString("max_y:"+Double.toString(maxY).substring(0,5), super.getWidth()/2, 20);

	
	g.drawString("mid:"+"("+Double.toString((maxX+minX)/2).substring(0,5)+"|"+Double.toString((maxY+minY)/2).substring(0,5)+")", super.getWidth()/2, super.getHeight()/2);

	
	g.drawLine(0, super.getHeight()/2, super.getWidth(), super.getHeight()/2);
	g.drawLine(super.getWidth()/2 , 0 , super.getWidth()/2 , super.getHeight() );

}
}

public class DrawRectangle {

public void draw(double[][] data, double minX, double maxX,double minY, double maxY, int numBins, double binWidth, double binHeigth ){
	ArrayList<Tuple> sortedList = new ArrayList<Tuple>();
	
	for (int i=0;i<data.length;i++){
		for (int x = 0;x<data[i].length;x++){
			Tuple t = new Tuple((double)i,(double)x);
			t.setValue(data[i][x]);
			sortedList.add(t);
		}
	}
	Collections.sort(sortedList);
	for (Tuple t: sortedList){
		System.out.println(t.getValue());
	//	System.out.println(("MAX:"+sortedList.get(sortedList.size()-1).getValue()));
		t.setColor(getColor(sortedList.get(0).getValue(),sortedList.get(sortedList.size()-1).getValue(),t.getValue()));
	}

	
//	Color[][] colors = new Color[(int)Math.sqrt(numBins)][(int)Math.sqrt(numBins)];
//	for (Color[] array: colors){
//		for (int i =0; i<array.length;i++){
//			array[i]=Color.ORANGE;
//		}
//	}
	
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   window.setBounds(100, 100, 600,600);
    window.getContentPane().add(new MyCanvas(sortedList,minX,maxX,minY,maxY,numBins,binWidth,binHeigth));
    window.setVisible(true);
	
}
public Color getColor(double minimum, double maximum, double value){
//	System.out.println("minimum:"+minimum);
	double midVal = (maximum-minimum)/2;
//	System.out.println("max:"+maximum);
//	System.out.println("mid:"+midVal);

	int b =0;
	int r =0;
	int g= 0;

	if (value>midVal){
		r=255;
		g= (int)(Math.round(255 * ((maximum - value) / (maximum - midVal))));
	}
	else {
		g=255;
		r=(int)(Math.round(255*((value-minimum)/midVal-minimum)));
	}
	
//	System.out.println("r: "+r);
//	System.out.println("g: "+g);
//	System.out.println("b: "+b);
//
	return new Color(r,g,b);
}

}