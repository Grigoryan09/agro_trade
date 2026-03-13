package am.agrotrade.model.entity;

import am.agrotrade.model.enums.Gender;
import am.agrotrade.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String surname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate birthDate;

    private String address;

    private String email;

    private String phoneNumber;

    private String username;

    private String password;

    private String token;

    private boolean active;

    @OneToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @OneToMany(mappedBy = "author" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<News>  news;

}
