<#assign base=request.contextPath />
<!DOCTYPE html>
<div class="col-sm-12">
    <div class="card-box">
        <form id="postForm" data-parsley-validate>
            <input type="hidden" id="id" name="id" value="${(menu.id?c)!''}">
            <div class="row">
                <div class="col-md-12 ">
                    <#if parentMenu ??>
                        <div class="form-group">
                            <label for="parentName">上级菜单</label>
                            <input type="hidden" id="parentId" name="parentId" value="${parentMenu.id}">

                            <div class="input-group">
                                <input type="text" name="parentName" class="form-control" id="parentName" value="${parentMenu.name}" readonly>
                                <span class="input-group-btn">
                                    <button type="button" class="btn waves-effect waves-light" onclick="menuShow()"><i class="fa fa-search"></i></button>
                                </span>
                            </div>
                        </div>
                    <#else>
                        <div class="form-group">
                            <label for="parentName">上级菜单</label>
                            <input type="hidden" id="parentId" name="parentId" value="0">
                            <div class="input-group">
                                <input type="text" name="parentName" class="form-control" id="parentName" value="系统菜单" readonly>
                                <span class="input-group-btn">
                                    <button type="button" class="btn waves-effect waves-light" onclick="menuShow()"><i class="fa fa-search"></i></button>
                                </span>
                            </div>
                        </div>
                    </#if>

                    <div class="form-group">
                        <label for="name">菜单名称<span class="text-danger">*</span></label>
                        <input type="text" name="name" parsley-trigger="change" required="" class="form-control" value="${(menu.name)!''}"
                               data-parsley-length="[2, 15]"
                               data-parsley-length-message="菜单名称长度需要在2-15个字之间">
                    </div>
                    <div class="form-group">
                        <label for="href">链接</label>
                        <input type="text" name="url" parsley-trigger="change" class="form-control" value="${(menu.url)!''}">
                    </div>
                    <div class="form-group">
                        <label class="m-b-10">菜单类型<span class="text-danger">*</span></label>
                        <br>
                        <#if menu ??>
                            <div class="radio radio-info radio-inline">
                                <input type="radio" id="radio1" value="1" name="type" <#if menu.type ?? && menu.type == '1'>checked<#else>checked</#if>>
                                <label for="radio1"> 菜单 </label>
                            </div>
                            <div class="radio radio-info radio-inline">
                                <input type="radio" id="radio2" value="2" name="type" <#if menu.type ?? && menu.type == '2'>checked</#if>>
                                <label for="radio2"> 权限 </label>
                            </div>
                        <#else>
                            <div class="radio radio-info radio-inline">
                                <input type="radio" id="radio1" value="1" name="type" checked>
                                <label for="radio1"> 菜单 </label>
                            </div>
                            <div class="radio radio-info radio-inline">
                                <input type="radio" id="radio2" value="2" name="type">
                                <label for="radio2"> 权限 </label>
                            </div>
                        </#if>
                    </div>
                    <div class="form-group">
                        <label for="icon">菜单图标</label>
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i id="menuIcon" class="${(menu.icon)!'fa fa-fw'}"></i>
                            </span>
                            <input type="text" name="icon" class="form-control" id="icon" value="${(menu.icon)!''}">
                            <span class="input-group-btn">
                                <button type="button" class="btn waves-effect waves-light" onclick="iconShow()"><i class="fa fa-search"></i></button>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="permission">权限标识</label>
                        <input type="text" name="permission" parsley-trigger="change" class="form-control" id="permission" value="${(menu.permission)!''}">
                    </div>
                    <div class="form-group">
                        <label for="sort">排序<span class="text-danger">*</span></label>
                        <input type="text" name="sort" parsley-trigger="change" required="" class="form-control" id="sort" value="${(menu.sort?c)!''}"
                               data-parsley-min="1"
                               data-parsley-min-message="排序最小为1">
                    </div>
                    <div class="form-group">
                        <label class="m-b-10">可见<span class="text-danger">*</span></label>
                        <br>
                        <#if menu ??>
                            <div class="radio radio-info radio-inline">
                                <input type="radio" id="radio3" value="1" name="available" <#if menu.available ?? && menu.available == '1'>checked</#if>>
                                <label for="radio3"> 显示 </label>
                            </div>
                            <div class="radio radio-info radio-inline">
                                <input type="radio" id="radio4" value="0" name="available" <#if menu.available ?? && menu.available == '0'>checked</#if>>
                                <label for="radio4"> 隐藏 </label>
                            </div>
                        <#else>
                            <div class="radio radio-info radio-inline">
                                <input type="radio" id="radio3" value="1" name="available" checked>
                                <label for="radio3"> 显示 </label>
                            </div>
                            <div class="radio radio-info radio-inline">
                                <input type="radio" id="radio4" value="0" name="available">
                                <label for="radio4"> 隐藏 </label>
                            </div>
                        </#if>
                    </div>
                    <div class="form-group">
                        <label for="remarks">备注</label>
                        <input type="text" name="remarks" parsley-trigger="change" class="form-control" id="sort" value="${(menu.remarks)!''}"
                               data-parsley-maxlength="200"
                               data-parsley-maxlength-message="备注长度需要在200个字以内">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-offset-5 col-sm-9">
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-primary" id="submit">
                        <i class="fa fa-check"></i> 保存
                    </button>
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-success" onclick="showRight('${base}/sys/menu/list.page');">
                        <i class="fa fa-undo"></i> 返回
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<div id="selectMenu" class="modal" tabindex="-1" role="dialog" aria-labelledby="custom-width-modalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog" style="width:30%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="custom-width-modalLabel">选择菜单</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-2 control-label">关键字</label>
                        <div class="col-md-10">
                            <input type="text" class="form-control" onkeyup="AutoMatch(this)">
                        </div>
                    </div>
                </form>
                <div id="menuContent" class="menuContent">
                    <ul id="treeDemo" class="ztree">
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="iconModal" class="modal" tabindex="-1" role="dialog" aria-labelledby="custom-width-modalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog" style="width:90%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="custom-width-modalLabel">图标</h4>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="#fontawesome" data-toggle="tab" aria-expanded="false">
                            <span class="visible-xs"><i class="fa fa-home"></i></span>
                            <span class="hidden-xs">fontawesome</span>
                        </a>
                    </li>
                    <li class="">
                        <a href="#materialdesign" data-toggle="tab" aria-expanded="true">
                            <span class="visible-xs"><i class="fa fa-user"></i></span>
                            <span class="hidden-xs">materialdesign</span>
                        </a>
                    </li>
                    <li class="">
                        <a href="#ionicons" data-toggle="tab" aria-expanded="false">
                            <span class="visible-xs"><i class="fa fa-envelope-o"></i></span>
                            <span class="hidden-xs">ionicons</span>
                        </a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="fontawesome">
                    	<#include "../../include/icon/fontawesome.ftl" />
                    </div>
                    <div class="tab-pane" id="materialdesign">
                    	<#include "../../include/icon/materialdesign.ftl" />
                    </div>
                    <div class="tab-pane" id="ionicons">
                    	<#include "../../include/icon/ionicons.ftl" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var setting = {
        view: {
            selectedMulti: false //是否允许多选
        },
        data: {
            key: {
                title:"name",
                name:"name",
                url: ""
            },
            simpleData: {
                enable: false,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: 0
            }
        },
        callback: {
            //zTree节点的点击事件
            onClick: onClick
        }
    };
    var zNodes = menuJson;

    //表单验证初始化
    var $parsley = $('#postForm').parsley();
    $("#submit").click(function() {
        var id = $("#id").val();
        var flag = $parsley.validate()
        if(!flag){
            return;
        }
        if(id){
            update();
        }else {
            save();
        }
    });
    //选择图标点击事件
    $(".tab-pane i").click(function(event){
        var icon = $(this).attr("class");
        $("#icon").val(icon);
        $("#menuIcon").attr("class",icon);
        $('#iconModal').modal('hide');
    });
    //图标模态框弹出
    function iconShow(){
        $('#iconModal').modal('show');
    }

    //菜单模态框弹出
    function menuShow(){
        InitialZtree();
        $('#selectMenu').modal('show');
    }

    //还原zTree的初始数据
    function InitialZtree() {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    }
    //根据文本框的关键词输入情况自动匹配树内节点 进行模糊查找
    function AutoMatch(txtObj) {
        if (txtObj.value.length > 0) {
            InitialZtree();
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var nodeList = zTree.getNodesByParamFuzzy("menuName", txtObj.value);
            //将找到的nodelist节点更新至Ztree内
            $.fn.zTree.init($("#treeDemo"), setting, nodeList);
        } else {
            InitialZtree();
        }
    }
    function onClick(e, treeId, treeNode) {
        //将选中节点的名称显示在文本框内
        var parentName = $("#parentName");
        parentName.attr("value", treeNode.name);
        var parentId = $("#parentId");
        parentId.attr("value", treeNode.id);
        $('#selectMenu').modal('hide');
    }

    //保存
    function save(){
        var url = "${base}/sys/menu/save.json";
        $.post(url, $("#postForm").serialize(), function(data) {
            if (data.ret) {
                swal(data.msg, "", "success");
                $("#main_content").load('${base}/sys/menu/list.page');
            }else{
                swal(data.msg, "", "error");
            }
        });
    }
    //更新
    function update(){
        var url = "${base}/sys/menu/update.json";
        $.post(url, $("#postForm").serialize(), function(data) {
            if (data.ret) {
                swal(data.msg, "", "success");
                $("#main_content").load('${base}/sys/menu/list.page');
            }else{
                swal(data.msg, "", "error");
            }
        });
    }

</script>