/**
 * 
 */
package uni.utd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

	static class InputData {
		int x;
		int y;
		int distance;
		char sign;

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String strFileName1 = null, strFileName2 = null;
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		Scanner input = new Scanner(System.in);
		int kFold = 0; // k-fold cross validation
		double m = 0; // number of examples
		int t = 0; // the number of random permutations
		int k = 1; // k-nearest neighbor which will contain values as 1,2,3,4,5
		float e = 0.0f; // estimate error
		float sigma = 0.0f; // standard deviation

		List<String> tPermutations = new ArrayList<String>(); // t-random
																// permutations
		String inputData1[][]; // inputData which contains entire grid
		Integer examples[][]; // examples from the given grid

		// List<InputData[]> chunks = new ArrayList<InputData[]>();
		ArrayList<ArrayList<String>> chunks = new ArrayList<ArrayList<String>>();

		try {

			System.out
					.println("Enter Filename which contanis cross validation information: ");
			// strFileName1= input.nextLine();
			strFileName1 = "d:/input.txt";
			System.out.println("Enter Filename which contains the data: ");
			// strFileName2 = input.nextLine();
			strFileName2 = "d:/data.txt";
			System.out.println("FileNames are : " + strFileName1 + " "
					+ strFileName2);

			/*
			 * Below Code gets cross validation information and stores in the
			 * respective variables kFold : k-fold cross validation m : total
			 * numbers of examples t : the random number of permutations
			 * tPermutations :
			 */

			input = new Scanner(new File(strFileName1));

			kFold = Integer.parseInt(input.next());
			m = Integer.parseInt(input.next());
			t = Integer.parseInt(input.next());
			System.out.println("K-fold cross validation: " + kFold);
			System.out.println("Number of Examples: " + m);
			System.out.println("Number of Permutations: " + t);

			while (input.hasNextLine()) {

				String strNumbers = input.nextLine();
				if (strNumbers.trim().length() > 0)
					tPermutations.add(strNumbers); // space separated elements
													// are added in a list

			}

			/*
			 * Below code gets data grid and stores grid data and examples in
			 * respective variables rows : number of rows given in a grid cols :
			 * number of cols given in a grid inputData : input data grid
			 * including examples examples : examples given in a grid
			 */
			input = new Scanner(new File(strFileName2));
			int rows = 0, cols = 0;

			rows = Integer.parseInt(input.next());
			cols = Integer.parseInt(input.next());

			System.out.println("Rows: " + rows);
			System.out.println("Cols: " + cols);

			inputData1 = new String[rows][cols];
			examples = new Integer[(int) m][4];

			String str;
			int count = 0;
			for (int i = 0; i < rows; i++) {

				for (int j = 0; j < cols; j++) {
					str = input.next();
					inputData1[i][j] = str;
					if (str.equals("+")) {
						examples[count][0] = count;
						examples[count][1] = j;
						examples[count][2] = i;
						examples[count][3] = 1;
						count++;
					} else if (str.equals("-")) {
						examples[count][0] = count;
						examples[count][1] = j;
						examples[count][2] = i;
						examples[count][3] = 0;
						count++;
					}
				}
			}

			// display the example data on screen
			System.out.println("example Number " + " x1 " + " x2 " + " y ");
			for (int i = 0; i < count; i++) {

				for (int j = 0; j < 4; j++) {

					System.out.print(examples[i][j] + " ");
				}
				System.out.println();
			}

			
			int partitionVector = (int) Math.ceil(m / kFold);
			System.out.println("Partition Vector: "+partitionVector);
		

			ArrayList<String> strChunk;
			
			for (int i = 0; i < tPermutations.size(); i++) {
				strChunk = new ArrayList<String>();
				partitionVector = (int) Math.ceil(m / kFold);
				String strg = tPermutations.get(i);
				String[] strChunks = strg.split(" ");
				String strSample = "";

				int count1 = 0;
				for (int l = 0; l < kFold; l++) {

					if ((strChunks.length - count1) < partitionVector)
						break;
					for (int j = count1; j < count1 + partitionVector; j++)
						strSample += strChunks[j] + " ";

					count1 = count1 + partitionVector;
					strChunk.add(strSample);
					strSample = "";
				}

				for (int j = count1; j < strChunks.length; j++)
					strSample += strChunks[j] + " ";

				if (strSample.length() > 0)
					strChunk.add(strSample);
				strSample = "";

				chunks.add(strChunk);

			}
			int lmn = 0;
			for (ArrayList<String> str1 : chunks) {
				System.out.println("Permutations :"+(++lmn));
				for (String str2 : str1) {
					System.out.println("Chunks :"+str2);
				}
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {

			if (input != null) {
				input.close();
			}

		}

	}

}
