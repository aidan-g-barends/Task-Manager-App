package ac.za.mycput.taskmanager.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    private String name;
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected User(){}

    private User (Builder  builder){
        this.name = builder.name;
        this.email = builder.email;
        this.id = builder.id;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }

    public static class Builder{

        private String name;
        private String email;
        private Long id;

        public Builder setName(String name) {
             this.name = name;
             return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder copy(User user){
            this.name = user.getName();
            this.email = user.getEmail();
            this.id = user.getId();

            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
