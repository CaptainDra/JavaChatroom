
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.applet.*;


//���÷�����IP��ַ
public class ChatClient 
{
      
    public ChatClient()
    {
    }
    public static void main(String args[])
	{
		new Login();
	}
}
    
//��¼��Ϣ���л�
class Customer extends Object implements java.io.Serializable
{
     String custName;
     String custPassword;
}

//ע����Ϣ���л�
class Register_Customer extends Object implements java.io.Serializable
{
     String custName;
     String custPassword;
}

//���ڷ�������������û�����Ϣ  
class Message implements Serializable
{
  	Vector userOnLine;
  	Vector chat;
}
//������Ϣ���л�
class Chat implements Serializable
{
	String  chatUser;
	String  chatMessage;
	String  chatToUser;
	//boolean whisper;
}  
//�˳���Ϣ���л�
class Exit implements Serializable
{
    String exitname;	
}