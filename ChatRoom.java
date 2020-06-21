import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
	
public class ChatRoom extends Thread implements ActionListener
{
	static JFrame frmChat;
	JPanel  pnlChat;
	JButton  btnCls,btnExit,btnSend,btnClear,btnSave,btnTimer,btnSendFile,btnBrowse;
	JLabel  lblUserList,lblUserMessage,lblSendMessage,lblChatUser;
	JLabel  lblUserTotal,lblCount,lblBack,lblFile;
	JTextField txtMessage,txtFile;
	java.awt.List  lstUserList;
	TextArea  taUserMessage;
	JComboBox  cmbUser;
	JCheckBox  chPrivateChat;
	String  strServerIp,strLoginName;
	Thread  thread;
	JMenuBar mbChat;
	JMenu mnuSystem,mnuHelp;
	JMenuItem mnuiCls,mnuiSave,mnuiClock,mnuiExit,mnuiContent,mnuiIndex,mnuiAbout;
	
    //���ڽ��������ڶ�λ
	Dimension scrnsize;
	Toolkit toolkit=Toolkit.getDefaultToolkit();
	//���췽��			
	public ChatRoom(String name,String ip)
	{
	    strServerIp=ip;
	    strLoginName=name;
  		frmChat=new JFrame("������"+"[�û�:"+name+"]");
	    pnlChat=new JPanel(); 
	    frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frmChat.getContentPane().add(pnlChat);

   	    Font fntDisp1=new Font("����",Font.PLAIN,12);
		
		frmChat.setJMenuBar(mbChat);
	    
	    String list[]={"������"};  
	    btnCls=new JButton("����");
	    btnExit=new JButton("�˳�");
	    btnSend=new JButton("����");
	    btnSave=new JButton("����");
	    lblUserList=new JLabel("�������ҳ�Ա��");
	    lblUserMessage=new JLabel("��������Ϣ��");
	    lblSendMessage=new JLabel("��������:");
	    lblChatUser=new JLabel("���:"); 
	    lblUserTotal=new JLabel("��������:");
	    lblCount=new JLabel("0");
	    txtFile=new JTextField(20);
	    lstUserList=new java.awt.List();
	    txtMessage=new JTextField(170);
	    cmbUser         =new JComboBox(list);
	    taUserMessage=new TextArea("",300,200,TextArea.SCROLLBARS_VERTICAL_ONLY);
	    taUserMessage.setEditable(false);   
	    	        
	    pnlChat.setLayout(null);
		pnlChat.setBackground(new Color(127,255,170));
		btnSave.setBounds(300,330,80,25);
	    btnCls.setBounds(400,330,80,25);
	    btnExit.setBounds(500,330,80,25);
	    btnSend.setBounds(500,300,80,25);
	    
	    lblUserList.setBounds(380,0,120,40);
   	    lblUserTotal.setBounds(505,0,60,40);
	    lblCount.setBounds(565,0,60,40);
	    lblUserMessage.setBounds(5,0,180,40);
	    lblChatUser.setBounds(10,290,40,40);
	    lblSendMessage.setBounds(210,290,60,40);

	    lstUserList.setBounds(380,40,210,250);
	    taUserMessage.setBounds(5,40,360,250);
	    txtMessage.setBounds(270,300,210,25);
	    cmbUser.setBounds(50,300,80,25);
	    
		btnCls.setFont(fntDisp1);
		btnExit.setFont(fntDisp1);
		btnSend.setFont(fntDisp1);
		btnSave.setFont(fntDisp1);
		lblUserList.setFont(fntDisp1);
		lblUserMessage.setFont(fntDisp1);
		lblChatUser.setFont(fntDisp1);
		lblSendMessage.setFont(fntDisp1);
		lblUserTotal.setFont(fntDisp1);
		lblCount.setFont(fntDisp1);
		txtFile.setFont(fntDisp1);
		cmbUser.setFont(fntDisp1);
		taUserMessage.setFont(new Font("����",Font.PLAIN,12));
		
		lblUserList.setForeground(Color.BLACK);
		lblUserMessage.setForeground(Color.BLACK);
		lblSendMessage.setForeground(Color.black);
		lblChatUser.setForeground(Color.black);
		lblSendMessage.setForeground(Color.black);
		lblUserTotal.setForeground(Color.BLACK);
		lblCount.setForeground(Color.BLACK);
		cmbUser.setForeground(Color.BLACK);
		lstUserList.setBackground(Color.white);
		taUserMessage.setBackground(Color.white);
		btnCls.setBackground(Color.white);
	    btnExit.setBackground(Color.white);
	    btnSend.setBackground(Color.white);
	    btnSave.setBackground(Color.white);
		
		pnlChat.add(btnCls);
	    pnlChat.add(btnExit);
	    pnlChat.add(btnSend);
	    pnlChat.add(btnSave);
	    pnlChat.add(lblUserList);
	    pnlChat.add(lblUserMessage);
	    pnlChat.add(lblSendMessage);
	    pnlChat.add(lblChatUser);
	    pnlChat.add(lblUserTotal);
	    pnlChat.add(lblCount);
	    pnlChat.add(lstUserList);
	    pnlChat.add(taUserMessage);
	    pnlChat.add(txtMessage);
	    pnlChat.add(cmbUser);
	    
	    frmChat.addWindowListener(new Windowclose());
	    btnCls.addActionListener(this);
	    btnExit.addActionListener(this);
	    btnSend.addActionListener(this);
	    btnSave.addActionListener(this);
	    lstUserList.addActionListener(this);
	    txtMessage.addActionListener(this);
	   
	    
	    //��������ҳ����Ϣˢ���߳�
		Thread thread = new Thread(this);
      	thread.start();
	    		
	    frmChat.setSize(600,400);
	    frmChat.setVisible(true);
	    frmChat.setResizable(false);
	    
	    //�����ڶ�λ����Ļ����
    	scrnsize=toolkit.getScreenSize();
    	frmChat.setLocation(scrnsize.width/2-frmChat.getWidth()/2,
    	                 scrnsize.height/2-frmChat.getHeight()/2);
    	Image img=toolkit.getImage("C:\\Users\\Leonard\\Desktop\\Java-Chat-master\\Java-Chat\\Happychat\\images\\appico.jpg");
        frmChat.setIconImage(img);
	
	}  
	
