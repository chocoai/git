var LPFlowKit = {
		save : function(url,param,callBack){//流程保存
			if(!param)
				param = {};

			param.g_iscreateform = window.g_iscreateform;
			param.g_formdataversion = window.g_formdataversion;
			param.g_flowid = window.g_flowid;
			param.g_flowversionid = window.g_flowversionid;
			param.g_flownodeid = window.g_flownodeid;
			param.g_flownodename = window.g_flownodename;
			param.g_flownodetype = window.g_flownodetype;
			param.g_flownodeembranch = window.g_flownodeembranch;
			param.g_flowinstanceid = window.g_flowinstanceid;
			param.g_flownodeinstanceid = window.g_flownodeinstanceid;

			getJson(url,param,true,function(re){
				if(re.isOk){
					if(window.g_iscreateform == "true"){
						LPFlowKit.refreshWaitDo();
					}
					
					window.g_iscreateform = "false";
					window.g_formdataversion = re.g_formdataversion;
				}
				
				if(callBack){
					callBack(re);
				}
				else{
					if(re.isOk){
						MsgBox.showSuccessTips("保存成功！");
					}
					else{
						MsgBox.showErrTips("保存失败！");
					}
				}
			});
		},
		submit : function(callBack){//流程提交
			var param = {};
			param.g_iscreateform = window.g_iscreateform;
			param.g_formdataversion = window.g_formdataversion;
			param.g_flowid = window.g_flowid;
			param.g_flowversionid = window.g_flowversionid;
			param.g_flownodeid = window.g_flownodeid;
			param.g_flownodename = window.g_flownodename;
			param.g_flownodetype = window.g_flownodetype;
			param.g_flownodeembranch = window.g_flownodeembranch;
			param.g_flowinstanceid = window.g_flowinstanceid;
			param.g_flownodeinstanceid = window.g_flownodeinstanceid;
			
			var url = window.g_path + "/sys/flowctrl/submit";		
			getJson(url,param,true,function(re){
				if(re.isOk){
					if(re.resultCode == 1){
						goResultDeal(true,false);
					}
					if(re.resultCode == 0){
						var url = window.g_path + "/sys/flowctrl/selectsubmitnode";
						var title = "选择提交节点";
						MsgBox.openWin(url, title, "550px", "80%", function(win){
							win.setData(re.submitNode,window.g_flownodeembranch);
						}, function(action,win){
							if(action == "ok"){
								var data = win.getData();
								var submitData = mini.clone(data);
								param.submitdata = mini.encode(submitData);
								var url = window.g_path + "/sys/flowctrl/submit";
								getJson(url,param,true,function(re){
									goResultDeal(re.isOk,false);
								});
							}
							else{
								goResultDeal(false,true);
							}
						});
					}
				}
				else{
					MsgBox.showErrTips("系统异常，提交失败！");
				}
			});
			
			function goResultDeal(isOk,isCancel){
				if(isOk)
					LPFlowKit.refreshWaitDo();
					
				if(callBack){
					callBack(re.isOk,isCancel);
				}
				else{
					if(isOk){
						MsgBox.showSuccessTips("提交成功！");
						LPFlowKit.closePage();
					}
				}
			}
		},
		untread : function(callBack){//流程回退
			var param = {};
			param.g_iscreateform = window.g_iscreateform;
			param.g_formdataversion = window.g_formdataversion;
			param.g_flowid = window.g_flowid;
			param.g_flowversionid = window.g_flowversionid;
			param.g_flownodeid = window.g_flownodeid;
			param.g_flownodename = window.g_flownodename;
			param.g_flownodetype = window.g_flownodetype;
			param.g_flownodeembranch = window.g_flownodeembranch;
			param.g_flowinstanceid = window.g_flowinstanceid;
			param.g_flownodeinstanceid = window.g_flownodeinstanceid;
			
			var url = window.g_path + "/sys/flowctrl/untreadnote";
			var title = "填写回退意见";
			MsgBox.openWin(url, title, "550px", "300px", null,function(action,win){
				if(action != "ok"){
					goResultDeal(false,true);
					return;
				}
				
				param.note = win.getData();
				var url = window.g_path + "/sys/flowctrl/untread";
				getJson(url,param,true,function(re){
					if(re.isOk){
						if(re.resultCode == 1){
							goResultDeal(true,false);
						}
						if(re.resultCode == 0){
							var url = window.g_path + "/sys/flowctrl/selectuntreadnode";
							var title = "选择回退节点";
							MsgBox.openWin(url, title, "550px", "80%", function(win){
								win.setData(re.untreadNode);
							}, function(action,win){
								if(action == "ok"){
									var data = win.getData();
									var untreadData = mini.clone(data);
									param.untreaddata = mini.encode(untreadData);
									var url = window.g_path + "/sys/flowctrl/untread";
									getJson(url,param,true,function(re){
										goResultDeal(re.isOk,false);
									});
								}
								else{
									goResultDeal(false,true);
								}
							});
						}
					}
					else{
						MsgBox.showErrTips("系统异常，回退失败！");
					}
				});
			});
			
			function goResultDeal(isOk,isCancel){
				if(isOk)
					LPFlowKit.refreshWaitDo();
				
				if(callBack){
					callBack(re.isOk,isCancel);
				}
				else{
					if(isOk){
						MsgBox.showSuccessTips("回退成功！");
						LPFlowKit.closePage();
					}
				}
			}
		},
		viewLog : function(){//查看流程日志
			var url = window.g_path + "/sys/flowctrl/showlog?i=" + window.g_flowinstanceid+"&flowversionid=" + window.g_flowversionid;
			var title = "查看流程日志";
			MsgBox.openWin(url, title, "100%", "100%", function(win){
			}, function(action,win){
			});
		},
		refreshWaitDo : function(){
			if(window.top.LPMainPage){
				window.top.LPMainPage.invokeEvent("waitdo");
			}
		},
		closePage : function(){
			if(window.top.LPMainPage){
				window.setTimeout(function(){
					window.top.LPMainPage.closePage(window);
				}, 0);
			}
			else{
				window.close();
			}			
		}
}