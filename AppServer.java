import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.util.*;


//��װ��¼��Ϣ
class Customer implements Serializable
{
	String custName;
	String custPassword;
}

//��װע����Ϣ
class Register_Customer extends Object implements java.io.Serializable
{
     String custName;
     String custPassword;
     //String age;
     //String sex;
     //String email;
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
}  
//�˳���Ϣ���л�
class Exit implements Serializable
{
    String exitname;	
}


//����������
public class AppServer extends Thread
{
	ServerSocket serverSocket;
	public ServerFrame sFrame;
	static Vector u=new Vector(1,1);
	static Vector v=new Vector(1,1);
	int len;
	public AppServer()
	{
	 	sFrame=new ServerFrame();
	 	try
	 	{
			serverSocket = new ServerSocket(1001);
			//��ȡ����������������IP��ַ
			InetAddress address = InetAddress.getLocalHost();      
   			sFrame.txtServerName.setText(address.getHostName());
   			sFrame.txtIP.setText(address.getHostAddress());
   			sFrame.txtPort.setText("1001");
		}
		catch(IOException e)
		{
			fail(e,"������������");
		}
		sFrame.txtStatus.setText("������...");
		this.start();    //�����߳�
	}
	public void apppendMsg(String msg){

        this.sFrame.taMessage.append(msg+"\r\n");
    }
	public void lstup(){

		this.sFrame.lstUser.clear();
		for (int i=0;i<u.size();i++)
		{
			String User=(String)u.elementAt(i);
			this.sFrame.lstUser.addItem(User);
		}
		Integer a=new Integer(u.size());
		this.sFrame.lblUserCount.setText("����������"+a.toString()+"��");
		this.sFrame.txtNumber.setText(a.toString()+"��");
		//this.sFrame.txtMax.setText(a.toString()+"��");
    }
	
	public static void fail(Exception e,String str)
	{
		System.out.println(str+" ��"+e);
	}
	
	//�����߳�
	class Connection extends Thread
	{
		
		protected Socket netClient;
		
		Vector userOnline;
		Vector userChat;
		
		protected ObjectInputStream fromClient;  //�ӿͻ���������
		protected PrintStream toClient; //�����ͻ���
		public Vector  vList = new Vector();
		
		Object obj;
		
		public Connection(Socket client,Vector u,Vector c)
		{
			netClient = client;
			userOnline=u;
			userChat=c;
			
			try
			{
				//����˫��ͨ��
				                                   //�����ͻ�����
				fromClient = new ObjectInputStream(netClient.getInputStream());
							
				                                   //������д���ͻ�
				toClient = new PrintStream(netClient.getOutputStream());
			}
			catch(IOException e)
			{
				try
				{
					netClient.close();
				}
				catch(IOException e1)
				{
					String msg="���ܽ�����"+e1+"\n";			      	           			      	        
		      	    apppendMsg(msg);
					return;
				}			
			}
			this.start();
		}
		
