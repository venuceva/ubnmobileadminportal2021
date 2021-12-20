function pwdonly(netid,pwd)
{
	if (pwd.length<8)
	{
		return("Password Length Should Not Be Less Than 8");
	}
	else if (pwd==netid)
	{
		return("Net-ID/User-ID And Password Should Not Be Same");
	}
	else 
		return("");
}

function trim(Str) {
 var objRegExp = /^(\s*)$/;

    //check for all spaces
    if(objRegExp.test(Str)) {
       Str = Str.replace(objRegExp, '');
       if( Str.length == 0)
          return Str;
    }

   //check for leading & trailing spaces
   objRegExp = /^(\s*)([\W\w]*)(\b\s*$)/;
   if(objRegExp.test(Str)) {
       //remove leading and trailing whitespace characters
       Str = Str.replace(objRegExp, '$2');
    }
  return Str;
}



function logFormNames(netid,pinno)
{

  var incr=0;
  var str="";
  var myarray=new Array();
  if(trim(netid).length==0)
  {
		

             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].NET_ID.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:12px;font-family:arial;'>\n At User ID  \n</font>\n</td>\n<td style='font-size:12px;color:black;font-family:arial;'>:"+ requireValue(netid) +"\n</td>\n</tr>";
             incr++;
  }
  else if(alphaval(trim(netid))!="")
  {
			

             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].NET_ID.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:12px;font-family:arial;'>\n At User ID  \n</font>\n</td>\n<td style='font-size:12px;font-family:arial;color:black;'>:"+ numbonly(netid) +"\n</td>\n</tr>";
             incr++;
  }
  else if(trim(netid).length<4)
  {
	

             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].NET_ID.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-family:arial;font-size:12px'>\n AT User ID  \n</font>\n</td>\n<td style='font-size:12px;font-family:arial;color:black;'>: User ID length should be minimum of 4 \n</td>\n</tr>";
             incr++;
  }
  if(trim(pinno).length==0)
  {
	

             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].PIN_NO.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-family:arial;font-size:12px'>\n At Password \n</font>\n</td>\n<td style='font-size:12px;font-family:arial;color:black;'>:"+ requireValue(pinno) +"\n</td>\n</tr>";
             incr++;
  }
  else if(pwdonly(netid,pinno)!="")
  {
	

           myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].PIN_NO.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-family:arial;font-size:12px'>\n At Password \n</font>\n</td>\n<td style='font-size:12px;font-family:arial;color:black;'>:"+pwdonly(netid,pinno)+"\n</td>\n</tr>";
	     incr++;	
  }
  if (myarray.length!=0)
  {
    	str="<table align=center cellpadding=0 cellspacing=0 class='errorarea' style='font-size:13px;'>\n";
	    for(var i=0;i<myarray.length;i++)
	    {
		  str+=myarray[i];   
	    }
    	str+="\n</table>";
  }
  else
  {
       str="";
  }

   return(str);
}



function pwdlogFormNames1(netid,pinno,newpin,cfrpin)
{
	return pwdlogFormNames(netid,pinno,newpin,cfrpin)

}

function pwdlogFormNames(netid,pinno,newpin,cfrpin)
{
  var incr=0;
  var str="";
  var myarray=new Array();
  if(trim(netid).length==0)
  {
             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].USER_ID.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At User ID  \n</font>\n</td>\n<td>:"+ requireValue(netid) +"\n</td>\n</tr>";
             incr++;
  }
  else if(alphaval(trim(netid),6,100)!="")
  {
             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].USER_ID.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At User ID  \n</font>\n</td>\n<td>:"+ alpahval(netid) +"\n</td>\n</tr>";
             incr++;
  }
  else if(trim(netid).length<6)
  {
             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].USER_ID.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At User ID  \n</font>\n</td>\n<td>: The Data Should Be Of Proper Length\n</td>\n</tr>";
             incr++;
  }
  if(trim(pinno).length==0)
  {
             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].OLD_PSW.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At Old Password \n</font>\n</td>\n<td>:"+ requireValue(pinno) +"\n</td>\n</tr>";
             incr++;
  }
  else if(pwdonly(netid,pinno)!="")
  {
           myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].OLD_PSW.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At Old Password \n</font>\n</td>\n<td>:"+pwdonly(netid,pinno)+"\n</td>\n</tr>";
	     incr++;	
  }
  if(trim(newpin).length==0)
  {
             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].NEW_PSW.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At New Password \n</font>\n</td>\n<td>:"+ requireValue(newpin) +"\n</td>\n</tr>";
             incr++;
  }
  else if(pwdonly(netid,newpin)!="")
  {
           myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].NEW_PSW.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At New Password \n</font>\n</td>\n<td>:"+pwdonly(netid,newpin)+"\n</td>\n</tr>";
	     incr++;	
  }
  if(trim(cfrpin).length==0)
  {
             myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].CFRM_PSW.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At Confirm Password \n</font>\n</td>\n<td>:"+ requireValue(cfrpin) +"\n</td>\n</tr>";
             incr++;
  }
  else if(pwdonly(netid,cfrpin)!="")
  {
           myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].CFRM_PSW.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At Confirm Password \n</font>\n</td>\n<td>:"+pwdonly(netid,cfrpin)+"\n</td>\n</tr>";
	     incr++;	
  }
  if(newpin!=cfrpin)
  {
            myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].NEW_PSW.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At Confirm Password \n</font>\n</td>\n<td>: New And Confirm PIN Mismatch \n</td>\n</tr>";
            incr++;
  }
  if(pinno==newpin)
  {
            myarray[incr]="\n<tr onclick='window.parent.frames[\"mainframe\"].document.forms[0].NEW_PSW.select()' style='cursor:hand'>\n<td >\n<font style='color:black;font-weight:bold;font-size:13px'>\n At Old Password \n</font>\n</td>\n<td>: Old And New PIN Numbers Should Not Match \n</td>\n</tr>";
            incr++;
  }
  if (myarray.length!=0)
  {
    	str="<table align=center cellpadding=0 cellspacing=0 class='errorarea' style='font-size:13px;'>\n";
	    for(var i=0;i<myarray.length;i++)
	    {
		  str+=myarray[i];   
	    }
    	str+="\n</table>";
  }
  else
  {
       str="";
  }
   return(str);
}

function requireValue(fld)
{
	if(fld.length==0)
	{
		return ("The Field Cannot Be Left Blank");
	}
	else
	{
		return ("");
	}
}


function alphaval(str,minlen,maxlen)
{
	if (str.length<minlen)
	{
		return("The Data Should Be Of Minimum "+minlen+" Length");
	}
	else if (str.length>maxlen)
	{
		return("The Data Should Be Of Maximum "+maxlen+" Length");
	}
	else
	{
      return("");
	}
}

	
function numbval(str,minlen,maxlen)
{
	var count=0;
	while(count<str.length)
	{
		var charat=str.charAt(count);
		if(charat=="-")
		{
			return ("Only Numberic Data Is Allowed");
		}
		count++;
	}
	if (str.length<minlen)
	{
		return("The Data Should Be Of Minimum "+minlen+" Length");
	}
	else if (str.length>maxlen)
	{
		return("The Data Should Be Of Maximum "+maxlen+" Length");
	}
	else 
	{
        var errstr;
		errstr=numbonly(str);
            return(errstr);
	}
}
