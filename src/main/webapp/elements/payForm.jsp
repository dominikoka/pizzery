<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<%@ include file="../views/commonHead.jsp" %>
<body>
<%@ include file="../views/commonNavbar.jsp" %>


<%
  Double amount = (Double) session.getAttribute("amount");
  if (amount == null) {
    amount = 0.0;
  }
%>
<section class="form">
  <form name="myForm" class="form__box" action="/getPay" method="post">

    <label for="fname">First name:</label>
    <input class="form__input" type="text" id="fname" name="fname" required><br><br>

    <label for="lname">Last name:</label>
    <input  class="form__input" type="text" id="lname" name="lname" required><br><br>

    <label for="address">Address:</label>
    <input  class="form__input" type="text" id="address" name="address" required><br><br>

    <label for="price">Price:</label>
    <input  class="form__input" type="number" id="price" name="price" min="0" step="0.01" value="<%= amount %>" readonly required><br><br>

    <label for="phone">Wprowadź swój numer telefonu, jeśli chcesz otrzymać SMS-a o złożonym zamówieniu:</label><br>
    <input  class="form__input" type="tel" id="phone" name="phone" placeholder="123-456-789" pattern="[0-9]{3}-[0-9]{3}-[0-9]{3}" required>

    <label for="email">Wprowadź swój adres e-mail, aby otrzymać potwierdzenie zamówienia:</label><br>
    <input  class="form__input" type="email" id="email" name="email" placeholder="example@email.com" required><br><br>

    <label for="discountCode">Wprowadź kod rabatowy:</label><br>
    <input  class="form__input" type="text" id="discountCode" name="discountCode" placeholder="Twój kod rabatowy" required>
    <button type="button" onclick="rabat()" id="applyDiscount">Zastosuj kod</button>
    <br><br>


    <input type="button" onclick="formSubmit()" value="wyslij">
  </form>
</section>

</body>
<script>
    function getRabat() {
        let rabatText = document.getElementById("discountCode").value;
        return rabatText;
    }
    let defaultPrice = 0;
    function rabat() {
        fetch('http://localhost:8090/getRabat',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    //'Origin': 'http://localhost:8090'
                },
                body: JSON.stringify({
                    rabat: getRabat()
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('HTTP error! status: ' + response.status);
                }
                console.log("moje response " + response)
                return response.json();
            })
            .then(data => {
                document.getElementById("price").value = data.result;

            })
            .catch(error => {
                console.error('Błąd:', error);
            });
    }


    //console.log("Otrzymany JSON:", responseData);

    function getMail() {
        let fName = document.getElementById("email").value;
        console.log(fName);
        return fName;
    }

    function getPhoneNb() {
        let fName = document.getElementById("phone").value;
        console.log(fName);
        return fName;
    }

    function getFirstName() {
        let fName = document.getElementById("fname").value;
        console.log(fName);
        return fName;
    }

    function getSecondName() {
        let fName = document.getElementById("lname").value;
        console.log(fName);
        return fName;
    }

    function getAdress() {
        let fName = document.getElementById("address").value;
        console.log(fName);
        return fName;
    }

    function formSubmit() {

        //fetch('http://localhost:8090/payForm',
        fetch('http://localhost:8090/getPay',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Origin': 'http://localhost:8090'
                },
                body: JSON.stringify({
                    name: getFirstName(),
                    lname: getSecondName(),
                    adress: getAdress(),
                    phone: getPhoneNb(),
                    email: getMail()
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('HTTP error! status: ' + response.status);
                }
                console.log("moje response " + response)
                return response.text();
            })
            .then(data => {
                console.log("Otrzymany URL:", data);
                //window.location.href = data;
                window.open(
                    data,
                    '_blank'
                );
                console.log('Sukces:', data);
            })
            .catch(error => {
                console.error('Błąd:', error);
            });
    }
</script>