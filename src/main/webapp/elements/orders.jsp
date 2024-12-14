<%--
  Created by IntelliJ IDEA.
  User: lenda
  Date: 25.11.2024
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../views/commonHead.jsp" %>
<body>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>

<%@ include file="../views/commonNavbar.jsp" %>
</body>
<main class="main">
  <article class="main__box">
    <script>
        async function fetchOrders() {
            try {
                const response = await fetch('http://localhost:8090/ordersJSON');
                const orders = await response.json();
                console.log(orders);
                displayOrders(orders);

                accordionFunctionality();
                eventListenerInPayBtn();
            } catch (e) {
                console.log("Error name:", e.name);
                console.log("Error message:", e.message);
                console.log("Stack trace:", e.stack);
            }
        }

        let mainBox = document.getElementsByClassName("main__box")[0];

        function displayOrders(orders) {
            let divOrders = document.createElement('section');
            divOrders.setAttribute('class', 'Orders accordion');
            for (let i = 0; i < orders.length; i++) {
                let divOrder;
                divOrder = document.createElement('article');
                divOrder.setAttribute('class', 'Order accordion-item');
                let divTitle;
                divTitle = document.createElement('h3');
                divTitle.setAttribute('class', 'Order__title accordion-item-header');
                divTitle.textContent = orders[i].id + "  " + orders[i].date + "   Items: " + orders[i].items.length + " total: " + orders[i].total_price + " pay: " + orders[i].is_paid;
                let payButton;
                let formPost;
                let is_paid = orders[i].is_paid;
                if (!is_paid && is_paid != null) {
                    payButton = document.createElement('button');
                    //payButton.href="http://localhost:8090/getPay?id="+orders[i].id
                    payButton.setAttribute('class', 'Order_payBtn');
                    //payButton.target = "_blank";
                    payButton.setAttribute('data-id', orders[i].id);
                    payButton.textContent = 'pay';
                    divOrder.appendChild(payButton);
                }
                let detailsItem;
                detailsItem = document.createElement('p');
                detailsItem.setAttribute('class', 'Order__details accordion-item-body');
                if (orders[i].items != null) {
                    createItems(orders[i].items, detailsItem);
                }

                divOrder.appendChild(divTitle);
                divOrder.appendChild(detailsItem);
                divOrders.appendChild(divOrder);
            }
            mainBox.appendChild(divOrders);
        }

        // let payBtn = document.getElementsByClassName('Order_payBtn');
        // console.log(payBtn);

        function eventListenerInPayBtn() {
            document.querySelectorAll('.Order_payBtn').forEach(function (element) {
                element.addEventListener('click', function () {
                    idDataPizza = this.dataset.id;
                    getPay(idDataPizza);
                });
            });
        }
        function getPay(idPizza) {
            fetch('/payForm', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id: idPizza })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('HTTP error! status: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log("Otrzymane dane:", data);
                    console.log("URL:", data.paymentUrl);
                    console.log("Kwota:", data.amount);
                    getPay(data.amount)
                    window.location.href = data.paymentUrl;
                })
                .catch(error => console.error('Błąd:', error));


        }

        function createItems(items, detailsItem) {
            for (let j = 0; j < items.length; j++) {
                createItem(items[j], detailsItem);
            }
        }

        function createPizzaName(item) {
            let pizzaName = document.createElement('span');
            pizzaName.setAttribute('class', 'Order__pizzaName');
            pizzaName.textContent = item.pizzaName;
            return pizzaName;
        }

        function createPizzaSize(item) {
            let pizzaSize = document.createElement('span');
            pizzaSize.setAttribute('class', 'Order__pizzaSize');
            pizzaSize.textContent = item.size.toLowerCase();
            return pizzaSize;
        }

        function createPizzaTotalPrice(item) {
            let pizzaTotalPrice = document.createElement('span');
            pizzaTotalPrice.setAttribute('class', 'Order__pizzaTotalPrice');
            pizzaTotalPrice.textContent = item.totalPrice + " zł";
            return pizzaTotalPrice;
        }

        function createItem(item, detailsItem) {
            let pizzaName = createPizzaName(item);
            let pizzaSize = createPizzaSize(item);
            let itemOrder = document.createElement('span');
            itemOrder.setAttribute('class', 'Order__item');
            let pizzaTotalPrice = createPizzaTotalPrice(item);
            let defaultIngredients;
            if (item.defaultIngredients && item.defaultIngredients.length > 0) {
                defaultIngredients = createDefaultIngredients(item.defaultIngredients);
            }
            let modifyIngredients;
            itemOrder.appendChild(pizzaName);

            itemOrder.appendChild(pizzaSize);
            itemOrder.appendChild(pizzaTotalPrice);
            itemOrder.appendChild(defaultIngredients);
            if (item.modifyIngredients != null && item.modifyIngredients.length > 0) {
                modifyIngredients = createModifyIngredients(item.modifyIngredients);
                itemOrder.appendChild(modifyIngredients);
            }


            detailsItem.appendChild(itemOrder);

        }

        function createModifyIngredientName(ingredient) {
            let modifyIngredientName = document.createElement('span');
            modifyIngredientName.setAttribute('class', 'Order__modifyIngredientName');
            modifyIngredientName.textContent = ingredient.name;
            return modifyIngredientName;
        }

        function createModifyIngredientAction(ingredient) {
            let modifyIngredientAction = document.createElement('span');
            modifyIngredientAction.setAttribute('class', 'Order__modifyIngredientAction');
            modifyIngredientAction.textContent = ingredient.action.toLowerCase();
            return modifyIngredientAction;
        }

        function createModifyIngredients(modifyIngredients) {
            let modifyIngredientsContainer = null;
            modifyIngredientsContainer = document.createElement('span');
            modifyIngredientsContainer.setAttribute('class', 'Order__modifyIngredients');
            for (let k = 0; k < modifyIngredients.length; k++) {
                let modifyIngredient = document.createElement('span');
                modifyIngredient.setAttribute('class', 'Order__modifyIngredient');
                let modifyIngredientName = createModifyIngredientName(modifyIngredients[k]);
                let modifyIngredientAction = createModifyIngredientAction(modifyIngredients[k]);
                modifyIngredient.appendChild(modifyIngredientName);
                modifyIngredient.appendChild(modifyIngredientAction);
                modifyIngredientsContainer.appendChild(modifyIngredient);
            }
            return modifyIngredientsContainer;
        }

        function createDefaultIngredient(defaultIngredientEl) {
            let defaultIngredient;
            defaultIngredient = document.createElement('span');
            defaultIngredient.setAttribute('class', 'Order__defaultIngredient');
            defaultIngredient.textContent = defaultIngredientEl.name;
            return defaultIngredient;
        }

        function createDefaultIngredients(defaultIngredients) {
            let defaultIngredientContainer = null;
            defaultIngredientContainer = document.createElement('span');
            defaultIngredientContainer.setAttribute('class', 'Order__defaultIngredients');
            for (let k = 0; k < defaultIngredients.length; k++) {
                let defaultIngredient = createDefaultIngredient(defaultIngredients[k]);
                defaultIngredientContainer.appendChild(defaultIngredient)
            }
            return defaultIngredientContainer;
        }

        function accordionFunctionality() {
            const accordionItemHeaders = document.querySelectorAll(".accordion-item-header");
            accordionItemHeaders.forEach(accordionItemHeader => {
                accordionItemHeader.addEventListener("click", event => {
                    accordionItemHeader.classList.toggle("active");
                    const accordionItemBody = accordionItemHeader.nextElementSibling;
                    if (accordionItemHeader.classList.contains("active")) {
                        accordionItemBody.style.maxHeight = accordionItemBody.scrollHeight + "px";
                    } else {
                        accordionItemBody.style.maxHeight = 0;
                    }
                });
            });
        }

        document.addEventListener('DOMContentLoaded', fetchOrders);
    </script>
  </article>
</main>
</html>
