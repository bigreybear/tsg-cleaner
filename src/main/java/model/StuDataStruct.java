package model;

import java.io.Serializable;
import java.util.*;

public class StuDataStruct implements Serializable{
    private int year;
    private Map<String, ArrayList<ClaStruct>> deps;

    public static void main(String[] args) {
        StuDataStruct sds = new StuDataStruct(2013);
        sds.addStu("ab", "cs", "css", "zhaoxin", 110);
        System.out.println(sds.toString());
//        Student st = new Student("zhaoxin", 123);
//        System.out.println(st.toString());
//        ClaStruct cs = new ClaStruct("li3", "css");
//        cs.addStu(st);
//        System.out.println(cs.toString());

    }

    StuDataStruct(int year){
        this.year = year;
        deps = null;
    }
    public void addDep(String dep_name){
        if (deps==null){
            deps = new HashMap<String, ArrayList<ClaStruct>>();
        }

        if (!deps.containsKey(dep_name)){
            deps.put(dep_name, new ArrayList<ClaStruct>());
        }
    }

    public void addStu(String _depName, String _claName, String _majName, String _stuName, int _id){
        int index;
        addDep(_depName);
        ArrayList<ClaStruct> dep = deps.get(_depName);
        for (ClaStruct cs:dep) {
            if(cs.getName() == _claName){
                index = dep.indexOf(cs);
                cs.addStu(_stuName, _id);
                dep.set(index, cs);
                return;
            }
        }
        ClaStruct ncs = new ClaStruct(_claName, _majName);
        ncs.addStu(_stuName, _id);
        dep.add(ncs);
        deps.put(_depName, dep);
    }

    @Override
    public String toString(){
        String ret = String.format("Print all departments:\n");
        String reta = null;
        for(Map.Entry<String, ArrayList<ClaStruct>> ent : deps.entrySet()){
            reta = String.format("Department: %s\n", ent.getKey());
            for (ClaStruct cla: ent.getValue()) {
                reta += "  ";
                reta += cla.toString();
            }
            ret += reta;
        }
        return ret;
    }


}


class ClaStruct implements Serializable{
    private String name;
    private String majName;
    private ArrayList<Student> stus;
    ClaStruct(String _name, String _majName){
        name = _name;
        majName = _majName;
        stus = null;
    }

    public void addStu(Student stu){
        this.addStu(stu.getName(), stu.getId());
    }

    public void addStu(String _name, int _id){
        if(stus == null){
            stus = new ArrayList<Student>();
        }
        for (Student stt:stus) {
            if (stt.getId() == _id){
                stt.incDup_cnt();
                return;
            }
        }
        stus.add(new Student(_name, _id));
    }

    public int ifExsistOnlyOne(int _id){
        if(stus==null){
            return 0;
        }
        for (Student stt:stus) {
            if(stt.getId() == _id){
                return stt.getDup_cnt();
            }
        }
        return 0;
    }

    @Override
    public String toString(){
        String ret = String.format("Class Name: %s, Maj Name: %s\n", name, majName);
        for (Student stu:stus) {
            ret += String.format("   ");
            ret += String.format(stu.toString());
        }
        return ret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajName() {
        return majName;
    }

    public void setMajName(String majName) {
        this.majName = majName;
    }

    public ArrayList<Student> getStus() {
        return stus;
    }

    public void setStus(ArrayList<Student> stus) {
        this.stus = stus;
    }
}

class Student implements Serializable{
    private String name;
    private int id;
    private int dup_cnt;

    public Student(String _name, int _id){
        name = _name;
        id = _id;
        dup_cnt = 0;
    }

    @Override
    public String toString(){
        String ret = String.format("Name: %s, ID: %d, dups:%d \n", name, id, dup_cnt);
        return ret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDup_cnt() {
        return dup_cnt;
    }

    public void setDup_cnt(int dup_cnt) {
        this.dup_cnt = dup_cnt;
    }

    public void incDup_cnt(){
        this.dup_cnt++;
    }
}