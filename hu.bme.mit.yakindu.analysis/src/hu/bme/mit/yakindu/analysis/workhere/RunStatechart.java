package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;
import java.util.Scanner;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;

import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		
		s.init();
		s.enter();
		s.runCycle();
		//print(s);
		
		Scanner scanner = new Scanner(System.in);
		do {
			String cmd = scanner.nextLine();
			if (cmd.contentEquals("start")) {
				s.raiseStart();
			} else if (cmd.contentEquals("white")) {
				s.raiseWhite();
			} else if (cmd.contentEquals("black")) {
				s.raiseBlack();
			} else if (cmd.contentEquals("exit")) {
				break;
			}
			s.runCycle();
			print(s);
		} while(true);
		
		/*
		s.raiseStart();
		s.runCycle();
		System.in.read();
		s.raiseWhite();
		s.runCycle();
		print(s);
		*/
		System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
