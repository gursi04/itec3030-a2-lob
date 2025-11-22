package ca.yorku.cmg.lob.stockexchange.tradingagent;
import ca.yorku.cmg.lob.stockexchange.events.Event;
/**
 * Interface for accessing TradingStrategy functionality.
 */
public interface ITradingStrategy {
	void actOnEvent(Event e, int pos, int price);
}

public class AggressiveStrategy  implements ITradingStrategy{
	private Trader trader;
	private StockExchange exchange;

	public AggressiveStrategy(Trader trader, StockExchange exchange){
		this.trader = trader;
		this.exchange = exchange;
	}


	@Override
	protected void actOnEvent(Event e, int pos, int price) {
		IOrder newOrder = null;
		
		if (e instanceof GoodNews) {
            newOrder = new Bid(trader,e.getSecrity(),(int) Math.round(price*1.05), (int) Math.round(pos*0.5),e.getTime());
        } else if (e instanceof BadNews) {
        	newOrder = new Ask(trader,e.getSecrity(),(int) Math.round(price*0.90), (int) Math.round(pos*0.8),e.getTime());
        } else {
            System.out.println("Unknown event type");
        }
		
		if (newOrder!=null) {
			exchange.submitOrder(newOrder,e.getTime());
		}
    }
}

public class ConservativeStrategy implements ITradingStrategy{
	private Trader trader;
	private StockExchange exchange;

	public ConservativeStrategy (Trader trader, StockExchange exchange){
		this.trader = trader;
		this.exchange = exchange;
	}

	@Override
	protected void actOnEvent(Event e, int pos, int price) {
		
		IOrder newOrder = null;
		
		if (e instanceof GoodNews) {
            newOrder = new Bid(trader,e.getSecrity(),(int) Math.round(price*1.05), (int) Math.round(pos*0.2),e.getTime());
        } else if (e instanceof BadNews) {
        	newOrder = new Ask(trader,e.getSecrity(),(int) Math.round(price*0.95), (int) Math.round(pos*0.2),e.getTime());
        } else {
            System.out.println("Unknown event type");
        }
		
		if (newOrder!=null) {
			exchange.submitOrder(newOrder,e.getTime());
		}
    }
}

public abstract class TradingAgent implements INewsObserver{
	protected Trader trader;
	protected StockExchange exchange;
	protected NewsBoard newsBoard;
	protected ITradingStrategy strategy;

	public TradingAgent(Trader T, StockExchange e, NewsBoard n){
		this.trader = t;
		this.exchange = e;
		this.NewsBoard = n;

		if (n != null) {
            n.attach(this);
        }
	}

	public void update(Event e){
		int pos = trader.getPosition(e.getSecrity());
		int price = exchange.getCurrentPrice(e,getSecrity());

		actOnEvent(e, pos, price);
	}

	public void setStrategy (ITradingStrategy strategy){
		this.strategy = strategy;
	}

	public ITradingStrategy getStrategy(){
		return this.strategy;
	}


	public void actOnEvent(Event e, int pos, int price){
		if(strategy == null){
			System.out.println("no agent type selected")
			return;
		} else
		strategy.actOnEvent(e, pos, price);
	}

	public Trader getTrader(){
		return trader;
	}

	public StockExchange getExchange(){
		return exchange;
	}
	
	public NewsBoard getNewsBoard(){
		return newsBoard;
	}

	public class TradingAgentInstitutional extends TradingAgent{
		public TradingAgentInstitutional(Trader t, StockExchange e, NewsBoard n, ITradingStrategy strategy){
			super(t, e, n);
			this.strategy = strategy;
		}
	}

	public class TradingAgentRetail extends TradingAgent{
		public TradingAgentRetail(Trader t, StockExchange e, NewsBoard n, ITradingStrategy strategy){
			super(t, e, n);
			this.strategy = strategy;
		}
	}
}
