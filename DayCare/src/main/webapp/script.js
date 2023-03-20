/**
 * 
 */
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



var daycareList;//daycarelist
var daycareIndex;
var nameOfPerson="";
var notificationLength=0;

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




















//js for 2nd html page
var totalNotifications={};
function init(){
	var a=getCookie("SessionId");
	if(a==""){
		window.location.href="./index.html";
	}else{
		if(a!=""){
		var xhr=new XMLHttpRequest();
		xhr.open("POST","./CookieValidation");
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send("sessionId="+a);
		xhr.onreadystatechange=function(){
			if(this.readyState==4){
				if(this.status==200 && this.responseText!="no Cookie exists"){
					var obj=JSON.parse(this.responseText);
					if(obj.Message!="Cookie doesn't exist"){
						if(obj.role=="mother"){
						var list=document.getElementsByClassName("menulist");
						for(let i=0;i<list.length;i++){
							if(i>=4 && i<7){
								list[i].style.display="none";
							}
							else{
								list[i].style.display="block";
//								list[i].style.marginTop="9%";
							}
						}
						}
						else if(obj.role=="admin"){
							var list=document.getElementsByClassName("menulist");
							for(let i=0;i<list.length;i++){
								if(i>=0 && i<=3)
									list[i].style.display="none";
								else
									list[i].style.display="block"; 
							}
							var xhr=new XMLHttpRequest;
							xhr.open("POST","./admin/GetNotifications");
							xhr.send();
							xhr.onreadystatechange=function(){
								if(this.readyState==4 && this.status==200){
									totalNotifications=JSON.parse(this.responseText);
								}
							}
							document.getElementById("titles").innerHTML+='<lord-icon src="https://cdn.lordicon.com/msetysan.json" trigger="hover" style="width:90px;height:60px" onclick="notifications()" id="lord"></lord-icon>'
							setInterval(function(){
								var xhr=new XMLHttpRequest;
								xhr.open("POST","./admin/GetNotifications");
								xhr.send();
								xhr.onreadystatechange=function(){
									if(this.readyState==4 && this.status==200){
										var oldNotification=totalNotifications.length;
										totalNotifications=JSON.parse(this.responseText);
										console.log(totalNotifications.length>oldNotification)
										if(totalNotifications.length>oldNotification){
											document.getElementById("lord").colors="primary:#c71f16";
										}
									}
								}
								
							},5000)
						}
//						document.getElementById("welcome").innerText+="\n"+obj.name.toUpperCase();
						nameOfPerson=obj.name;	
						var h1=document.createElement("h1");
						h1.innerText="Hello "+nameOfPerson;
						document.getElementById("small").insertBefore(h1,document.getElementById("small").children[1]);		
					}
					
				}
			}
		}
		}
	}
//	var menu=document.getElementById("menu");
//	document.getElementById("xmark").style.display="none";
//	menu.style.display="none";
}
function notifications(){
	bgChange();
	document.getElementById("lord").colors="primary:#121331";
	document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="back()"></i><div id="listView"></div>';
	for(let i=totalNotifications.length-1;i>=0;i--){
		console.log(i)
		document.getElementById("listView").innerHTML+='<div class="listDiv"><img class="daycareImage" src="img.png"><div><h2>Mother Name:'+totalNotifications[i].motherRegno+'</h2><h2>DayCare Name:'+totalNotifications[i].daycareId+'</h2><h2>Date:'+totalNotifications[i].date+'</h2><h2>Region:'+totalNotifications[i].region+'</h2></div></div>'
	}
	
}
function pointing(index){
	var a=document.getElementsByClassName("menulist");
	for(let i=0;i<a.length;i++){
		a[i].classList.remove("pointing");
	}
	if(index>=0)
		a[index].classList.add("pointing");
}
//var i=0;
//function menuBar(){
//	console.log(document.getElementsByTagName("body"))
//    if(i==0){
//        menu.style.display="block";
//        document.getElementById("ham").style.display="none";
//        document.getElementById("xmark").style.display="block";
//		i=1;
//    }
//    else{
//        menu.style.display="none";
//        document.getElementById("ham").style.display="block";
//        document.getElementById("xmark").style.display="none";
//        i=0;
//    }
//}
function logout(){
	var xhr=new XMLHttpRequest();
	xhr.onreadystatechange=function(){
		if(this.readyState==4){
			if(this.status==200){
				window.onunload = function () { null };
				window.location.href="./index.html";
			}
		}
	}
	xhr.open("POST","./Logout");
	xhr.send();
}
function enrollView(){
	bgChange();
	var xml=new XMLHttpRequest();
	xml.onreadystatechange = function() {
		if(this.readyState == 4) {
			if(this.status == 200) {
				daycareList=JSON.parse(this.responseText);
//				var div= document.createElement("div");
//				div.setAttribute("id","subDiv");
//				document.body.insertBefore(div,document.body.children[1]);
				document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="back()"></i><div id="listView"></div>';
				for(let i=1;i<daycareList.length;i++){
					document.getElementById("listView").innerHTML+='<div class="listDiv"><img class="daycareImage" src="img.png"><div><h2>Name:'+daycareList[i].name+'</h2><h2>Region:'+daycareList[i].region+'</h2><h2>Number:'+daycareList[i].number+'</h2><h2>Fees:'+daycareList[i].fee+'</h2></div><button class="enrollButton" id='+i+' onclick="enroll(this.id)">Enroll</button><div data-id='+i+' class="comment" onclick="viewComments(this.dataset.id)">View Comments</div></div>'
//					document.getElementById("listView").innerHTML+='<div class="listDiv"><div><h2>Name:'+daycareList[i].name+'</h2><h2>Region:'+daycareList[i].region+'</h2><h2>Number:'+daycareList[i].number+'</h2><h2>Fees:'+daycareList[i].fee+'</h2></div><div><h2>Child details</h2><h3>Name:'+daycareList[i].childName+'</h3><h3>Age:'+daycareList[i].childAge+'</h3><h3>Class:'+daycareList[i].childClass+'</h3><h3>School:'+daycareList[i].childSchool+'</h3></div><button class="enrollButton" id='+i+' onclick="enroll(this.id)">Enroll</button><div data-id='+i+' class="comment" onclick="viewComments(this.dataset.id)">View Comments</div></div>'
				}
				if(daycareList[0]!=null){
					var p=document.createElement("h1");
					p.setAttribute("id","info");
					p.innerHTML='You can\'t enroll a dayacare, becacuse you are already enrolled a daycare,please go "<span style="color:#767672" onclick="rateView()">To rate</span>" to view your daycare details';
					document.getElementById("listView").prepend(p);
					var li=document.getElementsByClassName("enrollButton");
					for(let i=0;i<li.length;i++){
						li[i].style.display="none";
					}
				}
			}
		}
	}
	xml.open("POST","./user/ViewDaycares");
	xml.send();
}
function bgChange(){
	document.getElementById("body").style.backgroundImage='url("")';
//	document.getElementById("welcome").style.display="none";	
	document.getElementById("body").style.backgroundColor='#ffffff';
//	document.getElementById("body").style.backgroundAttachment="fixed";
//	document.getElementById("body").style.backgroundSize="100%";
}
function back(){
	pointing(-1);
	document.getElementById("body").style.background='url("daycare.jpg")';
	document.getElementById("body").style.backgroundRepeat="no-repeat";
	document.getElementById("body").style.backgroundSize="100%";
//	document.getElementById("welcome").style.display="block";
	document.getElementById("icon1").remove();
	document.getElementById("subDiv").innerHTML="";
	document.getElementsByClassName("confirmPop")[0].remove();
}	
function back2(){
	pointing(-1);
	document.getElementById("body").style.background='url("daycare.jpg")';
	document.getElementById("body").style.backgroundRepeat="no-repeat";
	document.getElementById("body").style.backgroundSize="100%";
//	document.getElementById("welcome").style.display="block";
	document.getElementById("icon1").remove();
	document.getElementById("heading").remove();
	document.getElementById("rateViewDiv").remove();
	if(document.getElementById("pop")!=null)
		document.getElementById("pop").remove();
}
function back3(){
	pointing(-1);
	document.getElementsByClassName("edit")[0].remove();
	document.getElementById("body").style.background='url("daycare.jpg")';
	document.getElementById("body").style.backgroundRepeat="no-repeat";
	document.getElementById("body").style.backgroundSize="100%";
//	document.getElementById("welcome").style.display="block";
	document.getElementById("icon1").remove();
	document.getElementById("heading").remove();
	document.getElementById("details").remove();
}
function no(){
	document.getElementsByClassName("confirmPop")[0].remove();
	document.getElementById("listView").style.display="block";
}
function yes(){
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./user/Enroll");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("daycareId="+daycareList[daycareIndex].registerNo);
	xhr.onreadystatechange=function (){
		if(this.readyState==4 && this.status==200){
			if(this.responseText=="enrolled successfully"){
				document.getElementsByClassName("confirmPop")[0].innerHTML="<h1 style='text-align: center;margin-top: 15%;'>You are successfully enrolled a daycare</h1><button id='doneButton' onclick='back()'>Done</button>";
			}
		}
	}
}
function enroll(id){
	daycareIndex=id;
	document.getElementById("listView").style.display="none";
	var a=document.createElement("div");
	a.classList.add("confirmPop");
	document.getElementById("body").appendChild(a);
	var p=document.createElement("h2")
	p.classList.add("textConfirm");
	a.appendChild(p);
	p.innerText="Do you want to enroll?";
	var button=document.createElement("button");
	button.innerText="NO";
	button.setAttribute("onclick","no()");
	button.classList.add("noConfirm");
	a.appendChild(button);
	button=document.createElement("button");
	button.innerText="YES";
	button.setAttribute("onclick","yes()");
	button.classList.add("yesConfirm");
	a.appendChild(button);
}
function rateView(){
	bgChange();
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./user/EnrolledDaycare");
	xhr.send();
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			console.log(this.responseText=="[]")
			if(this.responseText=="[]"){
				document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="back2()"></i><h1 id="heading">Your are not enrolled any daycare</h1>';
			}
			else{
				var arr=JSON.parse(this.responseText);
				document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="back2()"></i><h1 id="heading">Your Daycare:</h1><div id="rateViewDiv"><div><h2>Name:'+arr[0].name+'</h2><h2>Region:'+arr[0].region+'</h2><h2>Number:'+arr[0].number+'</h2><h2>Fees:'+arr[0].fee+'</h2></div>';
				for(let i=1;i<arr.length;i++){
					document.getElementById("rateViewDiv").innerHTML+='<div><h2>Child details</h2><h3>Name:'+arr[i].childName+'</h3><h3>Age:'+arr[i].childAge+'</h3><h3>Class:'+arr[i].childClass+'</h3><h3>School:'+arr[i].childSchool+'</h3></div><button id="rateButton" onclick="rateBut()">Rate</button>';
				}
			}
		}
	}
}
function rateButton(){
	var comment=document.getElementById("comment").value;
	var stars=document.getElementsByClassName("star");
	var starCount=0;
	for(let i=0;i<stars.length;i++){
		if(stars[i].style.color=="gold"){
			starCount++;
		}
	}
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./user/RateDaycare");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("comment="+comment+"&starCount="+starCount);
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			document.getElementById("pop").remove();
			var a=document.createElement("div");
			a.classList.add("confirmPop");
			document.getElementById("body").appendChild(a);
			document.getElementsByClassName("confirmPop")[0].innerHTML="<h1 style='text-align: center;margin-top: 15%;'>Review submitted successfully</h1><button id='doneButton' onclick='back()'>Done</button>";
		}
	}
}
function rateBut(){
	document.getElementById("rateViewDiv").style.display="none";
	document.getElementById("heading").style.display="none";
	document.getElementById("body").innerHTML+='<div id="pop"><div style="margin: 8% 0 0 33%;"><i class="fa-solid fa-star star" onclick="starColor(1)"></i><i class="fa-solid fa-star star" onclick="starColor(2)"></i><i class="fa-solid fa-star star" onclick="starColor(3)"></i><i class="fa-solid fa-star star" onclick="starColor(4)"></i><i class="fa-solid fa-star star" onclick="starColor(5)"></i></div><textarea type="text" id="comment" required placeholder="write your comments"></textarea><br><button id="submitButton" onclick="rateButton()">Submit</button></div>'
}
function starColor(no){
        var stars=document.getElementsByClassName("star");
        for(let i=0;i<stars.length;i++){
            stars[i].style.color="#000000";
        }
        for(let i=0;i<no;i++){
            stars[i].style.color="gold";
        }
}
var details;
function detail(){
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./user/ViewMyDetails");
	xhr.send();
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			details=JSON.parse(this.responseText)
			bgChange();
			document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="back3()"></i><h1 id="heading">Your Details:</h1><i class="fa-solid fa-pen-to-square edit" onclick="editMe()"></i><div id="details"><img class="daycareImage" src="img.png"><div><h1>Name:'+details[0].name+'</h1><h1>Id:'+details[0].registerNo+'</h1><h1>Region:'+details[0].region+'</h1><h1>Number:'+details[0].number+'</h1></div></div>';
			for(let i=1;i<details.length;i++){
				document.getElementById("details").innerHTML+='<div><h2>Child Name:'+details[i].childName+'</h2><h2>Age:'+details[i].childAge+'</h2><h2>Class:'+details[i].childClass+'</h2><h2>School:'+details[i].childSchool+'</h2></div>';
			}
		}
	}
}

