package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;

public interface CIMMessageHandler {

	public boolean process(Message message);
}
