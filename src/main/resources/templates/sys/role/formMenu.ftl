<#assign base=request.contextPath />
<!DOCTYPE html>
<div class="row">
    <div class="col-xs-12">
        <div class="page-title-box">
            <h4 class="page-title m-t-10">角色授权功能菜单 </h4>
            <ol class="breadcrumb p-0 m-0">
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<div class="col-sm-12">
    <div class="card-box">
        <form id="postForm" data-parsley-validate>
            <input type="hidden" id="id" name="id" value="${(sysRole.id?c)!''}">
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="role">角色名称</label>
                    <input type="text" id="role" name="role" class="form-control" value="${(sysRole.role)!''}" readonly>
                </div>
                <div class="form-group col-md-6">
                    <label for="description">角色描述</label>
                    <input type="text" id="description" name="description" class="form-control" value="${(sysRole.description)!''}" readonly>
                </div>
            </div>
            <hr/>
            <div class="row">
                <ul id="tree" class="ztree"></ul>
            </div>
            <div class="row">
                <div class="col-sm-offset-5 col-sm-9">
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-primary" onclick="saveMenu('${sysRole.id?c}')">
                        <i class="fa fa-check"></i> 保存
                    </button>
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-success" onclick="showRight('${base}/sys/role/list.page');">
                        <i class="fa fa-undo"></i> 返回
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var datas = ${(jsonList)!''};
    var menuIds = ${(menuIds)!''};
    var menus = [];
    var listUrl = '${base}/sys/role/list.page';
    var saveMenuUrl = '${base}/sys/role/saveMenu.json';
    var setting = {
        check: {
            enable: true
        },
        data: {
            key: {
                url: ""
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        },
        callback: {
            onClick: onClick
        }
    };
    $(function () {
        $.fn.zTree.init($("#tree"), setting, datas);
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        treeObj.expandAll(true);
        var node = treeObj.getNodes();
        var nodes = treeObj.transformToArray(node);
        for (var i=0, l=nodes.length; i < l; i++) {
            if(menuIds.indexOf(nodes[i].id) != -1){
                treeObj.checkNode(nodes[i], true, false);
            }
        }
    });

    function onClick(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        for (var i=0, l=nodes.length; i < l; i++) {
            treeObj.checkNode(nodes[i], true, false);
        }
    }

    //保存授权菜单
    function saveMenu(id){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getCheckedNodes();

        if(nodes.length <= 0 ){
            swal("请选择授权菜单", "", "warning");
            return;
        }
        $(nodes).each(function (index,value) {
            menus.push(value.id);
        });
        $.post(saveMenuUrl,{"id":id,"menuIds":JSON.stringify( menus )},function(data) {
            if (data.ret) {
                swal(data.msg, "", "success");
                $("#main_content").load(listUrl);
            }else{
                swal(data.msg, "", "error");
            }
        });
    }
</script>
