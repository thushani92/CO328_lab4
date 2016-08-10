
package lk.ac.pdn.co328.restapi;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import lk.ac.pdn.co328.studentSystem.dbimplementation.DerbyStudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("rest")
public class StudentService
{
   //private static StudentRegister register = new DerbyStudentRegister();
   StudentRegister register;


    StudentService() throws SQLException {
     register = new DerbyStudentRegister();
    }

    StudentService k = new StudentService();

    @POST
    @Path("student/new")
    @Consumes(MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    public Response addStudent(Student input) {
        System.out.println(input.getFirstName());
        if (input != (null)) {
            try {
                k.register.addStudent(input);


                return Response.status(HttpResponseCodes.SC_OK).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).build();
            }
        }else{
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).build();
        }
    }

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        System.out.println(id);
        try {
            Student st = k.register.findStudent(id);
            if (st == null) {
                return Response.status(HttpResponseCodes.SC_NOT_FOUND).build();
            }
            return Response.status(HttpResponseCodes.SC_OK).entity(st).build();
        }
        catch (Exception e){
            System.out.println("Error in Get");
        }
        return Response.status(HttpResponseCodes.SC_OK).entity("succeses").build();
    }

    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        if(input == null) {
            try {
                k.register.addStudent(input);
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR).build();
            }
        }
        else{
            k.register.removeStudent(id);
            try {
                k.register.addStudent(input);
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Student is modified.").build();
            }
        }
        return Response.status(HttpResponseCodes.SC_OK).build();
    }

    @DELETE
    @Path("student/{id}")

    public Response deleteStudent(@PathParam("id") int id) {
        if ((k.register.findStudent(id) != (null))) {
            try {
                k.register.removeStudent(id);
               return Response.status(HttpResponseCodes.SC_OK).build();
            } catch (Exception e) {
                return Response.status(HttpResponseCodes.SC_INTERNAL_SERVER_ERROR).build();
            }
        }else {
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).build();
        }
    }


}