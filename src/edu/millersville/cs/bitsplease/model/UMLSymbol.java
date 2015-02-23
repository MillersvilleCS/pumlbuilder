package edu.millersville.cs.bitsplease.model;

import javafx.event.EventDispatcher;
import javafx.event.Event;
import javafx.event.EventDispatchChain;

public abstract class UMLSymbol implements EventDispatcher {
	
	public Event dispatchEvent(Event event, EventDispatchChain tail){
		
		return event;
	}

}
