(function ($) {
	$.LPFlowChar = function(containerJq, _opt){
		var options = $.extend($.LPFlowChar.defaults,_opt),
        docOffset,
        _ua = navigator.userAgent.toLowerCase(),
        is_msie = /msie/.test(_ua),
        ie8mode = /msie [1-8]\./.test(_ua);

		function changeState(state,type){
			clearState(state);
        	$state = state;
        	$type = type;
        	$workArea.removeClass("sel line node").addClass($state);
        	
        	if(state == "sel"){
	        	$(".btnsel").removeClass("active");
	        	$("#btnSel").addClass("active");
        	}
        }
        
        function clearState(state){
        	if($state == "sel" && state != "sel"){
        		$(".line_selected").removeClass("line_selected");
        		$(".nodeitem").removeClass("selected1 selected2");
        		unselectedLine();
        		if($selObjType != "flow"){
        			changeSelectedFlow();
        		}
			}
        	else if($state == "line" && $joinObj){
    			$(".nodeitem-joinpoint").removeClass("selected");
    			$draw.removeChild($joinObj.line);
    			$joinObj=null;
			}
			else if($state == "node"){
			}
        }
        
        function reloadProGrid(editConfig,jsonData){
        	var tableJq = $(".progrid");
        	tableJq.find(".progrid-item").remove();
        	for(var i=0; i < editConfig.length; i++){
        		var item = editConfig[i];
        		
        		if(!checkIsNeedPro(jsonData,item))
        			continue;
        			
        		var html = "";
        		var htmlJq = null;
        		if(item.type == "label"){
        			var txt = "";
        			if(item.showText){
        				txt = item.showText(jsonData);
        			}
        			else{
        				txt = jsonData[item.field] ? jsonData[item.field] : "";
        			}
        			
        			html =
        				 '<tr class="progrid-item">'
			        	+'	<td class="progrid-label"><label>' +item.name+ '</label></td>'
			        	+'	<td class="progrid-edit">'
			        	+'		<span class="txt">' +txt+ '</span>'
			        	+'	</td>'
			        	+'</tr>';
			        htmlJq = $(html);
        		}
        		else if(item.type == "textbox"){
        			var txt = jsonData[item.field] ? jsonData[item.field] : "";
        			html =
        				 '<tr class="progrid-item">'
			        	+'	<td class="progrid-label"><label>' +item.name+ '</label></td>'
			        	+'	<td class="progrid-edit">'
			        	+'		<input type="text" value="' +txt+ '" />'
			        	+'	</td>'
			        	data
			        	+'</tr>';
			        htmlJq = $(html);
			        htmlJq.find("input").blur({jsonData:jsonData,itemConfig:item},function(e){
			        	var jsonData = e.data.jsonData;
			        	var item = e.data.itemConfig;
			        	jsonData[item.field] = $(this).val();
			        	if($selObjType == "node" && item.field=="flownodename"){
			        		adjustFlowNodeDom(jsonData);
			        	}
			        });
        		}
        		else if(item.type == "combobox"){
        			var txt = jsonData[item.field] == null ? "" : jsonData[item.field];
        			var data = item.data;
        			if(jQuery.isFunction(data)){
        				data = item.data(jsonData);
        			}
        			var selectHtml = '<select>';
        			for(var j=0; j < data.length; j++){
        				if(data[j].id == txt)
        					selectHtml += '<option value="' +data[j].id+ '" selected="selected">' +data[j].text+ '</option>'
        				else
        					selectHtml += '<option value="' +data[j].id+ '">' +data[j].text+ '</option>'
        			}
        			selectHtml +='</select>';
        			
        			html =
        				 '<tr class="progrid-item">'
			        	+'	<td class="progrid-label"><label>' +item.name+ '</label></td>'
			        	+'	<td class="progrid-edit">'
			        	+ selectHtml
			        	+'	</td>'
			        	+'</tr>';
			        htmlJq = $(html);
			        
			        htmlJq.find("select").change({jsonData:jsonData,itemConfig:item},function(e){
			        	var jsonData = e.data.jsonData;
			        	var item = e.data.itemConfig;
			        	jsonData[item.field] = $(this).val();
			        	
			        	if($selObjType == "line" && item.field=="conditionid"){
			        		adjustFlowTranCondition(jsonData);
			        	}
			        });
        		}
        		else if(item.type == "buttonedit"){
        			var txt = jsonData[item.field] ? jsonData[item.field] : "";
        			html =
        				 '<tr class="progrid-item">'
			        	+'	<td class="progrid-label"><label>' +item.name+ '</label></td>'
			        	+'	<td class="progrid-edit">'
			        	+'		<div class="button">...</div>'
			        	+'	</td>'
			        	+'</tr>';
			        htmlJq = $(html);
			        htmlJq.find(".button").click({jsonData:jsonData,itemConfig:item},function(e){
			        	var jsonData = e.data.jsonData;
			        	var item = e.data.itemConfig;
			        	if(item.click)
			        		item.click(jsonData);
			        });
        		}
        		
        		tableJq.append(htmlJq);
        	}
        }
        
        function checkIsNeedPro(jsonData,item){
        	if($selObjType == "node"){
        		if(jsonData.nodetype == "0"){
        			if(item.field == "commonjson")
        				return false;
        		}
        		else if(jsonData.nodetype == "9"){
        			if(item.field == "flownodename" 
        			|| item.field == "embranch" 
        			|| item.field == "actorjson"
        			|| item.field == "commonjson")
        				return false;
        		}
        	}
        	else if($selObjType == "line"){
        		if(jsonData.trantype == "1"){
        			if(item.field == "allowuntread" 
        			|| item.field == "conditionid")
        				return false;
        		}
        	}
        	return true;
        }
        
        function endProGridEdit(){
        	$(".progrid-item input:focus").blur();
        }
        
        function changeSelectedFlow(){
        	$selObjType = "flow";
        	$selDomJq = $workArea;
        	
        	$("#lbSelObjType").text("流程");
        	reloadProGrid($.LPFlowChar.flowEditConfig,$flowDataObj);
        }
        
        function changeSelectedLine(dom){
        	$(".nodeitem").removeClass("selected1 selected2");
        	unselectedLine();
        	
        	$selObjType = "line";
        	$selDomJq = $(dom);
        	
        	var lineObj = getFlowTranData(dom.id);
        	if(ie8mode){
        		dom.strokeColor=options.selected_line_color;
        	}
        	else{
				dom.childNodes[1].setAttribute("stroke",options.selected_line_color);
				dom.childNodes[1].setAttribute("marker-end","url(#arrow3)");
			} 

			sNodeData = getFlowNodeData(lineObj.fromnodeid);
			$("#" + sNodeData.id).addClass("line_selected");
			$("#" + sNodeData.id).find(".nodeitem-joinpoint.ord-" + lineObj.drawjson.sOrd).addClass("line_selected");
			eNodeData = getFlowNodeData(lineObj.tonodeid);
			$("#" + eNodeData.id).addClass("line_selected");
			$("#" + eNodeData.id).find(".nodeitem-joinpoint.ord-" + lineObj.drawjson.eOrd).addClass("line_selected");
			
			$("#lbSelObjType").text("连线");
        	reloadProGrid($.LPFlowChar.lineEditConfig,lineObj);
        }
        
        function unselectedLine(){
        	if($selObjType == "line"){
        		$(".line_selected").removeClass("line_selected");
        		
	        	var lineObj = getFlowTranData($selDomJq.attr("id"));
	        	if(ie8mode){
	        		$selDomJq.get(0).strokeColor= lineObj.trantype=="0" ? options.submit_line_color : options.untread_line_color;
	        	}
	        	else{
					$selDomJq.get(0).childNodes[1].setAttribute("stroke",lineObj.trantype=="0" ? options.submit_line_color : options.untread_line_color);
					$selDomJq.get(0).childNodes[1].setAttribute("marker-end",lineObj.trantype=="0" ? "url(#arrow1)" : "url(#arrow2)" );
				} 
        	}
        }
        
        function changeSelectedNode(domJq,shiftKey){
        	unselectedLine();
        	
        	$selObjType = "node";
        	if(!jQuery.isArray($selDomJq) || !shiftKey){
        		$selDomJq = [];
        		$(".nodeitem").removeClass("selected1 selected2");
        	}
        	
        	for(var i=0; i < $selDomJq.length; i++){
        		if($selDomJq[i].get(0) === domJq.get(0))
        			return;
        	}
        	
        	if($selDomJq.length > 0 && shiftKey){
        		domJq.addClass("selected2");
        	}
        	else{
        		domJq.addClass("selected1");
        		
        		$("#lbSelObjType").text("流程节点");
        		reloadProGrid($.LPFlowChar.nodeEditConfig,domJq.data("myData"));
        	}
        	
        	$selDomJq.push(domJq);
        }
        
        function initDraw(){
        	var dom = null;
	    	if(ie8mode){
	    		document.namespaces.add("v", "urn:schemas-microsoft-com:vml");
	    		var vmlJq = $("<div class='work_vml'></div>");
	    		dom = document.createElement("v:group");
				vmlJq.append(dom);
				$workArea.append(vmlJq);
			}
			else{
				var svgJq = $("<div class='work_svg'></div>");
				dom=document.createElementNS("http://www.w3.org/2000/svg","svg");
				var defs=document.createElementNS("http://www.w3.org/2000/svg","defs");
				dom.appendChild(defs);
				defs.appendChild(createSvgMarker("arrow1",options.submit_line_color));
				defs.appendChild(createSvgMarker("arrow2",options.untread_line_color));
				defs.appendChild(createSvgMarker("arrow3",options.selected_line_color));
				defs.appendChild(createSvgMarker("arrow4",options.hassubmit_line_color));
				
				svgJq.append(dom);
				$workArea.append(svgJq);
			}
			
			dom.style.width = "99%";
			dom.style.height = "99%";
			
			return dom;
	    }
	    
	    function createSvgMarker(id,color){
			var m=document.createElementNS("http://www.w3.org/2000/svg","marker");
			m.setAttribute("id",id);
			m.setAttribute("viewBox","0 0 6 6");
			m.setAttribute("refX",5);
			m.setAttribute("refY",3);
			m.setAttribute("markerUnits","strokeWidth");
			m.setAttribute("markerWidth",6);
			m.setAttribute("markerHeight",6);
			m.setAttribute("orient","auto");
			var path=document.createElementNS("http://www.w3.org/2000/svg","path");
			path.setAttribute("d","M 0 0 L 6 3 L 0 6 z");
			path.setAttribute("fill",color);
			path.setAttribute("stroke-width",0);
			m.appendChild(path);
			return m;
		}
		
	    function drawLine(id,sp,ep,type,dash){
	    	if(!type) 
				type="0";
			var line;
			if(ie8mode){
				line=document.createElement("v:polyline");
				if(id!="")	line.id=id;
				line.points.value=sp[0]+","+sp[1]+" "+ep[0]+","+ep[1];
				line.setAttribute("fromTo",sp[0]+","+sp[1]+","+ep[0]+","+ep[1]);
				line.strokeWeight="1.2";
				line.stroke.EndArrow="Block";
				line.style.cursor="crosshair";
				if(dash)	line.stroke.dashstyle="Dash";
				if(type=="0"){
					
					line.strokeColor=options.submit_line_color;
					line.fillColor=options.submit_line_color;
				}
				else if(type=="1"){
					line.strokeColor=options.untread_line_color;
					line.fillColor=options.untread_line_color;
				}
			}
			else{
				line=document.createElementNS("http://www.w3.org/2000/svg","g");
				var hi=document.createElementNS("http://www.w3.org/2000/svg","path");
				var path=document.createElementNS("http://www.w3.org/2000/svg","path");
	
				if(id!="")	line.setAttribute("id",id);
				line.setAttribute("from",sp[0]+","+sp[1]);
				line.setAttribute("to",ep[0]+","+ep[1]);
				hi.setAttribute("visibility","hidden");
				hi.setAttribute("stroke-width",9);
				hi.setAttribute("fill","none");
				hi.setAttribute("stroke","white");
				hi.setAttribute("d","M "+sp[0]+" "+sp[1]+" L "+ep[0]+" "+ep[1]);
				hi.setAttribute("pointer-events","stroke");
				path.setAttribute("d","M "+sp[0]+" "+sp[1]+" L "+ep[0]+" "+ep[1]);
				path.setAttribute("stroke-width",1.4);
				path.setAttribute("stroke-linecap","round");
				path.setAttribute("fill","none");
				if(dash)	path.setAttribute("style", "stroke-dasharray:6,5");
				if(type=="0"){
					path.setAttribute("stroke",options.submit_line_color);
					path.setAttribute("marker-end","url(#arrow1)");
				}
				else if(type=="1"){
					path.setAttribute("stroke",options.untread_line_color);
					path.setAttribute("marker-end","url(#arrow2)");
				}
				line.appendChild(hi);
				line.appendChild(path);
				line.style.cursor="default";
			}
			$draw.appendChild(line);
			return line;
		}
		
		function drawPoly(id,pois,type,dash){
			var poly,strPath="";
			if(!type) 
				type="0";
			if(ie8mode){
				poly=document.createElement("v:Polyline");
				if(id!="")	poly.id=id;
				poly.filled="false";
				strPath=pois[0][0]+","+pois[0][1];
				for(var i=0; i < pois.length; i++){
					strPath += " " + pois[i][0] + "," + pois[i][1];
				}
				poly.points.value=strPath;
				poly.setAttribute("fromTo",pois[0][0]+","+pois[0][1]+","+pois[pois.length - 1][0]+","+pois[pois.length - 1][1]);
				poly.strokeWeight= "1.2";
				poly.stroke.EndArrow="Block";
				poly.style.cursor="pointer";
	            if(dash)	line.stroke.dashstyle="Dash";
	            if(type=="0"){
					poly.strokeColor=options.submit_line_color;
					poly.fillColor=options.submit_line_color;
				}
				else if(type=="1"){
					poly.strokeColor=options.untread_line_color;
					poly.fillColor=options.untread_line_color;
				}
				else if(type=="2"){
					poly.strokeWeight= "2.4";
					poly.strokeColor=options.hassubmit_line_color;
					poly.fillColor=options.hassubmit_line_color;
				}
			}
			else{
				poly=document.createElementNS("http://www.w3.org/2000/svg","g");
				var hi=document.createElementNS("http://www.w3.org/2000/svg","path");
				var path=document.createElementNS("http://www.w3.org/2000/svg","path");
				if(id!="")	poly.setAttribute("id",id);
				poly.setAttribute("from",pois[0][0]+","+pois[0][1]);
				poly.setAttribute("to",pois[pois.length - 1][0]+","+pois[pois.length - 1][1]);
				hi.setAttribute("visibility","hidden");
				hi.setAttribute("stroke-width",9);
				hi.setAttribute("fill","none");
				hi.setAttribute("stroke","white");
				strPath="M "+pois[0][0]+" "+pois[0][1];
				for(var i=1; i < pois.length; i++){
					strPath += " L " + pois[i][0] + "," + pois[i][1];
				}
				hi.setAttribute("d",strPath);
				hi.setAttribute("pointer-events","stroke");
				path.setAttribute("d",strPath);
				path.setAttribute("stroke-width",1.4);
				path.setAttribute("stroke-linecap","round");
				path.setAttribute("fill","none");
	            if(dash)	path.setAttribute("style", "stroke-dasharray:6,5");
	            if(type=="0"){
					path.setAttribute("stroke",options.submit_line_color);
					path.setAttribute("marker-end","url(#arrow1)");
				}
				else if(type=="1"){
					path.setAttribute("stroke",options.untread_line_color);
					path.setAttribute("marker-end","url(#arrow2)");
				}
				else if(type=="2"){
					path.setAttribute("stroke-width",2.4);
					path.setAttribute("stroke",options.hassubmit_line_color);
					path.setAttribute("marker-end","url(#arrow4)");
				}
				poly.appendChild(hi);
				poly.appendChild(path);
				poly.style.cursor="pointer";
			}
			$draw.appendChild(poly);
			return poly;
		}
	
		
		
        function px(n) {
	      return Math.round(n) + 'px';
	    }
	    function getPos(obj)
	    {
	      var pos = $(obj).offset();
	      return [pos.left, pos.top];
	    }
	    function mouseAbs(e) 
	    {
	      if(!docOffset)
	      	docOffset = getPos($workArea);
	      return [(e.pageX - docOffset[0]), (e.pageY - docOffset[1])];
	    }
	    

	    function createFlowTran(sNodeData,sOrd,eNodeData,eOrd){
	    	var pois = caclPolyPois(sNodeData,sOrd,eNodeData,eOrd);
	    	
			var lineObj = {
				__state:"add",
				id:getId(),
				flowversionid:$flowDataObj.flowversionid,
				fromnodeid:sNodeData.id,
				tonodeid:eNodeData.id,
				trantype:$type,
				allowuntread:1,
				conditionid:"-1",
				drawjson : {
					sOrd : sOrd,
					eOrd : eOrd,
					pois : pois
				}
			}
			
			$flowDataObj.wfs_flowtrans_list.push(lineObj);
			loadFlowTran(lineObj);
		}
		
		function caclPolyPois(sNodeData,sOrd,eNodeData,eOrd){
			var obj1 = {
   				ord : sOrd,
				left : sNodeData.drawjson.left,
				top : sNodeData.drawjson.top,
				width : sNodeData.drawjson.width,
				height : sNodeData.drawjson.height
   			}
   			
   			var obj2 = {
				ord : eOrd,
				left : eNodeData.drawjson.left,
				top : eNodeData.drawjson.top,
				width : eNodeData.drawjson.width,
				height : eNodeData.drawjson.height
			}

   			return calcPoints(obj1,obj2,options.min_distance);
		}
		
		function adjustFlowTran(lineObj){
			var sNodeData = getFlowNodeData(lineObj.fromnodeid);
			var sOrd = lineObj.drawjson.sOrd;
			var eNodeData = getFlowNodeData(lineObj.tonodeid);
			var eOrd = lineObj.drawjson.eOrd;
			
			var newPois = caclPolyPois(sNodeData,sOrd,eNodeData,eOrd);
			lineObj.drawjson.pois = newPois;
			
		    adjustLineDom(lineObj);
		    
		    adjustFlowTranCondition(lineObj);
		}
		
		function adjustFlowTranCondition(lineObj){
			var domid = "condition_" + lineObj.id;
			if(!lineObj.conditionid || lineObj.conditionid == "-1"){
				lineObj.conditionid="-1";
				lineObj.drawjson.condition = null;
				$("#" + domid).remove();
			}
			else{
				var conditionObj = getFlowConditionData(lineObj.conditionid);
				var poi1 = lineObj.drawjson.pois[0];
				var poi2 = lineObj.drawjson.pois[lineObj.drawjson.pois.length - 1];
				var x =  poi1[0] - 26 + (poi2[0] - poi1[0]) / 2;
				var y = poi1[1] - 12 + (poi2[1] - poi1[1]) / 2;
				lineObj.drawjson.condition = {
						name : conditionObj.conditionname,
						left : x,
						top : y
				}
				var domJq = $("#" + domid);
				if(domJq.size() < 1){
					loadFlowCondition(lineObj);
				}
				else{
					domJq.text(lineObj.drawjson.condition.name).css({
			    		left : px(lineObj.drawjson.condition.left),
			    		top : px(lineObj.drawjson.condition.top)
			    	});
				}
			}
		}
		
		function getFlowConditionData(conditionid){
	    	for(var i=0; i < $flowDataObj.wfs_condition_list.length; i++){
	    		if($flowDataObj.wfs_condition_list[i].id == conditionid 
	    				&& $flowDataObj.wfs_condition_list[i].__state!="del"){
	    			return $flowDataObj.wfs_condition_list[i];
	    		}
	    	}
	    	
	    	return null;
	    }
		
		function adjustLineDom(lineObj){
			var poly = $("#" + lineObj.id).get(0);
			var pois = lineObj.drawjson.pois;
			
			if(ie8mode){
				var strPath=pois[0][0]+","+pois[0][1];
				for(var i=0; i < pois.length; i++){
					strPath += " " + pois[i][0] + "," + pois[i][1];
				}
				poly.points.value=strPath;
				poly.setAttribute("fromTo",pois[0][0]+","+pois[0][1]+","+pois[pois.length - 1][0]+","+pois[pois.length - 1][1]);
			}
			else{
				var strPath="M "+pois[0][0]+" "+pois[0][1];
				for(var j=1; j < pois.length; j++){
					strPath += " L " + pois[j][0] + "," + pois[j][1];
				}
	   			poly.childNodes[0].setAttribute("d",strPath);
				poly.childNodes[1].setAttribute("d",strPath);
			}
		}
		
		function delFlowTran(lineObj){
			lineObj.__state="del";
			var poly = $("#" + lineObj.id).get(0);
			$draw.removeChild(poly);
			$("#condition_" + lineObj.id).remove();
		}
		
		function loadFlowTran(lineObj){
			var poly = drawPoly(lineObj.id,lineObj.drawjson.pois,lineObj.trantype);
			
			
			if(lineObj.drawjson.condition){
				loadFlowCondition(lineObj);
			}
		}
		
		function loadFlowCondition(lineObj){
			var domJq = $('<div id="condition_' +lineObj.id+ '" class="linecondition"></div>');
			domJq.data("myData",lineObj).text(lineObj.drawjson.condition.name).css({
	    		left : px(lineObj.drawjson.condition.left),
	    		top : px(lineObj.drawjson.condition.top)
	    	});
			
			if(options.editable){
				domJq.mousedown({lineData:lineObj,domJq:domJq},function(e){
					e.stopPropagation();
		    		var lineData = e.data.lineData;
		    		var mP = mouseAbs(e);
		    		
		    		$conditionMoveObj = {
	    				mP : mP,
	    				objP : e.data.domJq.position(),
	    				domJq :e.data.domJq,
	    				lineData : lineData
	    			}
				});
			}
			$workArea.append(domJq);
		}
		
		function getFlowTranData(lineid){
	    	for(var i=0; i < $flowDataObj.wfs_flowtrans_list.length; i++){
	    		if($flowDataObj.wfs_flowtrans_list[i].id == lineid){
	    			return $flowDataObj.wfs_flowtrans_list[i];
	    		}
	    	}
	    	
	    	return null;
	    }
	    
	    function createFlowNode(x,y,nodetype){
	    	var nodeObj = {
	    		__state:"add",
	    		id:getId(),
	    		flowid:$flowDataObj.id,
				flowversionid:$flowDataObj.flowversionid,
       			flownodename:"新的节点",
       			nodetype : nodetype,
       			embranch : "0",
       			converge : "0",
       			drawjson:{
			        width:110,
			        height:50,
			        left:x,
			        top:y
		        },
		        actorjson:{
		        	type : "流程启动者"
		        },
		        extjson:{}
		    };
		    
	    	if(nodetype=="0"){
	    		nodeObj.drawjson.width=60;
	    		nodeObj.drawjson.height=60;
	    		nodeObj.flownodename = "开始";
	    	}
	    	else if(nodeObj.nodetype=="1"){
	    		nodeObj.flownodename = "普通节点";
	    	}
	    	else if(nodeObj.nodetype=="2"){
	    		nodeObj.flownodename = "子流程节点";
	    	}
	    	else if(nodeObj.nodetype=="3"){
	    		nodeObj.flownodename = "自动节点";
	    	}
	    	else if(nodeObj.nodetype=="9"){
	    		nodeObj.drawjson.width=60;
	    		nodeObj.drawjson.height=60;
	    		nodeObj.flownodename = "结束";
	    	}
	    	
	    	$flowDataObj.wfs_flownode_list.push(nodeObj);
	    	loadFlowNode(nodeObj);
	    }
	    
	    function getFlowNodeData(nodeid){
	    	for(var i=0; i < $flowDataObj.wfs_flownode_list.length; i++){
	    		if($flowDataObj.wfs_flownode_list[i].id == nodeid){
	    			return $flowDataObj.wfs_flownode_list[i];
	    		}
	    	}
	    	
	    	return null;
	    }
	    
	    function adjustFlowNodeDom(nodeObj){
	    	var dom = $("#" + nodeObj.id);
	    	dom.css({
	    		left : px(nodeObj.drawjson.left),
	    		top : px(nodeObj.drawjson.top),
	    		width : px(nodeObj.drawjson.width),
	    		height : px(nodeObj.drawjson.height)
	    	});
	    	
	    	if(nodeObj.nodetype != "9")
	    		dom.find(".nodetext span").text(nodeObj.flownodename);
	    	
			for(var i=0; i < $flowDataObj.wfs_flowtrans_list.length; i++){
				var lineItem = $flowDataObj.wfs_flowtrans_list[i];
				if(lineItem.__state=="del")
					continue;
				if(lineItem.fromnodeid == nodeObj.id || lineItem.tonodeid == nodeObj.id){
					adjustFlowTran(lineItem);
				}
			}
	    }
	    
	    function delFlowNode(nodeObj){
	    	nodeObj.__state="del";
	    	var dom = $("#" + nodeObj.id);
	    	dom.remove();
	    	
	    	for(var i=0; i < $flowDataObj.wfs_flowtrans_list.length; i++){
				var lineItem = $flowDataObj.wfs_flowtrans_list[i];
				if(lineItem.__state=="del")
					continue;
				if(lineItem.fromnodeid == nodeObj.id || lineItem.tonodeid == nodeObj.id){
					delFlowTran(lineItem);
				}
			}
	    }
	    
	    function loadFlowNode(nodeObj){
	    	var node = null;
	    	if(nodeObj.nodetype=="0"){
	    		node = new BeginNode();
	    	}
	    	else if(nodeObj.nodetype=="1"){
	    		node = new TaskNode();
	    	}
	    	else if(nodeObj.nodetype=="2"){
	    		node = new ChildNode();
	    	}
	    	else if(nodeObj.nodetype=="3"){
	    		node = new AutoNode();
	    	}
	    	else if(nodeObj.nodetype=="9"){
	    		node = new EndNode();
	    	}
	    	
	    	node.initNode(nodeObj);
	    	return node;
	    }

	    function FlowNode(){
	    	this.nodeObj=null;
			this.$dom=null;

	    	this.initNode = function(nodeObj){
	    		this.nodeObj=nodeObj;
				
				this.$dom = $('<div class="nodeitem" style="width: ' + px(nodeObj.drawjson.width) +'; height: ' +px(nodeObj.drawjson.height)+ '; top: ' + px(nodeObj.drawjson.top) + '; left: ' + px(nodeObj.drawjson.left) +'; "></div>')
	    		this.$dom.data("myData",nodeObj).attr("id",nodeObj.id);
	    		
	    		this.createHandler();
	    		this.createContent();
	    		
	    		$workArea.append(this.$dom);
	    		
	    		if(!options.editable)
	    			return;
	    			
	    		this.$dom.click(function(e){
	    			//console.log("nodeitem.click");
	    			e.stopPropagation();
	    			
	    			if($state == "line" && $joinObj){
	    				return;
	    			}
	    			
	    			if($state != "sel"){
	    				changeState("sel","-1");
	    			}
	    			
	    			changeSelectedNode($(this),e.shiftKey);
	    		}).mousedown(function(e){
	    			e.stopPropagation();
	    		}); 
	    		
	    		this.$dom.find(".nodeitem-joinpoint").click({nodeData:this.nodeObj},function(e){
	    			//console.log("nodeitem-joinpoint.click");
	    			e.stopPropagation();
	    			if($state != "line"){
	    				return;
	    			}
	    			
	    			if(!$joinObj){
		    			var ord = $(this).attr("ord");
		    			var nodeData = e.data.nodeData;
		    			var sP = [0,0];
		    			if(ord == "n"){
		    				sP[0] = nodeObj.drawjson.left + (nodeObj.drawjson.width / 2);
		    				sP[1] = nodeObj.drawjson.top;
		    			}
		    			else if(ord == "e"){
		    				sP[0] = nodeObj.drawjson.left + nodeObj.drawjson.width;
		    				sP[1] = nodeObj.drawjson.top + (nodeObj.drawjson.height / 2);
		    			}
		    			else if(ord == "s"){
		    				sP[0] = nodeObj.drawjson.left + (nodeObj.drawjson.width / 2);
		    				sP[1] = nodeObj.drawjson.top + nodeObj.drawjson.height;
		    			}
		    			else{
		    				sP[0] = nodeObj.drawjson.left;
		    				sP[1] = nodeObj.drawjson.top + (nodeObj.drawjson.height / 2);
		    			}
		    			var eP = mouseAbs(e);
	
		    			var line = drawLine("tmp_join_line",sP,eP,$type,true);
		    			
		    			$joinObj = {
		    				ord : ord,
		    				nodeData : nodeData,
		    				sP : sP,
		    				line : line
		    			};
		    			
		    			$(this).addClass("selected");
	    			}
	    			else{
	    				var endOrd = $(this).attr("ord");
		    			var endNodeData = e.data.nodeData;
		    			
		    			if(endNodeData === $joinObj.nodeData)
		    				return;
		    			

		    			createFlowTran($joinObj.nodeData,$joinObj.ord,endNodeData,endOrd);
		    			
						$(".nodeitem-joinpoint").removeClass("selected");
		    			$draw.removeChild($joinObj.line);
		    			$joinObj=null;
	    			}
	    		});
	    		
	    		this.$dom.find(".nodeitem-joinpoint").hover(function(e){
	    			$(this).toggleClass("hover");
	    		});

	    		this.$dom.find(".nodeitem-resize").mousedown({nodeData:this.nodeObj,domJq:this.$dom},function(e){
	    			e.stopPropagation();
	    			var nodeData = e.data.nodeData;
	    			var proxyDomJq = $(".nodeproxy");
	    			if(proxyDomJq.size() < 1){
	    				proxyDomJq = $('<div class="nodeproxy"></div>');
	    				$workArea.append(proxyDomJq);
	    			}
	    			
	    			proxyDomJq.css("left",px(nodeData.drawjson.left))
	    				.css("top",px(nodeData.drawjson.top))
	    				.css("width",px(nodeData.drawjson.width))
	    				.css("height",px(nodeData.drawjson.height));
					
					var mP = mouseAbs(e);
	    			$nodeMoveObj = {
	    				mP : mP,
	    				domJq :e.data.domJq,
	    				nodeData : nodeData,
	    				proxyDomJq : proxyDomJq
	    			}
	    			
	    			proxyDomJq.show();
	    		});
	    		
	    	}
	    	
	    	this.createHandler = function(){
				var html =  '' 
							+'<div class="nodeitem-selection">'
							+'	<div class="nodeitem-hline"></div>'
							+'	<div class="nodeitem-hline bottom"></div>'
							+'	<div class="nodeitem-vline right"></div>'
							+'	<div class="nodeitem-vline"></div>'
							+'</div>'
							+'<div class="nodeitem-resize">'
							+'	<div class="ord-n nodeitem-dragbar"></div>'
							+'	<div class="ord-s nodeitem-dragbar"></div>'
							+'	<div class="ord-e nodeitem-dragbar"></div>'
							+'	<div class="ord-w nodeitem-dragbar"></div>'
							+'	<div class="ord-n nodeitem-handle"></div>'
							+'	<div class="ord-s nodeitem-handle"></div>'
							+'	<div class="ord-e nodeitem-handle"></div>'
							+'	<div class="ord-w nodeitem-handle"></div>'
							+'	<div class="ord-nw nodeitem-handle"></div>'
							+'	<div class="ord-ne nodeitem-handle"></div>'
							+'	<div class="ord-se nodeitem-handle"></div>'
							+'	<div class="ord-sw nodeitem-handle"></div>'
							+'</div>'
							+'<div class="nodeitem-join">'
							+'	<div class="ord-n nodeitem-joinpoint" ord="n"></div>'
							+'	<div class="ord-s nodeitem-joinpoint" ord="s"></div>'
							+'	<div class="ord-e nodeitem-joinpoint" ord="e"></div>'
							+'	<div class="ord-w nodeitem-joinpoint" ord="w"></div>'
							+'</div>'
							;
				this.$dom.append(html);
			}
			
	    	this.createContent = function(){
	    	}
	    }
	    
	    function BeginNode(){
			this.createContent = function(){
	    		var html =  '<div class="nodeitem-content begin">'
							+'	<table>'
							+'		<tbody>'
							+'			<td class="nodetext">'
							+'				<span>' +this.nodeObj["flownodename"]+ '</span>'
							+'			</td></tr>'
							+'		</tbody>'
							+'	</table>'	
							+'</div>';
				this.$dom.append(html);
	    	}
	    }
	    BeginNode.prototype = new FlowNode();
	    
	    function EndNode(){
	    	this.createContent = function(){
	    		var html =  '<div class="nodeitem-content end">'
							+'	<table>'
							+'		<tbody>'
							+'			<td class="nodetext">'
							+'				<span><i class="fa fa-stop"></i></span>'
							+'			</td></tr>'
							+'		</tbody>'
							+'	</table>'	
							+'</div>';
				this.$dom.append(html);
	    	}
	    }
	    EndNode.prototype = new FlowNode();
	    
	    function TaskNode(){
	    	this.createContent = function(){
	    		var html =  '<div class="nodeitem-content task">'
							+'	<table>'
							+'		<tbody>'
							+'			<tr><td class="icon">'
							+'				<i class="fa fa-users"></i>'
							+'			</td>'
							+'			<td class="nodetext">'
							+'				<span>' +this.nodeObj["flownodename"]+ '</span>'
							+'			</td></tr>'
							+'		</tbody>'
							+'	</table>'	
							+'</div>';
				this.$dom.append(html);
	    	}
	    }
	    
	    TaskNode.prototype = new FlowNode();
	    
	    function AutoNode(){
	    	this.createContent = function(){
	    		var html =  '<div class="nodeitem-content auto">'
							+'	<table>'
							+'		<tbody>'
							+'			<tr><td class="icon">'
							+'				<i class="iconfont icon-zidongjiedian"></i>'
							+'			</td>'
							+'			<td class="nodetext">'
							+'				<span>' +this.nodeObj["flownodename"]+ '</span>'
							+'			</td></tr>'
							+'		</tbody>'
							+'	</table>'	
							+'</div>';
				this.$dom.append(html);
	    	}
	    }
	    AutoNode.prototype = new FlowNode();
	    
	    function ChildNode(){
	    	this.createContent = function(){
	    		var html =  '<div class="nodeitem-content child">'
							+'	<table>'
							+'		<tbody>'
							+'			<tr><td class="icon">'
							+'				<i class="iconfont icon-zijiedian"></i>'
							+'			</td>'
							+'			<td class="nodetext">'
							+'				<span>' +this.nodeObj["flownodename"]+ '</span>'
							+'			</td></tr>'
							+'		</tbody>'
							+'	</table>'	
							+'</div>';
				this.$dom.append(html);
	    	}
	    }
	    ChildNode.prototype = new FlowNode();     
	    
	    
	    var _idIndex = 0;
	    var _idList = null;
	    function initIds(){
	    	var url = window.g_path + "/sys/flow/createId";
			getJson(url,{num:"200"},true,function(re){
				_idList = re.data;
				
				if($flowDataObj.wfs_flownode_list.length < 1)
					initFlowStartNode();
			});
	    }
	    
	    function getId(){
	    	if(_idIndex >200)
	    	{
	    		alert("over limit max id count");
	    	}
	    	return _idList[_idIndex++];
	    }
	    
	    
	    var $workArea = $("<div class='lpflowchar_work'></div>").attr({"unselectable":"on",
        								"onselectstart":"return false",
        								"onselect":"document.selection.empty()"});
        $workArea.appendTo(containerJq);
        var $draw = initDraw();

        var $state="sel";//当前状态,sel:选择,line:迁移线,node:创建节点
        var $type="-1";//当前状态，对应的类型（line[1:迁移线，2回退线],node(0:开始节点,1:普通节点，2:子流程节点，3:自动节点,9:结束节点)）
        var $joinObj=null;//连线时用的参数
        var $selObjType = "flow";//当前选中对象类型(flow,line,node)
        var $selDomJq = $workArea;//当前选中对象
        var $isMoveing=false;
        var $nodeMoveObj=null;//移动节点时参数
        var $selectionObj=null;//框选时用的参数
        var $conditionMoveObj=null;
        var $reDoList = [];
        var $unDoList = [];
		var $flowDataObj = options.flowDataObj;
		window.$flowDataObj = options.flowDataObj;
		
		initFlow();
		function initFlow(){
			if(options.editable){
		    	for(var i=0; i < $flowDataObj.wfs_flownode_list.length; i++){
					var nodeItem = $flowDataObj.wfs_flownode_list[i];
					if(nodeItem.__state=="del")
						continue;
					loadFlowNode(nodeItem);
				}
				
		    	for(var i=0; i < $flowDataObj.wfs_flowtrans_list.length; i++){
					var lineItem = $flowDataObj.wfs_flowtrans_list[i];
					if(lineItem.__state=="del")
						continue;
					loadFlowTran(lineItem)
				}
			}
			else{
				for(var i=0; i < $flowDataObj.wfs_flownode_list.length; i++){
					var nodeItem = $flowDataObj.wfs_flownode_list[i];
					if(nodeItem.__state=="del")
						continue;
					var node = loadFlowNode(nodeItem);
					
					if(nodeItem.runstatus == "0"){
						node.$dom.addClass("current");
					}
					else if(nodeItem.runstatus == "1"){
						node.$dom.addClass("pass");
					}
					else{
						node.$dom.addClass("nopass");
					}
				}
				
		    	for(var i=0; i < $flowDataObj.wfs_flowtrans_list.length; i++){
					var lineItem = $flowDataObj.wfs_flowtrans_list[i];
					if(lineItem.__state=="del")
						continue;
					
					if(lineItem.trantype == "1" && lineItem.runstatus){
						loadFlowTran(lineItem);
					}
					else if(lineItem.trantype == "0"){
						if(!lineItem.runstatus){
							loadFlowTran(lineItem);
						}
					}
				}
		    	
		    	for(var i=0; i < $flowDataObj.wfs_flowtrans_list.length; i++){
					var lineItem = $flowDataObj.wfs_flowtrans_list[i];
					if(lineItem.__state=="del")
						continue;
					
					if(lineItem.trantype == "0"){
						if(lineItem.runstatus){
							lineItem.trantype = "2";
							loadFlowTran(lineItem);
						}
					}
				}
			}
			
			
			var minWidth = 0,minHeight = 0;
			for(var i=0; i < $flowDataObj.wfs_flownode_list.length; i++){
				var nodeItem = $flowDataObj.wfs_flownode_list[i];
				if(nodeItem.__state=="del")
					continue;
				
				var x = nodeItem.drawjson.left + nodeItem.drawjson.width + options.min_distance;
				var y = nodeItem.drawjson.top + nodeItem.drawjson.height + options.min_distance;
				if(minWidth < x )
					minWidth = x;
				if(minHeight < y )
					minHeight = y;
			}
			$workArea.css("minWidth",px(minWidth)).css("minHeight",px(minHeight));
	    }
		
		
		if(!options.editable)
	    	return;
	    
		$.LPFlowChar.getId = getId;
  		$("#btnSave").click(function(){
  			flowSave();
  		});
  		$("#btnUndo").click(function(){
  			undo();
  		});
  		$("#btnRedo").click(function(){
  			redo();
  		});
  		$("#btnYCenter").click(function(){//居中对齐
  			if($state=="sel" && jQuery.isArray($selDomJq) && $selDomJq.length > 1){
  				var obj1 = $selDomJq[0].data("myData");
  				var tmp = obj1.drawjson.left + (obj1.drawjson.width / 2);
  				for(var i=1; i < $selDomJq.length; i++){
  					obj2 = $selDomJq[i].data("myData");
  					var newX = tmp - (obj2.drawjson.width / 2);
  					obj2.drawjson.left = newX;
  					
  					adjustFlowNodeDom(obj2);
  				}
  			}
  		});
  		$("#btnXCenter").click(function(){//中间对齐
  			if($state=="sel" && jQuery.isArray($selDomJq) && $selDomJq.length > 1){
  				var obj1 = $selDomJq[0].data("myData");
  				var tmp = obj1.drawjson.top + (obj1.drawjson.height / 2);
  				for(var i=1; i < $selDomJq.length; i++){
  					obj2 = $selDomJq[i].data("myData");
  					var newY = tmp - (obj2.drawjson.height / 2);
  					obj2.drawjson.top = newY;
  					
  					adjustFlowNodeDom(obj2);
  				}
  			}
  		});
        $(".btnsel").click(function(){
        	$(".btnsel").removeClass("active");
        	$(this).addClass("active");
        	changeState($(this).attr("state"),$(this).attr("val"));
        });
        
        $workArea.mousedown(function(e){
        	//console.log("$workArea.mousedown");
        	if($state == "sel"){
    			 var selectionDomJq = $(".mouse-selection");
    			if(selectionDomJq.size() < 1){
    				var html = '<div class="mouse-selection">'
							+'		<div class="mouse-selection-hline"></div>'
							+'		<div class="mouse-selection-hline bottom"></div>'
							+'		<div class="mouse-selection-vline right"></div>'
							+'		<div class="mouse-selection-vline"></div>'
							+'</div>'
    				selectionDomJq = $(html).appendTo($workArea);
    			}
    			
				var mP = mouseAbs(e);
				selectionDomJq.css("left",px(mP[0]))
    				.css("top",px(mP[1]))
    				.css("width","0px")
    				.css("height","0px");
    				
    			$selectionObj = {
    				mP : mP,
    				selectionDomJq : selectionDomJq
    			}
	    		selectionDomJq.show();
        	}
        });
        
        $workArea.mousemove(function(e){
        	////console.log("$workArea.mousemove");
			if($state == "line" && $joinObj){
				var sP = $joinObj.sP;
				var eP = mouseAbs(e);
				var line = $joinObj.line
				if(ie8mode){
					line.points.value=sP[0]+","+sP[1]+" "+eP[0]+","+eP[1];
				}
				else{
					line.childNodes[0].setAttribute("d","M "+sP[0]+" "+sP[1]+" L "+eP[0]+" "+eP[1]);
					line.childNodes[1].setAttribute("d","M "+sP[0]+" "+sP[1]+" L "+eP[0]+" "+eP[1]);
				}
			}
			else if($state == "sel" && $nodeMoveObj){
				var mP = $nodeMoveObj.mP;
				var eP = mouseAbs(e);
				var x = eP[0] - mP[0] + $nodeMoveObj.nodeData.drawjson.left;
				var y = eP[1] - mP[1] + $nodeMoveObj.nodeData.drawjson.top;
				
				$nodeMoveObj.proxyDomJq.css("left",px(x)).css("top",px(y));
			}
			else if($state == "sel" && $selectionObj){
				var mP = $selectionObj.mP;
				var eP = mouseAbs(e);
				var x = eP[0] >= mP[0] ? mP[0] : eP[0];
				var y = eP[1] >= mP[1] ? mP[1] : eP[1];
				
				var width = Math.abs(eP[0] - mP[0]);
				var height = Math.abs(eP[1] - mP[1]);
				$selectionObj.selectionDomJq.css({
						left : px(x),
						top : px(y),
						width : px(width),
						height : px(height)
					});
				
			}
			
			if($conditionMoveObj){
				var mP = $conditionMoveObj.mP;
				var eP = mouseAbs(e);
				var x = $conditionMoveObj.objP.left + eP[0] - mP[0];
				var y = $conditionMoveObj.objP.top + eP[1] - mP[1];
				$conditionMoveObj.domJq.css({
					left : x,
					top : y
				});
				
				$conditionMoveObj.lineData.drawjson.condition.left = x;
				$conditionMoveObj.lineData.drawjson.condition.top = y;
			}
		});
        
        $workArea.click(function(e){
        	if($state == "sel" && $isMoveing){
        		$isMoveing=false;
        		return;
        	}
        	
        	//console.log("$workArea.click");
			clearState();
			
		});
		
		$workArea.mouseup(function(e){
			//console.log("$workArea.mouseup");
			if($state == "sel" && $nodeMoveObj){
				var mP = $nodeMoveObj.mP;
				var eP = mouseAbs(e);
				var disX = eP[0] - mP[0];
				var disY = eP[1] - mP[1];
				
				for(var i=0; i < $selDomJq.length; i++){
					var nodeData = $selDomJq[i].data("myData");
					nodeData.drawjson.left += disX;
					nodeData.drawjson.top += disY;
					adjustFlowNodeDom(nodeData);
				}
				
				$nodeMoveObj.proxyDomJq.hide();

				$nodeMoveObj=null;
				$isMoveing=true;
			}
			else if($state == "sel" && $selectionObj){
				var mP = $selectionObj.mP;
				var eP = mouseAbs(e);
				
				var left = mP[0] <= eP[0] ? mP[0] : eP[0];
				var top = mP[1] <= eP[1] ? mP[1] : eP[1];
				var right = mP[0] <= eP[0] ? eP[0] : mP[0];
				var bottom = mP[1] <= eP[1] ? eP[1] : mP[1];
				
				var selNodes = [];
				for(var i=0; i < $flowDataObj.wfs_flownode_list.length; i++){
					var nodeData = $flowDataObj.wfs_flownode_list[i];
					if(nodeData.__state == "del")
						continue;
					
					var l = nodeData.drawjson.left;
					var r = nodeData.drawjson.left + nodeData.drawjson.width;
					var t = nodeData.drawjson.top;
					var b = nodeData.drawjson.top + nodeData.drawjson.height;
					//判断是否被框选
					if(((l >= left && l <= right) || (r >= left && r <= right))
						&& ((t >= top && t <= bottom) || (b >= top && b <= bottom)))
					{
						selNodes.push(nodeData);
					}
				}
				
				if(selNodes.length >0){
					selNodes = nodesSort(selNodes);
					changeSelectedNode($("#" + selNodes[0].id),false);
					for(var i=1; i < selNodes.length; i++){
						changeSelectedNode($("#" + selNodes[i].id),true);
					}
					
					$isMoveing=true;
				}
				else{
					$isMoveing=false;
				}
				
				$selectionObj.selectionDomJq.hide();
				$selectionObj=null;
			}
			
			if($conditionMoveObj){
				$isMoveing = true;
				$conditionMoveObj = null;
			}
		});
		
		function nodesSort(selNodes){
			var newList = [];
			while (selNodes.length > 0) {
				var index = -1;
				var preObj = null;
				for(var i=0; i < selNodes.length; i++){
					if(preObj == null){
						index = i
						preObj = selNodes[i];
					}
					else{
						var itemObj = selNodes[i];
						if((itemObj.drawjson.top < preObj.drawjson.top)
							|| ((itemObj.drawjson.top == preObj.drawjson.top) 
								&& itemObj.drawjson.left < preObj.drawjson.left)){
							index = i;
							preObj = itemObj;
						}
					}
				}
				
				selNodes.splice(index,1);
				newList.push(preObj);
			}
			return newList;
		}
		
		$workArea.dblclick(function(e){
			//console.log("$workArea.dblclick");
			if($state == "node"){
				var mp = mouseAbs(e);
				createFlowNode(mp[0],mp[1],$type);
			}
		});
		
		$($draw).delegate(ie8mode ? "polyline" : "g","click",function(e){
			//console.log("polyline.click");
	    	e.stopPropagation();
	    	
	    	if($state == "line" && $joinObj){
  				return;
  			}
  			
  			if($state != "sel"){
  				changeState("sel","-1");
  			}
  			
			changeSelectedLine(this);
		});
		
		$($draw).delegate(ie8mode ? "polyline" : "g","mousedown",function(e){
			//console.log("polyline.mousedown");
	    	e.stopPropagation();
		});
		
		//redo,undo管理器
		//支持操作:add、del、edit、list
		var UndoManager = {
			undoList : [],
			redoList : [],
			clear : function(){
				this.undoList = [];
				this.redoList = [];
			},
			push : function(common){
				this.undoList.push(common);
				this.redoList = [];
			},
			undo : function(){
				if(this.undoList.length > 0){
					var common = this.redoList[this.undoList.length - 1];
					this.undoList.splice(this.undoList.length - 1,1);
					common.undo();
					this.redoList.push(common);
				}
				
				if(undoList.length < 1){
					
				}
				
				if(redoList.length < 1){
					
				}
			},
			redo : function(){
				if(this.redoList.length > 0){
					var common = this.redoList[this.redoList.length - 1];
					this.redoList.splice(redoList.length - 1,1);
					common.redo();
					this.undoList.push(common);
				}
				
				if(undoList.length < 1){
					
				}
				
				if(redoList.length < 1){
					
				}
			}
		};
		
		var KeyManager = (function () {
		
		  function moveNode(x,y){
		  	if($state == "sel" && $selObjType == "node"){
		  		for(var i=0; i < $selDomJq.length; i++){
		  			var nodeData = $selDomJq[i].data("myData");
		  			nodeData.drawjson.left += x;
			  		if(x != 0){
			  			nodeData.drawjson.left += x;
			  		}
			  		
			  		if(y != 0){
			  			nodeData.drawjson.top += y;
			  		}
			  		
			  		adjustFlowNodeDom(nodeData);
				}
		  	}
		  	
		  }
		  
		  function del(){
		  	if($state == "sel"){
		  		if($selObjType == "node"){
		  			for(var i=0; i < $selDomJq.length; i++){
			  			var nodeData = $selDomJq[i].data("myData");
				  		delFlowNode(nodeData);
					}

					clearState();
		  		}
		  		else if($selObjType == "line"){
		  			var lineObj = getFlowTranData($selDomJq.attr("id"));
		  			delFlowTran(lineObj);
					clearState();
		  		}
		  	}
		  }
		  
	      function parseKey(e) 
	      {
	        /* if (e.ctrlKey || e.metaKey || e.shiftKey) {
	          return true;
	        } */
	
	        switch (e.keyCode) {
		        case 37:
		          moveNode(-1,0);
		          break;
		        case 39:
		          moveNode(1,0);
		          break;
		        case 38:
		          moveNode(0,-1);
		          break;
		        case 40:
		          moveNode(0,1);
		          break;
		        case 46:
		          del();
		          break;
		        case 65:
		          if(e.ctrlKey) {
		        	  selectAll();
			          e.returnValue=false;
			          return false;
		          }
		          break;
		        case 83:
		          if(e.ctrlKey) {
		        	  flowSave();
		        	  e.returnValue=false;
		        	  return false;
		          }
		          break;
				case 89:
		          if(e.ctrlKey) {
		        	  redo();
		        	  e.returnValue=false;
		        	  return false;
		          }
		          break;
		        case 90:
		          if(e.ctrlKey) {
		        	  undo();
		        	  e.returnValue=false;
		        	  return false; false;
		          }
		          break;
	        }
	        return true;
	      }
	      
		  $(document).keydown(parseKey);
		  
	    }());
	    
    	initIds();

		function initFlowStartNode(){
		    createFlowNode(250,40,"0");
		    createFlowNode(250,400,"9");
		}
	    
		function flowSave(){
			if(window.flowSaveing)
				return;
				
			var url = window.g_path + "/sys/flow/flowSave";
			window.flowSaveing = true;
			getJson(url,{data:JSON.stringify($flowDataObj)},true,function(re){
				window.flowSaveing = false;
				if(re.isOk){
					$flowDataObj.saveversion = re.msg;
					$flowDataObj.wfs_param_list = refreshDataList($flowDataObj.wfs_param_list);
					$flowDataObj.wfs_condition_list = refreshDataList($flowDataObj.wfs_condition_list);
					$flowDataObj.wfs_event_list = refreshDataList($flowDataObj.wfs_event_list);
					$flowDataObj.wfs_flownode_list = refreshDataList($flowDataObj.wfs_flownode_list);
					$flowDataObj.wfs_flowtrans_list = refreshDataList($flowDataObj.wfs_flowtrans_list);
					MsgBox.showSuccessTips("保存成功！");
				}
				else{
					MsgBox.showErrTips("保存失败！" + re.msg);
				}
			});
		}
		
		function refreshDataList(list){
			var newList = [];
			for(var i=0; i < list.length; i++){
				var item = list[i];
				if(item.__state && item.__state=="del")
					continue;
				item.__state = "edit";
				newList.push(item);
			}
			
			return newList;
		}
		
		function undo(){
			MsgBox.showWarningTips("暂未实现撤销功能！");
		}
		
		function redo(){
			MsgBox.showWarningTips("暂未实现重做功能！");
		}
		
		function selectAll(){
			if($state == "sel"){
				var selNodes = [];
				for(var i=0; i < $flowDataObj.wfs_flownode_list.length; i++){
					var nodeData = $flowDataObj.wfs_flownode_list[i];
					if(nodeData.__state == "del")
						continue;
					
					selNodes.push(nodeData);
				}
					
				if(selNodes.length >0){
					selNodes = nodesSort(selNodes);
					changeSelectedNode($("#" + selNodes[0].id),false);
					for(var i=1; i < selNodes.length; i++){
						changeSelectedNode($("#" + selNodes[i].id),true);
					}
				}
			}
		}
		
		changeSelectedFlow();
	}
	
	$.LPFlowChar.defaults = {
		editable : true,
	    submit_line_color:"#444",
	    untread_line_color:"#FF0000",
	    selected_line_color:"#FF8C00",
	    hassubmit_line_color:"#00AA00",
	    min_distance : 28
	};
	
	$.LPFlowChar.flowEditConfig = [
	   {
		   "name":"流程ID",
		   "field":"id",
		   "type":"label"
	   },
	   {
		   "name":"流程版本ID",
		   "field":"flowversionid",
		   "type":"label"
	   },
	   {
		   "name":"流程名称",
		   "field":"flowname",
		   "type":"textbox"
	   },
	   {
		   "name":"待办地址",
		   "field":"openurl",
		   "type":"textbox"
	   },
	   {
		   "name":"流程参数",
		   "type":"buttonedit",
		   "click":function(jsonData){
		   		var url = window.g_path + "/sys/flow/edit_flowparam";
		   		MsgBox.openWin(url,"流程参数","800px","500px",function(win){
		   			win.init(jsonData.wfs_param_list);
		   		},function(action,win){
		   			if(action == "ok"){
		   				var data = win.getJsonData();
		   				//var newData = mini.clone(data);
		   				mergeList(jsonData.flowversionid,jsonData.wfs_param_list,data,["parambm","paramname"]);
		   			}
		   		});
		   }
	   },
	   {
		   "name":"流程条件",
		   "type":"buttonedit",
		   "click":function(jsonData){
		   		var url = window.g_path + "/sys/flow/edit_flowcondition";
		   		MsgBox.openWin(url,"流程条件","800px","500px",function(win){
		   			win.init(jsonData.wfs_condition_list);
		   		},function(action,win){
		   			if(action == "ok"){
		   				var data = win.getJsonData();
		   				//var newData = mini.clone(data);
		   				mergeList(jsonData.flowversionid,jsonData.wfs_condition_list,data,["conditionname","conditiontype","comparetype","leftval","rightval"]);
		   			}
		   		});
		   }
	   },
	   {
		   "name":"流程事件",
		   "type":"buttonedit",
		   "click":function(jsonData){
			   var url = window.g_path + "/sys/flow/edit_flowevent";
		   		MsgBox.openWin(url,"流程事件","800px","500px",function(win){
		   			win.init(jsonData.wfs_event_list);
		   		},function(action,win){
		   			if(action == "ok"){
		   				var data = win.getJsonData();
		   				//var newData = mini.clone(data);
		   				mergeList(jsonData.flowversionid,jsonData.wfs_event_list,data,["javaclass","px"]);
		   			}
		   		});
		   }
	   }
	];
	
	$.LPFlowChar.nodeEditConfig = [
		{
		   "name":"流程节点ID",
		   "field":"id",
		   "type":"label"
	   },
	   {
		   "name":"流程版本ID",
		   "field":"flowversionid",
		   "type":"label"
	   },
	   {
		   "name":"节点类型",
		   "field":"nodetype",
		   "type":"label",
		   "showText":function(jsonData){
		   		var val = jsonData["nodetype"];
		   		if(val == "0")
		   			return "开始节点";
		   		else if(val == "1")
		   			return "普通节点";
		   		else if(val == "2")
		   			return "子流程节点";
		   		else if(val == "3")
		   			return "自动节点";	
		   		else if(val == "9")
		   			return "结束节点";	
		   		else
		   			return "未知节点";		
		   }
	   },
	   {
		   "name":"节点名称",
		   "field":"flownodename",
		   "type":"textbox"
	   },
	   {
		   "name":"分支模式",
		   "field":"embranch",
		   "type":"combobox",
		   "data":[
		   			{"id":"0","text":"单一分支"},
		   			{"id":"1","text":"单一分支（带选择）"},
		   			{"id":"2","text":"多路分支"},
		   			{"id":"3","text":"多路分支（带选择）"}
		   		  ]
	   },
	   {
		   "name":"聚合模式",
		   "field":"converge",
		   "type":"combobox",
		   "data":[
		   			{"id":"0","text":"单一聚合"},
		   			{"id":"1","text":"多路聚合"}
		   		  ]
	   },
	   {
		   "name":"流程参与者",
		   "field":"actorjson",
		   "type":"buttonedit",
		   "click":function(jsonData){
		   		if(jsonData.nodetype == "2" || jsonData.nodetype == "3"){
		   			MsgBox.showWarningTips("系统暂不支持该节点类型功能!");
		   			return;
		   		}
		   		
		   		var url = window.g_path + "/sys/flow/edit_actorjson";
		   		MsgBox.openWin(url,"流程参与者","800px","500px",function(win){
		   			win.init(jsonData.actorjson,$flowDataObj.wfs_flownode_list,$flowDataObj.wfs_param_list);
		   		},function(action,win){
		   			if(action == "ok"){
		   				var data = win.getJsonData();
		   				var actorjson = mini.clone(data);
		   				jsonData.actorjson = actorjson;
		   			}
		   		});
		   }
	   },
	   {
		   "name":"扩展属性",
		   "field":"commonjson",
		   "type":"buttonedit",
		   "click":function(jsonData){
		   		if(jsonData.nodetype == "2" || jsonData.nodetype == "3"){
		   			MsgBox.showWarningTips("系统暂不支持该节点类型功能!");
		   			return;
		   		}
		   		
		   		var url = window.g_path + "/sys/flow/edit_extjson";
		   		MsgBox.openWin(url,"节点扩展属性","550px","350px",function(win){
		   			win.init(jsonData.extjson);
		   		},function(action,win){
		   			if(action == "ok"){
		   				var data = win.getJsonData();
		   				var extjson = mini.clone(data);
		   				jsonData.extjson = extjson;
		   			}
		   		});
		   }
	   }
	   
	];
	
	$.LPFlowChar.lineEditConfig = [
		{
		   "name":"连线ID",
		   "field":"id",
		   "type":"label"
	   },
	   {
		   "name":"源节点id",
		   "field":"fromnodeid",
		   "type":"label"
	   },
	   {
		   "name":"目标节点id",
		   "field":"tonodeid",
		   "type":"label"
	   },
	   {
		   "name":"迁移类型",
		   "field":"trantype",
		   "type":"label",
		   "showText":function(jsonData){
		   		var val = jsonData["trantype"];
		   		if(val == "0")
		   			return "迁移线";
		   		else if(val == "1")
		   			return "回退线";
		   		
		   		return "未知";
		   }
	   },
	   {
		   "name":"是否允许回退",
		   "field":"allowuntread",
		   "type":"combobox",
		   "data":[
		   			{"id":"0","text":"不允许"},
		   			{"id":"1","text":"允许"}
		   		  ]
	   },
	   {
		   "name":"流程条件",
		   "field":"conditionid",
		   "type":"combobox",
		   "data":function(){
			   var datas = [{"id":"-1","text":"无"}];
			   for(var i=0; i < $flowDataObj.wfs_condition_list.length; i++){
				   var itemData = $flowDataObj.wfs_condition_list[i];
				   if(itemData.__state=="del")
					   continue;
				   datas.push({"id":itemData.id,"text":itemData.conditionname});
			   }
		   	   return datas;
		   }
	   }
	];
	
	function mergeList(flowversionid,list,changeList,fileds){
		for(var i=0; i < changeList.length; i++){
			var changeItemData = changeList[i];
			var itemData = null;
			if(changeItemData["_state"] == "added"){
				itemData = {
					__state : "add",
					flowversionid : flowversionid,
					id : $.LPFlowChar.getId()
				};
				for(var j=0; j < fileds.length; j++){
					itemData[fileds[j]] = changeItemData[fileds[j]];
				}
				list.push(itemData);
				continue;
			}	
			
			for(var j=0; j < list.length; j++){
				if(list[j].id == changeItemData.id){
					itemData = list[j];
					break;
				}
			}
  					
			if(changeItemData["_state"] == "modified"){
				for(var j=0; j < fileds.length; j++){
					itemData[fileds[j]] = changeItemData[fileds[j]];
				}
			}
			else{
				itemData.__state="del";
			}
		}
	}
})(jQuery);