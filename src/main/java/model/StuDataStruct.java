package model;

import java.util.ArrayList;
import java.util.Set;

public class StuDataStruct {
    private int year;
    private Set<DepStruct> dep;
    StuDataStruct(int year){
        this.year = year;
        dep = null;
    }
    void addDep(String dep_name){

    }

}

class DepStruct{
    String name;
    ArrayList<ClaStruct> clas;
}

class ClaStruct{
    private String name;
    private String majName;
    ArrayList<Student> stus;
    ClaStruct(String _name, String _majName){
        name = _name;
        majName = _majName;
        stus = null;
    }

    void addStu(String _name, int _id){
        if(stus == null){
            stus = new ArrayList<Student>();
        }
        for (Student stt:stus) {
            if (stt.id == _id){
                stt.dup_cnt++;
                return;
            }
        }
        stus.add(new Student(_name, _id));
        return;
    }

    int ifExsitOnlyOne(int _id){
        if(stus==null){
            return 0;
        }
        for (Student stt:stus) {
            if(stt.id == _id){
                return stt.dup_cnt;
            }
        }
        return 0;
    }
}

class Student{
    String name;
    int id;
    int dup_cnt;
    Student(String _name, int _id){
        name = _name;
        id = _id;
        dup_cnt = 0;
    }
}