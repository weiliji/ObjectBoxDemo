package com.example.objectbox.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.objectbox.bean.Student;
import com.example.objectbox.bean.Student_;
import com.example.objectbox.bean.Teacher;
import com.example.objectbox.bean.Teacher_;
import com.example.objectbox.util.ObjectBox;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

public class ManyToManyPresenter {
    private Activity activity;
    private final Box<Teacher> teacherBox;
    private final Box<Student> studentBox;

    public ManyToManyPresenter(Activity activity) {
        this.activity = activity;
        teacherBox = ObjectBox.get().boxFor(Teacher.class);
        studentBox = ObjectBox.get().boxFor(Student.class);
    }

    /**
     * 添加一个老师
     * @param rondom_id
     * @param teacherName
     */
    public List<Teacher> addTeacher(int rondom_id,String teacherId, String teacherName,String studentId) {
        Teacher teacher = new Teacher();
        teacher.setName(TextUtils.isEmpty(teacherName) ? "老师" + rondom_id + "号" : teacherName);
        if (TextUtils.isEmpty(studentId)){
            teacherBox.put(teacher);
        }else if(!TextUtils.isEmpty(teacherId)&&!TextUtils.isEmpty(studentId)){//若果老师与学生ID不为空 则绑定在一起
            Teacher teacher1 = teacherBox.query().equal(Teacher_.id, Long.parseLong(teacherId)).build().findUnique();
            Student student = studentBox.query().equal(Student_.id, Long.parseLong(studentId)).build().findUnique();
            if(teacher1==null||student==null){
                Toast.makeText(activity, "未找到该老师或学生无法绑定一起", Toast.LENGTH_SHORT).show();
            }else if(teacher1!=null&&student!=null){
                teacher1.students.add(student);
                teacherBox.put(teacher1);
            }
        }else{
            Student student = studentBox.query().equal(Student_.id, Long.parseLong(studentId)).build().findUnique();
            if (student==null){
                Toast.makeText(activity, "未找到该学生", Toast.LENGTH_SHORT).show();
            }else{
                student.teachers.add(teacher);
                studentBox.put(student);
            }
        }
        return queryTeacher("", "");
    }

    /**
     * 删除老师
     *
     * @param teacherId
     * @param teacherName
     */
    public List<Teacher> deleteTeacher(String teacherId, String teacherName) {
        if (TextUtils.isEmpty(teacherId) && TextUtils.isEmpty(teacherName)) {//删除最后一个老师
            List<Teacher> teachers = teacherBox.query().build().find();
            if(teachers.size()>0) teacherBox.remove(teachers.get(teachers.size()-1));
        } else if (TextUtils.isEmpty(teacherId)) {//删除指定名字
            teacherBox.query().equal(Teacher_.name, teacherName).build().remove();
        } else if (TextUtils.isEmpty(teacherName)) {//删除指定ID
            teacherBox.query().equal(Teacher_.id, Long.parseLong(teacherId)).build().remove();
        } else {//删除指定ID与名字
            teacherBox.query().equal(Teacher_.id, Long.parseLong(teacherId)).equal(Teacher_.name, teacherName).build().remove();
        }
        return queryTeacher("", "");
    }

