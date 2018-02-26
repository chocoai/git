
var lpsys = {}

//导入excel
lpsys.setImport = function (domid,callback){
	var url = window.g_path + "/sys/attach/importExcel?callback=" + callback;
	var _maxUpload = 100;
	var uploadCtrl = new plupload.Uploader({
        browse_button: domid, 
        url: url,
        filters: {
            mime_types : [
              { title : "Excel files", extensions : "xls,xlsx" }, 
            ],
            max_file_size: _maxUpload + 'Mb'
        },
        flash_swf_url: window.g_path + '/js/plupload/Moxie.swf'
    });

    uploadCtrl.init();
    uploadCtrl.bind('FilesAdded', function (uploader, files) {
    	showLoading();
        uploader.start();
    });
    
    uploadCtrl.bind('FileUploaded', function (uploader, file, responseObject) {
        jQuery(document.body).append(responseObject.response);
    });

    uploadCtrl.bind('UploadComplete', function (uploader, files) {
        hideLoading();
        uploader.splice(0);
    });

    uploadCtrl.bind('Error', function (uploader, errObject) {
        var msg = "";
        if (errObject.code == plupload.HTTP_ERROR) {
            msg = "上传文件\"" + errObject.file.name + "\"时出错：网络故障！";
        }
        else if (errObject.code == plupload.FILE_EXTENSION_ERROR) {
            msg = "上传文件\"" + errObject.file.name + "\"时出错：不能上传该文件类型！";
        }
        else if (errObject.code == plupload.FILE_DUPLICATE_ERROR) {
            msg = "上传文件\"" + errObject.file.name + "\"时出错：不能上传重名的文件！";
        }
        else if (errObject.code == plupload.FILE_SIZE_ERROR) {
            msg = "上传文件\"" + errObject.file.name + "\"时出错：不能上传大于(" + _maxUpload + "M)的文件！";
        }
        else {
            msg = "上传文件\"" + errObject.file.name + "\"时出错!";
        }

        MsgBox.showErrTips(msg);
    });
}

//导出excel
lpsys.Export = function (grid,param,url){
	var columns = grid.columns;
    function getColumns(columns) {
        var cols = [];
        for (var i = 0; i < columns.length; i++) {
            var column = columns[i];
			if(column.type=="checkcolumn" || column.type=="indexcolumn")
				continue;
            var col = { header: column.header, field: column.field, type: column.type };
            if (column.columns) {
                col.columns = getColumns(column.columns);
            }
            cols.push(col);

        }
        return cols;
    }
    
	var uploadFormJq = $("#excelExportForm");
	if (jQuery("[name=upload_iframe]").size() < 1){
		uploadFormJq = $('<form id="excelExportForm" name="excelExportForm" target="_blank" method="post" action="' +url+ '"><input name="columns" type="hidden"/><input name="data" type="hidden"/></form>');
        jQuery(document.body).append(uploadFormJq);
    }
    
    uploadFormJq.find("[name=columns]").val(mini.encode(columns));
    uploadFormJq.find("[name=data]").val(mini.encode(param));
    uploadFormJq.get(0).submit();
}

//附件上传控件
lpsys.PlUpload = function (param) {

    this._Name = param.ContainerId;
    var cellName = "plupload_" + this._Name;
    lpsys.PlUpload[cellName] = this;

    this._divContainerJq = jQuery("#" + param.ContainerId);
    this._Path = param.Path || "";
    this._TableName = param.TableName;
    this._Field = param.Field;
    this._ObjId = param.ObjId;
    this._ReadOnly = param.ReadOnly || false;
    this._MaxUpload = param.MaxUpload || 100;
    
    this._files = [];
    
    var self = this;
    if(this._ObjId){
    	var param = {tablename:this._TableName,field:this._Field,objid:this._ObjId};
    	var url = this._Path + "/sys/attach/list";
    	getJson(url,param,true,function(list){
    		for(var i=0;i<list.length;i++){
    			var f = {
					fileId : list[i].fileid,
					fileName : list[i].filename
				}
    			self._files.push(f);
    		}
    		
    		self.init();
    	});
    }
    else
    	this.init();
}

