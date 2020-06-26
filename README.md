# JavaChatroom
>A java chat room for LAN user.   
>This is an application I followed a Java book to develop a chatroom.   
>P.S.: For this project, I used text document to store the data of user and chatting records (to save time). If you want to use the application to build a robust system, you should try to use database like MySQL.   

## System Design:  
>The chat room has two kinds of users: user and administrator.  
>For users, they can register, login, and chat with other after login.  

<img src="pic/System_design_user.png" width = 650/>  

>For administrator, there is no need to register or login. When they start server, they begin to manage the server. Administrator can use server to process register&login data, store chat records, and send message to normal users.  

<img src="pic/System_design_admin.png" width = 650/>  

## Function & UI:  
### User's Function:   
>The main interface for user should be login function. However, new user should register before login.    

<img src="pic/login_UI.png" height = 250/>    <img src="pic/register_UI.png" height = 250/>  


## Reference:   
>刘志成:《Java程序设计案例教程》
