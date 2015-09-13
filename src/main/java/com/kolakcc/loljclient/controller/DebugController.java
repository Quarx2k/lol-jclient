package com.kolakcc.loljclient.controller;

import com.kolakcc.loljclient.view.DebugView;

import javax.swing.*;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;


public class DebugController extends KolaController {
	DebugView view;
	public DebugController() {
		view = new DebugView();
		this.setView(view);
		
		try {
			final PipedInputStream outPipe = new PipedInputStream();
			System.setOut(new PrintStream(new PipedOutputStream(outPipe), true));
			
			new SwingWorker<Void, String>() {
		        protected Void doInBackground() throws Exception {
		            Scanner s = new Scanner(outPipe);
		            while (s.hasNextLine()) publish(s.nextLine() + "\n");
		            s.close();
		            return null;
		        }
		        protected void process(List<String> chunks) {
		            for (String line : chunks) view.addOutLine(line);
		        }
		    }.execute();
		    

			final PipedInputStream errPipe = new PipedInputStream();
			System.setErr(new PrintStream(new PipedOutputStream(errPipe), true));
			
			new SwingWorker<Void, String>() {
		        protected Void doInBackground() throws Exception {
		            Scanner s = new Scanner(errPipe);
		            while (s.hasNextLine()) publish(s.nextLine() + "\n");
		            s.close();
		            return null;
		        }
		        protected void process(List<String> chunks) {
		            for (String line : chunks) view.addErrLine(line);
		        }
		    }.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
