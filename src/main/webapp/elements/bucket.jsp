<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="com.google.gson.JsonElement" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.gson.JsonArray" %>
<h3>to je bucket</h3>
<section class="bucket">
  <section class="bucket__box">
    <script>
        loadBucketItems();

        function loadBucketItems() {
            if (typeof bucketData === "undefined") {
                var bucketData = JSON.parse('<%= request.getAttribute("bucket") %>');
                var bucket = bucketData.orders;
                var bucketB = document.getElementsByClassName("bucket__box")[0];
                createBucket(bucket, bucketB)
            } else {

                bucketData = JSON.parse('<%= request.getAttribute("bucket") %>');
                bucket = bucketData.orders;
                bucketB = document.getElementsByClassName("bucket__box")[0];
                createBucket(bucket, bucketB)
            }
        }

        function createElement(createElement, atr1, atr2, textContent) {
            let element = document.createElement(createElement);
            element.setAttribute(atr1, atr2);
            if (textContent) {
                element.textContent = textContent;
            }
            return element;
        }

        function createBucket(bucket, bucketBox) {
            for (let i = 0; i < bucket.length; i++) {
                console.log(bucket[i]);
                let item = createElement("article", "class", "BItem");
                let nameItem = createElement("h3", "class", "BItem__name", bucket[i].name);
                let descItem = createElement("p", "class", "BItem__desc", bucket[i].desc);
                let sizeItem = createElement("p", "class", "BItem__size", bucket[i].size);
                let priceItem = createElement("p", "class", "BItem__price", bucket[i].price);
                let defaultIng = createElement("p", "class", "BItem__defaultIng", bucket[i].defaultIngredients);
                let extraIng = createElement("p", "class", "BItem__extraIng", bucket[i].extraIngredients);
                let removeBtn = createElement("button", "class", "BItem__removeItem", "remove item");
                removeBtn.setAttribute('data-id', i);
                item.appendChild(nameItem);
                item.appendChild(descItem);
                item.appendChild(sizeItem);
                item.appendChild(priceItem);
                item.appendChild(defaultIng);
                item.appendChild(extraIng);
                item.appendChild(removeBtn);
                bucketBox.appendChild(item);
            }
        }

        function setEmptyItemBox() {
            $(".bucket__box").empty();
        }

        function asd(id) {
            removeItemFromBucket(id);
            setEmptyItemBox();
            loadBucketItems();
            eventListenerOnButtons();

            console.log("dziaua" + id);
        }

        eventListenerOnButtons();

        function eventListenerOnButtons() {
            document.querySelectorAll('.BItem__removeItem').forEach(function (element) {
                element.addEventListener('click', function () {
                    let id = this.dataset.id
                    removeItemFromBucket(id);
                });
            });
        }

        function removeItemFromBucket(id) {
            var adressURL = "http://localhost:8090/RemoveItemBucket";
            let json = {"id": id};
            $.ajax({
                type: "POST",
                url: adressURL,
                data: JSON.stringify(json),
                contentType: "application/json",
                success: function (result) {
                    console.log("udalo sie")
                },
                error: function (error) {
                    console.error("Ajax request failed: " + error);
                }
            });
        }

        $(".bucket__order").click(function () {
            const createOrderUrl = "http://localhost:8090/createOrder";
            const payFormUrl = "http://localhost:8090/payForm";
            $.ajax({
                type: "POST",
                url: createOrderUrl,
                success: function (result) {
                    const idOrder = result.idOrder; // Musisz upewnić się, że serwer zwraca idOrder
                    fetch(payFormUrl, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({id: idOrder}) // Przekazanie idOrder do payForm
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error("Błąd podczas pobierania danych płatności.");
                            }
                            return response.json();
                        })
                        .then(data => {
                            console.log("Otrzymany JSON z /payForm:", data);
                            window.location.href = data.paymentUrl;
                        })
                        .catch(error => {
                            console.error("Błąd podczas przetwarzania płatności:", error);
                            alert("Wystąpił problem z płatnością. Spróbuj ponownie.");
                        });
                },
                error: function (xhr, status, error) {
                    console.error("Błąd podczas tworzenia zamówienia:", status, error);
                    alert("Wystąpił problem z utworzeniem zamówienia. Spróbuj ponownie.");
                }
            });
        });


    </script>
  </section>
  <button class="bucket__order">do order</button>


</section>