		public void run()
		{
		 try
		{//obj��Object��Ķ���
		obj = (Object)fromClient.readObject();
				if(obj.getClass().getName().equals("Customer"))
				{
				    serverLogin();	
				}
				if(obj.getClass().getName().equals("Register_Customer"))
				{
				    serverRegiste();	
				}
			    if(obj.getClass().getName().equals("Message"))
			    {
			        serverMessage();
			    }
			    if(obj.getClass().getName().equals("Chat"))
			    {
			        serverChat();
			    }
			    if(obj.getClass().getName().equals("Exit"))
			    {
			    	System.out.println("��ʼ�Ƴ�");
			        serverExit();	
			    }	
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
			catch(ClassNotFoundException e1)
			{
				String msg="�����������󣨽���������ǲ�����ֵģ�"+"\n";			      	           			      	        
	      	    apppendMsg(msg);
			}
			finally
			{
				try
				{
					netClient.close();
				}
				catch(IOException e)
				{
					System.out.println(e);
				}
			}
		}
		
		//��¼����
		public void serverLogin()
		{
			String temp_message;
		    try
		    {
		    Customer clientMessage2 = (Customer)obj;
		    			    	
		            //���ļ�
	            FileInputStream file3 =new FileInputStream("./user.txt");
			    ObjectInputStream objInput1 = new ObjectInputStream(file3);
			    vList=(Vector)objInput1.readObject(); 
				    	
			    int find=0;  //�����жϱ�־
			    System.out.println(find);
			    Integer loginnum=new Integer(u.size());//�Ƿ���Ա
			    if(loginnum>49) 
			    {
			    	toClient.println("�������Ѿ���Ա");
		      	    String msg="��������"+"\n";			      	           			      	        
			      	apppendMsg(msg);
			    }
			    else
			    {
			    	for(int i=0;i<vList.size();i++)
			    {     
			       Register_Customer reg=(Register_Customer)vList.elementAt(i);
			          
			       if ( reg.custName.equals(clientMessage2.custName) )
			       {
			           find=1; 
			           if( !reg.custPassword.equals(clientMessage2.custPassword) )
			           {
			      	        toClient.println("���벻��ȷ");
			      	        String msg="������������"+"\n";			      	           			      	        
				      	     apppendMsg(msg);
			      	        break;
			           }
			           else
			           {
			      	        //�ж��Ƿ��Ѿ���¼
			      	        int login_flag=0;
			      	        for(int a=0;a<userOnline.size();a++)
			      	        {
			      	            if(	clientMessage2.custName.equals(userOnline.elementAt(a)))
			      	            {
			      	            	login_flag=1;
			      	            	break;
			      	            }
			      	        }
			      	        
			      	        if (login_flag==0)
			      	        {
			      	            userOnline.addElement(clientMessage2.custName);  //�����û�������
			      	            toClient.println("��¼�ɹ�");
			      	            Date t=new Date();			      	  
			      	            String msg="�û�"+clientMessage2.custName+"��¼�ɹ���"+
	   	                               "��¼ʱ��:"+t.toLocaleString()+"\n";			      	           			      	        
			      	            apppendMsg(msg);
			      	            lstup();
			       	            break;
			       	        }
			       	        else
			       	        {
			       	            toClient.println("���û��ѵ�¼");
			       	            String msg="���û��ѵ�¼"+"\n";			      	           			      	        
			       	            apppendMsg(msg);
			       	        }
			           } 
			       }
			       else
			       {
			           continue;	
			       }    
			    }
			    if (find == 0)
			    {
			  	    toClient.println("û������û�������ע��");
			  	    String msg="�������û���½����"+"\n";			      	           			      	        
	      	        apppendMsg(msg);
		        }
			    }
			    
		        
		        file3.close();
			    objInput1.close();
			    fromClient.close();	
		    }
		    catch(ClassNotFoundException e)
	  		{
	  			System.out.println(e);
	  		}
	  		catch(IOException e)
	  		{
	  			System.out.println(e);
	  		}
		    
		}
		
		//ע�ᴦ��
	     public void serverRegiste()
	    {
	     try
	     {
	       	int flag=0;  //�Ƿ������жϱ�־
			Register_Customer clientMessage =(Register_Customer)obj;
	       	File fList=new File("./user.txt");
	      	if(fList.length()!= 0)//�ж����ݿ�Ϊ���Ƿ��ǵ�һ��ע���û�
	      	{
	        	ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(fList));
				vList=(Vector)objInput.readObject(); 
				//�ж��Ƿ�������
				for(int i=0;i<vList.size();i++)//�ļ��������
				{
		 			Register_Customer reg=(Register_Customer)vList.elementAt(i);
	     			if(reg.custName.equals(clientMessage.custName))
	         		{
	          			toClient.println("ע�����ظ�,������ѡ��");
	          			String msg="���û�ע���ظ��û�"+"\n";			      	           			      	        
		      	        apppendMsg(msg);
	           			flag=1;
	             		break;
	          		}
				}
	       }
	      if (flag==0)
	      {
		vList.addElement(clientMessage);
		//д���ļ�
		FileOutputStream file = new FileOutputStream(fList);
		ObjectOutputStream objout = new ObjectOutputStream(file);
		objout.writeObject(vList);
		     
		//����ע��ɹ���Ϣ
			        toClient.println(clientMessage.custName+"ע��ɹ�");
			        Date t=new Date();			        
			        String msg="�û�"+clientMessage.custName+"ע��ɹ�, "+
	                           "ע��ʱ��:"+t.toLocaleString()+"\n";			      	           			      	        
	      	        apppendMsg(msg);
					    
			        file.close();
			        objout.close();
			        fromClient.close();
			    }
		    }
		    catch(ClassNotFoundException e)
	  		{
	  			System.out.println(e);
	  		}
	  		catch(IOException e)
	  		{
	  			System.out.println(e);
	  		}
	    }
	    
	    //������Ϣ����
	    public void serverMessage()
	    {
	        try
	        {
	        	Message mess=new Message();
	            mess.userOnLine=userOnline;
	            mess.chat=userChat;
	        
	            ObjectOutputStream outputstream=new ObjectOutputStream(netClient.getOutputStream());
	            outputstream.writeObject((Message)mess);
	            
	            netClient.close();
	            outputstream.close();
	        }    
	        catch(IOException e)
	  	  	{
	  	  	}
	        
	    }
	    
	    //������Ϣ����
		public void serverChat()
	  	{
	  		//�����յ��Ķ���ֵ����������Ϣ�����л�����
	  		Chat cObj = new Chat();
	  		cObj = (Chat)obj;
	  		//��������Ϣ�����л�������ӵ�����������Ϣ��ʸ����
	  		userChat.addElement((Chat)cObj);
	  		
	  		return;
	  	}	   
	  	
	  	/**********�û��˳�����**********/
	  	public void serverExit()
	  	{
	  		Exit  exit1=new Exit();
	  		System.out.println("1");
	  		exit1=(Exit)obj;	  		  		
	  		userOnline.removeElement(exit1.exitname);  //�����û�ɾ��
	  		Date t=new Date();
	  		String msg="�û�"+exit1.exitname+"�Ѿ��˳�, "+
	                   "�˳�ʱ��:"+t.toLocaleString()+"\n";			      	           			      	        
	        apppendMsg(msg);
	        lstup();
	  	}
	}
	//�����ͻ�������
	public void run()
	{
		String temp = "";
		try
		{
			while(true)
			{
				//���������ܿͻ�������
				Socket client = serverSocket.accept();
			    Connection con = new Connection(client,u,v);   //֧�ֶ��߳�
			    
			}
		}
		catch(IOException e)
		{
			fail(e,"���ܼ�����");
		}
    }
    
    
    //����������
    public static void main(String args[])
    {
    	new AppServer();
    }
}


