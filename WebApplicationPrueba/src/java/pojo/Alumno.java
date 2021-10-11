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
public class Alumno {
    private Integer idAlumno;
    private String nombre;
    private String apellidos;
    private String matricula;
    private String email;
    private String idCarrera;
    private String nombreCarrera;
    private String nombreFacultad;

    public Alumno() {
    }

    public Alumno(Integer idAlumno, String nombre, String apellidos, String matricula, String email, String idCarrera, String nombreCarrera, String nombreFacultad) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.email = email;
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
        this.nombreFacultad = nombreFacultad;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public void setNombreFacultad(String nombreFacultad) {
        this.nombreFacultad = nombreFacultad;
    }

    

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getEmail() {
        return email;
    }

    public String getIdCarrera() {
        return idCarrera;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }
    
    
}