function viewComments(id){
	var comments=document.getElementsByClassName("commentStyle");
	var a=comments.length;
	for(let i=0;i<a;i++){
		comments[0].remove();
	}
	var commentsButton=document.getElementsByClassName("comment");
	for(let i=0;i<commentsButton.length;i++){
		commentsButton[i].innerHTML="Show Comments";
		commentsButton[i].setAttribute("onclick","viewComments(this.dataset.id)");
	}
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./user/ViewComments");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("daycareId="+daycareList[id].registerNo);
	xhr.onreadystatechange=function (){
		if(this.readyState==4 && this.status==200){
			console.log(this.responseText)
			var commentList=JSON.parse(this.responseText);
			var listDiv=document.getElementsByClassName("listDiv");
			for(let i=0;i<commentList.length;i++){
				var div=document.createElement("div");
				for(let j=0;j<commentList[i].star;j++){
					div.innerHTML+='<i class="fa-solid fa-star reviewStar"></i>';
				}
				div.innerHTML+='<br><h3 class="commentFont">'+commentList[i].comment+'</h3><br><p class="commentTime"><b>'+commentList[i].givenBy+'</b>  '+commentList[i].date+'</p>';
				listDiv[id-1].after(div);
				div.classList.add("commentStyle");
				document.getElementsByClassName("comment")[id-1].innerHTML="Hide Comments";
				document.getElementsByClassName("comment")[id-1].setAttribute("onclick","hideComments(this.dataset.id)")
			}
		}
	}
}
function hideComments(id){
	var listDiv=document.getElementsByClassName("commentStyle");
	var a=listDiv.length;
	for(let i=0;i<a;i++){
		listDiv[0].remove();
	}
	document.getElementsByClassName("comment")[id-1].innerHTML="Show Comments";
	document.getElementsByClassName("comment")[id-1].setAttribute("onclick","viewComments(this.dataset.id)");
}


