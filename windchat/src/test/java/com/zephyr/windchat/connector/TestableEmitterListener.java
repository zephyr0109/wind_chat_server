package com.zephyr.windchat.connector;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.socket.emitter.Emitter;

public abstract class TestableEmitterListener implements Emitter.Listener{
	
	
	@Override
	@Test
	public abstract void call(Object... args);
	
	
	public JSONObject getJSONObject(Object obj) {
		
		if(obj instanceof String) {
			return new JSONObject((String)obj);
		} else if(obj instanceof JSONObject) {
			return (JSONObject) obj;
		}	
		return null;
	}

	

}
