<#assign base=request.contextPath />
<!DOCTYPE html>
<div class="row">
    <div class="col-xs-12">
        <div class="page-title-box m-t-10">
            <h4 class="page-title">角色管理 </h4>
            <ol class="breadcrumb p-0 m-0">
                <li>
                    <button type="button" class="btn btn-default btn-md add"><i class="fa fa-plus"></i> 新增</button>
                </li>
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-sm-12">
        <div class="card-box">
            <table id="datatable-editable" class="table table-colored-bordered table-bordered-custom table-hover m-0" cellspacing="0" width="100%">
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    var datas = ${(jsonList)!''};
    var listUrl = '${base}/sys/menu/list.page';
    var addUrl = "${base}/sys/menu/add.page";
    var editUrl = '${base}/sys/menu/edit.page';
    var delUrl = '${base}/sys/menu/delete.json';

    //初始化操作按钮的方法
    window.operateEvents = {
        'click .delete-row': function (e, value, row, index) {
            doDelete(row.id);
        },
        'click .edit-row': function (e, value, row, index) {
            update(row.id);
        },
        'click .add-row': function (e, value, row, index) {
            add(row.id);
        }
    };

    $(function () {
        initTable();
        //新增根目录
        $(".add").click(function() {
            add('');
        });
    });

    //初始化tableTree
    function initTable() {
        var $table = $("#datatable-editable");
        $table.bootstrapTable({
            data:datas,
            idField: 'id',
            dataType:'jsonp',
            columns: [
                { field: 'name',  title: '菜单名称',
                    formatter:function(value, row, index){
                        return "<i class='"+row.icon+"'></i> "+value;
                    } },
                { field: 'url',  title: '链接' },
                { field: 'sort',  title: '排序', align: 'center'},
                { field: 'type',  title: '类型', align: 'center',
                    formatter:function (value, row, index) {
                        if(value == '1'){
                            return "<span class=\"label label-success\">菜单</span>";
                        }else if(value == '2'){
                            return "<span class=\"label label-danger\">权限</span>";
                        }else {
                            return "<span class=\"label label-default\">未知</span>";
                        }
                    }},
                { field: 'available',  title: '可见', align: 'center',
                    formatter:function (value, row, index) {
                        if(value == '1'){
                            return "<span class=\"label label-success\">显示</span>";
                        }else if(value == '0'){
                            return "<span class=\"label label-danger\">隐藏</span>";
                        }else {
                            return "<span class=\"label label-default\">未知</span>";
                        }
                    }},
                {field: 'permission',  title: '权限标识'},
                { field: 'operate', title: '操作',class:'actions', align: 'center',events:operateEvents, formatter:'operateFormatter' }
            ],
            // bootstrap-table-treegrid.js 插件配置 -- start
            //在哪一列展开树形
            treeShowField: 'name',
            //指定父id列
            parentIdField: 'parentId',
            onResetView: function(data) {
                $table.treegrid({
                    initialState: 'collapsed',// 所有节点都折叠
                    //initialState: 'expanded',// 所有节点都展开，默认展开
                    treeColumn: 0,
                    expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',  //图标样式
                    expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                    onChange: function() {
                        $table.bootstrapTable('resetWidth');
                    }
                });
                //只展开树形的第一级节点
                $table.treegrid('getRootNodes').treegrid('expand');
            }
        });
    }

    // 格式化按钮
    function operateFormatter(value, row, index) {
        if(row.type == '2'){
            return [
                '<a href="#" class="on-default edit-row" title="编辑权限"><i class="fa fa-pencil"></i></a>',
                '<a href="#" class="on-default delete-row" title="删除权限"><i class="fa fa-trash-o"></i></a>'
            ].join(' ');
        }else{
            return [
                '<a href="#" class="on-default edit-row" title="编辑菜单"><i class="fa fa-pencil"></i></a>',
                '<a href="#" class="on-default delete-row" title="删除菜单"><i class="fa fa-trash-o"></i></a>',
                '<a href="#" class="on-default add-row" title="新增下级菜单"><i class="fa fa-plus-square"></i></a>'
            ].join(' ');
        }
    }

    //添加菜单
    function add(id){
        var data = {"parentId":id};
        $("#main_content").load(addUrl,data);
    }

    //编辑菜单
    function update(id){
        var data = {"id":id};
        $("#main_content").load(editUrl,data);
    }
    //删除
    function doDelete(id){
        swal({
            title: "删除",
            text: "您确定删除该菜单吗？",
            type: "warning",
            cancelButtonText:"取消",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            closeOnConfirm: false
        }, function(){
            $.post(delUrl,{"id":id},function(data) {
                if (data.ret) {
                    swal(data.msg, "", "success");
                    $("#main_content").load(listUrl);
                }else{
                    swal(data.msg, "", "error");
                }
            });
        });
    }
</script>