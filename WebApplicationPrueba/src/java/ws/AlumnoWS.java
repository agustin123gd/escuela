/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Alumno;
import pojo.Mensaje;

/**
 *
 * @author user
 */
@Path("alumno")
public class AlumnoWS {
    @Context
    private UriInfo context;

    public AlumnoWS() {
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alumno> getAllAlumno(){
        List<Alumno> alumno = new ArrayList();
        SqlSession conn= MyBatisUtil.getSession();
        if(conn != null){
            try{
                alumno = conn.selectList("Alumno.getAllAlumnos");
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return alumno;
    }
    
    @Path("byId/{idAlumno}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Alumno getAlumno(@PathParam("idAlumno")Integer idAlumno){
        Alumno alumno = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn != null){
            try{
                alumno = conn.selectOne("Alumno.getAlumnoById", idAlumno);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return alumno;
    }
    
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarUsuario(@FormParam("nombre") String nombre,
                                   @FormParam("apellidos") String apellidos,
                                   @FormParam("matricula") String matricula,
                                   @FormParam("email") String email,
                                   @FormParam("carrera") String idCarrera){
        Alumno alumno = new Alumno(0,nombre,apellidos,matricula,email,idCarrera,"","");
        Mensaje mensaje = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas = conn.insert("Alumno.registrarAlumno",alumno);
                conn.commit();
                if(filas>0){
                    mensaje = new Mensaje("Alumno guardado con exito", false);
                }else{
                    mensaje = new Mensaje("Lo sentimos el alumno no se pudo registrar", true);
                }
            }catch(Exception e){
                mensaje = new Mensaje("Error " + e.getMessage() , true);
            }finally{
                conn.close();
            } 
        }else{
            mensaje = new Mensaje("Por el momento no hay conexion con la base de datos" , true);
        }
        return mensaje;
    }
    
    @Path("actualizar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje actualizarAlumno(@FormParam("idAlumno") String idAlumno,
                                   @FormParam("nombre") String nombre,
                                   @FormParam("apellidos") String apellidos){
        Mensaje mensaje= null;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("idAlumno", idAlumno);
        parametros.put("nombre",nombre);
        parametros.put("apellidos",apellidos);
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas=conn.update("Alumno.actualizarAlumno",parametros);
                conn.commit();
                if(filas>0){
                    mensaje = new Mensaje("Alumno actualizado con exito",false);
                }else{
                    mensaje = new Mensaje("No se pudo actualizar el alumno",true);
                }
            }catch(Exception e){
                mensaje = new Mensaje("Error " + e.getMessage(),true);
            }finally{
                conn.close();
            }
        }else{
            mensaje = new Mensaje("Por el momento no hay coneccion con la BD",true);
        }    
        return mensaje;
    }
    
    @Path("eliminar")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarUsuario(@FormParam("idAlumno") Integer idAlumno){
        Mensaje mensaje = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas = conn.delete("Alumno.eliminarAlumno",idAlumno);
                conn.commit();
                if(filas>0){
                    mensaje= new Mensaje("Alumno eliminado con exito",false);
                }else{
                    mensaje= new Mensaje("El alumno no se pudo eliminar",true);
                }
            }catch(Exception e){
                    mensaje= new Mensaje("Error" + e.getMessage(),true);

            }finally{
                conn.close();
            }
        }else{
            mensaje= new Mensaje("Por el momento no hay conexion",true);
        }
        return mensaje;
    }
}
