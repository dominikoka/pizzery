<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="servlet.model.Pizza" %>
<%@ page import="java.util.List" %>

<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<section class="pizza">--%>
<%--  <div class="pizza__img-container">--%>
<%--    <img src="img/margheritta.webp" alt="margheritta" class="pizza__img">--%>
<%--  </div>--%>
<%--  <div class="pizza__info">--%>
<%--    <h2 class="pizza__title">Margherita</h2>--%>
<%--    <label for="size-select" class="pizza__label">Wybierz rozmiar:</label>--%>
<%--    <select id="size-select" onchange="updatePrice('half')" class="pizza__select">--%>
<%--      <option value="42" data-price="0" class="pizza__option">Duża 42 cm</option>--%>
<%--    </select>--%>
<%--    <p class="pizza__desc">Wybierz 2 połówki pizzy i rozkoszuj się podwójnym smakiem. Cena pojawi się po--%>
<%--      skompletowaniu zamówienia.</p>--%>
<%--    <button class="order-button pizza__orderBtn">Zamów</button>--%>
<%--  </div>--%>
<%--</section>--%>

<%
  List<Pizza> pizzas = (List<Pizza>) request.getAttribute("pizzas");
  for (int i = 0; i < pizzas.size(); i++) {
    out.println("<section class=\"pizza\" data-id=\"" + pizzas.get(i).getId() + "\">");
    out.print("<h2 class=\"pizza__title\">" + pizzas.get(i).getName() + "</h2>");
    out.print("<h2 class=\"pizza__desc\">" + pizzas.get(i).getDescription() + "</h2>");
//    out.print("<img src=\""+pizzas.get(i).getImg_url()+"\" alt=\"\">");
    out.print("<img src=\""+pizzas.get(i).getImg_url()+"\" class=\"pizza__img\" alt=\"\">");
    out.print("</section>");
  }
%>
<%--<img src="../img/ingredients/onion.jpg" alt="">--%>
<script>

    document.querySelectorAll('.pizza').forEach(function (element) {
        element.addEventListener('click', function () {
            idDataPizza = this.dataset.id;
            loadPizza(idDataPizza);
        });
    });


    function loadPizza(idPizza) {
        var adressURL = "http://localhost:8090/loadPizza";
        console.log("muj id pizza" + idPizza);
        $.ajax({
            type: "GET",
            url: adressURL,
            data: {
                idPizza: idPizza
            },
            success: function (result) {
                $(".pizzaContainer").html(result);
                createDefaultIngredients();
            },
            error: function (error) {

            }
        });
    }


    $(".pizzaInfo").click(function () {
        console.log(id);
        var adressURL = "http://localhost:8088/exportXML";
        $.ajax({
            type: "GET",
            url: adressURL,
            data: {},
            success: function (result) {

            },
            error: function (error) {
                console.error("Ajax request failed: " + error);
            }
        });
    });

</script>

