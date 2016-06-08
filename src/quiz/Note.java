package quiz;

public class Note implements Message {

	@Override
	public void displayMessage() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Note))
			return false;
		Note other = (Note) obj;
		return false;
	}
	
	
}
