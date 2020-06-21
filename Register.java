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
    //���ڽ��������ڶ�λ
	Dimension scrnsize;
    Toolkit toolkit=Toolkit.getDefaultToolkit();
    //���췽��
	public Register(String ip)
	{
		super("����������ע�ᴰ��");
		strServerIp=ip;
		pnlRegister=new JPanel();
		this.getContentPane().add(pnlRegister);
	
		lblUserName=new JLabel("�� �� ��:");
		lblPassword=new JLabel("��	��:");
		lblConfirmPass=new JLabel("ȷ������:");
		txtUserName=new JTextField(30);
		txtEmail=new JTextField(30);
		txtAge=new JTextField(10);
		pwdUserPassword=new JPasswordField(30);
		pwdConfirmPass=new JPasswordField(30);
	    btngGender=new ButtonGroup();
	    btnOk=new JButton("ȷ��");
	    btnOk.setMnemonic('O');
	    btnOk.setToolTipText("����ע����Ϣ");
		btnCancel=new JButton("����");
		btnCancel.setMnemonic('B');
		btnCancel.setToolTipText("���ص�¼����");
		btnClear=new JButton("���");
		btnClear.setMnemonic('L');
		btnClear.setToolTipText("���ע����Ϣ");
		
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
	
		Font fontstr=new Font("����",Font.PLAIN,12);	
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
	    
	    //���ñ���ͼƬ
	    Icon logo = new ImageIcon("C:\\Users\\Leonard\\Desktop\\Java-Chat-master\\Java-Chat\\chatroom\\images\\registerlogo.jpg");
	 	logoPosition = new JLabel(logo);
		logoPosition.setBounds(0, 0, 360,78);
		pnlRegister.add(logoPosition);
	    
	    this.setSize(360,240);
		this.setVisible(true);
		this.setResizable(false);
		//�����ڶ�λ����Ļ����
    	scrnsize=toolkit.getScreenSize();
    	this.setLocation(scrnsize.width/2-this.getWidth()/2,
    	                 scrnsize.height/2-this.getHeight()/2);
		Image img=toolkit.getImage("C:\\Users\\Leonard\\Desktop\\Java-Chat-master\\Java-Chat\\chatroom\\images\\appico.jpg");
        this.setIconImage(img);
		//������ťע�����
		btnOk    .addActionListener(this);
		btnCancel.addActionListener(this);
		btnClear   .addActionListener(this);
	} 
	
	//����
	public void actionPerformed(ActionEvent ae)
	{
		Object source=new Object();
	    source=ae.getSource();
	    if (source.equals(btnOk))      //ȷ��
	    {
	        register();
	    }
	    if (source.equals(btnCancel))  //����
	    {
	    	new Login();
	    	this.dispose();
	    }
	    if (source.equals(btnClear))     //���
	    {
	        txtUserName.setText("");
	        pwdUserPassword.setText("");
	        pwdConfirmPass.setText("");
	        txtAge.setText("");
	        txtEmail.setText("");	
	    }
	} 
	
	//ȷ��
	public void register()
	{
        Register_Customer data=new Register_Customer();
	    data.custName     = txtUserName.getText();
		data.custPassword = pwdUserPassword.getText();
		//��֤�û����Ƿ�Ϊ��
		if(data.custName.length()==0)
		{
		    JOptionPane.showMessageDialog(null,"�û�������Ϊ��");	
            return;	
		}
		//��֤�����Ƿ�Ϊ��
		if(data.custPassword.length()==0)
		{
		    JOptionPane.showMessageDialog(null,"���벻��Ϊ��");	
            return;	
		}
		
		//��֤�����һ����
		if(!data.custPassword.equals(pwdConfirmPass.getText()))
		{
		    JOptionPane.showMessageDialog(null,"�����������벻һ�£�����������");	
            return;
		}
		
		try
		{
		    //���ӵ�������
		    Socket toServer;
  		    toServer = new Socket(strServerIp,1001);
		    ObjectOutputStream streamToServer=new ObjectOutputStream (toServer.getOutputStream());					
		    //д�ͻ���ϸ���ϵ�������socket
		    streamToServer.writeObject((Register_Customer)data);
            //�����Է�����socket�ĵ�½״̬
            BufferedReader fromServer=new BufferedReader(new InputStreamReader(toServer.getInputStream()));
            String status=fromServer.readLine();
            //��ʾ�ɹ���Ϣ
            JOptionPane op=new JOptionPane();
            op.showMessageDialog(null,status);
            if(status.equals(data.custName+"ע��ɹ�"))
            {
                txtUserName.setText("");
                pwdUserPassword.setText("");
                pwdConfirmPass.setText("");
            }
            
            //�ر�������
		    streamToServer.close();
            fromServer.close();
         }
		 catch(InvalidClassException e1)
		 {
		    JOptionPane.showMessageDialog(null,"�����!");
		 }
		 catch(NotSerializableException e2)
		 {
			JOptionPane.showMessageDialog(null,"����δ���л�!");
		 }
		 catch(IOException e3)
		 {
		 	JOptionPane.showMessageDialog(null,"����д�뵽ָ��������!");
		 }
		
	}  
	public static void main(String args[])
	{
		new Register("127.0.0.1");
	}

}  
