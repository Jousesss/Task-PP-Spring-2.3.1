package ru.alkey.spring.mvc.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "spring_mvc", catalog = "spring_course")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "* Username cannot be empty!")
    @Size(min = 2, max = 36, message = "* Username must be between 2 and 36 characters!")
    @Column(name = "username")
    private String username;

    @Email(message = "* Email is not valid!")
    @NotBlank(message = "* Email cannot be empty!")
    @Column(name = "email")
    private String email;

    @Min(value = 1, message = "* Age must be greater than 0!")
    @Max(value = 200, message = "* Age should be real, you are not a vampire :)")
    @Column(name = "age")
    private short age;
}
