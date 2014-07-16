import java.util.Random;
import java.util.Scanner;

public class Main {
	
	private static Sort instance;
	private static FileSystemWatcher watcher;
	
	public static void main(String[] args) {
		
		int[] array = new int[]{3,2,5,2,1,7,8};
		watcher = FileSystemWatcher.getInstance();
		Scanner scanner = null;
		
		try {
			instance = SortFactory.getInstance();
			watcher.start();
			
			scanner = new Scanner(System.in);
			
			String inputData = null;
			while(true){
				System.out.println("\n\n\t[ new Start ]");
				System.out.println("\nPlease Input Enter, start \""+ instance.getClass().getName() + "\"\n");
				
				System.out.print("Before Sort : ");
				shuffleArray(array);
				printArray(array);
				
				inputData = scanner.nextLine();
				
				if (inputData.equalsIgnoreCase("exit"))
					break;

				array = instance.sort(array);
				
				System.out.print("After Sort : ");
				printArray(array);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Terminated");
		scanner.close();
		watcher.watchStop();
	}
	
	private static void shuffleArray(int[] array) {
		Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	      int index = random.nextInt(i + 1);

	      int temp = array[index];
	      array[index] = array[i];
	      array[i] = temp;
	    }
	}

	private static void printArray(int[] array) {
		System.out.print("[");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			
			if (i != array.length-1) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
	}

	static void changeSortInstance() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		instance = SortFactory.getInstance();
	}
}