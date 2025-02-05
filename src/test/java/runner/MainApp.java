package runner;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import runner.TestRunner;
public class MainApp {
	public static void main(String[] args) throws Exception {                    
		JUnitCore junit = new JUnitCore(); 
		junit.addListener(new TextListener(System.out)); 
		junit.run(TestRunner.class);   		
		//JUnitCore.main("runner.TestRunner");            
	}
}
