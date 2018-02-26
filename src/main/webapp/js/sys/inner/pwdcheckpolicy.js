
//密码加密
function lpPwdMd5(pwd){
	return CryptoJS.MD5("blit_lp_" + pwd) + "";
}

function lpCheckPwd(pwd)
{
	if(!window.LpPwdPolicy_init){
		window.LpPwdPolicy = {};
		var param = {
	        type: "POST",
	        url: window.g_path + "/sys/config/getPwdPolicy",
	        async: false,
	        data: null,
	        dataType: "json",
	        success: function (data, textStatus, jqXHR) {
	        	window.LpPwdPolicy_init = true;
	        	window.LpPwdPolicy = data;
	        }
	    };

	    $.ajax(param);
	}

	if(window.LpPwdPolicy.minlength && 
			window.LpPwdPolicy.minlength > 0){
		if(pwd.length < window.LpPwdPolicy.minlength){
			return "长度至少" +window.LpPwdPolicy.minlength+ "位";
		}
	}
	
	if(window.LpPwdPolicy.minspecialcharnum && 
			window.LpPwdPolicy.minspecialcharnum > 0){
		var patten = "";
		for(var i=0; i < window.LpPwdPolicy.minspecialcharnum; i++){
			patten += "[^a-zA-Z0-9][a-zA-Z0-9]*";
		}
		var reg = new RegExp(patten);
		var bl = reg.test(pwd);
		if(!bl){
			return "特殊字符(非数字、字母)至少" +window.LpPwdPolicy.minspecialcharnum+ "位"
		}
		
		var reg = new RegExp(patten);
	}
	
	return "";
}

