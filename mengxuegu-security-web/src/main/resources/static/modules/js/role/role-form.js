//permissionTree
$(function () {
    loadPermissionTree();
});

//加载树权限
function loadPermissionTree() {

    var menuSetting = {
        view:{
            showLine: true //显示连接线
        },
        check:{
            enable: true   //显示勾选框
        },
        data:{
            simpleData:{
                enable: true, //开启简单模式
                idKey: 'id',
                pIdKey: 'parentId',//父节点id
                rootPId: 0 //根节点数据
            },
            key:{
                name: 'name', //资源名称
                title: 'name' //鼠标放上去显示的id
            }
        },
        callback: {
            onClick: function (event,treeId,treeNode) {
                //treeNode 点击的节点id
                //被点击之后阻止跳转
                event.preventDefault();

            }
        }

    };

    //查询所有的权限
    $.post(contextPath + "permission/list",function (data) {
        console.log("data:"+data.data);
        var permissionTree = $.fn.zTree.init($("#permissionTree"),menuSetting,data.data);
        //判断是否修改页面，如果有就修改页面，没有则新增页面
        var id = $("#id").val();
        console.log(id);
        if (id !== '' && id !== null && id !== undefined){
            var perIds = JSON.parse($("#perIds").val());

            for (var i = 0; i<perIds.length; i++){
               var nodes = permissionTree.getNodeByParam("id",perIds[i],null);
               //勾选当前选中的节点
               permissionTree.checkNode(nodes, true, false);
               //是否展开
               permissionTree.expandNode(nodes, true, false);
            }
        }
    })
}

$("#form").submit(function () {
    //收集所有的被选中的节点
    var obj = $.fn.zTree.getZTreeObj("permissionTree");
    //获取被选中的节点集合
    var nodes = obj.getCheckedNodes(true);

    var perIds = [];
    for (var i = 0; i<nodes.length; i++){
        perIds.push(nodes[i].id);
    }
    $("#perIds").val(perIds);
    return true;
})