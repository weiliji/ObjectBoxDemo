package com.example.objectbox.baseOperation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.objectbox.R;
import com.example.objectbox.adapter.StudentAdapter;
import com.example.objectbox.adapter.TeacherAdapter;
import com.example.objectbox.bean.Student;
import com.example.objectbox.bean.Teacher;
import com.example.objectbox.presenter.ManyToManyPresenter;
import com.example.objectbox.util.MyUtils;

public class ManyToManyActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etTeacherId,etTeacherName,etStudentId,etStudentName;
    private int[] ids = {R.id.btn_add_teacher,R.id.btn_delete_teacher,R.id.btn_update_teacher,R.id.btn_query_teacher,
                        R.id.btn_add_student,R.id.btn_delete_student,R.id.btn_update_student,R.id.btn_query_student};
    private ManyToManyPresenter presenter;
    private TeacherAdapter teacherAdapter;
    private StudentAdapter studentAdapter;
    private LinearLayout llTeacher;
    private LinearLayout llStudent;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_to_many);
        initUI();
        onItemClick();
        presenter = new ManyToManyPresenter(this);
    }

    private void onItemClick() {
        teacherAdapter.setOnItemClickListener(new TeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Teacher teacher) {
                if(teacher.students.size()>0){
                    studentAdapter.setData(teacher.students);
                    int measuredWidth = llTeacher.getMeasuredWidth();
                    MyUtils.startAnim(1, measuredWidth,width, llTeacher, llStudent);
                }else {
                    Toast.makeText(ManyToManyActivity.this, "该老师没有订单", Toast.LENGTH_SHORT).show();
                }
            }
        });

        studentAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Student student) {
                if(student.teachers.size()>0){
                    teacherAdapter.setData(student.teachers);
                    int measuredWidth = llTeacher.getMeasuredWidth();
                    MyUtils.startAnim(2, measuredWidth,width, llTeacher, llStudent);
                }else {
                    Toast.makeText(ManyToManyActivity.this, "该学生没有老师", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUI() {
        width = getWindowManager().getDefaultDisplay().getWidth();
        for (int id : ids){
            findViewById(id).setOnClickListener(this);
        }
//        输入项
        etTeacherId = findViewById(R.id.et_teacher_id);
        etTeacherName = findViewById(R.id.et_teacher_name);
        etStudentId = findViewById(R.id.et_student_id);
        etStudentName = findViewById(R.id.et_student_name);

        llTeacher = findViewById(R.id.ll_teacher);
        llStudent = findViewById(R.id.ll_student);
//        Rv
        RecyclerView rvTeacher = findViewById(R.id.rv_teacher);
        RecyclerView rvStudent = findViewById(R.id.rv_student);
        LinearLayoutManager manager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        rvTeacher.setLayoutManager(manager);
        rvStudent.setLayoutManager(manager1);
        teacherAdapter = new TeacherAdapter();
        studentAdapter = new StudentAdapter();
        rvTeacher.setAdapter(teacherAdapter);
        rvStudent.setAdapter(studentAdapter);
    }

    @Override
    public void onClick(View v) {
        int rondom_id = MyUtils.creatRandom();
        int measuredWidth =llTeacher.getMeasuredWidth();
        String teacherId = etTeacherId.getText().toString().trim();
        String studentId = etStudentId.getText().toString().trim();
        String teacherName = etTeacherName.getText().toString().trim();
        String studentName = etStudentName.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_add_teacher://添加一个老师
                teacherAdapter.setData(presenter.addTeacher(rondom_id,teacherId, teacherName,studentId));
                MyUtils.startAnim(2, measuredWidth, width, llTeacher, llStudent);
                break;
            case R.id.btn_delete_teacher://删除一个老师
                teacherAdapter.setData(presenter.deleteTeacher(teacherId,teacherName));
                MyUtils.startAnim(2, measuredWidth, width, llTeacher, llStudent);
                break;
            case R.id.btn_update_teacher://修改老师名字
                teacherAdapter.setData(presenter.updateTeacher(teacherId, teacherName));
                MyUtils.startAnim(2, measuredWidth, width, llTeacher, llStudent);
                break;
            case R.id.btn_query_teacher://查询某个老师
                teacherAdapter.setData(presenter.queryTeacher(teacherId, teacherName));
                MyUtils.startAnim(2, measuredWidth, width, llTeacher, llStudent);
                break;
            case R.id.btn_add_student://添加一个学生
                studentAdapter.setData(presenter.addStudent(rondom_id,teacherId, studentName,studentId));
                MyUtils.startAnim(1, measuredWidth, width, llTeacher, llStudent);
                break;
            case R.id.btn_delete_student://删除一个学生
                studentAdapter.setData(presenter.deleteStudent(teacherId,studentId, studentName));
                MyUtils.startAnim(1, measuredWidth, width, llTeacher, llStudent);
                break;
            case R.id.btn_update_student://修改学生名字
                studentAdapter.setData(presenter.updateStudent(teacherId,studentId, studentName));
                MyUtils.startAnim(1, measuredWidth, width, llTeacher, llStudent);
                break;
            case R.id.btn_query_student://查询某个学生
                studentAdapter.setData(presenter.queryStudent(teacherId,studentId));
                MyUtils.startAnim(1, measuredWidth, width, llTeacher, llStudent);
                break;
        }
    }
}
