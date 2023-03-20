/**
 * 
 */
window.addEventListener("scroll",function(){
	var a=document.getElementsByClassName("abouts");
	for(let i=0;i<a.length;i++)
		a[i].style.display="block";
    document.getElementById("aboutDiv").classList.add("anime");
})
function login(){
    document.getElementById("box1").style.display="block";
    document.getElementById("mainContainer").style.filter="blur(5px)";
    document.getElementById("mainContainer").style.position="fixed";
    document.getElementById("mainContainer").style.pointerEvents="none";  
}
function signUp(){
	document.getElementById("box2").style.display="block";
    document.getElementById("mainContainer").style.filter="blur(5px)";
    document.getElementById("mainContainer").style.position="fixed";
    document.getElementById("mainContainer").style.pointerEvents="none";  
}
function signUpProcess(){
	var obj={};
	obj.Name=document.getElementById("signName").value;
	obj.Region=document.getElementById("regions").value;
	obj.Number=document.getElementById("number").value;
	obj.Password=document.getElementById("signPassword").value;
	var confirmPassword=document.getElementById("confirmPassword").value;
	obj.childName=document.getElementById("childName").value;
	obj.childClass=document.getElementById("childClass").value;
	obj.childAge=document.getElementById("childAge").value;
	obj.childSchool=document.getElementById("childSchool").value;
	if(obj.Password==confirmPassword){
		var xhr=new XMLHttpRequest();
		xhr.open("POST","./Signup");
		xhr.setRequestHeader("Content-Type","application/json");
		xhr.send(JSON.stringify(obj));
		xhr.onreadystatechange=function(){
			if(this.status==200 && this.readyState==4){
				var obj=JSON.parse(this.responseText);
				if(obj.result=="signed up successfully"){
					document.getElementById("box2").innerHTML='<h1 style="margin: 29%;">Your user id='+obj.id+'<br><span onclick="signInform()" style="color:#767672">Click here to login</span></h1>'
				}
			}
		}
	}
}
function returnOne(){
	document.getElementById("box1").style.display="none";
	document.getElementById("box2").style.display="none";
    document.getElementById("mainContainer").style.filter="blur(0px)";
    document.getElementById("mainContainer").style.position="static";
    document.getElementById("mainContainer").style.pointerEvents="auto";  
}

function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for(let i = 0; i <ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}
function validate(){
	var xml=new XMLHttpRequest();
	var params={};
	params.name=document.getElementById("name").value;
	params.password=document.getElementById("password").value;
	xml.onreadystatechange = function() {
		if(this.readyState == 4) {
			if(this.status == 200) {
				if(this.responseText=="mother login" || this.responseText=="admin login"){
					window.onunload = function () { null };
					window.location.href="./index1.html";
				}
				else{
					document.getElementById("result").innerHTML="<center><h2 style=\"margin:auto\">"+this.responseText+"</h2></center>";
				}
			}
		}
	}
	xml.open("POST","./LoginValidation");
	xml.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xml.send("username="+params.name+"&password="+params.password);
}
function first(){
	document.getElementById("box2").style.display="none";
	var a=getCookie("SessionId");
	if(a!=""){
		var xhr=new XMLHttpRequest();
		xhr.open("POST","./CookieValidation");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send("sessionId="+a);
		xhr.onreadystatechange=function(){
		if(this.readyState==4){
			if(this.status==200 && this.responseText!="no Cookie exists"){
				window.location.href="./index1.html";
			}
		}
	}
	}
	var today = new Date();
	var date = today.getDate();
	var month = today.getMonth() + 1;
	var year = today.getFullYear()-3;
	if (date < 10) 
  		date = '0' + date;
	if (month < 10) 
	   month = '0' + month;
	document.getElementById("childAge").max=year+'-'+month+'-'+date;
	document.getElementById("childAge").min=(year-9)+'-01-01';
}
function signUpform(){
	document.getElementById("box1").style.display="none";
	document.getElementById("box2").style.display="block";
}
function signInform(){
	document.getElementById("box1").style.display="block";
	document.getElementById("box2").style.display="none";
}
var addchildId=1;
function addChild(){
	console.log(document.getElementById("box2").offsetWidth);
	if(addchildId<4){
	document.getElementById("box2").style.width=((document.getElementById("box2").offsetWidth)+300)+"px";
	document.getElementById("child").innerHTML+='<div><h1>Child Details</h1><input type="text" id="password" placeholder="Name" class="input"><br><input type="text" id="password" placeholder="Age" class="input"><br><input type="text" id="password" placeholder="Class" class="input"><br><input type="text" id="password" placeholder="School name" class="input"><br></div>';
		if(addchildId==3){
			document.getElementById("child").innerHTML+="<h1>Maximum limit reached</h1>"
		}
	addchildId++;
	}
}
function removechild(){
	document.getElementById("box2").style.width=((document.getElementById("box2").offsetWidth)-300)+"px";
	console.log(document.getElementById("child"))
	document.getElementById("child").lastChild.remove();
}
