/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Mensaje;
import pojo.Usuario;

/**
 * REST Web Service
 *
 * @author user
 */
@Path("catalogos")
public class CatalogoWS {

    @Context
    private UriInfo context;

   
    public CatalogoWS() {
    }
    
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getAll(){
        List<Usuario> usuariosBD = new ArrayList();
        SqlSession conn= MyBatisUtil.getSession();
        if(conn != null){
            try{
                usuariosBD=conn.selectList("Usuario.getAllUsuarios");
            }catch (Exception e){
                e.printStackTrace();
                //System.out.println("Error: " + e.getMessage());
            }finally{
                conn.close();
            }
        }
        return usuariosBD;
    }
    
    
    @Path("byId/{idUsuario}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUserById(@PathParam("idUsuario")Integer idUsuario){
        Usuario user = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                user = conn.selectOne("Usuario.getUsuarioById", idUsuario);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }            
        }
        return user;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarUsuario(@FormParam("nombre") String nombre,
                                   @FormParam("username") String username,
                                   @FormParam("password") String password){
        Usuario user = new Usuario(0,nombre,username,password);
        Mensaje mensaje = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas = conn.insert("Usuario.registrarUsuario",user);
                conn.commit();
                if(filas>0){
                    mensaje = new Mensaje("Usuario guardado con exito", false);
                }else{
                    mensaje = new Mensaje("Lo sentimos el usuario no se pudo registrar", true);
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
    public Mensaje actualizarUsuario(@FormParam("idUsuario") String idUsuario,
                                   @FormParam("nombre") String nombre,
                                   @FormParam("password") String password){
        Mensaje mensaje= null;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("idUsuario", idUsuario);
        parametros.put("nombre",nombre);
        parametros.put("password",password);
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas=conn.update("Usuario.actualizarUsuario",parametros);
                conn.commit();
                if(filas>0){
                    mensaje = new Mensaje("Usuario actualizado con exito",false);
                }else{
                    mensaje = new Mensaje("No se pudo actualizar el usuario",true);
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
    public Mensaje eliminarUsuario(@FormParam("idUsuario") Integer idUsuario){
        Mensaje mensaje = null;
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                int filas = conn.delete("Usuario.eliminarUsuario",idUsuario);
                conn.commit();
                if(filas>0){
                    mensaje= new Mensaje("Usuario eliminado con exito",false);
                }else{
                    mensaje= new Mensaje("El usuario no se pudo eliminar",true);
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
    
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje loginUsuario(@FormParam("username") String username,
                                   @FormParam("contraseña") String password){
        Mensaje mensaje= null;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("username", username);
        parametros.put("password",password);
        SqlSession conn= MyBatisUtil.getSession();
        if(conn !=null){
            try{
                Usuario userbd = conn.selectOne("Usuario.loginUsuario", parametros);
                if(userbd != null && userbd.getIdUsuario() > 0){
                    mensaje = new Mensaje(userbd.getNombre(),false);
                }else{
                    mensaje = new Mensaje("Usuario y/o contraseña incorrectos.",false);
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
}