function menubarChange(){
	
}
function editMe(){
	document.getElementById("details").style.height="650px";
	document.getElementById("details").innerHTML='<div class="innerDiv"><h2 style="font-size: 3rem">Your Details</h2><input type="text" id="name" placeholder="Name" class="addDaycare"><br><input type="number" id="number" placeholder="Number" class="addDaycare"><br><select name="regions" id="region" class="addDaycare"><option value="Tenkasi">Tenkasi</option><option value="Kadayam">Kadayam</option><option value="Sankarankoil">Sankarankoil</option><option value="Shencottai">Shencottai</option><option value="Alangulam">Alangulam</option></select></div>';
	document.getElementById("details").innerHTML+='<div class="innerDiv"><h2 style="font-size: 3rem">Child Details</h2><input type="text" id="childName" placeholder="Child Name" class="addDaycare"><br><input type="date" id="childAge" placeholder="Child Name" class="addDaycare"><br><input type="number" id="childClass" placeholder="Child Class" class="addDaycare"><br><input type="text" id="childSchool" placeholder="Child School" class="addDaycare"><button id="addButton" onclick="updateEdit()">Update</button></div>';
	document.getElementsByClassName("innerDiv")[0].style.height="94%";
	document.getElementsByClassName("innerDiv")[1].style.height="94%";
	document.getElementById("name").value=details[0].name;
	document.getElementById("region").value=details[0].region;
	document.getElementById("number").value=details[0].number;
	document.getElementById("childName").value=details[1].childName;
	document.getElementById("childAge").value=details[1].dob;
	document.getElementById("childClass").value=details[1].childClass;
	document.getElementById("childSchool").value=details[1].childSchool;
}
function updateEdit(){
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./user/UpdateMyDetails");
	xhr.setRequestHeader("Content-Type","application/json");
	var update={};
	update.Name=document.getElementById("name").value;
	update.Number=document.getElementById("number").value;
	update.Region=document.getElementById("region").value;
//	update.Fee=document.getElementById("fee").value;
	update.ChildName=document.getElementById("childName").value;
	update.ChildClass=document.getElementById("childClass").value;
	update.ChildAge=document.getElementById("childAge").value;
	update.ChildSchool=document.getElementById("childSchool").value;
	xhr.send(JSON.stringify(update));
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			if(this.responseText=="updated successfully"){
				document.getElementById("details").style.display="none";
				detail();
			}
		}
	}
}

