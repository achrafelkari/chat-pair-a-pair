package chatmodule;

/**
* chatmodule/MessageBoxHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from chat.idl
* lundi 25 janvier 2016 22 h 27 CET
*/

public final class MessageBoxHolder implements org.omg.CORBA.portable.Streamable
{
  public chatmodule.MessageBox value = null;

  public MessageBoxHolder ()
  {
  }

  public MessageBoxHolder (chatmodule.MessageBox initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = chatmodule.MessageBoxHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    chatmodule.MessageBoxHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return chatmodule.MessageBoxHelper.type ();
  }

}
