package com.example.Persistence.entidades;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Audited
@Table(name = "categoria")
public class Categoria implements Serializable {
    private static final long serialVersionID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "denominacion")
    private String denominacion;

    @ManyToMany(mappedBy = "categorias")
    private List<Articulo> articulos = new ArrayList<Articulo>();

    public Categoria(String denominacion) {
        this.denominacion = denominacion;
    }

    public Categoria(String denominacion, List<Articulo> articulos) {
        this.denominacion = denominacion;
        this.articulos = articulos;
    }
}
