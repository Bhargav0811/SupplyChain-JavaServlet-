function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}
$(window).scroll(function(e){ 
  var $el = $('.categ'); 
  var isPositionFixed = ($el.css('position') == 'fixed');
  if ($(this).scrollTop() > 660){ 
    $el.css({'position': 'fixed', 'top': '0px'}); 
  }
  if ($(this).scrollTop() < 660){
    $el.css({ 'position': 'absolute', 'bottom': '0px', 'top': '' }); 
  }
});

function scrollPage(O)
{
//   console.log(O);
//   document.getElementById(O.name).scrollIntoView({
//     behavior: "smooth"
// });
var element = document.getElementById(O.name);
    var headerOffset = 120;
    var elementPosition = element.getBoundingClientRect().top;
    var offsetPosition = elementPosition + window.pageYOffset - headerOffset;
    window.scrollTo({
         top: offsetPosition,
         behavior: "smooth"
    });
}

document.getElementById('Username').innerHTML="Mr. Bhargav Patel"