var $table = $("#table")
$(function () {
    var options = {
        url: contextPath + "user/page",
        pageNumber: 1,
        pageSize: 3,
        columns: [
            {
                title: '序号',width: 20, formatter:function (value,row,index) {
                    return index+1;
                }
            },
            {
                field: 'id', visible: false
            },
            {field: 'username',title: '用户名'},
            {field: 'nickName',title: '昵称'},
            {field: 'mobile',title: '手机号'},
            {field: 'accountNonExpired',title: '账号过期',formatter: statusFormatter},
            {field: 'accountNonLocked',title: '是否被锁定',formatter: statusFormatter},
            {field: 'credentialsNonExpired',title: '密码过期',formatter: statusFormatter},
            {
                field: 'action',title:'操作',visible: true, width: 50,align: 'center',formatter:$.operationFormatter
            }
        ]
    };

    $.pageTable(options)
});

function statusFormatter(value,row,index) {
    return value == true? "<span class='badge bg-success'>否</span>":"<span class='badge bg-danger'>是</span>"
}


//搜索方法
function searchForm(){

    var query = {
        size: 3,  //每页显示多少条
        current: 1, //当前页码
        username: $("#username").val().trim(),
        mobile: $("#mobile").val().trim()
    };

    $table.bootstrapTable("refresh",{query: query})
}


