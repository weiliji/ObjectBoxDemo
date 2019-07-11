package com.example.objectbox.bean;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
@Entity
public class Student {
    @Id
    public long id;
    public String name;
    public ToMany<Teacher> teachers;

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

    public ToMany<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ToMany<Teacher> teachers) {
        this.teachers = teachers;
    }
}
