const BTN_SUBSCRIBE = "btn-subscribe";

document.onreadystatechange = () => {
    let btnSubscribe = document.getElementById(BTN_SUBSCRIBE);
    if (btnSubscribe) {
        btnSubscribe.addEventListener("click", subscribe);
    }
    function subscribe(event) {
        window.location.href = "/inscription";
    }
}