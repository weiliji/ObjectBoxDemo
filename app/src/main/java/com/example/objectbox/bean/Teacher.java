package com.example.objectbox.bean;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Teacher {
    @Id
    public long id;
    public String name;
    @Backlink(to = "teachers")
    public ToMany<Student> students;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToMany<Student> getStudents() {
        return students;
    }

    public void setStudents(ToMany<Student> students) {
        this.students = students;
    }
}
