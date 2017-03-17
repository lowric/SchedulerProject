package entities;

public class Nurse {
	
	
	private int ID;
	private String name;
	private Bundle[] bundles;
	
	public Nurse(int id, String name, Bundle[] bundle){
		
		this.ID = id;
		this.name = name;
		this.bundles = bundle;
	}
	
	public int getID(){
		
		return ID;
	}
	
	public String getName(){
		
		return name;
	}
	
	public Bundle[] getBundles(){
		
		return bundles;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
