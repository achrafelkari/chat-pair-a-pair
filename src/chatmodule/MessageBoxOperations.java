package chatmodule;


/**
* chatmodule/MessageBoxOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* lundi 25 janvier 2016 22 h 27 CET
*/

public interface MessageBoxOperations 
{
  String message ();
  void message (String newMessage);
  void receive (String message);
} // interface MessageBoxOperations
