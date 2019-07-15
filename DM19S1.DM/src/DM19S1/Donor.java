package DM19S1;
/**
 * 
 * @author Tushar Chawla

 */
import java.util.ArrayList;

/**
 * This class is use to validate each of the donor fields such as name , birthday etc.
 * It also contains getter and setter methods.
 * @author Tushar
 *
 */
public class Donor {

	// Declaration of instance fields
	private String name;
	private String birthday;
	private String phone;
	private String postcode;
	private String address;
	private String recipient;
	private String donation;

	// Default Constructor for Donor Class to initialize fields
	public Donor() {
		// Initialization of instance fields
		name = null;
		birthday = null;
		phone = null;
		postcode = null;
		address = null;
		recipient = null;
		donation = null;
	}

	
	/**
	 * Take the argument as an ArrayList of the 
	 * the Donor record and validate each field
	 * @param arr
	 */
	public Donor(ArrayList<String> arr) {


		for (String content : arr) {
			if (content.startsWith("name")) {
				name = content.substring(5);
			} else if (content.startsWith("birthday")) {
				birthday = content.substring(9);
			} else if (content.startsWith("postcode")) {
				String codeStr = content.substring(9);
				if(validatePostcode(codeStr)){
					postcode = codeStr;
				}else{
					phone = null;
				} 
			} else if (content.startsWith("phone")) {
				String phoneStr = content.substring(6);
				if(validatePhone(phoneStr)){
					phone = phoneStr;
				}else{
					phone = null;
				}
			} else if (content.startsWith("address")) {
				String temp = content.substring(8);
				if(validateAddress(temp)){
					address = temp;
				}else{
					address = null;
				}
				
			} else if (content.startsWith("recipient")) {
				recipient = content.substring(10);
			} else if (content.startsWith("donation")) {
				donation = content.substring(9);
			}
		}
	}
	

	public void setRecipientsFromDonorOperator(String data){
		recipient = data;
	}
	

	public void setDonationFromDonorOperator(String data){
		donation = data;
	}
	
/**
 * If name and birthday are valid
 * 
 */
	public boolean isValidToAdd() {
		return validateName() && validateBirthday();
	}

	/**
	 * Validate name if it contains only alphabet
	 * 
	 */
	private boolean validateName() {
		if (name != null && name.matches("^([a-zA-Z\\s]+)$")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validate birthday if it is in correct format
	 * 
	 */
	private boolean validateBirthday() {
		if (birthday != null && birthday.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validate Phone if it contains 8 digit number
	 * 
	 */
	private boolean validatePhone(String phoneNumber) {
		if (phoneNumber == null) {
			return true;
		} else if (phoneNumber.matches("^[0-9]{8}$")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validate Postcode if it contains 4 digit code
	 * 
	 */
	private boolean validatePostcode(String code) {
		if (code == null) {
			return true;
		} else if (code.matches("^[0-9]{4}$")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validate Address such that it ends with Australian State code
	 * 
	 */
	private boolean validateAddress(String addressData) {

		if (addressData == null) {
			return true;
		} 
		else{
			String[] arr = addressData.split(",");
			int index = arr.length - 1;
			if (arr[index].trim().equalsIgnoreCase("NSW") || arr[index].trim().equalsIgnoreCase("ACT")
					|| arr[index].trim().equalsIgnoreCase("TAS") || arr[index].trim().equalsIgnoreCase("VIC")
					|| arr[index].trim().equalsIgnoreCase("QLD") || arr[index].trim().equalsIgnoreCase("WA")
					|| arr[index].trim().equalsIgnoreCase("SA") || arr[index].trim().equalsIgnoreCase("NT"))
			{
				return true;
			} else {
				return false;
			}
		}
			
		
	}

	/**
	 * Return Name Value
	 *
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return Birthday Value
	 *
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * Set Birthday
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * Return Postcode Value
	 *
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Set Postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Return Phone Value
	 *
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set Phone Value
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Return Address Value
	 *
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set Address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Return Recipients Value
	 *
	 */
	public String getRecipients(){
		return recipient;
	}
	
	/**
	 * Set Recipients
	 */
	public void setRecipients(String recipient){
		this.recipient = recipient;
	}
	
	/**
	 * Return Donations Value
	 *
	 */
	public String getDonations(){
		return donation;
	}
	
	/**
	 * Set Doantions
	 */
	public void setDonations(String donation){
		this.donation = donation;
	}
	
	/**
	 * Get Recipients value in an array
	 * 
	 */
	public String[] getReceipientsInArray(){
		return recipient.split(",");
	}
	
	/**
	 * Get Donations value in an array
	 * 
	 */
	public String[] getDonationsInArray(){
		return donation.split(",");
	}

	@Override
	public String toString() {
		return "Donor [name =" + name + ", birthday =" + birthday + ",phone = " + phone + ", postcode =" + postcode
				+ ", address = " + address + ",recipient = " + recipient + ", donation = " + donation + "]";
	}

}
