# Password-Manager
Application for storing and managing password ensuring high security. It uses https to send data between a web server and a web browser.
Hashed master password with BCrypt and passwords for websides with AES CBC. The app is also impervious to SQL injections and xss attacks.
After 3 unsuccessful login attemps account is blocked. During signup application forces user to provide strong password (8 chars, 3 number,
1 capital letter, 1 special char) for the application 

Technologies used: **SpringBoot, MySQL, Thymeleaf**

Adding new password for the webside:

<img width="1436" alt="image" src="https://user-images.githubusercontent.com/61696629/160486629-d28c2734-7ed1-4329-a01d-823ea879f44e.png">

Main page:
<img width="1437" alt="image" src="https://user-images.githubusercontent.com/61696629/160486727-7803daf3-bdd6-4826-9b97-bc201f3d8360.png">

