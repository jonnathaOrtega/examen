/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import modelo.Cliente;
import modelo.ClientePK;

/**
 *
 * @author CRISTINA CALLE
 */
@Stateless
@Path("modelo.cliente")
public class ClienteFacadeREST extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "ExamenPU")
    private EntityManager em;

    private ClientePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;nombre=nombreValue;cedula=cedulaValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        modelo.ClientePK key = new modelo.ClientePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> nombre = map.get("nombre");
        if (nombre != null && !nombre.isEmpty()) {
            key.setNombre(nombre.get(0));
        }
        java.util.List<String> cedula = map.get("cedula");
        if (cedula != null && !cedula.isEmpty()) {
            key.setCedula(new java.lang.Integer(cedula.get(0)));
        }
        return key;
    }

    public ClienteFacadeREST() {
        super(Cliente.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cliente entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Cliente entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        modelo.ClientePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cliente find(@PathParam("id") PathSegment id) {
        modelo.ClientePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findAll() {
        return super.findAll();
    }
      @POST
    @Path("crear")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String crear(@FormParam("nombre")String nombre,@FormParam("apellido")String apellido,
        @FormParam("cedula")Integer cedula,@FormParam("direccion")String direccion,
        @FormParam("edad")Integer edad,@FormParam("provincia")String provincia,@FormParam("compra")String autoCompra){
    Cliente cliente =new Cliente(nombre, apellido, cedula, direccion, edad, provincia,autoCompra );
    super.create(cliente);
   
    return "cliente creado con exito";
}
     @POST
@Path ("Update")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public String update(@FormParam("nombre")String nombre,@FormParam("apellido")String apellido,
        @FormParam("cedula")Integer cedula,@FormParam("direccion")String direccion,
        @FormParam("edad")Integer edad,@FormParam("provincia")String provincia,@FormParam("compra")String autoCompra){
    Cliente cliente =new Cliente(nombre, apellido, cedula, direccion, edad, provincia,autoCompra ){
    
    Cliente cliente=super.find(nombre);
         
    cliente.setNombre(nombre);
    cliente.setApellido(apellido);
     cliente.setCedula(cedula);
    cliente.setEdad(edad);
    cliente.setProvincia(provincia);
    cliente.setCompra(autoCompra);
  
    
    return "cliente editado con exito";
}

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
