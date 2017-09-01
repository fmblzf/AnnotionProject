package test.andann;

public class Main {

	public static void main(String[] args) {
		Activity activity = new Activity();
		System.out.println(activity.view.name);
		activity.view.click();
		activity.view.longClick();
		activity.view.doubleClick();
	}
	
}
