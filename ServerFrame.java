import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
//服务器窗口类
public class ServerFrame extends JFrame implements ActionListener
{
	//服务器信息面板
	JPanel pnlServer,pnlServerInfo;
	JLabel lblStatus,lblNumber,lblMax,lblServerName,lblProtocol,lblIP,lblPort,lblLog;
	JTextField txtStatus,txtNumber,txtMax,txtServerName,txtProtocol,txtIP,txtPort;
	JButton btnStop,btnSaveLog;
	TextArea taLog;
	java.awt.List  lstUser;
	JTabbedPane tpServer;
	TextArea  taMessage;
	//用户信息面板
	JPanel pnlUser;
	JLabel lblMessage,lblUser,lbltoUser,lblNotice,lblUserCount;
	JScrollPane spUser;
	JTextField txtNotice,txttoUser;
	JButton btnSend,btnKick;

	public 	ServerFrame()
	{
	 	//服务器窗口
	 	super("聊天服务器");
	  	setSize(550,530);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);    
        Dimension scr=Toolkit.getDefaultToolkit().getScreenSize();//在屏幕居中显示
        Dimension fra=this.getSize();
        if(fra.width>scr.width)
        {
            fra.width=scr.width;
        }
        if(fra.height>scr.height)
        {
            fra.height=scr.height;
        }
        this.setLocation((scr.width-fra.width)/2,(scr.height-fra.height)/2);
                 
    
//==========服务器信息面板=========================
	 	pnlServer=new JPanel();
	 	pnlServer.setLayout(null);
	 	pnlServer.setBackground(new Color(127,255,170));
	 	
