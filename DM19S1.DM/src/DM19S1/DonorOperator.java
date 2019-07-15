package DM19S1;
/**
 * @author Tushar Chawla

 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is used to read members file and instruction file, and add it to the
 * directory.This class is also used to generate result and report file.
 * @author Tushar
 *
 */

public class DonorOperator {
	//Declaration of instance fields
	private File donorFile;
	private File instructionFile;
	private File resultFile;
	private File reportFile;
	private DonorDirectory directory;
	private Donor queryDonator;
	private ArrayList<Donor> queryTop;
	private ArrayList<Donor> queryName;
	private ArrayList<Donor> queryRecipients;
	private Scanner sc;

	//Constructor
	public DonorOperator(String[] s) {
		donorFile = new File(s[0] + ".txt");
		instructionFile = new File(s[1] + ".txt");
		resultFile = new File(s[2] + ".txt");
		reportFile = new File(s[3] + ".txt");
		queryTop = new ArrayList<Donor>();
		queryName = new ArrayList<Donor>();
		queryRecipients = new ArrayList<Donor>();
		directory = new DonorDirectory();

	}

	//Method Definitions
	/**
	 * Reading the data from the members file and adding it to the Donor Directory
	 */

	public void readDonorFile() {
		ArrayList<String> str = new ArrayList<String>();

		Scanner reader = null;
		try {
			reader = new Scanner(donorFile);

			String temp = "";
			String field = "";
			String fieldValue = "";
			String record = "";

			while (reader.hasNextLine()) {
				temp = reader.nextLine();
				if (temp.startsWith("name")) {
					field = temp.substring(0, 5);
					fieldValue = temp.substring(4).trim();
					record = field + fieldValue;
					str.add(record);
				} else if (temp.startsWith("birthday")) {
					field = temp.substring(0, 9);
					fieldValue = temp.substring(8).trim();
					record = field + fieldValue;
					str.add(record);
				} else if (temp.startsWith("postcode")) {
					field = temp.substring(0, 9);
					fieldValue = temp.substring(8).trim();
					record = field + fieldValue;
					str.add(record);
				} else if (temp.startsWith("phone")) {
					field = temp.substring(0, 6);
					fieldValue = temp.substring(5).trim();
					record = field + fieldValue;
					str.add(record);
				} else if (temp.startsWith("address")) {
					field = temp.substring(0, 8);
					fieldValue = temp.substring(7).trim();
					record = field + fieldValue;
					str.add(record);
				} else if (temp.startsWith("recipient")) {
					field = temp.substring(0, 10);
					fieldValue = temp.substring(9).trim();
					record = field + fieldValue;
					str.add(record);
				} else if (temp.startsWith("donation")) {
					field = temp.substring(0, 9);
					fieldValue = temp.substring(8).trim();
					record = field + fieldValue;
					str.add(record);
				} else if (temp.equals("")) {
					
					directory.addToDirectory(str);
					str.clear();
				} else {
					
					record = temp.trim();
					
					int index = 20;
					
					for (int i = 0; i < str.size(); i++) {
						if (str.get(i).startsWith("address")) {
							index = i;
						}
					}
					if (index != 20) {
						record = str.get(index) + " " + record;
						str.set(index, record);
					}
				}

			}

			directory.addToDirectory(str);
			str.clear();
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the file");
		}

	}

	/**
	 * Reading the instructions from the instruction file and changing the donor data in
	 * the directory accordingly.
	 * @throws IOException 
	 */

