package DM19S1;
/**
 * @author Tushar Chawla

 */



import java.io.IOException;

/**
 * This class includes a main method and used to initialize the
 * DonorOperator Class to read and write file data.
 * @author Tushar Chawla
 */
public class DM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 4) {

			DonorOperator donorOperator = new DonorOperator(args);
			donorOperator.readDonorFile();
			try {
				donorOperator.readDonorInstructionFile();
				donorOperator.writeToResultFile();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Input is Incorrect!");
		}
	}

}