	 	pnlServerInfo=new JPanel(new GridLayout(14,1));
        pnlServerInfo.setBackground(new Color(127,255,170));
        pnlServerInfo.setFont(new Font("宋体",0,12));
        pnlServerInfo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),BorderFactory.createEmptyBorder(1,1,1,1)));
         
        lblStatus=new JLabel("当前状态:");
        lblStatus.setForeground(Color.BLACK);
        lblStatus.setFont(new Font("宋体",0,12));
        txtStatus=new JTextField("正常",10);
        txtStatus.setBackground(Color.decode("#F0FFFF"));
        txtStatus.setFont(new Font("宋体",0,12));
        txtStatus.setEditable(false);
         
        lblNumber=new JLabel("当前在线人数:");
        lblNumber.setForeground(Color.BLACK);
        lblNumber.setFont(new Font("宋体",0,12));
        txtNumber=new JTextField("0 人",10);
        txtNumber.setBackground(Color.decode("#F0FFFF"));
        txtNumber.setFont(new Font("宋体",0,12));
        txtNumber.setEditable(false);
         
        lblMax=new JLabel("最多在线人数:");
        lblMax.setForeground(Color.BLACK);
        lblMax.setFont(new Font("宋体",0,12));
        txtMax=new JTextField("50 人",10);
        txtMax.setBackground(Color.decode("#F0FFFF"));
        txtMax.setFont(new Font("宋体",0,12));
        txtMax.setEditable(false);
         
        lblServerName=new JLabel("服务器名称:");
        lblServerName.setForeground(Color.BLACK);
        lblServerName.setFont(new Font("宋体",0,12));
        txtServerName=new JTextField("龙清扬专属",10);
        txtServerName.setBackground(Color.decode("#F0FFFF"));
        txtServerName.setFont(new Font("宋体",0,12));
        txtServerName.setEditable(false);
         
        lblProtocol=new JLabel("访问协议:");
        lblProtocol.setForeground(Color.BLACK);
        lblProtocol.setFont(new Font("宋体",0,12));
        txtProtocol=new JTextField("HTTP",10);
        txtProtocol.setBackground(Color.decode("#F0FFFF"));
        txtProtocol.setFont(new Font("宋体",0,12));
        txtProtocol.setEditable(false);
         
        lblIP=new JLabel("服务器IP:");
        lblIP.setForeground(Color.BLACK);
        lblIP.setFont(new Font("宋体",0,12));
        txtIP=new JTextField(10);
        txtIP.setBackground(Color.decode("#F0FFFF"));
        txtIP.setFont(new Font("宋体",0,12));
        txtIP.setEditable(false);
         
        lblPort=new JLabel("服务器端口:");
        lblPort.setForeground(Color.BLACK);
        lblPort.setFont(new Font("宋体",0,12));
        txtPort=new JTextField("8000",10);
        txtPort.setBackground(Color.decode("#F0FFFF"));
        txtPort.setFont(new Font("宋体",0,12));
        txtPort.setEditable(false);
         
        btnStop=new JButton("关闭服务器(C)");
        btnStop.setBackground(Color.ORANGE);
        btnStop.setFont(new Font("宋体",0,12));
        
        lblLog=new JLabel("[服务器日志]");
        lblLog.setForeground(Color.BLACK);
        lblLog.setFont(new Font("宋体",0,12));
         
        taLog=new TextArea(20,50);
        taLog.setFont(new Font("宋体",0,12));
        btnSaveLog=new JButton("保存日志(S)");
        btnSaveLog.setBackground(Color.ORANGE);
        btnSaveLog.setFont(new Font("宋体",0,12));
	 	
	    pnlServerInfo.add(lblStatus);
        pnlServerInfo.add(txtStatus);        
        pnlServerInfo.add(lblNumber);
        pnlServerInfo.add(txtNumber);
        pnlServerInfo.add(lblMax);
        pnlServerInfo.add(txtMax);
        pnlServerInfo.add(lblServerName);
        pnlServerInfo.add(txtServerName);
        pnlServerInfo.add(lblProtocol);
        pnlServerInfo.add(txtProtocol);
        pnlServerInfo.add(lblIP);
        pnlServerInfo.add(txtIP);
        pnlServerInfo.add(lblPort);
        pnlServerInfo.add(txtPort);
         
        pnlServerInfo.setBounds(380,5,395,400);
        lblLog.setBounds(5,5,300,30);
        taLog.setBounds(5,35,375,370);
        btnStop.setBounds(50,410,120,30);
        btnSaveLog.setBounds(200,410,120,30);
        pnlServer.add(pnlServerInfo); 
        pnlServer.add(lblLog);
        pnlServer.add(taLog);
        pnlServer.add(btnStop);
        pnlServer.add(btnSaveLog);
       //在线用户面板      
        pnlUser=new JPanel();
        pnlUser.setLayout(null);
        pnlUser.setBackground(new Color(127,255,170));
        pnlUser.setFont(new Font("宋体",0,12));
        lblMessage=new JLabel("【用户消息】"); 
        lblMessage.setFont(new Font("宋体",0,12));
        lblMessage.setForeground(Color.BLACK);
        taMessage=new TextArea(20,20);
        taMessage.setFont(new Font("宋体",0,12));
        lblNotice=new JLabel("通知：");
        lblNotice.setFont(new Font("宋体",0,12));
        txtNotice=new JTextField (20);
        txtNotice.setFont(new Font("宋体",0,12));
        lbltoUser=new JLabel("到：");
        lbltoUser.setFont(new Font("宋体",0,12));
        txttoUser=new JTextField (20);
        txttoUser.setFont(new Font("宋体",0,12));
        btnSend=new JButton("发送");
        btnSend.setBackground(Color.ORANGE);
        btnSend.setFont(new Font("宋体",0,12));
         
        lblUserCount=new JLabel("在线总人数 0 人");
        lblUserCount.setFont(new Font("宋体",0,12));
		 
        lblUser=new JLabel("[在线用户列表]"); 
        lblUser.setFont(new Font("宋体",0,12));
        lblUser.setForeground(Color.BLACK);
        
        lstUser=new java.awt.List();
        lstUser.setFont(new Font("宋体",0,12));
         
        spUser=new JScrollPane();
        spUser.setBackground(Color.decode("#d6f4f2"));
        spUser.setFont(new Font("宋体",0,12));
		spUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spUser.getViewport().setView(lstUser);
		
		lblMessage.setBounds(5,5,100,25);
		taMessage.setBounds(5,35,300,360);
		lblUser.setBounds(310,5,100,25);
		spUser.setBounds(310,35,220,360);
		lblNotice.setBounds(5,410,40,25);
		txtNotice.setBounds(50,410,160,25);
		lbltoUser.setBounds(5,440,40,25);
		txttoUser.setBounds(50,440,160,25);
		btnSend.setBounds(210,440,80,25);
		lblUserCount.setBounds(320,410,100,25);
		
        pnlUser.add(lblMessage);
        pnlUser.add(taMessage);
        pnlUser.add(lblUser);
        pnlUser.add(spUser);
        pnlUser.add(lblNotice);
        pnlUser.add(txtNotice);
        pnlUser.add(lbltoUser);
        pnlUser.add(txttoUser);
        pnlUser.add(btnSend);
        pnlUser.add(lblUserCount);
        
        btnSend.addActionListener(this);
        
//主标签面板

        tpServer=new JTabbedPane(JTabbedPane.TOP);
        tpServer.setBackground(Color.decode("#d6f4f2"));
        tpServer.setFont(new Font("宋体",0,12));
        tpServer.add("服务器管理/日志书写",pnlServer);
        tpServer.add("用户信息管理",pnlUser);
        this.getContentPane().add(tpServer);
        setVisible(true);
        
	}
	public void sendMessage()
    {
    	Chat chatobj=new Chat();
    	chatobj.chatUser="管理员";
    	chatobj.chatMessage=this.txtNotice.getText();
    	chatobj.chatToUser=this.txttoUser.getText();
       	//向服务器发送信息
    	try
    	{
    		Socket toServer=new Socket("127.0.0.1",1001);
    		//System.out.println("1");
    		ObjectOutputStream outObj=new ObjectOutputStream(toServer.getOutputStream());
    		//System.out.println("1");
    		outObj.writeObject(chatobj);
    		//System.out.println("1");
    		this.txtNotice.setText("");   //清空文本框
    	    outObj.close();
    		toServer.close();
    	}
    	catch(Exception e)
    	{
    		JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null,"服务器关闭");
			System.out.println(e);
    	}
    }
	public void actionPerformed(ActionEvent evt)
	{
		Object source=evt.getSource();
    	if (source.equals(btnSend))
    	{
    		//System.out.println("1\n");
    		sendMessage();
    	}
	}
	
	public static void main(String args[])
	{
		new ServerFrame();
	}
}