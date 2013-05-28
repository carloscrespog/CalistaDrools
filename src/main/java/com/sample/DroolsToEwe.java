package com.sample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.sample.model.Action;
import com.sample.model.Event;
import com.sample.model.EweRule;
import com.sample.model.Service;
import com.sample.model.User;

public class DroolsToEwe {

	private String source;

	
	public boolean ewify() throws IOException, FileNotFoundException{
		
		FileReader input = new FileReader(this.source);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;
		ArrayList<EweRule> arrayRules=new ArrayList<EweRule>();
		int currentRule=0;
		boolean flagThen=false;
		boolean flagWhen=false;
		User user=new User("my user");
		Service service = new Service("Drools");
		user.setService(service);
		while ( (myLine = bufRead.readLine()) != null)
		{    
			if(myLine.startsWith("rule")){
				String title = myLine.split("\"")[1];
				EweRule myRule=new EweRule(title);
				myRule.setUser(user);
				arrayRules.add(myRule);
			}

			if(flagWhen){
				//ewe only supports one event
				flagWhen=false;
				EweRule copyRule=arrayRules.get(currentRule);
				Event myEvent=new Event(myLine);
				copyRule.getUser().getService().setEvent(myEvent);
				arrayRules.set(currentRule, copyRule);
			}
			if(myLine.contains("when")){
				flagWhen=true;
			}
			if(flagThen&&myLine.contains("end")){
				flagThen=false;
				rdfy(arrayRules.get(currentRule));
				currentRule++;
			}
			
			if (flagThen){
				//flagThen=false;
				EweRule copyRule=arrayRules.get(currentRule);
				Action myAction=new Action(myLine);
				copyRule.getUser().getService().setAction(myAction);
				arrayRules.set(currentRule, copyRule);
			}
			if(myLine.contains("then")){
				flagWhen=false;
				flagThen=true;
			}

			
		}
		return true;
	}
	private void rdfy (EweRule eweRule){
		Model model = ModelFactory.createDefaultModel();		
		Resource myrule=model.createResource("http://www.gsi.dit.upm.es/ontologies/ewe#Rule");
		
		myrule.addProperty(DCTerms.title,eweRule.getTitle());
		myrule.addProperty(DCTerms.description,eweRule.getDescription());
		myrule.addProperty(ResourceFactory.createProperty("ewe:pageURL"), eweRule.getPageURL());
		myrule.addProperty(ResourceFactory.createProperty("ewe:timesUsed"), (new Integer(eweRule.getTimesUsed())).toString());
		
		Resource myUser=model.createResource("ewe:hasCreator");
		myUser.addProperty(FOAF.accountName, eweRule.getUser().getAccountName());
		
		Resource myService=model.createResource("ewe:Service");
		myService.addProperty(ResourceFactory.createProperty("ewe:pageURL"), eweRule.getUser().getService().getPageURL());
		myService.addProperty(DCTerms.description, eweRule.getUser().getService().getDescription());
		myService.addProperty(DCTerms.title, eweRule.getUser().getService().getTitle());
		myService.addProperty(FOAF.logo, eweRule.getUser().getService().getLogo());
		
		Resource myAction=model.createResource("ewe:Action");
		myAction.addProperty(DCTerms.title, eweRule.getUser().getService().getAction().getTitle());
		myAction.addProperty(DCTerms.description, eweRule.getUser().getService().getAction().getDescription());
		
		Resource myEvent=model.createResource("ewe:Event");
		myEvent.addProperty(DCTerms.title, eweRule.getUser().getService().getEvent().getTitle());
		myEvent.addProperty(DCTerms.description, eweRule.getUser().getService().getEvent().getDescription());
		
		myService.addProperty(ResourceFactory.createProperty("ewe:generatesEvent"),myEvent);
		
		myService.addProperty(ResourceFactory.createProperty("ewe:providesAction"),myAction);
		
		myUser.addProperty(ResourceFactory.createProperty("ewe:hasActiveService"),myService);
		
		myrule.addProperty(ResourceFactory.createProperty("ewe:hasCreator"),myUser);
		
		try {
			OutputStream out = new FileOutputStream("Rule"+eweRule.getTitle().trim()+".rdf");
			model.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ewe rule has been created as "+eweRule.getTitle().trim()+".rdf at root directory: ");
		model.write(System.out);
	}
	
	public DroolsToEwe(String source) {
		super();
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
//	public boolean ewify() {
//	Iterator rulesIterator=this.rules.iterator();
//	while(rulesIterator.hasNext()){
//		Rule newRule=(Rule) rulesIterator.next();
//		System.out.println("Parsing new rule: " + newRule.getName());
//		Map <String,Object> ruleMap = newRule.getMetaData();
//		Iterator mapIterator=ruleMap.entrySet().iterator();
//		System.out.println("in");
//		System.out.println(ruleMap.size());
//		while(mapIterator.hasNext()){
//			
//			Entry mapEntry =  (Entry) mapIterator.next();
//			System.out.println(mapEntry.getKey()+"----");
//		}
//		
//		
//	}
//	
//	return true;
//}
}
