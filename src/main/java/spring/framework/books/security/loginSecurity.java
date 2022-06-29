package spring.framework.books.security;

import spring.framework.books.requestDTO.loginDTO;

public class loginSecurity {


    public String generateToken(loginDTO loginDTO){
        String email=loginDTO.getEmail();
        String password=loginDTO.getPassword();
        StringBuilder temp= new StringBuilder();

        for (int i = 0; i < email.length(); i++) {
            int ascii= email.charAt(i);
            ascii+=4;
            char ch=(char)ascii;
            temp.append(ch);
        }
        temp.append('.');
        for (int i = 0; i < password.length(); i++) {
            int ascii= password.charAt(i);
            ascii+=4;
            char ch=(char)ascii;
            temp.append(ch);
        }
        System.out.println(temp);
        return temp.toString();
    }

    public String verifyToken(String password){

        String []splitToken=password.split("\\.");
        String hashPass=splitToken[1];
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < hashPass.length(); i++) {
            int ascii= hashPass.charAt(i);
            ascii-=4;
            char ch=(char)ascii;

            temp.append(ch);
        }
        System.out.println(temp);
        return String.valueOf(temp);
    }
}
