package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Niki on 2017-04-04.
 *
 * @author Niki
 */
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 13, nullable = false)
    private String isbn;

    @Column(length = 2048, nullable = false)
    private String description;

}