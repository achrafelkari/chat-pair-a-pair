package server;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

import chatmodule.ClientConnection;
import chatmodule.ClientConnectionHelper;
import chatmodule.ClientManager;
import chatmodule.ServerConnection;
import chatmodule.ServerConnectionHelper;
import chatmodule.ServerConnectionPOA;

public class ServerConnectionImpl extends ServerConnectionPOA{
	
	private ORB orb;
	
	public void setOrb(ORB o ){
		orb = o;
		
	}
	@Override
	public String[] client() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void client(String[] newClient) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connect(String nickname, ClientConnection cnx, ClientManager mgr) {
		System.out.println("Appelle à ma fonction ");
	
		try {	
			
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
		
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			NameComponent path[] = ncRef.to_name(nickname);
			ncRef.rebind(path, cnx); // je stock l'objet donné dans l'annuaire ! 
			
			System.out.println("Le client "+ nickname + " est ajouté à l'annaire ! ");
		} catch (Exception e) {
			
			System.out.println("Erreur dans l'ajout du client " + nickname + " au annuaire  ");

		}
	}

	@Override
	public void disconnect(String nickname) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClientConnection getClient(String nickname) {


		org.omg.CORBA.Object objRef;
		NamingContextExt ncRef =null;
		try {
			objRef = orb.resolve_initial_references("NameService");
			// load the corba Naming Servce 
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			ncRef= NamingContextExtHelper.narrow(objRef);
		} catch (InvalidName e) {
			e.printStackTrace();
		} catch (AdapterInactive e) {
			e.printStackTrace();
		}
		
		
		// je prends l'objet stocké dans l'annuaire  ! 

		ClientConnection clientConnexion = null;
		try {
			clientConnexion = (ClientConnection) ClientConnectionHelper.narrow(ncRef.resolve_str(nickname));
		} catch (NotFound | CannotProceed
				| org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			System.out.println("L'utilisateur '"+nickname + "' n'est pas connecté ! ");
		}
		
		return clientConnexion;
	}

}
