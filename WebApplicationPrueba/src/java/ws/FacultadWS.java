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
import pojo.Facultad;
import pojo.Mensaje;

/**
 *
 * @author user
 */
@Path("facultad")
public class FacultadWS {
    @Context
    private UriInfo context;

    public FacultadWS() {
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Facultad> getAll(){
        List<Facultad> fac = new ArrayList();
        SqlSession conn= MyBatisUtil.getSession();
        if(conn != null){
            try{
                fac=conn.selectList("Facultad.getAllFacultades");
            }catch (Exception e){
                e.printStackTrace();
                //System.out.println("Error: " + e.getMessage());
            }finally{
                conn.close();
            }
        }
        return fac;
    }
    
    @Path("byId/{idFacultad}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Facultad getFacultadById(@PathParam("idFacultad")Integer idFacultad){
        Facultad fac = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                fac = conn.selectOne("Facultad.getFacultadById", idFacultad);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }            
        }
        return fac;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarFacultad(@FormParam("nombre") String nombre){
        Facultad fac = new Facultad(0,nombre);
        Mensaje mensaje = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas = conn.insert("Facultad.registrarFacultad",fac);
                conn.commit();
                if(filas>0){
                    mensaje = new Mensaje("Facultad guardado con exito", false);
                }else{
                    mensaje = new Mensaje("Lo sentimos, la facultad no se pudo registrar", true);
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
    public Mensaje actualizarFacultad(@FormParam("idFacultad") String idFacultad,
                                   @FormParam("nombre") String nombre){
        Mensaje mensaje= null;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("idUsuario", idFacultad);
        parametros.put("nombre",nombre);
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas=conn.update("Facultad.actualizarFacultad",parametros);
                conn.commit();
                if(filas>0){
                    mensaje = new Mensaje("Facultad actualizado con exito",false);
                }else{
                    mensaje = new Mensaje("No se pudo actualizar la facultad",true);
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
    public Mensaje eliminarUsuario(@FormParam("idFacultad") Integer idFacultad){
        Mensaje mensaje = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas = conn.delete("Facultad.eliminarFacultad",idFacultad);
                conn.commit();
                if(filas>0){
                    mensaje= new Mensaje("Facultad eliminado con exito",false);
                }else{
                    mensaje= new Mensaje("La facultad no se pudo eliminar",true);
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
