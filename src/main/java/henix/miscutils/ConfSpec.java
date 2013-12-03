package henix.miscutils;

public class ConfSpec {

	public final String res;
	public final String target;
	public final Object[] params;

	public ConfSpec(String res, String target, Object[] params) {
		this.res = res;
		this.target = target;
		this.params = params;
	}
}
