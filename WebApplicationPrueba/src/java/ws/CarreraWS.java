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
import pojo.Carrera;
import pojo.Mensaje;


/**
 *
 * @author user
 */
@Path("carrera")
public class CarreraWS {
    @Context
    private UriInfo context;

   
    public CarreraWS() {
    }
    
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Carrera> getAll(){
        List<Carrera> usuariosBD = new ArrayList();
        SqlSession conn= MyBatisUtil.getSession();
        if(conn != null){
            try{
                usuariosBD=conn.selectList("Carrera.getAllCarreras");
            }catch (Exception e){
                e.printStackTrace();
                //System.out.println("Error: " + e.getMessage());
            }finally{
                conn.close();
            }
        }
        return usuariosBD;
    }
    
    
    @Path("byId/{idCarrera}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Carrera getCarreraById(@PathParam("idCarrera")Integer idCarrera){
        Carrera carrera = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                carrera = conn.selectOne("Carrera.getCarreraById", idCarrera);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }            
        }
        return carrera;
    }
    
    @Path("byFacultad/{idFacultad}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Carrera getCarreraByFacultad(@PathParam("idFacultad")Integer idFacultad){
        Carrera carrera = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                carrera = conn.selectOne("Carrera.getCarreraByFacultad", idFacultad);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }            
        }
        return carrera;
    }

    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarUsuario(@FormParam("nombre") String nombre,
                                   @FormParam("codigo") String codigo,
                                   @FormParam("idFacultad") Integer idFacultad){
        Carrera carrera = new Carrera(0,nombre,codigo,idFacultad);
        Mensaje mensaje = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas = conn.insert("Carrera.registrarCarrera",carrera);
                conn.commit();
                if(filas>0){
                    mensaje = new Mensaje("Carrera guardado con exito", false);
                }else{
                    mensaje = new Mensaje("Lo sentimos la carrera no se pudo registrar", true);
                }
            }catch(Exception e){
                mensaje = new Mensaje("Error " + e.getMessage() , true);
            }finally{
                conn.close();
            } 
        }
        return mensaje;
    }
    
    @Path("actualizar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje actualizarUsuario(@FormParam("idCarrera") String idCarrera,
                                   @FormParam("nombre") String nombre,
                                   @FormParam("codigo") String codigo,
                                   @FormParam("idFacultad") Integer idFacultad){
        Mensaje mensaje= null;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("idCarrera", idCarrera);
        parametros.put("nombre",nombre);
        parametros.put("codigo",codigo);
        parametros.put("idFacultad",idFacultad);

        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas=conn.update("Carrera.actualizarCarrera",parametros);
                conn.commit();
                if(filas>0){
                    mensaje = new Mensaje("Carrera actualizado con exito",false);
                }else{
                    mensaje = new Mensaje("No se pudo actualizar la carrera",true);
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
    public Mensaje eliminarUsuario(@FormParam("idCarrera") Integer idCarrera){
        Mensaje mensaje = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas = conn.delete("Carrera.eliminarCarrera",idCarrera);
                conn.commit();
                if(filas>0){
                    mensaje= new Mensaje("Carrea eliminado con exito",false);
                }else{
                    mensaje= new Mensaje("La carrera no se pudo eliminar",true);
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