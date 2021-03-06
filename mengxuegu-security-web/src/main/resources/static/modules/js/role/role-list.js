var $table = $("#table")
$(function () {
    var options = {
        url: contextPath + "role/page",
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
            {
                field: 'name',title: '角色名称'
            },
            {
                field: 'remark',title:'角色描述'
            },
            {
                field: 'action',title:'操作',visible: true, width: 50,align: 'center',formatter:$.operationFormatter
            }
        ]

    };

    $.pageTable(options)
});

//搜索方法
function searchForm(){

    var query = {
        size: 3,  //没页显示多少条
        current: 1, //当前页码
        name: $("#name").val().trim()
    };

    $table.bootstrapTable("refresh",{query: query})
}
