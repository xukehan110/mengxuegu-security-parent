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
            enable: false  //不显示勾选框
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

                if (treeNode.id == $("#id").val()){
                    layer.tips('自己不能作为父资源','#'+treeId,{time: 1000})
                    return;
                }

                //选的的节点放到父节点处
                // $("#parentId").val(treeNode.id);
                // $("#parentName").val(treeNode.name);
                parentPermission(treeNode.id,treeNode.name);
            }
        }

    };

    //查询所有的权限
    $.post(contextPath + "permission/list",function (data) {
        console.log("data:"+data.data);
        var permissionTree = $.fn.zTree.init($("#permissionTree"),menuSetting,data.data);
        var parentId = $("#parentId").val();
        if (parentId !== null && parentId !== '' && parentId !== undefined && parentId != 0){
            var nodes = permissionTree.getNodesByParam("id",parentId,null);
            $("#parentName").val(nodes[0].name);
        }
    })
}

function parentPermission(parentId,parentName) {
    if (parentId == null || parentName == null){
        parentId = 0;
        parentName = '根菜单';
    }
    $("#parentId").val(parentId);
    $("#parentName").val(parentName);
}