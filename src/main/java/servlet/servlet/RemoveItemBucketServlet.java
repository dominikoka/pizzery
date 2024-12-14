package servlet.servlet;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/RemoveItemBucket")
public class RemoveItemBucketServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = request.getReader();
    String str = null;
    while ((str = br.readLine()) != null) {
      sb.append(str);
      //System.out.println(str);
    }
    JSONObject jObj = new JSONObject(sb.toString());

    var idItem = jObj.getString("id");
    HttpSession session = request.getSession();
    var bucket = session.getAttribute("bucket").toString();
    JSONObject bucketJson = jObj.getJSONObject(bucket);

    System.out.println(idItem);
  }

}
