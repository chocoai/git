/**
 * 黄健
 * Created by JMan on 2018/1/16.
 */

var mainTabs;
var currentTab = null;

$(function() {
  mini.parse();
  mainTabs = mini.get("mainTabs");

  //给页签注册双击关闭事件
  $("#mainTabs .mini-tab").live("dblclick", function(){
    mainTabs.removeTab(mainTabs.getActiveTab());
  });

  //注册键盘事件
  $(document).keydown(function(e){
    var id = e.keyCode;
    switch (id){
      case 69:
        if($("#mainTabsMenu").css("display") == "block"){
          mainTabs.reloadTab(currentTab);
          closeMainTabsMenu();
        }
        break;
      case 68:
        if($("#mainTabsMenu").css("display") == "block"){
          mainTabs.removeTab(currentTab);
          closeMainTabsMenu();
        }
        break;
    }
  });


});

//添加页签，在主界面添加页签主要就是通过此方法
//_title：页签标题，
//_url：页签内容加载的URL地址，相对于项目跟路径，
//_verifyClose：是否校验关闭，如果校验则传入字符串1，否则传入字符串0或不传任何参数
function addMainTab(_title, _url){
  var tabs = mainTabs.getTabs();
  var tabsLength = tabs.length;
  //循环判断当前队列中是否已经存在_title标题的页签，如果存在则激活它
  for(var i=0; i<tabsLength; i++){
    if(tabs[i].title == _title){
      mainTabs.activeTab(mainTabs.getTab(_title));
      return false;
    }
  }
  var tab = {title: _title, name: _title, url: _url, showCloseButton: true};
  tab = mainTabs.addTab(tab);

  mainTabs.activeTab(tab);

}

//隐藏主页签右键菜单
function closeMainTabsMenu(){
  $("#mainTabsMenu, .mini-shadow").hide();
}

//刷新标签页
function refreshTab(){
  mainTabs.reloadTab(currentTab);
}

//关闭标签页
function closeCurrentTab(){
  mainTabs.removeTab(currentTab);
}

//关闭其他标签页
function closeOtherTabs(){
  mainTabs.removeAll(currentTab);
}

//关闭右侧标签页
function closeRightTabs(){
  var currentTabName = currentTab.name;
  var tabs = mainTabs.getTabs();
  var tabsLength = tabs.length;
  var startIndex = null;
  for(var i=0; i<tabsLength; i++){
    if(tabs[i].name == currentTabName){
      startIndex = i+1;
      break;
    }
  }
  for(var i=startIndex; i<=(tabsLength-1); i++){
    mainTabs.removeTab(startIndex);
  }
}

//关闭所有标签页
function closeAllTabs(){
  mainTabs.removeAll();
}

function onBeforeOpen(e) {
  currentTab = mainTabs.getTabByEvent(e.htmlEvent);
  if (!currentTab) {
    e.cancel = true;
  }
}
