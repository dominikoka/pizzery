<!DOCTYPE html>
<html>
<%@ include file="views/commonHead.jsp" %>
<body>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>

<%@ include file="views/commonNavbar.jsp" %>

<main class="main">
  <article class="main__box">
  </article>
  <article class="pizzaContainer"></article>
</main>
</body>
<script>
    // async function fetchOrders() {
    //     try {
    //         const response = await fetch('http://localhost:8090/bucket');
    //         const bucket = await response.json();
    //         console.log(bucket);
    //
    //     } catch (e) {
    //         console.log("Error name:", e.name);
    //         console.log("Error message:", e.message);
    //         console.log("Stack trace:", e.stack);
    //     }
    // }
    function loadPizzas() {
        var adressURL = "http://localhost:8090/loadPizzas";
        console.log("Page loaded, making AJAX request");
        $.ajax({
            type: "GET",
            url: adressURL,
            data: {},
            success: function (result) {
                $(".main__box").html(result);
            },
            error: function (error) {
                console.error("Ajax request failed: " + error);
            }
        });
    }

    // $(".bucket__btn").click(function () {
    //     var adressURL = "http://localhost:8090/bucket";
    //     $.ajax({
    //         type: "GET",
    //         url: adressURL,
    //         data: {},
    //         success: function (result) {
    //             $(".main__box").html(result);
    //         },
    //         error: function (error) {
    //             console.error("Ajax request failed: " + error);
    //         }
    //     });
    // });

    $(document).ready(function () {
        loadPizzas();
        // loadSportOnPageLoad();
    });
</script>
</html>
