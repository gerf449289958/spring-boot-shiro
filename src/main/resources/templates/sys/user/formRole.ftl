<#assign base=request.contextPath />
<!DOCTYPE html>
<div class="row">
    <div class="col-xs-12">
        <div class="page-title-box">
            <h4 class="page-title m-t-10">分配角色 </h4>
            <ol class="breadcrumb p-0 m-0">
            </ol>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<div class="col-sm-12">
    <div class="card-box">
        <form id="postForm" data-parsley-validate>
            <input type="hidden" id="id" name="id" value="${(sysUser.id?c)!''}">
            <div class="row">
                <div class="form-group col-md-6">
                    <label for="username">账户名称</label>
                    <input type="text" id="username" name="username" class="form-control" value="${(sysUser.username)!''}" readonly>
                </div>
                <div class="form-group col-md-6">
                    <label for="name">用户名称</label>
                    <input type="text" id="name" name="name" class="form-control" value="${(sysUser.name)!''}" readonly>
                </div>
            </div>
            <hr/>
            <div class="row">
                <div class="form-group col-md-12">
                    <select class="select2 select2-multiple" id="roleIds" name="roleIds[]" multiple="multiple" multiple data-placeholder="请选择角色" required>
                        <#list allRoles as obj >
                            <option value="${obj.id?c}">${obj.role}(${obj.description})</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-offset-5 col-sm-9">
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-primary" onclick="saveRole('${sysUser.id?c}')">
                        <i class="fa fa-check"></i> 保存
                    </button>
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-success" onclick="showRight('${base}/sys/user/list.page');">
                        <i class="fa fa-undo"></i> 返回
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var roleIds = ${(jsonList)!''};
    var listUrl = '${base}/sys/user/list.page';
    var saveRoleUrl = '${base}/sys/user/saveRole.json';
    $('.select2').select2();
    $('#roleIds').val(roleIds).trigger('change');
    //表单验证初始化
    var $parsley = $('#postForm').parsley();

    //保存授权菜单
    function saveRole(id){
        var roleIds = $("#roleIds").val();
        var flag = $parsley.validate();
        if(!flag){
            return;
        }
        $.post(saveRoleUrl,{"id":id,"roleIds":JSON.stringify( roleIds )},function(data) {
            if (data.ret) {
                swal(data.msg, "", "success");
                $("#main_content").load(listUrl);
            }else{
                swal(data.msg, "", "error");
            }
        });
    }
</script>