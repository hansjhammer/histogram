


import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
public class Histogram{
public static void main(String[] args){
	
	/*
	 * Set the amount of samples you want here, or pass them as an argument to the main method
	 */
	int samples = 200;

	if (args.equals(null)){
 samples = Integer.parseInt(args[0]);
	}


double[] means = {2.0,3.0}; 	
double[][] matrixData = { {4.0,0.0},{0.0,2.0}};
RealMatrix r = MatrixUtils.createRealMatrix(matrixData);
double[][] covariances = r.getData();


MultivariateNormalDistribution mnd = new MultivariateNormalDistribution(means, covariances);
//double vals[] = mnd.sample();
double minX=999;
double maxX =-999;
double minY=999;
double maxY =-999;

Tuple[] data = new Tuple[samples];
for (int i=0;i<samples;i++){
	double vals[] = mnd.sample();
	Tuple t = new Tuple(0.0,0.0);
	t.setX(vals[0]);
	t.setY(vals[1]);
	data[i]=t;
	if (t.getX()>maxX){
		maxX=t.getX();
	}
	if (t.getY()>maxY){
		maxY=t.getY();
	}
	if (t.getX()<minX){
		minX=t.getX();
	}
	if (t.getY()<minY){
		minY=t.getY();
	}
}

/**
 * You can set the amount of bins here (edit the 36). You will have to use a quadratic number.
 */
calcHistogram(data,minX,maxX,minY,maxY,36,samples);
calcLikelihood(data);

}
public static double[][] calcHistogram(Tuple[] data, double minX, double maxX,double minY, double maxY, int numBins,int samples) {
	  final double[][] result = new double[(int)Math.sqrt(numBins)][(int)Math.sqrt(numBins)];
	  for (double[] x :result){
		  for (int i= 0; i<(int)Math.sqrt(numBins);i++){
			  x[i]=0.0;
		  }
	  }
	  double binSize = Math.sqrt(((maxX - minX)* (maxY-minY)) / numBins);
	  double binWidth = (maxX-minX)/((int)Math.sqrt(numBins));
	  double binHeigth = (maxY-minY)/((int)Math.sqrt(numBins));
//	  System.out.println("width:"+binWidth);
//	  System.out.println("height:"+binHeigth);
//	  System.out.println("maxX: "+maxX + " minX: " +minX + " sqrt: "+ (int)Math.sqrt(numBins) );
//	  System.out.println("minY: "+minY + " maxY: "+ maxY);
	  for (Tuple t : data) {
		  
		  Double x = t.getX();
		  Double y = t.getY();
		  int x_bin = 0;
		  int y_bin= 0;

		  for (double i = minX;i<=maxX;i=i+binWidth){
//			  System.out.println("i: "+i);
//			  System.out.println("bucket: "+x_bin);
			
			  
			  if (x<=i+binWidth){
//				  System.out.println("i:"+i);
//				  System.out.println(x);
				  break;
			  }
			  if (x>i+binWidth){
			  x_bin++;
			  }
	    }
		  for (double i = minY;i<=maxY;i=i+binHeigth){
//			  System.out.println("i: "+i);
//			  System.out.println("bucket: "+y_bin);
		
			  if (y<=i+binHeigth){
				  break;
			  }
			  if (y>i+binHeigth){
			  y_bin++;
			  }
	    }  
//		  System.out.println("x:"+x+" y:" +y+ " binsize:"+binSize+ " y_bin:"+y_bin + " x_bin: "+x_bin);
		  if (x_bin>=(int)Math.sqrt(numBins)){
			  x_bin=(int)Math.sqrt(numBins)-1;
		  }
		  if (y_bin>=(int)Math.sqrt(numBins)){
			  y_bin=(int)Math.sqrt(numBins)-1;
		  }
				  
		  result[x_bin][y_bin]++;
	  }
	  for (int i=0; i<result.length;i++){
		  for (int z=0;z< result[i].length;z++){
			  result[i][z]=result[i][z]/samples;
		  }
	  }
	  DrawRectangle rect = new DrawRectangle();

	  rect.draw( result, minX,  maxX, minY,  maxY,  numBins , binWidth, binHeigth );

	  return result;
	}

	public static void calcLikelihood(Tuple[] data){
		double x_mean = 0.0;
		double y_mean = 0.0;
		for (Tuple t: data){
			x_mean = x_mean + t.getX();
		}
		x_mean = x_mean/data.length;
		System.out.println("most likely x_mean: " + x_mean);
		for (Tuple t: data){
			y_mean = y_mean + t.getY();
		}
		y_mean = y_mean/data.length;
		System.out.println("most likely y_mean: " + y_mean);
		
		double x_var = 0.0;
		for (Tuple t: data){
			double deviation_squared = Math.pow((t.getX()-x_mean),2.0);
			x_var = x_var + deviation_squared;
		}
		x_var = x_var/data.length;
		System.out.println("most likely x_var: "+x_var);
		
		double y_var = 0.0;
		for (Tuple t: data){
			double deviation_squared = Math.pow((t.getY()-y_mean),2.0);
			y_var = y_var + deviation_squared;
		}
		y_var = y_var/data.length;
		System.out.println("most likely y_var: "+y_var);
		
		double coVar = 0.0;
		for (Tuple t: data){
			coVar = coVar + (t.getX()-x_mean)*(t.getY()-y_mean);
		}
		coVar = coVar/(data.length-1);
		System.out.println("most likely coVar: "+coVar);
	}

}