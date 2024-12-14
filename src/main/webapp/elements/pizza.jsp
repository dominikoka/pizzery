<%@ page import="servlet.model.Pizza" %>
<%@ page import="java.util.List" %>
<%@ page import="servlet.model.Ingredient" %>
<%@ page import="servlet.model.IngredientSelection" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.gson.Gson" %>


<article class="pizzaInfo">
  <section class="pizzaInfo__box">
    <section class="pizzaInfo__top">
      <%
        Pizza pizza = (Pizza) request.getAttribute("pizza");
        var pizzaName = pizza.getName();
        var pizzaPrice = 0;
//        var pizzaId = pizza.getId();
        var pizzaDescription = pizza.getDescription();
        out.println("<h3 class=\"pizzaInfo__title\">"
          + pizzaName + "</h3>");
        out.println("<h3 class=\"pizzaInfo__description\">"
          + pizzaDescription + "</h2>");
        out.println("<h3 class=\"pizzaInfo__price\">"
          + pizzaPrice + "</h3>");
        out.print("<select class=\"pizzaInfo__size\">\n" +
          "    <!-- option tag starts -->\n" +
          "    <option>Choose a size</option>\n" +
          "    <option value=\"0\">Small</option>\n" +
          "    <option value=\"1\">Medium</option>\n" +
          "    <option value=\"2\">Large</option>\n" +
          "    <!-- option tag ends -->\n" +
          "  </select>");
      %>

      <section class="ingredient">
        <%
          List<IngredientSelection> ingredients = (List<IngredientSelection>) request.getAttribute("ingredients");
          var price =  request.getAttribute("price");
          System.out.println(price+"z mojego jsp posortowana");

          for (IngredientSelection ingredient : ingredients) {
            if (ingredient.isSelected()) {
              out.print("<label class=\"ingredient__element \">");
              out.print("<div class=\"switch\">");
              out.print("<input type=\"checkbox\" class=\"ingredient__checkbox\" data-price=\"" + 0 + "\" data-id=\"" + ingredient.getIngredient().getId() + "\" checked>");
              out.print("<span class=\"knob\"></span>");
              out.print("</div>");
              out.print("<h3 class=\"ingredient__name\">"+ingredient.getIngredient().getName()+"</h3>");
              out.print("</label>");
            } else {
              out.print("<label class=\"ingredient__element\">");
              out.print("<div class=\"switch\">");
              out.print("<input type=\"checkbox\" class=\"ingredient__checkbox\" data-price=\"" + ingredient.getIngredient().getPrice() + "\" data-id=\"" + ingredient.getIngredient().getId() + "\">");
              out.print("<span class=\"knob\"></span>");
              out.print("</div>");
              out.print("<h3 class=\"ingredient__name\">"+ingredient.getIngredient().getName()+"</h3>");
              out.print("</label>");
            }
          }
        %>
      </section>

      <button class="pizza__order"> add to</button>

    </section>
  </section>
</article>

<script>
    let priceItem = 0;
    const pricePizza = <%= price %>;

    console.log(typeof pricePizza);
    let pricePizza1;
    let pizzaPrice = 0;
    let originalPizza = true;
    let ingredientPrize = 0;
    let originalIngredients = [];
    let modifyIngredients = [];
    let indexInPricePizza;
    let pizzaId;
    let ingredientsCheckBox = document.getElementsByClassName("ingredient__checkbox");
    const selectElement = document.querySelector(".pizzaInfo__size");

    selectElement.addEventListener("change", (event) => {
        const numberSize = event.target.value;
        console.log(event);
        indexInPricePizza = Object.keys(pricePizza)[numberSize];
        pricePizza1 = pricePizza[indexInPricePizza]
        pizzaId = pricePizza1[0];
        //priceItem = pricePizza1[1] + ingredientPrize;
        document.querySelector(".pizzaInfo__price").innerHTML = priceItem.toFixed(2);
    });

    for (let i = 0; i < ingredientsCheckBox.length; i++) {
        ingredientsCheckBox[i].addEventListener("change", () => {
            originalPizza = false;
            let price = parseFloat(ingredientsCheckBox[i].dataset.price);
            let idIngredients = parseFloat(ingredientsCheckBox[i].dataset.id);
            if (ingredientsCheckBox[i].checked) {
                //ingredients(idIngredients, price, 0);
                ingredientPrize += price;
                firstLoad = false;
                modifyIngredientsFunc(originalIngredients, idIngredients, 0);
            } else {
                //ingredients(idIngredients, price, 1);
                modifyIngredientsFunc(originalIngredients, idIngredients, 1);
                ingredientPrize -= price;

            }

            console.log(ingredientPrize + " cena skladnikow");
            //priceItem = pricePizza1[1] + ingredientPrize;
            console.log("calkowita cena " + priceItem);
            document.querySelector(".pizzaInfo__price").innerHTML = priceItem.toFixed(2);

        })
    }

    function modifyIngredientsFunc(orgIng, idIng, type) {
        if (type === 0) {
            if (orgIng.includes(idIng)) {
                modifyIngredients = modifyIngredients.filter(val => val !== -idIng);
                console.log(idIng + "przy dodawaniu")
            } else {
                modifyIngredients.push(idIng)
            }
        } else {
            modifyIngredients = modifyIngredients.filter(val => val !== idIng);
            if (orgIng.includes(idIng)) {
                modifyIngredients.push(-idIng)
            }
        }
        modifyIngredients.forEach(i => console.log(i));
    }

    //createDefaultIngredients();

    function createDefaultIngredients() {
        for (let i = 0; i < ingredientsCheckBox.length; i++) {
            let idIngredients = parseFloat(ingredientsCheckBox[i].dataset.id);
            if (ingredientsCheckBox[i].checked) {
                ingredients(idIngredients, 0, 0);
            }
        }
    }

    function ingredients(idIngredients, price, type) {
        if (type === 0) {
            originalIngredients.push(idIngredients)
            originalIngredients.forEach(i => console.log(i));
        } else {

            originalIngredients = originalIngredients.filter(val => val !== (idIngredients * -1));
            // mapIngredients.forEach(i => console.log(i));
        }
    }

    $(".pizza__order").click(function () {
        console.log(pizzaId + "id in size");
        console.log(idDataPizza + " numer pizzy");
        console.log(modifyIngredients);
        var adressURL = "http://localhost:8090/createItem";

        console.log()
        const order = {
            "type": 0,
            "pizzaId": idDataPizza,
            "size": pizzaId,
            // // "originalIngredients": originalPizza,
            "ingredients": modifyIngredients
        };
        $.ajax({
            type: "POST",
            url: adressURL,
            data: JSON.stringify(order),
            contentType: "application/json",
            success: function (result) {
                console.log("udalo sie")
            },
            error: function (error) {
                console.error("Ajax request failed: " + error);
            }
        });
    });
</script>