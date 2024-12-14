<header class="navbar">
  <nav class="navbar__nav">
    <ul class="navbar__ul">
      <li class="navbar__li"><a href="http://localhost:8090/" class="navbar__li">Pizza</a></li>
      <li class="navbar__li"><a href="#" class="navbar__li">For child</a></li>
      <li class="navbar__li"><a href="#" class="navbar__li">Burgers</a></li>
      <li class="navbar__li"><a href="http://localhost:8090/" class="navbar__li">Salad</a></li>
            <li class="navbar__li"><a href="#" class="navbar__li bucket__btn" id="bucket__btn">Bucket</a></li>
<%--      <button class="navbar__li bucket__btn" id="bucket__btn">bucket</button>--%>

      <li class="navbar__li"><a href="http://localhost:8090/orders" class="navbar__li bucket__btn">Orders</a></li>
    </ul>
  </nav>
</header>
<script>
    //document.getElementById("bucket__btn").addEventListener('click', fetchBucket);

    // async function fetchBucket() {
    //     try {
    //         const response = await fetch('http://localhost:8090/bucket');
    //         const bucket = await response.json();
    //         console.log(bucket);
    //     } catch (e) {
    //         console.log("Error name:", e.name);
    //         console.log("Error message:", e.message);
    //         console.log("Stack trace:", e.stack);
    //     }
    // }
    $(".bucket__btn").click(function () {
        var adressURL = "http://localhost:8090/bucket";
        $.ajax({
            type: "GET",
            url: adressURL,

            contentType: "application/json",
            success: function (result) {
                $(".main__box").empty();
                $(".main__box").html(result);

            },
            error: function (error) {
                console.error("Ajax request failed: " + error);
            }
        });
    });
</script>