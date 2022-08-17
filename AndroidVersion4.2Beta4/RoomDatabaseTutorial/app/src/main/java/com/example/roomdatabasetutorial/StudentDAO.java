package com.example.roomdatabasetutorial;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {

    @Insert
    public void addStudent(Student student);

    @Query("select * from students")
    public List<Student> getStudents();

    @Query("select * from students where studentID = :studentID")
    public List<Student> getStudentWithId(String studentID);

    @Update
    public void updateStudent(Student student);

    @Delete
    public void deleteStudent(Student student);
}
