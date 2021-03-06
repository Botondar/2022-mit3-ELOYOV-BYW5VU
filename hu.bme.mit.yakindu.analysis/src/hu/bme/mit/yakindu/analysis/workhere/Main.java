package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		int i = 0; 
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			i++;
			if(content instanceof State) {
				State state = (State) content;
				System.out.println(state.getName());
				
				// 2.4
				EList<Transition> outTransitions = state.getOutgoingTransitions();
				if (outTransitions.isEmpty())
				{
					System.out.println(state.getName() + " csapda allapot");
				}
				
				// 2.5
				if (state.getName().isEmpty()) {
					System.out.println("Nevtelen allapot; javaslat: Allapot_" + i);
				}
			}
			
			// 2.3
			if (content instanceof Transition)
			{
				Transition transition = (Transition)content;
				System.out.println(transition.getSource().getName() + " -> " + transition.getTarget().getName());
			}
			
			// 4.3
			if (content instanceof EventDefinition) {
				EventDefinition eventDef = (EventDefinition)content;
				System.out.println("Esemeny: " + eventDef.getName());
			}
			
			// 4.3
			if (content instanceof VariableDefinition) {
				VariableDefinition varDef = (VariableDefinition)content;
				System.out.println("Valtozo: " + varDef.getName());
			}
		}
		
		fel45(s);
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	
	public static void fel44(Statechart s) {
		// 4.4
		System.out.println("public static void print(IExampleStateMachine s) {");
		TreeIterator<EObject> iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if (content instanceof VariableDefinition) {
				VariableDefinition varDef = (VariableDefinition)content;
				String name = varDef.getName();
				char firstChar = name.toUpperCase().charAt(0);
				String casedName = firstChar + name.substring(1);
				System.out.println("System.out.println(\"" + firstChar + " = \" + s.getSCInterface().get" + casedName  + "());");
			}
		}
		System.out.println("}");	
	}
	
	public static void fel45(Statechart s) {
		
		System.out.println("public static void main(String[] args) {");
		
		System.out.println("Scanner scanner = new Scanner(System.in);");
		System.out.println("do {");
		System.out.println("String line = scanner.nextLine()");
		System.out.println("if (line.contentEquals(\"exit\")) {");
		System.out.println("break;");
		
		TreeIterator<EObject> iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if (content instanceof EventDefinition) {
				EventDefinition evtDef = (EventDefinition)content;
				String name = evtDef.getName();
				System.out.println("} else if (line.contentEquals(\"" + name + "\") {");
				System.out.println("s.raise" + name.toUpperCase().charAt(0) + name.substring(1) + "();");
			}
		}
		System.out.println("}");
		System.out.println("s.runCycle();");
		System.out.println("} while(true);");
		System.out.println("}");
		
		fel44(s);
	}
}
