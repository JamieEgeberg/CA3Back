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

}
