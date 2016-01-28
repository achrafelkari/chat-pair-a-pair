package testUnitaire;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import client.ClientConnectionImpl;
import client.ClientManagerImpl;
import client.MessageBoxImpl;
import chatmodule.ClientConnection;
import chatmodule.ClientConnectionHelper;
import chatmodule.ClientManager;
import chatmodule.ClientManagerHelper;
import chatmodule.MessageBox;
import chatmodule.MessageBoxHelper;
import chatmodule.ServerConnection;
import chatmodule.ServerConnectionHelper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // je force un peu l'ordre des execution ! 
public class TestPairAPair {


	@Test // je test si on peux prendre d'un anuaire namespace des objet ! 
	public void t1estAnnuaire() {
		try{
			String[] arg = new String[0];
			ORB orb = ORB.init(arg,null);
			// Create the ORB (or connect to it in our case)
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// load the corba Naming Servce 
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			// get the Hello Object from the Naming service 
			ServerConnection serverCon = (ServerConnection) ServerConnectionHelper.narrow(ncRef.resolve_str("ABC"));
			assertNotNull(serverCon);
			}catch(Exception e){
				fail();
				System.out.println("Erreur !");
				e.getStackTrace();
			}
	}
	
	@Test // je test si on peux prendre d'un anuaire namespace des objet ! 
	public void t1estConnexion() {
		try{
			String[] arg = new String[0];
			ORB orb = ORB.init(arg,null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			ServerConnection serverCon = (ServerConnection) ServerConnectionHelper.narrow(ncRef.resolve_str("ABC"));
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
		
			/** Création du MessageBox et du ClientConnection **/
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
				messageBox = clienASeCo.connect("titi", msgbox); // on test  sur le même client ! 
				messageBox.receive("Mon message");
			}
			
			assertTrue(msgbox.message() !=null && !"".equals(msgbox.message()));
			
		}catch(Exception e){
				fail();
				System.out.println("Erreur !");
				e.getStackTrace();
			}
	}
	
	
	
	
}
