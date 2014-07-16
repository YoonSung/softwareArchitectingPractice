
public class Main {
	
	public static void main(String[] args) {
		int[] array = new int[]{3,2,5,2,1,7,8};
		Sort instance;
		try {
			instance = SortFactory.getInstance();
			array = instance.sort(array);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
}