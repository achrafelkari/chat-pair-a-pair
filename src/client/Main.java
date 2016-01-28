package client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import chatmodule.ClientConnection;
import chatmodule.ClientConnectionHelper;
import chatmodule.ClientManager;
import chatmodule.ClientManagerHelper;
import chatmodule.MessageBox;
import chatmodule.MessageBoxHelper;
import chatmodule.ServerConnection;
import chatmodule.ServerConnectionHelper;


public class Main {

	public static void main(String[] args) {
		try{
			// Create the ORB (or connect to it in our case)
			ORB orb = ORB.init(args,null);
			
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// load the corba Naming Servce 
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			// je prends l'objet stocké dans l'annuaire  ! 
			
			ServerConnection serverCon = (ServerConnection) ServerConnectionHelper.narrow(ncRef.resolve_str("ABC"));
			
			/** Création du MessageBox **/
			MessageBoxImpl messageBoxImpl = new MessageBoxImpl();
			org.omg.CORBA.Object ref3 = rootpoa.servant_to_reference(messageBoxImpl);
			MessageBox msgbox = MessageBoxHelper.narrow(ref3);
			

			ClientConnectionImpl clientConnImp = new ClientConnectionImpl();
			clientConnImp.msx = msgbox;
			
			/** Création du client Manager   **/
			ClientManagerImpl mgrimpl = new ClientManagerImpl();
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(mgrimpl);
			ClientManager mgr = ClientManagerHelper.narrow(ref);
			
			/** Création du ClientConnection !  **/
			org.omg.CORBA.Object ref2 = rootpoa.servant_to_reference(clientConnImp);
			ClientConnection clientConn = ClientConnectionHelper.narrow(ref2);
			
			
			/**Passer les objets créée au serveur!  **/ 
			serverCon.connect("titi", clientConn, mgr);

			
			ClientConnection clienASeCo=  serverCon.getClient("titi"); // on choisi le meme client (juste pour le test ! )
			
			
			MessageBox messageBox = null;
			if(clienASeCo!=null){
				messageBox = clienASeCo.connect("titi", msgbox);
				messageBox.receive("Mon message");
			}
			System.out.println("message "+msgbox.message());
			//	obj.getMessages("Achraf");
			
		}catch(Exception e){
			
		}

	}

}
