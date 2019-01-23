<#assign base=request.contextPath />
<!DOCTYPE html>
<div class="row">
    <div class="col-xs-12">
        <div class="page-title-box">
            <h4 class="page-title m-t-10">角色编辑 </h4>
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
                <div class="col-md-12 ">

                    <div class="form-group">
                        <label for="role">角色名称<span class="text-danger">*</span></label>
                        <input type="text" id="role" name="role" parsley-trigger="change" required="" class="form-control" value="${(sysRole.role)!''}"
                               data-parsley-length="[2, 15]"
                               data-parsley-length-message="角色名称长度需要在2-15个字之间">
                    </div>
                    <div class="form-group">
                        <label for="description">角色描述<span class="text-danger">*</span></label>
                        <input type="text" id="description" name="description" parsley-trigger="change" required="" class="form-control" value="${(sysRole.description)!''}"
                               data-parsley-length="[2, 15]"
                               data-parsley-length-message="角色描述长度需要在2-15个字之间">
                    </div>
                    <div class="form-group">
                        <label for="remarks">备注</label>
                        <input type="text" name="remarks" parsley-trigger="change" class="form-control" id="sort" value="${(sysRole.remarks)!''}"
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
                    <button type="button" class="btn waves-effect waves-light btn-rounded btn-success" onclick="showRight('${base}/sys/role/list.page');">
                        <i class="fa fa-undo"></i> 返回
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var listUrl = "${base}/sys/role/list.page";
    var saveUrl = "${base}/sys/role/save.json";
    var updareUrl = "${base}/sys/role/update.json";
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
    //保存
    function save(){
        $.post(saveUrl, $("#postForm").serialize(), function(data) {
            if (data.ret) {
                swal(data.msg, "", "success");
                $("#main_content").load(listUrl);
            }else{
                swal(data.msg, "", "error");
            }
        });
    }
    //更新
    function update(){
        $.post(updareUrl, $("#postForm").serialize(), function(data) {
            if (data.ret) {
                swal(data.msg, "", "success");
                $("#main_content").load(listUrl);
            }else{
                swal(data.msg, "", "error");
            }
        });
    }
</script>