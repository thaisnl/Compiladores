package envs;

public class ErrorMsg {
	private boolean anyErrors = false;

	public void complain(String msg) {
		anyErrors = true;
		System.out.println(msg);
	}
}