lpsys.PlUpload.get = function (name) {
    var cellName = "plupload_" + name;
    return lpsys.PlUpload[cellName];
}

/**
 * 设置控件只读
 */
lpsys.PlUpload.prototype.setReadOnly = function (readOnly) {
    this._ReadOnly = readOnly ? true : false;
    this.refreshFile();
}

/**
 * 上传控件初始化，上传
 */
lpsys.PlUpload.prototype.init = function () {
    if (this._pluploader)
        return;
    
    //添加用于打开附件的iframe
    if (jQuery("[name=upload_iframe]").size() < 1){
        jQuery(document.body).append('<iframe name="upload_iframe"  style="width: 1px; height: 1px;display:none" src="about:blank"></iframe>');
    }
   
    var strHtml = '<div class="plucontainer" style="width:100%;height:100%;">' +
						'<div class="pluaddfileheader" style="height: 25px;"><a class="pluaddfilelink"><b class="pluaddfilelinkattach"></b>添加附件</a></div>' +
						'<div class="plufilecontainer"></div>' +
						'<div class="progressbarcontainer">' +
							'<div class="progressbar" style="margin-top:0px;">' +
								'<div class="progressbartext">0%</div>' +
								'<div class="progressbarvalue"></div>' +
								'<div class="progressbartext">0%</div>' +
							'</div>' +
						'</div>' +
					'</div>';

    this._divContainerJq.append(strHtml);
    this._plucontainer = this._divContainerJq.find(".plucontainer");
    this._pluaddfileheader = this._divContainerJq.find(".pluaddfileheader");
    this._plufilecontainer = this._divContainerJq.find(".plufilecontainer");
    this._progressbarcontainer = this._divContainerJq.find(".progressbarcontainer");
    var fileContainerH = this._divContainerJq.height();
    if (!this._ReadOnly)
        fileContainerH = fileContainerH - 25;
    else {
        this._plucontainer.addClass("plureadonly");
        this._pluaddfileheader.hide();
    }

    //this._plufilecontainer.height(fileContainerH);
    this._progressbarcontainer.height(fileContainerH).width(this._plufilecontainer.width());
    this._progressbarcontainer.find(".progressbar").css("marginTop", (fileContainerH - 22) / 2 + "px");

    this.refreshFile();
    var self = this;

    if (this._ReadOnly)
        return;

    var addFileBtnId = this._divContainerJq.find(".pluaddfilelink").get(0);
    var url = this._Path + "/sys/attach/upload"
            + "?cellid=" + this._Name + "&tablename=" + this._TableName + "&field=" +this._Field+ "&objid=" + this._ObjId;
    self._pluploader = new plupload.Uploader({
        browse_button: addFileBtnId, //触发文件选择对话框的按钮，为那个元素id
        url: url, //服务器端的上传页面地址
        //runtimes:'flash',
        filters: {
            //mime_types : [ //只允许上传图片和zip文件
              //{ title : "Image files", extensions : "jpg,gif,png,bmp" }, 
              //{ title : "Zip files", extensions : "zip" },
              //{ title : "rar files", extensions : "rar" }
            //],
            max_file_size: self._MaxUpload + 'Mb',
            prevent_duplicates: true //不允许选取重复文件
        },
        flash_swf_url: this._Path + '/js/plupload/Moxie.swf' //swf文件，当需要使用swf方式进行上传时需要配置该参数
    });

    this._pluploader.init();

    this._pluploader.bind('FilesAdded', function (uploader, files) {
        var fileCount = self._files.length + files.length;
        for (var i = 0; i < self._files.length; i++) {
            for (var j = 0; j < files.length; j++) {
                if (self._files[i].fileName == files[j].name) {
                    alert("不能上传重名的附件！");
                    uploader.splice(0);
                    return;
                }
            }
        }

        self.__errMsg = new Array();
        uploader.start();
        self.showProgressBar(0);
    });

    this._pluploader.bind('FileUploaded', function (uploader, file, responseObject) {
        jQuery(document.body).append(responseObject.response);
    });

    this._pluploader.bind('UploadProgress', function (uploader, file) {
        self.showProgressBar(uploader.total.percent);
    });

    this._pluploader.bind('UploadComplete', function (uploader, files) {
        self.hideProgressBar();
        uploader.splice(0);
        for (var i = 0; i < self.__errMsg.length; i++) {
            alert(self.__errMsg[i]);
        }
        self.__errMsg = null;
    });

    this._pluploader.bind('Error', function (uploader, errObject) {
        var msg = "";
        if (errObject.code == plupload.HTTP_ERROR) {
            msg = "上传文件\"" + errObject.file.name + "\"时出错：网络故障！";
        }
        else if (errObject.code == plupload.FILE_EXTENSION_ERROR) {
            msg = "上传文件\"" + errObject.file.name + "\"时出错：不能上传该文件类型！";
        }
        else if (errObject.code == plupload.FILE_DUPLICATE_ERROR) {
            msg = "上传文件\"" + errObject.file.name + "\"时出错：不能上传重名的文件！";
        }
        else if (errObject.code == plupload.FILE_SIZE_ERROR) {
            msg = "上传文件\"" + errObject.file.name + "\"时出错：不能上传大于(" +self._MaxUpload+ "M)的文件！";
        }
        else {
            msg = "上传文件\"" + errObject.file.name + "\"时出错!";
        }

        if (self.__errMsg)
            self.__errMsg.push(msg);
        else
            alert(msg);
    });
}

