package client;

import chatmodule.ClientConnectionPOA;
import chatmodule.MessageBox;

public class ClientConnectionImpl extends ClientConnectionPOA{
	
	public MessageBox msx;
	
	@Override
	public MessageBox connect(String nickname, MessageBox rcv) {

		return msx;
	}

	@Override
	public MessageBox mbx() {
		return msx;
	}

	@Override
	public void mbx(MessageBox newMbx) {
    msx = newMbx;
	}

}
