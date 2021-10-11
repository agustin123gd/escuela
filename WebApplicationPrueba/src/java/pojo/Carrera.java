/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author user
 */
public class Carrera {
    private Integer idCarrera;
    private String nombre;
    private String codigo;
    private Integer idFacultad;

    public Carrera() {
    }

    public Carrera(Integer idCarrera, String nombre, String codigo, Integer idFacultad) {
        this.idCarrera = idCarrera;
        this.nombre = nombre;
        this.codigo = codigo;
        this.idFacultad = idFacultad;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public Integer getIdFacultad() {
        return idFacultad;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setIdFacultad(Integer idFacultad) {
        this.idFacultad = idFacultad;
    }
    
    
}
