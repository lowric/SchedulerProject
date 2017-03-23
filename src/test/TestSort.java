package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;


import entities.*;


public class TestSort {
	
	private List<Entry<Map<Integer,Bundle>,Double>> pool;
	private ArrayList<Patient> patients;
	private ArrayList<Nurse> nurses;
	private Map<Integer,Bundle> schedule;
	private Vector<Patient> scheduledPatients;
	private double totalSaving;
	
	
	/**
	 * Default Constructor
	 * Initialize the servers.
	 */
	public TestSort(){
		
		pool = new LinkedList<Entry<Map<Integer,Bundle>,Double>>();
		patients = new ArrayList<Patient>();
		nurses = new ArrayList<Nurse>();
		schedule = new HashMap<Integer,Bundle>();
		scheduledPatients = new Vector<Patient>();
		totalSaving = 0.0;
		
		int[] values = {40,45,30,20,60,50,65,55,60};
		
		for(int i = 0; i < 9; i++){
			
			Patient p = new Patient(i+1, values[i]);
			
			patients.add(p);
		}
		
		for(int i = 0; i < 3; i++){
			
			if(i == 0){
				
				Bundle[] b = new Bundle[2];
				
				Double[] p = {100.0,80.0};
				
				Vector<Vector<Patient>> pl = new Vector<Vector<Patient>>();
				Vector<Patient> p1 = new Vector<Patient>();
				Vector<Patient> p2 = new Vector<Patient>();
				
				p1.add(patients.get(0));p1.add(patients.get(2));p1.add(patients.get(6));
				p2.add(patients.get(1));p2.add(patients.get(2));p2.add(patients.get(3));
				
				pl.add(p1);
				pl.add(p2);
				
				for(int j = 0; j < 2; j++){
					
					Bundle b1 = new Bundle(p[j],pl.get(j));
					
					b[j] = b1;
					
				}
				
				Nurse n1 = new Nurse(i+1,"N1", b);
				nurses.add(n1);
				
			}
            if(i == 1){
            	
                Bundle[] b = new Bundle[3];
				
				Double[] p = {125.0,93.0,120.0};
				
				Vector<Vector<Patient>> pl = new Vector<Vector<Patient>>();
				Vector<Patient> p1 = new Vector<Patient>();
				Vector<Patient> p2 = new Vector<Patient>();
				Vector<Patient> p3 = new Vector<Patient>();
				
				p1.add(patients.get(2));p1.add(patients.get(8));p1.add(patients.get(7));
				p2.add(patients.get(0));p2.add(patients.get(4));p2.add(patients.get(3));
				p3.add(patients.get(4));p3.add(patients.get(7));p3.add(patients.get(5));
				
				pl.add(p1);
				pl.add(p2);
				pl.add(p3);
				
				for(int j = 0; j < 3; j++){
					
					Bundle b1 = new Bundle(p[j],pl.get(j));
					
					b[j] = b1;
					
				}
				
				Nurse n2 = new Nurse(i+1,"N2", b);
				nurses.add(n2);
				
			}
            if(i == 2){
            	
                Bundle[] b = new Bundle[2];
				
				Double[] p = {97.0,85.0};
				
				Vector<Vector<Patient>> pl = new Vector<Vector<Patient>>();
				Vector<Patient> p1 = new Vector<Patient>();
				Vector<Patient> p2 = new Vector<Patient>();
				
				p1.add(patients.get(5));p1.add(patients.get(2));p1.add(patients.get(0));
				p2.add(patients.get(1));p2.add(patients.get(3));p2.add(patients.get(8));
				
				pl.add(p1);
				pl.add(p2);
				
				for(int j = 0; j < 2; j++){
					
					Bundle b1 = new Bundle(p[j],pl.get(j));
					
					b[j] = b1;
					
				}
				
				Nurse n3 = new Nurse(i+1,"N3", b);
				nurses.add(n3);
				
			}
		}
	}
	
	
	/**
	 * Creates a pool consisting of all the bundles from every nurses.
	 */
    public void createPool(){
		
		Map<Map<Integer,Bundle>,Double> m = new HashMap<Map<Integer,Bundle>,Double>();
		
		for(Nurse n : nurses){
			
			for(Bundle b: n.getBundles()){
				
				Map<Integer,Bundle> key = new HashMap<Integer,Bundle>();
				key.put(n.getID(), b);
				m.put(key, b.getSaving());		
			}
			
		 }
		
		pool = new LinkedList<Entry<Map<Integer,Bundle>,Double>>(m.entrySet());
	}
	
    
    /**
	 * @return Map<Map<Integer,Bundle>,Double> sortedPool
	 * Sort the created pool from the bundle saving the most to the one saving the leat money.
	 */
	public Map<Map<Integer,Bundle>,Double> sortPool(){
		
		 // Sorting the list based on values
       Collections.sort(pool, new Comparator<Entry<Map<Integer,Bundle>,Double>>()
       {
           public int compare(Entry<Map<Integer,Bundle>,Double> o1,
           		Entry<Map<Integer,Bundle>,Double> o2)
           {
           	return o2.getValue().compareTo(o1.getValue()); 
           }
       });
       
       Map<Map<Integer,Bundle>,Double> sortedPool = new LinkedHashMap<Map<Integer,Bundle>,Double>();
       for (Entry<Map<Integer,Bundle>,Double> entry : pool)
       {
       	sortedPool.put(entry.getKey(), entry.getValue());
       }
       
       return sortedPool;
	}
	
	
	/**
	 * @param sortedPool
	 * @param b: selected Bundle
	 * @param id: selected nurse id
	 * Removes other bundles from the selected nurse as well as other overlapping bundles from other nurses.
	 */
	public void removeOverlaps(Map<Map<Integer,Bundle>,Double> sortedPool, Bundle b, int id){
		
		Iterator<Entry<Map<Integer,Bundle>,Double>> it = sortedPool.entrySet().iterator();
	    
	    while(it.hasNext()){
	    	
	    	Map<Integer,Bundle> m1 = it.next().getKey();
	    	
	    	
	    	int compID = (int) m1.keySet().toArray()[0];
	    	Bundle bd = m1.get(compID);
	    	
	    	if(id == compID){
	    
	    		it.remove();
	    	}
	    	else if(b.overlap(bd)){
	    		
	    		it.remove();
	    	}
	    	
	    }
	}
	
	
	/**
	 * Assigns backup nurses to unscheduled patients.
	 */
	public void scheduleBackupNurses(){
		
		int backupNurseID = 44444;
		   
		   for(Patient p : patients){
			   
			   if(!scheduledPatients.contains(p)){
				   
				   Vector<Patient> vec = new Vector<Patient>();
				   vec.add(p);
				   
				   schedule.put(++backupNurseID, new Bundle(p.PRICE, vec));
			   }
		   }
		
	}
	
	
	/**
	 * @param sortedPool
	 * Generates a schedule for available nurses and possibly backup nurses
	 * Selecting the bundles that save the most money.
	 */
	public Map<Integer,Bundle> makeSchedule(Map<Map<Integer,Bundle>,Double> sortedPool){
		
		   int count = 0; // counts the number of assigned nurses
		   
		   while(!nurses.isEmpty() && sortedPool.size() != 0){
			   
			   @SuppressWarnings("unchecked")
			   Map<Integer,Bundle> m = (Map<Integer,Bundle>) sortedPool.keySet().toArray()[0];
			   int id = (int) m.keySet().toArray()[0];
			   
			   System.out.println("N"+id+" is being scheduled, pool size: " + sortedPool.size());
			   System.out.println("Pool: "+sortedPool);
			    
			    Bundle b = (Bundle) m.get(id);
			    schedule.put(id, b);
			    
			    removeOverlaps(sortedPool,b,id);
			    
			    totalSaving += b.getSaving();
			    
			    int comp = id-(++count);
			    
			    if(comp >= 0 && !nurses.isEmpty()) nurses.remove(comp);
			    
			    for(Patient pa:b.getPatients()) {
			    	scheduledPatients.addElement(pa);
			    }
		   }
		   
		   if(!sortedPool.isEmpty()){
			   
			   scheduleBackupNurses();
		   }  
			
			return schedule;
		}
	
	
	/**
	 * Computes the total cost of the schedule.
	 */
    public double getTotatCost(){
		
		int NormalCost = 0;
		
		for(Patient p : patients){
			
			NormalCost += p.PRICE;
		}
		
		return NormalCost - totalSaving;
	}

    
    /**
	 * Executes the Program.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestSort test = new TestSort();
		test.createPool();
		Map<Integer,Bundle> scdl = test.makeSchedule(test.sortPool());
		
		System.out.println();
		
		for(Entry<Integer,Bundle> entry : scdl.entrySet()){
			
			System.out.print("N"+entry.getKey()+": ");
			
			for(Patient p : entry.getValue().getPatients()){
				
				System.out.print(p.getID()+" ");
			}
			System.out.println();
			
		}
		
		System.out.println();
		
		System.out.println("Total Cost: "+test.getTotatCost()+"0$");

	}

}
