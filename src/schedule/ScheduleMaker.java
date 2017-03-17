package schedule;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import entities.*;


public class ScheduleMaker {
	
	private List<Entry<Map<Integer,Bundle>,Double>> pool; //List<Entry<Map<Nurse.ID,Bundle>,Bundle.saving>>
	private Map<Integer,Vector<Patient>> schedule; // Map<Nurse.ID,Bundle.Patient[]>
	private Vector<Nurse> availableNurses;
	private Vector<Patient> patientList;
	private Vector<Patient> scheduledPatients;
	private double totalSaving; 
	
	
	
	public ScheduleMaker(){
		
		pool = new LinkedList<Entry<Map<Integer,Bundle>,Double>>();
		schedule = new HashMap<Integer,Vector<Patient>>();
		scheduledPatients = new Vector<Patient>();
		patientList = new Vector<Patient>();
		availableNurses = new Vector<Nurse>();
		totalSaving = 0.0;
	}
	
	
	
	public void createPool(){
		
		Map<Map<Integer,Bundle>,Double> m = new HashMap<Map<Integer,Bundle>,Double>();
		
		for(Nurse n : availableNurses){
			
			for(Bundle b: n.getBundles()){
				
				Map<Integer,Bundle> key = new HashMap<Integer,Bundle>();
				key.put(n.getID(), b);
				m.put(key, b.getSaving());		
			}
			
		 }
		
		pool = new LinkedList<Entry<Map<Integer,Bundle>,Double>>(m.entrySet());
	}
	
	
	
	
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
	
	
	//when you call this method put sortPool() as parameter
	public Map<Integer,Vector<Patient>> makeSchedule(Map<Map<Integer,Bundle>,Double> sortedPool){
		
	   while(!availableNurses.isEmpty()){
		   
		   Map<Integer,Bundle> m = (Map<Integer,Bundle>) sortedPool.keySet().toArray()[0];
		    int id = (int) m.keySet().toArray()[0];
		    Bundle b = (Bundle) m.get(id);
		    schedule.put(id, b.getPatients());
		    
		    for(Entry<Map<Integer,Bundle>,Double> entry: sortedPool.entrySet()){
		    	
		    	if(id == (int) entry.getKey().keySet().toArray()[0]) sortedPool.remove(entry.getKey());
		    	if(b.overlap(entry.getKey().get(id))) sortedPool.remove(entry.getKey());
		    }
		    
		    totalSaving += b.getSaving();
		    availableNurses.remove(id-1);
		    
		    for(Patient pa:b.getPatients()) {
		    	scheduledPatients.addElement(pa);
		    }
	   }
	   
	   if(!sortedPool.isEmpty()){
		   
		   int backupNurseID = 44444;
		   
		   for(Patient p : patientList){
			   
			   if(!scheduledPatients.contains(p)){
				   
				   Vector<Patient> vec = new Vector<Patient>();
				   vec.add(p);
				   
				   schedule.put(++backupNurseID, vec);
			   }
		   }
	   }  
		
		return schedule;
	}
	
	
	public void printSchedule(){
		
		for(Entry<Integer,Vector<Patient>> e: schedule.entrySet()){
			
			System.out.println(e.toString());
		}
	}
	
	
	public double getTotatCost(){
		
		int NormalCost = 0;
		
		for(Patient p : patientList){
			
			NormalCost += p.PRICE;
		}
		
		
		return NormalCost - totalSaving;
	}
	

	public static void main(String[] args) {
		
		/**
		 * 1 - load the Patients in the patientList (for each patient call new Patient(ID,price) and add it to the list)
		 * 2 - get the nurses and their bundles: put nurses in the availableNurse vector(create the bundles list of a nurse before creating the nurse)
		 * 3 - call createPool()
		 * 4 - call makeSchedule(sortPool())
		 * 5 - PrintScledule() 
		 */
		
	}

}
