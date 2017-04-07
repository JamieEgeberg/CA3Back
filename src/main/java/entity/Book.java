package entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Niki on 2017-04-04.
 *
 * @author Niki
 */
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Expose
    @Column(length = 255, nullable = false)
    private String title;

    @Expose
    @Column(length = 13, nullable = false)
    private String isbn;

    @Expose
    @Column(length = 2048, nullable = false)
    private String description;

    public Book() {
    }

    public Book(String title, String isbn, String description) {
        this.title = title;
        this.isbn = isbn;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