	public void readDonorInstructionFile() throws IOException {
		ArrayList<String> str1 = new ArrayList<String>();

		Scanner reader = null;
		try {

			reader = new Scanner(instructionFile);

			String temp = "";
			String field = "";
			String fieldValue = "";

			while (reader.hasNextLine()) {
				temp = reader.nextLine();

				sc = new Scanner(temp);
				if (sc.hasNext()) {
					// key value
					field = sc.next();

					// update operation
					if (field.equalsIgnoreCase("update")) {

						// getting the data
						fieldValue = sc.nextLine().trim();
						String[] arr = fieldValue.split(";");

						// Separating the records and storing in the arrayList
						int i = 0;
						while (i < arr.length) {
							str1.add(arr[i].trim());
							i++;
						}
						directory.addToDirectory(str1);
						// Reset the str and arr for next use
						str1.clear();
						arr = null;
					}

					// delete function
					if (field.equalsIgnoreCase("delete")) {
						// getting the data
						fieldValue = sc.nextLine().trim();
						String[] r = fieldValue.split(";");
						directory.deleteDonor(r[0], r[1]);
					}

					// donate function
						if(field.equalsIgnoreCase("donate")){
							fieldValue = sc.nextLine().trim();
							int wordIndex;
							int donationSum;
							String donationValue;
							String[] r = fieldValue.split(";");
							queryDonator = directory.queryNameAndBirthday(r[0].trim(), r[1].trim());
							
							for(int i = 2;i<r.length;i++){
								String[] insData = r[i].split(",");
								if(queryDonator.getRecipients() != null){
								if(queryDonator.getRecipients().toLowerCase().contains(insData[0].trim())){
									String[] recipientArray = queryDonator.getRecipients().split(",");
									String[] donationArray = queryDonator.getDonations().split(",");
									//System.out.println(directory.wordIndex(recipientArray, insData[0]));
									wordIndex = directory.wordIndex(recipientArray, insData[0]);
									donationSum = Integer.parseInt(donationArray[wordIndex].trim())+Integer.parseInt(insData[1].trim());
									donationValue = Integer.toString(donationSum);
									donationArray[wordIndex] = donationValue;
									//System.out.println(String.join(",", donationArray));
									queryDonator.setDonations(String.join(",", donationArray));
									//System.out.println("true");
								}else{
									//System.out.println("false");
										queryDonator.setRecipients(queryDonator.getRecipients().concat("," + insData[0]));
										queryDonator.setDonations(queryDonator.getDonations().concat("," + insData[1]));
								}
								}else{
									queryDonator.setRecipientsFromDonorOperator(insData[0]);
									queryDonator.setDonationFromDonorOperator(insData[1]);
								}
							}
							//System.out.println(queryDonator.toString());
							
							
							
						}

					// query Name function
					if (field.equalsIgnoreCase("query")) {
						field = sc.next().trim();

						// "query name" function
						if (field.equalsIgnoreCase("name")) {
							fieldValue = sc.nextLine().trim();
							queryName = directory.queryName(fieldValue);
							DonorDirectory.SortBirthday(queryName);
							publish();
							queryName.clear();
							
							
						}
						else if(field.equalsIgnoreCase("top")){
							fieldValue = sc.next().trim();
							/*System.out.println(fieldValue);
							for(int i =0 ;i<directory.size();i++){
								System.out.println(directory.donationSum()[i]);
							}*/
							queryTop = directory.largestDonation(Integer.parseInt(fieldValue));
							
							/*for(int i = 0;i<queryTop.size();i++){
								System.out.println(queryTop.get(i).getName());
							}*/
							publish();
							queryTop.clear();
							
						}
					}
					
					
					//query Recipients Function
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Writing the Donor Directory data into the results file

 */
	public void writeToResultFile() {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(resultFile));
			int i = 0;
			String newline = "\r\n";
			out.write("[Output File]" + newline);
			out.write(newline);
			while (i < directory.size()) {
				// write each object to the result file
				out.write("name    " + directory.get(i).getName() + newline);

				out.write("birthday    " + directory.get(i).getBirthday() + newline);

				if (directory.get(i).getPhone() != null) {
					out.write("phone    " + directory.get(i).getPhone() + newline);
				}

				if (directory.get(i).getRecipients() != null) {
					out.write("recipient    " + directory.get(i).getRecipients() + newline);
				}

				if (directory.get(i).getDonations() != null) {
					out.write("donation    " + directory.get(i).getDonations() + newline);
				}

				if (directory.get(i).getAddress() != null) {
					out.write("address    " + directory.get(i).getAddress() + newline);
				}

				if (directory.get(i).getPostcode() != null) {
					out.write("postcode    " + directory.get(i).getPostcode() + newline);
				}
				out.write(newline);
				i++;
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/** Write query report based on name , top and recipients
	 * @throws IOException
	 * */
	
	private void writeToReportFile(ArrayList<Donor> al , String str) throws IOException{
		if(al != null);
		BufferedWriter out;
		try{
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFile,true)));
			
			int i=0;
			String newline = "\r\n";
			out.write("[Query " + str + "]" + newline);
			out.write(newline);
			
			if(str.equalsIgnoreCase("name")){
				while(i<al.size()){
					out.write("name    " + al.get(i).getName() + newline);
					out.write("birthday    " + al.get(i).getBirthday() + newline);
					
					// For optional fields
					if(al.get(i).getPhone() != null){
						out.write("phone    " + al.get(i).getPhone() + newline);
					}
					
					if(al.get(i).getRecipients() != null){
						out.write("recipient    " + al.get(i).getRecipients() + newline);
					}
					if(al.get(i).getDonations() != null){
						out.write("donation    " + al.get(i).getDonations() + newline);
					}
					if(al.get(i).getAddress() != null){
						out.write("address    " + al.get(i).getAddress() + newline);
					}
					if(al.get(i).getPostcode() != null){
						out.write("postcode    " + al.get(i).getPostcode() + newline);
					}
					out.write(newline);
					i++;
					
				}
				out.write("-------------------------------------------------" + newline);
				out.flush();
				out.close();
			}else if(str.equalsIgnoreCase("top")){
				while(i<al.size()){
					out.write(al.get(i).getName()  + "; ");
					out.write(al.get(i).getBirthday() + "; ");
					out.write(DonorDirectory.largestAmount.get(i).toString());
					out.write(newline);
					i++;
				}
				out.write("-------------------------------------------------" + newline);
				out.flush();
				out.close();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	/** Publish the result file and the report file
	 * @throws IOException
	 * */
	public void publish() throws IOException{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFile,true)));
		out.write("QueryReport" + "\r\n" + "\r\n");
		out.close();
		if(queryName.size() != 0){
			writeToReportFile(queryName, "name");
		}
		if(queryTop.size() != 0){
			writeToReportFile(queryTop, "top");
		}
	}

}
