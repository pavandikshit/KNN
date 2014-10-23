/**
 * 
 */
package uni.utd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author Pavan
 *
 */
public class MainClass {

	/**
	 * @param args
	 */

	String strFileName1 = null, strFileName2 = null;
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader(System.in));
	Scanner input = new Scanner(System.in);
	int kFold = 0; // k-fold cross validation
	double m = 0; // number of examples
	int t = 0; // the number of random permutations
	int k = 1; // k-nearest neighbor which will contain values as 1,2,3,4,5
	double e = 0.0; // estimate error
	double sigma = 0.0; // standard deviation
	int error = 0; // number of errors
	List<String> tPermutations = new ArrayList<String>(); // t-random permutations
	int rows = 0, cols = 0;
	ArrayList<ArrayList<String>> inputData;
	ArrayList<ArrayList<String>> inputData1; // inputData which contains entire grid
	ArrayList<ArrayList<String>> examples; // examples from the given grid

	// List<InputData[]> chunks = new ArrayList<InputData[]>();
	ArrayList<ArrayList<ArrayList<String>>> chunks = new ArrayList<ArrayList<ArrayList<String>>>();
	ArrayList<Distance> distLabel = new ArrayList<Distance>();
	ArrayList<Distance> dotLabel = new ArrayList<Distance>();
	
	static class InputData {
		int x;
		int y;
		int distance;
		char sign;

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		MainClass mainClass = null;

		try {
			mainClass = new MainClass();

			System.out
			.println("Enter Filename which contanis cross validation information: ");
			// strFileName1= input.nextLine();
			mainClass.strFileName1 = "d:/input.txt";
			System.out.println("Enter Filename which contains the data: ");
			// strFileName2 = input.nextLine();
			mainClass.strFileName2 = "d:/data.txt";
			System.out.println("FileNames are : " + mainClass.strFileName1
					+ " " + mainClass.strFileName2);

			/*
			 * Below Code gets cross validation information and stores in the
			 * respective variables kFold : k-fold cross validation m : total
			 * numbers of examples t : the random number of permutations
			 * tPermutations :
			 */

			mainClass.input = new Scanner(new File(mainClass.strFileName1));

			mainClass.kFold = Integer.parseInt(mainClass.input.next());
			mainClass.m = Integer.parseInt(mainClass.input.next());
			mainClass.t = Integer.parseInt(mainClass.input.next());
			System.out.println("K-fold cross validation: " + mainClass.kFold);
			System.out.println("Number of Examples: " + mainClass.m);
			System.out.println("Number of Permutations: " + mainClass.t);

			while (mainClass.input.hasNextLine()) {

				String strNumbers = mainClass.input.nextLine();
				if (strNumbers.trim().length() > 0)
					mainClass.tPermutations.add(strNumbers); // space separated
				// elements
				// are added in a list

			}

			/*
			 * Below code gets data grid and stores grid data and examples in
			 * respective variables rows : number of rows given in a grid cols :
			 * number of cols given in a grid inputData : input data grid
			 * including examples examples : examples given in a grid
			 */
			mainClass.input = new Scanner(new File(mainClass.strFileName2));
			

			mainClass.rows = Integer.parseInt(mainClass.input.next());
			mainClass.cols = Integer.parseInt(mainClass.input.next());

			System.out.println("Rows: " + mainClass.rows);
			System.out.println("Cols: " + mainClass.cols);

			mainClass.inputData = new ArrayList<ArrayList<String>>();
			mainClass.inputData1 = new ArrayList<ArrayList<String>>();
			mainClass.examples = new ArrayList<ArrayList<String>>();
			ArrayList<String> temp;
			String str;
			int count = 0;
			for (int i = 0; i < mainClass.rows; i++) {

				for (int j = 0; j < mainClass.cols; j++) {
					str = mainClass.input.next();
					temp =  new ArrayList<String>();
					
					temp.add("" + count);
					temp.add("" + j);
					temp.add("" + i);
					temp.add("" + str);
					mainClass.inputData.add(temp);
					
					temp = new ArrayList<String>();
					if (str.equals("+") || str.equals("-")) {
						temp.add("" + count);
						temp.add("" + j);
						temp.add("" + i);
						temp.add("" + str);
						mainClass.examples.add(temp);
						count++;
					}

				}
			}

			//mainClass.inputData1 = (ArrayList<ArrayList<String>>)mainClass.inputData.clone();
			
			// display the example data on screen
			System.out.println("example Number " + " x1 " + " x2 " + " y ");
			for (ArrayList<String> strArray : mainClass.examples) {
				for (String string : strArray) {
					System.out.print(string + " ");
				}
				System.out.println();
			}

			int partitionVector = (int) Math
					.ceil(mainClass.m / mainClass.kFold);
			System.out.println("Partition Vector: " + partitionVector);

			ArrayList<String> strSample;
			ArrayList<ArrayList<String>> strChunk;
			for (int i = 0; i < mainClass.tPermutations.size(); i++) {
				strSample = new ArrayList<String>();
				strChunk = new ArrayList<ArrayList<String>>();
				partitionVector = (int) Math
						.ceil(mainClass.m / mainClass.kFold);
				String strg = mainClass.tPermutations.get(i);
				String[] strChunks = strg.split(" ");

				int count1 = 0;
				for (int l = 0; l < mainClass.kFold; l++) {
					strSample = new ArrayList<String>();
					if ((strChunks.length - count1) < partitionVector)
						break;
					for (int j = count1; j < count1 + partitionVector; j++)
						strSample.add(strChunks[j]);
					System.out.println("strSample :: " + strSample);
					strChunk.add(strSample);
					count1 = count1 + partitionVector;
					// strChunk.add(strSample);
				}

				for (int j = count1; j < strChunks.length; j++)
					strSample.add(strChunks[j]);
				if (strSample.size() > 0) {
					System.out.println("strSample :: " + strSample);

					strChunk.add(strSample);
				}
				mainClass.chunks.add(strChunk);
			}
			int lmn = 0;
			for (ArrayList<ArrayList<String>> str1 : mainClass.chunks) {
				int pqr = 0;
				System.out.println("Permutations :" + (++lmn));
				for (ArrayList<String> str2 : str1) {
					System.out.println("Chunk" + (++pqr));
					for (String string : str2) {
						System.out.println(string);
					}

				}
			}

			// call function to calculate estimated error
			mainClass.calculateError();
			
			//
			
			//mainClass.deriveLabelForInputData(1);
			//mainClass.deriveLabelForInputData(2);
			mainClass.deriveLabelForInputData(3);
			//mainClass.deriveLabelForInputData(4);
			//mainClass.deriveLabelForInputData(5);
			

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {

			if (mainClass.input != null) {
				mainClass.input.close();
			}

		}

	}

	/*
	 * Function: deriveLabelFork2() will derive the labels for inputdata when k = 2
	 */
	
	public void deriveLabelFork2(){
		
		
	}
	
	public void calculateError() {
		ArrayList<String> temp;
		int x1, x2;
		ArrayList<ArrayList<String>> distLabel = null;
		String y = "";
		String label = "";
		double V = 0;
		double ex = 0;
		double E[] = {0,0,0,0,0,0,0,0,0,0,0,0};
		int cnt = 0;
		for (ArrayList<ArrayList<String>> arrayList : chunks) {
			error = 0;// initialise to 0
			for (ArrayList<String> string : arrayList) {
				for (String string2 : string) {
					temp = new ArrayList<String>();
					temp = findExample(string2);
					x1 = Integer.parseInt(temp.get(1));
					x2 = Integer.parseInt(temp.get(2));
					y = temp.get(3);

					calculateDistance(x1, x2, y, string);
					System.out.println(x1 + " " + x2 + " " + y);
					
				}
			}
			ex = ((float)error)/m;
			e += ex;
			E[cnt] = ex;
			cnt++;
			System.out.println("Estimated error:: "+ex + " " +cnt);
		}
		
		e = e / t;
		for(int i=0;i<t;i++){
			V += (E[i] - e)*(E[i] - e); 
		}
		V = V / (t - 1);
		System.out.println("Accurate Error:: "+e);
		sigma = (float)Math.sqrt(V);
		System.out.println("Sigma:: "+sigma);
		
		
		
		
	}

	// This Function derives label for input data
	public void deriveLabelForInputData(int k){
		int cnt = 0;
		int dist = 0;
		Distance temp = null;
		dotLabel = new ArrayList<MainClass.Distance>();
		inputData1 = new ArrayList<ArrayList<String>>();
		ArrayList<String> strSample = new ArrayList<String>();
		int x1 = -1;
		int x2 = -1;
		String exampleNo = "";
		String y = "";
		int flag = 1;
				
		for (ArrayList<String> arrayList : inputData) {
			
			y = arrayList.get(3);
			exampleNo = arrayList.get(0);
			x1 = Integer.parseInt(arrayList.get(1));
			x2 = Integer.parseInt(arrayList.get(2));
			
			System.out.println("arrayList:: "+arrayList+" cnt:: "+(++cnt));
			if(y.equalsIgnoreCase(".")){
				
				dotLabel = new ArrayList<MainClass.Distance>();
				
				System.out.println("x1::"+x1+" x2::"+x2);
				for (ArrayList<String> arrayList1 : examples) {
					int x11 = Integer.parseInt(arrayList1.get(1));
					int x22 = Integer.parseInt(arrayList1.get(2));
					dist = (x1 - x11)*(x1 - x11) + (x2 - x22)*(x2 - x22);
					System.out.println("dist:: "+dist);
					temp = new Distance();
					temp.setDistance(dist);
					temp.setLabel(arrayList1.get(3));
					
					dotLabel.add(temp);
				}
				
				System.out.println("Before Sorting: "+dotLabel.toString());
				Collections.sort(dotLabel, new CustomComparator());
				System.out.println("After Sorting: "+dotLabel.toString());
				
				
				int counter = 1;
				String lbl = dotLabel.get(0).getLabel();
				int dis = dotLabel.get(0).getDistance();
				flag = 1;
				for(int i = 1; i< dotLabel.size(); i++){
				
					if(dis != dotLabel.get(i).getDistance()){
						if(flag == k)
							break;
						else{
							flag++;
							dis = dotLabel.get(i).getDistance();
							
							if(lbl.equalsIgnoreCase(dotLabel.get(i).getLabel())){
								counter ++;
							}
							else{
								if(counter != 0){
									counter --;
								}
								else{
									lbl = dotLabel.get(i).getLabel();
									counter++;
								}
							}
						}
					}
					else{
						
						
						if(lbl.equalsIgnoreCase(dotLabel.get(i).getLabel())){
							counter ++;
						}
						else{
							if(counter != 0){
								counter --;
							}
							else{
								lbl = dotLabel.get(i).getLabel();
								counter++;
							}
						}
					}
				}
				
				
				if(counter == 0){
					y = "-";
					
				}else{
					
					y = lbl;
					
				}
			}
			strSample = new ArrayList<String>();
			//strSample.add(exampleNo);
			strSample.add(Integer.toString(x1));
			strSample.add(Integer.toString(x2));
			strSample.add(y);
			inputData1.add(strSample);
			

		}
		
		/*for (ArrayList<String> strArray : inputData1) {
			for (String string : strArray) {
				System.out.print(string + " ");
			}
			System.out.println();
		}*/
		System.out.println("-------------");
		System.out.println("k= "+k+" e="+e+" sigma="+sigma);
		
		int kg = 0;
		for(int i=0; i < rows; i++){
			
			for(int j=0; j < cols; j++){
				System.out.print(inputData1.get(kg++).get(2)+" ");
			}
			System.out.println("");
		}
		
	}
	
	
	public ArrayList<String> findExample(String str) {
		System.out.println("Find the string: " + str);
		for (ArrayList<String> arrayList : examples) {
			if (arrayList.get(0).equals(str)) {
				return arrayList;
			}
		}

		return null;
	}

	public void calculateDistance(int x1, int x2, String y,
			ArrayList<String> chunk) {
		int dist = 0;
		Distance temp = null;
		distLabel = new ArrayList<MainClass.Distance>();
		for (ArrayList<String> arrayList : examples) {
			if (!chunk.contains(arrayList.get(0))) {
				System.out.println("chunk:: " + chunk);
				System.out.println("arraylist:: " + arrayList.get(0));
				int x11 = Integer.parseInt(arrayList.get(1));
				int x22 = Integer.parseInt(arrayList.get(2));
				dist = (x1 - x11)*(x1 - x11) + (x2 - x22)*(x2 - x22);
				temp = new Distance();
				temp.setDistance(dist);
				temp.setLabel(arrayList.get(3));

				distLabel.add(temp);
			}
		}
		System.out.println("Before Sorting: "+distLabel.toString());
		Collections.sort(distLabel, new CustomComparator());
		System.out.println("Before Sorting: "+distLabel.toString());
		
		int counter = 1;
		String lbl = distLabel.get(0).getLabel();
		int dis = distLabel.get(0).getDistance();
		
		for(int i = 1; i< distLabel.size(); i++){
		
			if(dis != distLabel.get(i).getDistance()){
				break;
			}
			else{
				if(lbl.equalsIgnoreCase(distLabel.get(i).getLabel())){
					counter ++;
				}
				else{
					if(counter != 0){
						counter --;
					}
					else{
						lbl = distLabel.get(i).getLabel();
						counter++;
					}
				}
			}
		}
		
		if(counter == 0){
			error++;
			
		}else{
			if(!(y.equalsIgnoreCase(lbl))){
				error++;
			}
		}
		
		System.out.println("Error:: "+error);
	}

	public class CustomComparator implements Comparator<Distance> {

		@Override
		public int compare(Distance o1, Distance o2) {
			// TODO Auto-generated method stub
			return o1.getDistance() - o2.getDistance();
		}



	}

	class Distance{
		private int distance;
		private String label;
		
		
		public int getDistance() {
			return distance;
		}


		public void setDistance(int distance) {
			this.distance = distance;
		}


		public String getLabel() {
			return label;
		}


		public void setLabel(String label) {
			this.label = label;
		}


		@Override
		public String toString() {
			return "Distance [distance=" + distance + ", label=" + label + "]";
		}
		
		
		
	}
}
