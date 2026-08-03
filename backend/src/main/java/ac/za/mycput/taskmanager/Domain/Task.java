package ac.za.mycput.taskmanager.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = " Title cannot be blank")
    private String title;

    private boolean completed;

    private int Loggin;

    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Task(){}

    private Task(Builder builder){

        this.id = builder.id;
        this.title = builder.title;
        this.completed = builder.completed;
        this.dueDate = builder.dueDate;
        this.user = builder.user;

    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                ", dueDate=" + dueDate +
                ", user=" + user +
                '}';
    }

    public static class Builder{

        private Long id;
        private String title;
        private boolean completed;
        private LocalDate dueDate;
        private User user;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCompleted(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder copy(Task task){
            this.id = task.getId();
            this.title = task.getTitle();
            this.completed = task.isCompleted();
            this.dueDate = task.getDueDate();
            this.user = task.getUser();

            return this;
        }

        public Task build(){
            return new Task(this);
        }
    }
}
