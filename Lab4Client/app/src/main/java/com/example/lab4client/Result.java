package com.example.lab4client;

public class Result {
    private int flag;
    private String message;
    private Users data;
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Users getData() {
        return data;
    }
    public void setData(Users user) {
        this.data = user;
    }

    public static class Users {
        private String username;
        private String pass;
        private String teleno;

        public Users() {
            super();
            // TODO Auto-generated constructor stub
        }
        public Users(String username, String pass ,String teleno) {
            super();
            this.username = username;
            this.pass = pass;
            this.teleno = teleno;
        }

        public String getTeleno() {
            return teleno;
        }

        public void setTeleno(String teleno) {
            this.teleno = teleno;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPass() {
            return pass;
        }
        public void setPass(String pass) {
            this.pass = pass;
        }

    }
}
