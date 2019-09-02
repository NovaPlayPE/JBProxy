package net.novaplay.jbproxy.session;

import net.novaplay.library.callback.Callback;
import net.novaplay.jbproxy.server.Server;
import net.novaplay.library.netty.ConnectionListener;
import net.novaplay.library.netty.NettyHandler;
import net.novaplay.library.netty.PacketHandler;
import net.novaplay.library.netty.packet.Packet;

import java.io.*;
import java.util.*;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.net.*;

public class SessionManager {
	
	private Server server;
	private Integer port;
	private NettyHandler nettyHandler;
	private PacketHandler packetHandler;
	private ConnectionListener connectionListener;
	private ArrayList<Channel> verifiedChannels = new ArrayList<Channel>();
	
	public SessionManager(Server server, int port) {
		this.server = server;
		this.port = port;
	}
	
	public void start() {
		nettyHandler = new NettyHandler();
		nettyHandler.startServer(this.port, new Callback(){
			@Override
			public void accept(Object... args) {
				
			}
		});
		packetHandler = new PacketHandler() {
			@Override
			public void receivePacket(Packet packet, Channel channel) {
				server.handleProxyPackets(packet,channel);
			}
			
			@Override
			public void registerPackets() {
				
			}
		};
		nettyHandler.registerPacketHandler(packetHandler);
		server.refreshClients();
	}
	
	public void registerPacket(Class<? extends Packet> packett) {
		if(packetHandler != null && nettyHandler != null) {
			packetHandler.registerPacket(packett);
		}
	}
	
	public void sendPacket(Packet packet, Channel channel) {
		if(packetHandler != null && nettyHandler != null) {
			if(verifiedChannels.contains(channel)){
				packetHandler.sendPacket(packet,channel);
			}
		}
	}
	
	public ArrayList<Channel> getVerifiedChannels(){
		return verifiedChannels;
	}

}