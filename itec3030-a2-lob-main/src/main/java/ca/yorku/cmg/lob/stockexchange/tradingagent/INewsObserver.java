package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.events.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Interface to be implemented by object wishing to receive events from a NewsBoard.git 
 */
public interface INewsObserver {
	public void update(Event e);
}

class NewsBoard{
	private Queue<Event> event;
	private List<INewsObserver> observer;

	public NewsBoard(){
		this.event = new LinkedList<>();
		this.event = new ArrayList<>();
	}

	public void attach(INewsObserver observer){
		if(observer != null && !observers.cotains(observer))
		observer.add(observer);
	}

	public void detach(INewsObserver observer){
		observer.remove(observer);
	}

	private void notifyObservers (Event e){
		for (INewsObserver observer:observers)
			observer.update(e);
	}

	public void runEventsList(){
		 while (!events.isEmpty()) {
            Event e = events.poll(); 
            notifyObservers(e);
		 }
	}

	public void addEvent(Event e){
		 if (e != null) {
            events.add(e);
		}
	}

	public int getObserverCount(){
		return observers.size();
	}

	public getEventCount(){
		return events.size();
	}

	public void clearEvents(){
		events.clear();
	}



}
