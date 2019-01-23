<#assign base=request.contextPath />
<!DOCTYPE html>
<div class="row">
    <div class="col-xs-12">
        <div class="page-title-box">
            <h4 class="page-title m-t-10">角色管理 </h4>
            <ol class="breadcrumb p-0 m-0">
                <li>
                    <button type="button" class="btn btn-default btn-md query" onclick="query()"><i class="fa fa-filter"></i> 查询</button>
                    <button type="button" class="btn btn-default btn-md" onclick="add()"><i class="fa fa-plus"></i> 新增</button>
                </li>
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-sm-12">
        <div class="card-box">
            <div class="btn-group dropdown-btn-group pull-left hide search">
                <div class="text-muted font-13 m-b-20">
                    <form id="queryForm" class="form-inline">
                        <div class="form-group m-r-10">
                            <label for="role">角色名称：</label>
                            <input type="text" class="form-control width-120" id="role" name="role">
                        </div>
                        <div class="form-group m-r-10">
                            <label for="available">状态：</label>
                            <div class="control-inline width-90">
                                <select class="form-control select2" id="available" name="available">
                                    <option value="" >请选择</option>
                                    <option value="1">正常</option>
                                    <option value="0">停用</option>
                                </select>
                            </div>

                        </div>
                        <button type="button" class="btn btn-custom btn-md" onclick="selectQuery('');"> 查询</button>
                        <button type="button" class="btn btn-default btn-md" onclick="reset();"> 重置</button>
                    </form>
                </div>
            </div>
            <table id="datatable-editable" class="table table-colored-bordered table-bordered-custom table-hover m-0" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <th>角色名称</th>
                        <th>角色描述</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <#list page.content as obj>
                        <tr class="gradeX">
                            <td>${(obj.role)!'' }</td>
                            <td>${(obj.description)!'' }</td>
                            <td id="available-${obj.id?c}">
                                <#if obj.available == '1'>
                                    <span class="label label-success">正常</span>
                                <#else>
                                    <span class="label label-danger">停用</span>
                                </#if>
                            </td>
                            <td class="actions">
                                <a href="javascript:edit('${obj.id?c}');" class="on-default" title="编辑角色"><i class="fa fa-pencil"></i></a>
                                <#if obj.available == '1'>
                                    <a href="javascript:stop('${obj.id?c}');" class="on-default" title="停用角色"><i class="fa fa-ban"></i></a>
                                <#else>
                                    <a href="javascript:star('${obj.id?c}');" class="on-default" title="启用角色"><i class="fa fa-check"></i></a>
                                </#if>
                                <a href="javascript:doDelete('${obj.id?c}');" class="on-default" title="删除角色"><i class="fa fa-trash-o"></i></a>
                                <a href="javascript:setMenu('${obj.id?c}');" class="on-default" title="功能菜单"><i class="fa fa-navicon"></i></a>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        <#include "../../include/paginator.ftl" />
        </div>
    </div>
</div>
<script type="text/javascript">
    var listUrl = '${base}/sys/role/list.page';
    var addUrl = "${base}/sys/role/add.page";
    var editUrl = '${base}/sys/role/edit.page';
    var delUrl = '${base}/sys/role/delete.json';
    var stopUrl = '${base}/sys/role/stop.json';
    var starUrl = '${base}/sys/role/star.json';
    var setUserUrl = '${base}/sys/role/setUser.page';
    var setMenuUrl = '${base}/sys/role/setMenu.page';
    var param = JSON.parse('${param}');

    $(function () {

        $(".select2").select2();
        showAndHide();
        if(param.role){
            $("#role").val(param.role);
        }
        if(param.available){
            $("#available").val(param.available).trigger("change");
        }
    });

    //查询
    function selectQuery(pageIndex){
        // var role = $.trim($("#role").val());
        // var available = $.trim($("#available").val());
        // var data = $.param({
        //     "pageIndex":pageIndex,
        //     "role":role,
        //     "available":available,
        // });
        var data = $.param({"pageIndex":pageIndex})+'&'+$('#queryForm').serialize();
        $("#main_content").load(listUrl,data);
    }
    //新增角色
    function add() {
        $("#main_content").load(addUrl);
    }
    //编辑角色
    function edit(id) {
        var data = {"id":id};
        $("#main_content").load(editUrl,data);
    }
    //删除角色
    function doDelete(id) {
        swal({
            title: "删除",
            text: "您确定删除该角色吗？",
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
    //启用角色
    function star(id) {
        swal({
            title: "启用",
            text: "您确定启用该角色吗？",
            type: "warning",
            cancelButtonText:"取消",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            closeOnConfirm: false
        }, function(){
            $.post(starUrl,{"id":id},function(data) {
                if (data.ret) {
                    swal(data.msg, "", "success");
                    $("#available-"+id).html("<span class=\"label label-success\">正常</span>");
                }else{
                    swal(data.msg, "", "error");
                }
            });
        });
    }
    //停用角色
    function stop(id) {
        swal({
            title: "停用",
            text: "您确定停用该角色及关联用户吗？",
            type: "warning",
            cancelButtonText:"取消",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            closeOnConfirm: false
        }, function(){
            $.post(stopUrl,{"id":id},function(data) {
                if (data.ret) {
                    swal(data.msg, "", "success");
                    $("#available-"+id).html("<span class=\"label label-danger\">停用</span>");
                }else{
                    swal(data.msg, "", "error");
                }
            });
        });
    }
    //授权用户
    function setUser(id) {
        var data = {"id":id};
        $("#main_content").load(setUserUrl,data);
    }
    //授权功能菜单
    function setMenu(id) {
        var data = {"id":id};
        $("#main_content").load(setMenuUrl,data);
    }
</script>