    /**
     * 修改老师名字
     *
     * @param teacherId
     * @param teacherName
     */
    public List<Teacher> updateTeacher(String teacherId, String teacherName) {
        if (TextUtils.isEmpty(teacherId) && TextUtils.isEmpty(teacherName)) {
            Toast.makeText(activity, "请输入要修改的老师ID与修改后的老师昵称", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(teacherId)) {
            Toast.makeText(activity, "请输入要修改的老师ID", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(teacherName)) {
            Toast.makeText(activity, "请输入修改后的老师昵称", Toast.LENGTH_SHORT).show();
        } else {
            Teacher unique = teacherBox.query().equal(Teacher_.id, Long.parseLong(teacherId)).build().findUnique();
            if(unique==null){
                Toast.makeText(activity, "未找到该老师", Toast.LENGTH_SHORT).show();
            }else{
                unique.setName(teacherName);
                teacherBox.put(unique);
            }
        }
        return queryTeacher("", "");
    }

    /**
     * 查询某个老师
     *
     * @param teacherId
     * @param teacherName
     */
    public List<Teacher> queryTeacher(String teacherId, String teacherName) {
        QueryBuilder<Teacher> query = teacherBox.query();
        if (!TextUtils.isEmpty(teacherId) && !TextUtils.isEmpty(teacherName)) {
            query.equal(Teacher_.id, Long.parseLong(teacherId)).equal(Teacher_.name, teacherName);
        } else if (!TextUtils.isEmpty(teacherId)) {
            query.equal(Teacher_.id, Long.parseLong(teacherId));
        } else if (!TextUtils.isEmpty(teacherName)) {
            query.contains(Teacher_.name, teacherName);
        }
        return query.build().find();
    }

    /**
     * 添加一个学生
     * @param rondom_id
     * @param teacherId
     * @param studentName
     * @return
     */
    public List<Student> addStudent(int rondom_id, String teacherId, String studentName,String studentId) {
        Student student = new Student();
        student.setName(TextUtils.isEmpty(studentName) ? "学生" + rondom_id + "号" : studentName);
        if (TextUtils.isEmpty(teacherId)) {//给最后一个老师添加学生
            List<Teacher> teachers = teacherBox.query().build().find();
            if (teachers.size() > 0) {
                Teacher teacher = teachers.get(teachers.size() - 1);
                teacher.students.add(student);
                teacherBox.put(teacher);
            }else{
                studentBox.put(student);
            }
        } else if(!TextUtils.isEmpty(teacherId)&&!TextUtils.isEmpty(studentId)){
            Teacher teacher = teacherBox.query().equal(Teacher_.id, Long.parseLong(teacherId)).build().findUnique();
            Student student1 = studentBox.query().equal(Student_.id, Long.parseLong(studentId)).build().findUnique();
            if(teacher==null||student1==null){
                Toast.makeText(activity, "未找到该老师或学生无法绑定一起", Toast.LENGTH_SHORT).show();
            }else if(teacher!=null&&student1!=null){
                teacher.students.add(student);
                teacherBox.put(teacher);
            }
        }else {//给指定老师添加学生
            Teacher unique = teacherBox.query().equal(Teacher_.id, Long.parseLong(teacherId)).build().findUnique();
            if(unique!=null){
                unique.students.add(student);
                teacherBox.put(unique);
            }else {
                Toast.makeText(activity, "未找到该老师", Toast.LENGTH_SHORT).show();
            }
        }
        return queryStudent(teacherId, null);
    }

    /**
     * 删除一个学生
     *
     * @param teacherId
     * @param studentId
     * @param studentName
     * @return
     */
    public List<Student> deleteStudent(String teacherId, String studentId, String studentName) {
        if (TextUtils.isEmpty(teacherId) && TextUtils.isEmpty(studentId) && TextUtils.isEmpty(studentName)) {//删除最后一名学生
            List<Student> students = studentBox.query().build().find();
            if(students.size()>0) studentBox.remove(students.get(students.size()-1));
        } else if (TextUtils.isEmpty(teacherId) && TextUtils.isEmpty(studentId)) {//根据学生昵称删除
            studentBox.query().equal(Student_.name, studentName).build().remove();
        } else if (TextUtils.isEmpty(teacherId) && TextUtils.isEmpty(studentName)) {//根据学生id删除
            studentBox.query().equal(Student_.id, Long.parseLong(studentId)).build().remove();
        } else if (TextUtils.isEmpty(teacherId) && TextUtils.isEmpty(studentName)) {//根据老师id删除
            QueryBuilder<Student> query = studentBox.query();
            query.link(Student_.teachers).equal(Teacher_.id, Long.parseLong(teacherId)).build();
            query.build().remove();
        } else if (TextUtils.isEmpty(teacherId)) {//根据学生昵称与学生id删除
            studentBox.query().equal(Student_.id, Long.parseLong(studentId)).equal(Student_.name, studentName).build().remove();
        } else if (TextUtils.isEmpty(studentId)) {//根据老师id 与学生名删除
            QueryBuilder<Student> equal = studentBox.query().equal(Student_.name, studentName);
            equal.link(Student_.teachers).equal(Teacher_.id, Long.parseLong(teacherId));
            equal.build().remove();
        } else {//根据老师id与学生id删除
            QueryBuilder<Student> equal = studentBox.query().equal(Student_.id, Long.parseLong(studentId));
            equal.link(Student_.teachers).equal(Teacher_.id, Long.parseLong(teacherId));
            equal.build().remove();
        }
        return queryStudent(teacherId, null);
    }

    /**
     * 修改学生昵称
     *
     * @param teacherId
     * @param studentId
     * @param studentName
     * @return
     */
    public List<Student> updateStudent(String teacherId, String studentId, String studentName) {
        if (TextUtils.isEmpty(studentName)) {
            Toast.makeText(activity, "请输入学生昵称", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(teacherId) && TextUtils.isEmpty(studentId)) {
            Toast.makeText(activity, "请输入要修改的老师ID或学生ID", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(teacherId)) {
            Student unique = studentBox.query().equal(Student_.id, Long.parseLong(studentId)).build().findUnique();
            if (unique == null) {
                Toast.makeText(activity, "找不到该学生", Toast.LENGTH_SHORT).show();
            } else {
                unique.setName(studentName);
                studentBox.put(unique);
            }
        } else if (TextUtils.isEmpty(studentId)) {
            Toast.makeText(activity, "请输入学生ID", Toast.LENGTH_SHORT).show();
        } else {
            QueryBuilder<Student> equal = studentBox.query().equal(Student_.id, Long.parseLong(studentId));
            equal.link(Student_.teachers).equal(Teacher_.id, Long.parseLong(teacherId));
            Student unique = equal.build().findUnique();
            if (unique == null) {
                Toast.makeText(activity, "找不到该学生", Toast.LENGTH_SHORT).show();
            } else {
                unique.setName(studentName);
                studentBox.put(unique);
            }
        }
        return queryStudent(teacherId, studentId);
    }

    /**
     * 查询学生
     *
     * @param teacherId
     * @param studentId
     * @return
     */
    public List<Student> queryStudent(String teacherId, String studentId) {
        QueryBuilder<Student> query = studentBox.query();
        if (!TextUtils.isEmpty(teacherId) && !TextUtils.isEmpty(studentId)) {
            QueryBuilder<Student> equal = query.equal(Student_.id, Long.parseLong(studentId));
            equal.link(Student_.teachers).equal(Teacher_.id, Long.parseLong(teacherId));
        } else if (!TextUtils.isEmpty(teacherId)) {
            Teacher teacher = teacherBox.query().equal(Teacher_.id, Long.parseLong(teacherId)).build().findUnique();
            if (teacher == null) {
                Toast.makeText(activity, "未找到该学生", Toast.LENGTH_SHORT).show();
            } else {
                return teacher.students;
            }
        } else if (!TextUtils.isEmpty(studentId)) {
            query.equal(Student_.id, Long.parseLong(studentId));
        }
        return query.build().find();
    }
}
