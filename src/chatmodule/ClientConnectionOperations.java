package chatmodule;


/**
* chatmodule/ClientConnectionOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* lundi 25 janvier 2016 22 h 27 CET
*/

public interface ClientConnectionOperations 
{
  chatmodule.MessageBox mbx ();
  void mbx (chatmodule.MessageBox newMbx);
  chatmodule.MessageBox connect (String nickname, chatmodule.MessageBox rcv);
} // interface ClientConnectionOperations
