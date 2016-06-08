package quiz;

public class Challenge implements Message {

	@Override
	public void displayMessage() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Challenge))
			return false;
		Challenge other = (Challenge) obj;
		return false;
	}
	
}
