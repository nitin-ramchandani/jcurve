package main.server;

import main.PlayerOptions;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	
	public static void registerClasses(EndPoint ep){
		Kryo kryo = ep.getKryo();
		kryo.register(PlayerOptions.class);
	}

}