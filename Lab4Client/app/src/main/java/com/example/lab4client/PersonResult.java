package com.example.lab4client;

import java.util.List;

public class PersonResult {
    private int flag;
    private String message;
    private List<Persons> perosns;

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
    public List<Persons> getPersons() {
        return perosns;
    }
    public void setPersons(List<Persons> person) {
        this.perosns = person;
    }

    public static class Persons {
        private String username;
        private String name;
        private Integer age;
        private String teleno;
        private  int personId;
        public Persons(String username, String name, Integer age, String teleno) {
            super();
            this.username = username;
            this.name = name;
            this.age = age;
            this.teleno = teleno;
        }
        public Persons(String username,String name){
            this(username,name,null,"");
        }
        public Persons(String username,String name,Integer age){
            this(username,name,age,"");
        }
        public Persons(String username,String name,String teleno){
            this(username,name,null,teleno);
        }
        public Persons() {
            super();
            // TODO Auto-generated constructor stub
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
        public String getTeleno() {
            return teleno;
        }
        public void setTeleno(String teleno) {
            this.teleno = teleno;
        }
        public int getPersonId() {
            return personId;
        }
        public void setPersonId(int personId) {
            this.personId = personId;
        }

    }
}
