package entities;

import java.util.Vector;

public class Bundle {
	
	private double price;
	private Vector<Patient> patients;
	private double saving;
	
	public Bundle(double price, Vector<Patient> patients){
		
		this.price = price;
		this.patients = patients;
		saving = computeSaving(patients);
		
	}
	
	private double computeSaving(Vector<Patient> patient){
		
		int sum = 0;
		
		for(Patient p: patient){
			
			sum += p.PRICE;
			
		}
		
		return sum - price;
		
	}
	
	public boolean overlap(Bundle b){
		
		for (Patient p : patients){
			
			if(b.patients.contains(p)) return true;
		}
		
		return false;
	}
	
	public double getSaving(){
		
		return saving;
	}
	
	public Vector<Patient> getPatients(){
		
		return patients;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
