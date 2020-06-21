import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Register extends JFrame  implements ActionListener
{
	JPanel  pnlRegister;
	JLabel  lblUserName,lblGender,lblAge;
	JLabel  lblPassword,lblConfirmPass,lblEmail,logoPosition;
	JTextField  txtUserName,txtAge,txtEmail;
	JPasswordField  pwdUserPassword,pwdConfirmPass;
	JRadioButton  rbtnMale,rbtnFemale;
	ButtonGroup  btngGender;
    JButton  btnOk,btnCancel,btnClear;
	String  strServerIp;
    //用于将窗口用于定位
	Dimension scrnsize;
    Toolkit toolkit=Toolkit.getDefaultToolkit();
    //构造方法
	public Register(String ip)
	{
		super("龙哥聊天室注册窗口");
		strServerIp=ip;
		pnlRegister=new JPanel();
		this.getContentPane().add(pnlRegister);
	
		lblUserName=new JLabel("用 户 名:");
		lblPassword=new JLabel("密	码:");
		lblConfirmPass=new JLabel("确认密码:");
		txtUserName=new JTextField(30);
		txtEmail=new JTextField(30);
		txtAge=new JTextField(10);
		pwdUserPassword=new JPasswordField(30);
		pwdConfirmPass=new JPasswordField(30);
	    btngGender=new ButtonGroup();
	    btnOk=new JButton("确定");
	    btnOk.setMnemonic('O');
	    btnOk.setToolTipText("保存注册信息");
		btnCancel=new JButton("返回");
		btnCancel.setMnemonic('B');
		btnCancel.setToolTipText("返回登录窗口");
		btnClear=new JButton("清空");
		btnClear.setMnemonic('L');
		btnClear.setToolTipText("清空注册信息");
		
		pnlRegister.setLayout(null);    
		pnlRegister.setBackground(new Color(127,255,170));

		lblUserName.setBounds(30,80,100,30);
		txtUserName.setBounds(110,85,200,20);
		lblPassword.setBounds(30,105,100,30);
		pwdUserPassword.setBounds(110,110,200,20);
		lblConfirmPass.setBounds(30,130,100,30);
		pwdConfirmPass.setBounds(110,135,200,20);

	    btnOk.setBounds(30,170,80,25);	
	    btnCancel.setBounds(140,170,80,25);
	    btnClear.setBounds(250,170,80,25);
	
		Font fontstr=new Font("宋体",Font.PLAIN,12);	
		lblUserName.setFont(fontstr);
		lblPassword.setFont(fontstr);
		lblConfirmPass.setFont(fontstr);
		txtUserName.setFont(fontstr);
		btnOk.setFont(fontstr);
		btnCancel.setFont(fontstr);
		btnClear.setFont(fontstr);
						
		lblUserName.setForeground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);
		lblConfirmPass .setForeground(Color.BLACK);
		btnOk.setBackground(Color.WHITE);	
	    btnCancel.setBackground(Color.WHITE);
	    btnClear.setBackground(Color.WHITE);
		
		pnlRegister.add(lblUserName);
		pnlRegister.add(lblPassword);
		pnlRegister.add(lblConfirmPass);
		pnlRegister.add(txtUserName);
		pnlRegister.add(pwdUserPassword);
		pnlRegister.add(pwdConfirmPass);
		pnlRegister.add(btnOk);
		pnlRegister.add(btnCancel);
		pnlRegister.add(btnClear);
	    
	    //设置背景图片
	    Icon logo = new ImageIcon("C:\\Users\\Leonard\\Desktop\\Java-Chat-master\\Java-Chat\\chatroom\\images\\registerlogo.jpg");
	 	logoPosition = new JLabel(logo);
		logoPosition.setBounds(0, 0, 360,78);
		pnlRegister.add(logoPosition);
	    
	    this.setSize(360,240);
		this.setVisible(true);
		this.setResizable(false);
		//将窗口定位在屏幕中央
    	scrnsize=toolkit.getScreenSize();
    	this.setLocation(scrnsize.width/2-this.getWidth()/2,
    	                 scrnsize.height/2-this.getHeight()/2);
		Image img=toolkit.getImage("C:\\Users\\Leonard\\Desktop\\Java-Chat-master\\Java-Chat\\chatroom\\images\\appico.jpg");
        this.setIconImage(img);
		//三个按钮注册监听
		btnOk    .addActionListener(this);
		btnCancel.addActionListener(this);
		btnClear   .addActionListener(this);
	} 
	
	//监听
	public void actionPerformed(ActionEvent ae)
	{
		Object source=new Object();
	    source=ae.getSource();
	    if (source.equals(btnOk))      //确定
	    {
	        register();
	    }
	    if (source.equals(btnCancel))  //返回
	    {
	    	new Login();
	    	this.dispose();
	    }
	    if (source.equals(btnClear))     //清空
	    {
	        txtUserName.setText("");
	        pwdUserPassword.setText("");
	        pwdConfirmPass.setText("");
	        txtAge.setText("");
	        txtEmail.setText("");	
	    }
	} 
	
	//确定
	public void register()
	{
        Register_Customer data=new Register_Customer();
	    data.custName     = txtUserName.getText();
		data.custPassword = pwdUserPassword.getText();
		//验证用户名是否为空
		if(data.custName.length()==0)
		{
		    JOptionPane.showMessageDialog(null,"用户名不能为空");	
            return;	
		}
		//验证密码是否为空
		if(data.custPassword.length()==0)
		{
		    JOptionPane.showMessageDialog(null,"密码不能为空");	
            return;	
		}
		
		//验证密码的一致性
		if(!data.custPassword.equals(pwdConfirmPass.getText()))
		{
		    JOptionPane.showMessageDialog(null,"密码两次输入不一致，请重新输入");	
            return;
		}
		
		try
		{
		    //连接到服务器
		    Socket toServer;
  		    toServer = new Socket(strServerIp,1001);
		    ObjectOutputStream streamToServer=new ObjectOutputStream (toServer.getOutputStream());					
		    //写客户详细资料到服务器socket
		    streamToServer.writeObject((Register_Customer)data);
            //读来自服务器socket的登陆状态
            BufferedReader fromServer=new BufferedReader(new InputStreamReader(toServer.getInputStream()));
            String status=fromServer.readLine();
            //显示成功消息
            JOptionPane op=new JOptionPane();
            op.showMessageDialog(null,status);
            if(status.equals(data.custName+"注册成功"))
            {
                txtUserName.setText("");
                pwdUserPassword.setText("");
                pwdConfirmPass.setText("");
            }
            
            //关闭流对象
		    streamToServer.close();
            fromServer.close();
         }
		 catch(InvalidClassException e1)
		 {
		    JOptionPane.showMessageDialog(null,"类错误!");
		 }
		 catch(NotSerializableException e2)
		 {
			JOptionPane.showMessageDialog(null,"对象未序列化!");
		 }
		 catch(IOException e3)
		 {
		 	JOptionPane.showMessageDialog(null,"不能写入到指定服务器!");
		 }
		
	}  
	public static void main(String args[])
	{
		new Register("127.0.0.1");
	}

}  