function history(){
	bgChange();
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./user/History");
	xhr.send();
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			var obj=JSON.parse(this.responseText);
			document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="back()"></i><div id="listView"></div>';
			for(let i=0;i<obj.length;i++){
				document.getElementById("listView").innerHTML+='<div class="listDiv"><img class="daycareImage" src="img.png"><div><h2>Name:'+obj[i].name+'</h2><h2>Number:'+obj[i].number+'</h2><h2>Fees:'+obj[i].fee+'</h2></div><div><h2>Enrolled on:'+obj[i].enrolledTime+'</h2></div></div>'
			}
		}
	}
}





//Admin flow
function addDaycare(){
	bgChange();
	document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="adminback2()"></i><div id="addDiv"><div class="innerDiv"><h2 style="font-size: 3rem">Daycare Details</h2><input type="text" id="name" placeholder="Name" class="addDaycare"><br><input type="number" id="number" placeholder="Number" class="addDaycare"><br><select name="regions" id="region" class="addDaycare"><option value="Tenkasi">Tenkasi</option><option value="Kadayam">Kadayam</option><option value="Sankarankovil">Sankarankovil</option><option value="Shencottai">Shencottai</option><option value="Alangulam">Alangulam</option></select><br><input type="number" id="fee" placeholder="Fees" class="addDaycare"></div></div>';
	document.getElementById("addDiv").innerHTML+='<div class="innerDiv"><h2 style="font-size: 3rem">Child Details</h2><input type="text" id="childName" placeholder="Child Name" class="addDaycare"><br><input type="date" id="childAge" placeholder="Child Name" class="addDaycare"><br><input type="text" id="childClass" placeholder="Child Class" class="addDaycare"><br><input type="text" id="childSchool" placeholder="Child School" class="addDaycare"><br><button id="addButton" onclick="addDaycareToDB()">Add</button></div>';
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
function addDaycareToDB(){
	var daycareDetails={};
	daycareDetails.Name=document.getElementById("name").value;
	daycareDetails.Number=document.getElementById("number").value;
	daycareDetails.Region=document.getElementById("region").value;
	daycareDetails.Fee=document.getElementById("fee").value;
	daycareDetails.ChildName=document.getElementById("childName").value;
	daycareDetails.ChildClass=document.getElementById("childClass").value;
	daycareDetails.ChildAge=document.getElementById("childAge").value;
	daycareDetails.ChildSchool=document.getElementById("childSchool").value;
	console.log(daycareDetails)
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./admin/AddDaycare");
	xhr.setRequestHeader("Content-Type","application/json");
	xhr.send(JSON.stringify(daycareDetails));
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			if(this.responseText=="successfully added"){
				document.getElementById("addDiv").innerHTML="<h1>Daycare added successfully</h1>";
			}
		}
	}

}
function viewMothersView(){
		bgChange();
//		document.getElementById("ham").style.display="none";
//		document.getElementById("xmark").style.display="none";
		document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="adminback()"></i><select name="regions" id="regions" class="input" onchange="viewMothers(this.value)"><option value="Tenkasi">Tenkasi</option><option value="Kadayam">Kadayam</option><option value="Sankarankovil">Sankarankovil</option><option value="Shengottai">Shengottai</option><option value="Alangulam">Alangulam</option></select><br><div id="listView"></div>';
		viewMothers("Tenkasi");
}
function viewMothers(val){
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./admin/ViewMothers");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("region="+val);
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			mothersList=JSON.parse(this.responseText);
			document.getElementById("listView").innerHTML="";
			if(mothersList.length==0){
				document.getElementById("listView").innerHTML="<h1>No mothers in this region</h1>"
			}
			for(let i=0;i<mothersList.length;i++){
				document.getElementById("listView").innerHTML+='<div class="listDiv"><img class="daycareImage" src="img.png"><div><h2>Name:'+mothersList[i].name+'</h2><h2>Region:'+mothersList[i].region+'</h2><h2>Number:'+mothersList[i].number+'</h2><h2>Register Number:'+mothersList[i].registerNumber+'</h2><h2>Daycare Id:'+(mothersList[i].daycare==null?'  -':mothersList[i].daycare)+'</h2></div></div>'
				console.log(mothersList[i].childList.length);
				for(let j=0;j<mothersList[i].childList.length;j++){
					document.getElementsByClassName("listDiv")[i].innerHTML+='<div><h2>Child details</h2><h3>Name:'+(mothersList[i].childList[j]['childName'+j])+'</h3><h3>Age:'+(mothersList[i].childList[j]['childAge'+j])+'</h3><h3>Class:'+(mothersList[i].childList[j]['childClass'+j])+'</h3><h3>School:'+(mothersList[i].childList[j]['childSchool'+j])+'</h3></div>'
				}
			}
		}
	}
}

