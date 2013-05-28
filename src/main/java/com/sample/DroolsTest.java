package com.sample;

import java.io.InputStreamReader;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemoryEntryPoint;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.PackageBuilder;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;

import com.sample.model.Event;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

	private static WorkingMemoryEntryPoint entryPoint;
	private static KnowledgeBuilder kbuilder;
	private static StatefulKnowledgeSession ksession;
	private static NodeToExpert nte;
	private static KnowledgeBase kbase;
	private static RuleBase rulebase;
	
    public static final void main(String[] args) {
        try {
            // load up the knowledge base
        	Bot bot = new Bot();
        	bot.send("Bienvenido");
        		
        	//Twitter tw =new Twitter();
        	//tw.send("ola k ase @carloscrespog");
            kbase = readKnowledgeBase();
            
           
            
            DroolsToEwe dte=new DroolsToEwe("src/main/rules/Sample.drl");
            dte.ewify();

            KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
            conf.setOption(ClockTypeOption.get("realtime"));
            ksession = kbase.newStatefulKnowledgeSession(conf,null);
            
            entryPoint = (WorkingMemoryEntryPoint) ksession.getWorkingMemoryEntryPoint("entrada");
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            new Thread() {
            	@Override
            	public void run() {
            		ksession.fireUntilHalt();
            	}
            }.start();
            nte=new NodeToExpert("http://localhost:3000/",entryPoint,kbuilder,kbase,ksession);
            //SessionPseudoClock clock = ksession.getSessionClock();
            Event ev=new Event("prueba","hello ");
            entryPoint.insert(ev);
            new Thread() {
            	@Override
            	public void run() {
    	            
            		nte.init();
            	}
            }.start();

            
            try {
                //System.err.println("[[ Sleeping ...]]");
                Thread.sleep(5000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            // go !
            
            
            
            
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
      
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        

//        Collection<KnowledgePackage> c = kbuilder.getKnowledgePackages();
//        KnowledgePackage k = c.iterator().next();
//        Collection<Rule> rules = k.getRules();
//        DroolsToEwe dte = new DroolsToEwe(rules);
//        dte.ewify();
        
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
       
        return kbase;
    }
    


}
