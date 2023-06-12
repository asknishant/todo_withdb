package com.todoApp.todo_withdb.notes;

import com.todoApp.todo_withdb.common.BaseEntity;
import com.todoApp.todo_withdb.tasks.TaskEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity(name = "notes") // this means that we are telling spring that this is a table so then thsi calss asks for PK.
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteEntity extends BaseEntity {

    @Column(name = "title", nullable = false, length = 100)
    String title;

    @Column(name = "body", nullable = false, length = 1000 )
    String body;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    TaskEntity task;

}
