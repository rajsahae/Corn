import java.util.*;

public class BoardGame {
	public static int maxPlayers;
	public static int minPlayers;
	public static int recommendedNumPlayers;
	
	public GameBoard board;
	public List<Player> players;
	public List<Player.PlayerBoard> playerBoards;
	public List<BoardGameListener> listeners;
	
	public BoardGame(int numPlayers){
		this.board = new GameBoard(numPlayers);
	}
	
	public void addListener(BoardGameListener bgl){
		listeners.add(bgl);
	}
	
	public void removeListener(BoardGameListener bgl){
		listeners.remove(bgl);
	}
	
	public int getNumPlayers(){
		return playerBoards.size();
	}
	
	public static void setMaxPlayers(int num){
		BoardGame.maxPlayers = num;
	}
	
	public static void setMinPlayers(int num){
		BoardGame.minPlayers = num;
	}
	
	public static void setRecNumPlayers(int num){
		BoardGame.recommendedNumPlayers = num;
	}
	
	public void addPlayer(String playerName, int position){
		players.add(new Player(playerName, position));
		firePlayerAddedEvent();
	}
	
	public void removePlayer(String playerName){
		Iterator<Player> i = players.iterator();
		while(i.hasNext()){
			if (i.next().getName().equals(playerName)){
				i.remove();
				firePlayerRemovedEvent();
				return;
			}
		}
	}
	
	private void firePlayerAddedEvent(){
		BoardGameEvent bge = new BoardGameEvent(this);
		Iterator<BoardGameListener> list = listeners.iterator();
		while (list.hasNext()){
			list.next().playerAdded(bge);
		}
	}
	
	private void firePlayerRemovedEvent(){
		BoardGameEvent bge = new BoardGameEvent(this);
		Iterator<BoardGameListener> list = listeners.iterator();
		while (list.hasNext()){
			list.next().playerDropped(bge);
		}
	}
	
	private void fireGameStartedEvent(){
		BoardGameEvent bge = new BoardGameEvent(this);
		Iterator<BoardGameListener> list = listeners.iterator();
		while (list.hasNext()){
			list.next().gameStarted(bge);
		}
	}
	
	private void fireGamePausedEvent(){
		BoardGameEvent bge = new BoardGameEvent(this);
		Iterator<BoardGameListener> list = listeners.iterator();
		while (list.hasNext()){
			list.next().gamePaused(bge);
		}
	}
	
	private void fireGameEndedEvent(){
		BoardGameEvent bge = new BoardGameEvent(this);
		Iterator<BoardGameListener> list = listeners.iterator();
		while (list.hasNext()){
			list.next().gameEnded(bge);
		}
	}
	
	public static class GameBoard {
		private int numPlayers;
		
		public GameBoard(int num){
			this.setNumPlayers(num);
		}

		/**
		 * @param numPlayers the numPlayers to set
		 */
		public void setNumPlayers(int numPlayers) {
			this.numPlayers = numPlayers;
		}

		/**
		 * @return the numPlayers
		 */
		public int getNumPlayers() {
			return numPlayers;
		}
	}
	
	public static class Player {
		private String name;
		private int position;
		
		public Player(String name, int position){
			this.name = name;
			this.position = position;
		}
		
		public String getName(){
			return name;
		}
		
		public int getPosition(){
			return position;
		}
		
		public static class PlayerBoard extends GameBoard {

			public PlayerBoard(int num) {
				super(num);
				// TODO Auto-generated constructor stub
			}
		}
	}
	
	public static class BoardGameEvent extends EventObject {
		private static final long serialVersionUID = 4543579682732986136L;
		
		public BoardGameEvent(Object source){
			super(source);
		}
	}
	
	public static interface BoardGameListener {
		public void playerAdded(BoardGameEvent e);
		public void playerDropped(BoardGameEvent e);
		public void gameStarted(BoardGameEvent e);
		public void gamePaused(BoardGameEvent e);
		public void gameEnded(BoardGameEvent e);
		
	}
	
}
