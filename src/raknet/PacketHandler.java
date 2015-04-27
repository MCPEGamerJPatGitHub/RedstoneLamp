package raknet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import raknet.packets.JoinPacket;
import raknet.packets.QueryPacket;
import raknet.packets.StartLoginPacket;
import redstonelamp.RedstoneLamp;
import redstonelamp.Server;
import redstonelamp.utils.MinecraftPacket;

public class PacketHandler implements Runnable {
	public RedstoneLamp server;
	private Server network;
	private DatagramPacket packet;
	private InetAddress clientAddress;
	private int clientPort;
	
	public PacketHandler(RedstoneLamp redstone, Server server, DatagramPacket packet) {
		this.server = redstone;
		this.network = server;
		this.packet = packet;
		this.clientAddress = packet.getAddress();
		this.clientPort = packet.getPort();
	}
	
	public void run() {
		Packet pkt = null;
		if(!(packet == null)) {
			int packetType = (packet.getData()[0] & 0xFF);
			int packetSize = packet.getData().length;
			switch(packetType) {
				case MinecraftPacket.QueryPacket:
					pkt = new QueryPacket(packet, network.serverID); //Show server info on Main Menu
				break;
				
				case MinecraftPacket.StartLoginPacket:
					pkt = new StartLoginPacket(packet, network.serverID, clientAddress, clientPort); //Check RakNet Protocol Version
				break;
				
				case MinecraftPacket.JoinPacket:
					pkt = new JoinPacket(packet, network.serverID, clientAddress, ((short) clientPort), network); //Start logging the player in
				break;
				
				case MinecraftPacket.RakNetReliability:
					encapsulatedDecode();
				break;
				
				default:
					this.network.getLogger().warn("Unknown packet from: " + clientAddress + ":" + clientPort + " | PacketData - Packet: " + packetType + " Size: " + packetSize);
				break;
			}
			
			if(!(pkt == null))
				pkt.process(this);
		}
	}
	
	public void sendPacket(ByteBuffer d) {
		DatagramPacket p = new DatagramPacket(d.array(), d.capacity());
		p.setAddress(clientAddress);
		p.setPort(clientPort);
		try {
			network.socket.send(p);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void encapsulatedDecode() {
		
	}
}