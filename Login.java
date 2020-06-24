import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class  Login extends JFrame  implements ActionListener
{
	JPanel  pnlLogin;
 	JButton  btnLogin,btnRegister,btnExit;
	JLabel  lblServer,lblUserName,lblPassword,lblLogo;
	JTextField  txtUserName,txtServer;
	JPasswordField pwdPassword;
	String  strServerIp;
   //用于将窗口定位
	Dimension scrnsize;
	Toolkit toolkit=Toolkit.getDefaultToolkit();
	//构造方法           	   
	public Login()
	{
		super("登录龙哥聊天室");
		pnlLogin=new JPanel();
		this.getContentPane().add(pnlLogin);
		
		lblServer=new JLabel("服务器:");
		lblUserName=new JLabel("用户名:");
		lblPassword=new JLabel("密  码:");
		txtServer=new JTextField(20);
		txtServer.setText("127.0.0.1");
		txtUserName=new JTextField(20);
		pwdPassword=new JPasswordField(20);
		btnLogin=new JButton("登录");
		btnLogin.setToolTipText("登录到服务器");
		btnRegister=new JButton("注册");
		btnRegister.setToolTipText("注册新用户");
		btnExit=new JButton("退出");
		btnExit.setToolTipText("退出系统");
		
		pnlLogin.setLayout(null);  
		pnlLogin.setBackground(new Color(127,255,170));
		
		lblServer.setBounds(50,100,100,30);
		txtServer.setBounds(150,100,120,25);
		lblUserName.setBounds(50,130,100,30);
		txtUserName.setBounds(150,130,120,25);
		lblPassword.setBounds(50,160,100,30);
		pwdPassword.setBounds(150,160,120,25);	
		btnLogin.setBounds(50,200,80,25);
		btnRegister.setBounds(130,200,80,25);
		btnExit.setBounds(210,200,80,25);
		
		Font fontstr=new Font("宋体",Font.PLAIN,12);
		lblServer.setFont(fontstr);
		txtServer.setFont(fontstr);
		lblUserName.setFont(fontstr);
		txtUserName.setFont(fontstr);
		lblPassword.setFont(fontstr);
		pwdPassword.setFont(fontstr);
		btnLogin.setFont(fontstr);
		btnRegister.setFont(fontstr);
		btnExit.setFont(fontstr);
		
		lblUserName.setForeground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.WHITE);
		btnRegister.setBackground(Color.WHITE);
		btnExit.setBackground(Color.WHITE);
		
		pnlLogin.add(lblServer);
		pnlLogin.add(txtServer);
		pnlLogin.add(lblUserName);
		pnlLogin.add(txtUserName);
		pnlLogin.add(lblPassword);
		pnlLogin.add(pwdPassword);		
		pnlLogin.add(btnLogin);
		pnlLogin.add(btnRegister);
		pnlLogin.add(btnExit);
		
		//设置背景图片
		Icon logo1 = new ImageIcon("./images/loginlogo.jpg");
	 	lblLogo = new JLabel(logo1);
		lblLogo.setBounds(0,0,340,66);
		pnlLogin.add(lblLogo);
        //设置登录窗口
        setResizable(false);
		setSize(340,260);
		setVisible(true);
	    scrnsize=toolkit.getScreenSize();
    	setLocation(scrnsize.width/2-this.getWidth()/2,
    	                 scrnsize.height/2-this.getHeight()/2);
    	Image img=toolkit.getImage("./images/appico.jpg");
        setIconImage(img);
			
		//三个按钮注册监听
		btnLogin  .addActionListener(this);
		btnRegister.addActionListener(this);
		btnExit   .addActionListener(this);
		
	}  //构造方法结束
	
		
	//按钮监听响应
	public void actionPerformed(ActionEvent ae)
	{
		Object source=ae.getSource();
		if (source.equals(btnLogin))
		{
		    //判断用户名和密码是否为空
	    	if(txtUserName.getText().equals("") || pwdPassword.getText().equals(""))
		    {
			    JOptionPane op1=new JOptionPane();
                op1.showMessageDialog(null,"用户名或密码不能为空");
            }
            else
		    {
			    strServerIp=txtServer.getText();
			    login();
		    }
		}
		if (source.equals(btnRegister))
		{
		    strServerIp=txtServer.getText();
		    this.dispose();
		    new Register(strServerIp);
		}
		if (source == btnExit)
		{
		    System.exit(0);
		}
	}  
		
	//登录事件响应方法
	public void login()
	{
	    //接受客户的详细资料
        Customer data=new Customer();
	   	data.custName=txtUserName.getText();
		data.custPassword = pwdPassword.getText();
		try
		{
			//连接到服务器
		   	Socket toServer;
  		   	toServer = new Socket(strServerIp,1001);
		   	ObjectOutputStream streamToServer=new ObjectOutputStream (toServer.getOutputStream());					
		   	//写客户详细资料到服务器socket
		   	streamToServer.writeObject((Customer)data);           
           	//读来自服务器socket的登录状态
           	BufferedReader fromServer=new BufferedReader(new InputStreamReader(toServer.getInputStream()));
           	String status=fromServer.readLine();
           	if (status.equals("登录成功"))
           	{
				new ChatRoom((String)data.custName,strServerIp);
           	    this.dispose();
           	    //关闭流对象
		        streamToServer.close();
                fromServer.close();
                toServer.close();
           	}
           	else
           	{
           	    JOptionPane.showMessageDialog(null,status);
               //关闭流对象
		        streamToServer.close();
                fromServer.close();
                toServer.close();
           	}
         }
         catch(ConnectException e1)
         {
         	JOptionPane.showMessageDialog(null,"未能建立到指定服务器的连接!");
         }
		 catch(InvalidClassException e2)
		 {
		    JOptionPane.showMessageDialog(null,"类错误!");
		 }
		 catch(NotSerializableException e3)
		 {
			JOptionPane.showMessageDialog(null,"对象未序列化!");
		 }
		 catch(IOException e4)
		 {
		 	JOptionPane.showMessageDialog(null,"不能写入到指定服务器!");
		 }
	}  
	
	public static void main(String args[])
	{
		new Login();
	}
	
}  