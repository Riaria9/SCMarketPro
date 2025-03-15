package com.example.demo.Tables;
import jakarta.persistence.*;





@Entity
@Table(name = "verifications")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="email")
    private String email;
    @Column(name="code")
    private String code;
 

    // Constructors, getters, setters
    public String getEmail(){
        return email;
    }
    public String getOTP(){
        return code;
    }
    public void setOTP(String otp){
        this.code= otp;
    }
    public void setEmail(String email){
        this.email=email;
    }
}