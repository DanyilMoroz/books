package com.moroz.books.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "message_tittle")
    private String tittle;

    @Column(name = "message_body", length = 65535)
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Message() { }

    public Message(String tittle, String body, User user) {
        this.tittle = tittle;
        this.body = body;
        this.author = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author.getUsername();
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(tittle, message.tittle) &&
                Objects.equals(body, message.body) &&
                Objects.equals(author, message.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tittle, body, author);
    }
}
