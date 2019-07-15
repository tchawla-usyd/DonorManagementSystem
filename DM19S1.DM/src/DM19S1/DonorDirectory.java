package DM19S1;

/**
 * @author Tushar Chawla

 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This class is used to enter valid donor records into the directory.
 * It also contains methods for deleting, quering and sorting donors
 * based on their name and birthday.
 * @author Tushar
 *
 */
public class DonorDirectory {

	// Declaration of instance fields
	private ArrayList<Donor> directory;
	public static ArrayList<Double> largestAmount = new ArrayList<Double>();

	// Declaration of Constructor fields
	public DonorDirectory() {
		directory = new ArrayList<Donor>();
	}

	// Method fields
	
	/**
	 * Take the ArrayList as argument and pass it to the Donor Class.If the Donor is valid,
	 * take appropriate action to add or update the donor into the directory
	 * @param arr
	 */

	public void addToDirectory(ArrayList<String> arr) {
		Donor d = new Donor(arr);
		if (d.isValidToAdd()) {
			if (!checkExistingDonor(d)) {
				directory.add(d);
			} else {
				// update or merge
				for (Donor exists : directory) {
					if (exists.getName().equals(d.getName()) && exists.getBirthday().equals(d.getBirthday())) {
						// Update records
						if (d.getPhone() != null) {
							exists.setPhone(d.getPhone());
						}
						if (d.getPostcode() != null) {
							exists.setPostcode(d.getPostcode());
						}
						if (d.getAddress() != null) {
							exists.setAddress(d.getAddress());
						}
						if(d.getRecipients() !=null){
							exists.setRecipients(d.getRecipients());
						}
						if(d.getDonations() !=null){
							exists.setDonations(d.getDonations());
						}

					}
				}
			}

		}

	}

	/**
	 *	Check if the Donor already exists in the Directory
	 * 
	 * @param Donor newOne
	 * @return true false
	 */

	private boolean checkExistingDonor(Donor newOne) {
		for (Donor exists : directory) {
			if (exists.getName().equals(newOne.getName()) && exists.getBirthday().equals(newOne.getBirthday())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * delete the donor from the directory based on his name and birthday
	 * 
	 * @param name
	 * @param birthday
	 */

	public void deleteDonor(String name, String birthday) {

		name = name.trim();
		birthday = birthday.trim();
		int i = 0;
		while (i < directory.size()) {
			if (directory.get(i).getName().equalsIgnoreCase(name) && directory.get(i).getBirthday().equals(birthday)) {
				directory.remove(directory.get(i));
			} else {
				++i;
			}
		}
	}

	/**
	 * query the donor having the same name
	 * 
	 * @param name
	 * @return ArrayList queryName
	 */

	public ArrayList<Donor> queryName(String name) {
		ArrayList<Donor> queryName = new ArrayList<Donor>();
		name = name.trim();
		int i = 0;
		while (i < directory.size()) {
			if (directory.get(i).getName().equalsIgnoreCase(name)) {
				queryName.add(directory.get(i));
				++i;
			} else {
				++i;
			}
		}

		return queryName;

	}
	
	/** Find index of the word in an array **/
	public int wordIndex(String[] wordArray, String word){
		int index = -1;
		for(int i=0;i<wordArray.length;i++){
			if(wordArray[i].trim().equalsIgnoreCase(word.trim())){
				index =i;
			}
		}
		return index;
	}
	
	/**Query the contact who has same Name and Birthday
	 * @param name
	 * @param birthday
	 * @return Donor
	 * 
	 * */
	
	public Donor queryNameAndBirthday(String name ,String birthday){
		Donor queryNameAndBirthday = new Donor();
		int i=0;
		while(i<directory.size()){
			if(directory.get(i).getName().equalsIgnoreCase(name) 
					&& directory.get(i).getBirthday().equalsIgnoreCase(birthday)){
				queryNameAndBirthday = directory.get(i);
				++i;
			}else{
				++i;
			}
		}
		return queryNameAndBirthday;
	}
	
	/** Sort By Name **/
	public static void SortName(ArrayList<Donor> al) {
		for (int i = 1; i < al.size(); i++) {
			for (int j = i; (j > 0) && (al.get(j).getName().compareTo(al.get(j - 1).getName()) < 0); j--) {
				Donor temp = new Donor();

				temp = al.get(j);
				al.set(j, al.get(j - 1));
				al.set(j - 1, temp);
			}
		}

	}
	
	/** Sort Birthdays using Insertion Sort*/

	public static void SortBirthday(ArrayList<Donor> al) {
		SimpleDateFormat toDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		for (int i = 0; i < al.size(); i++) {
			for (int j = i; j > 0 ; j--) {
				try{
					if(toDateFormat.parse(al.get(j).getBirthday())
							.compareTo(toDateFormat.parse(al.get(j - 1).getBirthday())) < 0){
						Donor temp = new Donor();

						temp = al.get(j);
						al.set(j, al.get(j - 1));
						al.set(j - 1, temp);
						
					}
				}catch(ParseException e){
					e.printStackTrace();
				}
			
			}
		}
	}
	
	/**
	 * Sum the donation amount for a particular donor
	 * @param n
	 * @return
	 */
	public Double[] donationSum(){
		Double[] arl = new Double[directory.size()];
		String[] arr = new String[directory.size()];
		for(int i=0;i<directory.size();i++){
			if(directory.get(i).getDonations() != null){
				arr = directory.get(i).getDonations().split(",");
				double sum =0;
				for(int j=0;j<arr.length;j++){
					sum = sum +Double.parseDouble(arr[j].trim());
				}
				arl[i] = (sum);
			}
			else{
				arl[i] = 0.0;
			}
			
		}
		return arl;
	}
	
	/**
	 * Compute the largest Donator and return the ArrayList<Donor>
	 * @param n
	 * @return
	 */
	public ArrayList<Donor> largestDonation(int n){
		ArrayList<Donor> arc = new ArrayList<Donor>();
		Double[] donationSum = donationSum();
		int i,maxIndex = 0;
		double max = donationSum[0];
		for(int k = 1 ;k<=n;k++){
			for(i=1;i<donationSum.length;i++){
				if(donationSum[i]>max){
					max = donationSum[i];
					maxIndex = i;
				}
			}
			largestAmount.add(max);
			arc.add(directory.get(maxIndex));
			donationSum[maxIndex] = -1.0;
			maxIndex =0;
			max = donationSum[0];
		}
		return arc;	
	}


	
	/** return the directory size 
	 * @return directory size
	 * */
	public int size(){
		return directory.size();
	}
	
	/** find the Donor using the index*/
	public Donor get(int i){
		return directory.get(i);
	}

}
