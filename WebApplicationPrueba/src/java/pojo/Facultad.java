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
public class Facultad {
    private Integer idFacultad;
    private String nombre;

    public Facultad() {
    }

    public Facultad(Integer idFacultad, String nombre) {
        this.idFacultad = idFacultad;
        this.nombre = nombre;
    }

    public Integer getIdFacultad() {
        return idFacultad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdFacultad(Integer idFacultad) {
        this.idFacultad = idFacultad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
