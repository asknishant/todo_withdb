package com.todoApp.todo_withdb.tasks;

import com.todoApp.todo_withdb.common.BaseEntity;
import com.todoApp.todo_withdb.notes.NoteEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name = "tasks") // this means that we are telling spring that this is a table so then thsi calss asks for PK.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "due_date", nullable = false)
    Date dueDate;

    @Column(name = "done", nullable = false, columnDefinition = "boolean default false")
    Boolean done;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @ToString.Exclude
    List<NoteEntity> notes;


    public void setNotes(String name) {
        this.name = name;
    }
}