lpsys.PlUpload.prototype.UploadFileCallBack = function(isOK,msg){
	if(isOK){
		for(var i=0; i < msg.length; i++){
			this.addFile(msg[i].fileId,msg[i].fileName);
		}
	}
	else{
		this.__errMsg.push(msg);
	}
}

lpsys.PlUpload.prototype.showProgressBar = function(percent){
	this._progressbarcontainer.show();
	this._progressbarcontainer.find(".progressbartext").text(percent + "%");
	this._progressbarcontainer.find(".progressbarvalue").css("width",percent + "%");
}

lpsys.PlUpload.prototype.hideProgressBar = function(){
	this._progressbarcontainer.hide();
}

lpsys.PlUpload.prototype.addFile = function(fileId,fileName){
	var f = {
		fileId : fileId,
		fileName : fileName
	}
	this._files.push(f);
	this.refreshFile();
}

lpsys.PlUpload.prototype.delFile = function (index) {
	var $this = this;
    if (window.confirm("确定要删除附件吗？")) {
    	var url = this._Path + "/sys/attach/del?fileid=" + $this._files[index].fileId;
		getJson(url,null,true,function(re){
			if(re.isOk){
				$this._files.splice(index, 1);
				$this.refreshFile();
			}
		});
    	
    }
}

lpsys.PlUpload.prototype.refreshFile = function(){
	var fileContainer = this._plufilecontainer;
	fileContainer.empty();
	
	for(var i=0; i < this._files.length; i++){
		var html = "<div class=\"plufileitem\">"
		     +"              <span class=\"plufilelink\">"
		     + "                  <span onclick=\"lpsys.PlUpload.showFile('" + this._Name + "'," + i + ");\""
		     						+" class=\"plufilelinktext\" title=\"" +this._files[i].fileName+ "\">"
		     							+(i+1)+ "."+this._files[i].fileName
		     +"                  </span>"
		     +"              </span>"
		     + "              <span onclick=\"lpsys.PlUpload.delFile('" + this._Name + "'," + i + ");\" "
		     					+"class=\"plufilebtn\">删除</span>"
		     +"</div>";
		var fileItemJq = jQuery(html);
		if(this._files[i].fileId)
			fileItemJq.attr("fileId",this._files[i].fileId);
		fileContainer.append(fileItemJq);
	}
}

lpsys.PlUpload.delFile = function (name, index) {
	lpsys.PlUpload.get(name).delFile(index);
}

lpsys.PlUpload.showFile = function(name,index){
    var pluCell = lpsys.PlUpload.get(name);
    var file = pluCell._files[index];
    lpsys.PlUpload.openFile(pluCell._Path,file.fileId);
}

lpsys.PlUpload.openFile = function (path,fileId) {
    var url = path + "/sys/attach/show?fileid=" + fileId;
    jQuery("[name=upload_iframe]").attr("src", "about:blank").attr("src", url);
}