import java.util.Scanner;

public class Client {
	Scanner input = new Scanner(System.in);
	
	Engine engine = new Engine();
	
	public void menu() {
		//creates menu for user to select whether to create hamming code or check hamming code
		while(true) {
			System.out.println("________________________________________________________________________");
			System.out.println("*** Hamming Code Simulator ***\n\n");
			System.out.print(String.format("%-25s %s\n","Read Image:", "A"));
			System.out.print(String.format("%-25s %s\n","Encode Image:", "B"));
			System.out.print(String.format("%-25s %s\n","Check Changes:", "C"));
			System.out.print(String.format("%-25s %s\n","Exit:", "X"));
			System.out.print(String.format("%-25s %s","Enter Selection:", ""));
			switch (input.nextLine().toLowerCase()) {
			case "a":
				readImage();
				break;
			case "b":
				setMessage();
				break;
			case "c":
				checkChanges();
				break;
			case "x":
				exit();
				break;
			default:	//if the input matched none of the cases then
				System.out.println("\n__________________________________________________________");	//prints error to the console to select one of the values and repeats the loop
				System.out.println("\n\nInvalid input, please enter one of the options below\n");
			}
		}
	}
	
	public void readImage() {
		engine.getImage();
	}
	
	public void setMessage() {
		System.out.print(String.format("%-25s %s","Enter Number Message:", ""));
		engine.setMessage(input.nextInt());
		input.nextLine();
		System.out.println(engine.message);
		
		engine.encodeImage();
		
		engine.createImage();
	}
	
	public void checkChanges() {
		engine.checkChanges();
	}
	
	public void exit() {
		System.exit(0);
	}
}
