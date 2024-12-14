package servlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/bucket")
public class BucketServlet extends HttpServlet {
//  private ObjectMapper objectMapper;
//  @Override
//  public void init() throws ServletException {
//    objectMapper = new ObjectMapper();
//    objectMapper.registerModule(new JavaTimeModule());
//    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
//  }


  public void doGet(HttpServletRequest req, HttpServletResponse resp) {

    HttpSession session = req.getSession();
    var bucket = session.getAttribute("bucket");
    System.out.println("aaa");
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    req.setAttribute("bucket", bucket);
    try {
      req.getRequestDispatcher("/elements/bucket.jsp").forward(req, resp);
    } catch (ServletException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