	public void run()
	{   
	    int intMessageCounter=0;
	    int intUserTotal=0;
	    boolean isFirstLogin=true; //�ж��Ƿ�յ�½
	    boolean isFound;      //�Ƿ����
	    Vector user_exit=new Vector();
	    try
	    {
			for(;;)
			{
	         	Socket toServer;
		 		toServer=new Socket(strServerIp,1001);
				//����Ϣ����������
	     		Message messobj=new Message();
	    		ObjectOutputStream streamtoserver=new ObjectOutputStream(toServer.getOutputStream());
			    streamtoserver.writeObject((Message)messobj);
			  	//�����Է���������Ϣ
	    		ObjectInputStream streamfromserver=new ObjectInputStream(toServer.getInputStream());
			    messobj=(Message)streamfromserver.readObject();
			    
	 			//ˢ��������Ϣ
	 			if (isFirstLogin)   //����յ�½
				{
				 	intMessageCounter=messobj.chat.size();   //���θ��û���½ǰ����������
				 	isFirstLogin=false;
				}
				for (int i=intMessageCounter;i<messobj.chat.size();i++)
				{
   					Chat temp=(Chat)messobj.chat.elementAt(i);
    				String temp_message;
   					if (temp.chatUser.equals(strLoginName))
   					{
		 	        	if(temp.chatToUser.equals(strLoginName))
			 	        {
			 	     	    temp_message="Ŀ����������Լ���"+"\n";
			 	        }
			 	        else
			 	        {
			 	        	temp_message="���㡿�ԡ�"+temp.chatToUser+"��˵��"+temp.chatMessage+"\n";	
			 	        }	
			 	    }
			 	    else
			 	    {
			 	        if(temp.chatToUser.equals(strLoginName))
			 	        {
			 	        	temp_message="��"+temp.chatUser+"���ԡ��㡿˵��"+temp.chatMessage+"\n";
			 	        }
			 	        else
			 	        {
			 	            if (!temp.chatUser.equals(temp.chatToUser))  //�Է�û����������
			 	            {
			 	          	    if (temp.chatToUser.equals("������"))   //�������Ļ�
			 	          	    {
			 	          	        temp_message="��"+temp.chatUser+"���ԡ�"+temp.chatToUser+"��˵��"+temp.chatMessage+"\n";
			 	           	    }
			 	           	    else
			 	           	    {
			 	           	    	temp_message="";
			 	           	    }
			 	            }
			 	            else
			 	            {
			 	          	    temp_message="";
			 	            }
			 	         }
			 	     }
			 	     taUserMessage.append(temp_message);
			 	     intMessageCounter++;
			    } 
			   			    
				//ˢ��
				lstUserList.clear();
				for (int i=0;i<messobj.userOnLine.size();i++)
				{
	 				String User=(String)messobj.userOnLine.elementAt(i);
					lstUserList.addItem(User);
   				}
    			Integer a=new Integer(messobj.userOnLine.size());
				lblCount.setText(a.toString());
				//��ʾ�û����������ҵ���Ϣ
				if(messobj.userOnLine.size()>intUserTotal)
				{
	    			String tempstr=messobj.userOnLine.elementAt(messobj.userOnLine.size()-1).toString();
	    			if(!tempstr.equals(strLoginName))
					{
						taUserMessage.append("��"+tempstr+"������"+"\n");
					}
				}
   				//��ʾ�û��뿪�����ҵ���Ϣ
   				if(messobj.userOnLine.size()<intUserTotal)
   				{
   	    			for(int b=0;b<user_exit.size();b++)
   	    			{
   	        			isFound=false;
   	        			for(int c=0;c<messobj.userOnLine.size();c++)
   	        			{	
   	            			if(user_exit.elementAt(b).equals(messobj.userOnLine.elementAt(c)))
   	            			{
								isFound=true;
			   					break;
			   				}
   		    			} 
	    				if(!isFound)  //û�з��ָ��û�
						{
		   					if(!user_exit.elementAt(b).equals(strLoginName))
		    				{
		        				taUserMessage.append("��"+user_exit.elementAt(b)+"������"+"\n");
		    				}
						}
					}	
				}
				user_exit=messobj.userOnLine;			   	
				intUserTotal=messobj.userOnLine.size();
    			streamtoserver.close();
    			streamfromserver.close();
    			toServer.close();
    			thread.sleep(1000);
			}
		    
		}
		catch (Exception e)
		{
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null,"�������ӷ�������");
			System.out.println(e);
		}
	
	}  //run()����
	
	//������ť��Ӧ/
    public void actionPerformed(ActionEvent ae)
    {
    	Object source=(Object)ae.getSource();
    	if (source.equals(btnTimer))
    	{
    		new Clock();
    	}
    	if(source.equals(btnCls))
    	{
    	    clearMessage();
    	}
    	if(source.equals(btnExit))
    	{
    	    exit();
    	}
    	if(source.equals(btnSend))
    	{
    		sendMessage();
    	}
    	if(source.equals(btnSave))
    	{
    	    saveMessage();	
    	}
    	if(source.equals(lstUserList))  //˫���б��
    	{
    		changeUser();
    	}
    } // actionPerformed()����
    
    
    //�������ڹر���Ӧ/
    class Windowclose extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
        	exit();
        }
    }
    
    //����
    public void  clearMessage()
    {
        taUserMessage.setText("");	
    }
    
    //�˳�
    public void exit()
    {
        Exit exit=new Exit();
        exit.exitname=strLoginName;
        //System.out.println("1");
        //�����˳���Ϣ
        try
        {
            Socket toServer=new Socket(strServerIp,1001);
            //�������������Ϣ
            //System.out.println("1");
            ObjectOutputStream outObj=new ObjectOutputStream(toServer.getOutputStream());
            outObj.writeObject(exit);
            outObj.close();
    		toServer.close();
    		frmChat.dispose();
        }
        catch(Exception e)
        {
        }
    
    }
    
    //����
    public void sendMessage()
    {
    	Chat chatobj=new Chat();
    	chatobj.chatUser=strLoginName;
    	chatobj.chatMessage=txtMessage.getText();
    	chatobj.chatToUser=String.valueOf(cmbUser.getSelectedItem());
    	try
    	{
    		Socket toServer=new Socket(strServerIp,1001);
    		ObjectOutputStream outObj=new ObjectOutputStream(toServer.getOutputStream());
    		outObj.writeObject(chatobj);
    	    txtMessage.setText("");   //����ı���
    	    //JOptionPane.showMessageDialog(null,"���ͳɹ�");//�˴�Ϊ���ͳɹ�������ʵ��ʹ���в�����
    	    outObj.close();
    		toServer.close();
    	}
    	catch(Exception e)
    	{
    		JOptionPane.showMessageDialog(null,e);//������ͳ����򵯳�����ԭ��
    	}
    } 
    
    //����
    public void saveMessage()
    {
    	try
    	{
    		FileOutputStream  fileoutput=new FileOutputStream("C:\\Users\\Leonard\\Desktop\\Java-Chat-master\\Java-Chat\\chatroom\\message.txt",true);
    	    String temp=taUserMessage.getText();
    	    System.out.println(temp);
    	    fileoutput.write(temp.getBytes());
    	    fileoutput.close();
        }
        catch(Exception e)
        {
        	System.out.println(e);
        }
        
    }
    
    //�ı�˽�Ŀ�
  	public void changeUser()
	{
		boolean key = true;
		String selected = lstUserList.getSelectedItem();
		
		for(int i = 0; i < cmbUser.getItemCount(); i++)
		{
		   if(selected.equals(cmbUser.getItemAt(i)))
	       { 
			   key = false;
		       break;
		   }
		}
		if(key == true)
		{
		   cmbUser.insertItemAt(selected,0);
		}
		cmbUser.setSelectedItem(selected);
    
    }
    
    public static void main(String args[])
    {
    	new ChatRoom("�����û�","127.0.0.1");
    }
    	
}