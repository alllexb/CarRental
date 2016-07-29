package ua.kiev.allexb.carrental.data.domain;

import java.io.Serializable;

/**
 * @author allexb
 * @version 1.0 14.12.2015
 */

public abstract class AbstractDomain implements Serializable {

    private static final long serialVersionUID = -3235671974350823999L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    private Long id;

    public AbstractDomain() {
    }

    public AbstractDomain(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
