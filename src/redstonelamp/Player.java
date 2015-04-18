package redstonelamp;

public class Player {
	private int health;
	private int maxhealth = 20;
	
	public void sendMessage(String message) {
		//TODO: Send message to player
	}
	
	public String getName() {
		return null;
	}
	
	public void setHealth(int health){
		this.health = health;
		if(health > this.getMaxHealth())
			this.health = 20;
		if(health < 0)
			this.health = 0;
		if(health == 0)
			this.kill();
		return;
	}

	public int getMaxHealth(){
		return this.maxhealth;
	}
	
	private void kill() {
		if(this.getHealth() > 0)
			this.setHealth(0);
		//TODO: Kill player
	}
	
	public int getHealth(){
		return this.health;
	}
}