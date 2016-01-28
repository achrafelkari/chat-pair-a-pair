package client;

import chatmodule.MessageBoxPOA;

public class MessageBoxImpl extends MessageBoxPOA{

	public String message; 
	
	@Override
	public String message() {
		return message;
	}

	@Override
	public void message(String newMessage) {
		message = newMessage;
	}

	@Override
	public void receive(String message) {
		this.message = " Message  : " + message;
	}

}