function adminback(){
	backFirst();
	document.getElementById("icon1").remove();
	document.getElementById("listView").remove();
	document.getElementById("regions").remove();
//	document.getElementsByClassName("confirmPop")[0].remove();
}
function adminback2(){
	backFirst();
	document.getElementById("icon1").remove();
	document.getElementById("addDiv").remove();
}
function backFirst(){
	pointing(-1);
//	document.getElementById("ham").style.display="block";
//	document.getElementById("xmark").style.display="none";
	document.getElementById("body").style.background='url("daycare.jpg")';
	document.getElementById("body").style.backgroundRepeat="no-repeat";
	document.getElementById("body").style.backgroundSize="100%";
//	document.getElementById("welcome").style.display="block";
	document.getElementById("subDiv").innerHTML="";
}
function adminViewDaycaresPage(){
	bgChange();
//	document.getElementById("ham").style.display="none";
//	document.getElementById("xmark").style.display="none";
	document.getElementById("subDiv").innerHTML='<i class="fa-solid fa-arrow-left" id="icon1" onclick="adminback()"></i><select name="regions" id="regions" class="input" onchange="adminViewDaycares(this.value)"><option value="Kadayam">Kadayam</option><option value="Tenkasi">Tenkasi</option><option value="Sankarankovil">Sankarankovil</option><option value="Shengottai">Shengottai</option><option value="Alangulam">Alangulam</option></select><br><div id="listView"></div>';
	adminViewDaycares("Kadayam");
}
function adminViewDaycares(region){
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./admin/ViewDaycares");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("region="+region);
	xhr.onreadystatechange=function(){
		if(this.readyState==4 && this.status==200){
			daycareList=JSON.parse(this.responseText);
			if(daycareList.length==0){
				document.getElementById("listView").innerHTML="<h1>No daycares in this region</h1>"
			}
			document.getElementById("listView").innerHTML="";
			for(let i=0;i<daycareList.length;i++){
				document.getElementById("listView").innerHTML+='<div class="listDiv"></i><img class="daycareImage" src="img.png"><div><h2>Name:'+daycareList[i].name+'</h2><h2>Region:'+daycareList[i].region+'</h2><h2>Number:'+daycareList[i].number+'</h2><h2>Fees:'+daycareList[i].fee+'</h2><h2>Status:'+daycareList[i].status.toUpperCase()+'</h2></div><div><h2>Child details</h2><h3>Name:'+daycareList[i].childName+'</h3><h3>Age:'+daycareList[i].childAge+'</h3><h3>Class:'+daycareList[i].childClass+'</h3><h3>School:'+daycareList[i].childSchool+'</h3></div><div data-id='+i+' class="comment" onclick="adminViewComments(this.dataset.id)">Show Comments</div><i class="fa-solid fa-pen-to-square edit" onclick="editMe()"></div>'
			}
		}
	}
}
function adminViewComments(id){
	var comments=document.getElementsByClassName("commentStyle");
	var a=comments.length;
	for(let i=0;i<a;i++){
		comments[0].remove();
	}
	var commentsButton=document.getElementsByClassName("comment");
	for(let i=0;i<commentsButton.length;i++){
		commentsButton[i].innerHTML="Show Comments";
		commentsButton[i].setAttribute("onclick","adminViewComments(this.dataset.id)");
	}
	var xhr=new XMLHttpRequest();
	xhr.open("POST","./admin/ViewComments");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("daycareId="+daycareList[id].registerNo);
	xhr.onreadystatechange=function (){
		if(this.readyState==4 && this.status==200){
			console.log(this.responseText)
			var commentList=JSON.parse(this.responseText);
			var listDiv=document.getElementsByClassName("listDiv");
			for(let i=0;i<commentList.length;i++){
				var div=document.createElement("div");
				for(let j=0;j<commentList[i].star;j++){
					div.innerHTML+='<i class="fa-solid fa-star reviewStar"></i>';
				}
				div.innerHTML+='<br><h3 class="commentFont">'+commentList[i].comment+'</h3><br><p class="commentTime"><b>'+commentList[i].givenBy+'</b>  '+commentList[i].date+'</p>';
				console.log(div)
				listDiv[id].after(div);
				div.classList.add("commentStyle");
				document.getElementsByClassName("comment")[id].innerHTML="Hide Comments";
				document.getElementsByClassName("comment")[id].setAttribute("onclick","adminHideComments(this.dataset.id)")
			}
		}
	}
}
function adminHideComments(id){
	var listDiv=document.getElementsByClassName("commentStyle");
	var a=listDiv.length;
	for(let i=0;i<a;i++){
		listDiv[0].remove();
	}
	document.getElementsByClassName("comment")[id].innerHTML="Show Comments";
	document.getElementsByClassName("comment")[id].setAttribute("onclick","adminViewComments(this.dataset.id)");
}




